#![allow(non_snake_case, non_camel_case_types)]
use bytes::{Bytes, Buf};

use crate::klv::helpers::read_string;
use super::enums::labelType::LabelType;

#[derive(Debug)]
pub struct Label {
  pub label: String,
  pub labelType: LabelType, // u8, named type
}

impl Label {
  pub fn read(buf: &mut Bytes) -> Label {
    let label = read_string(buf);
    let labelType = unsafe { ::std::mem::transmute(buf.get_u8()) };

    Label { label, labelType }
  }
}
