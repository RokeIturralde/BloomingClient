package businessLogic.user;

import exceptions.ClientErrorException;
import objects.Member;

import java.util.List;

/**
 *
 * @author dani
 */
public interface MemberInterface {

    public void editMember(Member member) throws ClientErrorException;

    public List<Member> getEveryMember() throws ClientErrorException;

    public List<Member> getEveryUser() throws ClientErrorException;

    public Member findMemberByLogin(String login) throws ClientErrorException;

    public void createMember(Member member) throws ClientErrorException;

    public List<Member> findMembersByPlan(String plan) throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;
}
