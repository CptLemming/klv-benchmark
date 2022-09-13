use crate::klv::common::enums::meterSourceOption::MeterSourceOption;

#[derive(Debug, Clone, Copy)]
pub struct MeterLabel {
  pub option: MeterSourceOption,
  pub row: i32,
  pub column: i32,
  pub height: i32,
  pub width: i32,
}
