#![allow(non_camel_case_types)]

#[repr(u32)]
#[derive(Debug)]
pub enum PathType {
  U,
  CH,
  GP,
  MN,
  TK,
  AUX,
  AFL,
  PFL,
  VCA,
  DIR,
  EXT,
  MM,
  AUT,
  MMO,
  OAC,
  REM_FADER,
  REM_AUX,
}