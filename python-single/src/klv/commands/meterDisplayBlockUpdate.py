from typing import List
from klv import Walker
from klv.common.meterBlockUpdate import MeterBlockUpdate


class MeterDisplayBlockUpdate:
  def __init__(self, walker: Walker):
    self.target = walker.read_u8()
    numUpdates = walker.read_u32()
    self.updates: List[MeterBlockUpdate] = []

    for _ in range(0, numUpdates):
      self.updates.append(MeterBlockUpdate(walker))

    self.isSnapshot = walker.read_u8() != 0
