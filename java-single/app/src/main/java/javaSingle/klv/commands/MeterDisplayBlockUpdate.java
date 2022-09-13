package javaSingle.klv.commands;

import java.util.List;
import java.util.ArrayList;

import io.netty.buffer.ByteBuf;
import javaSingle.klv.common.MeterBlockUpdate;

public class MeterDisplayBlockUpdate {

  public Target target;

  public List<MeterBlockUpdate> updates;

  public boolean isSnapshot;

  public enum Target {
    FADER,
    OUTPUT,
  }

  public MeterDisplayBlockUpdate(ByteBuf buf) {
    target = Target.values()[buf.readByte()];
    var numUpdates = buf.readUnsignedIntLE();
    updates = new ArrayList<>();

    for (int i = 0; i < numUpdates; i++) {
      updates.add(new MeterBlockUpdate(buf));
    }

    isSnapshot = buf.readByte() != 0;
  }
}
