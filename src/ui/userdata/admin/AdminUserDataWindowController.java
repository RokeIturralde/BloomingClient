package ui.userdata.admin;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import businessLogic.user.FactoryMember;
import logic.objects.Member;
import logic.objects.User;
import logic.objects.Privilege;
import logic.objects.Status;

/**
 * @author dani
 */
public class AdminUserDataWindowController {

    // FXML (window elements)

    @FXML
    private Button 
        btnSearch, btnClear, btnAddUser,
        btnModifyUser,btnDeleteUser;
    private final String
        btnSearchText = "Search", btnClearText = "Clear", btnModifyUserText = "Add user",
        btnAddUserText = "Add user", btnDeleteUserText = "Delete user";


    @FXML
    private TextField 
        txtSearchValue, txtLogin,
        txtEmail, txtFullName;
    private final String
        txtSearchValuePromptText = "Search", txtLoginPromptText = "Login",
        txtEmailPromptText = "Email", txtFullNamePromptText = "Full name";


    @FXML
    private ComboBox <String> 
        comboBoxSearchParameter,
        comboBoxMemberStatusSearch, comboBoxMembershipPlans;
    private final String 
        comboBoxSearchParameterText = "Parameter",
        comboBoxMembershipPlansText = "Membership plans";

    @FXML
    private DatePicker
        datePickerStart,
        datePickerEnd;
    private final String 
        datePickerStartText = "Membership starting date",
        datePickerEndText = "Membership ending";

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

    private Stage stage;

    private final String WINDOW_NAME = "AdminUserData"; 

    // LOGGER that will be used to note every event of the window.
    private static final Logger LOGGER =
        Logger.getLogger("package user;");


    // options of the search combobox
    private final List <String> searchParametersList = 
        Arrays.asList("Login", "Email", 
        "Full Name", "Privilege", "Status");



    /**
     * HANDLERS OF EVERY CHANGE IN THE WINDOW ------------------------------------------------------------------------------------------
     */


     /**
      * handle change of any text from the window.
      * @param observable
      * @param oldValue
      * @param newValue
      */

    private void handleTextChanged(
    ObservableValue observable,
    String oldValue, String newValue) {

        // search value has changed

        boolean nicely = false;
        String message = "";

        if (!txtSearchValue.getText().isEmpty()) {
            
            switch (
            comboBoxSearchParameter
            .getSelectionModel()
            .getSelectedItem().toString()) {

                case "Login": nicely = AUDW.isLoginFormat(txtSearchValue.getText());
                    
                break;

                case "Email": nicely = AUDW.isEmailFormat(txtSearchValue.getText());
                break;

                case "Full Name": nicely = AUDW.isNameFormat(txtSearchValue.getText());
                break;

                default:
                break;
            }

            btnSearch.setDisable(!nicely);
        
        } else {
            txtSearchValue.setPromptText(txtSearchValuePromptText);
            btnSearch.setDisable(true);
        }





        // TODO: fix this, message and look if the loogin exists or not.
        btnAddUser.setDisable(!everyUserParamAreFull());
        btnModifyUser.setDisable(!everyUserParamAreFull());
        btnDeleteUser.setDefaultButton(!everyUserParamAreFull());   

        /*

        if (!everyUserParamIsEmpty()) {
            if (AUDW.isLoginFormat(txtLogin.getText()))
            if (AUDW.isEmailFormat(txtEmail.getText()))
            if (AUDW.isNameFormat(txtFullName.getText())

        }
        */
    }

    /**
     * check that every parameter is full
     * @return true if 
     */
    private boolean everyUserParamAreFull() {
        
        return 
            AUDW.isLoginFormat(txtLogin.getText()) && 
            AUDW.isEmailFormat(txtEmail.getText()) &&
            AUDW.isNameFormat(txtFullName.getText()) &&
            
              (checkBoxPrivilegeAdmin.isArmed() 
            || checkBoxPrivilegeMember.isArmed() 
            || checkBoxPrivilegeUser.isArmed()) && 

            
              (checkBoxStatusEnabled.isArmed() 
            || checkBoxStatusDisabled.isArmed()) &&
            
            comboBoxMembershipPlans.isArmed() &&
            
            (datePickerStart.isArmed() &&
            datePickerEnd.isArmed());
    }

