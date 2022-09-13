from klv import Walker


class MeterOptions:
  def __init__(self, walker: Walker):
    bitset = walker.read_u32()

    self.isAudio = bitset >> 31 & 1 != 0
    self.isDynamics = bitset >> 30 & 1 != 0
    self.isLoudness = bitset >> 29 & 1 != 0
    self.isDownmix = bitset >> 28 & 1 != 0
    self.isPhase = bitset >> 27 & 1 != 0
    self.isSurroundDownmix = bitset >> 26 & 1 != 0
    self.isFullImmersive = bitset >> 25 & 1 != 0
    self.isDownmixLoudness = bitset >> 24 & 1 != 0
    self.isDownmixSurroundLoudness = bitset >> 23 & 1 != 0
    self.isDualTrack = bitset >> 22 & 1 != 0
    self.isQuadTrack = bitset >> 21 & 1 != 0
    self.isTrackPairLoudness = bitset >> 20 & 1 != 0
    self.isTrackOddLoudness = bitset >> 19 & 1 != 0
    self.isTrackEvenLoudness = bitset >> 18 & 1 != 0
