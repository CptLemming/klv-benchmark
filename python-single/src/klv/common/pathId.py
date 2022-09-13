
from klv import Walker
from klv.common.enums.pathType import PathType


class PathId:
  NONE =  "U/65535/0"
  CURRENT = "U/65535/1"

  def __init__(self, walker: Walker):
    self.raw = walker.read_u32()

  def pathType(self):
    return PathType(self.raw >> 24) if self.raw >> 24 > 0 else PathType.U

  def number(self):
    return self.raw >> 8 & 0x0000ffff if self.raw >> 24 > 0 else 0xffff

  def component(self):
    return self.raw & 0x000000ff if self.raw >> 24 > 0 else 0

  def getHasPath(self):
    return self.to_string() != ""

  def getIsChannel(self):
    return self.pathType() is PathType.CH

  def getIsAux(self):
    return self.pathType() is PathType.AUX

  def getIsMain(self):
    return self.pathType() is PathType.MN

  def getIsGroup(self):
    return self.pathType() is PathType.GP

  def getIsMixMinus(self):
    return self.pathType() is PathType.MM

  def getIsTrack(self):
    return self.pathType() is PathType.TK

  def getIsDirectOutput(self):
    return self.pathType() is PathType.DIR

  def getIsVCAMaster(self):
    return self.pathType() is PathType.VCA

  def getIsRemoteFader(self):
    return self.pathType() is PathType.REM_FADER

  def getIsRemoteAux(self):
    return self.pathType() is PathType.REM_AUX

  def getIsRemote(self):
    return self.getIsRemoteFader() or self.getIsRemoteAux()

  def to_string(self):
    path = "{}/{}/{}".format(self.pathType(), self.number(), self.component())

    return "" if path == self.NONE or path == self.CURRENT else path
