/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kpomian
 */
public class GUIHandlerTest extends junit.framework.TestCase {

    GUIHandler gui;

    public GUIHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        gui = new GUIHandler();
    }

    @After
    public void tearDown() {
    }
    
    /**
     * Tests if the columns in the database are the same as in a newly created table
     * and if they are in the same order
     */
    @Test
    public void testCreateTable() {
        DatabaseHandler db = null;
        try {
            db = new DatabaseHandler();
            db.handleDbConnection();
            String query = "SELECT `COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`COLUMNS` WHERE `TABLE_SCHEMA`='kpomian' AND `TABLE_NAME`='crimedata';";
            db.executeQuery(query);
            ResultSet rs = db.getResultSet();
            
            DefaultTableModel model = new DefaultTableModel();
            gui.table = new JTable(model);
            for (int i = 0; i < GUIHandler.columnNames.length; i++) {
                model.addColumn(GUIHandler.columnNames[i]);
            }
            
            rs.last();
            int expectedNumberOfColumns = rs.getRow();
            int realNumberOfColumns = gui.table.getColumnCount();
            if (expectedNumberOfColumns != realNumberOfColumns) {
                assertTrue(false);
            }
            
            int counter = 1;
            while (rs.next()) {
                assertTrue(rs.getString(counter).equals(gui.table.getColumnName(counter)));
                counter++;
            }
        } catch (NoResultsException nre) {
        } catch (SQLException slqe) {
            assertTrue(false);
        } finally {
            if (db != null) {
                db.disconnect();
            }  
        }

    }
}
