package factories;

import logic.AntiServerTestDataUser;

/**
 * @author dani
 */
public class FactoryUser {
    public static UserInterface get() {
        return new AntiServerTestDataUser();
    }
}