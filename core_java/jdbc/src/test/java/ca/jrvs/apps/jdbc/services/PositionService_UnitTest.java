package ca.jrvs.apps.jdbc.services;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.jdbc.dao.PositionDao;
import ca.jrvs.apps.jdbc.dao.QuoteDao;
import ca.jrvs.apps.jdbc.dto.Position;
import ca.jrvs.apps.jdbc.dto.Quote;
import ca.jrvs.apps.jdbc.helpers.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class PositionService_UnitTest {

  private PositionDao mockPositionDao;
  private QuoteDao mockQuoteDao;
  private DatabaseConnectionManager mockDatabaseConnectionManager;
  private Connection mockConnection;
  private PositionService positionService;

  @Before
  public void setup() throws SQLException {
    mockPositionDao = mock(PositionDao.class);
    mockQuoteDao = mock(QuoteDao.class);
    mockDatabaseConnectionManager = mock(DatabaseConnectionManager.class);
    mockConnection = mock(Connection.class);
    when(mockDatabaseConnectionManager.getConnection()).thenReturn(mockConnection);
    mockPositionDao.setC(mockConnection);
    mockQuoteDao.setC(mockConnection);
    positionService = new PositionService(mockPositionDao, mockQuoteDao);
  }

  @Test
  public void testBuy() {
    String ticker = "AAPL";
    int numberOfShares = 10;
    double price = 150.0;

    Quote mockQuote = new Quote();
    mockQuote.setTicker(ticker);
    mockQuote.setVolume(100);  // Sufficient volume

    Position existingPosition = new Position();
    existingPosition.setTicker(ticker);
    existingPosition.setNumOfShares(5);
    existingPosition.setValuePaid(700.0); // 5 * 150.0

    // Mock findById method to return existing position
    when(mockPositionDao.findById(ticker)).thenReturn(Optional.of(existingPosition));
    when(mockQuoteDao.findById(ticker)).thenReturn(Optional.of(mockQuote));


    Position result = positionService.buy(ticker, numberOfShares, price);

    // Assertions to verify functionality
    assertEquals("Ticker should match", ticker, result.getTicker());
    assertEquals("Number of shares should be updated", 15, result.getNumOfShares());
    assertEquals("Value paid should be updated", 850.0, result.getValuePaid(), 0.0);

    // Verify save was called on PositionDao
    verify(mockPositionDao).save(any(Position.class));
  }

  // Test if buy throws exception on invalid ticker
  @Test(expected = RuntimeException.class)
  public void testBuyInvalidTicker() {
    String ticker = "INVALID";
    int numberOfShares = 10;
    double price = 100.0;

    when(mockPositionDao.findById(ticker)).thenReturn(Optional.empty());

    positionService.buy(ticker, numberOfShares, price);
  }

  //  Test if buy throws exception when attempting to buy more shares than available volume
  @Test(expected = RuntimeException.class)
  public void testBuyInsufficientVolume() {
    String ticker = "AAPL";
    int numberOfShares = 200; // More than available volume
    double price = 150.0;

    Quote mockQuote = new Quote();
    mockQuote.setTicker(ticker);
    mockQuote.setVolume(100);  // Insufficient volume

    when(mockQuoteDao.findById(ticker)).thenReturn(Optional.of(mockQuote));

    positionService.buy(ticker, numberOfShares, price);
  }

  @Test
  public void testSell() {
    String ticker = "AAPL";
    Position existingPosition = new Position();
    existingPosition.setTicker(ticker);
    existingPosition.setNumOfShares(10);
    existingPosition.setValuePaid(1500.0);  // 10 * 150.0

    when(mockPositionDao.findById(ticker)).thenReturn(Optional.of(existingPosition));

    positionService.sell(ticker);

    verify(mockPositionDao).deleteById(ticker);
  }

  // Test checks if sell throws exception when trying to sell position that doesn't exist
  @Test(expected = RuntimeException.class)
  public void testSellPositionNotFound() {
    String ticker = "AAPL";

    when(mockPositionDao.findById(ticker)).thenReturn(Optional.empty());
//    when(mockPositionDao.findById(ticker)).thenReturn(Optional.of(new Position()));

    positionService.sell(ticker);
  }

  @Test
  public void testView() {
    String ticker = "AAPL";
    Position existingPosition = new Position();
    existingPosition.setTicker(ticker);
    existingPosition.setNumOfShares(10);
    existingPosition.setValuePaid(1500.0);  // 10 * 150.0

    // Mock findById
    when(mockPositionDao.findById(ticker)).thenReturn(Optional.of(existingPosition));

    Position result = positionService.view(ticker);

    assertEquals("Ticker should match", ticker, result.getTicker());
    assertEquals("Number of shares should match", 10, result.getNumOfShares());
    assertEquals("Value paid should match", 1500.0, result.getValuePaid(), 0.0);
  }

  // Test if view throws exception when position for a given ticker is not found
  @Test(expected = RuntimeException.class)
  public void testViewPositionNotFound() {
    String ticker = "AAPL";

    when(mockPositionDao.findById(ticker)).thenReturn(Optional.empty());
//    when(mockPositionDao.findById(ticker)).thenReturn(Optional.of(new Position()));

    positionService.view(ticker);
  }
}