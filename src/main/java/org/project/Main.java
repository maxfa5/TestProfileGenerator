package org.project;

import org.project.Writer.ProfileExportService;
import org.project.model.LoadProfile;
import org.project.model.RequestData;

import java.io.File;
import java.util.Map;

public class Main {
  public static void main(String[] args) {
    ProfileExportService exportService = new ProfileExportService();

    try {
      File file = new File("./prod5.csv");
      Map<String, RequestData> dataMap = CsvParser.parseCsvToMap(file);
      LoadProfile loadProfile = LoadProfileGenerator.generateTestProfile(dataMap);
      exportService.exportToFormats(loadProfile, "./test_profile");
      
    } catch (Exception e) {
      System.err.println("Ошибка: " + e.getMessage());
      e.printStackTrace();
    }
  }
}