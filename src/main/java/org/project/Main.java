package org.project;

import org.project.model.PlaneRequest;
import org.project.model.RequestData;

import java.io.File;
import java.util.Map;

public class Main {
  public static void main(String[] args) {
    try {
      File file = new File("./prod4.csv");
      Map<String, RequestData> dataMap = CsvParser.parseCsvToMap(file);
      Map<String, PlaneRequest> testProfile = TestProfileGenerator.generateTestProfile(dataMap);
      ProfileExportService exportService = new ProfileExportService();
      exportService.exportToFormats(testProfile, "./test_profile");
      
    } catch (Exception e) {
      System.err.println("Ошибка: " + e.getMessage());
      e.printStackTrace();
    }
  }
}