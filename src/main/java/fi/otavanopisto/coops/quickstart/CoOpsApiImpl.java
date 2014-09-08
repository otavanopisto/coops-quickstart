package fi.otavanopisto.coops.quickstart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.codehaus.jackson.map.ObjectMapper;

import fi.foyt.coops.CoOpsConflictException;
import fi.foyt.coops.CoOpsForbiddenException;
import fi.foyt.coops.CoOpsInternalErrorException;
import fi.foyt.coops.CoOpsNotFoundException;
import fi.foyt.coops.CoOpsNotImplementedException;
import fi.foyt.coops.CoOpsUsageException;
import fi.foyt.coops.model.File;
import fi.foyt.coops.model.Join;
import fi.foyt.coops.model.Patch;
import fi.otavanopisto.coops.quickstart.dao.CoOpsSessionDAO;
import fi.otavanopisto.coops.quickstart.dao.FileDAO;
import fi.otavanopisto.coops.quickstart.dao.FileExtensionPropertyDAO;
import fi.otavanopisto.coops.quickstart.dao.FilePropertyDAO;
import fi.otavanopisto.coops.quickstart.dao.FileRevisionDAO;
import fi.otavanopisto.coops.quickstart.dao.FileRevisionExtensionPropertyDAO;
import fi.otavanopisto.coops.quickstart.dao.FileRevisionPropertyDAO;
import fi.otavanopisto.coops.quickstart.model.CoOpsSession;
import fi.otavanopisto.coops.quickstart.model.CoOpsSessionType;
import fi.otavanopisto.coops.quickstart.model.FileExtensionProperty;
import fi.otavanopisto.coops.quickstart.model.FileProperty;
import fi.otavanopisto.coops.quickstart.model.FileRevision;
import fi.otavanopisto.coops.quickstart.model.FileRevisionExtensionProperty;
import fi.otavanopisto.coops.quickstart.model.FileRevisionProperty;

@Dependent
@Stateless
public class CoOpsApiImpl implements fi.foyt.coops.CoOpsApi {

  private final static String COOPS_PROTOCOL_VERSION = "1.0.0";
  
  @Inject
  @Any
  private Instance<CoOpsDiffAlgorithm> algorithms;

  @Inject
  private FileDAO fileDAO;

  @Inject
  private FilePropertyDAO filePropertyDAO;

  @Inject
  private FileExtensionPropertyDAO fileExtensionPropertyDAO;

  @Inject
  private FileRevisionDAO fileRevisionDAO;

  @Inject
  private FileRevisionPropertyDAO fileRevisionPropertyDAO;

  @Inject
  private FileRevisionExtensionPropertyDAO fileRevisionExtensionPropertyDAO;

  @Inject
  private CoOpsSessionDAO coOpsSessionDAO;
  
  public File fileGet(String fileId, Long revisionNumber) throws CoOpsNotImplementedException, CoOpsNotFoundException, CoOpsUsageException, CoOpsInternalErrorException, CoOpsForbiddenException {
    fi.otavanopisto.coops.quickstart.model.File file = findFile(fileId);
    
    if (file == null) {
      throw new CoOpsNotFoundException();
    }

    if (revisionNumber != null) {
      // TODO: Implement
      throw new CoOpsNotImplementedException();
    } else {
      Long currentRevisionNumber = file.getRevisionNumber();
      String data = file.getData();
      List<FileProperty> fileProperties = filePropertyDAO.listByFile(file);
      Map<String, String> properties = new HashMap<String, String>();
      
      for (FileProperty fileProperty : fileProperties) {
        properties.put(fileProperty.getKey(),fileProperty.getValue());
      }
      
      return new File(currentRevisionNumber, data, file.getContentType(), properties);
    }
  }

