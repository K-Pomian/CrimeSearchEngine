package coursework;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class DatabaseHandlerTest extends junit.framework.TestCase {

    DatabaseHandler db;

    public DatabaseHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        db = new DatabaseHandler();
    }

    @After
    public void tearDown() {
    }
    
    /**
     * Tests if the connection is valid
     */
    @Test
    public void testHandleDbConnection() {
        Connection conn = db.handleDbConnection();
        try {
            assertTrue(conn.isValid(0));
        } catch (SQLException sqle) {
            assertTrue(false);
        } finally {
            db.disconnect();

        }
    }
    
    /**
     * Tests if a query gives results
     */
    @Test
    public void testExecuteQuery() {
        String query = "SELECT * FROM `crimedata`";
        try {
            db.handleDbConnection();
            db.executeQuery(query);
            assertTrue(true);
        } catch (NoResultsException nre) {
            assertTrue(false);
        } finally {
            db.disconnect();
        }

    }
    
    /**
     * Tests if disconnect() method works
     */
    @Test
    public void testDisconnect() {
        db.handleDbConnection();
        assertTrue(db.disconnect());
    }
}
