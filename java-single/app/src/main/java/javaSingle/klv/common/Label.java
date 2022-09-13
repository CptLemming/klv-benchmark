package javaSingle.klv.common;

import io.netty.buffer.ByteBuf;
import javaSingle.klv.Helper;
import javaSingle.klv.common.enums.LabelType;

public class Label {
  public String label;

  public LabelType type;

  public Label(ByteBuf buf) {
    label = Helper.readString(buf);
    type = LabelType.values()[buf.readByte()];
  }

  public Integer getId() {
    return type.ordinal();
  }

  @Override
  public String toString() {
    return "Label{" + "label='" + label + '\'' + ", type=" + type + '}';
  }
}
