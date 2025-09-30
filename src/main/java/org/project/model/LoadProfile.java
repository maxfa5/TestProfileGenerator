package org.project.model;

import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@EqualsAndHashCode
public class LoadProfile {
  Map<String, PlaneRequest> Requests;
  double intensivityByHour;
  long CountAllRequests;
  double planCoveragePercentage;
}
