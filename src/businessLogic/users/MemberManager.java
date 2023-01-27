package businessLogic.users;

import java.util.Collection;
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
    public Collection<Member> getEveryMember() throws ClientErrorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Member> getEveryUser() throws ClientErrorException {
        List<Member> users = null;
        try{
            LOGGER.info("MemberManager: Finding all members from REST service (XML).");
            //Ask webClient for all departments' data.
            users = 
                webClient.getEveryUser_XML(new GenericType<List<Member>>() {});
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception finding all departments, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error finding all departments:\n"+ex.getMessage());
        }
        return users;
    }

    @Override
    public Member findMemberByLogin(String login) throws ClientErrorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void createMember(Member member) throws ClientErrorException {
        try {
            LOGGER.log(Level.INFO,"MemberManager: Creating user {0}.",member.getLogin());
            //Send user data to web client for creation. 
            webClient.create_XML(member);
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception creating user, {0}",
                    ex.getMessage());
            throw new ClientErrorException("Error creating user:\n" + ex.getMessage());
        }
        
    }

    @Override
    public Collection<Member> findMembersByPlan(String plan) throws ClientErrorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void remove(String id) throws ClientErrorException {
        // TODO Auto-generated method stub
        
    }
    
}
