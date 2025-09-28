package org.project.Writer;

import org.project.model.LoadProfile;
import org.project.model.PlaneRequest;

import java.util.Map;

public class ProfileExportService {
  
  public void exportToFormats(LoadProfile loadProfile, String baseFilename) {
    exportToFormat(loadProfile, baseFilename + ".txt", "txt");
    exportToFormat(loadProfile, baseFilename + ".csv", "csv");
  }
  
  public void exportToFormat(LoadProfile loadProfile, String filename, String format) {
    try {
      ProfileWriter writer = ProfileWriterFactory.getWriter(format);
      writer.write(loadProfile, filename);
    } catch (Exception e) {
      System.err.println("Ошибка экспорта в формат " + format + ": " + e.getMessage());
    }
  }
}