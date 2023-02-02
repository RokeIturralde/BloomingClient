package ui.userdata.admin;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.GenericType;

import org.apache.xpath.functions.FuncExtFunctionAvailable;
import org.hibernate.loader.custom.CollectionReturn;

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
import objects.MembershipPlan;
import objects.Privilege;
import objects.Status;
import objects.User;
import businessLogic.membership.MembershipPlanFactory;
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
        btnSearchText = "Search", btnClearText = "Clear", btnModifyUserText = "Modify user",
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
        comboBoxSearch, comboBoxMembershipPlans;
    private final String 
        comboBoxSearchParameterText = "Parameter",
        comboBoxSearchStatusText = "Status",
        comboBoxSearchPrivilegeText = "Privilege",
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

    // memberhsip plan names

    public List <String> membershipPlanNames() {
        try {
            /* return MembershipPlanFactory.getModel()
            .findAll_XML(
                new GenericType <List<MembershipPlan>> () {}); */
           return
            MembershipPlanFactory.getModel()
                .findAll_XML(
                    new GenericType <List<MembershipPlan>> () {})
                    .stream().map(p -> p.getName())
                        .collect(Collectors.toList());  
        } catch (Exception e) {
            e.printStackTrace();
            return Arrays.asList("bro que cojones");
        }
    } 
            



    // HANDLERS OF EVERY CHANGE IN THE WINDOW ------------------------------------------------------------------------------------------



     /**
      * handle change of any text from the window. 
      * depending on which one it is, the format will be 
      * checked.
      */

    private void handleTextChanged(
    ObservableValue observable,
    String oldValue, String newValue) {

        // if the searching param is empty, the search button remains disabled.

        String 
            value = txtSearchValue.getText(),
            param = (!comboBoxSearchParameter.getSelectionModel().isEmpty()) ?
                comboBoxSearchParameter.getSelectionModel().getSelectedItem() :
                "";

        if (value.isEmpty()) 
            btnSearch.setDisable(true);
        
        else { // if there is a value to search, check format.

            boolean nicely = textSearches.contains(param);
            if (nicely)
                nicely = 
                (param.equalsIgnoreCase("login") 
                && USF.isLoginFormat(value)) ||
                    
                (param.equalsIgnoreCase("email") 
                && USF.isEmailFormat(value)) ||

                param.equalsIgnoreCase("full name") 
                && USF.isFullNameFormat(value);
           
            btnSearch.setDisable(!nicely); 
            // if it mataches patterns, we allow user to do search
        }

        // any user param (CRUD options) has changed

        boolean correctParams = 
            everyUserParamIsCorrect();
        // NOTE: this method will set prompt texts if empty, automatically

        btnAddUser.setDisable(!correctParams);
        btnModifyUser.setDisable(!correctParams);
        btnDeleteUser.setDefaultButton(!correctParams);
    }


    /**
     * puts every prompt text.
     * 
     * @return true if every user param is full,
     * with proper format.
     */

    private boolean everyUserParamIsCorrect() {
        boolean // this boolean will be concatenated.
            nicely = true;

        // text checkings

        if (txtSearchValue.getText().isEmpty())
            txtSearchValue.setPromptText(txtSearchValuePromptText);


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


        // radio buttons

        nicely = nicely &&
            (radioButtonMember.isSelected() 
            || radioButtonClient.isSelected()
            || radioButtonAdmin.isSelected());
            
        
        // date checkings

        /* if (radioButtonMember.isSelected()) {
            if (datePickerStart.getTypeSelector().isEmpty())
                datePickerStart.setPromptText(datePickerStartText);
            else
                nicely = nicely && USF.dateFormatIsFine(datePickerStart.getValue());
            
            if (datePickerEnd.getTypeSelector().isEmpty())
                datePickerEnd.setPromptText(datePickerEndText);

            else
                nicely = nicely && USF.dateFormatIsFine(datePickerEnd.getValue());
        } */
        
        

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
    

    /**
     * prepare window to be shown
     */

    private void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Initializing UserWindowController::handlerWindowShowing");

        // buttons

        btnSearch.setText(btnSearchText);
        btnClear.setText(btnClearText);
        btnAddUser.setText(btnAddUserText);
        btnModifyUser.setText(btnModifyUserText);
        btnDeleteUser.setText(btnDeleteUserText);

        btnClear.setDisable(false);


        // radio button

        radioButtonAdmin.setText(
            radioButtonAdminText);
        radioButtonClient.setText(
            radioButtonClientText);
        radioButtonMember.setText(
            radioButtonMemberText);


        // combo box

        comboBoxSearchParameter
        .getItems().addAll(textSearches);
        comboBoxSearchParameter
        .getItems().addAll(enumeratedSearches);
        comboBoxMembershipPlans
        .getItems().addAll(membershipPlanNames());

        membershipPlanNames().forEach(m -> System.out.println(m));

        LOGGER.info("Attempting to load users to the table.");
        
        try {
            tableUsers.setItems(
                FXCollections
                .observableArrayList(FactoryMember.get().getEveryUser())
            );
        } catch (Exception e) {
            LOGGER.severe("There was an error loading users:\n" + e.getMessage());
        }   
    }


    /**
     * method that makes the research.
     * NOTE: theoretically, it's impossible not
     * having correct parameters, search won't be enabled
     */
    @FXML
    private void handleSearchButtonAction() {
        String 
            param = 
                comboBoxSearchParameter
                    .getSelectionModel().getSelectedItem(),
            value = txtSearchValue.getText();

        LOGGER.info(
            "Attempting to search users by " + param + "=" + value);

        List <User> searchResults = null; // every result will be stored in here


        if (textSearches.contains(param)) // case of being a text search
            try {
                switch (param) {
                    case "Login" : searchResults = 
                        Arrays.asList(
                            FactoryUser.get()
                            .findUserByLogin(value)
                        );
                    break;
                
                    case "Email" : searchResults =
                        Arrays.asList(
                            FactoryUser.get().findUserByEmail(value)
                        );
                    break;

                    case "Name" : searchResults = 
                        FactoryUser.get()
                            .findUserByName(value);   
                    break;
                    default:
                        break;
                }
            } catch (Exception e) {
                // TODO: handle exception :((((((
            }


        else
            try {
                searchResults = 
                    param.equalsIgnoreCase("status") ?
                        FactoryUser.get().findUserByStatus(value) :
                        FactoryUser.get().findUserByPrivilege(value);
            } catch (Exception e) {
                // TODO: handle exception
            }

        tableUsers.setItems(FXCollections.observableArrayList(searchResults));
    }

    private void handleUsersTableSelectionChanged(
    ObservableValue observable,
    Object oldValue, Object newValue) {
        handleClearButtonAction();

        User u = User.class.cast(newValue);
        loadUserData(u);

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

        boolean enabled = u.getStatus().equals(Status.ENABLE);
        if (enabled)
            checkBoxStatus.setText(checkBoxStatusEnableText);
        else 
            checkBoxStatus.setText(checkBoxStatusDisableText);
        
        checkBoxStatus.setSelected(enabled);

        if (u.getPrivilege().equals(Privilege.ADMIN))
            radioButtonAdmin.setSelected(true);
        if (u.getPrivilege().equals(Privilege.CLIENT))
            radioButtonClient.setSelected(true);
        if (u.getPrivilege().equals(Privilege.MEMBER)) {
            radioButtonMember.setSelected(true);
            
            datePickerStart.setValue(
                toLocalDate(
                    Member.class.cast(u)
                    .getMemberStartingDate()));
            datePickerEnd.setValue(
                toLocalDate(
                    Member.class.cast(u)
                    .getMemberEndingDate()));
        }
    }


    public LocalDate toLocalDate(Date date) {
        return date.toInstant()
            .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @FXML
    private void handleClearButtonAction() {
        txtSearchValue.clear();
        txtSearchValue.setPromptText(txtSearchValuePromptText);
        // it has to be shown
        txtSearchValue.setVisible(true);

        txtLogin.clear();
        txtLogin.setPromptText(txtLoginPromptText);
        txtEmail.clear();
        txtEmail.setPromptText(txtEmailPromptText);
        txtFullName.clear();
        txtFullName.setPromptText(txtFullNamePromptText);

        btnSearch.setDisable(true);
        btnAddUser.setDisable(true); 
        btnModifyUser.setDisable(true);  
        btnDeleteUser.setDisable(true);

        checkBoxStatus.setSelected(false);
        checkBoxStatus.setText(checkBoxStatusDisableText);

        radioButtonAdmin.setSelected(false);
        radioButtonClient.setSelected(false);
        radioButtonMember.setSelected(false);

        datePickerStart.setValue(null);
        datePickerStart.setPromptText(datePickerStartText);
        datePickerEnd.setValue(null);
        datePickerEnd.setPromptText(datePickerEndText);

        comboBoxSearchParameter.getSelectionModel().clearSelection();
        comboBoxSearchParameter.setPromptText(comboBoxSearchParameterText);

        comboBoxMembershipPlans.getSelectionModel().clearSelection();
        comboBoxMembershipPlans.setPromptText(comboBoxMembershipPlansText);

        comboBoxSearch.getSelectionModel().clearSelection();
        comboBoxSearch.setVisible(false);
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

        txtLogin.textProperty().addListener(
            this::handleTextChanged);

        txtEmail.textProperty().addListener(
            this::handleTextChanged);

        txtFullName.textProperty().addListener(
            this::handleTextChanged);



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

        // combo box
        comboBoxSearchParameter.setOnAction((event) -> {
            //comboBoxSearch.getItems().clear();
            btnSearch.setDisable(true);
            txtSearchValue.setPromptText(txtSearchValuePromptText);;

            String searchMode = 
                comboBoxSearchParameter.getSelectionModel()
                    .getSelectedItem();

            if (!textSearches.contains(searchMode)) {
                comboBoxSearch.getItems().clear();
                if (searchMode.equalsIgnoreCase("status")) {
                    comboBoxSearch.getItems().addAll(privileges);
                    comboBoxSearch.setPromptText(comboBoxSearchStatusText);
                }
                    
                else {
                    comboBoxSearch.getItems().addAll(status);
                    comboBoxSearch.setPromptText(comboBoxSearchPrivilegeText);
                } 

                txtSearchValue.setVisible(false);
                comboBoxSearch.setVisible(true);
            }
            else {
                comboBoxSearch.setVisible(false);
                txtSearchValue.setVisible(true);
                txtSearchValue.setPromptText(txtSearchValuePromptText);
                handleTextChanged(null, null, null);
            }   
        });

        comboBoxSearch.setVisible(false);


        comboBoxSearch.setOnAction((event) -> {
            btnSearch.setDisable(
                comboBoxSearch.getSelectionModel().isEmpty());
        });

        /* comboBoxMembershipPlans.setItems(
            FXCollections.observableArrayList(membershipPlanNames()));
 */
        /* MembershipPlanFactory.getModel().findAll_XML(new GenericType<List<MembershipPlan>>() {});


        MembershipPlanFactory.getModel().findPlanByName_XML(new GenericType<List<MembershipPlan>>() {}, "plan1"); */

        // table users 
        {
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
        }

        handleClearButtonAction();

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