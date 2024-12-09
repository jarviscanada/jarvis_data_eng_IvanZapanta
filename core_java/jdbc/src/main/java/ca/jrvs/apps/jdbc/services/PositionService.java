package ca.jrvs.apps.jdbc.services;

import ca.jrvs.apps.jdbc.dao.PositionDao;
import ca.jrvs.apps.jdbc.dao.QuoteDao;
import ca.jrvs.apps.jdbc.dto.Position;
import ca.jrvs.apps.jdbc.dto.Quote;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PositionService {

  private PositionDao dao;
  private QuoteDao quoteDao;
  private static final Logger infoLogger = LogManager.getLogger("infoLog");
  private static final Logger errorLogger = LogManager.getLogger("errorLog");

  public PositionService(PositionDao dao, QuoteDao quoteDao) {
    this.dao = dao;
    this.quoteDao = quoteDao;
  }

  /**
   * Processes a buy order and updates the database accordingly
   * @param ticker
   * @param numberOfShares
   * @param price
   * @return The position in our database after processing the buy
   */
  public Position buy(String ticker, int numberOfShares, double price) {
    infoLogger.info("Buying stock...");
    try {
      Optional<Quote> quote = quoteDao.findById(ticker);
      Optional<Position> currentPosition = dao.findById(ticker);
      // Check if symbol is valid
      if (!quote.isPresent()){
        throw new RuntimeException("Invalid stock symbol. Please try again.");
      }
      // Check if available volume is more than number of shares
      if (quote.get().getVolume() < numberOfShares && numberOfShares <= 0) {
        throw new RuntimeException("Insufficient volume or invalid number of shares ");
      }

      // If position exist, retrieve and increment, otherwise create new
      Position position;
      if (currentPosition.isPresent()) {
        position = currentPosition.get();
      } else {
        position = new Position();
      }
      position.setTicker(ticker);
      position.setNumOfShares(position.getNumOfShares() + numberOfShares);
      position.setValuePaid(position.getValuePaid() + price);
      dao.save(position);

      // Retrieve and return updated position
      Optional<Position> pos = dao.findById(ticker);
      return pos.get();
    } catch (Exception e) {
      errorLogger.error("Error occurred while buying stock. ", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * Sells all shares of the given ticker symbol
   * @param ticker
   */
  public void sell(String ticker) {
    infoLogger.info("Selling stock...");
    try {
      Optional<Position> position = dao.findById(ticker);

      if (position.isPresent()) {
        dao.deleteById(ticker);
      } else {
        throw new RuntimeException("Invalid stock symbol: " + ticker);
      }
    } catch (Exception e) {
      errorLogger.error("Error occurred while selling stock: ", e);
      throw new RuntimeException(e);
    }
  }

  public Position view(String ticker) {
    infoLogger.info("Viewing position...");
    try {
      Optional<Position> currentPosition = dao.findById(ticker);

      if (currentPosition.isPresent()) {
        return currentPosition.get();
      } else {
        throw new RuntimeException("Sorry, " + ticker + " is not available in your portfolio. Please try again!");
      }
    } catch (Exception e) {
      errorLogger.error("Error occurred while viewing stock: ", e);
      throw new RuntimeException(e);
    }
  }
}
