package org.project;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.project.RequestData;

public class TestProfileGenerator {
  private static long MIN_COUNT_REQUEST = 10;
  private static double INTENSIVITY_MULTIPLIER = 3;
  private static double STATISTIC_BY_DAYS = 30;
  private static double WORK_HOURS = 24;
  private static double INTENSIVITY_MIN = 0.5;
  private static double ROUND_MULTIPLIER = 1000; //3 знака после .
  public static Map<String, PlaneRequest>  generateTestProfile(Map<String, RequestData>dataMap) {
    Map<String, PlaneRequest>resultMap = new TreeMap<String, PlaneRequest>();
    for (Map.Entry<String, RequestData> entry : dataMap.entrySet()) {
      long count = entry.getValue().getCount();
      if (count >MIN_COUNT_REQUEST ){
        PlaneRequest planeRequest = new PlaneRequest();
        planeRequest.setRequestType(entry.getValue().getRequestType());
        planeRequest.setPath(entry.getValue().getPath());
        planeRequest.setCount(count);
        planeRequest.setServiceName(entry.getValue().getServiceName());
        planeRequest.setIntensivity(intensivity_generator(planeRequest));
        if (planeRequest.getIntensivity() < INTENSIVITY_MIN){
          continue;
        }
        resultMap.put(entry.getKey(), planeRequest);
      }
    }
    return resultMap;
  }
  private static double intensivity_generator(PlaneRequest planeRequest) {
    return roundTo(planeRequest.getCount()/STATISTIC_BY_DAYS/WORK_HOURS * INTENSIVITY_MULTIPLIER);
  }
  
  private static double roundTo(double number) {
    return Math.round(number * ROUND_MULTIPLIER) / ROUND_MULTIPLIER;
  }
}
