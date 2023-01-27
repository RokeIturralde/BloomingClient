/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.album;

import java.util.List;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import logic.objects.Album;

/**
 *
 * @author nerea
 */
public interface AlbumInterface {

    public void createAlbum_XML(Object requestEntity) throws ClientErrorException;

    public void updateAlbum_XML(Object requestEntity) throws ClientErrorException;

    public void removeAlbum(String id) throws ClientErrorException;

    public <T> T findAlbumByID_XML(Class<T> responseType, String id) throws ClientErrorException;

    public <T> T findMyAllAlbums_XML(GenericType<T> genericType, String userLogin) throws ClientErrorException;

    public <T> T findMyAlbumsByName_XML(Class<T> responseType, String userLogin, String name) throws ClientErrorException;

    public <T> T findMyAlbumsByDate_XML(Class<T> responseType, String userLogin, String stringDate) throws ClientErrorException;

    public <T> T findMyAllSharedAlbums_XML(Class<T> responseType, String userLogin) throws ClientErrorException;

    public <T> T findMySharedAlbumsByName_XML(Class<T> responseType, String userLogin, String name) throws ClientErrorException;

    public <T> T findMySharedAlbumsByDate_XML(Class<T> responseType, String userLogin, String stringDate) throws ClientErrorException;

    public <T> T findMySharedAlbumsByCreator_XML(Class<T> responseType, String userLogin, String creatorLogin) throws ClientErrorException;

}
