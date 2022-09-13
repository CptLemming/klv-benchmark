package javaSingle.klv.common.enums;

public enum AudioFormat {
  NO_FORMAT(0),
  MONO(1),
  STEREO(2),
  SURROUND_3_0(3),
  SURROUND_4_0(4),
  SURROUND_5_0(5),
  SURROUND_5_1(8),
  MONO_ODD(11),
  MONO_EVEN(12),
  SURROUND_1_1(13),
  SURROUND_2_1(14),
  SURROUND_3_1(15),
  SURROUND_4_1(16),
  S(19),
  LORO_STEREO(20),
  DUAL_MONO(21),
  QUAD_MONO(22),
  LTRT_STEREO(23),
  MONO_MO(24),
  MONO_MT(25),
  PAIR_N_N(26),
  PAIR_N_M(27),
  PAIR_N_E(28),
  PAIR_M_N(29),
  PAIR_M_M(30),
  PAIR_M_E(31),
  PAIR_O_N(32),
  PAIR_O_M(33),
  PAIR_O_E(34),
  REMOTE_PLACEHOLDER(35),
  UNUSED_36(36),
  UNUSED_37(37),
  UNUSED_38(38),
  UNUSED_39(39),
  IMMERSIVE_5_1_2(40),
  IMMERSIVE_5_1_4(41),
  IMMERSIVE_7_1_2(42),
  IMMERSIVE_7_1_4(43),
  IMMERSIVE_POINT_TWO(44),
  IMMERSIVE_POINT_FOUR(45),
  FORMAT_COUNT(45),
  MAX_ALLOWABLE_AUDIO_WIDTH(8),
  MSB_GREYED_OUT(255);

  private final int audioFormat;

  AudioFormat(final int audioFormat) {
    this.audioFormat = audioFormat;
  }
}
