/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.customText;

import exceptions.ClientErrorException;

/**
 *
 * @author Roke
 */
public interface CustomTextInterface {

    public void edit_XML(Object requestEntity) throws ClientErrorException;

    public void create_XML(Object requestEntity) throws ClientErrorException;

    public <T> T findCustomTextById_XML(Class<T> responseType, String id) throws ClientErrorException;
}
