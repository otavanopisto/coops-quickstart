package fi.otavanopisto.coops.quickstart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class FileExtensionProperty {

  public Long getId() {
    return id;
  }
  
  public File getFile() {
    return file;
  }
  
  public void setFile(File file) {
    this.file = file;
  }
  
  public String getKey() {
    return key;
  }
  
  public void setKey(String key) {
    this.key = key;
  }
  
  public String getValue() {
    return value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
  
  @Id
  @GeneratedValue (strategy=GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne
  private File file;

  @Column (nullable = false)
  private String key;
  
  @Lob
  @Column (nullable = false)
  private String value;
}