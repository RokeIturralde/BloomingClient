package businessLogic;

import services.MemberFacadeREST;

/**
 * @author dani
 */
public class FactoryMember {
    public static MemberInterface get() {
        return new MemberFacadeREST();
    }
}
