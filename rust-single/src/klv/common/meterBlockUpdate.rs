#![allow(non_snake_case, non_camel_case_types)]
use bytes::{Bytes, Buf};
use crate::{klv::helpers::read_string, gql::meters::{meterLabel::MeterLabel, meterBlockLabels::MeterBlockLabels}};

use super::{enums::{assignment::Assignment, meterStyle::MeterStyle, meterLabelBackgroundColour::MeterLabelBackgroundColour, format::Format, dynamicsMode::DynamicsMode, loudnessMode::LoudnessMode, loudnessScale::LoudnessScale, faderInputDisplay::FaderInputDisplay, meterLayout::MeterLayout, meterFormat::MeterFormat, meterSourceOption::MeterSourceOption, meterSource::MeterSource, pathType::PathType, labelType::LabelType}, pathId::PathId, meterOptions::MeterOptions, label::Label};

#[derive(Debug)]
pub struct MeterBlockUpdate {
  pub row: u8,
  pub column: u8,
  pub assignment: Assignment, // u8
  pub blockHeight: u8,
  pub style: MeterStyle, // u8
  pub labelBgColour: MeterLabelBackgroundColour, // u8
  pub label1: Label,
  pub label2: Label,
  pub label3: Label,
  pub timeslots: Vec<u16>,
  pub dynamicTimeslots: Vec<u16>,
  pub legLabels: Vec<String>,
  pub format: Format, // u32
  pub index: u16,
  pub errorMessage: String,
  pub dynamicsMode: DynamicsMode, // u8
  pub loudnessMode: LoudnessMode, // u8
  pub loudnessScale: LoudnessScale, // u8
  pub loudnessTimeslot: u16,
  pub path: PathId,
  pub faderDisplay: FaderInputDisplay, // u8
  pub layout: MeterLayout, // u8
  pub options: MeterOptions, // u32
  pub blockWidth: u8,
  pub combinedBlockWidth: u8,
  pub highlightIndex: u16,
  pub remoteHostName: String,
  pub remoteFaderLabel: String,
  pub meterFormat: MeterFormat, // u8
}

impl MeterBlockUpdate {
  const ROW_ZERO_CONFIGURATION_ROWS: &'static [i32] = &[0, 4, 8, 12, 16, 20, 24, 28 ];
  const ROW_ONE_CONFIGURATION_ROWS: &'static [i32] = &[1, 5, 9, 13, 17, 21, 25, 29 ];
  const ROW_TWO_CONFIGURATION_ROWS: &'static [i32] = &[2, 6, 10, 14, 18, 22, 26, 30 ];
  const ROW_THREE_CONFIGURATION_ROWS: &'static [i32] = &[3, 7, 11, 15, 19, 23, 27, 31 ];
  const NO_OF_COLUMNS_IN_A_BLOCK: &'static i32 = &3;

