/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.membership;


/**
 *
 * @author minyb
 */
public class MembershipPlanFactory {
    
    public static MembershipPlanInterface model;
    
    public static MembershipPlanInterface getModel(){
        if (model == null){
            //model = new MembershipPlanFacadeREST();
        }
        return model;
    }
    
}
