package javaSingle.klv.common.enums;


import java.util.Optional;

public enum LegChannelEnum {
  M_OR_L,
  R,
  C,
  LFE("e"),
  Ls("l"),
  Rs("r"),
  M,
  L,
  Lo,
  Ro,
  Mo,
  Ltf,
  Rtf,
  Ltr,
  Rtr,
  Ltb,
  Rtb,
  Lss,
  Rss,
  Lrs,
  Rrs;

  private final String shortLabel;

  LegChannelEnum() {
    this.shortLabel = null;
  }

  LegChannelEnum(final String shortLabel) {
    this.shortLabel = shortLabel;
  }

  public String getShortLabel() {
    return Optional.ofNullable(shortLabel).orElse(name());
  }

  public static LegChannelEnum findLegType(Integer index, Format format) {
    if (format != Format.M && index == 0) {
      return LegChannelEnum.L;
    } else if (format == Format.M && index == 0) {
      return LegChannelEnum.M;
    }
    return LegChannelEnum.values()[index];
  }

  public static LegChannelEnum findLegTypeByName(String legName) {
    return LegChannelEnum.valueOf(legName);
  }

  public int getLegIndexByWidth(Format width) {
    if (width != null) {
      return width.getLegChannelEnums().indexOf(this);
    }
    return this.getLegIndex();
  }

  public int getLegIndex() {
    if (this == LegChannelEnum.M || this == LegChannelEnum.L) {
      return 0;
    }
    return this.ordinal();
  }
}
