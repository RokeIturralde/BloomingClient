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

    private static int OPTION = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            if (OPTION == 0) {
                String path = 
                    "/ui/signUp/SignUpWindow.fxml";
                FXMLLoader loader = 
                    new FXMLLoader(getClass().getResource(path));

                Parent root = Parent.class.cast(loader.load());
                //Obtain the window controller
                SignUpWindowController controller = 
                    SignUpWindowController.class
                        .cast(loader.getController());
                        controller.setStage(stage);
                        controller.initStage(root);
            }


            else {
                String path = 
                    "/ui/userdata/admin/AdminUserDataWindow.fxml";
                FXMLLoader loader = 
                    new FXMLLoader(getClass().getResource(path));

                Parent root = Parent.class.cast(loader.load());
                //Obtain the Sign In window controller
                AdminUserDataWindowController controller = 
                    AdminUserDataWindowController.class
                        .cast(loader.getController());

                controller.setStage(stage);
                controller.initStage(root);
            }

        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}