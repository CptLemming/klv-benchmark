use bytes::{BytesMut, BufMut};

// Key = 229
#[derive(Debug)]
pub struct ResendMeterData {}

impl ResendMeterData {
  pub fn write(self, buf: &mut BytesMut) -> &mut BytesMut {
    let mut klv_header = 0;
    klv_header |= 5 << 27;
    klv_header |= 0 << 22;
    klv_header |= 229 << 0;

    buf.put_u32_le(klv_header);
    buf.put_u32_le(0); // length

    buf
  }
}
