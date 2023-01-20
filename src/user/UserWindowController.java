package user;

import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
 *
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
   
   public void initUser(Parent root) {
       LOGGER.info("Initializing 'User' window");
       
       // Stablish scene
       stage.setScene(
            new Scene(root));
       // Set name
       stage.setTitle("User");
       // Set sizes
       stage.setResizable(false);
       stage.setOnShowing(this::handlerWindowShowing);
       
       
       
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
