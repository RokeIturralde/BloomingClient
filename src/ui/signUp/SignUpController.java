/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.signUp;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.User;
import businessLogic.user.FactoryMember;
import businessLogic.user.FactoryUser;

/**
 *
 * @author dani
 */
public class SignUpController {

    @FXML
    private TextField 
        txtLogin, txtEmail, txtName;
    private final String
        txtLoginPromptText, txtEmailPrompt, txtNamePrompt 


    public void setStage(Stage stage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void initSignUp(Parent root) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
