package javaSingle.klv.common.enums;

import java.util.Arrays;

public enum VideoSource {
  VIDEO_INPUT_1,
  VIDEO_INPUT_2;

  public static VideoSource findOrNull(String name) {
    if (name == null) return null;
    return Arrays.stream(values()).filter(value -> value.toString().equalsIgnoreCase(name)).findFirst().orElse(null);
  }
}
