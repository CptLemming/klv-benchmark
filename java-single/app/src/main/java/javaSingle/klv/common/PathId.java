package javaSingle.klv.common;

import io.netty.buffer.ByteBuf;
import javaSingle.klv.common.enums.PanType;
import javaSingle.klv.common.enums.PathType;

import java.util.EnumSet;

public class PathId {
  public static final int RAW_NO_PATH = 0xffff00;
  public static final String NONE = "U/65535/0";
  public static final String CURRENT = "U/65535/1";

  public long raw;

  public PathId(ByteBuf buf) {
    this.raw = buf.readUnsignedIntLE();
  }

  public PathId(long raw) {
    this.raw = (raw >> 24 > 0 ? raw : RAW_NO_PATH);
  }

  public PathId(PathType pathType, int number, int component) {
    raw = (pathType.ordinal() << 24) + (number << 8) + component;
  }

  public PathId(String s) {
    String[] token = (s.isEmpty() ? NONE : s).split("/");

    raw =
      (PathType.valueOf(token[0]).ordinal() << 24) +
      (Integer.parseInt(token[1]) << 8) +
      (token.length == 2 ? 0 : Integer.parseInt(token[2]));
  }

  public boolean isChannel() {
    return raw >> 24 == 1;
  }

  public boolean isValid() {
    return raw != RAW_NO_PATH;
  }

  @Override
  public boolean equals(Object o) {
    return (o != null && (o == this || o.getClass() == getClass() && o.hashCode() == hashCode()));
  }

  @Override
  public int hashCode() {
    return (int) raw;
  }

  public PathType type() {
    return (raw >> 24 > 0 ? PathType.values()[(int) raw >> 24] : PathType.U);
  }

  public int number() {
    return (raw >> 24 > 0 ? (int) raw >> 8 & 0x0000ffff : 0xffff);
  }

  public int component() {
    return (raw >> 24 > 0 ? (int) raw & 0x000000ff : 0);
  }

  public PathId component(int component) {
    return new PathId(type(), number(), component);
  }

  @Override
  public String toString() {
    String path = String.format("%s/%s/%s", type(), number(), component());

    return ((path.equals(NONE) || path.equals(CURRENT)) ? "" : path);
  }

  public boolean isBuss() {
    return EnumSet.of(PathType.MN, PathType.GP, PathType.TK, PathType.AUX, PathType.MM).contains(type());
  }


  public String getPath() {
    return toString();
  }

  public Boolean getHasPath() {
    return toString() != "";
  }

  public Boolean getIsChannel() {
    return isChannel();
  }

  public Boolean getIsAux() {
    return type() == PathType.AUX;
  }

  public Boolean getIsMain() {
    return type() == PathType.MN;
  }

  public Boolean getIsGroup() {
    return type() == PathType.GP;
  }

  public Boolean getIsMixMinus() {
    return type() == PathType.MM;
  }

  public Boolean getIsTrack() {
    return type() == PathType.TK;
  }

  public Boolean getIsDirectOutput() {
    return type() == PathType.DIR;
  }

  public Boolean getIsVCAMaster() {
    return type() == PathType.VCA;
  }

  public Boolean getIsRemoteFader() {
    return type() == PathType.REM_FADER;
  }

  public Boolean getIsRemoteAux() {
    return type() == PathType.REM_AUX;
  }

  public Boolean getIsRemote() {
    return getIsRemoteFader() || getIsRemoteAux();
  }

  public Boolean isSpill() {
    return component() > 0;
  }

  public boolean isCurrentPath() {
    return type().equals(PathType.U) && ((int) raw & 0x000000ff) == 1;
  }

  public String getName() {
    String type = "";

    if (getIsMain()) type = "Main";
    if (getIsGroup()) type = "Group";
    if (getIsAux()) type = "Aux";
    if (getIsTrack()) type = "Track";
    if (getIsMixMinus()) type = "Mix Minus";

    return type + " " + (number() + 1);
  }

  public String getShortName() {
    String type = "";

    if (getIsMain()) type = "M";
    if (getIsGroup()) type = "G";
    if (getIsAux()) type = "A";
    if (getIsTrack()) type = "T";
    if (getIsMixMinus()) type = "MM";

    return type + (number() + 1);
  }

  public Integer getPanIndex() {
    Integer panIndex = PanType.PATH_PANNER.getPanIndex();
    if (getIsTrack()) panIndex = PanType.TRACK_PANNER.getPanIndex();
    if (getIsAux()) panIndex = PanType.AUX_PANNER.getPanIndex();
    return panIndex;
  }
}
