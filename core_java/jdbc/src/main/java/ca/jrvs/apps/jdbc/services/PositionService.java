package ca.jrvs.apps.jdbc.services;

import ca.jrvs.apps.jdbc.dao.PositionDao;
import ca.jrvs.apps.jdbc.dao.QuoteDao;
import ca.jrvs.apps.jdbc.dto.Position;
import ca.jrvs.apps.jdbc.dto.Quote;
import java.util.NoSuchElementException;
import java.util.Optional;

public class PositionService {

  private PositionDao dao;

  private QuoteDao quoteDao;

  public PositionService(PositionDao dao, QuoteDao quoteDao) {
    this.dao = dao;
    this.quoteDao = quoteDao;
  }

  public PositionService(PositionDao dao) {
    this.dao = dao;
  }

  /**
   * Processes a buy order and updates the database accordingly
   * @param ticker
   * @param numberOfShares
   * @param price
   * @return The position in our database after processing the buy
   */
  public Position buy(String ticker, int numberOfShares, double price) {

    Optional<Quote> quote = quoteDao.findById(ticker);
    Optional<Position> currentPosition = dao.findById(ticker);

    if (!quote.isPresent()){
      throw new NoSuchElementException("Ticker not found");
    }
    if (quote.get().getVolume() < numberOfShares && numberOfShares <= 0) {
      throw new IllegalArgumentException("Insufficient volume or invalid number of shares ");
    }

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

    Optional<Position> pos = dao.findById(ticker);
    System.out.println(pos.get());
    return pos.get();
  }

  /**
   * Sells all shares of the given ticker symbol
   * @param ticker
   */
  public void sell(String ticker) {
    dao.deleteById(ticker);
  }

}
