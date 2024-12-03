package ca.jrvs.apps.jdbc.dao;

import ca.jrvs.apps.jdbc.dto.Position;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PositionDao implements CrudDao<Position, String> {

  private Connection c;

  private static final Logger logger = LogManager.getLogger("quotelog");

  private static final String GET_ONE = "SELECT symbol, number_of_shares, value_paid FROM position WHERE symbol = ?";

  private static final String INSERT = "INSERT INTO position (symbol, number_of_shares, value_paid) VALUES (?, ?, ?)";

  private static final String UPDATE = "UPDATE position SET number_of_shares = ?, value_paid = ? WHERE symbol = ?";

  private static final String GET_ALL = "SELECT symbol, number_of_shares, value_paid FROM position";

  private static final String DELETE_ONE = "DELETE FROM position WHERE symbol = ?";

  private static final String DELETE_ALL = "DELETE FROM position";

  @Override
  public Position save(Position entity) throws IllegalArgumentException {
    Position position;

    if(findById(entity.getTicker()).isPresent()){
      logger.info("Updating '{}' position.", entity.getTicker());
      position = update(entity);
    } else {
      logger.info("Creating '{}' position.", entity.getTicker());
      position = create(entity);
    }
    return position;
  }

  public Position create(Position entity){
    try(PreparedStatement statement = this.c.prepareStatement(INSERT)){
      statement.setString(1,entity.getTicker());
      statement.setInt(2,entity.getNumOfShares());
      statement.setDouble(3,entity.getValuePaid());
      statement.execute();
      return entity;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Position update(Position entity){
    try(PreparedStatement statement = this.c.prepareStatement(UPDATE)){
      statement.setInt(1,entity.getNumOfShares());
      statement.setDouble(2,entity.getValuePaid());
      statement.setString(3,entity.getTicker());
      statement.execute();
      return entity;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Optional<Position> findById(String s) throws IllegalArgumentException {
    Position position = null;
    logger.info("Finding position by id");
    try(PreparedStatement statement = this.c.prepareStatement(GET_ONE)){
      statement.setString(1, s);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()){
        position = new Position();
        position.setTicker(resultSet.getString("symbol"));
        position.setNumOfShares(resultSet.getInt("number_of_shares"));
        position.setValuePaid(resultSet.getDouble("value_paid"));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return Optional.ofNullable(position);
  }

  @Override
  public Iterable<Position> findAll() {
    List<Position> positions = new ArrayList<>();
    logger.info("Finding all positions.");
    try(PreparedStatement statement = this.c.prepareStatement(GET_ALL)){
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()){
        Position position = new Position();
        position.setTicker(resultSet.getString("symbol"));
        position.setNumOfShares(resultSet.getInt("number_of_shares"));
        position.setValuePaid(resultSet.getDouble("value_paid"));
        positions.add(position);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return positions;
  }

  @Override
  public void deleteById(String s) throws IllegalArgumentException {
    logger.info("Deleting position by id.");
    try(PreparedStatement statement = this.c.prepareStatement(DELETE_ONE)){
      statement.setString(1, s);
      statement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void deleteAll() {
    logger.info("Deleting all positions");
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