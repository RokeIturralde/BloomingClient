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
import ui.userdata.admin.USF;
import businessLogic.user.FactoryMember;
import businessLogic.user.FactoryUser;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author dani
 */
public class SignUpWindowController {

    private TextField txtLogin;

    @FXML
    private TextField 
        txtEmail, txtFullName;
    private final String
        txtLoginPromptText = "", txtEmailPromptText = "", 
        txtFullNamePromptText = "";
    

    @FXML
    private PasswordField
        password, passwordConfirmation;
    private final String 
        passwordPromptText = "Passsworrdd pleasee", 
        passwordConfirmationPromptText = "Repeat the password";


    @FXML 
    private Button 
        btnRegister, btnClear, btnExit;
    private final String 
        btnRegisterText = "Register", 
        btnClearText = "Clear", btnExitText = "Exit";


    private Stage stage;

    private final String WINDOW_NAME = "AdminUserData"; 

    // LOGGER that will be used to note every event of the window.
    private static final Logger LOGGER =
        Logger.getLogger("package user;");
    @FXML
    private AnchorPane AnchorPane;




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
        /* boolean lesgoo = everyUserParamIsCorrect();
        
        btnRegister.setDisable(!lesgoo); */





    }

    /**
     * manage message (user already in use.)
     * @return
     */

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
            nicely = nicely && USF.isLoginFormat(txtLogin.getText());
    
        if (txtEmail.getText().isEmpty())
            txtEmail.setPromptText(txtEmailPromptText);    
        else
            nicely = nicely && USF.isFullNameFormat(txtFullName.getText());

        if (txtFullName.getText().isEmpty())
            txtFullName.setPromptText(txtFullNamePromptText);
        else
            nicely = nicely && USF.isEmailFormat(txtEmail.getText()); 

        if (password.getText().isEmpty())
            password.setPromptText(passwordPromptText);
        else
            if (passwordConfirmation.getText().isEmpty()) 
                passwordConfirmation.setPromptText(passwordConfirmationPromptText);


        nicely = nicely && (password.equals(passwordConfirmationPromptText));
                
                

        

        // TODO: are both passwords the same?

        return nicely;
    }

    private void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Initializing UserWindowController::handlerWindowShowing");

        // TODO: set all the texts
    }

    /**
     * void that initiates the whole window.
     * @param root parent 
     */
     
     public void initStage(Parent root) {
        LOGGER.info("Initializing 'SignUp' window");
        // Stablish scene
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Set properties
        stage.setTitle(WINDOW_NAME);
        stage.setResizable(false);
        stage.setOnShowing(this::handlerWindowShowing);

        stage.show();
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
