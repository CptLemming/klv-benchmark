from enum import IntEnum


class DynamicsMode(IntEnum):
  NO_DYN = 0
  DYN1 = 1
  DYN2 = 2
  DYN1_N_2 = 3
  LVL_N_DYN1 = 4
  LVL_N_DYN2 = 5
  LVL_N_DYN1_OR_2 = 6
  DYN1_OR_2 = 7
  LVL_DYN1_N_DYN2 = 7
  LVL_DYN1_N_AMIX = 8
  DYN1_N_DYN2 = 9
  DYN1_N_AMIX = 10

