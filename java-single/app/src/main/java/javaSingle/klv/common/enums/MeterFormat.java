package javaSingle.klv.common.enums;

public enum MeterFormat {
  SURROUND_STEREO_OR_MONO("Surround, stereo/mono"),
  STEREO_LO_RO("Stereo LoRo"),
  MS("M/S (sum, difference)"),
  STEREO_PHASE("Stereo phase"),
  MONO("Mono"),
  STEREO("Stereo"),
  SURROUND("Surround"),
  MF_UNDEFINED("undefined"),
  DOWNMIX_5_1("5.1 downmix");

  private final String legend;

  MeterFormat(final String legend) {
    this.legend = legend;
  }

  public String toString() {
    return legend;
  }

  public String getLegend() {
    return legend;
  }
}
