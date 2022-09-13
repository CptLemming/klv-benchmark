#![allow(non_camel_case_types)]

#[derive(Debug)]
pub enum DynamicsMode {
  NO_DYN,
  DYN1,
  DYN2,
  DYN1_N_2,
  LVL_N_DYN1,
  LVL_N_DYN2,
  LVL_N_DYN1_OR_2,
  DYN1_OR_2,
  LVL_DYN1_N_DYN2,
  LVL_DYN1_N_AMIX,
  DYN1_N_DYN2,
  DYN1_N_AMIX,
}