    private boolean everyUserParamIsEmpty() {
        return 
            txtLogin.getText().isEmpty() &&
            txtEmail.getText().isEmpty() &&
            txtFullName.getText().isEmpty() &&

            !(checkBoxPrivilegeAdmin.isArmed() 
            || checkBoxPrivilegeMember.isArmed() 
            || checkBoxPrivilegeUser.isArmed()) && 

            !(checkBoxStatusEnabled.isArmed() 
            || checkBoxStatusDisabled.isArmed()) &&

            !comboBoxMembershipPlans.isArmed() &&
            
            !(datePickerStart.isArmed() &&
            datePickerEnd.isArmed());
    }

    

    
    private void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Initializing UserWindowController::handlerWindowShowing");
        
        try {
            tableUsers.setItems(
                FXCollections
                .observableArrayList(FactoryMember.get().getEveryUser())
            );
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }   
    }



    @FXML
    private void handleSearchButtonAction() {
        ObservableList <User> l;

        // do i have to check?


        
        
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

    private String formatNormal(String s) {
        return 
            Character.toUpperCase(
                s.charAt(0)) + 
            s.substring(1);
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
        stage.setTitle(WINDOW_NAME);
        stage.setResizable(false);
        stage.setOnShowing(this::handlerWindowShowing);

        // listeners
        txtSearchValue.textProperty()
            .addListener(
            this::handleTextChanged);
        txtSearchValue.setPromptText(txtSearchValuePromptText);
        txtLogin.textProperty()
            .addListener(
            this::handleTextChanged);
            txtLogin.setPromptText(txtLoginPromptText);
        txtEmail.textProperty()
            .addListener(
            this::handleTextChanged);
        txtEmail.setPromptText(txtEmailPromptText);
        txtFullName.textProperty()
            .addListener(
            this::handleTextChanged);
        txtFullName.setPromptText(txtFullNamePromptText);

        // buttons
        btnSearch.setDisable(true);
            btnSearch.setText(btnSearchText);
        btnClear.setDisable(true);
            btnClear.setText(btnClearText);
        btnAddUser.setDisable(true);
            btnAddUser.setText(btnAddUserText);
        btnModifyUser.setDisable(true);
            btnModifyUser.setText(btnModifyUserText);
        btnDeleteUser.setDisable(true);
            btnDeleteUser.setText(btnDeleteUserText);

        // texts
        txtSearchValue.setPromptText(txtSearchValuePromptText);
        txtLogin.setPromptText(txtLoginPromptText);
        txtEmail.setPromptText(txtEmailPromptText);
        txtFullName.setPromptText(txtFullNamePromptText);

        // combobox
        comboBoxSearchParameter.setOnAction((event) -> {
            comboBoxMemberStatusSearch.getItems().clear();



            switch (comboBoxSearchParameter.getSelectionModel().getSelectedItem()) {
                case "Privilege":

                    comboBoxMemberStatusSearch.setPromptText("Privilege");
                        comboBoxMemberStatusSearch.getItems().add("Client");
                        comboBoxMemberStatusSearch.getItems().add("Member");
                        comboBoxMemberStatusSearch.getItems().add("Admin");
                    txtSearchValue.setVisible(false);
                    comboBoxMemberStatusSearch.setVisible(true);
                break;

                case "Status":
                    comboBoxMemberStatusSearch.setPromptText("Status");
                        comboBoxMemberStatusSearch.getItems().add("Enable");
                        comboBoxMemberStatusSearch.getItems().add("Disable");
                    txtSearchValue.setVisible(false);
                    comboBoxMemberStatusSearch.setVisible(true);
                break;

                default:
                    comboBoxMemberStatusSearch.setVisible(false);
                    txtSearchValue.setVisible(true);
                    txtSearchValue.setPromptText("Value");
                    handleTextChanged(null, null, null);
                break;
            }
        

            btnSearch.setDisable(true);
                
            });
           
        comboBoxSearchParameter.setItems(
            FXCollections
            .observableArrayList(searchParametersList));

        // TODO: listener of parameter change

        comboBoxMemberStatusSearch.setVisible(false);

        // table users
        
        tableUsers.getSelectionModel().selectedItemProperty()
            .addListener(this::handleUsersTableSelectionChanged);

        List <String> tableColumns = 
            Arrays.asList("login", "email", "fullName", "status", "privilege", "plan", "lastPasswordChange");
    
        
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
     * @param stage where the window shows
     */

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}