package ca.jrvs.apps.jdbc.helpers;

import static ca.jrvs.apps.jdbc.helpers.JsonParser.toObjectFromJson;

import ca.jrvs.apps.jdbc.dto.Quote;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuoteHttpHelper {

  private static String apiKey = "3787dcdcd6msha9a34dafd0ebc68p16a022jsnde1946a9091c";
  private static OkHttpClient client = new OkHttpClient();
  private static final Logger logger = LogManager.getLogger("quotelog");

  /**
   * Fetch latest quote data from Alpha Vantage endpoint
   * @param symbol
   * @return Quote with latest data
   * @throws IllegalArgumentException - if no data was found for the given symbol.
   */
  public static Quote fetchQuoteInfo(String symbol) throws IllegalArgumentException {

    Request request = new Request.Builder()
        .url("https://alpha-vantage.p.rapidapi.com/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&datatype=json")
        .get()
        .addHeader("x-rapidapi-key", apiKey)
        .addHeader("x-rapidapi-host", "alpha-vantage.p.rapidapi.com")
        .build();

    try {
      Response response = client.newCall(request).execute();
      String jsonResponse = response.body().string();
      logger.info("Response JSON: {}", jsonResponse);

      Quote quote = toObjectFromJson(jsonResponse, Quote.class);
      quote.setTimestamp(Timestamp.from(Instant.now()));
      logger.info("Deserialized Quote: {}", quote);

      return quote;
    } catch (JsonMappingException e) {
      logger.error("Error mapping JSON for symbol: {}", symbol, e);
    } catch (JsonProcessingException e) {
      logger.error("Error processing JSON response for symbol: {}", symbol, e);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("Error fetching data for symbol: {}", symbol, e);
    }
    return null;
  }

  public QuoteHttpHelper(String apiKey, OkHttpClient client) {
    this.apiKey = apiKey;
    this.client = client;
  }

}

