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
  
  

}
