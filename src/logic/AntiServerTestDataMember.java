package logic;

import java.util.List;
import java.util.stream.Collectors;

import exceptions.ClientErrorException;
import factories.MemberInterface;
import logic.objects.Member;
import logic.objects.Privilege;

public class AntiServerTestDataMember implements MemberInterface {

    @Override
    public List<Member> getEveryMember() throws ClientErrorException {
        return UserList.everyUserAsMember
            .stream()
            .filter(m -> 
                m.getPrivilege()
                .equals(Privilege.MEMBER))
            .collect(Collectors.toList());
    }

    @Override
    public List<Member> getEveryUser() throws ClientErrorException {
        return UserList.everyUserAsMember;
    }

    @Override
    public Member findMemberByLogin(String login) throws ClientErrorException {
        return UserList.everyUserAsMember
            .stream()
            .filter(m -> m.getLogin()
                    .equals(login))
            .findFirst().get();
    }

    @Override
    public List<Member> findMembersByPlan(String plan) throws ClientErrorException {
        return UserList.everyUserAsMember
            .stream()
            .filter(m -> String.valueOf(
                    m.getPlan()
                            .getId()) == plan)
            .collect(Collectors.toList());
    }

    @Override
    public void createMember(Member member) throws ClientErrorException {}
    @Override
    public void editMember(Member member) throws ClientErrorException {}
    @Override
    public void remove(String id) throws ClientErrorException {}
}