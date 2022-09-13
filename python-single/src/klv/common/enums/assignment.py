from enum import IntEnum


class Assignment(IntEnum):
  SEL_METER = 0
  UNUSED1 = 1
  RTB = 2
  UTILITY_MONITOR_MIXER = 3
  PFL = 4
  AFL = 5
  APFL = 6
  CRLS_PRE = 7
  MIX_MINUS = 8
  TRACK_OP = 9
  AUX_OP = 10
  EXTERNAL = 11
  FADER_IP = 12
  FADER_IP_DYN1 = 13
  FADER_IP_DYN2 = 14
  FADER_IP_DYN1_N_2 = 15
  FADER_IP_LVL_N_DYN = 16
  GROUP_OP = 17
  MAIN_DESK_PRE_DELAY = 18
  MAIN_LINE_PRE_DELAY = 19
  MAIN_LINE_USA_PRE_DELAY = 20
  ROUTING_DISPLAY = 21
  EQ_DISPLAY = 22
  DYN_1_DISPLAY = 23
  DYN_2_DISPLAY = 24
  PAN_DISPLAY = 25
  TRACK_DISPLAY = 26
  FUNC_MAIN_DESK = 27
  FUNC_MAIN_LINE = 28
  FUNC_MAIN_LINE_USA = 29
  FUNC_AUX_OP = 30
  FUNC_TRACK_OP = 31
  OAC_OP = 32
  VIDEO_OVERLAY = 33
  UNASSIGNED = 34