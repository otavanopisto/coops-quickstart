package fi.otavanopisto.coops.quickstart.dao;

import fi.otavanopisto.coops.quickstart.model.File;

public class FileDAO extends GenericDAO<File> {

  private static final long serialVersionUID = -8715223954604734705L;

  public File create(Long revisionNumber, String contentType, String data) {
    File file = new File();
    
    file.setContentType(contentType);
    file.setData(data);
    file.setRevisionNumber(revisionNumber);
    
    return persist(file);    
  }

  public File updateData(fi.otavanopisto.coops.quickstart.model.File file, String data) {
    file.setData(data);
    return persist(file);    
  }

  public File updateRevisionNumber(fi.otavanopisto.coops.quickstart.model.File file, Long revisionNumber) {
    file.setRevisionNumber(revisionNumber);
    return persist(file);    
  }

}
