package businessLogic.user;

import businessLogic.user.managers.MemberManager;

/**
 * @author dani
 */
public class FactoryMember {

    public static MemberInterface get() {
        return new MemberManager();
    }
}
