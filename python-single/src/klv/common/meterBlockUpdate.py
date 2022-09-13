from gql.meters.meterBlockLabels import MeterBlockLabels
from gql.meters.meterLabel import MeterLabel
from klv import Walker
from klv.common.enums.assignment import Assignment
from klv.common.enums.dynamicsMode import DynamicsMode
from klv.common.enums.faderInputDisplay import FaderInputDisplay
from klv.common.enums.format import Format
from klv.common.enums.labelType import LabelType
from klv.common.enums.loudnessMode import LoudnessMode
from klv.common.enums.loudnessScale import LoudnessScale
from klv.common.enums.meterFormat import MeterFormat
from klv.common.enums.meterLabelBackgroundColour import MeterLabelBackgroundColour
from klv.common.enums.meterLayout import MeterLayout
from klv.common.enums.meterSource import MeterSource
from klv.common.enums.meterSourceOption import MeterSourceOption
from klv.common.enums.meterStyle import MeterStyle
from klv.common.enums.pathType import PathType
from klv.common.label import Label
from klv.common.meterOptions import MeterOptions
from klv.common.pathId import PathId


class MeterBlockUpdate:
  ROW_ZERO_CONFIGURATION_ROWS = [0, 4, 8, 12, 16, 20, 24, 28 ]
  ROW_ONE_CONFIGURATION_ROWS = [1, 5, 9, 13, 17, 21, 25, 29 ]
  ROW_TWO_CONFIGURATION_ROWS = [2, 6, 10, 14, 18, 22, 26, 30 ]
  ROW_THREE_CONFIGURATION_ROWS = [3, 7, 11, 15, 19, 23, 27, 31 ]
  NO_OF_COLUMNS_IN_A_BLOCK = 3

  def __init__(self, walker: Walker):
    self.row = walker.read_u8()
    self.column = walker.read_u8()
    self.assignment = Assignment(walker.read_u8())
    self.blockHeight = walker.read_u8()
    self.style = MeterStyle(walker.read_u8())
    self.labelBgColour = MeterLabelBackgroundColour(walker.read_u8())
    self.label1 = Label(walker)
    self.label2 = Label(walker)
    self.label3 = Label(walker)

    numTimeslots = walker.read_u32()
    self.timeslots = []
    for _ in range(0, numTimeslots):
      self.timeslots.append(walker.read_u16())
    
    numDynamicTimeslots = walker.read_u32()
    self.dynamicTimeslots = []
    for _ in range(0, numDynamicTimeslots):
      self.dynamicTimeslots.append(walker.read_u16())
    
    numLegLabels = walker.read_u32()
    self.legLabels = []
    for _ in range(0, numLegLabels):
      self.legLabels.append(walker.read_str())

    self.format = Format(walker.read_u32())
    self.index = walker.read_u16()
    self.errorMessage = walker.read_str()
    self.dynamicsMode = DynamicsMode(walker.read_u8())
    self.loudnessMode = LoudnessMode(walker.read_u8())
    self.loudnessScale = LoudnessScale(walker.read_u8())
    self.loudnessTimeslot = walker.read_u16()
    self.path = PathId(walker)
    self.faderDisplay = FaderInputDisplay(walker.read_u8())
    self.layout = MeterLayout(walker.read_u8())
    self.options = MeterOptions(walker)
    self.blockWidth = walker.read_u8()
    self.combinedBlockWidth = walker.read_u8()
    self.highlightIndex = walker.read_u16()
    self.remoteHostName = walker.read_str()
    self.remoteFaderLabel = walker.read_str()
    self.meterFormat = MeterFormat(walker.read_u8())

  @staticmethod
  def cantorize(k1: int, k2: int):
    return (k1 + k2) * (k1 + k2 + 1) / 2 + k2

  def getMCSRow(self):
    return self.row

  def getMCSColumn(self):
    return self.column

  def getMeterAssignment(self):
    return self.assignment

  def getPath(self):
    return self.path

  def getBlockWidth(self):
    return self.blockWidth

  def getBlockHeight(self):
    return self.blockHeight

  def getRow(self):
    row = self.getMCSRow()
    if row % 4 == 0:
      return 0
    if (row - 1) % 4 == 0:
      return 1
    if (row - 2) % 4 == 0:
      return 2
    if (row - 3) % 4 == 0:
      return 3
    return -1

  def getChannelIndex(self):
    if self.isChannelMeter():
      return (self.index + 1)
    return 0

  def isChannelMeter(self):
    return self.isChannelOrBussMeter() and "FADER" in str(self.assignment)

  def isBussMeter(self):
    return not self.path.getIsChannel()

  def isBussAssignedMeter(self):
    return self.isChannelMeter() and not self.path.getIsChannel()

  def isChannelOrBussMeter(self):
    return not (self.isLoudnessMeter() or self.isLoRoDownmixMeter() or self.isSurroundDownmixMeter() or self.isPhaseMeter())

  def isDynamicsMeter(self):
    return len(self.dynamicTimeslots) > 0

  def isLoudnessMeter(self):
    return self.style is MeterStyle.LOUDNESS

  def isLoRoDownmixMeter(self):
    return not self.isLoudnessMeter() and self.meterFormat is MeterFormat.STEREO_LO_RO

  def isSurroundDownmixMeter(self):
    return not self.isLoudnessMeter() and self.meterFormat is MeterFormat.DOWNMIX_5_1

  def isPhaseMeter(self):
    return self.style is MeterStyle.PHASE

  def isImmersiveMeter(self):
    return self.format.getImmersiveLegCount() > 0

  def getOptions(self):
    return self.options

  def getIndex(self):
    return self.index

  def getFormat(self):
    return self.format

  def getLayout(self):
    return self.layout

  def getRemoteLabel(self):
    return "{}-{}".format(self.remoteHostName, self.remoteFaderLabel)

  def getMeterSource(self):
    return self.getMeterSourceFromAssignment(self.assignment, self.getIndex(), self.getFormat())

  def getLabel1(self):
    return self.label1.label

  def getLabel2(self):
    return self.label2.label

  def getLabel3(self):
    return self.label3.label

  def getSubLayer(self):
    if self.isChannelOrBussMeter():
      if self.label1.label.startswith("{}A".format(self.index + 1)):
        return "A"
      elif self.label1.label.startswith("{}B".format(self.index + 1)):
        return "B"
    return ""

  def getImmersiveLegsCount(self):
    return self.format.getImmersiveLegCount()

  def getLegLabels(self):
    return self.legLabels

  def getMeterSourceFromAssignment(
    self,
    assignment: Assignment,
    faderIndexNumber: int,
    format: Format,
  ):
    if format is Format.ST:
      return self.getMeterSourceFromAssignment3(assignment, faderIndexNumber, Format.ST)
    elif format is Format.SU_5_1:
      return self.getMeterSourceFromAssignment3(assignment, faderIndexNumber, Format.SU_5_1)
    return self.getMeterSourceFromAssignment2(assignment, faderIndexNumber)

  def getMeterSourceFromAssignment2(self, assignment: Assignment, faderIndexNumber: int):
    return self.getMeterSourceFromAssignment3(assignment, faderIndexNumber, Format.NP)

  def getMeterSourceFromAssignment3(
    self,
    assignment: Assignment,
    faderIndexNumber: int,
    format: Format
  ):
    if assignment is Assignment.FUNC_MAIN_LINE or assignment is Assignment.MAIN_DESK_PRE_DELAY or assignment is  Assignment.FUNC_MAIN_DESK or assignment is Assignment.MAIN_LINE_PRE_DELAY:
        return MeterSource.MAIN
    elif assignment is Assignment.GROUP_OP:
      return MeterSource.GROUP
    elif assignment is Assignment.FUNC_AUX_OP or assignment is Assignment.AUX_OP:
      return MeterSource.AUX
    elif assignment is Assignment.FUNC_TRACK_OP or assignment is Assignment.TRACK_OP:
      return MeterSource.TRACK
    elif assignment is Assignment.EXTERNAL:
      return MeterSource.EXTERNAL_INPUT
    elif assignment is Assignment.AFL:
      if format is Format.ST:
        return MeterSource.AFL_STEREO
      return MeterSource.AFL_SURROUND
    elif assignment is Assignment.PFL:
      if format is Format.ST:
        return MeterSource.PFL_STEREO
      return MeterSource.PFL_SURROUND
    elif assignment is Assignment.APFL:
      if format is Format.ST:
        return MeterSource.APFL_STEREO
      return MeterSource.APFL_SURROUND
    elif assignment is Assignment.CRLS_PRE:
      return MeterSource.CONSOLE_LS
    elif assignment is Assignment.OAC_OP:
      return MeterSource.OAC
    elif assignment is Assignment.MIX_MINUS:
      return MeterSource.MIX_MINUS.getMixMinusMeterSourceByIndex(faderIndexNumber)
    elif assignment is Assignment.SEL_METER:
      if faderIndexNumber == 0:
        return MeterSource.USER_METER_1
      return MeterSource.USER_METER_2
    elif assignment is Assignment.FADER_IP_LVL_N_DYN:
      return MeterSource.FADER
    elif assignment is Assignment.VIDEO_OVERLAY:
      return MeterSource.VIDEO
    return None

  def getMeterLabels(
    self,
    _source: MeterSource,
    _options: MeterOptions,
    _layout: MeterLayout,
    _isStaging: bool
  ):
    meterLabels = []

    meterLabels.append(MeterLabel(option=MeterSourceOption.AUDIO, row=0, column=0, height=1, width=1 ))

    meterLabels.append(MeterLabel(option=MeterSourceOption.DYNAMICS, row=0, column=1, height=1, width=1 ))
    # if options.isDynamics {
    # }

    return meterLabels

  def getMeterLabel(self, meterOption: MeterSourceOption):
    meterLabels = self.getMeterLabels(self.getMeterSource(), self.getOptions(), self.getLayout(), False)

    meterLabel = None

    for label in meterLabels:
      if label.option == meterOption:
        meterLabel = label

    return meterLabel

  def getNumberOfBlocks(configurationRows, row):
    for i in range(0, len(configurationRows)):
      if configurationRows[i] == row:
        return i
    return -1

  def getBlockId(self):
    row = self.getRow()
    column = self.getColumn()
    if self.isLoudnessMeter():
      meterLabel = None
      if self.getOptions().isLoudness:
        meterLabel = self.getMeterLabel(MeterSourceOption.LOUDNESS)
      if self.getOptions().isTrackPairLoudness:
        meterLabel = self.getMeterLabel(MeterSourceOption.TRACK_PAIR_LOUDNESS)
      if self.getOptions().isTrackOddLoudness:
        meterLabel = self.getMeterLabel(MeterSourceOption.TRACK_ODD_LOUDNESS)
      if self.getOptions().isTrackEvenLoudness:
        meterLabel = self.getMeterLabel(MeterSourceOption.TRACK_EVEN_LOUDNESS)
      if self.getOptions().isDownmixLoudness:
        meterLabel = self.getMeterLabel(MeterSourceOption.DOWNMIX_LOUDNESS)
      if self.getOptions().isDownmixSurroundLoudness:
        meterLabel = self.getMeterLabel(MeterSourceOption.SU_DOWNMIX_LOUDNESS)

      if meterLabel is not None:
        row = row - meterLabel.row
        column = column - meterLabel.column
    elif self.isLoRoDownmixMeter() or self.isSurroundDownmixMeter():
      meterOption = MeterSourceOption.SU_DOWNMIX if self.isSurroundDownmixMeter() else MeterSourceOption.DOWNMIX
      meterLabel = self.getMeterLabel(meterOption)
      if meterLabel is not None:
        row = row - meterLabel.row
        column = column - meterLabel.column
    elif self.isPhaseMeter():
      meterLabel = self.getMeterLabel(MeterSourceOption.PHASE)
      if meterLabel is not None:
        row = row - meterLabel.row
        column = column - meterLabel.column
    return MeterBlockUpdate.cantorize(row, column)

  def getColumn(self):
    row = self.getMCSRow()
    column = self.getMCSColumn()

    numberOfBlocks = 0
    rowNum = self.getRow()

    if rowNum == 0:
      numberOfBlocks = MeterBlockUpdate.getNumberOfBlocks(MeterBlockUpdate.ROW_ZERO_CONFIGURATION_ROWS, row)
    elif rowNum == 1:
      numberOfBlocks = MeterBlockUpdate.getNumberOfBlocks(MeterBlockUpdate.ROW_ONE_CONFIGURATION_ROWS, row)
    elif rowNum == 2:
      numberOfBlocks = MeterBlockUpdate.getNumberOfBlocks(MeterBlockUpdate.ROW_TWO_CONFIGURATION_ROWS, row)
    elif rowNum == 3:
      numberOfBlocks = MeterBlockUpdate.getNumberOfBlocks(MeterBlockUpdate.ROW_THREE_CONFIGURATION_ROWS, row)

    return (numberOfBlocks * MeterBlockUpdate.NO_OF_COLUMNS_IN_A_BLOCK) + column

  def getLabelBgColour(self):
    assignment = self.getMeterAssignment()

    if not (assignment is Assignment.SEL_METER or assignment is Assignment.UNUSED1 or assignment is Assignment.UTILITY_MONITOR_MIXER or assignment is Assignment.CRLS_PRE):
      if self.getPath().getHasPath():
        if self.getPath().getIsChannel():
          return MeterLabelBackgroundColour.GREY
        if self.getPath().getIsMain():
          return MeterLabelBackgroundColour.RED
        if self.getPath().getIsGroup():
          return MeterLabelBackgroundColour.TEAL
        if self.getPath().getIsTrack():
          return MeterLabelBackgroundColour.BLUE
        if self.getPath().getIsAux():
          return MeterLabelBackgroundColour.SEA_GREEN
    return self.labelBgColour

  def getPathLabel(self):
    pathType = self.path.pathType()

    if pathType is PathType.MN:
      return "Main"
    elif pathType is PathType.AUX:
      return "Aux"
    elif pathType is PathType.REM_AUX:
      return "{}-Rem Aux".format(self.remoteHostName)
    elif pathType is PathType.TK:
      if self.getOptions().isDualTrack or self.getOptions().isQuadTrack:
        return "Tracks"
      return "Track"
    elif pathType is PathType.GP:
      return "Group"
    elif PathType.EXT:
      return "Ext Input"
    
    return ""

  def getSystemLabel(self):
    pathLabel = self.getPathLabel()

    if not self.getPath().getHasPath() or len(pathLabel) < 1:
      if self.getPath().getIsRemoteFader():
        return "{}{}".format(self.label1.label, self.getRemoteLabel())
      return self.label1.label

    pathSuffix = str(self.getPath().number() + 1)

    if self.getOptions().isDualTrack:
      pathSuffix = "{}-{}".format(self.getPath().number() + 1, self.getPath().number() + 2)
    elif self.getOptions().isQuadTrack:
      pathSuffix = "{}-{}".format(self.getPath().number() + 1, self.getPath().number() + 4)

    return "{} {}".format(pathLabel, pathSuffix)

  def getPreToneTBLabel(self):
    return MeterBlockUpdate.getPreToneTBLabel2(self.getMeterAssignment())

  def getPreToneTBLabel2(meterAssignment: Assignment):
    if meterAssignment is Assignment.MAIN_DESK_PRE_DELAY:
      return "Pre-delay, Tone & TB"
    elif meterAssignment is Assignment.FUNC_MAIN_DESK:
      return "Pre-tone & TB"
    elif (
      meterAssignment is Assignment.MAIN_LINE_PRE_DELAY or
      meterAssignment is Assignment.AUX_OP or
      meterAssignment is Assignment.TRACK_OP
    ):
      return "Pre-delay"
    return ""

  def getDisplayLabels(self):
    labels = MeterBlockLabels()
    systemLabel = self.getSystemLabel()
    isHector = False

    assignment = self.getMeterAssignment()

    if assignment is Assignment.FUNC_MAIN_LINE or \
        assignment is Assignment.FUNC_MAIN_DESK or \
        assignment is Assignment.FUNC_AUX_OP or \
        assignment is Assignment.FUNC_TRACK_OP or \
        assignment is Assignment.TRACK_OP or \
        assignment is Assignment.AUX_OP or \
        assignment is Assignment.GROUP_OP or \
        assignment is Assignment.MAIN_LINE_PRE_DELAY or \
        assignment is Assignment.MAIN_DESK_PRE_DELAY:
      labels.setLabel(systemLabel)
      if LabelType.TONE in [self.label1.labelType, self.label2.labelType, self.label3.labelType]:
        labels.setLabel("TONE")
      labels.setPreToneTBPreDelayLabel(self.getPreToneTBLabel())
      if not self.getOptions().isDualTrack and not self.getOptions().isQuadTrack:
        labels.setTapeOrPortLabel(self.label1, systemLabel)
    elif assignment is Assignment.APFL or assignment is Assignment.AFL or assignment is Assignment.PFL:
      if isHector:
        labels.setLabel(self.getLabel1())
      else:
        labels.setLabel(format("{} {}", self.getLabel1(), self.getLabel2()))
    elif assignment is Assignment.SEL_METER or assignment is Assignment.UNUSED1 or assignment is Assignment.RTB or assignment is Assignment.UTILITY_MONITOR_MIXER:
      if self.getMeterSource() is not None:
        if not isHector and self.assignment is Assignment.SEL_METER:
          labels.setTitleLabel(format("{} {}", self.getMeterSource().getFullTitle(), (self.getIndex() + 1)))
        else:
          labels.setTitleLabel(format("{} {}", self.getMeterSource().getFullTitle(), self.getMeterSource().getLabelSuffix()))

      labels.setLabel(self.getLabel2())
    elif assignment is Assignment.CRLS_PRE:
      labels.setTitleLabel(self.label1.label)
      labels.setLabel(self.getLabel2())
      if not self.label3.labelType is LabelType.NATIVE and self.label3.label != "":
        labels.setTapeOrPortLabel(self.label3, systemLabel)
    elif assignment is Assignment.OAC_OP:
      labels.setLabel(self.getLabel1())
    elif assignment is Assignment.MIX_MINUS:
      labels.setLabel(format("{} {}", self.getLabel1(), (self.getIndex() + 1)))
    elif assignment is Assignment.EXTERNAL:
      if not self.isLoudnessMeter():
        labels.setLabel(systemLabel)
      if not self.label1.labelType is LabelType.NATIVE:
        labels.setTapeOrPortLabel(self.label1, systemLabel)
    else:
      labels.setLabel(systemLabel)
      labels.setTapeOrPortLabel(self.label2, systemLabel)

    return labels

  def getLabels(self):
    # TODO Calculate audio format for monitors
    return self.getMeterLabels(
      self.getMeterSourceFromAssignment(self.getMeterAssignment(), self.getIndex(), Format.NP),
      self.getOptions(),
      self.getLayout(),
      False
    )
