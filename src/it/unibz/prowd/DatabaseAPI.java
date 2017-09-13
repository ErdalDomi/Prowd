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

    System.out.println("Creating new database connection...");

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
      System.out.println("Connection created. ");


    } else {
      System.out.println("Failed to make connection!");

    }
  }

  public Connection getConnection(){
    connectionCount++;
    System.out.println("Getting static connection " + connectionCount+" .");
    return this.connection;
  }

//  public void createTable(Profile profile){
//
//    PreparedStatement ps = null;
//    String sqlCode = "create table " + profile.name + " (";
//
//    for(Facet currentFacet : profile.facets){
//      sqlCode +=currentFacet.attr.name + " varchar, ";
//    }
//    sqlCode += "total integer, ";
//    for(Attribute currentAttribute : profile.attrs){
//      sqlCode += currentAttribute.name+"Count" + " int, ";
//    }
//
//    int attrCount = profile.attrs.size();
//    for(int i = 0; i <= attrCount; i++){
//      sqlCode += "p"+(100/attrCount*i)+" integer, ";
//      sqlCode += "e"+(100/attrCount*i)+" varchar, ";
//      sqlCode += "bin_vec"+(100/attrCount*i)+" varchar, ";
//    }
//    sqlCode = sqlCode.substring(0, sqlCode.length()-2);
//    sqlCode += ");";
//    System.out.println("SQLcode: "+sqlCode);
//
//    try {
//      ps = connection.prepareStatement(sqlCode);
//      ps.executeUpdate();
//      ps.close();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//  }
}
