package org.project.Writer;

import org.project.model.LoadProfile;
import org.project.model.PlaneRequest;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;

public class CsvProfileWriter implements ProfileWriter {
  
  @Override
  public void write(LoadProfile loadProfile, String filename) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
      writeHeader(writer);
      writeBody(writer, loadProfile.getRequests());
      System.out.println("CSV профиль записан в файл: " + filename);
    } catch (Exception e) {
      System.err.println("Ошибка записи CSV профиля: " + e.getMessage());
      throw new RuntimeException("Failed to write CSV profile", e);
    }
  }
  
  private void writeHeader(PrintWriter writer) {
    writer.println("RequestType,ServiceName,Path,Count,Intensivity");
  }
  
  private void writeBody(PrintWriter writer, Map<String, PlaneRequest> profile) {
    profile.values().stream()
        .sorted((r1, r2) -> Double.compare(r2.getIntensivity(), r1.getIntensivity()))
        .forEach(req -> {
          writer.printf("%s,%s,%s,%d,%.3f%n",
              req.getRequestType(),
              req.getServiceName(),
              req.getPath(),
              req.getCount(),
              req.getIntensivity());
        });
  }
}