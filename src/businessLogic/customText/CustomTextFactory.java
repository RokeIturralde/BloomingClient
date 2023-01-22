/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.customText;

import services.CustomTextFacadeREST;

/**
 *
 * @author Roke
 */
public class CustomTextFactory {

    public static CustomTextInterface model;

    public static CustomTextInterface getModel() {
        if (model == null) {
            model = new CustomTextFacadeREST();
        }
        return model;
    }
}
