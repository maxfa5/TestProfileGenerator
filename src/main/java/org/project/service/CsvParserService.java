package org.project.service;

import com.opencsv.CSVReader;
import org.project.model.RequestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.File;
import java.util.*;

@Service
public class CsvParserService {
  
  private final PathNormalizerService pathNormalizerService;
  
  @Autowired
  public CsvParserService(PathNormalizerService pathNormalizerService) {
    this.pathNormalizerService = pathNormalizerService;
  }
  
  private RequestData parseRowToRequestData(String[] row) {
    RequestData data = new RequestData();
    data.setRequestType(row[1]);
    data.setServiceName(row[0]);
    
    data.setPath(pathNormalizerService.normalizePathWithDots(row[2]));
    try {
      data.setCount(Long.parseLong(row[3]));
    } catch (NumberFormatException e) {
      data.setCount(0L);
    }
    
    return data;
  }
  
  public Map<String, RequestData> parseCsvToMap(File file) {
    Map<String, RequestData> resultMap = new HashMap<>();
    
    try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
      String[] values;
      
      while ((values = csvReader.readNext()) != null) {
        if (values.length >= 4) {
          RequestData requestData = parseRowToRequestData(values);
          String compositeKey = requestData.getCompositeKey();
          
          if (compositeKey != null && !compositeKey.isEmpty()) {
            
            // Если ключ уже существует, суммируем count
            if (resultMap.containsKey(compositeKey)) {
              RequestData existing = resultMap.get(compositeKey);
              existing.setCount(existing.getCount() + requestData.getCount());
            } else {
              resultMap.put(compositeKey, requestData);
            }
          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Ошибка чтения CSV файла", e);
    }
    
    return resultMap;
  }
  
  public Map<String, RequestData> parseToMapWithMerge(List<String[]> csvData) {
    Map<String, RequestData> resultMap = new HashMap<>();
    
    for (String[] row : csvData) {
      if (row.length >= 4) {
        RequestData requestData = parseRowToRequestData(row);
        String compositeKey = requestData.getCompositeKey();
        
        if (compositeKey != null && !compositeKey.isEmpty()) {
          if (resultMap.containsKey(compositeKey)) {
            RequestData existing = resultMap.get(compositeKey);
            existing.setCount(existing.getCount() + requestData.getCount());
            existing.setServiceName(requestData.getServiceName());
          } else {
            resultMap.put(compositeKey, requestData);
          }
        }
      }
    }
    
    return resultMap;
  }
  
  // Метод для получения данных по отдельным компонентам ключа
  public RequestData getByCompositeKey(Map<String, RequestData> map,
                                       String requestType, String path) {
    String compositeKey = requestType + ":" + path;
    return map.get(compositeKey);
  }
}