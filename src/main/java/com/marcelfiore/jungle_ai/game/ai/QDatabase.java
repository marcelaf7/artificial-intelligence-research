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
      System.exit(1);
    }
  }

  public String getMoves(String state)  {
    try {
      Statement stmt = con.createStatement();
      ResultSet rs;

      rs = stmt.executeQuery("SELECT Move from " + tableName + " WHERE State = '" + state + "';");

      String moves = "";
      // TODO this query is only returning one move
      while (rs.next()) {
        if (!moves.equals("")) {
          moves += ";";
        }
        moves = rs.getString("Move");
      }

      return moves;

    } catch (SQLException e) {
      System.err.println("Error querying the database for moves");
      e.printStackTrace();
      return null;
    }
  }

  // Returns Q value from database
  // Return -1 for error or if not in database
  public float getQ(String state, String move) {
    try {
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT Q from " + tableName + " WHERE State = '" + state + "' AND Move = '" + move + "';");

      if (rs.next()) {
        return rs.getFloat("Q");
      }
    } catch (Exception e) {
      System.err.println("Error querying the database for Q");
      e.printStackTrace();
      return -1;
    }

    return -1;
  }

  public boolean setQ(String state, String move, double Q) {
    try {
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT Q from " + tableName + " WHERE State = '" + state + "' AND Move = '" + move + "';");

      int rowCount;
      if (rs.next()) {
        rowCount = stmt.executeUpdate("UPDATE " + tableName + " SET Q= " + Q + " WHERE State = '" + state + "' AND Move = '" + move + "';");
      } else {
        rowCount = stmt.executeUpdate("INSERT INTO " + tableName + " values ('" + state + "', '" + move + "', '" + Q + "');");
      }

      if (rowCount == 0) {
        System.err.println("Error setting Q in the database.");
        return false;
      } else {
        return true;
      }

    } catch (SQLException e) {
      System.err.println("Error setting Q in the database. Do you have the right permissions?");
      e.printStackTrace();
      return false;
    }
  }

  //This method should only be used for testing and would never be used otherwise.
  public static void main(String[] args) {
    QDatabase qd = new QDatabase();
    System.out.println("Adding state = state1, move = move1, Q = 0.354");
    qd.setQ("state1", "move1", 0.354);
    System.out.println("Adding state = state1, move = move2, Q = 0.354");
    qd.setQ("state1", "move2", 0.356);
    System.out.println("Changing Q value for state1 and move2 to 0.576");
    qd.setQ("state1", "move2", 0.576);

    System.out.println("Moves for state1 are: " + qd.getMoves("state1"));
    System.out.println("Q for state1 and move1 is: " + qd.getQ("state1", "move1"));
    System.out.println("Q for state1 and move2 is: " + qd.getQ("state1", "move2"));
  }


}
