package factories;

import antiserver.AntiServerTestDataUser;
import businessLogic.user.UserManager;

/**
 * @author dani
 */
public class FactoryUser {
    public static UserInterface get() {
        return new UserManager();
    }
}