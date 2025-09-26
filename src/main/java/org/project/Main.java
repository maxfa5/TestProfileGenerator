package org.project;

import org.project.Writer.ProfileExportService;
import org.project.model.PlaneRequest;
import org.project.model.RequestData;

import java.io.File;
import java.util.Map;

public class Main {
  public static void main(String[] args) {
    ProfileExportService exportService = new ProfileExportService();

    try {
      File file = new File("./prod7.csv");
      Map<String, RequestData> dataMap = CsvParser.parseCsvToMap(file);
      Map<String, PlaneRequest> testProfile = TestProfileGenerator.generateTestProfile(dataMap);
      exportService.exportToFormats(testProfile, "./test_profile");
      
    } catch (Exception e) {
      System.err.println("Ошибка: " + e.getMessage());
      e.printStackTrace();
    }
  }
}