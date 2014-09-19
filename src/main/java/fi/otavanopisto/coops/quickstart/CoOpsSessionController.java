package fi.otavanopisto.coops.quickstart;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import fi.otavanopisto.coops.quickstart.dao.CoOpsSessionDAO;
import fi.otavanopisto.coops.quickstart.events.CoOpsSessionCloseEvent;
import fi.otavanopisto.coops.quickstart.events.CoOpsSessionOpenEvent;
import fi.otavanopisto.coops.quickstart.model.CoOpsSession;
import fi.otavanopisto.coops.quickstart.model.CoOpsSessionType;
import fi.otavanopisto.coops.quickstart.model.File;

@Dependent
@Stateless
public class CoOpsSessionController {
  
  private static final long SESSION_TIMEOUT = 10 * 1000;

  @Inject
  private Event<CoOpsSessionOpenEvent> sessionOpenEvent;
  
  @Inject
  private Event<CoOpsSessionCloseEvent> sessionCloseEvent;
  
  @Inject
  private CoOpsSessionDAO coOpsSessionDAO;
  
  public CoOpsSession createSession(File file, String sessionId, Long joinRevision, String algorithm) {
    CoOpsSession session = coOpsSessionDAO.create(file, sessionId, CoOpsSessionType.REST, joinRevision, algorithm, Boolean.FALSE, new Date());
    sessionOpenEvent.fire(new CoOpsSessionOpenEvent(session.getSessionId()));
    return session;
  }

  public CoOpsSession findSessionBySessionId(String sessionId) {
    return coOpsSessionDAO.findBySessionId(sessionId);
  }
  
  public List<CoOpsSession> listTimedoutRestSessions() {
    Date accessedBefore = new Date(System.currentTimeMillis() - SESSION_TIMEOUT);
    return coOpsSessionDAO.listByAccessedBeforeAndTypeAndClosed(accessedBefore, CoOpsSessionType.REST, Boolean.FALSE);
  }

  public List<CoOpsSession> listSessionsByFileAndClosed(File file, Boolean closed) {
    return coOpsSessionDAO.listByFileAndClosed(file, closed);
  }
  
  public CoOpsSession updateSessionType(CoOpsSession coOpsSession, CoOpsSessionType type) {
    return coOpsSessionDAO.updateType(coOpsSession, type);
  }

  public void closeSession(CoOpsSession session) {
    closeSession(session, false);
  }
  
  public void closeSession(CoOpsSession session, boolean quiet) {
    coOpsSessionDAO.updateClosed(session, Boolean.TRUE);
    if (!quiet) {
      sessionCloseEvent.fire(new CoOpsSessionCloseEvent(session.getSessionId()));
    }
  }
}
