/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.membership;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author minyb
 */
public interface MembershipPlanInterface {
    
    public <T> T findPlanByName_XML(GenericType<T> responseType, String name) throws ClientErrorException;
    
    public <T> T findPlanByDuration_XML(GenericType<T> responseType, String duration) throws ClientErrorException;
    
    public void edit_XML(Object requestEntity) throws ClientErrorException;
    
    public <T> T find_XML(Class<T> responseType, String id) throws ClientErrorException;
    
    public void create_XML(Object requestEntity) throws ClientErrorException;
    
    public <T> T findPlanByPrice_XML(GenericType<T> responseType, String price) throws ClientErrorException;
    
    public <T> T findAll_XML(GenericType<T> responseType) throws ClientErrorException;
    
    public void remove(String id) throws ClientErrorException;
    
}
