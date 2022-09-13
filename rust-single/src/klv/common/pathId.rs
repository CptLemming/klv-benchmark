use bytes::{Bytes, Buf};

use super::enums::pathType::PathType;

#[derive(Debug)]
pub struct PathId {
  raw: u32,
}

impl PathId {
  const NONE: &'static str = "U/65535/0";
  const CURRENT: &'static str = "U/65535/1";

  pub fn read(buf: &mut Bytes) -> PathId {
    let raw = buf.get_u32_le();

    PathId { raw }
  }

  pub fn pathType(&self) -> PathType {
    if self.raw >> 24 > 0 { unsafe { ::std::mem::transmute(self.raw >> 24) } } else { PathType::U }
  }

  pub fn number(&self) -> u16 {
    if self.raw >> 24 > 0 { self.raw as u16 >> 8 & 0x0000ffff } else {  0xffff }
  }

  pub fn component(&self) -> u8 {
    if self.raw >> 24 > 0 { self.raw as u8 & 0x000000ff } else { 0 }
  }

  pub fn getHasPath(&self) -> bool {
    return !self.to_string().is_empty();
  }

  pub fn getIsChannel(&self) -> bool {
    matches!(self.pathType(), PathType::CH)
  }

  pub fn getIsAux(&self) -> bool {
    matches!(self.pathType(), PathType::AUX)
  }

  pub fn getIsMain(&self) -> bool {
    matches!(self.pathType(), PathType::MN)
  }

  pub fn getIsGroup(&self) -> bool {
    matches!(self.pathType(), PathType::GP)
  }

  pub fn getIsMixMinus(&self) -> bool {
    matches!(self.pathType(), PathType::MM)
  }

  pub fn getIsTrack(&self) -> bool {
    matches!(self.pathType(), PathType::TK)
  }

  pub fn getIsDirectOutput(&self) -> bool {
    matches!(self.pathType(), PathType::DIR)
  }

  pub fn getIsVCAMaster(&self) -> bool {
    matches!(self.pathType(), PathType::VCA)
  }

  pub fn getIsRemoteFader(&self) -> bool {
    matches!(self.pathType(), PathType::REM_FADER)
  }

  pub fn getIsRemoteAux(&self) -> bool {
    matches!(self.pathType(), PathType::REM_AUX)
  }

  pub fn getIsRemote(&self) -> bool {
    self.getIsRemoteFader() || self.getIsRemoteAux()
  }

  pub fn to_string(&self) -> String {
    let path = format!("{:?}/{}/{}", self.pathType(), self.number(), self.component());

    if path == PathId::NONE || path == PathId::CURRENT { String::new() } else { path }
  }
}
