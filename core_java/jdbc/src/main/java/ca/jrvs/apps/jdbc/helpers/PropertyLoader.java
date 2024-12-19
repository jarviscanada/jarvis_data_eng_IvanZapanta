package ca.jrvs.apps.jdbc.helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PropertyLoader {

  /**
   * Loads properties from the file path src/main/resources/properties.txt where each line contains
   * a key-value pair separated by a colon.
   *
   * @return a map containing the key-value pairs from the file
   */
  public static Map<String, String> loadProperties() {
    Map<String, String> properties = new HashMap<>();
    String filePath = "src/main/resources/properties.txt";

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] tokens = line.split(":");
        properties.put(tokens[0], tokens[1]);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

//    properties.forEach((key, value) -> System.out.println(key + ": " + value));
    return properties;


  }
}
