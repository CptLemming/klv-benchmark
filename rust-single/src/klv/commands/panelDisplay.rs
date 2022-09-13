use bytes::{Bytes};
use crate::klv::common::{enums::layoutId::LayoutID, identityCommon::IdentityCommon};

// Key = 1001
#[derive(Debug)]
pub struct PanelDisplay {
  pub common: IdentityCommon,
  pub layout: LayoutID,
}

impl PanelDisplay {
  pub fn write(buf: &mut Bytes) -> &mut Bytes {

    buf
  }
}
