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

      rs = stmt.executeQuery("SELECT Move from " + tableName + "WHERE State = " + state);

      String moves = "";
      while (rs.next()) {
        if (!moves.equals("")) {
          moves += ";";
        }
        moves = rs.getString("Move");
      }

      return moves;

    } catch (SQLException e) {
      System.err.println("Error querying the database for moves");
      return null;
    }
  }

  // Returns Q value from database
  // Return -1 for error or if not in database
  public int getQ(String state, String move) {
    try {
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT Q from " + tableName + "WHERE State = " + state + "AND Move = " + move);

      if (rs.next()) {
        return rs.getInt("Q");
      }
    } catch (Exception e) {
      System.err.println("Error querying the database for Q");
      return -1;
    }

    return -1;
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
