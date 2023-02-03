/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import app.App;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import objects.User;
import ui.membershipPlan.admin.AdminMembershipPlanController;

/**
 *
 * @author minyb
 */
public class mainMembershipPlan extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/membershipPlan/admin/AdminMembershipPlan.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the Sign In window controller
            AdminMembershipPlanController controller = (AdminMembershipPlanController) loader.getController();

            controller.setStage(stage);
            User user = new User();
            user = null;
            controller.initStage(root, user);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
