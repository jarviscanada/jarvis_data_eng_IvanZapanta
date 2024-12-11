package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.controllers.StockQuoteController;
import ca.jrvs.apps.jdbc.dao.PositionDao;
import ca.jrvs.apps.jdbc.dao.QuoteDao;
import ca.jrvs.apps.jdbc.helpers.PropertyLoader;
import ca.jrvs.apps.jdbc.helpers.QuoteHttpHelper;
import ca.jrvs.apps.jdbc.services.PositionService;
import ca.jrvs.apps.jdbc.services.QuoteService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import okhttp3.OkHttpClient;

public class Main {

  public static void main(String[] args) {
    Map<String, String> properties = PropertyLoader.loadProperties();
    try {
      Class.forName(properties.get("db-class"));
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    OkHttpClient client = new OkHttpClient();
    String url = "jdbc:postgresql://"+properties.get("server")+":"+properties.get("port")+"/"+properties.get("database");
    try (Connection c = DriverManager.getConnection(url, properties.get("username"), properties.get("password"))) {
      QuoteDao qRepo = new QuoteDao(c);
      PositionDao pRepo = new PositionDao(c);
      QuoteHttpHelper rcon = new QuoteHttpHelper(properties.get("api-key"), client);
      QuoteService sQuote = new QuoteService(qRepo, rcon);

      PositionService sPos = new PositionService(pRepo, qRepo);
      StockQuoteController con = new StockQuoteController(sQuote, sPos);
      con.initClient();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}