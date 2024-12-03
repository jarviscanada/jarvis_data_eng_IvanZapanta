package ca.jrvs.apps.jdbc.dao;

import ca.jrvs.apps.jdbc.dto.Quote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuoteDao implements CrudDao<Quote, String> {

  private Connection c;

  private static final Logger logger = LogManager.getLogger("quotelog");

  private static final String GET_ONE = "SELECT symbol, open, high, low, price, volume, latest_trading_day, previous_close, change, change_percent, timestamp FROM quote WHERE symbol = ?";

  private static final String INSERT = "INSERT INTO quote (symbol, open, high, low, price, volume, latest_trading_day, previous_close, change, change_percent, timestamp)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String UPDATE = "UPDATE quote SET open = ?, high = ?, low = ?, price = ?, volume = ?, latest_trading_day = ?, previous_close = ?, change = ?, change_percent = ?, timestamp = ? WHERE symbol = ?";

  private static final String GET_ALL = "SELECT symbol, open, high, low, price, volume, latest_trading_day, previous_close, change, change_percent, timestamp FROM quote";

  private static final String DELETE_ONE = "DELETE FROM quote WHERE symbol = ?";

  private static final String DELETE_ALL = "DELETE FROM quote";

  @Override
  public Quote save(Quote entity) throws IllegalArgumentException {
    Quote quote;

    if(findById(entity.getTicker()).isPresent()){
      logger.info("Updating '{}' quote.", entity.getTicker());
      quote = update(entity);
    } else {
      logger.info("Creating '{}' quote.", entity.getTicker());
      quote = create(entity);
    }
    return quote;
  }

  public Quote create(Quote entity) {
    try (PreparedStatement statement = this.c.prepareStatement(INSERT)) {
      statement.setString(1, entity.getTicker());
      statement.setDouble(2, entity.getOpen());
      statement.setDouble(3, entity.getHigh());
      statement.setDouble(4, entity.getLow());
      statement.setDouble(5, entity.getPrice());
      statement.setInt(6, entity.getVolume());
      statement.setDate(7, entity.getLatestTradingDay());
      statement.setDouble(8, entity.getPreviousClose());
      statement.setDouble(9, entity.getChange());
      statement.setString(10, entity.getChangePercent());
      statement.setTimestamp(11, entity.getTimestamp());
      statement.execute();
      return entity;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Quote update(Quote entity) {
    try (PreparedStatement statement = this.c.prepareStatement(UPDATE)) {
      statement.setDouble(1, entity.getOpen());
      statement.setDouble(2, entity.getHigh());
      statement.setDouble(3, entity.getLow());
      statement.setDouble(4, entity.getPrice());
      statement.setInt(5, entity.getVolume());
      statement.setDate(6, entity.getLatestTradingDay());
      statement.setDouble(7, entity.getPreviousClose());
      statement.setDouble(8, entity.getChange());
      statement.setString(9, entity.getChangePercent());
      statement.setTimestamp(10, entity.getTimestamp());
      statement.setString(11, entity.getTicker());
      statement.execute();
      return entity;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Optional<Quote> findById(String s) throws IllegalArgumentException {
    Quote quote = null; // Initialize quote as null to return Optional.empty() when no result is found (for create)
    logger.info("Finding quote by id.");
    try (PreparedStatement statement = this.c.prepareStatement(GET_ONE)) {
      statement.setString(1, s);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()){
        // Only populate quote if a result is found (for update)
        quote = new Quote();
        quote.setTicker(resultSet.getString("symbol"));
        quote.setOpen(resultSet.getDouble("open"));
        quote.setHigh(resultSet.getDouble("high"));
        quote.setLow(resultSet.getDouble("low"));
        quote.setPrice(resultSet.getDouble("price"));
        quote.setVolume(resultSet.getInt("volume"));
        quote.setLatestTradingDay(resultSet.getDate("latest_trading_day"));
        quote.setPreviousClose(resultSet.getDouble("previous_close"));
        quote.setChange(resultSet.getDouble("change"));
        quote.setChangePercent(resultSet.getString("change_percent"));
        quote.setTimestamp(resultSet.getTimestamp("timestamp"));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return Optional.ofNullable(quote); // Return Optional.empty() if no quote is found (quote = null -for create)
  }

  @Override
  public Iterable<Quote> findAll() {
    List<Quote> quotes = new ArrayList<>();
    logger.info("Finding all quotes.");
    try(PreparedStatement statement = this.c.prepareStatement(GET_ALL)){
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Quote quote = new Quote();
        quote.setTicker(resultSet.getString("symbol"));
        quote.setOpen(resultSet.getDouble("open"));
        quote.setHigh(resultSet.getDouble("high"));
        quote.setLow(resultSet.getDouble("low"));
        quote.setPrice(resultSet.getDouble("price"));
        quote.setVolume(resultSet.getInt("volume"));
        quote.setLatestTradingDay(resultSet.getDate("latest_trading_day"));
        quote.setPreviousClose(resultSet.getDouble("previous_close"));
        quote.setChange(resultSet.getDouble("change"));
        quote.setChangePercent(resultSet.getString("change_percent"));
        quote.setTimestamp(resultSet.getTimestamp("timestamp"));
        quotes.add(quote);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return quotes;
  }

  @Override
  public void deleteById(String s) throws IllegalArgumentException {
    logger.info("Deleting quote by id.");
    try(PreparedStatement statement = this.c.prepareStatement(DELETE_ONE)){
      statement.setString(1, s);
      statement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteAll() {
    logger.info("Deleting all quotes.");
    try(PreparedStatement statement = this.c.prepareStatement(DELETE_ALL)){
      statement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Connection getC() {
    return c;
  }

  public void setC(Connection c) {
    this.c = c;
  }
}
