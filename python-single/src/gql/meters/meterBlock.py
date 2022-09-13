from gql.common.enums.modelMutation import ModelMutation


class MeterBlock:
  def __init__(
    self,
    mutation,
    index,
    row,
    column,
    width,
    height,
    layout,
    options,
    meters,
    immersiveMeters,
    dynamics1Meters,
    dynamics2Meters,
    loudnessMeters,
    downmixMeters,
    surroundDownmixMeters,
    phaseMeters,
    videoMeters,
    meterLabels,
    colour,
    labels,
    path,
    isFader,
    autoFader,
    channelIndex,
    layer,
    format,
    loudnessFormat,
    isCurrentAssignedFader,
  ):
    self.mutation = mutation
    self.index = index
    self.row = row
    self.column = column
    self.width = width
    self.height = height
    self.layout = layout
    self.options = options
    self.meters = meters
    self.immersiveMeters = immersiveMeters
    self.dynamics1Meters = dynamics1Meters
    self.dynamics2Meters = dynamics2Meters
    self.loudnessMeters = loudnessMeters
    self.downmixMeters = downmixMeters
    self.surroundDownmixMeters = surroundDownmixMeters
    self.phaseMeters = phaseMeters
    self.videoMeters = videoMeters
    self.meterLabels = meterLabels
    self.colour = colour
    self.labels = labels
    self.path = path
    self.isFader = isFader
    self.autoFader = autoFader
    self.channelIndex = channelIndex
    self.layer = layer
    self.format = format
    self.loudnessFormat = loudnessFormat
    self.isCurrentAssignedFader = isCurrentAssignedFader

  def set_mutation(self, mutation: ModelMutation):
    self.mutation = mutation
