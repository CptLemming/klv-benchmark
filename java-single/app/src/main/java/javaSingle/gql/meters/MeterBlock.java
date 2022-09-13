package javaSingle.gql.meters;

import java.util.List;

import javaSingle.gql.common.enums.ModelMutation;
import javaSingle.klv.common.MeterOptions;
import javaSingle.klv.common.enums.Format;
import javaSingle.klv.common.enums.MeterLabelBackgroundColour;
import javaSingle.klv.common.enums.MeterLayout;

public class MeterBlock {

  public ModelMutation mutation;

  public final Integer index;

  public final Integer row;

  public final Integer column;

  public final Integer width;

  public final Integer height;

  public final MeterLayout layout;

  public final MeterOptions options;

  public List<MeterBar> meters;

  public List<MeterBar> immersiveMeters;

  public List<MeterBar> dynamics1Meters;

  public List<MeterBar> dynamics2Meters;

  public List<MeterBar> loudnessMeters;

  public List<MeterBar> downmixMeters;

  public List<MeterBar> surroundDownmixMeters;

  public List<MeterBar> phaseMeters;

  public List<VideoMeter> videoMeters;

  public List<MeterLabel> meterLabels;

  public MeterLabelBackgroundColour colour;

  public MeterBlockLabels labels;

  public String path;

  public Boolean isFader;

  public Integer autoFader;

  public Integer channelIndex;

  public String layer;

  public Format format;

  public Format loudnessFormat;

  public Boolean isCurrentAssignedFader;

  public MeterBlock(
    final ModelMutation mutation,
    final Integer index,
    final Integer row,
    final Integer column,
    final Integer width,
    final Integer height,
    final MeterLabelBackgroundColour colour,
    final MeterBlockLabels labels,
    final List<MeterBar> meters,
    final List<MeterBar> immersiveMeters,
    final List<MeterBar> dynamics1Meters,
    final List<MeterBar> dynamics2Meters,
    final List<MeterBar> loudnessMeters,
    final List<MeterBar> downmixMeters,
    final List<VideoMeter> videoMeters,
    final List<MeterLabel> meterLabels,
    final String path,
    final Boolean isFader,
    final Integer autoFader,
    final MeterLayout layout,
    final MeterOptions options,
    final Integer channelIndex,
    final String layer,
    final Format format,
    final Format loudnessFormat,
    final Boolean isCurrentAssignedFader
  ) {
    this(mutation, index, row, column, width, height, layout, options);
    this.meters = meters;
    this.immersiveMeters = immersiveMeters;
    this.dynamics1Meters = dynamics1Meters;
    this.dynamics2Meters = dynamics2Meters;
    this.loudnessMeters = loudnessMeters;
    this.downmixMeters = downmixMeters;
    this.videoMeters = videoMeters;
    this.meterLabels = meterLabels;
    this.colour = colour;
    this.labels = labels;
    this.path = path;
    this.isFader = isFader;
    this.autoFader = autoFader;
    this.channelIndex = channelIndex;
    this.layer = layer;
    this.format = format;
    this.loudnessFormat = loudnessFormat;
    this.isCurrentAssignedFader = isCurrentAssignedFader;
  }

  public MeterBlock() {
    this(ModelMutation.UPDATED, null, null, null, null, null, null, null);
    this.meters = null;
    this.immersiveMeters = null;
    this.dynamics1Meters = null;
    this.dynamics2Meters = null;
    this.loudnessMeters = null;
    this.downmixMeters = null;
    this.colour = null;
    this.labels = null;
    this.path = null;
    this.isFader = null;
    this.channelIndex = null;
    this.layer = null;
    this.format = null;
    this.loudnessFormat = null;
    this.isCurrentAssignedFader = null;
  }

  public MeterBlock(
    final ModelMutation mutation,
    final Integer index,
    final Integer row,
    final Integer column,
    final Integer width,
    final Integer height,
    final MeterLayout layout,
    final MeterOptions options
  ) {
    this.mutation = mutation;
    this.index = index;
    this.row = row;
    this.column = column;
    this.width = width;
    this.height = height;
    this.layout = layout;
    this.options = options;
  }

  public void setMeters(List<MeterBar> meters) {
    this.meters = meters;
  }

  public void setImmersiveMeters(List<MeterBar> immersiveMeters) {
    this.immersiveMeters = immersiveMeters;
  }

  public void setDynamics1Meters(List<MeterBar> dynamics1Meters) {
    this.dynamics1Meters = dynamics1Meters;
  }

  public void setDownmixMeters(List<MeterBar> downmixMeters) {
    this.downmixMeters = downmixMeters;
  }

  public void setSurroundDownmixMeters(List<MeterBar> surroundDownmixMeters) {
    this.surroundDownmixMeters = surroundDownmixMeters;
  }

  public void setDynamics2Meters(List<MeterBar> dynamics2Meters) {
    this.dynamics2Meters = dynamics2Meters;
  }

  public void setLoudnessMeters(List<MeterBar> loudnessMeters) {
    this.loudnessMeters = loudnessMeters;
  }

  public void setLoudnessFormat(Format loudnessFormat) {
    this.loudnessFormat = loudnessFormat;
  }

  public void setAutoFader(Integer autoFader) {
    this.autoFader = autoFader;
  }

  public void setPhaseMeters(List<MeterBar> phaseMeters) {
    this.phaseMeters = phaseMeters;
  }

  public void setCurrentAssignedFader(Boolean currentAssignedFader) {
    isCurrentAssignedFader = currentAssignedFader;
  }

  public void setVideoMeters(List<VideoMeter> videoMeters) {
    this.videoMeters = videoMeters;
  }
}
