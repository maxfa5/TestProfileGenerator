package org.project;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.project.model.LoadProfile;
import org.project.model.PlaneRequest;
import org.project.model.RequestData;

@Component
public class LoadProfileGenerator {
  
  @Value("${app.load-profile.min-count-request}")
  private long minCountRequest;
  
  @Value("${app.load-profile.intensivity-multiplier}")
  private double intensivityMultiplier;
  
  @Value("${app.load-profile.statistic-by-days}")
  private double statisticByDays;
  
  @Value("${app.load-profile.work-hours}")
  private double workHours;
  
  @Value("${app.load-profile.intensivity-min}")
  private double intensivityMin;
  
  @Value("${app.load-profile.round-multiplier}")
  private double roundMultiplier;
  
  public LoadProfile generateTestProfile(Map<String, RequestData> dataMap) {
    Map<String, PlaneRequest> resultMap = new HashMap<>();
    double allCountRequests = 0;
    
    for (Map.Entry<String, RequestData> entry : dataMap.entrySet()) {
      long count = entry.getValue().getCount();
      if (count > minCountRequest) {
        PlaneRequest planeRequest = new PlaneRequest();
        planeRequest.setRequestType(entry.getValue().getRequestType());
        planeRequest.setPath(entry.getValue().getPath());
        planeRequest.setCount(count);
        allCountRequests += count;
        planeRequest.setServiceName(entry.getValue().getServiceName());
        planeRequest.setIntensivity(intensivity_calc(planeRequest));
        
        if (planeRequest.getIntensivity() < intensivityMin) {
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
  
  private double intensivity_calc(PlaneRequest planeRequest) {
    return roundTo(planeRequest.getCount() / statisticByDays / workHours * intensivityMultiplier);
  }
  
  private double intensivityByService(double allCountRequests) {
    return roundTo(allCountRequests / statisticByDays / workHours * intensivityMultiplier);
  }
  
  private double roundTo(double number) {
    return Math.round(number * roundMultiplier) / roundMultiplier;
  }
}