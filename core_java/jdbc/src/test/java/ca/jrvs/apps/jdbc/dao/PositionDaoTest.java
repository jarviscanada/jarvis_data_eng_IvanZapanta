package ca.jrvs.apps.jdbc.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import ca.jrvs.apps.jdbc.dto.Position;
import ca.jrvs.apps.jdbc.helpers.DatabaseConnectionManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PositionDaoTest {

  private PositionDao positionDao;
  private Connection connection;

  @BeforeEach
  void setUp() throws SQLException {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "stock_quote", "postgres", "password");
    connection = dcm.getConnection();
    positionDao = new PositionDao(connection);
    positionDao.setC(connection);
  }

  // Helper method to create a Position object
  private Position createTestPosition(String ticker) {
    Position position = new Position();
    position.setTicker(ticker);
    position.setNumOfShares(1000);
    position.setValuePaid(50000.00);
    return position;
  }

  @Test
  void save() {
    Position newPosition = createTestPosition("AAPL");
    Position savedPosition = positionDao.save(newPosition);

    assertNotNull(savedPosition);
    assertEquals("AAPL", savedPosition.getTicker());
    assertEquals(1000, savedPosition.getNumOfShares());
    assertEquals(50000.00, savedPosition.getValuePaid(), 0.01);
  }

  @Test
  void create() {
    Position newPosition = createTestPosition("AAPL");
    Position savedPosition = positionDao.save(newPosition);

    assertNotNull(savedPosition);
    assertEquals("AAPL", savedPosition.getTicker());
    assertEquals(1000, savedPosition.getNumOfShares());
    assertEquals(50000.00, savedPosition.getValuePaid(), 0.01);
  }

  @Test
  void update() {
    Position newPosition = createTestPosition("MSFT");
    positionDao.save(newPosition);

    newPosition.setNumOfShares(1500);
    newPosition.setValuePaid(75000.00);

    Position updatedPosition = positionDao.save(newPosition);

    assertNotNull(updatedPosition);
    assertEquals("MSFT", updatedPosition.getTicker());
    assertEquals(1500, updatedPosition.getNumOfShares());
    assertEquals(75000.00, updatedPosition.getValuePaid(), 0.01);
  }

  @Test
  void findById() {
    Optional<Position> foundPosition = positionDao.findById("MSFT");

    assertTrue(foundPosition.isPresent());
    assertEquals("MSFT", foundPosition.get().getTicker());
    assertEquals(1500, foundPosition.get().getNumOfShares());
    assertEquals(75000.00, foundPosition.get().getValuePaid(), 0.01);
  }

  @Test
  void findAll() {
    Iterable<Position> positions = positionDao.findAll();

    assertNotNull(positions);
    assertTrue(positions.iterator().hasNext());
  }

  @Test
  void deleteById() {
    positionDao.deleteById("MSFT");

    Optional<Position> foundPosition = positionDao.findById("MSFT");
    assertFalse(foundPosition.isPresent());
  }

  @Test
  void deleteAll() {
    positionDao.deleteAll();

    Iterable<Position> positions = positionDao.findAll();
    assertFalse(positions.iterator().hasNext());
  }
}