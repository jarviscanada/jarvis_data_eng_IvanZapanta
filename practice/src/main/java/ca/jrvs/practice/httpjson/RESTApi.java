package ca.jrvs.practice.httpjson;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RESTApi {
  private static final String symbol = "AMZN";
  private static final String API_KEY = "3787dcdcd6msha9a34dafd0ebc68p16a022jsnde1946a9091c";

  public static void main(String[] args) {
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol="+symbol+"&datatype=json"))
        .header("x-rapidapi-key", API_KEY)
        .header("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
        .method("GET", HttpRequest.BodyPublishers.noBody())
        .build();
    try {
    HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.body());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
