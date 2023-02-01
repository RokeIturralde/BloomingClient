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
import ui.userdata.admin.AUDW;
import businessLogic.user.FactoryMember;
import businessLogic.user.FactoryUser;

/**
 *
 * @author dani
 */
public class SignUpController {

    @FXML
    private TextField 
        txtLogin, txtEmail, txtFullName;
    private final String
        txtLoginPromptText = "", txtEmailPromptText = "", 
        txtFullNamePromptText = "";
    

    @FXML
    private PasswordField
        password, passwordConfirmation;
    private final String 
        passwordPromptText = "Passsworrdd pleasee", 
        passwordConfirmationPromptText = "Repeat the password";


    @FXML Button 
        btnRegister, btnClear, btnExit;
    private final String 
        btnRegisterText = "Register", 
        btnClearText = "Clear", btnExitText = "Exit";



    private Stage stage;

    private final String WINDOW_NAME = "AdminUserData"; 

    // LOGGER that will be used to note every event of the window.
    private static final Logger LOGGER =
        Logger.getLogger("package user;");




    public void setStage(Stage stage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void initSignUp(Parent root) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    /**
     * HANLERS OF EVERY POSSIBLE CHANGE IN THE WINDOW ------------------
     */

     /**
      * if any text from the window changes, go for it.
      * @param observableValue
      * @param oldvalue
      * @param newValue
      */

     private void handleTextChanged(
    ObservableValue observableValue,
    String oldvalue, String newValue) {


    }

    /**
     * TODO: checking should be correct
     * puts every prompt text
     * @return true if every user param is full,
     * and with the correct format.
     */

     private boolean everyUserParamIsCorrect() {
        boolean 
            nicely = true;

        // text checkings

        if (txtLogin.getText().isEmpty())
            txtLogin.setPromptText(txtLoginPromptText);
        else 
            nicely = nicely && AUDW.isLoginFormat(txtLogin.getText());
    
        if (txtEmail.getText().isEmpty())
            txtEmail.setPromptText(txtEmailPromptText);    
        else
            nicely = nicely && AUDW.isFullNameFormat(txtFullName.getText());

        if (txtFullName.getText().isEmpty())
            txtFullName.setPromptText(txtFullNamePromptText);
        else
            nicely = nicely && AUDW.isEmailFormat(txtEmail.getText()); 

        /* if (password.getText().isEmpty())
            password.setPromptText(passwordPromptText);
        else
            if (passwordConfirmation.getText().isEmpty()) {
                passwordConfirmation.setPromptText(passwordConfirmationPromptText);
                nicely 
            }
                
            else */
                

        

        // TODO: are both passwords the same?

        return nicely;
    }

    @FXML
    private void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Initializing UserWindowController::handlerWindowShowing");
    }

    

    /**
     * checks if the spaces have values
     * @return
     */
    private boolean everyUserParamIsEmpty() {
        return 
            txtLogin.getText().isEmpty() &&
            txtEmail.getText().isEmpty() &&
            txtFullName.getText().isEmpty() &&

            password.getText().isEmpty() &&
            passwordConfirmation.getText().isEmpty();
    }

    @FXML
    private void handlerRegisterButtonAction() {
        
    }


    @FXML
    private void handlerClearButtonAction() {

    }

    @FXML
    private void handlerExitButtonAction() {

    }
}
