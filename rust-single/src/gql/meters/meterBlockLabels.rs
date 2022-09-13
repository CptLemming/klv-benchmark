#![allow(non_snake_case)]

use crate::klv::common::{label::Label, enums::labelType::LabelType};


#[derive(Debug, Clone)]
pub struct MeterBlockLabels {
  pub titleLabel: String,
  pub tapeLabel: String,
  pub label: String,
  pub preToneTBPreDelayLabel: String,
  pub portLabel: String,
}

impl MeterBlockLabels {
  pub fn new() -> MeterBlockLabels {
    MeterBlockLabels { titleLabel: String::new(), tapeLabel: String::new(), label: String::new(), preToneTBPreDelayLabel: String::new(), portLabel: String::new() }
  }

  pub fn setTitleLabel(&mut self, titleLabel: String) {
    self.titleLabel = titleLabel;
  }

  pub fn setLabel(&mut self, label: String) {
    self.label = label;
  }

  pub fn setPreToneTBPreDelayLabel(&mut self, preToneTBPreDelayLabel: String) {
    self.preToneTBPreDelayLabel = preToneTBPreDelayLabel;
  }

  pub fn setTapeOrPortLabel(&mut self, typedLabel: &Label, systemLabel: String) {
    if matches!(typedLabel.labelType, LabelType::FADER_USER) {
      self.tapeLabel = typedLabel.label.clone();
    } else if typedLabel.label != "" && systemLabel != typedLabel.label {
      self.portLabel = typedLabel.label.clone();
    }
  }
}
