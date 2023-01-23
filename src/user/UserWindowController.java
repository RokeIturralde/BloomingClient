package user;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * @author dani
 */
public class UserWindowController {
    @FXML
    private Button 
            btnMembership, btnUser, btnProfile,
            btnClear, btnSearch,
            btnAddUser, btnModifyUser, btnDeleteUser, btnPrint;
    @FXML
    private TextField
            txtSearchValue, 
            txtLogin, txtEmail;
    @FXML
   private ComboBox 
           comboBoxSearchParameter, 
           comboBoxMembershipPlan;
   @FXML
   private CheckBox
           checkBoxStatusEnabled, checkBoxStatusDisabled,
           checkBoxPrivilegeAdmin, checkBoxPrivilegeUser, checkBoxPrivilegeMember;
   @FXML
   private DatePicker
           datePickerStart,
           datePickerEnd;
   @FXML
   private Pane
           paneBar,
           paneUserData;
   @FXML
   private TableView
           tableUsers;
   
   
   private Stage stage;
   private static final Logger LOGGER = Logger.getLogger("package user;");
   /**
    * 
    */
   
   private final List <String> searchParameters = 
           Arrays.asList("Login", "Email", "Full Name", "Privilege", "Status");
   
   public void initUser(Parent root) {
       LOGGER.info("Initializing 'User' window");
       // Stablish scene
       stage.setScene(
            new Scene(root)
       );
       // Set name
       stage.setTitle("User");
       // Set sizes
       stage.setResizable(false);
       stage.setOnShowing(this::handlerWindowShowing);
       
       // buttons       
       btnSearch.setDisable(true);
       btnAddUser.setDisable(true);
       btnModifyUser.setDisable(true);
       btnDeleteUser.setDisable(true);
       
       // texts
       txtSearchValue.textProperty().addListener(this::textChanged);
       txtLogin.textProperty().addListener(this::textChanged);
       txtEmail.textProperty().addListener(this::textChanged);
       
       comboBoxSearchParameter.setItems(searchParameters);
              
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
   }
   
   @FXML
   private void handleSearchButtonAction()  {}
   
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
            
            
}
