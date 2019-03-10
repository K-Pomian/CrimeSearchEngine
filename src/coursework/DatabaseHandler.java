/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import java.sql.*;

/**
 * Class used to connect and execute queries
 * 
 * @author kpomian
 */
public class DatabaseHandler {

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;
    private static final String username = "";
    private static final String password = "";

    /**
     * Connects with database
     * 
     * @return Connection object holding information about the connection
     */
    public Connection handleDbConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://lamp.scim.brad.ac.uk/", username, password);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe);
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
        return conn;
    }

    /**
     * Returns ResultSet of executed query
     * 
     * @return ResultSet object holding information about results of query
     */
    public ResultSet getResultSet() {
        return rs;
    }

    /**
     * Executes passed query
     * 
     * @param query Query to be executed
     */
    public void executeQuery(String query) throws NoResultsException {
        try {
            rs = stmt.executeQuery(query);
            if (rs.first() == false) {
                throw new NoResultsException();
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        }
    }
    
    /**
     * Closes established connection
     * 
     * @return true if disconnection was successful
     */
    public boolean disconnect() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException sqle) {
                System.out.println(sqle);
                return false;
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqle) {
                System.out.println(sqle);
                return false;
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqle) {
                System.out.println(sqle);
                return false;
            }
        }
        return true;
    }
}
