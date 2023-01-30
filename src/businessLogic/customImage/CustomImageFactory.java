/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.customImage;

import services.CustomImageFacadeREST;

/**
 *
 * @author Roke
 */
public class CustomImageFactory {
    public static CustomImageInterface model;

    public static CustomImageInterface getModel() {
        if (model == null) {
            model = new CustomImageFacadeREST();
        }
        return model;
    }
}
