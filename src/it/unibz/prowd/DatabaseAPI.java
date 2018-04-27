package it.unibz.prowd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by e7250 on 6/19/2017.
 */
public class DatabaseAPI {

  static Connection connection;
  static int connectionCount = 0;
  public DatabaseAPI(){

    System.out.println("Creating new database connection in DatabaseAPI class.");

    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println("Where is your PostgreSQL JDBC Driver? "+ "Include in your library path!");
      e.printStackTrace();
    }

    System.out.println("PostgreSQL JDBC Driver Registered.");


    try {
      connection = DriverManager.getConnection(
              "jdbc:postgresql://127.0.0.1:5432/prowd", "postgres",
              "password");
    } catch (SQLException e) {
      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
    }

    if (connection != null) {
      connectionCount++;
      System.out.println("Connection created. Static connection count: " + connectionCount);


    } else {
      System.out.println("Failed to make connection!");

    }
  }

  public Connection getConnection(){
    System.out.println("Getting static connection.");
    return this.connection;
  }
}
