#![allow(non_camel_case_types)]

#[derive(Debug)]
pub enum MeterStyle {
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
  VIDEO_INPUT_1,
  VIDEO_INPUT_2,
}