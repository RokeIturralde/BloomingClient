package businessLogic.user;

import java.util.List;

import exceptions.ClientErrorException;
import exceptions.LoginDoesNotExistException;
import exceptions.NotThePasswordException;
import logic.objects.User;



/**
 * @author dani
 */
public interface UserInterface {

    public static enum SearchParameter {
        LOGIN,
        EMAIL,
        NAME,
        STATUS,
        PRIVILEGE
    }
    
    public User signIn(String login, String password) throws LoginDoesNotExistException, NotThePasswordException;

    public List<User> find(SearchParameter sp, String value) throws ClientErrorException;


    /*
     * TODO:
     * LA IDEA ES UN SISTEMA QUE HACE LA BUSQUEDA DE FORMA MUCHO MAS AUTOMATICA.
     * LAS LLAMADAS AL MÉTODO RESULTARÍAN MUCHO MAS FACILES
     */
    public List<User> findUserByName(String name) throws ClientErrorException;

    public void editUser(User user) throws ClientErrorException;

    public List<User> findUserByStatus(String status) throws ClientErrorException;

    public void createUser(User user) throws ClientErrorException;

    public User findUserByEmail(String email) throws ClientErrorException;

    public void removeUser(String id) throws ClientErrorException;

    public User findUserByLogin(String login) throws ClientErrorException;
}
