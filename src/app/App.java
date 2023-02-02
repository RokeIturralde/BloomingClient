package app;

import changePassword.ChangePasswordController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import ui.content.ContentWindowController;
import ui.membershipPlan.admin.AdminMembershipPlanController;
import ui.signIn.SignInController;
import ui.userdata.admin.AdminUserDataWindowController;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    /*@Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("changePassword/ChangePasswordWindow.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the Sign In window controller
            ChangePasswordController controller = (ChangePasswordController) loader.getController();

            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/signIn/SignInCrud.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the Sign In window controller
            SignInController controller = (SignInController) loader.getController();

            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/membershipPlan/admin/AdminMembershipPlan.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the Sign In window controller
            AdminMembershipPlanController controller = (AdminMembershipPlanController) loader.getController();

            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
