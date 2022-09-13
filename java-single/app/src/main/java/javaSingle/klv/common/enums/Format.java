package javaSingle.klv.common.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public enum Format {
  NP,
  M(new LegChannelEnum[] { LegChannelEnum.M }, 1),
  ST(
    new LegChannelEnum[] { LegChannelEnum.L, LegChannelEnum.R, LegChannelEnum.M },
    new SpillLeg[] { SpillLeg.MONO_L, SpillLeg.MONO_R },
    2
  ),
  SU_3_0,
  SU_4_0,
  SU_5_0,
  SU_6_0,
  SU_7_0,
  SU_5_1(
    "5.1",
    new LegChannelEnum[] {
      LegChannelEnum.L,
      LegChannelEnum.R,
      LegChannelEnum.C,
      LegChannelEnum.LFE,
      LegChannelEnum.Ls,
      LegChannelEnum.Rs,
      LegChannelEnum.Lo,
      LegChannelEnum.Ro,
      LegChannelEnum.Mo,
    },
    new SpillLeg[] { SpillLeg.FRONT_LR, SpillLeg.FRONT_C, SpillLeg.LFE, SpillLeg.LR_SURROUND },
    6
  ),
  SU_7_1(
    "7.1",
    new LegChannelEnum[] {
      LegChannelEnum.L,
      LegChannelEnum.R,
      LegChannelEnum.C,
      LegChannelEnum.LFE,
      LegChannelEnum.Lss,
      LegChannelEnum.Rss,
      LegChannelEnum.Lrs,
      LegChannelEnum.Rrs,
    },
    new SpillLeg[] {
      SpillLeg.FRONT_LR,
      SpillLeg.FRONT_C,
      SpillLeg.LFE,
      SpillLeg.LR_SIDE_SURROUND,
      SpillLeg.LR_REAR_SURROUND,
    },
    8,
    0
  ),
  UNUSED_1,
  MO(new LegChannelEnum[] { LegChannelEnum.M }, 1),
  ME(new LegChannelEnum[] { LegChannelEnum.M }, 1),
  SU_1_1,
  SU_2_1,
  SU_3_1,
  SU_4_1,
  UNUSED_2,
  UNUSED_3,
  S(new LegChannelEnum[] { LegChannelEnum.M }, 5), //5.1 downmix
  LORO_ST(new LegChannelEnum[] { LegChannelEnum.Lo, LegChannelEnum.Ro }, new SpillLeg[] { SpillLeg.STEREO_LR }, 2),
  DUAL_M,
  QUAD_M,
  DOWNMIX_ST, //Stereo downmix (LtRt) only exists on old core Apollo and Artemis systems
  MMO,
  DOWNMIX_5_1(
    new LegChannelEnum[] {
      LegChannelEnum.L,
      LegChannelEnum.R,
      LegChannelEnum.C,
      LegChannelEnum.LFE,
      LegChannelEnum.Ls,
      LegChannelEnum.Rs,
    },
    6
  ),
  PAIR_N_N,
  PAIR_N_M,
  PAIR_N_E,
  PAIR_M_N,
  PAIR_M_M,
  PAIR_M_E,
  PAIR_O_N,
  PAIR_O_M,
  PAIR_O_E,
  REMOTE_PLACEHOLDER("REM"),
  UNUSED_4,
  UNUSED_5,
  UNUSED_6,
  UNUSED_7,
  IM_5_1_2(
    "5.1.2",
    new LegChannelEnum[] {
      LegChannelEnum.L,
      LegChannelEnum.R,
      LegChannelEnum.C,
      LegChannelEnum.LFE,
      LegChannelEnum.Ls,
      LegChannelEnum.Rs,
      LegChannelEnum.Ltf,
      LegChannelEnum.Rtf,
    },
    new SpillLeg[] { SpillLeg.FRONT_LR, SpillLeg.FRONT_C, SpillLeg.LFE, SpillLeg.LR_SURROUND, SpillLeg.LR_TOP_FRONT_2 },
    8,
    2
  ),
  IM_5_1_4(
    "5.1.4",
    new LegChannelEnum[] {
      LegChannelEnum.L,
      LegChannelEnum.R,
      LegChannelEnum.C,
      LegChannelEnum.LFE,
      LegChannelEnum.Ls,
      LegChannelEnum.Rs,
      LegChannelEnum.Ltf,
      LegChannelEnum.Rtf,
      LegChannelEnum.Ltr,
      LegChannelEnum.Rtr,
    },
    new SpillLeg[] {
      SpillLeg.FRONT_LR,
      SpillLeg.FRONT_C,
      SpillLeg.LFE,
      SpillLeg.LR_SURROUND,
      SpillLeg.LR_TOP_FRONT_4,
      SpillLeg.LR_TOP_REAR,
    },
    10,
    4
  ),
  IM_7_1_2(
    "7.1.2",
    new LegChannelEnum[] {
      LegChannelEnum.L,
      LegChannelEnum.R,
      LegChannelEnum.C,
      LegChannelEnum.LFE,
      LegChannelEnum.Lss,
      LegChannelEnum.Rss,
      LegChannelEnum.Lrs,
      LegChannelEnum.Rrs,
      LegChannelEnum.Ltf,
      LegChannelEnum.Rtf,
    },
    new SpillLeg[] {
      SpillLeg.FRONT_LR,
      SpillLeg.FRONT_C,
      SpillLeg.LFE,
      SpillLeg.LR_SIDE_SURROUND,
      SpillLeg.LR_REAR_SURROUND,
      SpillLeg.LR_TOP_FRONT_2,
    },
    10,
    2
  ),
  IM_7_1_4(
    "7.1.4",
    new LegChannelEnum[] {
      LegChannelEnum.L,
      LegChannelEnum.R,
      LegChannelEnum.C,
      LegChannelEnum.LFE,
      LegChannelEnum.Lss,
      LegChannelEnum.Rss,
      LegChannelEnum.Lrs,
      LegChannelEnum.Rrs,
      LegChannelEnum.Ltf,
      LegChannelEnum.Rtf,
      LegChannelEnum.Ltb,
      LegChannelEnum.Rtb,
    },
    new SpillLeg[] {
      SpillLeg.FRONT_LR,
      SpillLeg.FRONT_C,
      SpillLeg.LFE,
      SpillLeg.LR_SIDE_SURROUND,
      SpillLeg.LR_REAR_SURROUND,
      SpillLeg.LR_TOP_FRONT_4,
      SpillLeg.LR_TOP_BACK,
    },
    12,
    4
  ),
  IM_0_2(
    "0.0.2",
    new LegChannelEnum[] { LegChannelEnum.Ltf, LegChannelEnum.Rtf },
    new SpillLeg[] { SpillLeg.LR_TOP_FRONT_2 },
    2,
    2
  ),
  IM_0_4(
    "0.0.4",
    new LegChannelEnum[] { LegChannelEnum.Ltf, LegChannelEnum.Rtf, LegChannelEnum.Ltb, LegChannelEnum.Rtb },
    new SpillLeg[] { SpillLeg.LR_TOP_FRONT_4, SpillLeg.LR_TOP_BACK },
    4,
    4
  ),
  // Special format for UI only; Used to indicate a faders' width is not known
  FADER;

  String label;
  LegChannelEnum[] legChannelEnums;
  SpillLeg[] spillLegs;
  int resourceCount = 0;
  int immersiveLegs = 0;

  Format() {
    this.legChannelEnums = null;
    this.spillLegs = null;
  }

  Format(String label) {
    this.label = label;
  }

  Format(String label, SpillLeg[] spillLegs, int resourceCount, int immersiveLegs) {
    this.label = label;
    this.legChannelEnums = null;
    this.spillLegs = spillLegs;
    this.resourceCount = resourceCount;
    this.immersiveLegs = immersiveLegs;
  }

  Format(String label, LegChannelEnum[] legChannelEnums, SpillLeg[] spillLegs, int resourceCount) {
    this.label = label;
    this.legChannelEnums = legChannelEnums;
    this.spillLegs = spillLegs;
    this.resourceCount = resourceCount;
  }

  Format(String label, LegChannelEnum[] legChannelEnums, SpillLeg[] spillLegs, int resourceCount, int immersiveLegs) {
    this.label = label;
    this.legChannelEnums = legChannelEnums;
    this.spillLegs = spillLegs;
    this.resourceCount = resourceCount;
    this.immersiveLegs = immersiveLegs;
  }

  Format(LegChannelEnum[] legChannelEnums, int resourceCount) {
    this.legChannelEnums = legChannelEnums;
    this.spillLegs = null;
    this.resourceCount = resourceCount;
  }

  Format(LegChannelEnum[] legChannelEnums, SpillLeg[] spillLegs, int resourceCount) {
    this.legChannelEnums = legChannelEnums;
    this.spillLegs = spillLegs;
    this.resourceCount = resourceCount;
  }

  public static Format findByLabel(String label, boolean isSpillFader) {
    AtomicReference<String> lookupLabel = new AtomicReference<>(label);
    if (label.equals("M/O")) lookupLabel.set("MO");
    if (label.equals("M/E")) lookupLabel.set("ME");

    return Arrays
      .stream(Format.values())
      .filter(format -> format.getLabel().equals(lookupLabel.get()))
      .findFirst()
      .orElse(isSpillFader ? Format.FADER : Format.NP);
  }

  public String getLabel() {
    return label == null ? toString() : label;
  }

  public List<LegChannelEnum> getLegChannelEnums() {
    return legChannelEnums == null ? new ArrayList<>() : Arrays.asList(legChannelEnums);
  }

  public List<LegChannelEnum> getLegChannelEnumsNoDownmix() {
    List<LegChannelEnum> legs = legChannelEnums == null ? new ArrayList<>() : Arrays.asList(legChannelEnums);
    List<LegChannelEnum> subset = new ArrayList<>();

    subset.addAll(legs.subList(0, getResourceCount()));

    return subset;
  }

  public Optional<LegChannelEnum> getLegChannelEnum(Integer index) {
    List<LegChannelEnum> legs = getLegChannelEnums();
    if (legs.size() == 0 || index >= legs.size()) return Optional.empty();
    return Optional.ofNullable(legs.get(index));
  }

  public List<SpillLeg> getSpillLegs() {
    return spillLegs == null ? new ArrayList<>() : Arrays.asList(spillLegs);
  }

  /**
   * Return number of spill legs
   */
  public int getResourceCount() {
    return resourceCount;
  }

  /**
   * Return number of immersive legs
   */
  public int getImmersiveLegCount() {
    return immersiveLegs;
  }
}
