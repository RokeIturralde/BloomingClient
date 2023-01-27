package businessLogic.users;

import javax.ws.rs.ClientErrorException;

/**
 *
 * @author dani
 */
public interface UserInterface {
    public <T> T findUserByName_XML(Class<T> responseType, String name) throws ClientErrorException ;

    public void edit_XML(Object requestEntity) throws ClientErrorException ;

    public <T> T findUserByStatus_XML(Class<T> responseType, String status) throws ClientErrorException ;

    public void create_XML(Object requestEntity) throws ClientErrorException ;

    public <T> T findUserByEmail_XML(Class<T> responseType, String email) throws ClientErrorException ;

    public void remove(String id) throws ClientErrorException;

    public <T> T findUserByLogin_XML(Class<T> responseType, String login) throws ClientErrorException ;
}
