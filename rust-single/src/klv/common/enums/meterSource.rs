#![allow(non_snake_case, non_camel_case_types)]

#[derive(Debug, Copy, Clone)]
pub enum MeterSource {
  MAIN,
  GROUP,
  AUX,
  TRACK,
  EXTERNAL_INPUT,
  MIX_MINUS,
  AFL_STEREO,
  AFL_SURROUND,
  PFL_STEREO,
  PFL_SURROUND,
  APFL_STEREO,
  APFL_SURROUND,
  CONSOLE_LS,
  OAC,
  USER_METER_1,
  USER_METER_2,
  FADER,
  VIDEO,
}

impl MeterSource {
  pub fn getFullTitle(self) -> String {
    "TODO".into()
  }

  pub fn getLabelSuffix(self) -> String {
    "TODO".into()
  }
  
  pub fn isMixMinusSource(self) -> bool {
    matches!(self, MeterSource::MIX_MINUS)
  }

  pub fn getMixMinusMeterSourceByIndex(self, _faderIndexNumber: i32) -> MeterSource {
    if self.isMixMinusSource() {
      // TODO
      // this.fullTitle = "Mix Minus" + " " + index;
      // this.index = index;
    }
    return self.clone();
  }
}