package app;

import businessLogic.album.FactoryAlbum;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import logic.objects.Album;
import ui.signIn.SignInController;

public class App extends Application {

    public static void main(String[] args) {
        List <Album> l = FactoryAlbum.getModel().findMyAllAlbums_XML(new GenericType<List<Album>>() {}, "u1");
        System.out.println(l);
        l.forEach(a -> System.out.println(a));
        
        

//launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/signIn/SignInCrud.fxml"));
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/album/UIAlbum.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the Sign In window controller
            //AlbumsViewController controller = (AlbumsViewController) loader.getController();
            SignInController controller = (SignInController) loader.getController();
            controller.setStage(stage);
            //User us = new User();
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
