use bytes::{BytesMut, BufMut};
use super::enums::deviceType::DeviceType;


#[derive(Debug)]
pub struct IdentityCommon {
  pub deviceType: DeviceType,
  pub instance: u32,
}

impl IdentityCommon {
  pub fn write(self, buf: &mut BytesMut) -> &mut BytesMut {
    buf.put_u32_le(DeviceType::PANEL_GEODE as u32);
    buf.put_u32_le(0);
    buf
  }
}
