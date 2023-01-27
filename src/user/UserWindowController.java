package user;


import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import businessLogic.users.FactoryMember;
import businessLogic.users.FactoryUser;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    private ComboBox <String> 
        comboBoxSearchParameter,
        comboBoxMembershipPlans;

    @FXML
    private DatePicker
        datePickerStart,
        datePickerEnd;

    @FXML
    private CheckBox checkBoxStatusEnabled, 
    checkBoxStatusDisabled, checkBoxPrivilegeAdmin,
    checkBoxPrivilegeUser, checkBoxPrivilegeMember;

    @FXML
    private TableView <User> tableUsers;

    @FXML
    private TableColumn <User, String>
        tbColLogin, tbColFullName, tbColEmail,tbColStatus,
        tbColPrivilege, tbColMembershipPlan, tbColLastPasswordChange;

    // LOGGER that will be used to note every event of the window.
    private static final Logger LOGGER =
        Logger.getLogger("package user;");

    private Stage stage;


    private void textPropertyChange(
    ObservableValue observable, 
    String oldValue, String newValue) {

        if (txtSearchValue.getText().length() > 100 
        || txtSearchValue.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR,
            "Please enter a valid text.\n",
            ButtonType.OK).showAndWait();
            btnSearch.setDisable(true);
        } else {
            switch (
            comboBoxSearchParameter
            .getSelectionModel()
            .getSelectedItem().toString()) {
                case "":
                break;
            }
        }
            
        /*
     
        if (isLoginFormat(txtLogin.getText()))

        

        if(txtEmail)

        if(txtFullName)
        */
        
       
    }

    /**
     * TODO: it has to tell you wether it contains weird characters
     * @param login
     * @return
     */
    private boolean isLoginFormat(String login) {
        if (login.contains(" "))
            return false;
        return true;
    }

    // TODO: recomendable make this method static from another class
    private boolean isEmailFormat(String email) {
        String patternEmail = 
            "([a-z0-9]*)@([a-z]*).(com|org|cn|net|gov|eus)";

        if (!Pattern.matches(patternEmail, email) || email.contains(" ")) 
            return false;
        else 
            return true;
    }

    private void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Initializing UserWindowController::handlerWindowShowing");
        
        
        try {
            tableUsers.setItems(
                FXCollections
                .observableArrayList()
            );
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }



        //FactoryUser.get().findUserByEmail("email");
    }

    // options of the combobox
    private final List<String> searchParametersList = 
    Arrays.asList("Login", "Email", 
    "Full Name", "Privilege", "Status");

    @FXML
    private void handleSearchButtonAction() {
        
        switch (comboBoxSearchParameter.getSelectionModel().getSelectedItem().toString()) {
            case "Login":
            break;

            /*case "Email":
                if (isEmailFormat(txtSearchValue.getText()))
                    btnSearch
            break;*/

            case "Full name":
            break;

            case "Privilege":
            break;

            case "Status":
            break;
        }
        
        System.out.println(comboBoxSearchParameter.getSelectionModel().getSelectedItem());
    }

    private void handleUsersTableSelectionChanged(
    ObservableValue observable,
    Object oldValue, Object newValue) {

        User u;
        if (newValue != null) {
            u = User.class.cast(newValue);
            txtLogin.setText(u.getLogin());
            txtEmail.setText(u.getEmail());
            txtFullName.setText(u.getFullName());
            
            
        }
            


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
        txtSearchValue.textProperty().addListener(this::textPropertyChange);
            txtSearchValue.setPromptText("Search by");
        txtLogin.textProperty().addListener(this::textPropertyChange);
            txtLogin.setPromptText("Login");
        txtEmail.textProperty().addListener(this::textPropertyChange);
            txtEmail.setPromptText("Email");
        txtFullName.textProperty().addListener(this::textPropertyChange);
            txtFullName.setPromptText("Full name");

        // buttons
        btnSearch.setDisable(true);
            btnSearch.setText("Search");
        btnAddUser.setDisable(true);
            btnAddUser.setText("Add user");
        btnModifyUser.setDisable(true);
            btnModifyUser.setText("Modify user");
        btnDeleteUser.setDisable(true);
            btnModifyUser.setText("Delete user");

        // texts
        txtSearchValue.setPromptText("Value");
        txtLogin.setPromptText("Login");
        txtEmail.setPromptText("Email");
        txtFullName.setPromptText("Full name");

        // combobox
        comboBoxSearchParameter.setItems(
            FXCollections
            .observableArrayList(searchParametersList));

            tableUsers.getSelectionModel().selectedItemProperty()
                .addListener(this::handleUsersTableSelectionChanged);
        

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

    /**

     * Prepare the stage for a change of scene
     *
     * @param stage where the window shows
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }



}
