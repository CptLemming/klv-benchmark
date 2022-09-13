package javaSingle.klv.common.enums;

import java.util.Arrays;

public enum MeterScale {
  PPM_8_20,
  PPM_9_15,
  PPM_10_18,
  VU_8_20,
  VU_12_20,
  VU_20,
  PHASE,
  DYN,
  TYPE_I_NORDIC,
  TYPE_IIA_BBC,
  TYPE_IIB_EBU,
  DIN,
  VU,
  DO_NOT_CARE,
  LOUDNESS,
  PPM_12_18,
  DYNAMICS,
  AUTOMIXER,
  GAIN,
  REDUCTION,
  DE_ESSER,
  LOUDNESS_EBU_9_REL,
  LOUDNESS_EBU_18_REL,
  LOUDNESS_EBU_9_ABS,
  LOUDNESS_EBU_18_ABS,
  LOUDNESS_ATSC_9_REL_2011,
  LOUDNESS_ATSC_18_REL_2011,
  LOUDNESS_ATSC_9_ABS_2011,
  LOUDNESS_ATSC_18_ABS_2011,
  LOUDNESS_ATSC_9_REL_2013,
  LOUDNESS_ATSC_18_REL_2013,
  LOUDNESS_ATSC_9_ABS_2013,
  LOUDNESS_ATSC_18_ABS_2013,
  LOUDNESS_ARIB_9_REL,
  LOUDNESS_ARIB_18_REL,
  LOUDNESS_ARIB_9_ABS,
  LOUDNESS_ARIB_18_ABS,
  LOUDNESS_DPP_LIVE_9_REL,
  LOUDNESS_DPP_LIVE_18_REL,
  LOUDNESS_DPP_LIVE_9_ABS,
  LOUDNESS_DPP_LIVE_18_ABS,
  LOUDNESS_DPP_NON_LIVE_9_REL,
  LOUDNESS_DPP_NON_LIVE_18_REL,
  LOUDNESS_DPP_NON_LIVE_9_ABS,
  LOUDNESS_DPP_NON_LIVE_18_ABS;

  public static MeterScale find(MeterStyle style) {
    return Arrays
      .stream(MeterScale.values())
      .filter(scale -> scale.name().equals(style.name()))
      .findFirst()
      .orElse(MeterScale.DO_NOT_CARE);
  }

  public static MeterScale find(LoudnessModeScaleEnum loudnessMode) {
    return Arrays
      .stream(MeterScale.values())
      .filter(scale -> scale.name().equals(loudnessMode.name()))
      .findFirst()
      .orElse(MeterScale.DO_NOT_CARE);
  }
}

