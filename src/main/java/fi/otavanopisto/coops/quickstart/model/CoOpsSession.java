package fi.otavanopisto.coops.quickstart.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class CoOpsSession {

  public Long getId() {
    return id;
  }
  
  public String getSessionId() {
    return sessionId;
  }
  
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
  
  public CoOpsSessionType getType() {
    return type;
  }
  
  public void setType(CoOpsSessionType type) {
    this.type = type;
  }
  
  public Boolean getClosed() {
    return closed;
  }
  
  public void setClosed(Boolean closed) {
    this.closed = closed;
  }
  
  public File getFile() {
    return file;
  }
  
  public void setFile(File file) {
    this.file = file;
  }
  
  public String getAlgorithm() {
    return algorithm;
  }
  
  public void setAlgorithm(String algorithm) {
    this.algorithm = algorithm;
  }
  
  public Long getJoinRevision() {
    return joinRevision;
  }
  
  public void setJoinRevision(Long joinRevision) {
    this.joinRevision = joinRevision;
  }
  
  public Date getAccessed() {
    return accessed;
  }
  
  public void setAccessed(Date accessed) {
    this.accessed = accessed;
  }

  @Id
  @GeneratedValue (strategy=GenerationType.IDENTITY)
  private Long id;
  
  @Column (nullable = false, unique = true)
  @NotEmpty
  @NotNull
  private String sessionId;
  
  @Column (nullable = false)
  @NotNull
  @Enumerated (EnumType.STRING)
  private CoOpsSessionType type;
  
  @Column (nullable = false)
  @NotNull
  private Boolean closed;

  @ManyToOne
  private File file;
  
  @Column (updatable = false, nullable = false)
  @NotNull
  @NotEmpty
  private String algorithm;
  
  @Column (updatable = false, nullable = false)
  @NotNull
  private Long joinRevision;
  
  @Column (nullable = false)
  @NotNull
  private Date accessed;
}