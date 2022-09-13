class MeterBar:
  def __init__(self, id, meterType, label, scale, isActive, isCut, timeslots, meterPacketType) -> None:
      self.id = id
      self.meterType = meterType
      self.label = label
      self.scale = scale
      self.isActive = isActive
      self.isCut = isCut
      self.timeslots = timeslots
      self.meterPacketType = meterPacketType
