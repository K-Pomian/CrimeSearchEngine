/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonModel;

/**
 * Implements GUI with the necessary functions
 *
 * @author kpomian
 */
public class GUIImplementation {

    private static DatabaseHandler connection = new DatabaseHandler();

    /**
     * Gets information about choices of the user and passes an appropriate
     * query
     *
     * @param sample String to be matched to the records inside database
     * @param criteria Criteria of searching
     * @return Query to be executed
     */
    private static String getQuery(String sample, String criteria) throws InvalidCharacterException {
        if (sample.isEmpty()) {
            return "SELECT * FROM `crimedata` WHERE 1 = 2";
        }

        String query = "SELECT * FROM `crimedata` WHERE ";
        String limit = "LIMIT 20;";

        switch (criteria) {
            case "Longitude": {
                checkCharacters(sample);
                query += "`Longitude` LIKE '%" + sample + "%' " + getSelectedSortingCriteria() + limit;
                break;
            }
            case "Latitude": {
                checkCharacters(sample);
                query += "`Latitude` LIKE '%" + sample + "%' " + getSelectedSortingCriteria() + limit;
                break;
            }
            case "LSOA name": {
                query += "`LSOA name` LIKE '%" + sample + "%' " + getSelectedSortingCriteria() + limit;
                break;
            }
            case "Crime type": {
                query += "`Crime type` LIKE '%" + sample + "%' " + getSelectedSortingCriteria() + limit;
                break;
            }
        }

        return query;
    }
    
    /**
     * Checks if any sorting criteria has been chosen and returns piece of SQL code
     * to update the query
     * 
     * @return Piece of SQL code to update the query
     */
    private static String getSelectedSortingCriteria() {
        String sortingQuery = "ORDER BY ";
        if (GUIHandler.month.isSelected()) {
            return sortingQuery + "`Month` ";
        } else if (GUIHandler.longitude.isSelected()) {
            return sortingQuery + "`Longitude` ";
        } else if (GUIHandler.latitude.isSelected()) {
            return sortingQuery + "`Latitude` ";
        } else if (GUIHandler.LSOAname.isSelected()) {
            return sortingQuery + "`LSOA name` ";
        } else {
            return "";
        }
    }
    
    /**
     * Checks if provided string contains only numbers. It also allows to be there
     * one dot and one hyphen, but only on the beginning
     * 
     * @param sample String to be checked
     * @throws InvalidCharacterException if the string contains at least one not
     * allowed character
     */
    static void checkCharacters(String sample) throws InvalidCharacterException {
        char[] sampleToCharArray = sample.toCharArray();
        for (int i = 0; i < sampleToCharArray.length - 1; i++) {
            if (i != 0) {
                for (int j = i + 1; j < sampleToCharArray.length; j++) {
                    if (sampleToCharArray[i] == 46 && sampleToCharArray[i] == sampleToCharArray[j]) {
                        throw new InvalidCharacterException();
                    }
                }
            }
            if (i == 0) {
                if ((sampleToCharArray[i] >= 48 && sampleToCharArray[i] <= 57) || sampleToCharArray[i] == 45) {
                } else {
                    throw new InvalidCharacterException();
                }
            }

        }
    }

    /**
     * Retrieves information from database, creates a 2D array filled with that
     * data and passes the array to be used inside of the table.
     *
     * @param sample
     * @param criteria
     * @return
     */
    public static Object[][] getResults(String sample, String criteria) throws InvalidCharacterException {
        Object[][] data = null;
        try {
            String query = getQuery(sample, criteria);

            connection.handleDbConnection();
            connection.executeQuery(query);
            ResultSet rs = connection.getResultSet();

            rs.last();
            int numberOfRows = rs.getRow();
            data = new Object[numberOfRows][12];
            rs.beforeFirst();
            for (int i = 0; i < numberOfRows; i++) {
                while (rs.next()) {
                    for (int j = 0; j < 12; j++) {
                        String information = rs.getString(j + 1);
                        data[i][j] = information;
                    }
                    break;
                }
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
        } catch (NoResultsException ex) {
            System.out.println(ex);
        }
        connection.disconnect();

        return data;
    }
}
