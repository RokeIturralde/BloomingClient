package user;

import businessLogic.FactoryMember;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.objects.Member;
import logic.objects.User;

/**
 * @author dani
 */
public class UserWindowController {
    
    private List <User> users;
    private Button btnSearch;
    private Button btnAddUser;
    
    @FXML
    private Button 
            
            btnModifyUser, btnDeleteUser;
    private TextField txtSearchValue;
    private TextField txtLogin;
    @FXML
    private TextField
            txtEmail, txtFullName;
    private ComboBox comboBoxSearchParameter;
   private TableView
           tableUsers;
    private TableColumn tbColLogin;
    private TableColumn tbColFullName;
   
   @FXML
   private TableColumn tbColEmail, tbColStatus,
           tbColPrivilege, tbColMembershipPlan, tbColLastPasswordChange;
      
   
   
   private Stage stage;
   private static final Logger LOGGER = Logger.getLogger("package user;");
   /**
    * 
    */
   
   private final List <String> searchParametersList = 
           Arrays.asList("Login", "Email", "Full Name", "Privilege", "Status");
   
   public void initStage(Parent root) {
       LOGGER.info("Initializing 'User' window");
       // Stablish scene
       Scene scene = new Scene(root);
       //stage.initModality(Modality.APPLICATION_MODAL);
       stage.setScene(scene);
       // Set name
       stage.setTitle("User");
       // Set sizes
       stage.setResizable(false);
       stage.setOnShowing(this::handlerWindowShowing);
       
       txtSearchValue.textProperty().addListener(this::textChanged);
       txtLogin.textProperty().addListener(this::textChanged);
       txtEmail.textProperty().addListener(this::textChanged);
       txtFullName.textProperty().addListener(this::textChanged);
       
       
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
       
       ObservableList <Member> everyMember = FXCollections
            .observableArrayList(
                FactoryMember.get().getEveryUser_XML(Member.class)
            );
       
       tableUsers.setItems(everyMember);
       
   }
   
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
       
       if (txtSearchValue.getText().trim().length() > 25) {
           txtSearchValue.setText(txtSearchValue.getText().substring(0, 25));
           new Alert(
               Alert.AlertType.ERROR, 
               "The maximum length for the search parameter is 25 characters.");
           btnSearch.setDisable(true);
       }
       
       //if (txtLogin.getText().trim().length() > 25)
       
   }
   
   private boolean weirdTextTester(String t) {
       List <String> l = 
            Arrays.asList(
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
       // Login
       // buttons   
 
       btnSearch.setDisable(true);
       btnAddUser.setDisable(true);
       btnModifyUser.setDisable(true);
       btnDeleteUser.setDisable(true);
       
       // texts
       txtSearchValue.setPromptText("Value");
       txtLogin.setPromptText("Login");
       txtEmail.setPromptText("Email");
       txtFullName.setPromptText("Full name");

       
       
   }
   
   @FXML
   private void handleSearchButtonAction()  {
   
   }
   
   @FXML
   private void handleClearButtonAction()  {}
   
   @FXML
   private void handleAddUserButtonAction()  {}
   
   @FXML
   private void handleModifyUserButtonAction()  {}
   
   @FXML
   private void handleDeleteUserButtonAction()  {}
   
   @FXML
   private void handlePrintButtonAction()  {}
            
   private void loadUsersAtTable(List <User> l) {
       //if (!txtSearchValue.getText().equals(""))
   }  

}
