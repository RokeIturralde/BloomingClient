/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.album;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author nerea
 */
public interface AlbumInterface {

    public void createAlbum_XML(Object requestEntity) throws ClientErrorException;

    public void updateAlbum_XML(Object requestEntity) throws ClientErrorException;

    public void removeAlbum(String id) throws ClientErrorException;

    public <T> T findAlbumByID_XML(Class<T> objectClass, String id) throws ClientErrorException;

    public <T> T findMyAllAlbums_XML(GenericType<T> genericType, String userLogin) throws ClientErrorException;

    public <T> T findMyAlbumsByName_XML(GenericType<T> genericType, String userLogin, String name) throws ClientErrorException;

    public <T> T findMyAlbumsByDate_XML(GenericType<T> genericType, String userLogin, String stringDate) throws ClientErrorException;

    public <T> T findMyAllSharedAlbums_XML(GenericType<T> genericType, String userLogin) throws ClientErrorException;

    public <T> T findMySharedAlbumsByName_XML(GenericType<T> genericType, String userLogin, String name) throws ClientErrorException;

    public <T> T findMySharedAlbumsByDate_XML(GenericType<T> genericType, String userLogin, String stringDate) throws ClientErrorException;

    public <T> T findMySharedAlbumsByCreator_XML(GenericType<T> genericType, String userLogin, String creatorLogin) throws ClientErrorException;

}
