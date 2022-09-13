#![allow(non_snake_case, non_camel_case_types)]

#[repr(u32)]
#[derive(Debug, Clone, Copy, Eq, PartialEq)]
pub enum Format {
  NP,
  M,
  ST,
  SU_3_0,
  SU_4_0,
  SU_5_0,
  SU_6_0,
  SU_7_0,
  SU_5_1,
  SU_7_1,
  UNUSED_1,
  MO,
  ME,
  SU_1_1,
  SU_2_1,
  SU_3_1,
  SU_4_1,
  UNUSED_2,
  UNUSED_3,
  S,
  LORO_ST,
  DUAL_M,
  QUAD_M,
  DOWNMIX_ST, //Stereo downmix (LtRt) only exists on old core Apollo and Artemis systems
  MMO,
  DOWNMIX_5_1,
  PAIR_N_N,
  PAIR_N_M,
  PAIR_N_E,
  PAIR_M_N,
  PAIR_M_M,
  PAIR_M_E,
  PAIR_O_N,
  PAIR_O_M,
  PAIR_O_E,
  REMOTE_PLACEHOLDER,
  UNUSED_4,
  UNUSED_5,
  UNUSED_6,
  UNUSED_7,
  IM_5_1_2,
  IM_5_1_4,
  IM_7_1_2,
  IM_7_1_4,
  IM_0_2,
  IM_0_4,
}

impl Format {
  pub fn getImmersiveLegCount(self) -> i32 {
    match self {
      Format::IM_5_1_2 | Format::IM_7_1_2 | Format::IM_0_2 => 2,
      Format::IM_5_1_4 | Format::IM_7_1_4 | Format::IM_0_4 => 4,
      _ => 0,
    }
  }
}
