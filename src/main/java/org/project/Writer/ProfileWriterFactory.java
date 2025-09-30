package org.project.Writer;

import org.project.Writer.service.CsvProfileWriter;
import org.project.Writer.service.TextProfileWriter;

import java.util.HashMap;
import java.util.Map;

public class ProfileWriterFactory {
  private static final Map<String, ProfileWriter> writers = new HashMap<>();
  
  static {
    writers.put("txt", new TextProfileWriter());
    writers.put("csv", new CsvProfileWriter());
  }
  
  public static ProfileWriter getWriter(String format) {
    ProfileWriter writer = writers.get(format.toLowerCase());
    if (writer == null) {
      throw new IllegalArgumentException("Unsupported format: " + format +
          ". Supported formats: " + writers.keySet());
    }
    return writer;
  }
  
  public static void registerWriter(String format, ProfileWriter writer) {
    writers.put(format.toLowerCase(), writer);
  }
}