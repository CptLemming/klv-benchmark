package javaSingle.klv.common.enums;

import java.util.Arrays;
import java.util.List;

import javaSingle.klv.common.MeterOptions;

public enum MeterSource {
  MAIN(
    "Main",
    "Main",
    "",
    "mainMeterUpdate",
    SourceInfoType.MAIN,
    2,
    1,
    3,
    null,
    Assignment.FUNC_MAIN_LINE,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.FULL_IMMERSIVE,
    MeterSourceOption.AUDIO,
    MeterSourceOption.DYNAMICS,
    MeterSourceOption.LOUDNESS,
    MeterSourceOption.PRE_TONE_TB,
    MeterSourceOption.PRE_DELAY,
    MeterSourceOption.DOWNMIX,
    MeterSourceOption.PHASE,
    MeterSourceOption.SU_DOWNMIX,
    MeterSourceOption.DOWNMIX_LOUDNESS,
    MeterSourceOption.SU_DOWNMIX_LOUDNESS
  ),
  GROUP(
    "Group",
    "Group",
    "",
    "groupMeterUpdate",
    SourceInfoType.GROUP,
    2,
    1,
    8,
    null,
    Assignment.GROUP_OP,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.FULL_IMMERSIVE,
    MeterSourceOption.AUDIO,
    MeterSourceOption.DYNAMICS,
    MeterSourceOption.LOUDNESS,
    MeterSourceOption.PHASE
  ),
  AUX(
    "Aux",
    "Aux",
    "",
    "auxMeterUpdate",
    SourceInfoType.AUX,
    1,
    1,
    16,
    null,
    Assignment.FUNC_AUX_OP,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.AUDIO,
    MeterSourceOption.DYNAMICS,
    MeterSourceOption.LOUDNESS,
    MeterSourceOption.PRE_DELAY,
    MeterSourceOption.PHASE
  ),
  TRACK(
    "Track",
    "Track",
    "",
    "",
    SourceInfoType.TRACK,
    1,
    1,
    32,
    null,
    Assignment.FUNC_TRACK_OP,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.AUDIO,
    MeterSourceOption.PRE_DELAY,
    MeterSourceOption.DUAL_TRACK,
    MeterSourceOption.QUAD_TRACK,
    MeterSourceOption.TRACK_PAIR_LOUDNESS,
    MeterSourceOption.TRACK_ODD_LOUDNESS,
    MeterSourceOption.TRACK_EVEN_LOUDNESS
  ),
  EXTERNAL_INPUT(
    "Ext In",
    "Ext In",
    "",
    "externalMonitorMeterUpdate",
    SourceInfoType.EXTERNAL_INPUT,
    2,
    1,
    48,
    null,
    Assignment.EXTERNAL,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.AUDIO,
    MeterSourceOption.LOUDNESS
  ),
  MIX_MINUS(
    "Mix Minus",
    "Mix Min",
    "",
    "mixMinusMeterUpdate",
    SourceInfoType.MIX_MINUS,
    1,
    1,
    12,
    null,
    Assignment.MIX_MINUS,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.AUDIO,
    MeterSourceOption.LOUDNESS
  ),
  AFL_STEREO(
    "AFL",
    "AFL",
    "Stereo",
    "aFLMeterUpdate",
    SourceInfoType.AFL,
    2,
    0,
    0,
    null,
    512,
    Assignment.AFL,
    AudioFormat.STEREO,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.AUDIO,
    MeterSourceOption.LOUDNESS,
    MeterSourceOption.PHASE
  ),
  AFL_SURROUND(
    AFL_STEREO.getFullTitle(),
    AFL_STEREO.getShortLabel(),
    "5.1",
    AFL_STEREO.getSnapshot(),
    SourceInfoType.AFL,
    AFL_STEREO.getMinWidth(),
    AFL_STEREO.getMinIndex(),
    AFL_STEREO.getMaxIndex(),
    null,
    0,
    AFL_STEREO.getMeterAssignment(),
    AudioFormat.SURROUND_5_1,
    AFL_STEREO.getMeterOptions()
  ),
  PFL_STEREO(
    "PFL",
    "PFL",
    "Stereo",
    "pFLMeterUpdate",
    SourceInfoType.PFL,
    2,
    0,
    0,
    null,
    512,
    Assignment.PFL,
    AudioFormat.STEREO,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.AUDIO,
    MeterSourceOption.LOUDNESS,
    MeterSourceOption.PHASE
  ),
  PFL_SURROUND(
    PFL_STEREO.getFullTitle(),
    PFL_STEREO.getShortLabel(),
    "5.1",
    PFL_STEREO.getSnapshot(),
    SourceInfoType.PFL,
    PFL_STEREO.getMinWidth(),
    PFL_STEREO.getMinIndex(),
    PFL_STEREO.getMaxIndex(),
    PFL_STEREO.getIndex(),
    0,
    PFL_STEREO.getMeterAssignment(),
    AudioFormat.SURROUND_5_1,
    PFL_STEREO.getMeterOptions()
  ),
  APFL_STEREO(
    "APFL",
    "APFL",
    "Stereo",
    "aPFLMeterUpdate",
    SourceInfoType.APFL,
    2,
    0,
    0,
    null,
    512,
    Assignment.APFL,
    AudioFormat.STEREO,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.AUDIO,
    MeterSourceOption.LOUDNESS,
    MeterSourceOption.PHASE
  ),
  APFL_SURROUND(
    APFL_STEREO.getFullTitle(),
    APFL_STEREO.getShortLabel(),
    "5.1",
    APFL_STEREO.getSnapshot(),
    SourceInfoType.APFL,
    APFL_STEREO.getMinWidth(),
    APFL_STEREO.getMinIndex(),
    APFL_STEREO.getMaxIndex(),
    APFL_STEREO.getIndex(),
    0,
    APFL_STEREO.getMeterAssignment(),
    AudioFormat.SURROUND_5_1,
    APFL_STEREO.getMeterOptions()
  ),
  CONSOLE_LS(
    "Console Monitor",
    "Console Monitor",
    "",
    "consoleMonitorMeterUpdate",
    SourceInfoType.MONITOR,
    2,
    0,
    0,
    null,
    null,
    Assignment.CRLS_PRE,
    AudioFormat.SURROUND_5_1,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.AUDIO,
    MeterSourceOption.LOUDNESS
  ),
  OAC(
    "Off Air Conference",
    "Off Air",
    "Conf",
    "oACMeterUpdate",
    SourceInfoType.OFF_AIR_CONFERENCE,
    1,
    0,
    0,
    null,
    Assignment.OAC_OP,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.AUDIO,
    MeterSourceOption.LOUDNESS
  ),
  USER_METER_1(
    "User Meter",
    "User Meter",
    "1",
    "userMeterMeterUpdate",
    SourceInfoType.USER_METER,
    2,
    1,
    2,
    null,
    0,
    Assignment.SEL_METER,
    AudioFormat.SURROUND_5_1,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.AUDIO,
    MeterSourceOption.LOUDNESS
  ),
  USER_METER_2(
    "User Meter",
    "User Meter",
    "2",
    "userMeterMeterUpdate",
    SourceInfoType.USER_METER,
    2,
    1,
    2,
    null,
    512,
    Assignment.SEL_METER,
    AudioFormat.STEREO,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.AUDIO,
    MeterSourceOption.LOUDNESS
  ),
  FADER(
    "Fader",
    "Fader",
    "",
    "faderMeterUpdate",
    SourceInfoType.CHANNEL,
    2,
    1,
    12,
    null,
    Assignment.FADER_IP_LVL_N_DYN,
    MeterSourceOption.FULL_HEIGHT,
    MeterSourceOption.IMMERSIVE_DYNAMICS
  ),
  VIDEO("Video", "Video", "", null, SourceInfoType.VIDEO, 12, 1, 2, null, Assignment.VIDEO_OVERLAY);

