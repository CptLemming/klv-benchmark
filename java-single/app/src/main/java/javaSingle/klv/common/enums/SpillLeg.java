package javaSingle.klv.common.enums;


public enum SpillLeg {
  UNK(0, "Full path", 0),
  MONO(0, "Mono"),
  MONO_L(1, "L"), // ST width
  MONO_R(2, "R"), // ST width
  STEREO_LR(1, "L/R", 2), // 5.1 width
  SU_MASTER("Surround", 6),
  FRONT_LR(1, "LR", 2),
  FRONT_C(4, "C"),
  LFE(5, "LFE"),
  LR_SURROUND(6, "Ls/Rs", 2),
  LR_SIDE_SURROUND(6, "Lss/Rss", 2),
  LR_REAR_SURROUND(7, "Lrs/Rrs", 2), //used in 7.1, 7.1.2, 7.1.4 width
  LR_TOP_FRONT_4(9, "Ltf/Rtf", 2, true), //used in 5.1.4, 7.1.4 width
  LR_TOP_FRONT_2(10, "Ltf/Rtf", 2, true), //used in 5.1.2, 7.1.2 width
  LR_TOP_BACK("Ltb/Rtb", 2, true),
  LR_TOP_REAR(12, "Ltr/Rtr", 2, true),
  MONO_ODD("Mono odd"),
  MONO_EVEN("Mono even"),
  DUAL_MONO("Dual mono"),
  QUAD_MONO("Quad mono");

  private final String label;
  private final int resources;
  private final boolean isImmersive;
  private final int spillLegIndex;

  SpillLeg(String label) {
    this(label, 1, false);
  }

  SpillLeg(int spillLegIndex, String label) {
    this(spillLegIndex, label, 1, false);
  }

  SpillLeg(String label, int resources) {
    this(label, resources, false);
  }

  SpillLeg(int spillLegIndex, String label, int resources) {
    this(spillLegIndex, label, resources, false);
  }

  SpillLeg(String label, int resources, boolean isImmersive) {
    this(0, label, resources, isImmersive);
  }

  SpillLeg(int spillLegIndex, String label, int resources, boolean isImmersive) {
    this.spillLegIndex = spillLegIndex;
    this.label = label;
    this.resources = resources;
    this.isImmersive = isImmersive;
  }

  public String getLabel() {
    return label;
  }

  public int getResources() {
    return resources;
  }

  public boolean isImmersive() {
    return isImmersive;
  }

  public int getSpillLegIndex() {
    return spillLegIndex;
  }

  public int getSpillLegIndexByWidth(Format width) {
    switch (width) {
      case SU_7_1:
        return this == LR_REAR_SURROUND ? 9 : spillLegIndex;
      case IM_5_1_2:
        {
          switch (this) {
            case LR_TOP_FRONT_2:
              return 9;
            default:
              return spillLegIndex;
          }
        }
      case IM_7_1_2:
        {
          switch (this) {
            case LR_TOP_FRONT_2:
              return 12;
            case LR_REAR_SURROUND:
              return 9;
            default:
              return spillLegIndex;
          }
        }
      case IM_7_1_4:
        {
          switch (this) {
            case LR_REAR_SURROUND:
              return 9;
            case LR_TOP_BACK:
              return 15;
            case LR_TOP_FRONT_4:
              return 12;
            default:
              return spillLegIndex;
          }
        }
      case IM_0_2:
        {
          switch (this) {
            case LR_TOP_FRONT_2:
              return 1;
            default:
              return spillLegIndex;
          }
        }
      case IM_0_4:
        {
          switch (this) {
            case LR_TOP_FRONT_4:
              return 1;
            case LR_TOP_BACK:
              return 4;
            default:
              return spillLegIndex;
          }
        }
      case IM_5_1_4:
      default:
        return spillLegIndex;
    }
  }
}
