/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import businessLogic.content.ContentInterface;
import exceptions.ClientErrorException;
import java.util.ResourceBundle;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import objects.Content;

/**
 * Jersey REST client generated for REST resource:ContentFacadeREST
 * [entities.content]<br>
 * USAGE:
 * <pre>
 *        ContentFacadeREST client = new ContentFacadeREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author 2dam
 */
public class ContentFacadeREST implements ContentInterface {

    private WebTarget webTarget;
    private Client client;
    private static final ResourceBundle bundle = ResourceBundle.getBundle("files.URLCredentials");
    private static final String BASE_URI = bundle.getString("BASE_URI");

    public ContentFacadeREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.content");
    }

    public <T> T findContentByDate_XML(GenericType<T> responseType, String date) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("date/{0}", new Object[]{date}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new ClientErrorException("An error ocurred when trying to find a content by date: " + e.getMessage());
        }
    }

    public <T> T findContentByDate_JSON(Class<T> responseType, String date) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("date/{0}", new Object[]{date}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T findContentByLocation_XML(GenericType<T> responseType, String contentLocation) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findByLocation/{0}", new Object[]{contentLocation}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new ClientErrorException("An error ocurred when trying to find a content by location: " + e.getMessage());
        }
    }

    public <T> T findContentByLocation_JSON(Class<T> responseType, String contentLocation) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findByLocation/{0}", new Object[]{contentLocation}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T findContentById_XML(Class<T> responseType, String id) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new ClientErrorException("An error ocurred when trying to find a content by id: " + e.getMessage());
        }
    }

    public <T> T findContentById_JSON(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T findAllContents_XML(GenericType<T> responseType) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new ClientErrorException("An error ocurred when trying to find all contents: " + e.getMessage());
        }
    }

    public <T> T findAllContents_JSON(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void remove(String id) throws ClientErrorException {
        try {
            webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{id})).request().delete(Content.class);
        } catch (Exception e) {
            throw new ClientErrorException("An error ocurred when trying to remove a content: " + e.getMessage());
        }
    }

    public <T> T findContentByName_XML(GenericType<T> responseType, String name) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findByName/{0}", new Object[]{name}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new ClientErrorException("An error ocurred when trying to find a content by name: " + e.getMessage());
        }
    }

    public <T> T findContentByName_JSON(Class<T> responseType, String name) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findByName/{0}", new Object[]{name}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public <T> T findContentByAlbum_XML(Class<T> responseType, String albumId) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("findByAlbum/{0}", new Object[]{albumId}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new ClientErrorException("An error ocurred when trying to find a content by album: " + e.getMessage());
        }
    }

    public <T> T findContentByAlbum_JSON(Class<T> responseType, String albumId) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findByAlbum/{0}", new Object[]{albumId}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }

}
