#![allow(non_snake_case)]

use crate::klv::common::enums::meterScale::MeterScale;

use super::enums::meterBarType::MeterBarType;

#[derive(Debug, Clone)]
pub struct MeterBar {
  pub id: i32,
  pub meterType: MeterBarType,
  pub label: String,
  pub scale: MeterScale,
  pub isActive: bool,
  pub isCut: bool,
  pub timeslots: Vec<i32>,
  pub meterPacketType: i32,
}
