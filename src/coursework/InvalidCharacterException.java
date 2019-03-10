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
public class InvalidCharacterException extends Exception {
    
    public InvalidCharacterException() {
        super("Longitude and/or Latitude have to be a number");
    }
    
    public InvalidCharacterException(String message) {
        super(message);
    }
}
