package org.project;

import java.util.HashMap;
import java.util.Map;

import org.project.model.LoadProfile;
import org.project.model.PlaneRequest;
import org.project.model.RequestData;

public class LoadProfileGenerator {
  private static long MIN_COUNT_REQUEST = 1;
  private static double INTENSIVITY_MULTIPLIER = 3;
  private static double STATISTIC_BY_DAYS = 7;
  private static double WORK_HOURS = 24;
  private static double INTENSIVITY_MIN = 0.2;
  private static double ROUND_MULTIPLIER = 1000; //3 знака после .
  public static LoadProfile generateTestProfile(Map<String, RequestData>dataMap) {
    Map<String, PlaneRequest>resultMap = new HashMap<>();
    double allCountRequests = 0;
    for (Map.Entry<String, RequestData> entry : dataMap.entrySet()) {
      long count = entry.getValue().getCount();
      if (count >MIN_COUNT_REQUEST ){
        PlaneRequest planeRequest = new PlaneRequest();
        planeRequest.setRequestType(entry.getValue().getRequestType());
        planeRequest.setPath(entry.getValue().getPath());
        planeRequest.setCount(count);
        allCountRequests += count;
        planeRequest.setServiceName(entry.getValue().getServiceName());
        planeRequest.setIntensivity(intensivity_calc(planeRequest));
        if (planeRequest.getIntensivity() < INTENSIVITY_MIN){
          continue;
        }
        resultMap.put(entry.getKey(), planeRequest);
      }
    }
    LoadProfile loadProfile = new LoadProfile();
    loadProfile.setRequests(resultMap);
    loadProfile.setIntensivityByHour(intensivityByService(allCountRequests));
    return loadProfile;
  }
  private static double intensivity_calc(PlaneRequest planeRequest) {
    return roundTo(planeRequest.getCount()/STATISTIC_BY_DAYS/WORK_HOURS * INTENSIVITY_MULTIPLIER);
  }
  private static double intensivityByService(double allCountRequests) {
    return roundTo(allCountRequests/STATISTIC_BY_DAYS/WORK_HOURS * INTENSIVITY_MULTIPLIER);
  }

  private static double roundTo(double number) {
    return Math.round(number * ROUND_MULTIPLIER) / ROUND_MULTIPLIER;
  }
}
