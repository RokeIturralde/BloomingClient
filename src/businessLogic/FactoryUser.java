package businessLogic;

import services.UserFacadeREST;

/**
 * @author dani
 */
public class FactoryUser {
    public static UserInterface get() {
        return new UserFacadeREST();
    }
}