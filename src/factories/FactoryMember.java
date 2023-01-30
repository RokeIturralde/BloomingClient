package factories;

import businessLogic.user.MemberManager;

/**
 * @author dani
 */
public class FactoryMember {
    public static MemberInterface get() {
        return new MemberManager();
    }
}
