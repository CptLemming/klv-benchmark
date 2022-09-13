package javaSingle.gql.meters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javaSingle.gql.common.enums.ModelMutation;
import javaSingle.gql.meters.enums.MeterBarType;
import javaSingle.klv.commands.MeterDisplayBlockUpdate;
import javaSingle.klv.common.MeterBlockUpdate;
import javaSingle.klv.common.PathId;
import javaSingle.klv.common.enums.Format;
import javaSingle.klv.common.enums.MeterScale;

public class Meters {
  public List<MeterBlock> meterBlocks;

  public Meters(List<MeterBlock> meterBlocks) {
    this.meterBlocks = meterBlocks;
  }

  public static Meters from(MeterDisplayBlockUpdate meterDisplayBlockUpdate) {
    List<MeterBlock> meterBlocks = new ArrayList<>();

    for (MeterBlockUpdate meterBlockUpdate : meterDisplayBlockUpdate.updates) {
      var immersiveLegs = meterBlockUpdate.getImmersiveLegsCount();
      var legLabels = meterBlockUpdate.getLegLabels();

      List<MeterBar> meters = new ArrayList<>();
      for (int index = 0; index < meterBlockUpdate.timeslots.size(); index++) {
        var timeslot = meterBlockUpdate.timeslots.get(index);
        meters.add(
          new MeterBar(
            timeslot,
            MeterBarType.CHANNEL_INPUT,
            legLabels.get(index),
            MeterScale.VU_8_20,
            false,
            false,
            Collections.singletonList(timeslot),
            MeterBarType.CHANNEL_INPUT.ordinal()
          )
        );
      }

      var numDynTimeslots = meterBlockUpdate.dynamicTimeslots.size() > 0 ? 1 : 0;
      var dynamics1Meters = new ArrayList<>();

      for (Integer timeslot : meterBlockUpdate.dynamicTimeslots) {
        dynamics1Meters.add(
          new MeterBar(
            timeslot,
            MeterBarType.CHANNEL_DYNAMICS_1_GAIN,
            "E",
            MeterScale.VU_8_20,
            false,
            false,
            Collections.singletonList(timeslot),
            MeterBarType.CHANNEL_DYNAMICS_1_GAIN.ordinal()
          )
        );
      }

      var dynamics2Meters = new ArrayList<>();
      for (Integer timeslot : meterBlockUpdate.dynamicTimeslots) {
        dynamics2Meters.add(
          new MeterBar(
            timeslot,
            MeterBarType.CHANNEL_DYNAMICS_1_REDUCTION,
            "C",
            MeterScale.VU_8_20,
            false,
            false,
            Collections.singletonList(timeslot),
            MeterBarType.CHANNEL_DYNAMICS_1_REDUCTION.ordinal()
          )
        );
      }

      // meterBlocks.add(
      //   new MeterBlock(
      //     ModelMutation.UPDATED,
      //     meterBlockUpdate.getBlockId(),
      //     meterBlockUpdate.getRow(),
      //     meterBlockUpdate.getColumn(),
      //     meterBlockUpdate.getBlockWidth(),
      //     meterBlockUpdate.getBlockHeight(),
      //     meterBlockUpdate.layout,
      //     meterBlockUpdate.options,
      //     meterBlockUpdate.getLabels(),
      //     meterBlockUpdate.getLabelBgColour(),
      //     meterBlockUpdate.getDisplayLabels(),
      //     Meters.getPath(meterBlockUpdate.getPath(), meterBlockUpdate.getFormat()),
      //     meterBlockUpdate.isChannelMeter(),
      //     meterBlockUpdate.isChannelMeter() ? meterBlockUpdate.index + 1 : 0,
      //     meterBlockUpdate.getSubLayer(),
      //     meterBlockUpdate.getFormat(),
      //     // These fields are set in another phase
      //     // meters: vec![MeterBar{ id: 0, meterType: MeterBarType::CHANNEL_INPUT, label: "L".to_string(), scale: MeterScale::VU_8_20, isActive: false, isCut: false, timeslots: vec![0], meterPacketType: 0 }],
      //     meters.subList(0, meters.size() - immersiveLegs),
      //     dynamics1Meters.subList(numDynTimeslots, dynamics1Meters.size()),
      //     dynamics2Meters.subList(numDynTimeslots, dynamics2Meters.size()),
      //     meters.subList(meters.size() - immersiveLegs, meters.size()),
      //     null,
      //     null,
      //     null,
      //     null,
      //     null,
      //     null,
      //     Format.NP,
      //     false
      //   )
      // );

      meterBlocks.add(
        new MeterBlock(
          ModelMutation.UPDATED,
          meterBlockUpdate.getBlockId(),
          meterBlockUpdate.getRow(),
          meterBlockUpdate.getColumn(),
          meterBlockUpdate.getBlockWidth(),
          meterBlockUpdate.getBlockHeight(),
          meterBlockUpdate.getLabelBgColour(),
          meterBlockUpdate.getDisplayLabels(),
          null, // Populated by buildMeterBars during the second pass
          null, // Populated by buildMeterBars during the second pass
          null, // Populated by buildMeterBars during the second pass
          null, // Populated by buildMeterBars during the second pass
          null, // Populated by buildMeterBars during the second pass
          null, // Populated by buildMeterBars during the second pass
          null, // Populated by buildMeterBars during the second pass
          meterBlockUpdate.getLabels(),
          getPath(meterBlockUpdate.getPath(), meterBlockUpdate.getFormat()),
          meterBlockUpdate.isChannelMeter(),
          null, // Set post build of MeterBlock list
          meterBlockUpdate.layout,
          meterBlockUpdate.options,
          meterBlockUpdate.isChannelMeter() ? meterBlockUpdate.index + 1 : 0,
          meterBlockUpdate.getSubLayer(),
          meterBlockUpdate.getFormat(),
          null, // Set post build of MeterBlock list
          null // Set post build of MeterBlock list
        )
      );
    }

    return new Meters(meterBlocks);
  }

  private static String getPath(PathId path, Format format) {
    if (path.toString().equals("") || (path.getIsRemote() && format.equals(Format.NP))) {
      return "";
    }
    return path.toString();
  }
}
