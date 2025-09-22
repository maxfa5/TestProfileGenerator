package org.project;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@EqualsAndHashCode
public class RequestData {
  String requestType;
  String serviceName;
  String path;
  Long count;
  
  public String getCompositeKey() {
    return requestType + ":" + path;
  }
}
