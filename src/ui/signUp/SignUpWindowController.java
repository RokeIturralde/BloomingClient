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

    // FXML elements

    @FXML
    private TextField txtLogin, txtEmail, txtFullName;
    private final String 
        txtLoginPromptText = "Login", txtEmailPromptText = "Email",
            txtFullNamePromptText = "Full name";

    @FXML
    private PasswordField password, passwordConfirmation;
    private final String passwordPromptText = "Password",
            passwordConfirmationPromptText = "Repeat the password";

    @FXML
    private Button btnRegister, btnClear, btnExit;
    private final String btnRegisterText = "Register",
            btnClearText = "Clear", btnExitText = "Exit";

    private Stage stage;

    private final String WINDOW_NAME = "AdminUserData";

    // LOGGER that will be used to note every event of the window.
    private static final Logger LOGGER = Logger.getLogger("package user;");
    @FXML
    private AnchorPane AnchorPane;

    /**
     * Prepare the stage for a change of scene
     * 
     * @param stage where the window shows
     */

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * HANDLERS OF EVERY POSSIBLE CHANGE IN THE WINDOW ------------------
     */

    /**
     * if any text from the window changes, go for it.
     * 
     * @param observableValue
     * @param oldvalue
     * @param newValue
     */

    private void handleTextChanged(
            ObservableValue observableValue,
            String oldvalue, String newValue) {

           btnRegister.setDisable(everyUserParamIsCorrect());
    }

    /**
     * manage message (user already in use.)
     * 
     * @return
     */

    /**
     * TODO: checking should be correct
     * puts every prompt text
     * 
     * @return true if every user param is full,
     *         and with the correct format.
     */

    private boolean everyUserParamIsCorrect() {
        boolean nicely = true;

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
        else if (passwordConfirmation.getText().isEmpty())
            passwordConfirmation.setPromptText(passwordConfirmationPromptText);
            
        nicely = nicely && (password.equals(passwordConfirmationPromptText));

        // TODO: are both passwords the same?

        return nicely;
    }

    /**
     * void that initiates the whole window.
     * 
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
     * set every element text from the begining
     * @param event
     */

    private void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Initializing UserWindowController::handlerWindowShowing");
        // button

        btnRegister.setText(btnRegisterText);
        btnClear.setText(btnClearText);
        btnExit.setText(btnExitText);

        btnRegister.setDisable(false);
        btnClear.setDisable(false);
        btnExit.setDisable(false);

        clearEverything();
        // TODO: set all the texts


    }

    /**
     * clears ever character
     */
    private void clearEverything() {
        // text
        txtLogin.clear();
        txtLogin.setPromptText(txtLoginPromptText);
        txtEmail.clear();
        txtEmail.setPromptText(txtEmailPromptText);
        txtFullName.clear();
        txtFullName.setPromptText(txtFullNamePromptText);

        // password

        password.clear();
        password.setPromptText(passwordPromptText);
        passwordConfirmation.clear();
        passwordConfirmation.setPromptText(passwordConfirmationPromptText);
    }

    /* /**
     * checks if the spaces have values
     * 
     * @return
     
    private boolean everyUserParamIsEmpty() {
        return 
            txtLogin.getText().isEmpty() &&
            txtEmail.getText().isEmpty() &&
            txtFullName.getText().isEmpty() &&

            password.getText().isEmpty() &&
            passwordConfirmation.getText().isEmpty(); 
    }*/

    private User generateFromFields() {
        User u = new User();
        // TODO
        return u;
    }

    @FXML
    private void handlerRegisterButtonAction() {
        try {
            FactoryUser.get().createUser(generateFromFields());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @FXML
    private void handlerClearButtonAction() {
        clearEverything();
    }

    @FXML
    private void handlerExitButtonAction() {
        // TODO:
    }


    public void initSignUp(Parent root) {
    }

}