  pub fn read(buf: &mut Bytes) -> MeterBlockUpdate {
    let row = buf.get_u8();
    let column = buf.get_u8();
    let assignment = unsafe { ::std::mem::transmute(buf.get_u8()) };
    let blockHeight = buf.get_u8();
    let style = unsafe { ::std::mem::transmute(buf.get_u8()) };
    let labelBgColour = unsafe { ::std::mem::transmute(buf.get_u8()) };
    let label1 = Label::read(buf);
    let label2 =  Label::read(buf);
    let label3 =  Label::read(buf);

    let numTimeslots = buf.get_u32_le();
    let mut timeslots = vec![];
    for _ in 0..numTimeslots {
      timeslots.push(buf.get_u16_le());
    }

    let numDynamicTimeslots = buf.get_u32_le();
    let mut dynamicTimeslots = vec![];
    for _ in 0..numDynamicTimeslots {
      dynamicTimeslots.push(buf.get_u16_le());
    }

    let numLegLabels = buf.get_u32_le();
    let mut legLabels = vec![];
    for _ in 0..numLegLabels {
      legLabels.push(read_string(buf));
    }

    let format = unsafe { ::std::mem::transmute(buf.get_u32_le()) };
    let index = buf.get_u16_le();
    let errorMessage = read_string(buf);
    let dynamicsMode = unsafe { ::std::mem::transmute(buf.get_u8()) };
    let loudnessMode = unsafe { ::std::mem::transmute(buf.get_u8()) };
    let loudnessScale = unsafe { ::std::mem::transmute(buf.get_u8()) };
    let loudnessTimeslot = buf.get_u16_le();
    let path = PathId::read(buf);
    let faderDisplay = unsafe { ::std::mem::transmute(buf.get_u8()) };
    let layout = unsafe { ::std::mem::transmute(buf.get_u8()) };
    let options = MeterOptions::read(buf);
    let blockWidth = buf.get_u8();
    let combinedBlockWidth = buf.get_u8();
    let highlightIndex = buf.get_u16_le();
    let remoteHostName = read_string(buf);
    let remoteFaderLabel = read_string(buf);
    let meterFormat = unsafe { ::std::mem::transmute(buf.get_u8()) };

    MeterBlockUpdate {
      row,
      column,
      assignment,
      blockHeight,
      style,
      labelBgColour,
      label1,
      label2,
      label3,
      timeslots,
      dynamicTimeslots,
      legLabels,
      format,
      index,
      errorMessage,
      dynamicsMode,
      loudnessMode,
      loudnessScale,
      loudnessTimeslot,
      path,
      faderDisplay,
      layout,
      options,
      blockWidth,
      combinedBlockWidth,
      highlightIndex,
      remoteHostName,
      remoteFaderLabel,
      meterFormat,
    }
  }

  pub fn cantorize(k1: i32, k2: i32) -> i32 {
    (k1 + k2) * (k1 + k2 + 1) / 2 + k2
  }

  fn getMCSRow(&self) -> i32 {
    self.row.into()
  }

  fn getMCSColumn(&self) -> i32 {
    self.column.into()
  }

  fn getMeterAssignment(&self) -> Assignment{
    self.assignment
  }

  pub fn getPath(&self) -> &PathId {
    &self.path
  }

  pub fn getBlockWidth(&self) -> i32 {
    self.blockWidth.into()
  }

  pub fn getBlockHeight(&self) -> i32 {
    self.blockHeight.into()
  }

  pub fn getRow(&self) -> i32 {
    let row = self.getMCSRow();
    if row % 4 == 0 { return 0; }
    if (row - 1) % 4 == 0 { return 1; }
    if (row - 2) % 4 == 0 { return 2; }
    if (row - 3) % 4 == 0 { return 3; }
    return -1;
  }

  pub fn getChannelIndex(&self) -> i32 {
    if self.isChannelMeter() {
      return (self.index + 1) as i32;
    }
    0
  }

  pub fn isChannelMeter(&self) -> bool {
    self.isChannelOrBussMeter() && self.assignment.to_string().contains("FADER")
  }

  pub fn isBussMeter(&self) -> bool {
    !self.path.getIsChannel()
  }

  pub fn isBussAssignedMeter(&self) -> bool {
    self.isChannelMeter() && !self.path.getIsChannel()
  }

  pub fn isChannelOrBussMeter(&self) -> bool {
    !(self.isLoudnessMeter() || self.isLoRoDownmixMeter() || self.isSurroundDownmixMeter() || self.isPhaseMeter())
  }

  pub fn isDynamicsMeter(&self) -> bool {
    !self.dynamicTimeslots.is_empty()
  }

  pub fn isLoudnessMeter(&self) -> bool {
    matches!(self.style, MeterStyle::LOUDNESS)
  }

  pub fn isLoRoDownmixMeter(&self) -> bool {
    !self.isLoudnessMeter() && matches!(self.meterFormat, MeterFormat::STEREO_LO_RO)
  }

  pub fn isSurroundDownmixMeter(&self) -> bool {
    !self.isLoudnessMeter() && matches!(self.meterFormat, MeterFormat::DOWNMIX_5_1)
  }

