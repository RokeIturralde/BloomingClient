package businessLogic.users;

import businessLogic.users.MemberInterface;
import services.MemberFacadeREST;

/**
 * @author dani
 */
public class FactoryMember {
    public static MemberInterface get() {
        return new MemberFacadeREST();
    }
}
