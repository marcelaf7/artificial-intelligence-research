package com.marcelfiore.jungle_ai.game.ai;

import java.sql.*;

public class QDatabase {
  private String username = "ai";
  private String password = "123456";

  private String url = "jdbc:mysql://127.0.0.1/ReinforcementLearning";

  private String tableName = "QTableTest";

  private Connection con;

  public QDatabase() {
    try {
      con = null;
      Class.forName("org.mariadb.jdbc.Driver");
      con = DriverManager.getConnection(url, username, password);
    } catch (ClassNotFoundException e) {
      System.err.println("Error opening driver for the database");
      e.printStackTrace();
      System.exit(1);
    } catch (SQLException e) {
      System.err.println("Error establishing connection to database");
    }
  }

  public String getMoves(String state)  {
    try {
      Statement stmt = con.createStatement();
      ResultSet rs;

      rs = stmt.executeQuery("SELECT move from " + tableName + "WHERE state = " + state);

      //TODO parse the response

    } catch (SQLException e) {
      System.err.println("Error making a query");
      return null;
    }
    return null;
  }

  public int getQ(String state, String move) {
    //TODO implement this
    return 0;
  }

  public boolean setQ(String state, String move) {
    //TODO implement this
    return false;
  }

  //This method should only be used for testing and would never be used otherwise.
  public static void main(String[] args) {
    new QDatabase();
    System.out.println("Everything ran with no exceptions thrown");
  }


}
