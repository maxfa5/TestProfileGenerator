package org.project.Writer;

import org.project.model.LoadProfile;

public interface ProfileWriter {
  void write(LoadProfile loadProfile, String filename);
}