  private String fullTitle;
  private final String shortLabel;
  private final String labelSuffix;
  private final String snapshot;
  private final SourceInfoType sourceType;
  private final int minWidth;
  private final int minIndex;
  private final int maxIndex;
  private Integer index;
  private final Integer klvIndex;
  private final Assignment meterAssignment;
  private final AudioFormat audioFormat;
  private final MeterSourceOption[] meterOptions;

  MeterSource(
    final String fullTitle,
    final String shortLabel,
    final String labelSuffix,
    final String snapshot,
    final SourceInfoType sourceType,
    final int minWidth,
    final int minIndex,
    final int maxIndex,
    final Integer index,
    final Integer klvIndex,
    final Assignment meterAssignment,
    final AudioFormat audioFormat,
    // prettier-ignore
    final MeterSourceOption... meterOptions
  ) {
    this.fullTitle = fullTitle;
    this.shortLabel = shortLabel;
    this.labelSuffix = labelSuffix;
    this.snapshot = snapshot;
    this.sourceType = sourceType;
    this.minWidth = minWidth;
    this.minIndex = minIndex;
    this.maxIndex = maxIndex;
    this.index = index;
    this.klvIndex = klvIndex;
    this.meterAssignment = meterAssignment;
    this.audioFormat = audioFormat;
    this.meterOptions = meterOptions;
  }

  MeterSource(
    final String fullTitle,
    final String shortLabel,
    final String labelSuffix,
    final String snapshot,
    final SourceInfoType sourceType,
    final int minWidth,
    final int minIndex,
    final int maxIndex,
    final Integer index,
    final Assignment meterAssignment,
    // prettier-ignore
    final MeterSourceOption... meterOptions
  ) {
    this(
      fullTitle,
      shortLabel,
      labelSuffix,
      snapshot,
      sourceType,
      minWidth,
      minIndex,
      maxIndex,
      index,
      null,
      meterAssignment,
      AudioFormat.NO_FORMAT,
      meterOptions
    );
  }

  public String getFullTitle() {
    return fullTitle;
  }

  public String getShortLabel() {
    return shortLabel;
  }

  public String getLabelSuffix() {
    return labelSuffix;
  }

  public String getSnapshot() {
    return snapshot;
  }

