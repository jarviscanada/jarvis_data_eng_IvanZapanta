package ca.jrvs.practice.learningjdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCExecutor {

  public static void main(String[] args) {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);

      // Deleting data
      Customer customer = customerDAO.findById(10000);
      customerDAO.delete(customer.getId());

      // Updating data
//      Customer customer = customerDAO.findById(10000);
//      System.out.println(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getEmail());
//      customer.setEmail("update_email_test@gmail.com");
//      customer = customerDAO.update(customer);
//      System.out.println(customer.getFirstName() + " " + customer.getLastName() + " " + customer.getEmail());

      // Reading data
//      Customer customer = customerDAO.findById(10000);
//      System.out.println(customer.getFirstName() + " " + customer.getLastName());

      // Creating data
//      Customer customer = new Customer();
//      customer.setFirstName("Ivan");
//      customer.setLastName("Zapanta");
//      customer.setEmail("abc@gmail.com");
//      customer.setPhone("437-123-4567");
//      customer.setAddress("1234 Bay St.");
//      customer.setCity("Toronto");
//      customer.setState("ON");
//      customer.setZipCode("12345");
//      customerDAO.create(customer);

      //Initial connection test
//      Statement statement = connection.createStatement();
//      ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM CUSTOMER");
//      while (resultSet.next()){
//        System.out.println(resultSet.getInt(1));
//      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
