package javaSingle.klv;

import io.netty.buffer.ByteBuf;

public class Helper {
  public static String readString(ByteBuf buf) {
    var length = buf.readUnsignedIntLE();
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < length; i++) {
      sb.append((char) buf.readByte());
    }

    return sb.toString();
  }
}
