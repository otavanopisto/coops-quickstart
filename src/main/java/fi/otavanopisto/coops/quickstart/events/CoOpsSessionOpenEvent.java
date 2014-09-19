package fi.otavanopisto.coops.quickstart.events;

public class CoOpsSessionOpenEvent {

  public CoOpsSessionOpenEvent(String sessionId) {
    super();
    this.sessionId = sessionId;
  }

  public String getSessionId() {
    return sessionId;
  }

  private String sessionId;
}
