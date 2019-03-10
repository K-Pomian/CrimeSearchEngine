/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kpomian
 */
public class DataQualityCheck {

    private static DatabaseHandler db = new DatabaseHandler();
    
    /**
     * Finds and prints into a file all records with no ID
     * 
     * @return number of records to be displayed in a pop-up message
     */
    public static int findRecordsWithNoID() {
        PrintWriter writer = null;
        int numberOfRows = 0;
        try {
            File outputFile = new File("M:\\Desktop\\SDD\\Coursework\\Coursework\\txt\\nocrimeid.txt");
            writer = new PrintWriter(new FileOutputStream(outputFile));

            String query = "SELECT * FROM `crimedata` WHERE `Crime ID` = ''";
            db.handleDbConnection();
            db.executeQuery(query);

            ResultSet rs = db.getResultSet();
            rs.last();
            numberOfRows = rs.getRow();
            writer.println("Records found with no Crime ID: " + numberOfRows);
            rs.beforeFirst();

            for (int i = 0; i < numberOfRows + 1; i++) {
                while (rs.next()) {
                    for (int j = 0; j < 12; j++) {
                        if (i == 0) {
                            writer.print("[" + GUIHandler.columnNames[j] + "]");
                        } else {
                            String information = rs.getString(j + 1);
                            writer.print("[" + information + "]");
                        }
                        if (j != 11) {
                            writer.print("-");
                        }
                    }
                    if (i == 0) {
                        writer.println();
                        rs.beforeFirst();
                    }
                    writer.println();
                    
                    break;
                }
            }
            db.disconnect();

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        } catch (SQLException sqle) {
            System.out.println(sqle);
        } catch (NoResultsException ex) {
            System.out.println(ex);
            return 0;
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return numberOfRows;
    }
    
    /**
     * Finds and prints into a file all records with duplicated ID
     * 
     * @return number of records to be displayed in a pop-up message
     */
    public static int findDuplicatedIDRecords() {
        PrintWriter writer = null;
        int numberOfRows = 0;
        try {
            File outputFile = new File("M:\\Desktop\\SDD\\Coursework\\Coursework\\txt\\duplicatecrimeid.txt");
            writer = new PrintWriter(new FileOutputStream(outputFile));

            String query = "SELECT `Crime ID`, COUNT(`Crime ID`) FROM `crimedata` GROUP BY `Crime ID` HAVING COUNT(`Crime ID`) > 1";
            db.handleDbConnection();
            db.executeQuery(query);

            ResultSet rs = db.getResultSet();
            rs.last();
            numberOfRows = rs.getRow();
            writer.println("Records found with duplicated Crime ID: " + numberOfRows);
            rs.beforeFirst();

            for (int i = 0; i < numberOfRows + 1; i++) {
                while (rs.next()) {
                    for (int j = 0; j < 2; j++) {
                        if (i == 0) {
                            writer.print("[Crime ID]-[COUNT]");
                            break;
                        } else {
                            String information = rs.getString(j + 1);
                            writer.print("[" + information + "]");
                        }
                        if (j != 1) {
                            writer.print("-");
                        }
                    }
                    if (i == 0) {
                        writer.println();
                        rs.beforeFirst();
                    }
                    writer.println();
                    
                    break;
                }
            }
            db.disconnect();

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        } catch (SQLException sqle) {
            System.out.println(sqle);
        } catch (NoResultsException ex) {
            System.out.println(ex);
            return 0;
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return numberOfRows;
    }
}
