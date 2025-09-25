package org.project.Writer;

import org.project.model.PlaneRequest;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Map;

public class TextProfileWriter implements ProfileWriter {
  
  @Override
  public void write(Map<String, PlaneRequest> profile, String filename) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
      writeHeader(writer, profile.size());
      writeBody(writer, profile);
      System.out.println("Текстовый профиль записан в файл: " + filename);
    } catch (Exception e) {
      System.err.println("Ошибка записи текстового профиля: " + e.getMessage());
      throw new RuntimeException("Failed to write text profile", e);
    }
  }
  
  private void writeHeader(PrintWriter writer, int profileSize) {
    writer.println("=== ТЕСТОВЫЙ ПРОФИЛЬ НАГРУЗКИ ===");
    writer.printf("Сгенерировано запросов: %d%n", profileSize);
    writer.println("Формат: ТипЗапроса | Сервис | Путь | Количество | Интенсивность (запросов/час)");
    writer.println("=".repeat(80));
  }
  
  private void writeBody(PrintWriter writer, Map<String, PlaneRequest> profile) {
    profile.entrySet().stream()
        .sorted((e1, e2) -> Double.compare(e2.getValue().getIntensivity(), e1.getValue().getIntensivity()))
        .forEach(entry -> {
          PlaneRequest req = entry.getValue();
          writer.printf("%-6s | %-20s | %-30s | %6d | %8.3f%n",
              req.getRequestType(),
              req.getServiceName(),
              req.getPath(),
              req.getCount(),
              req.getIntensivity());
        });
  }
}