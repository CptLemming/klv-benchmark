package javaSingle.klv.common.enums;

public enum PanType {
  PATH_PANNER(0),
  TRACK_PANNER(1),
  AUX_PANNER(2);

  private Integer startIndex;

  PanType(Integer startIndex) {
    this.startIndex = startIndex;
  }

  public Integer getPanIndex() {
    return startIndex;
  }
}