  public List<Patch> fileUpdate(String fileId, String sessionId, Long revisionNumber) throws CoOpsNotFoundException, CoOpsInternalErrorException, CoOpsUsageException, CoOpsForbiddenException {
    CoOpsSession session = coOpsSessionDAO.findBySessionId(sessionId);
    if (session == null) {
      throw new CoOpsUsageException("Invalid session id"); 
    }
    
    if (revisionNumber == null) {
      throw new CoOpsUsageException("revisionNumber parameter is missing");
    }
    
    fi.otavanopisto.coops.quickstart.model.File file = findFile(fileId);
    
    if (file == null) {
      throw new CoOpsNotFoundException();
    }

    List<Patch> updateResults = new ArrayList<>();

    List<FileRevision> fileRevisions = fileRevisionDAO.listByFileAndRevisionGreaterThan(file, revisionNumber);

    if (!fileRevisions.isEmpty()) {
      for (FileRevision fileRevision : fileRevisions) {
        String patch = fileRevision.getData();
        
        Map<String, String> properties = null;
        Map<String, Object> extensions = null;
        
        List<FileRevisionProperty> revisionProperties = fileRevisionPropertyDAO.listByFileRevision(fileRevision);
        if (revisionProperties.size() > 0) {
          properties = new HashMap<>();
          for (FileRevisionProperty revisionProperty : revisionProperties) {
            properties.put(revisionProperty.getKey(), revisionProperty.getValue());
          }
        }
        
        List<FileRevisionExtensionProperty> revisionExtensionProperties = fileRevisionExtensionPropertyDAO.listByFileRevision(fileRevision);
        if (revisionExtensionProperties.size() > 0) {
          extensions = new HashMap<>();
          for (FileRevisionExtensionProperty revisionExtensionProperty : revisionExtensionProperties) {
            extensions.put(revisionExtensionProperty.getKey(), revisionExtensionProperty.getValue());
          }
        }
        
        if (patch != null) {
          updateResults.add(new Patch(fileRevision.getSessionId(), fileRevision.getRevision(), fileRevision.getChecksum(), patch, properties, extensions));
        } else {
          updateResults.add(new Patch(fileRevision.getSessionId(), fileRevision.getRevision(), null, null, properties, extensions));
        }
      }    
    }

    return updateResults;
  }

  public void filePatch(String fileId, String sessionId, Long revisionNumber, String patch, Map<String, String> properties, Map<String, Object> extensions) throws CoOpsInternalErrorException, CoOpsUsageException, CoOpsNotFoundException, CoOpsConflictException, CoOpsForbiddenException {
    CoOpsSession session = coOpsSessionDAO.findBySessionId(sessionId);
    if (session == null) {
      throw new CoOpsUsageException("Invalid session id"); 
    }
    
    CoOpsDiffAlgorithm algorithm = findAlgorithm(session.getAlgorithm());
    if (algorithm == null) {
      throw new CoOpsUsageException("Algorithm is not supported by this server");
    }
 
    fi.otavanopisto.coops.quickstart.model.File file = findFile(fileId);
    
    Long currentRevision = file.getRevisionNumber();
    if (!currentRevision.equals(revisionNumber)) {
      throw new CoOpsConflictException();
    }
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    String checksum = null;
    String patched = null;
    
    if (StringUtils.isNotBlank(patch)) {
      String data = file.getData();
      if (data == null) {
        data = "";
      }
      
      patched = algorithm.patch(data, patch);
      checksum = DigestUtils.md5Hex(patched);
      fileDAO.updateData(file, patched);
    } 
    
    Long patchRevisionNumber = currentRevision + 1;
    FileRevision fileRevision = fileRevisionDAO.create(file, sessionId, patchRevisionNumber, new Date(), patch, checksum);
    fileDAO.updateRevisionNumber(file, patchRevisionNumber);

    if (properties != null) {
      for (String key : properties.keySet()) {
        String value = properties.get(key);
        
        FileProperty fileProperty = filePropertyDAO.findByFileAndKey(file, key);
        if (fileProperty == null) {
          filePropertyDAO.create(file, key, value);
        } else {
          filePropertyDAO.updateValue(fileProperty, value);
        }
        
        fileRevisionPropertyDAO.create(fileRevision, key, value);
      }
    }
    
    if (extensions != null) {
      for (String key : extensions.keySet()) {
        String value;
        
        try {
          value = objectMapper.writeValueAsString(extensions.get(key));
        } catch (IOException e) {
          throw new CoOpsInternalErrorException(e);
        }
        
        FileExtensionProperty fileExtensionProperty = fileExtensionPropertyDAO.findByFileAndKey(file, key);
        if (fileExtensionProperty == null) {
          fileExtensionPropertyDAO.create(file, key, value);
        } else {
          fileExtensionPropertyDAO.updateValue(fileExtensionProperty, value);
        }
        
        fileRevisionExtensionPropertyDAO.create(fileRevision, key, value);
      }
    }
  }

