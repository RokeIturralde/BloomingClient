/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package content;

import javafx.stage.Stage;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;

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
        LOGGER.info("Initializing Content window");
        //Gathers the radioButtons to the same group
        ToggleGroup toggleGroup = new ToggleGroup();
        rbCustomImage.setToggleGroup(toggleGroup);
        rbCustomText.setToggleGroup(toggleGroup);
        Scene scene = new Scene(root);
        //Establishes the scene in the stage
        stage.setScene(scene);
        //Window title
        stage.setTitle("Content");
        //Modal window
        stage.initModality(Modality.APPLICATION_MODAL);
        //Not resizable window
        stage.setResizable(false);
        stage.showAndWait();

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
    }

    @FXML
    private void handlePrintCustomImageButtonAction(ActionEvent event
    ) {
    }

    @FXML
    private void handlePrintCustomTextButtonAction(ActionEvent event
    ) {
    }
}
