package ui.userdata.admin;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import factories.FactoryMember;
import factories.FactoryUser;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.AntiServerTestDataMember;
import logic.objects.User;

/**
 * @author dani
 */
public class AdminUserDataWindowController {

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
        comboBoxMemberStatusSearch, comboBoxMembershipPlans;

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

    private Stage stage;

    // LOGGER that will be used to note every event of the window.
    private static final Logger LOGGER =
        Logger.getLogger("package user;");


    /**
     * 
     * @param observable
     * @param oldValue
     * @param newValue
     */

    private void handleTextChanged(
    ObservableValue observable,
    String oldValue, String newValue) {

        boolean nicely = false;

        if (txtSearchValue.getText().length() > AUDW.MAX_LENGTH
        /*|| txtSearchValue.getText().isEmpty() */) {
            new Alert(Alert.AlertType.ERROR,
            "Please enter a valid text.\n",
            ButtonType.OK).showAndWait();
            btnSearch.setDisable(true);
        } else 
            switch (
            comboBoxSearchParameter
            .getSelectionModel()
            .getSelectedItem().toString()) {

                case "Login": nicely = AUDW.isLoginFormat(txtSearchValue.getText());
                break;

                case "Email": nicely = AUDW.isEmailFormat(txtSearchValue.getText());
                break;

                case "Full Name": nicely = AUDW.isFullNameFormat(txtSearchValue.getText());
                break;

                default: // TODO: alert -> select the searching parameter :)
                break;
            }
        
        btnSearch.setDisable(!nicely);
        
            
        /*
     
        if (isLoginFormat(txtLogin.getText()))

        

        if(txtEmail)

        if(txtFullName)
        */
        
       
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



        //FactoryUser.get().findUserByEmail("email");
    }

    // options of the search combobox
    private final List <String> searchParametersList = 
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
        txtSearchValue.textProperty().addListener(this::handleTextChanged);
            txtSearchValue.setPromptText("Search by");
        txtLogin.textProperty().addListener(this::handleTextChanged);
            txtLogin.setPromptText("Login");
        txtEmail.textProperty().addListener(this::handleTextChanged);
            txtEmail.setPromptText("Email");
        txtFullName.textProperty().addListener(this::handleTextChanged);
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

       


        try {
            System.out.println(FactoryUser.get().findUserByEmail("danielbarrios2002@gmail.com"));
        } catch (Exception e) {
            // TODO: handle exception
        }
            

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