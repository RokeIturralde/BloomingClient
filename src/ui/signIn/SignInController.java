/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.signIn;

import ui.signUp.SignUpWindowController;
import businessLogic.album.AlbumInterface;
import businessLogic.user.FactoryUser;
import businessLogic.user.UserInterface;
import encrypt.Cryptology;
import exceptions.*;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.User;

import javax.crypto.Cipher;
import newUserInterface.NewUserInterface;
import newUserInterface.NewUserInterfaceFactory;
import objects.Privilege;
import objects.Status;

import ui.album.AlbumsViewController;
import ui.content.ContentWindowController;
import ui.membershipPlan.admin.AdminMembershipPlanController;
import ui.userdata.admin.AdminUserDataWindowController;

/**
 * Sign In FXML Controller class
 *
 * @author nerea
 */
public class SignInController {

    @FXML
    private TextField txtLogin;
    @FXML
    private PasswordField cpPassword;
    @FXML
    private Button btnRecover;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnRegister;
    @FXML
    private TextField txtLogin1;
    User user;

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("package ui.signIn");
    private UserInterface client;

    /**
     * Initializing the window method
     *
     * @param root root object with the DOM charged
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing SignIn window");
        //Creates an scene
        Scene scene = new Scene(root);
        //Establishes an scene
        stage.setScene(scene);
        //Window title
        stage.setTitle("SignIn");
        //Not resizable window
        stage.setResizable(false);
        //Set the Event handlers
        stage.setOnShowing(this::handlerWindowShowing);
        //Set the textfields with a listener
        stage.show();
    }

    /**
     * Method that handles the startup of the SignIn
     *
     * @param event event of showing the window
     */
    private void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Iniciando SignInFXMLDocumentController::handlerWindowShowing");
        //Login text field without text
        txtLogin.setText("");
        //Login text field without text
        cpPassword.setText("");
        //The Accept button is disabled
        btnSignIn.setDisable(false);
        //The Login field is focus
        txtLogin.requestFocus();
    }

    /**
     * Handle Action event on Accept button, if all goes well, the logged window
     * shows, if not, an alert is shown with the error
     *
     * @param event The action event object
     */
    @FXML
    private void handleEntrarComoAdminButtonAction(ActionEvent event) throws LoginPasswordFormatException, LoginFormatException {
        User user = new User();
        user.setLogin("loginAdmin");
        user.setEmail("login@gmail.com");
        user.setPassword("abcd*1234");
        user.setLastPasswordChange(Date.from(Instant.now()));
        user.setPrivilege(Privilege.ADMIN);
        user.setFullName("Login Admin");
        user.setStatus(Status.ENABLE);
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/membershipPlan/admin/AdminMembershipPlan.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the Sign In window controller
            AdminMembershipPlanController controller = (AdminMembershipPlanController) loader.getController();

            controller.setStage(stage);
            controller.initStage(root, user);
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleEntrarComoClientButtonAction(ActionEvent event) throws LoginPasswordFormatException, LoginFormatException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/content/ContentWindow.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the Sign In window controller
            ContentWindowController controller = (ContentWindowController) loader.getController();

            controller.setStage(stage);
            controller.initStage(root);
            
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The event which opens the Sign Up window
     *
     * @param event
     */
    @FXML
    private void handleEmailDemoButtonAction(ActionEvent event) {
        try {
            NewUserInterface userInterface = NewUserInterfaceFactory.getModel();
            userInterface.recoverPassword_XML(User.class, txtLogin1.getText());
        } catch (ClientErrorException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Text changed event handler. Validate that all the fields are not empty
     * and that they not surpass 25 characters. The Accept button is disabled if
     * either of those are not fulfilled
     *
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
     */
    private void textChanged(ObservableValue observable,
            String oldValue,
            String newValue) {
        //Checks if the lenght is above 25 characters, showing an alert if happens and erasing the las character

        if (txtLogin.getText().trim().length() > 25) {
            txtLogin.setText(txtLogin.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum lenght for the login is 25 characters.", ButtonType.OK).showAndWait();
            btnSignIn.setDisable(true);
        }
        if (cpPassword.getText().trim().length() > 25) {
            cpPassword.setText(cpPassword.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum lenght for the password is 25 characters.", ButtonType.OK).showAndWait();
            btnSignIn.setDisable(true);
        }
        //Validates that both fields are not empty
        if (txtLogin.getText().trim().isEmpty()
                || cpPassword.getText().trim().isEmpty()) {
            btnSignIn.setDisable(true);
        }//All the data is filled correctly and the button is enabled
        else {
            btnSignIn.setDisable(false);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * A method to encript the password with the server's public key.
     *
     * @param passwd An String with the password to encript
     * @return An string with the encripted password pased to hexadecimal.
     */
    private String cifrarClavePrivada(String passwd) {

        //Coger clave publica del servidor 
        byte[] encodedPasswd = null;
        /*Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        encodedPasswd = cipher.doFinal(passwd.getBytes());*/
        return Hexadecimal(encodedPasswd);
    }

    // Convierte Array de Bytes en hexadecimal
    static String Hexadecimal(byte[] resumen) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : resumen) {
            result.append(String.format("%02x", aByte));
        }
        return result.toString();
    }

}
