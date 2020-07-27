package assignment2;

import java.sql.*;

public class Assignment2 {

  // A connection to the database
  Connection connection;

  // Statement to run queries
  Statement sql;

  // Prepared Statement
  PreparedStatement ps;

  // Resultset for the query
  ResultSet rs;

  // CONSTRUCTOR
  Assignment2() {
  }

  // Using the input parameters, establish a connection to be used for this
  // session. Returns true if connection is sucessful
  public boolean connectDB(String URL, String username, String password) {
    boolean connectValue = true;
    URL = "jdbc:postgresql://db:5432/ruchesh1";
    username = "ruchesh1";
    password = "214899694";

    ////
    try {

      // Load JDBC driver
      Class.forName("org.postgresql.Driver");

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return false;

    }
    ///
    try {

      // Make the connection to the database, ****** but replace <dbname>, <username>,
      // <password> with your credentials ******
      connection = DriverManager.getConnection(URL, username, password);

    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (connection != null) {
      connectValue = true;
    } else {
      connectValue = false;
    }
    return connectValue;
  }

  // Closes the connection. Returns true if closure was sucessful
  public boolean disconnectDB() {
    boolean disconnectValue = true;
    try {
      connection.close();
      disconnectValue = true;
    } catch (SQLException e) {
      e.printStackTrace();
      disconnectValue = false;
    }
    System.out.println(disconnectValue);
    return disconnectValue;
  }

  public boolean insertCountry(int cid, String name, int height, int population) {
    boolean countryValue = true;
    String test;
    String sqlText;
    try {
      sql = connection.createStatement();
      test = "SELECT * " + " FROM country c " + " WHERE c.cid = " + cid; 
      rs = sql.executeQuery(test);
      if(rs == null) {
    	  countryValue = false;
      }
      rs.close();
      sqlText = "INSERT INTO country " + " VALUES ( " + cid + ", " +  name  + ", " + height + ", " + population + ")";
      sql.executeUpdate(sqlText);
      countryValue = true;
    } catch (SQLException e) {
      e.printStackTrace();
      countryValue = false;
    }
    return countryValue;

  }

  public int getCountriesNextToOceanCount(int oid) {
    int countryOceanValue;
    String sqlText;
    try {
      sqlText = "SELECT COUNT(*) " + " FROM oceanAccess o " + " WHERE o.oid =" + oid;
      sql.executeUpdate(sqlText);

      countryOceanValue = 0;
    } catch (SQLException e) {
      e.printStackTrace();
      countryOceanValue = -1;
    }
    return countryOceanValue;
  }

  public String getOceanInfo(int oid) {
    String oceanInfo = "";
    try {
      sql = connection.createStatement();
      String sqlText;
      sqlText = "SELECT oid, oname, depth " + " FROM ocean " + " WHERE o.oid =" + oid;
      sql.executeQuery(sqlText);
      oceanInfo += sqlText;
    } catch (SQLException e) {
      e.printStackTrace();
      oceanInfo = "";
    }
    return oceanInfo;
  }

  public boolean chgHDI(int cid, int year, float newHDI) {
    boolean chgHDI = true;
    try {
      sql = connection.createStatement();
      String sqlText;
      sqlText = "UPDATE hdi " + " SET hdi = " + newHDI + " WHERE year =" + year + " AND cid =" + cid;
      sql.executeUpdate(sqlText);
      chgHDI = true;
    } catch (SQLException e) {
      e.printStackTrace();
      chgHDI = false;
    }
    return chgHDI;
  }

  public boolean deleteNeighbour(int c1id, int c2id) {
    boolean dltNeighbour = true;
    String sqlText;
    try {
      sql = connection.createStatement();
      sqlText = "DELETE FROM neighbour " + " WHERE (country =" + c1id + " AND neighbor =" + c2id + ") OR (country ="
          + c2id + " AND neighbor =" + c1id + ")";
      sql.executeUpdate(sqlText);
      dltNeighbour = true;
    } catch (SQLException e) {
      e.printStackTrace();
      dltNeighbour = false;
    }
    return dltNeighbour;
  }

  public String listCountryLanguages(int cid) {
	  String sqlText;
	    try {
	      sql = connection.createStatement();
	      sqlText = "SELECT l.lid, l.lname, (c.population * l.lpercentage) / 100 AS population " + " FROM country c, language l " + " WHERE c.cid = " + cid + " AND l.cid = " + cid;
	      sql.executeUpdate(sqlText);
	      return sqlText;
	    } catch (SQLException e) {
	      e.printStackTrace();
	      return "";
	    }
  }

  public boolean updateHeight(int cid, int decrH) {
    boolean updateHeight = true;
    String sqlText;
    try {
      sql = connection.createStatement();
      sqlText = "UPDATE country " + " SET height = height - " + decrH + " WHERE cid = " + cid;

      sql.executeUpdate(sqlText);
      updateHeight = true;
    } catch (SQLException e) {
      e.printStackTrace();
      updateHeight = false;
    }
    return updateHeight;
  }

  public boolean updateDB() {
    boolean updateDB = true;
    String updateDBTABLE;
    String sqlText;
    String sqlText2;

    try {
      sql = connection.createStatement();
      updateDBTABLE = "CREATE TABLE mostPopulousCountries( " + " cid int," + " cname varchar(20), " + " ) ";
      sql.executeUpdate(updateDBTABLE);

      sqlText = "SELECT cid,cname " + " FROM country " + " WHERE population> 100000000 ORDER BY cid ASC ";
      rs = sql.executeQuery(sqlText);
      if (rs != null) {
        while (rs.next()) {
        	sqlText2 = "INSERT INTO mostPopulousCountries " + " VALUES (cid,'cname') ";
        }
      }
      rs.close();
      updateDB = true;
    } catch (SQLException e) {
      e.printStackTrace();
      updateDB = false;
    }
    return updateDB;
  }
}