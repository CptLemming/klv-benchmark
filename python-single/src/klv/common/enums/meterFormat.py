from enum import IntEnum


class MeterFormat(IntEnum):
  SURROUND_STEREO_OR_MONO = 0
  STEREO_LO_RO = 1
  MS = 2
  STEREO_PHASE = 3
  MONO = 4
  STEREO = 5
  SURROUND = 6
  MF_UNDEFINED = 7
  DOWNMIX_5_1 = 8
