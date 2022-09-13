package javaSingle.gql.meters;

import javaSingle.klv.common.enums.MeterSourceOption;

public class MeterLabel {

  public final MeterSourceOption option;

  public final Integer row;

  public final Integer column;

  public final Integer height;

  public final Integer width;

  public MeterLabel(MeterSourceOption option, Integer row, Integer column, Integer height, Integer width) {
    this.option = option;
    this.row = row;
    this.column = column;
    this.height = height;
    this.width = width;
  }
}
