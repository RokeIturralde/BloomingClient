package businessLogic.user;

import antiserver.AntiServerTestDataMember;
/* import antiserver.AntiServerTestDataMember; */
import businessLogic.user.managers.MemberManager;

/**
 * @author dani
 */
public class FactoryMember {

    private static int SYSTEM = 0;

    public static MemberInterface get() {
        if (SYSTEM == 0)
            return new AntiServerTestDataMember();
        return new MemberManager();
    }
}
