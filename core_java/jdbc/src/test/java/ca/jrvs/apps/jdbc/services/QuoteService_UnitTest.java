package ca.jrvs.apps.jdbc.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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

    when(mockQuoteHttpHelper.fetchQuoteInfo(ticker)).thenReturn(mockQuote);
    when(mockQuoteDao.save(any(Quote.class))).thenReturn(mockQuote);

    Optional<Quote> actualQuote = quoteService.fetchQuoteDataFromAPI(ticker);

    assertTrue(actualQuote.isPresent());
    assertEquals(ticker, actualQuote.get().getTicker());

    verify(mockQuoteDao).save(mockQuote);
  }

  @Test
  public void testFetchQuoteDataFromAPIInvalidTicker() {
    String ticker = "INVALIDTICKER";
    Quote quote = new Quote();
    quote.setTicker(null);

    when(mockQuoteHttpHelper.fetchQuoteInfo(ticker)).thenReturn(quote);

    Optional<Quote> result = quoteService.fetchQuoteDataFromAPI(ticker);

    assertFalse(result.isPresent());

    verify(mockQuoteDao, never()).save(any(Quote.class));
  }
}