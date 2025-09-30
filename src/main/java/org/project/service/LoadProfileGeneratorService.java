package org.project.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.project.model.LoadProfile;
import org.project.model.PlaneRequest;
import org.project.model.RequestData;
import org.springframework.stereotype.Service;

@Service
public class LoadProfileGeneratorService {
  
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
  private long countActiveRequests;
  public LoadProfile generateTestProfile(Map<String, RequestData> dataMap) {
    Map<String, PlaneRequest> resultMap = new HashMap<>();
    long allCountRequests = 0;
    
    for (Map.Entry<String, RequestData> entry : dataMap.entrySet()) {
      long count = entry.getValue().getCount();
      allCountRequests += count;
      if (count > minCountRequest) {
        PlaneRequest planeRequest = new PlaneRequest();
        planeRequest.setRequestType(entry.getValue().getRequestType());
        planeRequest.setPath(entry.getValue().getPath());
        planeRequest.setCount(count);
        planeRequest.setServiceName(entry.getValue().getServiceName());
        planeRequest.setIntensivity(intensivity_calc(planeRequest));
        countActiveRequests+=count;
        if (planeRequest.getIntensivity() < intensivityMin) {
          continue;
        }
        resultMap.put(entry.getKey(), planeRequest);
      }
    }
    
    LoadProfile loadProfile = new LoadProfile();
    loadProfile.setRequests(resultMap);
    loadProfile.setCountAllRequests(allCountRequests);
    loadProfile.setIntensivityByHour(intensivityByService(allCountRequests));
    loadProfile.setPlanCoveragePercentage(planCoverage(allCountRequests));
    return loadProfile;
  }
  
  private double planCoverage(long allCountRequests) {
    return allCountRequests / countActiveRequests * 100;
  }
  
  private double intensivity_calc(PlaneRequest planeRequest) {
    return roundTo(planeRequest.getCount() / statisticByDays / workHours * intensivityMultiplier);
  }
  
  private double intensivityByService(long allCountRequests) {
    return roundTo(allCountRequests / statisticByDays / workHours * intensivityMultiplier);
  }
  
  private double roundTo(double number) {
    return Math.round(number * roundMultiplier) / roundMultiplier;
  }
}