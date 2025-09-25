package org.project.Writer;

import org.project.model.PlaneRequest;
import java.util.Map;

public interface ProfileWriter {
  void write(Map<String, PlaneRequest> profile, String filename);
}