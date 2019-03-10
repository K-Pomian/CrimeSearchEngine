/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

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
public class GUIImplementationTest extends junit.framework.TestCase {
    
    public GUIImplementationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Tests if checkCharacters() method works appropriately
     */
    @Test
    public void testCheckCharacters() {
        String goodSample1 = "53.5";
        String goodSample2 = "-1.43";
        String wrongSample1 = "1.1.1";
        String wrongSample2 = "abcdefghxyz";
        String wrongSample3 = "@53.1&";
        
        try {
            GUIImplementation.checkCharacters(goodSample1);
            GUIImplementation.checkCharacters(goodSample2);
            assertTrue(true);
        } catch (InvalidCharacterException ice) {
            assertTrue(false);
        }
        
        try {
            GUIImplementation.checkCharacters(wrongSample1);
            assertTrue(false);
        } catch (InvalidCharacterException ice) {
            assertTrue(true);
        }
        
        try {
            GUIImplementation.checkCharacters(wrongSample2);
            assertTrue(false);
        } catch (InvalidCharacterException ice) {
            assertTrue(true);
        }
        
        try {
            GUIImplementation.checkCharacters(wrongSample3);
            assertTrue(false);
        } catch (InvalidCharacterException ice) {
            assertTrue(true);
        }
    }
}
