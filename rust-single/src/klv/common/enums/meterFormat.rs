#![allow(non_camel_case_types)]

#[derive(Debug)]
pub enum MeterFormat {
  SURROUND_STEREO_OR_MONO,
  STEREO_LO_RO,
  MS,
  STEREO_PHASE,
  MONO,
  STEREO,
  SURROUND,
  MF_UNDEFINED,
  DOWNMIX_5_1,
}