  pub fn isPhaseMeter(&self) -> bool {
    matches!(self.style, MeterStyle::PHASE)
  }

  pub fn isImmersiveMeter(&self) -> bool {
    self.format.getImmersiveLegCount() > 0
  }

  pub fn getOptions(&self) -> &MeterOptions {
    &self.options
  }

  pub fn getIndex(&self) -> i32 {
    self.index.into()
  }

  pub fn getFormat(&self) -> Format {
    self.format
  }

  pub fn getLayout(&self) -> MeterLayout {
    self.layout
  }

  fn getRemoteLabel(&self) -> String {
    format!("{}-{}", self.remoteHostName, self.remoteFaderLabel)
  }

  pub fn getMeterSource(&self) -> Option<MeterSource> {
    self.getMeterSourceFromAssignment(self.assignment, self.getIndex(), self.getFormat())
  }

  pub fn getLabel1(&self) -> &String {
    &self.label1.label
  }

  pub fn getLabel2(&self) -> &String {
    &self.label2.label
  }

  pub fn getLabel3(&self) -> &String {
    &self.label3.label
  }

  pub fn getSubLayer(&self) -> String {
    if self.isChannelOrBussMeter() {
      if self.label1.label.starts_with(format!("{}A", self.index + 1).as_str()) {
        return "A".into();
      } else if self.label1.label.starts_with(format!("{}B", self.index + 1).as_str()) {
        return "B".into();
      }
    }
    return String::new();
  }

  pub fn getImmersiveLegsCount(&self) -> i32 {
    self.format.getImmersiveLegCount()
  }

  pub fn getLegLabels(&self) -> Vec<String> {
    self.legLabels.iter().map(|label| label.clone()).collect()
  }

  pub fn getMeterSourceFromAssignment(
    &self,
    assignment: Assignment,
    faderIndexNumber: i32,
    format: Format,
  ) -> Option<MeterSource> {
    if matches!(format, Format::ST) {
      return self.getMeterSourceFromAssignment3(assignment, faderIndexNumber, Format::ST);
    } else if matches!(format, Format::SU_5_1) {
      return self.getMeterSourceFromAssignment3(assignment, faderIndexNumber, Format::SU_5_1);
    }
    return self.getMeterSourceFromAssignment2(assignment, faderIndexNumber);
  }

  pub fn getMeterSourceFromAssignment2(&self, assignment: Assignment, faderIndexNumber: i32) -> Option<MeterSource> {
    return self.getMeterSourceFromAssignment3(assignment, faderIndexNumber, Format::NP);
  }

  pub fn getMeterSourceFromAssignment3(
    &self,
    assignment: Assignment,
    faderIndexNumber: i32,
    format: Format
  ) -> Option<MeterSource> {
    match assignment {
      Assignment::FUNC_MAIN_LINE | Assignment::MAIN_DESK_PRE_DELAY | Assignment::FUNC_MAIN_DESK | Assignment::MAIN_LINE_PRE_DELAY => {
        return Some(MeterSource::MAIN);
      }
      Assignment::GROUP_OP => { return Some(MeterSource::GROUP); }
      Assignment::FUNC_AUX_OP | Assignment::AUX_OP => {
        return Some(MeterSource::AUX);
      }
      Assignment::FUNC_TRACK_OP | Assignment::TRACK_OP => {
        return Some(MeterSource::TRACK);
      }
      Assignment::EXTERNAL => { return Some(MeterSource::EXTERNAL_INPUT); }
      Assignment::AFL => {
        if matches!(format, Format::ST) { return Some(MeterSource::AFL_STEREO); } else { return Some(MeterSource::AFL_SURROUND); }
      }
      Assignment::PFL => {
        if matches!(format, Format::ST) { return Some(MeterSource::PFL_STEREO); } else { return Some(MeterSource::PFL_SURROUND); }
      }
      Assignment::APFL => {
        if matches!(format, Format::ST) { return Some(MeterSource::APFL_STEREO); } else { return Some(MeterSource::APFL_SURROUND); }
      }
      Assignment::CRLS_PRE => { return Some(MeterSource::CONSOLE_LS); }
      Assignment::OAC_OP => { return Some(MeterSource::OAC); }
      Assignment::MIX_MINUS => { return Some(MeterSource::MIX_MINUS.getMixMinusMeterSourceByIndex(faderIndexNumber)); }
      Assignment::SEL_METER => {
        if faderIndexNumber == 0 { return Some(MeterSource::USER_METER_1); } else { return Some(MeterSource::USER_METER_2); }
      }
      Assignment::FADER_IP_LVL_N_DYN => { return Some(MeterSource::FADER); }
      Assignment::VIDEO_OVERLAY => { return Some(MeterSource::VIDEO); }
      _ => { return None; }
    }
  }

