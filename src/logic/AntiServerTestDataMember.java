package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.ClientErrorException;
import factories.MemberInterface;
import logic.objects.Member;
import logic.objects.User;

public class AntiServerTestDataMember implements MemberInterface {

    @Override
    public List<Member> getEveryMember() throws ClientErrorException {
        return 
            everythiyUserAsMember()
            .stream()
            .filter(m -> 
                m.getPrivilege()
                .toString()
                .equals("MEMBER"))
            .collect(Collectors.toList());
    }

    @Override
    public List<Member> getEveryUser() throws ClientErrorException {
        return everythiyUserAsMember();
    }

    @Override
    public Member findMemberByLogin(String login) throws ClientErrorException {
        return 
            everythiyUserAsMember()
            .stream()
            .filter(m -> 
                m.getLogin()
                .equals(login))
            .findFirst().get();
    }

    

    @Override
    public List<Member> findMembersByPlan(String plan) throws ClientErrorException {
        return 
            everythiyUserAsMember()
            .stream()
            .filter(m -> 
                String.valueOf(
                    m.getPlan()
                    .getId()) == plan)
            .collect(Collectors.toList());
    }

    private List <Member> everythiyUserAsMember() {
        List <Member> l = new ArrayList<Member>();

        for (User u : UserList.everything) 
            l.add(
                new Member(u.getLogin(), u.getEmail(), 
                u.getFullName(), u.getPassword(), u.getPrivilege(), 
                u.getStatus(), u.getLastPasswordChange(),
                null, null, null));

        return l;
    }

    @Override
    public void createMember(Member member) throws ClientErrorException { }
    @Override
    public void editMember(Member member) throws ClientErrorException {}
    @Override
    public void remove(String id) throws ClientErrorException {}
    
}
