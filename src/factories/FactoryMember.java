package factories;

import logic.MemberManager;


/**
 * @author dani
 */
public class FactoryMember {
    public static MemberInterface get() {
        return new MemberManager();
    }
}
