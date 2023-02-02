package app;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import ui.signUp.SignUpWindowController;
import ui.userdata.admin.AdminUserDataWindowController;

public class App extends Application {

    private final String path = 
        "/ui/signUp/SignUpWindow.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = 
                new FXMLLoader(getClass().getResource(path));

            Parent root = Parent.class.cast(loader.load());
            //Obtain the window controller
            SignUpWindowController controller = 
                SignUpWindowController.class
                    .cast(loader.getController());
                    
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}