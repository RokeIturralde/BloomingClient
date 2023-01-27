package businessLogic.users;


/**
 * @author dani
 */
public class FactoryMember {
    public static MemberInterface get() {
        return new MemberManager();
    }
}
