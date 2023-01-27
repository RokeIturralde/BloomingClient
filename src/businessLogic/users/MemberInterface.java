package businessLogic.users;

import businessLogic.*;
import exceptions.ClientErrorException;
import logic.objects.Member;

import java.util.Collection;

import javax.ws.rs.core.GenericType;

/**
 *
 * @author dani
 */
public interface MemberInterface {
    
    public void editMember(Member member) throws ClientErrorException ;

    public Collection<Member> getEveryMember() throws ClientErrorException ;

    public Collection<Member> getEveryUser() throws ClientErrorException ;

    public Member findMemberByLogin(String login) throws ClientErrorException ;

    public void createMember(Member member) throws ClientErrorException ;

    public Collection<Member> findMembersByPlan(String plan) throws ClientErrorException ;

    public void remove(String id) throws ClientErrorException ;
}