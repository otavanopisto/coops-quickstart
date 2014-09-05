package fi.otavanopisto.coops.quickstart;

import fi.foyt.coops.CoOpsConflictException;

public interface CoOpsDiffAlgorithm {

  public String getName();

  public String patch(String data, String patch) throws CoOpsConflictException;
  
}
