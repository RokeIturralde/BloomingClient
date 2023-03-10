/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.content;

import exceptions.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Roke
 */
public interface ContentInterface {

    public <T> T findContentByDate_XML(GenericType<T> responseType, String date) throws ClientErrorException;

    public <T> T findContentByLocation_XML(GenericType<T> responseType, String contentLocation) throws ClientErrorException;

    public <T> T findContentById_XML(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findAllContents_XML(GenericType<T> responseType) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

    public <T> T findContentByName_XML(GenericType<T> responseType, String name) throws ClientErrorException;

    public <T> T findContentByAlbum_XML(Class<T> responseType, String albumId) throws ClientErrorException;
}
