package businessLogic.user;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import antiserver.AntiServerTestDataUser;
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
