package javaSingle.klv.common.enums;

import java.util.Arrays;

public enum MeterStyle {
  PPM_8_20(0, "Calrec PPM 8/20"),
  PPM_9_15(1, "Calrec PPM 9/15"),
  PPM_10_18(2, "Calrec PPM 10/18"),
  VU_8_20(3, "Calrec PPM 12/18"),
  VU_12_20(4, "Calrec VU 12/20"),
  VU_20(5, "Calrec VU 20"),
  PHASE(6, ""),
  DYN(7, ""),
  TYPE_I_NORDIC(8, "Calrec Nordic PPM"),
  TYPE_IIA_BBC(9, ""),
  TYPE_IIB_EBU(10, ""),
  DIN(11, ""),
  VU(12, ""),
  DO_NOT_CARE(13, ""),
  LOUDNESS(14, ""),
  PPM_12_18(15, "Calrec PPM 12/18"),
  VIDEO_INPUT_1(16, ""),
  VIDEO_INPUT_2(16, "");

  private final int index;
  private final String name;

  MeterStyle(final int index, final String name) {
    this.index = index;
    this.name = name;
  }

  public int getIndex() {
    return index;
  }

  public String getName() {
    return name;
  }

  public static MeterStyle findOrNull(final int index) {
    return Arrays.stream(values()).filter(v -> v.ordinal() == index).findFirst().orElse(null);
  }

  public static MeterStyle findOrNull(final String name) {
    if (name == null) return null;
    return Arrays
      .stream(values())
      .filter(value -> value.toString().toUpperCase().equals(name.toUpperCase()))
      .findFirst()
      .orElse(null);
  }
}
