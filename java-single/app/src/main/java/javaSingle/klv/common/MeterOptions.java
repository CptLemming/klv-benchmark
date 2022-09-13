package javaSingle.klv.common;

import io.netty.buffer.ByteBuf;
import javaSingle.klv.common.enums.MeterSourceOption;

public class MeterOptions {

  public boolean isAudio;

  public boolean isDynamics;

  public boolean isLoudness;

  public boolean isDownmix;

  public boolean isPhase;

  public boolean isSurroundDownmix;

  public boolean isFullImmersive;

  public boolean isDownmixLoudness;

  public boolean isDownmixSurroundLoudness;

  public boolean isDualTrack;

  public boolean isQuadTrack;

  public boolean isTrackPairLoudness;

  public boolean isTrackOddLoudness;

  public boolean isTrackEvenLoudness;

  public boolean isPreToneTb;

  public boolean isPreDelay;

  public boolean isFullHeight;

  private boolean unused;

  public MeterOptions(ByteBuf buf) {
    var bitset = buf.readUnsignedIntLE();

    isAudio = (bitset >> 31 & 1) != 0;
    isDynamics = (bitset >> 30 & 1) != 0;
    isLoudness = (bitset >> 29 & 1) != 0;
    isDownmix = (bitset >> 28 & 1) != 0;
    isPhase = (bitset >> 27 & 1) != 0;
    isSurroundDownmix = (bitset >> 26 & 1) != 0;
    isFullImmersive = (bitset >> 25 & 1) != 0;
    isDownmixLoudness = (bitset >> 24 & 1) != 0;
    isDownmixSurroundLoudness = (bitset >> 23 & 1) != 0;
    isDualTrack = (bitset >> 22 & 1) != 0;
    isQuadTrack = (bitset >> 21 & 1) != 0;
    isTrackPairLoudness = (bitset >> 20 & 1) != 0;
    isTrackOddLoudness = (bitset >> 19 & 1) != 0;
    isTrackEvenLoudness = (bitset >> 18 & 1) != 0;
  }

  public boolean isAudioOnly() {
    return (
      isAudio &&
      (
        !isDynamics &&
        !isDownmix &&
        !isLoudness &&
        !isSurroundDownmix &&
        !isPhase &&
        !isDownmixSurroundLoudness &&
        !isDownmixLoudness &&
        !isTrackPairLoudness &&
        !isTrackOddLoudness &&
        !isTrackEvenLoudness
      )
    );
  }

  public boolean isPhaseOnly() {
    return (
      isAudio &&
      isPhase &&
      (
        !isDynamics &&
        !isDownmix &&
        !isLoudness &&
        !isSurroundDownmix &&
        !isDownmixSurroundLoudness &&
        !isDownmixLoudness &&
        !isTrackPairLoudness &&
        !isTrackOddLoudness &&
        !isTrackEvenLoudness
      )
    );
  }

  public boolean isLoudnessType() {
    return (
      isLoudness ||
      isDownmixLoudness ||
      isDownmixSurroundLoudness ||
      isTrackPairLoudness ||
      isTrackOddLoudness ||
      isTrackEvenLoudness
    );
  }

  public MeterSourceOption getLoudnessType() {
    if (isLoudness) return MeterSourceOption.LOUDNESS;
    if (isDownmixLoudness) return MeterSourceOption.DOWNMIX_LOUDNESS;
    if (isDownmixSurroundLoudness) return MeterSourceOption.SU_DOWNMIX_LOUDNESS;
    if (isTrackPairLoudness) return MeterSourceOption.TRACK_PAIR_LOUDNESS;
    if (isTrackOddLoudness) return MeterSourceOption.TRACK_ODD_LOUDNESS;
    if (isTrackEvenLoudness) return MeterSourceOption.TRACK_EVEN_LOUDNESS;
    return null;
  }
}
