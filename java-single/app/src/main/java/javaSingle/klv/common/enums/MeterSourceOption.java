package javaSingle.klv.common.enums;

public enum MeterSourceOption {
  AUDIO,
  FULL_HEIGHT,
  FULL_IMMERSIVE(1),
  DYNAMICS(1),
  IMMERSIVE_DYNAMICS(1),
  DOWNMIX(1),
  LOUDNESS(2),
  PRE_TONE_TB,
  PRE_DELAY,
  PHASE(1),
  SU_DOWNMIX(2),
  DUAL_TRACK(2),
  QUAD_TRACK(2),
  TRACK_PAIR_LOUDNESS(2),
  TRACK_ODD_LOUDNESS(2),
  TRACK_EVEN_LOUDNESS(2),
  DOWNMIX_LOUDNESS(2),
  SU_DOWNMIX_LOUDNESS(2);

  Integer width;

  MeterSourceOption() {
    this.width = null;
  }

  MeterSourceOption(Integer width) {
    this.width = width;
  }

  public Integer getWidth() {
    return width;
  }
}
