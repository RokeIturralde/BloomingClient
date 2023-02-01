package businessLogic.user;

import businessLogic.user.managers.MemberManager;

/**
 * @author dani
 */
public class FactoryMember {

    private static int SYSTEM = -1;

    public static MemberInterface get() {
          
        return new MemberManager();
    }
}
