package javaSingle.gql.meters;

import javaSingle.klv.common.Label;
import javaSingle.klv.common.enums.LabelType;

public class MeterBlockLabels {

  String titleLabel;

  String tapeLabel;

  String label;

  String preToneTBPreDelayLabel;

  String portLabel;

  public MeterBlockLabels() {
    titleLabel = "";
    tapeLabel = null;
    label = "";
    preToneTBPreDelayLabel = null;
    portLabel = null;
  }

  public void setTitleLabel(String titleLabel) {
    this.titleLabel = titleLabel;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setPreToneTBPreDelayLabel(String preToneTBPreDelayLabel) {
    this.preToneTBPreDelayLabel = preToneTBPreDelayLabel;
  }

  public void setTapeOrPortLabel(Label typedLabel, String systemLabel) {
    if (LabelType.FADER_USER == typedLabel.type) {
      this.tapeLabel = typedLabel.label;
    } else if (typedLabel.label != "" && !systemLabel.equals(typedLabel.label)) {
      this.portLabel = typedLabel.label;
    }
  }
}
