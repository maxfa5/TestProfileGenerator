package org.project;

import org.project.Writer.service.ProfileExportService;
import org.project.model.LoadProfile;
import org.project.model.RequestData;
import org.project.service.CsvParserService;
import org.project.service.LoadProfileGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Map;

import static java.lang.System.exit;

@SpringBootApplication
public class Main implements ApplicationRunner {
  
  @Autowired
  private LoadProfileGeneratorService loadProfileGeneratorService;
  
  @Autowired
  private ProfileExportService exportService;
  
  @Value("${app.statistic.path}")
  private String pathToStatistic;
  
  @Autowired
  private CsvParserService csvParserService;
  
  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }
  
  @Override
  public void run(ApplicationArguments args) throws Exception {
    try {
      File file = new File(pathToStatistic);
      Map<String, RequestData> dataMap = csvParserService.parseCsvToMap(file);
      LoadProfile loadProfile = loadProfileGeneratorService.generateTestProfile(dataMap);
      exportService.exportToFormats(loadProfile, "./test_profile");
      
      System.out.println("Профиль нагрузки успешно сгенерирован и экспортирован!");
      exit(0);
    } catch (Exception e) {
      System.err.println("Ошибка: " + e.getMessage());
      e.printStackTrace();
    }
  }
}