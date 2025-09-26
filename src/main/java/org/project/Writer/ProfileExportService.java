package org.project.Writer;

import org.project.model.PlaneRequest;

import java.util.Map;

public class ProfileExportService {
  
  public void exportToFormats(Map<String, PlaneRequest> profile, String baseFilename) {
    exportToFormat(profile, baseFilename + ".txt", "txt");
    exportToFormat(profile, baseFilename + ".csv", "csv");
  }
  
  public void exportToFormat(Map<String, PlaneRequest> profile, String filename, String format) {
    try {
      ProfileWriter writer = ProfileWriterFactory.getWriter(format);
      writer.write(profile, filename);
    } catch (Exception e) {
      System.err.println("Ошибка экспорта в формат " + format + ": " + e.getMessage());
    }
  }
}