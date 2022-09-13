package javaSingle.klv.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.netty.buffer.ByteBuf;
import javaSingle.gql.meters.MeterBlockLabels;
import javaSingle.gql.meters.MeterLabel;
import javaSingle.klv.Helper;
import javaSingle.klv.common.enums.Assignment;
import javaSingle.klv.common.enums.AudioFormat;
import javaSingle.klv.common.enums.DynamicsMode;
import javaSingle.klv.common.enums.FaderInputDisplay;
import javaSingle.klv.common.enums.Format;
import javaSingle.klv.common.enums.LabelType;
import javaSingle.klv.common.enums.LoudnessMode;
import javaSingle.klv.common.enums.LoudnessScale;
import javaSingle.klv.common.enums.MeterFormat;
import javaSingle.klv.common.enums.MeterLabelBackgroundColour;
import javaSingle.klv.common.enums.MeterLayout;
import javaSingle.klv.common.enums.MeterSource;
import javaSingle.klv.common.enums.MeterSourceOption;
import javaSingle.klv.common.enums.MeterStyle;

public class MeterBlockUpdate {
  private static final int[] ROW_ZERO_CONFIGURATION_ROWS = { 0, 4, 8, 12, 16, 20, 24, 28 };
  private static final int[] ROW_ONE_CONFIGURATION_ROWS = { 1, 5, 9, 13, 17, 21, 25, 29 };
  private static final int[] ROW_TWO_CONFIGURATION_ROWS = { 2, 6, 10, 14, 18, 22, 26, 30 };
  private static final int[] ROW_THREE_CONFIGURATION_ROWS = { 3, 7, 11, 15, 19, 23, 27, 31 };
  private static final int NO_OF_COLUMNS_IN_A_BLOCK = 3;
  public static final int MAX_PANELS = 1;
  public static final int MAX_ROWS = 4;
  public static final int MAX_COLUMNS = 24;

  public int row;

  public int column;

  public Assignment assignment;

  public int blockHeight;

  public MeterStyle style;

  public MeterLabelBackgroundColour labelBgColour;

  public Label label1;

  public Label label2;

  public Label label3;

  public List<Integer> timeslots;

  public List<Integer> dynamicTimeslots;

  public List<String> legLabels;

  public Format format;

  public int index;

  public String errorMessage;

  public DynamicsMode dynamicsMode;

  public LoudnessMode loudnessMode;

  public LoudnessScale loudnessScale;

  public int loudnessTimeslot;

  public PathId path;

  public FaderInputDisplay faderDisplay;

  public MeterLayout layout;

  public MeterOptions options;

  public int blockWidth;

  public int combinedBlockWidth;

  public int highlightIndex;

  public String remoteHostName;

  public String remoteFaderLabel;

  public MeterFormat meterFormat;

  public MeterBlockUpdate(ByteBuf buf) {
    row = buf.readByte();
    column = buf.readByte();
    assignment = Assignment.values()[buf.readByte()];
    blockHeight = buf.readByte();
    style = MeterStyle.values()[(buf.readByte())];
    labelBgColour = MeterLabelBackgroundColour.values()[buf.readByte()];
    label1 = new Label(buf);
    label2 = new Label(buf);
    label3 = new Label(buf);

    var numTimeslots = buf.readUnsignedIntLE();
    timeslots = new ArrayList<>();
    for (int i = 0; i < numTimeslots; i++) {
      timeslots.add(buf.readUnsignedShortLE());
    }
    
    var numDynamicTimeslots = buf.readUnsignedIntLE();
    dynamicTimeslots = new ArrayList<>();
    for (int i = 0; i < numDynamicTimeslots; i++) {
      dynamicTimeslots.add(buf.readUnsignedShortLE());
    }
    
    var numLegLabels = buf.readUnsignedIntLE();
    legLabels = new ArrayList<>();
    for (int i = 0; i < numLegLabels; i++) {
      legLabels.add(Helper.readString(buf));
    }

    format = Format.values()[(int)buf.readUnsignedIntLE()];
    index = buf.readUnsignedShortLE();
    errorMessage = Helper.readString(buf);
    dynamicsMode = DynamicsMode.values()[buf.readByte()];
    loudnessMode = LoudnessMode.values()[buf.readByte()];
    loudnessScale = LoudnessScale.values()[buf.readByte()];
    loudnessTimeslot = buf.readUnsignedShortLE();
    path = new PathId(buf);
    faderDisplay = FaderInputDisplay.values()[buf.readByte()];
    layout = MeterLayout.values()[buf.readByte()];
    options = new MeterOptions(buf);
    blockWidth = buf.readByte();
    combinedBlockWidth = buf.readByte();
    highlightIndex = buf.readUnsignedShortLE();
    remoteHostName = Helper.readString(buf);
    remoteFaderLabel = Helper.readString(buf);
    meterFormat = MeterFormat.values()[buf.readByte()];
  }

