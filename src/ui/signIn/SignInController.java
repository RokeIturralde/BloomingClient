/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.signIn;

import businessLogic.FactoryUser;
import businessLogic.album.AlbumInterface;
import exceptions.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.crypto.Cipher;
import logic.objects.User;
import ui.album.AlbumsViewController;

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

    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("package ui.signIn");
    private AlbumInterface client;

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
        txtLogin.textProperty().addListener(this::textChanged);
        cpPassword.textProperty().addListener(this::textChanged);
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
        btnSignIn.setDisable(true);
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
    private void handleAcceptButtonAction(ActionEvent event) {
        try {
            LOGGER.info("Intentando abrir la ventana Logged");

            if (Character.isDigit(txtLogin.getText().charAt(0)) || txtLogin.getText().contains(" ")) {
                throw new LoginFormatException();
            }

            //Validates password format
            if (cpPassword.getText().contains(" ")) {
                throw new LoginPasswordFormatException();
            }

            //The data is charged into an User
            User usSignIn = new User();
            usSignIn.setLogin(txtLogin.getText());
            usSignIn.setPassword(cifrarClavePrivada(cpPassword.getText()));
            //The factory is used to obtain the implementation, and the method signIn is called, sending the User from above. 
            //client = (UserInterface) FactoryUser.get();
            /*if (response.getResponseType() != ResponseType.OK) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText(response.getResponseType().name());
                alert.setContentText("Try again");
                alert.showAndWait();
            } else {*/
            //Closing SignIn window
            this.stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UIAlbum.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the Sign In window controller
            AlbumsViewController controller = (AlbumsViewController) loader.getController();
            controller.setStage(stage);
            controller.initStage(root, usSignIn);
            //}

        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * The event which opens the Sign Up window
     *
     * @param event
     */
    @FXML
    private void handleRegisterButtonAction(ActionEvent event) {
        try {
            LOGGER.info("Oppening SignUp window");
            //We need another stage to open it in a Modal way
            Stage stageSignUp = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/signUp/SignUp.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the controller of the Sign Up window
            SignUpController controller = (SignUpController) loader.getController();
            controller.setStage(stageSignUp);
            controller.initSignUp(root);
        } catch (IOException ex) {
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
