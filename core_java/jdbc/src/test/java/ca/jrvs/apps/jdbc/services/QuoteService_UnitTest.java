package ca.jrvs.apps.jdbc.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.jdbc.dao.QuoteDao;
import ca.jrvs.apps.jdbc.dto.Quote;
import ca.jrvs.apps.jdbc.helpers.DatabaseConnectionManager;
import ca.jrvs.apps.jdbc.helpers.QuoteHttpHelper;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;


public class QuoteService_UnitTest {

  private QuoteDao mockQuoteDao;
  private QuoteHttpHelper mockQuoteHttpHelper;
  private DatabaseConnectionManager mockDatabaseConnectionManager;
  private Connection mockConnection;
  private QuoteService quoteService;

  @Before
  public void setup() throws SQLException {
    mockQuoteDao = mock(QuoteDao.class);
    mockQuoteHttpHelper = mock(QuoteHttpHelper.class);
    mockDatabaseConnectionManager = mock(DatabaseConnectionManager.class);
    mockConnection = mock(Connection.class);
    when(mockDatabaseConnectionManager.getConnection()).thenReturn(mockConnection);
    mockQuoteDao.setC(mockConnection);
    quoteService = new QuoteService(mockQuoteDao, mockQuoteHttpHelper);
  }

  @Test
  public void testFetchQuoteDataFromAPIValidTicker() {
    String ticker = "AAPL";
    Quote quote = new Quote();
    quote.setTicker(ticker);

    when(mockQuoteHttpHelper.fetchQuoteInfo(ticker)).thenReturn(quote);
    when(mockQuoteDao.save(any(Quote.class))).thenReturn(quote);

    Optional<Quote> actualQuote = quoteService.fetchQuoteDataFromAPI(ticker);

    assertTrue(actualQuote.isPresent());
    assertEquals(quote.getTicker(), actualQuote.get().getTicker());

    verify(mockQuoteHttpHelper, times(1)).fetchQuoteInfo(ticker);
    verify(mockQuoteDao.save(quote));
  }

//  @Test
//  public void testFetchQuoteDataFromAPI_InvalidTicker() {
//    String ticker = "INVALID";
//
//    // Mock the behavior of QuoteHttpHelper for invalid ticker
//    when(mockHttpHelper.fetchQuoteInfo(ticker)).thenReturn(null);
//
//    // Call the method under test
//    Optional<Quote> actualQuote = quoteService.fetchQuoteDataFromAPI(ticker);
//
//    // Assert that the result is empty
//    assertFalse(actualQuote.isPresent());
//
//    // Verify that the method was called once
//    verify(mockHttpHelper, times(1)).fetchQuoteInfo(ticker);
//  }
}