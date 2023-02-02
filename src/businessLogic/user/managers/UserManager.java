package businessLogic.user.managers;

import businessLogic.user.FactoryMember;
import businessLogic.user.UserInterface;
import encrypt.Cryptology;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;

import exceptions.ClientErrorException;
import exceptions.LoginDoesNotExistException;
import exceptions.NotThePasswordException;
import objects.Member;
import objects.User;
import services.UserFacadeREST;

public class UserManager implements UserInterface {

    // REST users web client
    private UserFacadeREST webClient;
    private static final Logger LOGGER = Logger.getLogger("Blooming");

    /**
     * Create a UserManagerImplementation object. It constructs a web client for
     * accessing a RESTful service that provides business logic in an
     * application server.
     */
    public UserManager() {
        webClient = new UserFacadeREST();
    }

    

    @Override
    public List<User> findUserByName(String name) throws ClientErrorException {
        List<User> l;
        try {
            LOGGER.info("MemberManager: Finding all members by name from REST service (XML).");
            // Ask webClient for all members' data.
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
            // Ask webClient for all members' data.
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
            // Ask webClient for all members' data.
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
            // Ask webClient for all members' data.
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
            // Ask webClient for all members' data.
            u = webClient.findUserByLogin_XML(User.class, login);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UserManager: Exception finding members, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error finding members:\n" + ex.getMessage());
        }
        return u;
    }

    @Override
    public List<User> findUserByPrivilege(String privilege) throws ClientErrorException {

        List <User> listMembers = new LinkedList <User> ();
        
        FactoryMember.get().getEveryMember()
            .stream()
            .filter(m -> 
                m.getPrivilege().toString().equalsIgnoreCase(privilege))
                .forEach(m -> {
                    if (m.getPrivilege().toString().equalsIgnoreCase("member")) {
                        listMembers.add(
                            new User(
                                m.getLogin(), m.getEmail(), 
                                m.getFullName(), m.getPassword(), 
                                m.getPrivilege(), m.getStatus(), 
                                m.getLastPasswordChange())
                        );
                    }
                    else
                        listMembers.add(User.class.cast(m));

                });

        return listMembers;
    }


    
    // FactoryUser.get().signIn("login", "password");

    @Override
    public User signIn(String login, String password) throws LoginDoesNotExistException, NotThePasswordException {
        User u;
        try {
            LOGGER.info("User manager: attemting to log user with login=" + login);

            if (findUserByLogin(login) == null) // not found
                throw new LoginDoesNotExistException();
            else 
                u = webClient.signIn_XML(User.class, login, password);
            
            if (u == null)
                throw new NotThePasswordException();
            else
                return u;

            
        } catch (ClientErrorException ce) {
            // error searching user
        } 
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UserManager: Error finding members, {0}",
                    ex.getMessage());
        }
        
        return null;
    }

}
