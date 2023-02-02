/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Usuario
 */
public class NameExistException extends Exception {

    /**
     * Creates a new instance of <code>NameExistException</code> without detail
     * message.
     */
    public NameExistException() {
    }

    /**
     * Constructs an instance of <code>NameExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NameExistException(String msg) {
        super("You already have an album with that name, please change it");
    }
}
