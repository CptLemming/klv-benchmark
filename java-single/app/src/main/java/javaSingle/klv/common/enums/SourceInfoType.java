package javaSingle.klv.common.enums;

public enum SourceInfoType {
  MAIN("main"),
  MN("main"),
  GROUP("group"),
  GP("group"),
  AUX("aux"),
  TRACK("track"),
  TK("track"),
  MIX_MINUS("mixminus"),
  MM("mixminus"),
  EXTERNAL_INPUT("channel"),
  OFF_AIR_CONFERENCE("offairconference"),
  MONITOR("controlRoomMonitor"),
  TALKBACK("talkback"),
  CHANNEL("channel"),
  USER_METER("channel"),
  APFL("autopfl"),
  PFL("pfl"),
  AFL("afl"),
  VIDEO("channel"),
  OTHER("channel"),
  MAIN_LINE("main"),
  MAIN_DESK("main"),
  DIR("directoutput");

  private final String theme;

  SourceInfoType(String theme) {
    this.theme = theme;
  }

  public String getTheme() {
    return this.theme;
  }
}
