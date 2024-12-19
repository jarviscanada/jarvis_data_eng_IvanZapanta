package ca.jrvs.apps.jdbc.services;

import static org.junit.Assert.*;

import ca.jrvs.apps.jdbc.dao.PositionDao;
import ca.jrvs.apps.jdbc.dao.QuoteDao;
import ca.jrvs.apps.jdbc.dto.Position;
import ca.jrvs.apps.jdbc.helpers.DatabaseConnectionManager;
import ca.jrvs.apps.jdbc.helpers.PropertyLoader;
import ca.jrvs.apps.jdbc.helpers.QuoteHttpHelper;
import java.sql.Connection;
import java.util.Map;
import okhttp3.OkHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PositionService_IntTest {
  private QuoteDao quoteDao;
  private PositionDao positionDao;
  private QuoteHttpHelper quoteHttpHelper;
  private DatabaseConnectionManager databaseConnectionManager;
  private Connection connection;
  private PositionService positionService;



  @Before
  public void setUp() throws Exception {
    Map<String, String> properties = PropertyLoader.loadProperties();
    databaseConnectionManager = new DatabaseConnectionManager("localhost", "stock_quote", "postgres", "password");
    connection = databaseConnectionManager.getConnection();
    quoteDao = new QuoteDao(connection);
    positionDao = new PositionDao(connection);
    String apiKey = properties.get("api-key");
    OkHttpClient okHttpClient = new OkHttpClient();
    quoteHttpHelper = new QuoteHttpHelper(apiKey, okHttpClient);
    positionService = new PositionService(positionDao, quoteDao);
  }

  @After
  public void tearDown() throws Exception {
    // Close DB connection after each test
    if (connection != null && !connection.isClosed()) {
      connection.close();
    }
  }

  @Test
  public void testBuy() {
    // Assuming AAPL is in quote table, not in position
    String ticker = "AAPL";
    int numberOfShares = 500;
    double price = 750.0;

    Position position = positionService.buy(ticker, numberOfShares, price);

    assertNotNull("Position should be created or updated", position);
    assertEquals("Ticker should match", ticker, position.getTicker());
    assertEquals("Number of shares should match", 500, position.getNumOfShares());
    assertEquals("Value paid should match", 750.0, position.getValuePaid(), 0.0);

    // Verify that the position is saved to DB
    Position savedPosition = positionDao.findById(ticker).get();
    assertEquals("Number of shares should match", 500, savedPosition.getNumOfShares());
    assertEquals("Value paid should match", 750.0, savedPosition.getValuePaid(), 0.0);

  }

  @Test
  public void testSell() {
    // Assuming MSFT is in quote table, not in position
    String ticker = "MSFT";
    int numberOfShares = 100;
    double price = 150.0;

    positionService.buy(ticker, numberOfShares, price);

    // Verify position if created in DB
    Position positionBeforeSell = positionDao.findById(ticker).get();
    assertNotNull("Position should exist before selling", positionBeforeSell);
    assertEquals("Number of shares should match", 100, positionBeforeSell.getNumOfShares());

    positionService.sell(ticker);
    // Verify position if deleted from DB
    assertFalse("Position should be deleted after selling", positionDao.findById(ticker).isPresent());
  }

  @Test
  public void testView() {
    // Assuming TSLA is in quote table, not in position
    String ticker = "TSLA";
    int numberOfShares = 50;
    double price = 100.0;

    // First buy the stock
    positionService.buy(ticker, numberOfShares, price);

    // Verify the position is available in the database
    Position position = positionService.view(ticker);
    assertNotNull("Position should be available after buy", position);
    assertEquals("Ticker should match", ticker, position.getTicker());
    assertEquals("Number of shares should match", 50, position.getNumOfShares());
    assertEquals("Value paid should match", 100.0, position.getValuePaid(), 0.0);

    // After viewing, the position should still exist in the database
    Position savedPosition = positionDao.findById(ticker).get();
    assertNotNull("Saved position should still exist", savedPosition);
    assertEquals("Number of shares should match", 50, savedPosition.getNumOfShares());
    assertEquals("Value paid should match", 100.0, savedPosition.getValuePaid(), 0.0);
  }
}