package businessLogic.users;

import java.util.List;

import exceptions.ClientErrorException;
import logic.objects.User;

/**
 * @author dani
 */
public interface UserInterface {

    public List <User> findUserByName(String name) throws ClientErrorException ;

    public void editUser(User user) throws ClientErrorException ;

    public List <User> findUserByStatus(String status) throws ClientErrorException ;

    public void createUser(User user) throws ClientErrorException ;

    public User findUserByEmail(String email) throws ClientErrorException ;

    public void removeUser(String id) throws ClientErrorException;

    public User findUserByLogin(String login) throws ClientErrorException ;
}
