#![allow(non_camel_case_types)]

use std::fmt;

#[derive(Debug, Copy, Clone)]
pub enum Assignment {
  SEL_METER,
  UNUSED1,
  RTB,
  UTILITY_MONITOR_MIXER,
  PFL,
  AFL,
  APFL,
  CRLS_PRE,
  MIX_MINUS,
  TRACK_OP,
  AUX_OP,
  EXTERNAL,
  FADER_IP,
  FADER_IP_DYN1,
  FADER_IP_DYN2,
  FADER_IP_DYN1_N_2,
  FADER_IP_LVL_N_DYN,
  GROUP_OP,
  MAIN_DESK_PRE_DELAY,
  MAIN_LINE_PRE_DELAY,
  MAIN_LINE_USA_PRE_DELAY,
  ROUTING_DISPLAY,
  EQ_DISPLAY,
  DYN_1_DISPLAY,
  DYN_2_DISPLAY,
  PAN_DISPLAY,
  TRACK_DISPLAY,
  FUNC_MAIN_DESK,
  FUNC_MAIN_LINE,
  FUNC_MAIN_LINE_USA,
  FUNC_AUX_OP,
  FUNC_TRACK_OP,
  OAC_OP,
  VIDEO_OVERLAY,
  UNASSIGNED,
}

impl fmt::Display for Assignment {
  fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
      write!(f, "{:?}", self)
      // or, alternatively:
      // fmt::Debug::fmt(self, f)
  }
}
