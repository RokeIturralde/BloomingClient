/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Roke
 */
public class ClientErrorException extends Exception {

    /**
     * Creates a new instance of <code>ClientErrorException</code> without
     * detail message.
     */
    public ClientErrorException() {
    }

    /**
     * Constructs an instance of <code>ClientErrorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ClientErrorException(String msg) {
        super(msg);
    }
}