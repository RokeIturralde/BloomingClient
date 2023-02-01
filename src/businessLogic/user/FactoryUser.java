package businessLogic.user;


import businessLogic.user.managers.UserManager;

/**
 * @author dani
 */
public class FactoryUser {

    private static int SYSTEM = -1;

    public static UserInterface get() {
       
        return new UserManager();

    }
}