  public SourceInfoType getSourceType() {
    return sourceType;
  }

  /**
   * Softpanels register for a variety of INDEXED_FEATURE_KEYED_TYPE ADV messages, each relating to a specific meter
   * type.
   *
   * The KLV Index for these ADV messages is usually the Sel meter number for Sel meters (only ever 0 for hector,
   * but 0, 1, 2 or 3 for Apollo+) or the APFL system number for AFL, PFL and APFL meters (only ever 0 for Hector
   * but 0, 1, or 2 for Apollo+).
   *
   * However, Sel or APFL system number is not enough to distinguish between a normal, native format meter and the
   * same meter but downmixed.
   *
   * Currently only Stereo downmixes are specified but future projects and developments could specify mono or, in the
   * case of immersive audio, 5.1 downmixes.
   *
   * https://allenheath.sharepoint.com/:w:/g/projects/ivor/Eey3EJFl-xdFubZgZx3otH4BBvVxSDG3lYC6Nv4fLvVx9g?e=Uizvab
   *
   * @param defaultIndex - A default index to fall back to if the source type has no specific snapshot index.
   * @return - An index to snapshot for a given source
   */
  public Integer getSnapshotIndex(final int defaultIndex) {
    switch (this) {
      case AFL_STEREO:
      case AFL_SURROUND:
      case PFL_STEREO:
      case PFL_SURROUND:
      case APFL_STEREO:
      case APFL_SURROUND:
      case USER_METER_1:
      case USER_METER_2:
        return this.getKlvIndex();
      default:
        return defaultIndex;
    }
  }

  public int getMinWidth() {
    return minWidth;
  }

  public int getMinIndex() {
    return minIndex;
  }

  public int getMaxIndex() {
    return maxIndex;
  }

  public Integer getIndex() {
    return index;
  }

  public boolean hasIndex() {
    return index != null;
  }

  public Assignment getMeterAssignment() {
    return meterAssignment;
  }

  public MeterSourceOption[] getMeterOptions() {
    return Arrays
      .stream(meterOptions)
      .toArray(MeterSourceOption[]::new);
  }

  public static List<MeterSource> getOtherSources() {
    // if (Product.HECTOR == Product.valueOf(System.getProperty("PRODUCT"))) {
    //   return Arrays.asList(CONSOLE_LS, PFL_SURROUND, AFL_SURROUND, APFL_SURROUND, PFL_STEREO, AFL_STEREO, APFL_STEREO);
    // }
    return Arrays.asList(CONSOLE_LS, PFL_STEREO, AFL_STEREO, APFL_STEREO);
  }

  public static List<MeterSource> getVideoSources() {
    return Arrays.asList(VIDEO);
  }

  // apollo or ivor
  public int getOtherSourcesSize() {
    switch (this) {
      default:
      case CONSOLE_LS:
        return 2;
      case PFL_STEREO:
      case AFL_STEREO:
      case APFL_STEREO:
      case PFL_SURROUND:
      case AFL_SURROUND:
      case APFL_SURROUND:
        return 3;
      case USER_METER_1:
      case USER_METER_2:
        return 4;
    }
  }

  public Format getFormatFromAudioFormat() {
    switch (getAudioFormat(null)) {
      case MONO:
        return Format.M;
      case STEREO:
        return Format.ST;
      case SURROUND_5_1:
        return Format.SU_5_1;
      default:
        return Format.NP;
    }
  }

  public static List<MeterSource> getUserMeters() {
    return Arrays.asList(USER_METER_1, USER_METER_2);
  }

  public boolean isOtherSource() {
    return getOtherSources().contains(this);
  }

  public boolean isUserSource() {
    return getUserMeters().contains(this);
  }

  public boolean isOACSource() {
    return this == MeterSource.OAC;
  }

  public boolean isMixMinusSource() {
    return this == MeterSource.MIX_MINUS;
  }

  public AudioFormat getAudioFormat(MeterOptions options) {
    if (options != null) {
      // These values are pulled from Apollo Console PC
      if (options.isDualTrack) return AudioFormat.PAIR_N_M;
      if (options.isQuadTrack) return AudioFormat.PAIR_N_E;
      if (options.isTrackPairLoudness) return AudioFormat.PAIR_N_M;
      if (options.isTrackOddLoudness) return AudioFormat.SURROUND_3_1;
      if (options.isTrackEvenLoudness) return AudioFormat.SURROUND_4_1;
    }

    return audioFormat;
  }

  public int getAudioWidth(MeterOptions options) {
    if (options != null) {
      if (options.isQuadTrack) return 4;
      if (options.isDualTrack || options.isTrackPairLoudness) return 2;
      if (options.isTrackOddLoudness || options.isTrackEvenLoudness) return 1;
    }
    return 0;
  }

  public Integer getKlvIndex() {
    return klvIndex;
  }

  public MeterSource getMixMinusMeterSourceByIndex(int index) {
    if (isMixMinusSource()) {
      this.fullTitle = "Mix Minus" + " " + index;
      this.index = index;
    }
    return this;
  }
}
