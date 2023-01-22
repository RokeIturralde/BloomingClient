/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.content;

import services.ContentFacadeREST;

/**
 *
 * @author Roke
 */
public class ContentFactory {

    public static ContentInterface model;

    public static ContentInterface getModel() {
        if (model == null) {
            model = new ContentFacadeREST();
        }
        return model;
    }
}
