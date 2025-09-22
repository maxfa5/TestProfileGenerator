package org.project;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

//@Service
public class CsvService {
  
  public Map<String, RequestData> parseCsvToMap(MultipartFile file) {
    Map<String, RequestData> resultMap = new HashMap<>();
    
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
      String[] values;
      // Пропускаем заголовок если есть
      // csvReader.readNext();
      
      while ((values = csvReader.readNext()) != null) {
        if (values.length >= 4) {
          RequestData requestData = parseRow(values);
          if (requestData.getPath() != null && !requestData.getPath().isEmpty()) {
            resultMap.put(requestData.getPath(), requestData);
          }
        }
      }
    } catch (IOException | CsvValidationException e) {
      throw new RuntimeException("Ошибка чтения CSV файла", e);
    }
    
    return resultMap;
  }
  
  private RequestData parseRow(String[] row) {
    RequestData data = new RequestData();
    data.setRequestType(row[0]);
    data.setServiceName(row[1]);
    data.setPath(row[2]);
    
    try {
      data.setCount(Long.parseLong(row[3]));
    } catch (NumberFormatException e) {
      data.setCount(0L);
    }
    
    return data;
  }
}