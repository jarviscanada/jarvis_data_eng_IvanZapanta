package ca.jrvs.apps.jdbc.dao;

import ca.jrvs.apps.jdbc.dto.Position;
import java.sql.Connection;
import java.util.Optional;

public class PositionDao implements CrudDao<Position, String> {

  private Connection c;

  //implement all inherited methods
  //you are not limited to methods defined in CrudDao

  @Override
  public Position save(Position entity) throws IllegalArgumentException {
    return null;
  }

  @Override
  public Optional<Position> findById(String s) throws IllegalArgumentException {
    return Optional.empty();
  }

  @Override
  public Iterable<Position> findAll() {
    return null;
  }

  @Override
  public void deleteById(String s) throws IllegalArgumentException {

  }

  @Override
  public void deleteAll() {

  }

}