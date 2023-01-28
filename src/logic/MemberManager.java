package logic;

import factories.MemberInterface;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.GenericType;

import exceptions.ClientErrorException;
import logic.objects.Member;
import services.MemberFacadeREST;

public class MemberManager implements MemberInterface {
     //REST users web client
     private MemberFacadeREST webClient;
     private static final Logger LOGGER =
        Logger.getLogger("Blooming");
 
     /**
      * Create a UsersManagerImplementation object. It constructs a web client for 
      * accessing a RESTful service that provides business logic in an application
      * server.
      */
     public MemberManager(){
         webClient = new MemberFacadeREST();
     }

    @Override
    public void editMember(Member member) throws ClientErrorException {
        try {
            LOGGER.log(Level.INFO,"UsersManager: Updating user {0}.", member.getLogin());
            webClient.edit_XML(member);
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception updating member, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error updating user:\n" + ex.getMessage());
        }
    }

    @Override
    public List<Member> getEveryMember() throws ClientErrorException {
        List<Member> users = null;
        try{
            LOGGER.info("MemberManager: Finding all members from REST service (XML).");
            //Ask webClient for all members' data.
            users = 
                webClient.getEveryMember_XML(new GenericType<List<Member>>() {});
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception finding all members, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error finding all members:\n"+ex.getMessage());
        }
        return users;
    }

    @Override
    public List<Member> getEveryUser() throws ClientErrorException {
        List<Member> users = null;
        try{
            LOGGER.info("MemberManager: Finding all users (as members) from REST service (XML).");
            //Ask webClient for all members' data.
            users = 
                webClient.getEveryUser_XML(new GenericType<List<Member>>() {});
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception finding all users (as members), {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error finding all users (as members):\n"+ex.getMessage());
        }
        return users;
    }

    @Override
    public Member findMemberByLogin(String login) throws ClientErrorException {
        Member m;
        try{
            LOGGER.info("MemberManager: Finding all members by login from REST service (XML).");
            //Ask webClient for all members' data.
            m = webClient.findMemberByLogin_XML(Member.class, login);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception finding members, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error finding members:\n"+ex.getMessage());
        }
        return m;
    }

    @Override
    public void createMember(Member member) throws ClientErrorException {
        try {
            LOGGER.log(Level.INFO,"MemberManager: creating member {0}.", member.getLogin());
            //Send user data to web client for creation. 
            webClient.create_XML(member);
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception creating member, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error creating member:\n" + ex.getMessage());
        }
        
    }

    @Override
    public List<Member> findMembersByPlan(String plan) throws ClientErrorException {
        List <Member> l;
        try {
            LOGGER.info("MemberManager: Finding all members from REST service (XML).");
            //Ask webClient for all members' data.
            l = webClient.findMembersByPlan_XML(new GenericType<List<Member>>() {}, plan);
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception finding all members, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error finding all members:\n"+ex.getMessage());
        }
        return l;
    }

    @Override
    public void remove(String id) throws ClientErrorException {
        try {
            LOGGER.log(Level.INFO,"UsersManager: Deleting member {0}.",id);
           // 
           webClient.remove(id);
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception deleting member, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error finding deleting member:\n"+ex.getMessage());
        }      
    }
    
}
