from enum import IntEnum


class Format(IntEnum):
  NP = 0
  M = 1
  ST = 2
  SU_3_0 = 3
  SU_4_0 = 4
  SU_5_0 = 5
  SU_6_0 = 6
  SU_7_0 = 7
  SU_5_1 = 8
  SU_7_1 = 9
  UNUSED_1 = 10
  MO = 11
  ME = 12
  SU_1_1 = 13
  SU_2_1 = 14
  SU_3_1 = 15
  SU_4_1 = 16
  UNUSED_2 = 17
  UNUSED_3 = 18
  S = 19
  LORO_ST = 20
  DUAL_M = 21
  QUAD_M = 22
  DOWNMIX_ST = 23  # Stereo downmix (LtRt) only exists on old core Apollo and Artemis systems
  MMO = 24
  DOWNMIX_5_1 = 25
  PAIR_N_N = 26
  PAIR_N_M = 27
  PAIR_N_E = 28
  PAIR_M_N = 29
  PAIR_M_M = 30
  PAIR_M_E = 31
  PAIR_O_N = 32
  PAIR_O_M = 33
  PAIR_O_E = 34
  REMOTE_PLACEHOLDER = 35
  UNUSED_4 = 36
  UNUSED_5 = 37
  UNUSED_6 = 38
  UNUSED_7 = 39
  IM_5_1_2 = 40
  IM_5_1_4 = 41
  IM_7_1_2 = 42
  IM_7_1_4 = 43
  IM_0_2 = 44
  IM_0_4 = 45
  
  def getImmersiveLegCount(self):
    if self is self.IM_5_1_2 or self is self.IM_7_1_2 or self is self.IM_0_2:
      return 2
    if self is self.IM_5_1_4 or self is self.IM_7_1_4 or self is self.IM_0_4:
      return 4
    return 0
