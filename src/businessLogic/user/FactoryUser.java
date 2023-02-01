package businessLogic.user;


import antiserver.AntiServerTestDataUser;
import businessLogic.user.managers.UserManager;

/**
 * @author dani
 */
public class FactoryUser {

    private static int SYSTEM = -1;

    public static UserInterface get() {
        if (SYSTEM == 0)
            return new AntiServerTestDataUser();
        return new UserManager();

    }
}