  pub fn getMeterLabels(
    &self,
    _source: Option<MeterSource>,
    _options: &MeterOptions,
    _layout: MeterLayout,
    _isStaging: bool
  ) -> Vec<MeterLabel>{
    let mut meterLabels = vec![];

    meterLabels.push(MeterLabel{ option: MeterSourceOption::AUDIO, row: 0, column: 0, height: 1, width: 1 });

    meterLabels.push(MeterLabel{ option: MeterSourceOption::DYNAMICS, row: 0, column: 1, height: 1, width: 1 });
    // if options.isDynamics {
    // }

    meterLabels
  }

  fn getMeterLabel(&self, meterOption: MeterSourceOption) -> Option<MeterLabel> {
    let meterLabels = self.getMeterLabels(self.getMeterSource(), self.getOptions(), self.getLayout(), false);

    let meterLabel = meterLabels.iter().find(|label| matches!(label.option, meterOption));

    return meterLabel.cloned().or(None);
  }

  fn getNumberOfBlocks(configurationRows: &'static [i32], row: i32) -> i32{
    for i in 0..configurationRows.len() {
      if configurationRows[i] == row {
        return i as i32;
      }
    }
    return -1;
  }

  pub fn getBlockId(&self) -> i32 {
    let mut row = self.getRow();
    let mut column = self.getColumn();
    if self.isLoudnessMeter() {
      let mut meterLabel: Option<MeterLabel> = None;
      if self.getOptions().isLoudness { meterLabel = self.getMeterLabel(MeterSourceOption::LOUDNESS) };
      if self.getOptions().isTrackPairLoudness { meterLabel = self.getMeterLabel(MeterSourceOption::TRACK_PAIR_LOUDNESS); }
      if self.getOptions().isTrackOddLoudness { meterLabel = self.getMeterLabel(MeterSourceOption::TRACK_ODD_LOUDNESS); }
      if self.getOptions().isTrackEvenLoudness { meterLabel = self.getMeterLabel(MeterSourceOption::TRACK_EVEN_LOUDNESS); }
      if self.getOptions().isDownmixLoudness { meterLabel = self.getMeterLabel(MeterSourceOption::DOWNMIX_LOUDNESS); }
      if self.getOptions().isDownmixSurroundLoudness { meterLabel = self.getMeterLabel(MeterSourceOption::SU_DOWNMIX_LOUDNESS); }

      if meterLabel.is_some() {
        row = row - meterLabel.unwrap().row;
        column = column - meterLabel.unwrap().column;
      }
    } else if self.isLoRoDownmixMeter() || self.isSurroundDownmixMeter() {
      let meterOption = if self.isSurroundDownmixMeter() { MeterSourceOption::SU_DOWNMIX } else { MeterSourceOption::DOWNMIX };
      let meterLabel = self.getMeterLabel(meterOption);
      if meterLabel.is_some() {
        row = row - meterLabel.unwrap().row;
        column = column - meterLabel.unwrap().column;
      }
    } else if self.isPhaseMeter() {
      let meterLabel = self.getMeterLabel(MeterSourceOption::PHASE);
      if meterLabel.is_some() {
        row = row - meterLabel.unwrap().row;
        column = column - meterLabel.unwrap().column;
      }
    }
    return MeterBlockUpdate::cantorize(row, column);
  }

