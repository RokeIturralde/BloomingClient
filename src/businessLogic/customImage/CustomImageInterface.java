/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.customImage;

import exceptions.ClientErrorException;
import javax.ws.rs.core.GenericType;
import objects.CustomImage;

/**
 *
 * @author Roke
 */
public interface CustomImageInterface {

    public void edit_XML(Object requestEntity) throws ClientErrorException;

    public void createCustomImage_XML(Object requestEntity) throws ClientErrorException;

    public <T> T findCustomTextById_XML(Class<T> responseType, String id) throws ClientErrorException;
}
