from gql.common.enums.modelMutation import ModelMutation
from gql.meters.enums.meterBarType import MeterBarType
from gql.meters.meterBar import MeterBar
from gql.meters.meterBlock import MeterBlock
from klv.commands.meterDisplayBlockUpdate import MeterDisplayBlockUpdate
from klv.common.enums.format import Format
from klv.common.enums.meterScale import MeterScale
from klv.common.pathId import PathId


class Meters:
  def __init__(self, meterBlocks):
    self.meterBlocks = meterBlocks

  @staticmethod
  def get_path(path: PathId, format: Format) -> str:
    if path.to_string() == "" or (path.getIsRemote() and format is Format.NP):
      return ""
    return path.to_string()

  @staticmethod
  def parse(meter_display_block_update: MeterDisplayBlockUpdate):
    meter_blocks = []

    for meter_block_update in meter_display_block_update.updates:
      immersiveLegs = meter_block_update.getImmersiveLegsCount()
      legLabels = meter_block_update.getLegLabels()

      meters = []
      for index, timeslot in enumerate(meter_block_update.timeslots):
        meters.append(
          MeterBar(
            id=timeslot,
            meterType=MeterBarType.CHANNEL_INPUT,
            label=legLabels[index],
            scale=MeterScale.VU_8_20,
            isActive=False,
            isCut=False,
            timeslots= [timeslot],
            meterPacketType=int(MeterBarType.CHANNEL_INPUT),
          )
        )

      numDynTimeslots = 1 if len(meter_block_update.dynamicTimeslots) else 0
      dynamics1Meters = []

      for timeslot in meter_block_update.dynamicTimeslots:
        dynamics1Meters.append(
          MeterBar(
            id=timeslot,
            meterType=MeterBarType.CHANNEL_DYNAMICS_1_GAIN,
            label="E",
            scale=MeterScale.VU_8_20,
            isActive=False,
            isCut=False,
            timeslots=[timeslot],
            meterPacketType=int(MeterBarType.CHANNEL_DYNAMICS_1_GAIN),
          )
        )

      dynamics2Meters = []
      for timeslot in meter_block_update.dynamicTimeslots:
        dynamics2Meters.append(
          MeterBar(
            id=timeslot,
            meterType=MeterBarType.CHANNEL_DYNAMICS_1_REDUCTION,
            label="C",
            scale=MeterScale.VU_8_20,
            isActive=False,
            isCut=False,
            timeslots=[timeslot],
            meterPacketType=int(MeterBarType.CHANNEL_DYNAMICS_1_REDUCTION),
          )
        )

      meter_blocks.append(
        MeterBlock(
          mutation=ModelMutation.UPDATED,
          index=meter_block_update.getBlockId(),
          row=meter_block_update.getRow(),
          column=meter_block_update.getColumn(),
          width=meter_block_update.getBlockWidth(),
          height=meter_block_update.getBlockHeight(),
          layout=meter_block_update.layout,
          options=meter_block_update.options,
          meterLabels=meter_block_update.getLabels(),
          colour=meter_block_update.getLabelBgColour(),
          labels=meter_block_update.getDisplayLabels(),
          path=Meters.get_path(meter_block_update.getPath(), meter_block_update.getFormat()),
          isFader=meter_block_update.isChannelMeter(),
          channelIndex=meter_block_update.getChannelIndex(),
          layer=meter_block_update.getSubLayer(),
          format=meter_block_update.getFormat(),
          # These fields are set in another phase
          # meters: vec![MeterBar{ id: 0, meterType: MeterBarType::CHANNEL_INPUT, label: "L".to_string(), scale: MeterScale::VU_8_20, isActive: false, isCut: false, timeslots: vec![0], meterPacketType: 0 }],
          meters=meters[:(len(meters) - immersiveLegs)],
          dynamics1Meters=dynamics1Meters[:numDynTimeslots],
          dynamics2Meters=dynamics2Meters[:numDynTimeslots],
          immersiveMeters=meters[(len(meters) - immersiveLegs):],
          loudnessMeters=[],
          downmixMeters=[],
          surroundDownmixMeters=[],
          phaseMeters=[],
          videoMeters=[],
          autoFader=None,
          loudnessFormat=Format.NP,
          isCurrentAssignedFader=False,
        )
      )

    Meters(meterBlocks=meter_blocks)
