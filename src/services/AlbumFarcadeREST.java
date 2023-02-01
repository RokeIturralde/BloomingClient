/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import businessLogic.album.AlbumInterface;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 * Jersey REST client generated for REST resource:AlbumFacadeREST
 * [entities.album]<br>
 * USAGE:
 * <pre>
 *        AlbumRESTfullClient client = new AlbumRESTfullClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author nerea
 */
public class AlbumFarcadeREST implements AlbumInterface{

    private final WebTarget webTarget;
    private final Client client;
    //Mover a Archivo de Propiedades!!!!
    private static final String BASE_URI = "http://localhost:8080/BloomingWeb/webresources";

    public AlbumFarcadeREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.album");
    }

    /**
     *
     * @param requestEntity
     * @throws ClientErrorException
     */
    @Override
    public void updateAlbum_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void updateAlbum_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     *
     * @param <T>
     * @param genericType
     * @param userLogin
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findMyAllAlbums_XML(GenericType<T> genericType, String userLogin) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMyAllAlbums/{0}", new Object[]{userLogin}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(genericType);
    }

    public <T> T findMyAllAlbums_JSON(GenericType<T> genericType, String userLogin) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMyAllAlbums/{0}", new Object[]{userLogin}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(genericType);
    }

    /**
     *
     * @param <T>
     * @param objectClass
     * @param id
     * @return
     * @throws ClientErrorException
     */
    public <T> T findAlbumByID_XML(Class<T> objectClass, Integer id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(objectClass);
    }

    public <T> T findAlbumByID_JSON(GenericType<T> genericType, Integer id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(genericType);
    }

    /**
     *
     * @param <T>
     * @param genericType
     * @param userLogin
     * @param creatorLogin
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findMySharedAlbumsByCreator_XML(GenericType<T> genericType, String userLogin, String creatorLogin) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMySharedAlbumsByCreator/{0}/{1}", new Object[]{userLogin, creatorLogin}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(genericType);
    }

    public <T> T findMySharedAlbumsByCreator_JSON(GenericType<T> genericType, String userLogin, String creatorLogin) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMySharedAlbumsByCreator/{0}/{1}", new Object[]{userLogin, creatorLogin}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(genericType);
    }

    /**
     *
     * @param <T>
     * @param genericType
     * @param userLogin
     * @param stringDate
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findMySharedAlbumsByDate_XML(GenericType<T> genericType, String userLogin, String stringDate) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMySharedAlbumsByDate/{0}/{1}", new Object[]{userLogin, stringDate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(genericType);
    }

    public <T> T findMySharedAlbumsByDate_JSON(GenericType<T> genericType, String userLogin, String stringDate) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMySharedAlbumsByDate/{0}/{1}", new Object[]{userLogin, stringDate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(genericType);
    }

    /**
     *
     * @param requestEntity
     * @throws ClientErrorException
     */
    @Override
    public void createAlbum_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    public void createAlbum_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     *
     * @param <T>
     * @param genericType
     * @param userLogin
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findMyAllSharedAlbums_XML(GenericType<T> genericType, String userLogin) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMyAllSharedAlbums/{0}", new Object[]{userLogin}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(genericType);
    }

    public <T> T findMyAllSharedAlbums_JSON(GenericType<T> genericType, String userLogin) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMyAllSharedAlbums/{0}", new Object[]{userLogin}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(genericType);
    }

    /**
     *
     * @param <T>
     * @param genericType
     * @param userLogin
     * @param name
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findMyAlbumsByName_XML(GenericType<T> genericType, String userLogin, String name) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMyAlbumsByName/{0}/{1}", new Object[]{userLogin, name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(genericType);
    }

    public <T> T findMyAlbumsByName_JSON(GenericType<T> genericType, String userLogin, String name) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMyAlbumsByName/{0}/{1}", new Object[]{userLogin, name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(genericType);
    }

    @Override
    public void removeAlbum(Integer id) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete();
    }

    @Override
    public <T> T findMyAlbumsByDate_XML(GenericType<T> genericType, String userLogin, String stringDate) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMyAlbumsByDate/{0}/{1}", new Object[]{userLogin, stringDate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(genericType);
    }

    public <T> T findMyAlbumsByDate_JSON(GenericType<T> genericType, String userLogin, String stringDate) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMyAlbumsByDate/{0}/{1}", new Object[]{userLogin, stringDate}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(genericType);
    }

    /**
     *
     * @param <T>
     * @param genericType
     * @param userLogin
     * @param name
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findMySharedAlbumsByName_XML(GenericType<T> genericType, String userLogin, String name) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMySharedAlbumsByName/{0}/{1}", new Object[]{userLogin, name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(genericType);
    }

    public <T> T findMySharedAlbumsByName_JSON(GenericType<T> genericType, String userLogin, String name) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findMySharedAlbumsByName/{0}/{1}", new Object[]{userLogin, name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(genericType);
    }

    public void close() {
        client.close();
    }
    
}
