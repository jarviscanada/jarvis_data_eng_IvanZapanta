package ca.jrvs.apps.jdbc.helpers;

import static ca.jrvs.apps.jdbc.helpers.JsonParser.toObjectFromJson;

import ca.jrvs.apps.jdbc.dto.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuoteHttpHelper {

  private String API_KEY = "3787dcdcd6msha9a34dafd0ebc68p16a022jsnde1946a9091c";
  private OkHttpClient client = new OkHttpClient();

  /**
   * Fetch latest quote data from Alpha Vantage endpoint
   * @param symbol
   * @return Quote with latest data
   * @throws IllegalArgumentException - if no data was found for the given symbol.
   */
  public Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException, IOException {

    Request request = new Request.Builder()
        .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&datatype=json")
        .get()
        .addHeader("x-rapidapi-key", API_KEY)
        .addHeader("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
        .build();

    try {
      Response response = client.newCall(request).execute();
      String jsonResponse = response.body().string();
//      System.out.println("Response JSON: " + jsonResponse);

      Quote quote = toObjectFromJson(jsonResponse, Quote.class);
//      System.out.println("Deserialized Quote: " + quote);

      if (quote == null) {
        throw new IllegalArgumentException("No data found for: " + symbol);
      }
      return quote;

    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Error processing JSON response", e);
    } catch (IOException e) {
      e.printStackTrace();
      throw new IOException("Error fetching data", e);
    }
  }

}

