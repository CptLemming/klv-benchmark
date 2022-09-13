#![allow(non_snake_case)]
use bytes::{Bytes, Buf};
use crate::klv::common::meterBlockUpdate::MeterBlockUpdate;

#[derive(Debug)]
pub struct MeterDisplayBlockUpdate {
  pub target: u8,
  pub updates: Vec<MeterBlockUpdate>,
  pub isSnapshot: bool,
}

impl MeterDisplayBlockUpdate {
  pub fn read(buf: &mut Bytes) -> MeterDisplayBlockUpdate {
    let target = buf.get_u8();
    let numUpdates = buf.get_u32_le();
    let mut updates = vec![];

    for _ in 0..numUpdates {
      updates.push(MeterBlockUpdate::read(buf));
    }

    let isSnapshot = buf.get_u8() != 0;
    MeterDisplayBlockUpdate {
      target,
      updates,
      isSnapshot,
    }
  }
}
