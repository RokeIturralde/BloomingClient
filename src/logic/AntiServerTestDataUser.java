package logic;

import java.util.List;
import java.util.stream.Collectors;

import exceptions.ClientErrorException;
import factories.UserInterface;
import logic.objects.User;

public class AntiServerTestDataUser implements UserInterface {

    @Override
    public User findUserByEmail(String email) throws ClientErrorException {
        return 
            UserList.everything
            .stream()
            .filter(u -> 
                u.getEmail()
                .equalsIgnoreCase(email))
            .findFirst().get();
    }

    @Override
    public User findUserByLogin(String login) throws ClientErrorException {
        return 
            UserList.everything
            .stream()
            .filter(u -> 
                u.getLogin()
                .equals(login))
            .findFirst().get();
    }

    @Override
    public List<User> findUserByName(String name) throws ClientErrorException {
        return 
            UserList.everything
            .stream()
            .filter(u -> 
                u.getFullName()
                .toLowerCase()
                .contains(name.toLowerCase()))
            .collect(Collectors.toList());
    }

    

    @Override
    public List<User> findUserByStatus(String status) throws ClientErrorException {
        return 
            UserList.everything
            .stream()
            .filter(u -> 
                u.getStatus()
                .toString()
                .equals(status))
            .collect(Collectors.toList());
    }

 
    @Override
    public void createUser(User user) throws ClientErrorException {}
    @Override
    public void removeUser(String id) throws ClientErrorException {}
    @Override
    public void editUser(User user) throws ClientErrorException {}
}