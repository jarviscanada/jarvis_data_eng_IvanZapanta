package ca.jrvs.apps.jdbc.services;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
    String ticker = "MSFT";
    Quote mockQuote = new Quote();
    mockQuote.setTicker(ticker);
    mockQuote.setPrice(100.00);

    when(mockQuoteHttpHelper.fetchQuoteInfo(ticker)).thenReturn(mockQuote);
    when(mockQuoteDao.save(any(Quote.class))).thenReturn(mockQuote);

    Optional<Quote> actualQuote = quoteService.fetchQuoteDataFromAPI(ticker);

    assertTrue(actualQuote.isPresent());
    assertEquals(ticker, actualQuote.get().getTicker());
    assertEquals("Price of returned quote should match mock price", 100.00, actualQuote.get().getPrice(), 0.0);

    verify(mockQuoteDao).save(mockQuote);
  }

  @Test
  public void testFetchQuoteDataFromAPIInvalidTicker() {
    String invalidTicker = "INVALIDTICKER";
    Quote quote = new Quote();
    quote.setTicker(null);

    when(mockQuoteHttpHelper.fetchQuoteInfo(invalidTicker)).thenReturn(quote);

    Optional<Quote> actualQuote = quoteService.fetchQuoteDataFromAPI(invalidTicker);

    assertFalse(actualQuote.isPresent());
    assertNull("Ticker should be null", quote.getTicker());
    assertEquals("Price should be 0.0 for invalid ticker", 0.0, quote.getPrice(), 0.0);

    verify(mockQuoteDao, never()).save(any(Quote.class));

  }
}