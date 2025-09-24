package org.project;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
@EqualsAndHashCode
public class PlaneRequest implements Comparable<PlaneRequest>{
  String requestType;
  String serviceName;
  String path;
  
  Long count;
  double intensivity;
  
  @Override
  public int compareTo(PlaneRequest o) {
    return Double.compare(this.intensivity, o.intensivity);
  }
}
