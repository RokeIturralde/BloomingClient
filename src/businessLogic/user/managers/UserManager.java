package businessLogic.user.managers;

import businessLogic.user.UserInterface;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.GenericType;

import exceptions.ClientErrorException;
import exceptions.LoginDoesNotExistException;
import exceptions.NotThePasswordException;
import objects.User;
import services.UserFacadeREST;

public class UserManager implements UserInterface {

    //REST users web client
    private UserFacadeREST webClient;
    private static final Logger LOGGER
            = Logger.getLogger("Blooming");

    /**
     * Create a UserManagerImplementation object. It constructs a web client for
     * accessing a RESTful service that provides business logic in an
     * application server.
     */
    public UserManager() {
        webClient = new UserFacadeREST();
    }

    // TODO: if there's time do it inside
    @Override
    public User signIn(String login, String password) /*throws LoginDoesNotExistException, NotThePasswordException*/ {
        User u = null;
        try {
            LOGGER.info("User manager: Sign in user with login " + login);
            u = webClient.signIn_XML(User.class, login, password);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,
                    "UserManager: Exception all members by name, {0}",
                    e.getMessage());
        }
        return u;
    }

    @Override
    public List<User> find(SearchParameter sp, String value) throws ClientErrorException {

        // TODO:
        return null;
    }

    @Override
    public List<User> findUserByName(String name) throws ClientErrorException {
        List<User> l;
        try {
            LOGGER.info("MemberManager: Finding all members by name from REST service (XML).");
            //Ask webClient for all members' data.
            l = webClient.findUserByName_XML(new GenericType<List<User>>() {
            }, name);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UserManager: Exception all members by name, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error all members by name:\n" + ex.getMessage());
        }
        return l;
    }

    @Override
    public void editUser(User user) throws ClientErrorException {
        try {
            LOGGER.log(Level.INFO, "UserManager: Updating user {0}.", user.getLogin());
            webClient.edit_XML(user);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UserManager: Exception updating user, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error updating user:\n" + ex.getMessage());
        }
    }

    @Override
    public List<User> findUserByStatus(String status) throws ClientErrorException {
        List<User> l;
        try {
            LOGGER.info("UserManager: Finding all members by status from REST service (XML).");
            //Ask webClient for all members' data.
            l = webClient.findUserByStatus_XML(new GenericType<List<User>>() {
            }, status);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UserManager: Exception members by status, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error members by status:\n" + ex.getMessage());
        }
        return l;
    }

    @Override
    public void createUser(User user) throws ClientErrorException {
        try {
            LOGGER.info("MemberManager: Finding all members by login from REST service (XML).");
            //Ask webClient for all members' data.
            webClient.create_XML(user);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UserManager: Exception finding members, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error finding members:\n" + ex.getMessage());
        }

    }

    @Override
    public User findUserByEmail(String email) throws ClientErrorException {
        User u;
        try {
            LOGGER.info("MemberManager: Finding all members by login from REST service (XML).");
            //Ask webClient for all members' data.
            u = webClient.findUserByEmail_XML(User.class, email);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UserManager: Exception finding members, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error finding members:\n" + ex.getMessage());
        }
        return u;
    }

    @Override
    public void removeUser(String id) throws ClientErrorException {
        try {
            LOGGER.log(Level.INFO, "UserManager: Deleting member {0}.", id);
            // 
            webClient.remove(id);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UserManager: Exception deleting member, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error finding deleting member:\n" + ex.getMessage());
        }
    }

    @Override
    public User findUserByLogin(String login) throws ClientErrorException {
        User u;
        try {
            LOGGER.info("MemberManager: Finding all members by login from REST service (XML).");
            //Ask webClient for all members' data.
            u = webClient.findUserByLogin_XML(User.class, login);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UserManager: Exception finding members, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error finding members:\n" + ex.getMessage());
        }
        return u;
    }


}
