package ca.jrvs.apps.jdbc.services;

import ca.jrvs.apps.jdbc.dao.PositionDao;
import ca.jrvs.apps.jdbc.dto.Position;
import ca.jrvs.apps.jdbc.dto.Quote;

public class PositionService {

  private PositionDao dao;

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
    Quote quote = new Quote();
    if (quote.getVolume() >= numberOfShares && numberOfShares > 0){
      Position position = new Position();
      position.setTicker(ticker);
      position.setNumOfShares(numberOfShares);
      position.setValuePaid(price);
      dao.save(position);
    }
    return null;
  }

  /**
   * Sells all shares of the given ticker symbol
   * @param ticker
   */
  public void sell(String ticker) {
    dao.deleteById(ticker);
  }

}
