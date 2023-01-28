package factories;

import logic.AntiServerTestDataMember;

/**
 * @author dani
 */
public class FactoryMember {
    public static MemberInterface get() {
        return new AntiServerTestDataMember();
    }
}
