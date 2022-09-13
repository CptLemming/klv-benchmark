#![allow(non_snake_case)]

use crate::{klv::common::{enums::{format::Format, meterLabelBackgroundColour::MeterLabelBackgroundColour, meterLayout::MeterLayout}, meterOptions::MeterOptions}, gql::common::enums::modelMutation::ModelMutation};

use super::{meterBlockLabels::MeterBlockLabels, meterBar::MeterBar, videoMeter::VideoMeter, meterLabel::MeterLabel};


#[derive(Debug, Clone)]
pub struct MeterBlock {
  pub mutation: ModelMutation,
  pub index: i32,
  pub row: i32,
  pub column: i32,
  pub width: i32,
  pub height: i32,
  pub layout: MeterLayout,
  pub options: MeterOptions,
  pub meters: Vec<MeterBar>,
  pub immersiveMeters: Vec<MeterBar>,
  pub dynamics1Meters: Vec<MeterBar>,
  pub dynamics2Meters: Vec<MeterBar>,
  pub loudnessMeters: Vec<MeterBar>,
  pub downmixMeters: Vec<MeterBar>,
  pub surroundDownmixMeters: Vec<MeterBar>,
  pub phaseMeters: Vec<MeterBar>,
  pub videoMeters: Vec<VideoMeter>,
  pub meterLabels: Vec<MeterLabel>,
  pub colour: MeterLabelBackgroundColour,
  pub labels: MeterBlockLabels,
  pub path: String,
  pub isFader: bool,
  pub autoFader: Option<i32>,
  pub channelIndex: i32,
  pub layer: String,
  pub format: Format,
  pub loudnessFormat: Format,
  pub isCurrentAssignedFader: bool,
}

impl MeterBlock {
  pub fn set_mutation(&mut self, mutation: ModelMutation) {
    self.mutation = mutation;
  }
}
