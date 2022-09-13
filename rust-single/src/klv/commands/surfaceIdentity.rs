use bytes::{BytesMut, BufMut};
use crate::klv::common::{identityCommon::IdentityCommon, enums::layoutId::LayoutID};

// Key = 1005
#[derive(Debug)]
pub struct SurfaceIdentity {
  pub common: IdentityCommon,
  pub layout: LayoutID,
  pub hid: u32,
}

impl SurfaceIdentity {
  pub fn write(self, buf: &mut BytesMut) -> &mut BytesMut {
    let mut klv_header = 0;
    klv_header |= 5 << 27;
    klv_header |= 0 << 22;
    klv_header |= 1005 << 0;

    buf.put_u32_le(klv_header);
    buf.put_u32_le(14); // length
    self.common.write(buf);
    buf.put_u16_le(LayoutID::IVOR_UPSTAND as u16);
    buf.put_u32_le(1);

    buf
  }
}
