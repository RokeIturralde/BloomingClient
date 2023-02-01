package businessLogic.user;


import businessLogic.user.managers.UserManager;

/**
 * @author dani
 */
public class FactoryUser {

    private static int SYSTEM = 0;

    public static UserInterface get() {
        if (SYSTEM == 0)
            return new AntiServerTestDataUser();
        return new UserManager();

    }
}
