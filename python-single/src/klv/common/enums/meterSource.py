from enum import IntEnum


class MeterSource(IntEnum):
  MAIN = 0
  GROUP = 1
  AUX = 2
  TRACK = 3
  EXTERNAL_INPUT = 4
  MIX_MINUS = 5
  AFL_STEREO = 6
  AFL_SURROUND = 7
  PFL_STEREO = 8
  PFL_SURROUND = 9
  APFL_STEREO = 10
  APFL_SURROUND = 11
  CONSOLE_LS = 12
  OAC = 13
  USER_METER_1 = 14
  USER_METER_2 = 15
  FADER = 16
  VIDEO = 17

  def getFullTitle(self):
    return "TODO"

  def getLabelSuffix(self):
    return "TODO"
  
  def isMixMinusSource(self):
    return self is MeterSource.MIX_MINUS

  def getMixMinusMeterSourceByIndex(self, _faderIndexNumber: int):
    if self.isMixMinusSource():
      # TODO
      # this.fullTitle = "Mix Minus" + " " + index;
      # this.index = index;
      pass
    return self
