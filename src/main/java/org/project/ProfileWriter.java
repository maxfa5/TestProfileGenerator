package org.project;

import org.project.PlaneRequest;
import java.util.Map;

public interface ProfileWriter {
  void write(Map<String, PlaneRequest> profile, String filename);
}