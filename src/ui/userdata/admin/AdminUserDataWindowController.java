package ui.userdata.admin;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.Member;
import objects.Status;
import objects.User;
import businessLogic.user.FactoryMember;
import businessLogic.user.FactoryUser;

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
    private CheckBox checkBoxStatus;
    private final String 
        checkBoxStatusEnableText = "Enabled",
        checkBoxStatusDisableText = "Disabled";

    @FXML
    private RadioButton 
        radioButtonAdmin,
        radioButtonClient, radioButtonMember;
    private final String 
        radioButtonAdminText = "Administrator",
        radioButtonClientText = "Client", radioButtonMemberText = "Member";

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
    private final List <String> 
        textSearches = 
            Arrays.asList("Login", "Email", "Full Name"),
        enumeratedSearches = 
            Arrays.asList("Privilege", "Status"),
        privileges = 
            Arrays.asList("Client", "Member", "Admin"),
        status = 
            Arrays.asList("Enable", "Disable");
    


    /**
     * HANDLERS OF EVERY CHANGE IN THE WINDOW ------------------------------------------------------------------------------------------
     */


     /**
      * handle change of any text from the window. 
      * in case of being the search parameter,
      * check the format and stuff.
      *
      * in case of being text from the CRUD, check
      * every posibility.
      * @param observable
      * @param oldValue
      * @param newValue
      */

    private void handleTextChanged(
    ObservableValue observable,
    String oldValue, String newValue) {

        // search value has changed

        String message = "";

        if (txtSearchValue.getText().isEmpty()) {
            txtSearchValue.setPromptText(txtSearchValuePromptText);
            btnSearch.setDisable(true);
        
        } else {
            String searchValue = 
                comboBoxSearchParameter
                    .getSelectionModel()
                    .getSelectedItem().toString();

            boolean nicely = textSearches.contains(searchValue);
            if (nicely)
                nicely = 
                (searchValue.equals("Login") 
                && USF.isLoginFormat(txtSearchValue.getText())) ||
                    
                (searchValue.equals("Email") 
                && USF.isEmailFormat(txtSearchValue.getText())) ||

                searchValue.equals("Full Name") 
                && USF.isFullNameFormat(txtSearchValue.getText());
           
            btnSearch.setDisable(!nicely);

            
        }


        // any user param (CRUD options) has changed


        boolean correctParams = 
        everyUserParamIsCorrect();

            btnAddUser.setDisable(!correctParams);
            btnModifyUser.setDisable(!correctParams);
            btnDeleteUser.setDefaultButton(!correctParams);
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
            nicely = nicely && USF.isLoginFormat(txtLogin.getText());
    
        if (txtEmail.getText().isEmpty())
            txtEmail.setPromptText(txtEmailPromptText);    
        else
            nicely = nicely && USF.isFullNameFormat(txtFullName.getText());

        if (txtFullName.getText().isEmpty())
            txtFullName.setPromptText(txtFullNamePromptText);
        else
            nicely = nicely && USF.isEmailFormat(txtEmail.getText()); 


        // date checkings
        
        if (!datePickerStart.isArmed())
            datePickerStart.setPromptText(datePickerStartText);
        else
            nicely = nicely && USF.dateFormatIsFine(datePickerStart.getValue());
        
        if (!datePickerEnd.isArmed())
            datePickerEnd.setPromptText(datePickerEndText);
        else
            nicely = nicely && USF.dateFormatIsFine(datePickerEnd.getValue());


        // checkbox checkings

        nicely = nicely &&
            (radioButtonAdmin.isArmed() 
            || radioButtonMember.isArmed() 
            || radioButtonClient.isArmed()) && 
            
            checkBoxStatus.isArmed() &&
            
            comboBoxMembershipPlans.isArmed();

        return nicely;
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

            !(radioButtonAdmin.isArmed() 
            || radioButtonMember.isArmed() 
            || radioButtonClient.isArmed()) && 

            !checkBoxStatus.isArmed() &&

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
        List <User> searchResults = null; // every result will be stored in here
        String 
            selection = 
                comboBoxSearchParameter
                .getSelectionModel()
                    .getSelectedItem(),
                param = txtSearchValue.getText();

        if (param.isEmpty())
            System.out.println("Please, first input a value to look for"); // TODO: pulir esto

        else if (selection.equalsIgnoreCase("parameter"))
            System.out.println("Please, select the search parmeter :)))");



        if (textSearches.contains(selection)) // case of being a text search
        // TODO: PLEASE PLEASE TELL ME THERE'S A BETTER WAY OF CATCHING EXCEPTIONS.
            try {
                switch (selection) {
                    case "Login" : searchResults = 
                        Arrays.asList(
                            FactoryUser.get()
                            .findUserByLogin(param)
                        );
                    break;
                
                    case "Email" : searchResults =
                        Arrays.asList(
                            FactoryUser.get().findUserByEmail(param)
                        );
                    break;

                    case "Name" : searchResults = 
                        FactoryUser.get()
                            .findUserByName(param);   
                    break;
                    default:
                        break;
                }
            } catch (Exception e) {
                // TODO: handle exception :((((((
            }

        else if (enumeratedSearches.contains(selection))
            try {
                switch (selection) {
                    case "Status" : searchResults = 
                        FactoryUser.get().findUserByStatus(param);
                    break;

                    /* case "Privilege" : searchResults = 
                        FactoryUser.get
                    break; */
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

        else
            System.out.println("Bro please select somethin'");
        tableUsers.setItems(FXCollections.observableArrayList(searchResults));

  
    }

    private void handleUsersTableSelectionChanged(
    ObservableValue observable,
    Object oldValue, Object newValue) {

        User u = User.class.cast(newValue);
        

    }

    private void loadUserData(User u) {
        if (u == null) {
            new Alert(AlertType.ERROR, "Some error");
            return;
        }

        Member m;

        // if (Member.class.isInstance(u))

        
        txtLogin.setText(u.getLogin());
        txtEmail.setText(u.getEmail());
        txtFullName.setText(u.getFullName()); 

        if (u.getStatus().equals(Status.ENABLE)) {
            checkBoxStatus.arm();
            checkBoxStatus.setText(checkBoxStatusEnableText);
        }
        else {
            checkBoxStatus.disarm();
            checkBoxStatus.setText(checkBoxStatusDisableText);
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
        stage.setTitle(WINDOW_NAME);
        stage.setResizable(false);
        stage.setOnShowing(this::handlerWindowShowing);


        // text listeners
        txtSearchValue.textProperty().addListener(
            this::handleTextChanged);
        txtSearchValue.setPromptText(txtSearchValuePromptText);

        txtLogin.textProperty().addListener(
            this::handleTextChanged);
        txtLogin.setPromptText(txtLoginPromptText);

        txtEmail.textProperty().addListener(
            this::handleTextChanged);
        txtEmail.setPromptText(txtEmailPromptText);

        txtFullName.textProperty().addListener(
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



        // radio button
        radioButtonAdmin.setText(
            radioButtonAdminText);
        radioButtonClient.setText(
            radioButtonClientText);
        radioButtonMember.setText(
            radioButtonMemberText);
            
        // TODO: listener

        

        // checkbox
        checkBoxStatus.selectedProperty()
            .addListener(new ChangeListener<Boolean>() {
                
                @Override
                public void changed(
                ObservableValue<? extends Boolean> observable, 
                Boolean oldValue, Boolean newValue) {
                    if(newValue)
                        checkBoxStatus.setText(checkBoxStatusEnableText);
                    else 
                        checkBoxStatus.setText(checkBoxStatusDisableText);
                }
            });
        

        // combobox
        /* comboBoxSearchParameter.setItems(
            FXCollections
            .observableArrayList(textSearches)); */
            comboBoxSearchParameter
                .getItems().addAll(textSearches);
            comboBoxSearchParameter
                .getItems().addAll(enumeratedSearches);
        // TODO: add both items.



        comboBoxSearchParameter.setOnAction((event) -> {
            //comboBoxMemberStatusSearch.getItems().clear();
            

            // TODO: pulir 

                switch (comboBoxSearchParameter.getSelectionModel().getSelectedItem()) {
                case "Privilege":

                    comboBoxMemberStatusSearch.setPromptText("Privilege");
                        comboBoxMemberStatusSearch.setItems(
                            FXCollections
                            .observableArrayList(privileges));
                            txtSearchValue.setVisible(false);
            comboBoxMemberStatusSearch.setVisible(true);
                break;

                case "Status":
                    comboBoxMemberStatusSearch.setPromptText("Status");
                        comboBoxMemberStatusSearch.setItems(
                            FXCollections
                            .observableArrayList(status));
                            txtSearchValue.setVisible(false);
                    comboBoxMemberStatusSearch.setVisible(true);
                break;

                default:
                    comboBoxMemberStatusSearch.setVisible(false);
                    txtSearchValue.setVisible(true);
                    txtSearchValue.setPromptText(txtSearchValuePromptText);
                    //handleTextChanged(null, null, null);
                break;
            } 

            
       

            btnSearch.setDisable(false);
                
            });
           

        // TODO: listener of parameter change

        comboBoxMemberStatusSearch.setVisible(false);

        // table users 
        {
            tableUsers.getSelectionModel().selectedItemProperty()
                .addListener(this::handleUsersTableSelectionChanged);

            List <String> tableColumns = 
                Arrays.asList("login", "email", "fullName",
                "status", "privilege", "plan", "lastPasswordChange");
        
            
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