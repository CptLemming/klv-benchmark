package javaSingle.gql.meters;

import java.util.List;

import javaSingle.gql.meters.enums.MeterBarType;
import javaSingle.klv.common.enums.MeterScale;


public class MeterBar {
  public Integer id;
  public MeterBarType meterType;
  public String label;
  public MeterScale scale;
  public Boolean isActive;
  public Boolean isCut;
  public List<Integer> timeslots;
  public Integer meterPacketType;

  public MeterBar(Integer id, MeterBarType meterType, String label, MeterScale scale, Boolean isActive, Boolean isCut, List<Integer> timeslots, Integer meterPacketType) {
      this.id = id;
      this.meterType = meterType;
      this.label = label;
      this.scale = scale;
      this.isActive = isActive;
      this.isCut = isCut;
      this.timeslots = timeslots;
      this.meterPacketType = meterPacketType;
  }
}
