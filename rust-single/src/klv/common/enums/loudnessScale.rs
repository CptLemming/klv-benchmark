#![allow(non_camel_case_types)]

#[derive(Debug)]
pub enum LoudnessScale {
  LOUDNESS_SCALE_REL_P9,
  LOUDNESS_SCALE_REL_P18,
  LOUDNESS_SCALE_ABS_P9,
  LOUDNESS_SCALE_ABS_P18,
  LOUDNESS_SCALE_COUNT,
}