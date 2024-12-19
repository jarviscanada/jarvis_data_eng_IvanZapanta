package ca.jrvs.apps.jdbc.services;

import ca.jrvs.apps.jdbc.dao.QuoteDao;
import ca.jrvs.apps.jdbc.dto.Quote;
import ca.jrvs.apps.jdbc.helpers.QuoteHttpHelper;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuoteService {

  private QuoteDao dao;
  private QuoteHttpHelper httpHelper;
  private static final Logger infoLogger = LogManager.getLogger("infoLog");
  private static final Logger errorLogger = LogManager.getLogger("errorLog");

  public QuoteService(QuoteDao dao, QuoteHttpHelper httpHelper) {
    this.dao = dao;
    this.httpHelper = httpHelper;
  }

  /**
   * Fetches latest quote data from endpoint
   * @param ticker
   * @return Latest quote information or empty optional if ticker symbol not found
   */
  public Optional<Quote> fetchQuoteDataFromAPI(String ticker) {
    infoLogger.info("Fetching quote data from API...");
    Quote quote = httpHelper.fetchQuoteInfo(ticker);
    if(quote.getTicker() != null){
      dao.save(quote);
    } else {
      return Optional.empty();
    }
    return Optional.of(quote);
  }
}
