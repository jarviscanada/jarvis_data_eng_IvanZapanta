//package ca.jrvs.apps.jdbc;
//
//import ca.jrvs.apps.jdbc.dao.PositionDao;
//import ca.jrvs.apps.jdbc.dao.QuoteDao;
//import ca.jrvs.apps.jdbc.dto.Quote;
//import ca.jrvs.apps.jdbc.helpers.DatabaseConnectionManager;
//import ca.jrvs.apps.jdbc.services.PositionService;
//import ca.jrvs.apps.jdbc.services.QuoteService;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.Timestamp;
//import java.time.Instant;
//import java.util.Optional;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//public class JDBCExecutor {
//  private static final Logger logger = LogManager.getLogger("quotelog");
//
//  public static void main(String[] args) {
//    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "stock_quote",
//        "postgres", "password");
//    try {
//      Connection connection = dcm.getConnection();
//      QuoteDao quoteDao = new QuoteDao(connection);
//      PositionDao positionDao = new PositionDao(connection);
//
//      PositionService positionService = new PositionService(positionDao, quoteDao);
//      positionService.buy("JRVS",100, 1000);
//      positionService.sell("MSFT");
//
//      QuoteService quoteService = new QuoteService(quoteDao);
//      quoteService.fetchQuoteDataFromAPI("ABC");
//
//      // delete all positions
//      positionDao.deleteAll();
//
//      // delete position by id
//      positionDao.deleteById("TSLA");
//
//      // find all positions
//      Iterable<Position> position = positionDao.findAll();
//      logger.info(position);
//
//      // test create/update on position
//      Position position = new Position();
//      position.setTicker("TSLA");
//      position.setNumOfShares(500);
//      position.setValuePaid(14800.50);
//      positionDao.save(position);
//
//      // find position by id
//      Optional<Position> position = positionDao.findById("TSLA");
//      logger.info(position.get().getTicker() + " " + position.get().getNumOfShares());
//
//      // delete all quotes
//      quoteDao.deleteAll();
//
//      // delete quote by id
//      quoteDao.deleteById("CHECK");
//
//      // get all quotes
//      Iterable<Quote> quote = quoteDao.findAll();
//      logger.info(quote);
//
//      // create/update on save quote
//      Quote quote = new Quote();
//      quote.setTicker("ABC");  // Example ticker symbol
//      quote.setOpen(123.5);
//      quote.setHigh(145.50);
//      quote.setLow(188.00);
//      quote.setPrice(323.20);
//      quote.setVolume(5000000);
//      quote.setLatestTradingDay(java.sql.Date.valueOf("2024-12-01"));
//      quote.setPreviousClose(149.00);
//      quote.setChange(3.00);
//      quote.setChangePercent("2.03%");
//      quote.setTimestamp(Timestamp.from(Instant.now()));
//      quoteDao.save(quote);
//
//       getById quote
//      Optional<Quote> quote = quoteDao.findById("TSLA");
//      logger.info(quote.get().getTicker() + " " + quote.get().getPrice());
//
//      // test connection
//      Statement statement = connection.createStatement();
//      ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM quote");
//      while (resultSet.next()){
//        System.out.println(resultSet.getInt(1));
//      }
//
//    }  catch (SQLException e) {
//      throw new RuntimeException(e);
//    }
//
//  }
//}
