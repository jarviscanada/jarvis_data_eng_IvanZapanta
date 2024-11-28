package ca.jrvs.apps.jdbc.helpers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonParser {

  /**
   * Converts a JSON to an object of specified class type.
   *
   * @param json JSON to be deserialized
   * @param clazz class type to map the JSON to
   * @param <T> type of object to return
   * @return deserialized object
   * @throws IOException if error occurs in deserialization
   */
  public static <T> T toObjectFromJson(String json, Class clazz) throws IOException{
    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // Unwraps root element of the JSON (e.g., Global Quote)
    objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    return (T) objectMapper.readValue(json, clazz);
  }

}