  public Join fileJoin(String fileId, List<String> algorithms, String protocolVersion) throws CoOpsNotFoundException, CoOpsNotImplementedException, CoOpsInternalErrorException, CoOpsForbiddenException, CoOpsUsageException {
    fi.otavanopisto.coops.quickstart.model.File file = findFile(fileId);
    
    if (!COOPS_PROTOCOL_VERSION.equals(protocolVersion)) {
      throw new CoOpsNotImplementedException("Protocol version mismatch. Client is using " + protocolVersion + " and server " + COOPS_PROTOCOL_VERSION);
    }
    
    if (algorithms == null || algorithms.isEmpty()) {
      throw new CoOpsInternalErrorException("Invalid request");
    }
    
    CoOpsDiffAlgorithm algorithm = findAlgorithm(algorithms);
    if (algorithm == null) {
      throw new CoOpsNotImplementedException("Server and client do not have a commonly supported algorithm.");
    }

    Long currentRevision = file.getRevisionNumber();
    String data = file.getData();
    if (data == null) {
      data = "";
    }

    List<CoOpsSession> openSessions = coOpsSessionDAO.listByFileAndClosed(file, Boolean.FALSE);
    Map<String, String> properties = new HashMap<>();
    
    List<FileProperty> fileProperties = filePropertyDAO.listByFile(file);
    for (FileProperty fileProperty : fileProperties) {
      properties.put(fileProperty.getKey(), fileProperty.getValue());
    }
    
    Map<String, Object> extensions = new HashMap<>();
    String sessionId = UUID.randomUUID().toString();
    
    CoOpsSession coOpsSession = coOpsSessionDAO.create(file, sessionId, CoOpsSessionType.REST, currentRevision, algorithm.getName(), Boolean.FALSE, new Date());
    
//    extensions.put("sessionEvents", coOpsSessionEventsController.createSessionEvents(openSessions, "OPEN"));
//    
//    String wsUrl = String.format("ws://%s:%s%s/ws/coops/document/%d/%s", 
//        httpRequest.getServerName(), 
//        httpRequest.getServerPort(), 
//        httpRequest.getContextPath(), 
//        document.getId(), 
//        coOpsSession.getSessionId());
//    
//    Map<String, Object> webSocketExtension = new HashMap<>();
//    webSocketExtension.put("ws", wsUrl);
//    extensions.put("webSocket", webSocketExtension);
//    
//    sessionOpenEvent.fire(new CoOpsSessionOpenEvent(coOpsSession.getSessionId()));
//    
    return new Join(coOpsSession.getSessionId(), coOpsSession.getAlgorithm(), coOpsSession.getJoinRevision(), data, file.getContentType(), properties, extensions);
  }
  
  private CoOpsDiffAlgorithm findAlgorithm(String algorithmName) {
    return findAlgorithm(Arrays.asList(algorithmName)); 
  }
  
  private CoOpsDiffAlgorithm findAlgorithm(List<String> algorithmNames) {
    Map<String, CoOpsDiffAlgorithm> algorithmMap = getAlgorithmMap();
    for (String algorithmName : algorithmNames) {
      if (algorithmMap.containsKey(algorithmName)) {
        return algorithmMap.get(algorithmName);
      }
    }
    
    return null;
  }
  
  private Map<String, CoOpsDiffAlgorithm> getAlgorithmMap() {
    Map<String, CoOpsDiffAlgorithm> result = new HashMap<>();
    
    Iterator<CoOpsDiffAlgorithm> iterator = this.algorithms.iterator();
    while (iterator.hasNext()) {
      CoOpsDiffAlgorithm algorithm = iterator.next();
      result.put(algorithm.getName(), algorithm);
    }
    
    return result;
  }

  private fi.otavanopisto.coops.quickstart.model.File findFile(String fileId) throws CoOpsUsageException, CoOpsNotFoundException {
    if (!StringUtils.isNumeric(fileId)) {
      throw new CoOpsUsageException("fileId must be a number");
    }
    
    Long id = NumberUtils.createLong(fileId);
    if (id == null) {
      throw new CoOpsUsageException("fileId must be a number");
    }
    
    fi.otavanopisto.coops.quickstart.model.File file = fileDAO.findById(id);
    if (file == null) {
      throw new CoOpsNotFoundException();
    }
    
    return file;
  }
}
