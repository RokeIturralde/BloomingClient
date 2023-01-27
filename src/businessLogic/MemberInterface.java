package businessLogic;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author dani
 */
public interface MemberInterface {
    
    public void edit_XML(Object requestEntity) throws ClientErrorException ;

    public <T> T getEveryMember_XML(Class<T> responseType) throws ClientErrorException ;

    public <T> T getEveryUser_XML(Class<T> responseType) throws ClientErrorException ;

    public <T> T findMemberByLogin_XML(Class<T> responseType, String login) throws ClientErrorException ;

    public void create_XML(Object requestEntity) throws ClientErrorException ;

    public <T> T findMembersByPlan_XML(GenericType<T> responseType, String plan) throws ClientErrorException ;

    public void remove(String id) throws ClientErrorException ;
}