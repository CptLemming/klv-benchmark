from klv import Walker
from klv.common.enums.labelType import LabelType


class Label:
  def __init__(self, walker: Walker):
    self.label = walker.read_str()
    self.labelType = LabelType(walker.read_u8())