  pub fn getColumn(&self) -> i32{
    let row = self.getMCSRow();
    let column = self.getMCSColumn();

    let mut numberOfBlocks = 0;

    match self.getRow() {
      0 => { numberOfBlocks = MeterBlockUpdate::getNumberOfBlocks(MeterBlockUpdate::ROW_ZERO_CONFIGURATION_ROWS, row) }
      1 => { numberOfBlocks = MeterBlockUpdate::getNumberOfBlocks(MeterBlockUpdate::ROW_ONE_CONFIGURATION_ROWS, row) }
      2 => { numberOfBlocks = MeterBlockUpdate::getNumberOfBlocks(MeterBlockUpdate::ROW_TWO_CONFIGURATION_ROWS, row) }
      3 => { numberOfBlocks = MeterBlockUpdate::getNumberOfBlocks(MeterBlockUpdate::ROW_THREE_CONFIGURATION_ROWS, row) }
      _ => {}
    }

    (numberOfBlocks * MeterBlockUpdate::NO_OF_COLUMNS_IN_A_BLOCK) + column
  }

  pub fn getLabelBgColour(&self) -> MeterLabelBackgroundColour{
    match self.getMeterAssignment() {
      Assignment::SEL_METER | Assignment::UNUSED1 | Assignment::RTB | Assignment::UTILITY_MONITOR_MIXER | Assignment::CRLS_PRE => {}
      _ => {
        if self.getPath().getHasPath() {
          if self.getPath().getIsChannel() { return MeterLabelBackgroundColour::GREY; }
          if self.getPath().getIsMain() { return MeterLabelBackgroundColour::RED; }
          if self.getPath().getIsGroup() { return MeterLabelBackgroundColour::TEAL; }
          if self.getPath().getIsTrack() { return MeterLabelBackgroundColour::BLUE; }
          if self.getPath().getIsAux() { return MeterLabelBackgroundColour::SEA_GREEN; }
        }
      }
    }
    return self.labelBgColour;
  }

  fn getPathLabel(&self) -> String {
    match self.path.pathType() {
      PathType::MN => { return String::from("Main"); }
      PathType::AUX => { return String::from("Aux"); }
      PathType::REM_AUX => { return String::from(format!("{}-Rem Aux", self.remoteHostName)); }
      PathType::TK => {
        if self.getOptions().isDualTrack || self.getOptions().isQuadTrack {
          return String::from("Tracks");
        }
        return String::from("Track");
      }
      PathType::GP => { return String::from("Group"); }
      PathType::EXT => { return String::from("Ext Input"); }
      _ => { return String::new() }
    }
  }

  fn getSystemLabel(&self) -> String {
    let pathLabel = self.getPathLabel();

    if !self.getPath().getHasPath() || pathLabel.len() < 1 {
      if self.getPath().getIsRemoteFader() {
        return format!("{}{}", self.label1.label, self.getRemoteLabel());
      }
      return self.label1.label.clone();
    }

    let mut pathSuffix = (self.getPath().number() + 1).to_string();

    if self.getOptions().isDualTrack {
      pathSuffix = format!("{}-{}", self.getPath().number() + 1, self.getPath().number() + 2);
    } else if self.getOptions().isQuadTrack {
      pathSuffix = format!("{}-{}", self.getPath().number() + 1, self.getPath().number() + 4);
    }

    return format!("{} {}", pathLabel, pathSuffix);
  }

  fn getPreToneTBLabel(&self) -> String {
    return MeterBlockUpdate::getPreToneTBLabel2(self.getMeterAssignment());
  }

