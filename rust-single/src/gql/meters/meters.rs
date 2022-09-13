#![allow(non_snake_case)]

use crate::{
  klv::{
    commands::meterDisplayBlockUpdate::MeterDisplayBlockUpdate,
    common::{
      pathId::PathId,
      enums::{format::Format, meterScale::MeterScale},
    },
  },
  gql::common::enums::modelMutation::ModelMutation,
};

use super::{meterBlock::MeterBlock, meterBar::MeterBar, enums::meterBarType::MeterBarType};


#[derive(Debug)]
pub struct Meters {
  pub meterBlocks: Vec<MeterBlock>,
}

impl Meters {
  pub fn get_path(path: &PathId, format: Format) -> String {
    if path.to_string().is_empty() || (path.getIsRemote() && matches!(format, Format::NP)) {
      return String::new();
    }
    return path.to_string();
  }
}

impl From<MeterDisplayBlockUpdate> for Meters {
    fn from(meter_display_block_update: MeterDisplayBlockUpdate) -> Self {
      let meter_blocks: Vec<MeterBlock> = meter_display_block_update.updates.iter().map(|meter_block_update| {
        let immersiveLegs = meter_block_update.getImmersiveLegsCount() as usize;
        let legLabels = meter_block_update.getLegLabels();

        let meters: Vec<MeterBar> = meter_block_update.timeslots.iter().enumerate().map(|(index, timeslot)| {
          MeterBar {
            id: timeslot.clone().into(),
            meterType: MeterBarType::CHANNEL_INPUT,
            label: legLabels.get(index).unwrap().clone(),
            scale: MeterScale::VU_8_20,
            isActive: false,
            isCut: false,
            timeslots: vec![timeslot.clone().into()],
            meterPacketType: MeterBarType::CHANNEL_INPUT as i32,
          }
        }).collect();

        let numDynTimeslots = if meter_block_update.dynamicTimeslots.len() > 0 { 1 } else { 0 };

        let dynamics1Meters: Vec<MeterBar> = meter_block_update.dynamicTimeslots.iter().map(|timeslot| {
          MeterBar {
            id: timeslot.clone().into(),
            meterType: MeterBarType::CHANNEL_DYNAMICS_1_GAIN,
            label: "E".into(),
            scale: MeterScale::VU_8_20,
            isActive: false,
            isCut: false,
            timeslots: vec![timeslot.clone().into()],
            meterPacketType: MeterBarType::CHANNEL_DYNAMICS_1_GAIN as i32,
          }
        }).collect();

        let dynamics2Meters: Vec<MeterBar> = meter_block_update.dynamicTimeslots.iter().map(|timeslot| {
          MeterBar {
            id: timeslot.clone().into(),
            meterType: MeterBarType::CHANNEL_DYNAMICS_1_REDUCTION,
            label: "C".into(),
            scale: MeterScale::VU_8_20,
            isActive: false,
            isCut: false,
            timeslots: vec![timeslot.clone().into()],
            meterPacketType: MeterBarType::CHANNEL_DYNAMICS_1_REDUCTION as i32,
          }
        }).collect();

        MeterBlock {
          mutation: ModelMutation::UPDATED,
          index: meter_block_update.getBlockId(),
          row: meter_block_update.getRow(),
          column: meter_block_update.getColumn(),
          width: meter_block_update.getBlockWidth(),
          height: meter_block_update.getBlockHeight(),
          layout: meter_block_update.layout,
          options: meter_block_update.options.clone(),
          meterLabels: meter_block_update.getLabels(),
          colour: meter_block_update.getLabelBgColour(),
          labels: meter_block_update.getDisplayLabels(),
          path: Meters::get_path(meter_block_update.getPath(), meter_block_update.getFormat()),
          isFader: meter_block_update.isChannelMeter(),
          channelIndex: meter_block_update.getChannelIndex(),
          layer: meter_block_update.getSubLayer().clone(),
          format: meter_block_update.getFormat(),
          // These fields are set in another phase
          // meters: vec![MeterBar{ id: 0, meterType: MeterBarType::CHANNEL_INPUT, label: "L".to_string(), scale: MeterScale::VU_8_20, isActive: false, isCut: false, timeslots: vec![0], meterPacketType: 0 }],
          meters: meters[..(meters.len() - immersiveLegs)].into(),
          dynamics1Meters: dynamics1Meters[..numDynTimeslots].into(),
          dynamics2Meters: dynamics2Meters[..numDynTimeslots].into(),
          immersiveMeters: meters[(meters.len() - immersiveLegs)..].into(),
          loudnessMeters: vec![],
          downmixMeters: vec![],
          surroundDownmixMeters: vec![],
          phaseMeters: vec![],
          videoMeters: vec![],
          autoFader: None,
          loudnessFormat: Format::NP,
          isCurrentAssignedFader: false,
        }
      }).collect();

      Self { meterBlocks: meter_blocks }
    }
}
