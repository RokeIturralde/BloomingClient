package user;

import businessLogic.FactoryMember;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.objects.Member;
import logic.objects.User;

/**
 * @author dani
 */
public class UserWindowController {

    // FXML (window elements)

    @FXML
    private Button 
        btnSearch, btnAddUser,
        btnModifyUser,btnDeleteUser;

    @FXML
    private TextField 
        txtSearchValue, txtLogin,
        txtEmail, txtFullName;

    @FXML
    private ComboBox comboBoxSearchParameter;

    @FXML
    private TableView tableUsers;

    @FXML
    private TableColumn 
        tbColLogin, tbColFullName, tbColEmail,tbColStatus,
        tbColPrivilege, tbColMembershipPlan, tbColLastPasswordChange;

    // LOGGER that will be used to note every event of the window.
    private static final Logger LOGGER =
        Logger.getLogger("package user;");

    private Stage stage;

    // options of the combobox
    private final List<String> searchParametersList = 
        Arrays.asList("Login", "Email", 
        "Full Name", "Privilege", "Status");



    

    /**
     * Prepare the stage for a change of scene
     *
     * @param stage where the window shows
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void textChanged(ObservableValue observable,
            String oldValue,
            String newValue) {
        /*
         * if (txtSearchValue.getText().trim().length() > 25) {
         * txtSearchValue.setText(txtSearchValue.getText().substring(0, 25));
         * new Alert(
         * Alert.AlertType.ERROR,
         * "The maximum length for the search parameter is 25 characters.");
         * btnSearch.setDisable(true);
         * }
         * 
         * //if (txtLogin.getText().trim().length() > 25)
         */
    }

    private boolean weirdTextTester(String t) {
        List<String> l = Arrays.asList(
                "%", ":", ";", ",", "'",
                "¡", "!", "?", "¿", "|",
                "=", "-", "<", ">", "`",
                "+", "@", "/", "[", "]",
                "ç", "{", "}", "#", "º",
                "ª", " ");

        if (t == null)
            return false;
        return ((!t.equals("")) && (t.matches("^[a-zA-Z0-9]*$")));
    }

    private void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Initializing UserWindowController::handlerWindowShowing");
        

    }

    @FXML
    private void handleSearchButtonAction() {

    }

    @FXML
    private void handleClearButtonAction() {
    }

    @FXML
    private void handleAddUserButtonAction() {
    }

    @FXML
    private void handleModifyUserButtonAction() {
    }

    @FXML
    private void handleDeleteUserButtonAction() {
    }

    @FXML
    private void handlePrintButtonAction() {
    }

    /**
     * void that initiates the whole window.
     * @param root parent 
     */

     public void initStage(Parent root) {
        LOGGER.info("Initializing 'User' window");

        // Stablish scene
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Set properties
        stage.setTitle("User Management Plan");
        stage.setResizable(false);
        stage.setOnShowing(this::handlerWindowShowing);

        // listeners
        txtSearchValue.textProperty().addListener(this::textChanged);
            txtSearchValue.setPromptText("");
        txtLogin.textProperty().addListener(this::textChanged);
            txtLogin.setPromptText("Login");
        txtEmail.textProperty().addListener(this::textChanged);
            txtEmail.setPromptText("Email");
        txtFullName.textProperty().addListener(this::textChanged);
            txtFullName.setPromptText("Full name");

        // Buttons

        btnSearch.setDisable(true);
        btnAddUser.setDisable(true);
        btnModifyUser.setDisable(true);
        btnDeleteUser.setDisable(true);

        // texts
        txtSearchValue.setPromptText("Value");
        txtLogin.setPromptText("Login");
        txtEmail.setPromptText("Email");
        txtFullName.setPromptText("Full name");

        // combobox
        comboBoxSearchParameter.setItems(
            FXCollections
            .observableArrayList(searchParametersList));

        
        tbColLogin.setCellValueFactory(
                new PropertyValueFactory<>("login"));

        tbColEmail.setCellValueFactory(
                new PropertyValueFactory<>("email"));

        tbColFullName.setCellValueFactory(
                new PropertyValueFactory<>("fullName"));

        tbColStatus.setCellValueFactory(
                new PropertyValueFactory<>("status"));

        tbColPrivilege.setCellValueFactory(
                new PropertyValueFactory<>("privilege"));

        tbColMembershipPlan.setCellValueFactory(
                new PropertyValueFactory<>("plan"));

        tbColLastPasswordChange.setCellValueFactory(
                new PropertyValueFactory<>("lastPasswordChange"));

        
        stage.show();
    }

}
