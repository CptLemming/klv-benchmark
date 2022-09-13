#![allow(non_camel_case_types)]

#[derive(Debug, Clone, Copy, Eq, PartialEq)]
pub enum MeterBarType {
  CHANNEL_INPUT,
  BUSS_ASSIGNED_INPUT,
  BUSS_INPUTS,
  CHANNEL_DYNAMICS_1_REDUCTION,
  BUSS_DYNAMICS_1_REDUCTION,
  CHANNEL_DYNAMICS_1_GAIN,
  BUSS_DYNAMICS_1_GAIN,
  CHANNEL_DYNAMICS_2_REDUCTION,
  BUSS_DYNAMICS_2_REDUCTION,
  CHANNEL_DYNAMICS_2_GAIN,
  BUSS_DYNAMICS_2_GAIN,
  CHANNEL_AUTOMIXER,
  BUS_AUTOMIXER,
  LOUDNESS,
  SLOW_LOUDNESS,
  DOWNMIX,
  CHANNEL_DEESSER_DETECTION,
  DIRECT_OUTPUTS_DYNAMICS_1,
  DIRECT_OUTPUTS_DYNAMICS_2,
  MIX_MINUS_DYNAMICS_1,
  MIX_MINUS_DYNAMICS_2,
  DYNAMICS_1_SIDECHAIN,
  DYNAMICS_2_SIDECHAIN,
  STEREO_PHASE_METER,
  IVOR_TEST_CHANNEL_INPUT,
  CHANNEL_INPUT_UPSTAND,
}