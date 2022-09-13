from klv.common.enums.labelType import LabelType
from klv.common.label import Label


class MeterBlockLabels:
  def __init__(self, titleLabel = "", tapeLabel = "", label = "", preToneTBPreDelayLabel = "", portLabel = "") -> None:
      self.titleLabel = titleLabel
      self.tapeLabel = tapeLabel
      self.label = label
      self.preToneTBPreDelayLabel = preToneTBPreDelayLabel
      self.portLabel = portLabel

  def setTitleLabel(self, titleLabel: str):
    self.titleLabel = titleLabel

  def setLabel(self, label: str):
    self.label = label

  def setPreToneTBPreDelayLabel(self, preToneTBPreDelayLabel: str):
    self.preToneTBPreDelayLabel = preToneTBPreDelayLabel

  def setTapeOrPortLabel(self, typedLabel: Label, systemLabel: str):
    if typedLabel.labelType is LabelType.FADER_USER:
      self.tapeLabel = typedLabel.label
    elif typedLabel.label != "" and systemLabel != typedLabel.label:
      self.portLabel = typedLabel.label
