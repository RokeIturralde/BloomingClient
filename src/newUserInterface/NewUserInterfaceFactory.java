/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newUserInterface;

import services.NewUserFacadeREST;

/**
 *
 * @author Roke
 */
public class NewUserInterfaceFactory {

    public static NewUserInterface model;

    public static NewUserInterface getModel() {
        if (model == null) {
            model = new NewUserFacadeREST();
        }
        return model;
    }
}
