package businessLogic.user;

import businessLogic.user.managers.UserManager;

/**
 * @author dani
 */
public class FactoryUser {
    public static UserInterface get() {
        return new UserManager();
    }
}