  public MeterStyle getStyle() {
    return style;
  }

  protected int getMCSRow() {
    return row;
  }

  protected int getMCSColumn() {
    return column;
  }

  public boolean isLoRoDownmixMeter() {
    return !isLoudnessMeter() && meterFormat.equals(MeterFormat.STEREO_LO_RO);
  }

  public boolean isSurroundDownmixMeter() {
    return !isLoudnessMeter() && meterFormat.equals(MeterFormat.DOWNMIX_5_1);
  }

  public void setRow(int row) {
    this.row = row;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public int getBlockHeight() {
    return blockHeight;
  }

  public void setStyle(MeterStyle style) {
    this.style = style;
  }

  public void setMeterFormat(MeterFormat meterFormat) {
    this.meterFormat = meterFormat;
  }

  public String getLabel1() {
    return label1.label;
  }

  public String getLabel2() {
    return label2.label;
  }

  public String getLabel3() {
    return label3.label;
  }

  public int getIndex() {
    return index;
  }

  public String getPreToneTBLabel() {
    return getPreToneTBLabel(getMeterAssignment());
  }
  public int getRow() {
    final int row = getMCSRow();
    if (row % 4 == 0) return 0;
    if ((row - 1) % 4 == 0) return 1;
    if ((row - 2) % 4 == 0) return 2;
    if ((row - 3) % 4 == 0) return 3;
    return -1;
  }
  public int getColumn() {
    final int row = getMCSRow();
    final int column = getMCSColumn();

    int numberOfBlocks = 0;

    switch (getRow()) {
      case 0:
        numberOfBlocks = getNumberOfBlocks(ROW_ZERO_CONFIGURATION_ROWS, row);
        break;
      case 1:
        numberOfBlocks = getNumberOfBlocks(ROW_ONE_CONFIGURATION_ROWS, row);
        break;
      case 2:
        numberOfBlocks = getNumberOfBlocks(ROW_TWO_CONFIGURATION_ROWS, row);
        break;
      case 3:
        numberOfBlocks = getNumberOfBlocks(ROW_THREE_CONFIGURATION_ROWS, row);
        break;
    }

    return ((numberOfBlocks * NO_OF_COLUMNS_IN_A_BLOCK) + column);
  }
  public static int cantorize(final int k1, final int k2) {
    return (k1 + k2) * (k1 + k2 + 1) / 2 + k2;
  }

  public int getBlockId() {
    int row = getRow();
    int column = getColumn();
    if (isLoudnessMeter()) {
      MeterLabel meterLabel = null;
      if (getOptions().isLoudness) meterLabel = getMeterLabel(MeterSourceOption.LOUDNESS);
      if (getOptions().isTrackPairLoudness) meterLabel = getMeterLabel(MeterSourceOption.TRACK_PAIR_LOUDNESS);
      if (getOptions().isTrackOddLoudness) meterLabel = getMeterLabel(MeterSourceOption.TRACK_ODD_LOUDNESS);
      if (getOptions().isTrackEvenLoudness) meterLabel = getMeterLabel(MeterSourceOption.TRACK_EVEN_LOUDNESS);
      if (getOptions().isDownmixLoudness) meterLabel = getMeterLabel(MeterSourceOption.DOWNMIX_LOUDNESS);
      if (getOptions().isDownmixSurroundLoudness) meterLabel = getMeterLabel(MeterSourceOption.SU_DOWNMIX_LOUDNESS);

      if (meterLabel != null) {
        row = row - meterLabel.row;
        column = column - meterLabel.column;
      }
    } else if (isLoRoDownmixMeter() || isSurroundDownmixMeter()) {
      final MeterLabel meterLabel = getMeterLabel(
        isSurroundDownmixMeter() ? MeterSourceOption.SU_DOWNMIX : MeterSourceOption.DOWNMIX
      );
      if (meterLabel != null) {
        row = row - meterLabel.row;
        column = column - meterLabel.column;
      }
    } else if (isPhaseMeter()) {
      final MeterLabel meterLabel = getMeterLabel(MeterSourceOption.PHASE);
      if (meterLabel != null) {
        row = row - meterLabel.row;
        column = column - meterLabel.column;
      }
    }
    return cantorize(row, column);
  }

  private MeterLabel getMeterLabel(final MeterSourceOption meterOption) {
    return getMeterLabels(getMeterSource(), getOptions(), getLayout(), false)
      .stream()
      .filter(label -> label.option.equals(meterOption))
      .findFirst()
      .orElse(null);
  }

  public static String getPreToneTBLabel(final Assignment meterAssignment) {
    String label = "";
    if (Assignment.MAIN_DESK_PRE_DELAY == meterAssignment) {
      label = "Pre-delay, Tone & TB";
    } else if (Assignment.FUNC_MAIN_DESK == meterAssignment) {
      label = "Pre-tone & TB";
    } else if (
      Assignment.MAIN_LINE_PRE_DELAY == meterAssignment ||
      Assignment.AUX_OP == meterAssignment ||
      Assignment.TRACK_OP == meterAssignment
    ) {
      label = "Pre-delay";
    }
    return label;
  }

  public String getUniqueId() {
    return Integer.toString(index);
  }

  public MeterLabelBackgroundColour getLabelBgColour() {
    switch (getMeterAssignment()) {
      case SEL_METER:
      case UNUSED1:
      case RTB:
      case UTILITY_MONITOR_MIXER:
      case CRLS_PRE:
        break;
      default:
        if (getPath().getHasPath()) {
          if (getPath().getIsChannel()) return MeterLabelBackgroundColour.GREY;
          if (getPath().getIsMain()) return MeterLabelBackgroundColour.RED;
          if (getPath().getIsGroup()) return MeterLabelBackgroundColour.TEAL;
          if (getPath().getIsTrack()) return MeterLabelBackgroundColour.BLUE;
          if (getPath().getIsAux()) return MeterLabelBackgroundColour.SEA_GREEN;
        }
    }
    return labelBgColour;
  }

  public MeterBlockLabels getDisplayLabels() {
    final MeterBlockLabels labels = new MeterBlockLabels();
    final String systemLabel = getSystemLabel();
    final boolean isHector = false;

    switch (getMeterAssignment()) {
      case FUNC_MAIN_LINE:
      case FUNC_MAIN_DESK:
      case FUNC_AUX_OP:
      case FUNC_TRACK_OP:
      case TRACK_OP:
      case AUX_OP:
      case GROUP_OP:
      case MAIN_LINE_PRE_DELAY:
      case MAIN_DESK_PRE_DELAY:
        labels.setLabel(systemLabel);
        if (Arrays.asList(label1.type, label2.type, label3.type).contains(LabelType.TONE)) {
          labels.setLabel("TONE");
        }
        labels.setPreToneTBPreDelayLabel(getPreToneTBLabel());
        if (!getOptions().isDualTrack && !getOptions().isQuadTrack) {
          labels.setTapeOrPortLabel(label1, systemLabel);
        }
        break;
      case APFL:
      case AFL:
      case PFL:
        if (isHector) {
          labels.setLabel(getLabel1());
        } else {
          labels.setLabel(getLabel1() + " " + getLabel2());
        }
        break;
      case SEL_METER:
      case UNUSED1:
      case RTB:
      case UTILITY_MONITOR_MIXER:
        if (!isHector && assignment == Assignment.SEL_METER) {
          labels.setTitleLabel(getMeterSource().getFullTitle() + " " + (getIndex() + 1));
        } else {
          labels.setTitleLabel(getMeterSource().getFullTitle() + " " + getMeterSource().getLabelSuffix());
        }
        labels.setLabel(getLabel2());
        break;
      case CRLS_PRE:
        labels.setTitleLabel(label1.label);
        labels.setLabel(getLabel2());
        if (LabelType.NATIVE != label3.type && label3.label != "") {
          labels.setTapeOrPortLabel(label3, systemLabel);
        }
        break;
      case OAC_OP:
        labels.setLabel(getLabel1());
        break;
      case MIX_MINUS:
        labels.setLabel(getLabel1() + " " + (getIndex() + 1));
        break;
      case EXTERNAL:
        if (!isLoudnessMeter()) {
          labels.setLabel(systemLabel);
        }
        if (LabelType.NATIVE != label1.type) {
          labels.setTapeOrPortLabel(label1, systemLabel);
        }
        break;
      default:
        labels.setLabel(systemLabel);
        labels.setTapeOrPortLabel(label2, systemLabel);
    }
    return labels;
  }

  public String getSubLayer() {
    String subLayer = "";
    if (isChannelOrBussMeter()) {
      if (label1.label.startsWith(String.format("%dA ", index + 1))) {
        subLayer = "A";
      } else if (label1.label.startsWith(String.format("%dB ", index + 1))) {
        subLayer = "B";
      }
    }
    return subLayer;
  }

  private String getSystemLabel() {
    final String pathLabel = getPathLabel();

    if (!getPath().getHasPath() || pathLabel.length() < 1) {
      if (getPath().getIsRemoteFader()) {
        return label1.label + getRemoteLabel();
      }
      return label1.label;
    }

    String pathSuffix = String.format("%s", getPath().number() + 1);

    if (getOptions().isDualTrack) {
      pathSuffix = String.format("%s-%s", getPath().number() + 1, getPath().number() + 2);
    } else if (getOptions().isQuadTrack) {
      pathSuffix = String.format("%s-%s", getPath().number() + 1, getPath().number() + 4);
    }

    return String.format("%s %s", pathLabel, pathSuffix);
  }

  private String getRemoteLabel() {
    return String.format("%s-%s", remoteHostName, remoteFaderLabel);
  }

  private String getPathLabel() {
    String label = "";
    switch (path.type()) {
      case MN:
        label = "Main";
        break;
      case AUX:
        label = "Aux";
        break;
      case REM_AUX:
        label = String.format("%s-Rem Aux", remoteHostName);
        break;
      case TK:
        if (getOptions().isDualTrack || getOptions().isQuadTrack) {
          label = "Tracks";
        } else {
          label = "Track";
        }
        break;
      case GP:
        label = "Group";
        break;
      case EXT:
        label = "Ext Input";
        break;
    }
    return label;
  }

  private static final String[] EXCLUDE_FROM_USER_LABEL = new String[] {
    "No input",
    "Tone",
    "OAC",
    "Auto Minus",
    "Fol Mon 1",
    "Fol Mon 1 + APFL",
    "Ext",
  };

  private String getUserLabel(final String label, final String systemLabel) {
    final String userLabel = systemLabel.equals(label) || label.length() == 0 ? null : label;
    if (userLabel == null) {
      return null;
    }
    for (String excludeLabel : EXCLUDE_FROM_USER_LABEL) {
      if (excludeLabel.equals(userLabel) || (excludeLabel + " " + (getIndex() + 1)).equals(userLabel)) {
        return null;
      }
    }
    return userLabel;
  }

  public int getLoudnessTimeslot() {
    return loudnessTimeslot;
  }

  public LoudnessScale getLoudnessScale() {
    return loudnessScale;
  }

  public LoudnessMode getLoudnessMode() {
    return loudnessMode;
  }

  public List<Integer> getTimeslots() {
    return timeslots;
  }

  public List<Integer> getDynamicTimeslots() {
    return dynamicTimeslots;
  }

  public List<String> getLegLabels() {
    return legLabels;
  }

  public void setLegLabels(List<String> legLabels) {
    this.legLabels = legLabels;
  }

  public Format getFormat() {
    return format;
  }

  public PathId getPath() {
    return path;
  }

  public int getBlockWidth() {
    return blockWidth;
  }

  private int getNumberOfBlocks(int[] configurationRows, int row) {
    for (int i = 0; i < configurationRows.length; i++) {
      if (configurationRows[i] == row) return i;
    }
    return -1;
  }

  // @todo: Rename channel to fader
  public boolean isChannelMeter() {
    return isChannelOrBussMeter() && this.assignment.name().contains("FADER");
  }

  public boolean isBussMeter() {
    return !path.isChannel();
  }

  public boolean isBussAssignedMeter() {
    return isChannelMeter() && !path.isChannel();
  }

  public boolean isChannelOrBussMeter() {
    return !(isLoudnessMeter() || isLoRoDownmixMeter() || isSurroundDownmixMeter() || isPhaseMeter());
  }

  public boolean isDynamicsMeter() {
    return !dynamicTimeslots.isEmpty();
  }

  public boolean isLoudnessMeter() {
    return style.equals(MeterStyle.LOUDNESS);
  }

  public boolean isPhaseMeter() {
    return style.equals(MeterStyle.PHASE);
  }

  public boolean isImmersiveMeter() {
    return format.getImmersiveLegCount() > 0;
  }

  public int getImmersiveLegsCount() {
    return format.getImmersiveLegCount();
  }

  public List<MeterLabel> getLabels() {
    // TODO Calculate audio format for monitors
    return getMeterLabels(
      getMeterSourceFromAssignment(getMeterAssignment(), getIndex(), AudioFormat.NO_FORMAT),
      getOptions(),
      getLayout(),
      false
    );
  }

  public MeterSource getMeterSource() {
    return getMeterSourceFromAssignment(assignment, getIndex(), getFormat());
  }

  public static List<MeterLabel> getMeterLabels(
    final MeterSource source,
    MeterOptions options,
    MeterLayout layout,
    Boolean isStaging
  ) {
    if (Objects.isNull(source)) return Collections.emptyList();
    int nextColumn = 0;
    final int fullHeight = options.isFullHeight ? 2 : 1;
    int levelWidth = 1;
    if (options.isAudioOnly()) levelWidth = source.getMinWidth();
    if (options.isQuadTrack) levelWidth = 2;
    final ArrayList<MeterLabel> labels = new ArrayList<>();
    if (options.isAudio) {
      labels.add(new MeterLabel(MeterSourceOption.AUDIO, 0, 0, fullHeight, levelWidth));
      nextColumn += 1;
    }

    if (options.isFullImmersive) {
      labels.add(
        new MeterLabel(
          MeterSourceOption.FULL_IMMERSIVE,
          0,
          nextColumn,
          fullHeight,
          MeterSourceOption.FULL_IMMERSIVE.getWidth()
        )
      );
      nextColumn += MeterSourceOption.FULL_IMMERSIVE.getWidth();
    }

    if (options.isDownmix && options.isDynamics && options.isLoudnessType() && !options.isSurroundDownmix) {
      switch (getDefaultLayout(layout, options)) {
        case LAYOUT_1:
          labels.add(new MeterLabel(MeterSourceOption.DOWNMIX, 0, nextColumn, 1, 1));
          labels.add(new MeterLabel(MeterSourceOption.DYNAMICS, 1, nextColumn, 1, 1));
          labels.add(new MeterLabel(options.getLoudnessType(), 0, nextColumn + 1, 2, 1));
          nextColumn += 2;
          break;
        case LAYOUT_2:
          labels.add(new MeterLabel(MeterSourceOption.DYNAMICS, 0, nextColumn, 1, 1));
          labels.add(new MeterLabel(MeterSourceOption.DOWNMIX, 0, nextColumn + 1, 1, 1));
          labels.add(new MeterLabel(options.getLoudnessType(), 1, nextColumn, 1, 2));
          nextColumn += 2;
          break;
        case LAYOUT_3:
          labels.add(new MeterLabel(MeterSourceOption.DYNAMICS, 0, nextColumn, fullHeight, 1));
          labels.add(new MeterLabel(MeterSourceOption.DOWNMIX, 0, nextColumn + 1, fullHeight, 1));
          labels.add(new MeterLabel(options.getLoudnessType(), 0, nextColumn + 2, fullHeight, 1));
          nextColumn += 3;
          break;
        default:
      }

      if (options.isPhase) {
        labels.add(
          new MeterLabel(MeterSourceOption.PHASE, 0, nextColumn, fullHeight, MeterSourceOption.PHASE.getWidth())
        );
        nextColumn += MeterSourceOption.PHASE.getWidth();
      }
    } else if (options.isDownmix && options.isDynamics && options.isSurroundDownmix && options.isLoudnessType()) {
      switch (getDefaultLayout(layout, options)) {
        case LAYOUT_1:
          labels.add(new MeterLabel(MeterSourceOption.DYNAMICS, 0, nextColumn, fullHeight, 1));
          labels.add(new MeterLabel(MeterSourceOption.DOWNMIX, 0, nextColumn + 1, fullHeight, 1));
          labels.add(new MeterLabel(MeterSourceOption.SU_DOWNMIX, 0, nextColumn + 2, fullHeight, 1));
          labels.add(new MeterLabel(options.getLoudnessType(), 0, nextColumn + 3, fullHeight, 1));
          nextColumn += 4;
          break;
        case LAYOUT_2:
          labels.add(new MeterLabel(MeterSourceOption.DYNAMICS, 0, nextColumn, 2, 1));
          labels.add(new MeterLabel(options.getLoudnessType(), 0, nextColumn + 1, 1, 2));
          labels.add(new MeterLabel(MeterSourceOption.DOWNMIX, 1, nextColumn + 1, 1, 1));
          labels.add(new MeterLabel(MeterSourceOption.SU_DOWNMIX, 1, nextColumn + 2, 1, 1));
          nextColumn += 3;
          break;
        default:
      }

      if (options.isPhase) {
        labels.add(
          new MeterLabel(MeterSourceOption.PHASE, 0, nextColumn, fullHeight, MeterSourceOption.PHASE.getWidth())
        );
        nextColumn += MeterSourceOption.PHASE.getWidth();
      }
    } else if (options.isDownmix && options.isDynamics) {
      switch (getDefaultLayout(layout, options)) {
        case LAYOUT_1:
          labels.add(new MeterLabel(MeterSourceOption.DOWNMIX, 0, nextColumn, 1, 1));
          labels.add(new MeterLabel(MeterSourceOption.DYNAMICS, 1, nextColumn, 1, 1));
          nextColumn += 1;
          break;
        case LAYOUT_2:
          labels.add(new MeterLabel(MeterSourceOption.DYNAMICS, 0, nextColumn, fullHeight, 1));
          labels.add(new MeterLabel(MeterSourceOption.DOWNMIX, 0, nextColumn + 1, fullHeight, 1));
          nextColumn += 2;
          break;
        case LAYOUT_3:
          labels.add(new MeterLabel(MeterSourceOption.DYNAMICS, 0, nextColumn, fullHeight, 1));
          labels.add(new MeterLabel(MeterSourceOption.SU_DOWNMIX, 0, nextColumn + 1, 1, 1));
          labels.add(new MeterLabel(MeterSourceOption.DOWNMIX, 1, nextColumn + 1, 1, 1));
          nextColumn += 2;
          break;
        case LAYOUT_4:
          labels.clear();
          labels.add(new MeterLabel(MeterSourceOption.DOWNMIX, 0, 0, 1, 1));
          labels.add(new MeterLabel(MeterSourceOption.SU_DOWNMIX, 0, 1, 1, isStaging ? 1 : 2));
          labels.add(new MeterLabel(MeterSourceOption.AUDIO, 1, 0, 1, isStaging ? 1 : 2));
          labels.add(new MeterLabel(MeterSourceOption.DYNAMICS, 1, isStaging ? 1 : 2, 1, 1));
          nextColumn = 2;
          break;
        default:
      }

      if (options.isSurroundDownmix && layout != MeterLayout.LAYOUT_3 && layout != MeterLayout.LAYOUT_4) {
        labels.add(new MeterLabel(MeterSourceOption.SU_DOWNMIX, 0, nextColumn, fullHeight, 1));
        nextColumn += 1;
      }

      if (options.isPhase) {
        labels.add(
          new MeterLabel(MeterSourceOption.PHASE, 0, nextColumn, fullHeight, MeterSourceOption.PHASE.getWidth())
        );
        nextColumn += MeterSourceOption.PHASE.getWidth();
      }
    } else if (options.isDynamics && options.isPhase) {
      switch (getDefaultLayout(layout, options)) {
        case LAYOUT_1:
          labels.add(new MeterLabel(MeterSourceOption.DYNAMICS, 0, nextColumn, fullHeight, 1));
          labels.add(new MeterLabel(MeterSourceOption.PHASE, 0, nextColumn + 1, fullHeight, 1));
          nextColumn += 2;
          break;
        case LAYOUT_2:
          labels.add(new MeterLabel(MeterSourceOption.DYNAMICS, 0, nextColumn, 1, 1));
          labels.add(new MeterLabel(MeterSourceOption.PHASE, 1, nextColumn, 1, 1));
          nextColumn += 1;
          break;
        default:
      }

      if (options.isSurroundDownmix) {
        labels.add(new MeterLabel(MeterSourceOption.SU_DOWNMIX, 0, nextColumn, fullHeight, 1));
        nextColumn += 1;
      }

      if (options.isLoudnessType()) {
        labels.add(new MeterLabel(options.getLoudnessType(), 0, nextColumn, fullHeight, 1));
        nextColumn += 1;
      }
    } else if (options.isDynamics && options.isLoudnessType() && !options.isSurroundDownmix) {
      switch (getDefaultLayout(layout, options)) {
        case LAYOUT_1:
          labels.add(new MeterLabel(MeterSourceOption.DYNAMICS, 0, nextColumn, fullHeight, 1));
          labels.add(new MeterLabel(options.getLoudnessType(), 0, nextColumn + 1, fullHeight, 2));
          nextColumn += 2;
          break;
        case LAYOUT_2:
          labels.clear();
          labels.add(new MeterLabel(MeterSourceOption.AUDIO, 1, 0, 1, levelWidth));
          labels.add(new MeterLabel(MeterSourceOption.DYNAMICS, 1, 1, 1, 1));
          labels.add(new MeterLabel(options.getLoudnessType(), 0, 0, 1, 2));
          nextColumn = 2;
          break;
        default:
      }
      // TODO Cannot support non-audio meter first at this stage
      // } else if (options.isPhaseOnly()) {
      //   switch (getDefaultLayout(layout, options)) {
      //     case LAYOUT_1:
      //       labels.add(new MeterLabel(MeterSourceOption.PHASE, 0, nextColumn, fullHeight, 1));
      //       nextColumn += 1;
      //       break;
      //     case LAYOUT_2:
      //       labels.clear();
      //       labels.add(new MeterLabel(MeterSourceOption.AUDIO, 1, 0, 1, levelWidth));
      //       labels.add(new MeterLabel(MeterSourceOption.PHASE, 0, 0, 1, levelWidth));
      //       nextColumn = levelWidth;
      //       break;
      //     default:
      //   }
    } else {
      if (options.isDynamics) {
        labels.add(
          new MeterLabel(MeterSourceOption.DYNAMICS, 0, nextColumn, fullHeight, MeterSourceOption.DYNAMICS.getWidth())
        );
        nextColumn += MeterSourceOption.DYNAMICS.getWidth();
      }

      if (options.isDownmix) {
        labels.add(
          new MeterLabel(MeterSourceOption.DOWNMIX, 0, nextColumn, fullHeight, MeterSourceOption.DOWNMIX.getWidth())
        );
        nextColumn += MeterSourceOption.DOWNMIX.getWidth();
      }

      if (options.isSurroundDownmix) {
        final Integer surroundDownmixWidth = (options.isDynamics || options.isDownmix || options.isFullImmersive)
          ? 1
          : MeterSourceOption.SU_DOWNMIX.getWidth();
        labels.add(new MeterLabel(MeterSourceOption.SU_DOWNMIX, 0, nextColumn, fullHeight, surroundDownmixWidth));
        nextColumn += surroundDownmixWidth;
      }

      if (options.isPhase) {
        labels.add(
          new MeterLabel(MeterSourceOption.PHASE, 0, nextColumn, fullHeight, MeterSourceOption.PHASE.getWidth())
        );
        nextColumn += MeterSourceOption.PHASE.getWidth();
      }

      if (options.isLoudnessType()) {
        final Integer loudnessWidth = (options.isSurroundDownmix) ? 1 : options.getLoudnessType().getWidth();
        labels.add(new MeterLabel(options.getLoudnessType(), 0, nextColumn, fullHeight, loudnessWidth));
        nextColumn += loudnessWidth;
      }
    }

    return labels;
  }

  public static MeterLayout getDefaultLayout(final MeterLayout layout, final MeterOptions options) {
    if (layout != null) return layout;

    if (
      options.isDownmix && options.isDynamics && options.isLoudnessType() && !options.isSurroundDownmix
    ) return options.isFullHeight ? MeterLayout.LAYOUT_1 : MeterLayout.LAYOUT_3;
    if (
      options.isDynamics && options.isDownmix && options.isSurroundDownmix && options.isLoudnessType()
    ) return MeterLayout.LAYOUT_1;
    if (options.isDownmix && options.isDynamics) return options.isFullHeight
      ? MeterLayout.LAYOUT_1
      : MeterLayout.LAYOUT_2;
    if (options.isDynamics && options.isPhase) return MeterLayout.LAYOUT_1;
    if (options.isDynamics && options.isLoudnessType()) return MeterLayout.LAYOUT_1;

    // if (options.isPhaseOnly()) return MeterLayout.LAYOUT_1;
    return null;
  }
  
  public static MeterSource getMeterSourceFromAssignment(final Assignment assignment, final Integer faderIndexNumber) {
    return getMeterSourceFromAssignment(assignment, faderIndexNumber, AudioFormat.NO_FORMAT);
  }

  public static MeterSource getMeterSourceFromAssignment(
    final Assignment assignment,
    final Integer faderIndexNumber,
    final Format format
  ) {
    if (Format.ST == format) {
      return getMeterSourceFromAssignment(assignment, faderIndexNumber, AudioFormat.STEREO);
    } else if (Format.SU_5_1 == format) {
      return getMeterSourceFromAssignment(assignment, faderIndexNumber, AudioFormat.SURROUND_5_1);
    } else {
      return getMeterSourceFromAssignment(assignment, faderIndexNumber);
    }
  }
  
  public static MeterSource getMeterSourceFromAssignment(
    final Assignment assignment,
    final Integer faderIndexNumber,
    final AudioFormat audioFormat
  ) {
    switch (assignment) {
      case FUNC_MAIN_LINE:
      case MAIN_DESK_PRE_DELAY:
      case FUNC_MAIN_DESK:
      case MAIN_LINE_PRE_DELAY:
        return MeterSource.MAIN;
      case GROUP_OP:
        return MeterSource.GROUP;
      case FUNC_AUX_OP:
      case AUX_OP:
        return MeterSource.AUX;
      case FUNC_TRACK_OP:
      case TRACK_OP:
        return MeterSource.TRACK;
      case EXTERNAL:
        return MeterSource.EXTERNAL_INPUT;
      case AFL:
        return AudioFormat.STEREO == audioFormat ? MeterSource.AFL_STEREO : MeterSource.AFL_SURROUND;
      case PFL:
        return AudioFormat.STEREO == audioFormat ? MeterSource.PFL_STEREO : MeterSource.PFL_SURROUND;
      case APFL:
        return AudioFormat.STEREO == audioFormat ? MeterSource.APFL_STEREO : MeterSource.APFL_SURROUND;
      case CRLS_PRE:
        return MeterSource.CONSOLE_LS;
      case OAC_OP:
        return MeterSource.OAC;
      case MIX_MINUS:
        return MeterSource.MIX_MINUS.getMixMinusMeterSourceByIndex(faderIndexNumber);
      case SEL_METER:
        return faderIndexNumber == 0 ? MeterSource.USER_METER_1 : MeterSource.USER_METER_2;
      case FADER_IP_LVL_N_DYN:
        return MeterSource.FADER;
      case VIDEO_OVERLAY:
        return MeterSource.VIDEO;
      default:
        return null;
    }
  }

  protected Assignment getMeterAssignment() {
    return assignment;
  }

  public Assignment getAssignment() {
    return assignment;
  }

  public MeterOptions getOptions() {
    return options;
  }

  public MeterLayout getLayout() {
    return layout;
  }
}
