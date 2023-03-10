package ui.userdata.admin;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericType;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.Member;
import objects.MembershipPlan;
import objects.Privilege;
import objects.Status;
import objects.User;
import ui.membershipPlan.admin.AdminMembershipPlanController;
import ui.signIn.SignInController;
import businessLogic.membership.MembershipPlanFactory;
import businessLogic.user.FactoryMember;
import businessLogic.user.FactoryUser;

/**
 * @author dani
 */
public class AdminUserDataWindowController {

    // FXML (window elements)

    private User adminLoged;

    @FXML
    private Button 
        btnSearch, btnClear, btnAddUser,
        btnModifyUser,btnDeleteUser, btnPrint,
        // pane
        btnBlooming, btnMembership, btnUser, btnMyProfile;
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
    private Text 
        staticTextLogin, staticTextEmail, staticTextFullName;
    private final String 
        staticTextLoginValue = "Login", 
        staticTextEmailValue = "Email", 
        staticTextFullNameValue = "Full name";

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
        radioButtonAdminText = "Admin",
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
           return
                MembershipPlanFactory.getModel()
                    .findAll_XML(
                        new GenericType <List<MembershipPlan>> () {})
                        .stream().map(p -> p.getName())
                            .collect(Collectors.toList());
        } catch (Exception e) {
            return Arrays.asList("1", "2", "3", "4");
        }
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

        if (txtSearchValue.getText().isEmpty()) // just in case
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
            
        
        // date checkings TODO: bro what

        // check if the date is a valid value

        /* if (radioButtonMember.isSelected()) {
            if (datePickerStart.getTypeSelector().isEmpty())
                datePickerStart.setPromptText(datePickerStartText);
            else
                nicely = nicely && USF.dateFormatIsFine(datePickerStart.getValue());
            
            if (datePickerEnd.getTypeSelector().isEmpty())
                datePickerEnd.setPromptText(datePickerEndText);

            else
                nicely = nicely && USF.dateFormatIsFine(datePickerEnd.getValue());
        }  */

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
     * puts every user of the database in the table.
     */

    private void loadEveryUser() {
        try {
            tableUsers.setItems(
                FXCollections
                .observableArrayList(FactoryMember.get().getEveryUser())
            );
        } catch (Exception e) {
            new Alert(AlertType.ERROR, "There was an error loading all users.");
            LOGGER.severe("Error loading users:\n" + e.getMessage());
        }   
    }
    
    /**
     * method that puts the user data at the table
     * @param u
     */
    private void loadUserData(User u) {
        if (u == null) {
            new Alert(AlertType.ERROR, "Not valid value");
            LOGGER.severe("User value is null.");
            return;
        }

        LOGGER.info("Loading user data of user of login=" + u.getLogin());

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

            Member m = Member.class.cast(u);

            comboBoxMembershipPlans.getSelectionModel()
                .select(
                    m.getPlan().getId());
                
            datePickerStart.setValue(
                toLocalDate(
                    m.getMemberStartingDate()));
            datePickerEnd.setValue(
                toLocalDate(
                    m.getMemberEndingDate()));
        } 
        else {
            comboBoxMembershipPlans.setPromptText(comboBoxMembershipPlansText);
        }
    }

    /**
     * convert value to LocalDate
     * @param date 
     * @return new value in LocalDate
     */
    private LocalDate toLocalDate(Date date) {
        return date.toInstant()
            .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * convert the value to Date
     * @param date
     * @return
     */

    private Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    /**
     * function to load every 
     * user parameter in an object
     * @return user with every value in the boxes
     */

    private User createFromParams() { // 
        LOGGER.info("Creating user from parameters.");
        User u = new User();

        Privilege p = Privilege.CLIENT; // default

        if (radioButtonAdmin.isSelected()) {
            p = Privilege.MEMBER;
            Member.class.cast(u)
                .setMemberStartingDate(
                    this.toDate(datePickerStart.getValue()));
            Member.class.cast(u)
                .setMemberEndingDate(
                    this.toDate(datePickerEnd.getValue()));

            List <MembershipPlan> l = new LinkedList<MembershipPlan>();

            try {
               l = MembershipPlanFactory.getModel()
                    .findPlanByName_XML(
                        new GenericType<List<MembershipPlan>>() {},
                        comboBoxMembershipPlans.getSelectionModel()
                    .getSelectedItem());                
            } catch (Exception e) {
                // TODO: handle exception
            }
                if (!l.isEmpty())
                    Member.class.cast(u).setPlan(l.get(0));
        }
            
        else if (radioButtonClient.isSelected())
            p = Privilege.ADMIN;
        else if (radioButtonMember.isSelected())
            p = Privilege.CLIENT;

        
        u.setLogin(txtLogin.getText());
        u.setEmail(txtEmail.getText());
        u.setFullName(txtFullName.getText());

        u.setPassword("DEFAULT"); // default passwords.

        u.setPrivilege(p);

        Status s = 
            checkBoxStatus.isSelected() ? 
                Status.ENABLE :
                Status.DISABLE;

        u.setStatus(s);
        
        u.setLastPasswordChange(Date.from(Instant.now()));

        LOGGER.info("User " + u.toString() + " created.");
        
        return u;
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
        btnDeleteUser.setDisable(!correctParams);
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



        LOGGER.info("Attempting to load users to the table.");
        
        loadEveryUser();
    }

    /**
     * method that makes the research.
     * NOTE: theoretically, it's impossible not
     * having correct parameters, search won't be enabled
     */
    @FXML
    private void handleSearchButtonAction() {
        String 
            value ="default",
            param = comboBoxSearchParameter
                .getSelectionModel().getSelectedItem();
            

        User u = null;
        List <User> searchResults = new LinkedList<>(); // every result will be stored in here

            try {

                if (textSearches.contains(param)) {// case of being a text search 
                    value = txtSearchValue.getText();
                    if (param.equalsIgnoreCase("login")) 
                        u = FactoryUser.get()
                            .findUserByLogin(value);
                    else if (param.equalsIgnoreCase("email"))
                        u = FactoryUser.get()
                            .findUserByEmail(value);
                    else if (param.equalsIgnoreCase("full name"))
                        searchResults = FactoryUser.get()
                            .findUserByName(value);

                    // multiple results
                    if (searchResults != null || u != null)
                        Arrays.asList(u);
                    else
                        throw new Exception("Some error");
                }

                else { // case of being an enumerated search
                    value = comboBoxSearch.getSelectionModel().getSelectedItem();
                    if (param.equalsIgnoreCase("privilege"))
                        searchResults = 
                            FactoryUser.get()
                                .findUserByPrivilege(value); 
                
                    else if (param.equalsIgnoreCase("status")) 
                        searchResults = 
                            FactoryUser.get()
                                .findUserByStatus(value);
                } 

            } catch (Exception e) {
                // TODO: handle

                new Alert(AlertType.INFORMATION, "No users were found with" + param + "=" + value);
            } 

        LOGGER.info(
        "Attempting to search users by " + param.toLowerCase() + "=" + value);


        tableUsers.setItems(FXCollections.observableArrayList(searchResults));
    }

    private void handleUsersTableSelectionChanged(
    ObservableValue observable,
    Object oldValue, Object newValue) {
        handleClearButtonAction();
        if (newValue != null) {
            User u = User.class.cast(newValue);
            loadUserData(u);
        }
        else 
            new Alert(AlertType.ERROR, "Error with selection.");
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

        staticTextLogin.setText(staticTextLoginValue);
        staticTextEmail.setText(staticTextEmailValue);
        staticTextFullName.setText(staticTextFullNameValue);

        btnSearch.setDisable(true);
        btnAddUser.setDisable(true); 
        btnModifyUser.setDisable(true);  
        btnDeleteUser.setDisable(true);

        checkBoxStatus.setSelected(false);
        checkBoxStatus.setText(checkBoxStatusDisableText);

        radioButtonAdmin.setSelected(false);
        radioButtonClient.setSelected(true);
        radioButtonMember.setSelected(false);

        datePickerStart.setValue(null);
        datePickerStart.setPromptText(datePickerStartText);
        datePickerEnd.setValue(null);
        datePickerEnd.setPromptText(datePickerEndText);

        //comboBoxSearchParameter.getSelectionModel().clearSelection();
        comboBoxSearchParameter.setPromptText(comboBoxSearchParameterText);

        comboBoxMembershipPlans.getSelectionModel().clearSelection();
        comboBoxMembershipPlans.setPromptText(comboBoxMembershipPlansText);

        comboBoxSearch.getSelectionModel().clearSelection();
        comboBoxSearch.setVisible(false);

        loadEveryUser();
    }

    
    
    
    @FXML
    private void handleAddUserButtonAction() {
        User u = createFromParams(); 
        try {
            FactoryUser.get().createUser(u);
        } catch (Exception e) {
            new Alert(
                AlertType.ERROR, 
                "There was an error recording user "+ u +".");
        }

        loadEveryUser(); // refresh table.
    }

    @FXML
    private void handleModifyUserButtonAction() {
        User u = createFromParams(); 
        try {
            FactoryUser.get().editUser(u);
        } catch (Exception e) {
            new Alert(
                AlertType.ERROR, 
                "There was an error editing user " + u + ".");
        }
        loadEveryUser(); // refresh table.
    }

    @FXML
    private void handleDeleteUserButtonAction() {
        String login = txtLogin.getText();
        try {
            FactoryUser.get().removeUser(login);
        } catch (Exception e) {
            new Alert(
                AlertType.ERROR, 
                "There was an error deleting user " + login + ".");
        }
        loadEveryUser(); // refresh table.
    }

    @FXML
    private void handleBloomingButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/signIn/SignInCrud.fxml"));
            Parent root = (Parent) loader.load();
            Stage stageAlbum = new Stage();
            //Obtain the Sign In window controller
            SignInController controller = (SignInController) loader.getController();
            controller.setStage(stageAlbum);
            controller.initStage(root);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @FXML
    private void handleMembershipButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/membershipPlan/admin/AdminMembershipPlan.fxml"));
            Parent root = (Parent) loader.load();
            Stage stageAlbum = new Stage();
            //Obtain the Sign In window controller
            AdminMembershipPlanController controller = (AdminMembershipPlanController) loader.getController();
            controller.setStage(stageAlbum);
            controller.initStage(root, adminLoged);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @FXML
    private void handleUserButtonAction() {
        
    }

    @FXML
    private void handleMyProfileButtonAction() {
        
    }

    @FXML
    private void handlePrintButtonAction() {}


    /**
     * void that initiates the whole window.
     * @param root parent 
     */

     public void initStage(Parent root, User user) {
        LOGGER.info("Initializing 'User' window");
        adminLoged = user;
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

        comboBoxSearchParameter
        .getItems().addAll(textSearches);
        comboBoxSearchParameter
        .getItems().addAll(enumeratedSearches);
        comboBoxMembershipPlans
        .getItems().addAll(membershipPlanNames());

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
                    comboBoxSearch.getItems().addAll(status);
                    comboBoxSearch.setPromptText(comboBoxSearchStatusText);
                }
                    
                else {
                    comboBoxSearch.getItems().addAll(privileges);
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