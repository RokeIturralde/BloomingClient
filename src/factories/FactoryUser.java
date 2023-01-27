package factories;

import logic.UserManager;

/**
 * @author dani
 */
public class FactoryUser {
    public static UserInterface get() {
        return new UserManager();
    }
}