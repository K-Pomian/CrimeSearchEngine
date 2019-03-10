/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

/**
 *
 * @author kpomian
 */
public class NoResultsException extends Exception {
    
    public NoResultsException() {
        super("ResultSet contains no results");
    }
    
    public NoResultsException(String message) {
        super(message);
    }
}
