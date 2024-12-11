package ca.jrvs.apps.jdbc.services;

import static org.junit.Assert.*;

import ca.jrvs.apps.jdbc.dao.PositionDao;
import ca.jrvs.apps.jdbc.dao.QuoteDao;
import ca.jrvs.apps.jdbc.dto.Quote;
import ca.jrvs.apps.jdbc.helpers.DatabaseConnectionManager;
import ca.jrvs.apps.jdbc.helpers.PropertyLoader;
import ca.jrvs.apps.jdbc.helpers.QuoteHttpHelper;
import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import okhttp3.OkHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QuoteService_IntTest {

  private QuoteDao quoteDao;
  private PositionDao positionDao;
  private QuoteHttpHelper quoteHttpHelper;
  private DatabaseConnectionManager databaseConnectionManager;
  private Connection connection;
  private QuoteService quoteService;

  @Before
  public void setUp() throws Exception {
    Map<String, String> properties = PropertyLoader.loadProperties();
    databaseConnectionManager = new DatabaseConnectionManager("localhost", "stock_quote", "postgres", "password");
    connection = databaseConnectionManager.getConnection();
    quoteDao = new QuoteDao(connection);
    String apiKey = properties.get("api-key");
    OkHttpClient okHttpClient = new OkHttpClient();
    quoteHttpHelper = new QuoteHttpHelper(apiKey, okHttpClient);
    quoteService = new QuoteService(quoteDao, quoteHttpHelper);
  }

  @After
  public void tearDown() throws Exception {
    // Close db connection after each test
    if (connection != null && !connection.isClosed()) {
      connection.close();
    }
  }

  @Test
  public void testFetchQuoteDataFromAPIValidTicker() {
    String validTicker = "TSLA";

    Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI(validTicker);

    // Check if data is fetched and saved
    assertTrue("Quote fetched and saved.", quote.isPresent());

    // Verify quote is saved to db
    Optional<Quote> savedQuote = quoteDao.findById(validTicker);
    assertNotNull("Quote should be saved in the database.", savedQuote);
    assertEquals(validTicker, savedQuote.get().getTicker());
  }

  @Test
  public void testFetchQuoteDataFromAPIInvalidTicker() {
    String invalidTicker = "INVALIDTICKER";

    Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI(invalidTicker);

    // Check if data is not fetched due to invalid ticker
    assertFalse("Quote should not be fetched for invalid ticker.", quote.isPresent());

    // Verify quote is not saved to db
    Optional<Quote> savedQuote = quoteDao.findById(invalidTicker);
    assertFalse("Quote should not be saved in the database for invalid ticker.", savedQuote.isPresent());
  }
}