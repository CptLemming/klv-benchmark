#![allow(non_snake_case, non_camel_case_types)]
use bytes::{Bytes, Buf};

pub fn read_string(buf: &mut Bytes) -> String {
  let strLength = buf.get_u32_le();
  let mut strBytes = vec![];

  for _ in 0..strLength {
    strBytes.push(buf.get_u8());
  };

  return String::from_utf8_lossy(&strBytes).into();
}
