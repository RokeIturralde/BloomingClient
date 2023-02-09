/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import businessLogic.customImage.CustomImageInterface;
import exceptions.ClientErrorException;
import java.util.ResourceBundle;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import objects.CustomImage;

/**
 * Jersey REST client generated for REST resource:CustomImageFacadeREST
 * [entities.customimage]<br>
 * USAGE:
 * <pre>
 *        CustomImageFacadeREST client = new CustomImageFacadeREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author 2dam
 */
public class CustomImageFacadeREST implements CustomImageInterface {

    private WebTarget webTarget;
    private Client client;
    private static final ResourceBundle bundle = ResourceBundle.getBundle("files.URLCredentials");
    private static final String BASE_URI = bundle.getString("BASE_URI");

    public CustomImageFacadeREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entities.customimage");
    }

    public void edit_XML(Object requestEntity) throws ClientErrorException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), CustomImage.class);
        } catch (Exception e) {
            throw new ClientErrorException("An error ocurred when trying to edit a custom image: " + e.getMessage());
        }
    }

    public void edit_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public void createCustomImage_XML(Object requestEntity) throws ClientErrorException {
        try {
            webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), CustomImage.class);
        } catch (Exception e) {
            throw new ClientErrorException("An error ocurred when trying to create a custom image: " + e.getMessage());
        }
    }

    public void createCustomImage_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public <T> T findCustomTextById_XML(Class<T> responseType, String id) throws ClientErrorException {
        try {
            WebTarget resource = webTarget;
            resource = resource.path(java.text.MessageFormat.format("customImageFindId/{0}", new Object[]{id}));
            return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
        } catch (Exception e) {
            throw new ClientErrorException("An error ocurred when trying to find a custom text by its id: " + e.getMessage());
        }
    }

    public <T> T findCustomTextById_JSON(Class<T> responseType, String id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("customImageFindId/{0}", new Object[]{id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }

}
