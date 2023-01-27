package businessLogic.album;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import services.AlbumFarcadeREST;
/**
 *
 * @author nerea
 */
public class FactoryAlbum {
     public static AlbumInterface model;

    public static AlbumInterface getModel() {
        if (model == null) {
            model = new AlbumFarcadeREST();
        }
        return model;
    }
}
