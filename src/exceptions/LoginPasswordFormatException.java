/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author nerea
 */
public class LoginPasswordFormatException extends Exception {

    /**
     * Creates a new instance of <code>LoginPasswordFormatException</code>
     * without detail message.
     */
    public LoginPasswordFormatException() {
        super("Password can't contain blank spaces");
    }

    /**
     * Constructs an instance of <code>LoginPasswordFormatException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public LoginPasswordFormatException(String msg) {
        super(msg);
    }
}
