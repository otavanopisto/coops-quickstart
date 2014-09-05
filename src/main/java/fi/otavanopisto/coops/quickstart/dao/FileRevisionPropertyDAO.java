package fi.otavanopisto.coops.quickstart.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import fi.otavanopisto.coops.quickstart.model.FileRevisionProperty;
import fi.otavanopisto.coops.quickstart.model.FileRevisionProperty_;
import fi.otavanopisto.coops.quickstart.model.FileRevision;

public class FileRevisionPropertyDAO extends GenericDAO<FileRevisionProperty> {

  private static final long serialVersionUID = -8715223954604734705L;

  public FileRevisionProperty create(FileRevision fileRevision, String key, String value) {
    FileRevisionProperty fileRevisionProperty = new FileRevisionProperty();
    
    fileRevisionProperty.setFileRevision(fileRevision);
    fileRevisionProperty.setValue(value);
    fileRevisionProperty.setKey(key);
    
    return persist(fileRevisionProperty);
  }
  
  public List<FileRevisionProperty> listByFileRevision(FileRevision fileRevision) {
    EntityManager entityManager = getEntityManager();

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<FileRevisionProperty> criteria = criteriaBuilder.createQuery(FileRevisionProperty.class);
    Root<FileRevisionProperty> root = criteria.from(FileRevisionProperty.class);
    criteria.select(root);
    criteria.where(
      criteriaBuilder.equal(root.get(FileRevisionProperty_.fileRevision), fileRevision)
    );
    
    return entityManager.createQuery(criteria).getResultList();
  }

}
