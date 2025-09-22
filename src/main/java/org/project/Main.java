package org.project;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
  public static void main(String[] args) {
//    List<String[]> csvData = Arrays.asList(
//        new String[]{"POST", "UserService", "/api/products", "10"},
//        new String[]{"POST", "UserService", "/api/users", "50"},
//        new String[]{"GET", "ProductService", "/api/products", "75"},
//    new String[]{"GET", "ProductService", "/api/products", "105"}
//    );
    
    // Парсинг в Map
    File file = new File("./prod1.csv");
    Map<String, RequestData> resultMap = CsvParser.parseCsvToMap(file);
    
    // Вывод результатов
    resultMap.forEach((path, data) -> {
      System.out.println("Path: " + path + " -> " + data);
    });
  }
}