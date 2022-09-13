from enum import IntEnum


class LoudnessMode(IntEnum):
  LOUDNESS_MODE_EBU = 0
  LOUDNESS_MODE_ATSC_A85_2011 = 1
  LOUDNESS_MODE_ARIB_TR_B32 = 2
  LOUDNESS_MODE_ATSC_A85_2013 = 3
  LOUDNESS_MODE_DPP_LIVE = 4
  LOUDNESS_MODE_DPP_NON_LIVE = 5
  LOUDNESS_MODE_COUNT = 6