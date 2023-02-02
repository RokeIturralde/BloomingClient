/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newUserInterface;

import exceptions.ClientErrorException;

/**
 *
 * @author Roke
 */
public interface NewUserInterface {

    public <T> T recoverPassword_XML(Class<T> responseType, String email) throws ClientErrorException;

    public <T> T signIn_XML(Class<T> responseType, String login, String password) throws ClientErrorException;
}
