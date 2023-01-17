/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package content;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import javafx.stage.Stage;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Roke
 */
public class ContentWindowController {
    
    @FXML
    private Button bLogo;
    @FXML
    private Button bAlbum;
    @FXML
    private Button bContent;
    @FXML
    private Button bMembership;
    @FXML
    private Button bAboutUs;
    @FXML
    private Button bProfile;
    @FXML
    private ImageView imagePreview;
    @FXML
    private ComboBox cboxParameter;
    @FXML
    private TextField lblValue;
    @FXML
    private Button bClear;
    @FXML
    private Button bFind;
    @FXML
    private TableView tableCustomImage;
    @FXML
    private Button bPrintCustomImage;
    @FXML
    private RadioButton rbCustomImage;
    @FXML
    private RadioButton rbCustomText;
    @FXML
    private ToggleGroup tgType;
    @FXML
    private TextField lblName;
    @FXML
    private DatePicker uploadDate;
    @FXML
    private TextField lblLocation;
    @FXML
    private Button bFileChooser;
    @FXML
    private TextArea lblDescription;
    @FXML
    private Button bAddContent;
    @FXML
    private Button bModifyContent;
    @FXML
    private Button bDeleteContent;
    @FXML
    private TableView tableCustomText;
    @FXML
    private Button bPrintCustomText;
    
    private final String tableImage = "Show Image";
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("package content");

    /**
     * Initializes the controller class.
     */
    /**
     * @Override public void initialize(URL url, ResourceBundle rb) { // TODO }
     */
    /**
     * Initializing method
     *
     * @param root root object with the DOM charged
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing SignIn window");
        //Creates an scene
        Scene scene = new Scene(root);
        //Establishes an scene
        stage.setScene(scene);
        //Window title
        stage.setTitle("Content");
        //Not resizable window
        stage.setResizable(false);
        //Set the Event handlers
        stage.setOnShowing(this::handlerWindowShowing);
        //Set the textfields with a listener
        lblName.textProperty().addListener(this::textPropertyChange);
        lblDescription.textProperty().addListener(this::textPropertyChange);
        lblLocation.textProperty().addListener(this::textPropertyChange);
        //Set the textfields with a listener
        stage.show();
        //Establish the values of each field in the table
        // tbcolName.setCellValueFactory(new PropertyValueFactory<>("name"));
        //tbcolUploadDate.setCellValueFactory(new PropertyValueFactory<>("uploadDate"));
        // tbcolLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        // tbcolImage.setCellValueFactory(new PropertyValueFactory<>(tableImage));
        // tbcolDescription.setCellValueFactory(new PropertyValueFactory<>("text"));
    }
    
    @FXML
    private void handleClearButtonAction(ActionEvent event
    ) {
        //Empty all the fields 
        lblValue.setText("");
        cboxParameter.getSelectionModel().selectFirst();
    }
    
    @FXML
    private void handleAddContentButtonAction(ActionEvent event
    ) {
    }
    
    @FXML
    private void handleModifyContentButtonAction(ActionEvent event
    ) {
    }
    
    @FXML
    private void handleDeleteContentButtonAction(ActionEvent event
    ) {
    }
    
    @FXML
    private void handleFindButtonAction(ActionEvent event
    ) {
    }
    
    @FXML
    private void handleLogoButtonAction(ActionEvent event
    ) {
    }
    
    @FXML
    private void handleAlbumButtonAction(ActionEvent event
    ) {
    }
    
    @FXML
    private void handleAboutUsButtonAction(ActionEvent event
    ) {
    }
    
    @FXML
    private void handleMyProfileButtonAction(ActionEvent event
    ) {
    }
    
    @FXML
    private void handleMembershipButtonAction(ActionEvent event
    ) {
    }
    
    @FXML
    private void handleFileChooserButtonAction(ActionEvent event
    ) {
        FileChooser.ExtensionFilter imageFilter
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Open Resource File");
        File image = fileChooser.showOpenDialog(stage);
        Image testing = new Image(image.toURI().toString());
        imagePreview.setImage(testing);
        
    }
    
    @FXML
    private void handlePrintCustomImageButtonAction(ActionEvent event
    ) {
    }
    
    @FXML
    private void handlePrintCustomTextButtonAction(ActionEvent event
    ) {
    }

    /**
     * Prepare the stage for a change of scene
     *
     * @param stage where the window shows
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    private void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Iniciando ContentWindowController::handlerWindowShowing");
        bDeleteContent.setDisable(true);
        bAddContent.setDisable(true);
        bModifyContent.setDisable(true);
        bFileChooser.setDisable(true);
        lblName.setDisable(true);
        lblDescription.setDisable(true);
        lblLocation.setDisable(true);
        uploadDate.setDisable(true);
        cboxParameter.getItems().addAll(
                "Location",
                "Upload Date",
                "Name");
        // Handle action events for the radio buttons. 
        rbCustomImage.setOnAction(e -> {
            bFileChooser.setDisable(false);
            lblName.setDisable(false);
            lblLocation.setDisable(false);
            uploadDate.setDisable(false);
            lblDescription.setDisable(true);
        });
        
        rbCustomText.setOnAction(e -> {
            bFileChooser.setDisable(true);
            lblName.setDisable(false);
            lblLocation.setDisable(false);
            uploadDate.setDisable(false);
            lblDescription.setDisable(false);
        });
        
    }

    /**
     * Text changed event handler. Validate that all the fields are not empty
     * and that they not surpass 25 characters. The Accept button is disabled if
     * either of those are not fulfilled
     *
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
     */
    private void textPropertyChange(ObservableValue observable,
            String oldValue,
            String newValue) {
        RadioButton rb = (RadioButton) tgType.getSelectedToggle();

        //If text fields values are longer than 25 (max value in Database), show error message and disable 
        //button
        if (lblName.getText().trim().length() > 25) {
            lblName.setText(lblName.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the name is 25 characters", ButtonType.OK).showAndWait();
            bAddContent.setDisable(true);
        }
        if (lblLocation.getText().trim().length() > 25) {
            lblLocation.setText(lblLocation.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the location is 25 characters", ButtonType.OK).showAndWait();
            bAddContent.setDisable(true);
        }
        if (rb.equals(rbCustomText) && lblDescription.getText().trim().length() > 100) {
            lblDescription.setText(lblDescription.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the description is 100 characters", ButtonType.OK).showAndWait();
            bAddContent.setDisable(true);
        }
        //If text fields are empty disable accept buttton
        if (lblName.getText().trim().isEmpty()
                || lblLocation.getText().trim().isEmpty()
                || (rb.equals(rbCustomText) && lblDescription.getText().trim().isEmpty())) {
            bAddContent.setDisable(true);
        } //Else, enable accept button
        else {
            bAddContent.setDisable(false);
        }
    }
}
