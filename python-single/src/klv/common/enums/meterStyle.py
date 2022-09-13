from enum import IntEnum


class MeterStyle(IntEnum):
  PPM_8_20 = 0
  PPM_9_15 = 1
  PPM_10_18 = 2
  VU_8_20 = 3
  VU_12_20 = 4
  VU_20 = 5
  PHASE = 6
  DYN = 7
  TYPE_I_NORDIC = 8
  TYPE_IIA_BBC = 9
  TYPE_IIB_EBU = 10
  DIN = 11
  VU = 12
  DO_NOT_CARE = 13
  LOUDNESS = 14
  PPM_12_18 = 15
  VIDEO_INPUT_1 = 16
  VIDEO_INPUT_2 = 17
