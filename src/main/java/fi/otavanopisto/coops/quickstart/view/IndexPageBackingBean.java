package fi.otavanopisto.coops.quickstart.view;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import fi.otavanopisto.coops.quickstart.dao.FileDAO;
import fi.otavanopisto.coops.quickstart.dao.FilePropertyDAO;
import fi.otavanopisto.coops.quickstart.model.File;
import fi.otavanopisto.coops.quickstart.model.FileProperty;

@Named
@RequestScoped
public class IndexPageBackingBean {

  @Inject
  private FileDAO fileDAO;

  @Inject
  private FilePropertyDAO filePropertyDAO;

  public List<FileBean> getOpenFiles() {
    List<FileBean> result = new ArrayList<>();
    
    for (File file : fileDAO.listAll()) {
      FileProperty fileProperty = filePropertyDAO.findByFileAndKey(file, "title");
      String title = fileProperty == null ? "Untitled" : fileProperty.getValue();
      result.add(new FileBean(file.getId(), title));
    }

    return result;
  }

  public class FileBean {

    public FileBean(Long id, String title) {
      super();
      this.id = id;
      this.title = title;
    }
    
    public Long getId() {
      return id;
    }

    public String getTitle() {
      return title;
    }

    private Long id;
    private String title;

  }
}