  fn getPreToneTBLabel2(meterAssignment: Assignment) -> String {
    if matches!(meterAssignment, Assignment::MAIN_DESK_PRE_DELAY) {
      return "Pre-delay, Tone & TB".into();
    } else if matches!(meterAssignment, Assignment::FUNC_MAIN_DESK) {
      return "Pre-tone & TB".into();
    } else if (
      matches!(meterAssignment, Assignment::MAIN_LINE_PRE_DELAY) ||
      matches!(meterAssignment, Assignment::AUX_OP) ||
      matches!(meterAssignment, Assignment::TRACK_OP)
    ) {
      return "Pre-delay".into();
    }
    String::new()
  }

  pub fn getDisplayLabels(&self) -> MeterBlockLabels {
    let mut labels = MeterBlockLabels::new();
    let systemLabel = self.getSystemLabel();
    let isHector = false;

    match self.getMeterAssignment() {
      Assignment::FUNC_MAIN_LINE |
      Assignment::FUNC_MAIN_DESK |
      Assignment::FUNC_AUX_OP |
      Assignment::FUNC_TRACK_OP |
      Assignment::TRACK_OP |
      Assignment::AUX_OP |
      Assignment::GROUP_OP |
      Assignment::MAIN_LINE_PRE_DELAY |
      Assignment::MAIN_DESK_PRE_DELAY => {
        labels.setLabel(systemLabel.clone());
        if vec![self.label1.labelType, self.label2.labelType, self.label3.labelType].contains(&LabelType::TONE) {
          labels.setLabel("TONE".to_string());
        }
        labels.setPreToneTBPreDelayLabel(self.getPreToneTBLabel());
        if !self.getOptions().isDualTrack && !self.getOptions().isQuadTrack {
          labels.setTapeOrPortLabel(&self.label1, systemLabel);
        }
      }
      Assignment::APFL | Assignment::AFL | Assignment::PFL => {
        if isHector {
          labels.setLabel(self.getLabel1().clone());
        } else {
          labels.setLabel(format!("{} {}", self.getLabel1(), self.getLabel2()));
        }
      }
      Assignment::SEL_METER | Assignment::UNUSED1 | Assignment::RTB | Assignment::UTILITY_MONITOR_MIXER => {
        if self.getMeterSource().is_some() {
          if !isHector && matches!(self.assignment, Assignment::SEL_METER) {
            labels.setTitleLabel(format!("{} {}", self.getMeterSource().unwrap().getFullTitle(), (self.getIndex() + 1)));
          } else {
            labels.setTitleLabel(format!("{} {}", self.getMeterSource().unwrap().getFullTitle(), self.getMeterSource().unwrap().getLabelSuffix()));
          }
        }

        labels.setLabel(self.getLabel2().clone());
      }
      Assignment::CRLS_PRE => {
        labels.setTitleLabel(self.label1.label.clone());
        labels.setLabel(self.getLabel2().clone());
        if !matches!(self.label3.labelType, LabelType::NATIVE) && self.label3.label != "" {
          labels.setTapeOrPortLabel(&self.label3, systemLabel);
        }
      }
      Assignment::OAC_OP => {
        labels.setLabel(self.getLabel1().clone());
      }
      Assignment::MIX_MINUS => {
        labels.setLabel(format!("{} {}", self.getLabel1(), (self.getIndex() + 1)));
      }
      Assignment::EXTERNAL => {
        if !self.isLoudnessMeter() {
          labels.setLabel(systemLabel.clone());
        }
        if !matches!(self.label1.labelType, LabelType::NATIVE) {
          labels.setTapeOrPortLabel(&self.label1, systemLabel);
        }
      }
      _ => {
        labels.setLabel(systemLabel.clone());
        labels.setTapeOrPortLabel(&self.label2, systemLabel);
      }
    }

    labels
  }

  pub fn getLabels(&self) -> Vec<MeterLabel> {
    // TODO Calculate audio format for monitors
    return self.getMeterLabels(
      self.getMeterSourceFromAssignment(self.getMeterAssignment(), self.getIndex(), Format::NP),
      self.getOptions(),
      self.getLayout(),
      false
    );
  }
}
