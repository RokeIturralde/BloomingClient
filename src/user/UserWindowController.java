package user;

import businessLogic.users.MemberInterface;
import businessLogic.membership.MembershipPlanFactory;
import businessLogic.membership.MembershipPlanInterface;
import businessLogic.users.FactoryMember;
import businessLogic.users.FactoryUser;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.core.GenericType;


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
import logic.objects.MembershipPlan;
import logic.objects.Status;

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


    

    private void textChanged(ObservableValue observable,
            String oldValue,
            String newValue) {
       
    }

    private void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Initializing UserWindowController::handlerWindowShowing");
        
        
        //FactoryMember.get().findMemberByLogin_XML(Member.class, "u3");

        
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

        
        
       /* List <Member> l = 
            FactoryUser.get().findUserByStatus_XML(new GenericType<List<Member>>(){}.getClass(), Status.ENABLE.toString());

        */
        /*
        ObservableList <MembershipPlan> l = FXCollections.observableArrayList(
            MembershipPlanFactory
            .getModel()
                .findAll_XML(new GenericType<List<MembershipPlan>> () {}));
       

        MembershipPlanFactory
            .getModel().find_XML(MembershipPlan.class, "3");
        */
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
