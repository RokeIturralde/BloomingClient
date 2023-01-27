package businessLogic.users;

/**
 * @author dani
 */
public class FactoryUser {
    public static UserInterface get() {
        return new UserManager();
    }
}