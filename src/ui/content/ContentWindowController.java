/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.content;

import businessLogic.content.ContentFactory;
import businessLogic.content.ContentInterface;
import businessLogic.customImage.CustomImageFactory;
import businessLogic.customImage.CustomImageInterface;
import businessLogic.customText.CustomTextFactory;
import businessLogic.customText.CustomTextInterface;
import exceptions.ClientErrorException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import javafx.stage.Stage;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;
import javax.ws.rs.core.GenericType;
import objects.CustomImage;
import objects.Content;
import objects.CustomText;
import services.ContentFacadeREST;
import services.CustomTextFacadeREST;

/**
 * FXML Controller class
 *
 * @author Roke
 */
public class ContentWindowController {

    @FXML
    private Button btnLogo;
    @FXML
    private Button btnAlbum;
    @FXML
    private Button btnContent;
    @FXML
    private Button btnMembership;
    @FXML
    private Button btnAboutUs;
    @FXML
    private Button btnProfile;
    @FXML
    private ImageView imagePreview;
    @FXML
    private ComboBox cboxParameter;
    @FXML
    private TextField txtValue;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnFind;
    @FXML
    private TableView tableCustomImage;
    @FXML
    private TableColumn customImageName;
    @FXML
    private TableColumn customImageLocation;
    @FXML
    private TableColumn customImageUploadDate;
    @FXML
    private TableColumn customImageImage;
    @FXML
    private Button btnPrintCustomImage;
    @FXML
    private RadioButton rbCustomImage;
    @FXML
    private RadioButton rbCustomText;
    @FXML
    private ToggleGroup tgType;
    @FXML
    private TextField txtName;
    @FXML
    private DatePicker uploadDate;
    @FXML
    private TextField txtLocation;
    @FXML
    private Button btnFileChooser;
    @FXML
    private TextArea taDescription;
    @FXML
    private Button btnAddContent;
    @FXML
    private Button btnModifyContent;
    @FXML
    private Button btnDeleteContent;
    @FXML
    private TableView tableCustomText;
    @FXML
    private TableColumn customTextName;
    @FXML
    private TableColumn customTextLocation;
    @FXML
    private TableColumn customTextUploadDate;
    @FXML
    private TableColumn customTextDescription;
    @FXML
    private Button btnPrintCustomText;

    private ObservableList<Content> clientsData;
    private final String tableImage = "Show Image";
    private Byte[] imageBytes = null;
    private Stage stage;
    private ContentInterface client;
    private static final Logger LOGGER = Logger.getLogger("package ui.content");

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
        txtName.textProperty().addListener(this::textPropertyChange);
        taDescription.textProperty().addListener(this::textPropertyChange);
        txtLocation.textProperty().addListener(this::textPropertyChange);
        txtValue.textProperty().addListener(this::searchTextPropertyChange);
        //Set CustomImage table with listener
        tableCustomImage.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tableCustomText.getSelectionModel().clearSelection();
            }
        });
        tableCustomText.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tableCustomImage.getSelectionModel().clearSelection();
            }
        });
        //Set the tables with values
        tableCustomImage.setEditable(false);
        tableCustomText.setEditable(false);
        customImageName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        customImageLocation.setCellValueFactory(
                new PropertyValueFactory<>("location"));
        customImageUploadDate.setCellValueFactory(
                new PropertyValueFactory<>("uploadDate"));

        customTextName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        customTextLocation.setCellValueFactory(
                new PropertyValueFactory<>("location"));
        customTextUploadDate.setCellValueFactory(
                new PropertyValueFactory<>("uploadDate"));
        customTextDescription.setCellValueFactory(
                new PropertyValueFactory<>("text"));

        try {
            client = ContentFactory.getModel();
            clientsData = FXCollections.observableArrayList(client.findAllContents_XML(new GenericType<List<Content>>() {
            }));
            tableCustomImage.setItems(clientsData);
            tableCustomText.setItems(clientsData);
            tableCustomImage.refresh();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        stage.show();
    }

    @FXML
    private void handleClearButtonAction(ActionEvent event
    ) {
        //Empty all the fields 
        txtValue.setText("");
        cboxParameter.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleAddContentButtonAction(ActionEvent event
    ) {
        LocalDate datePicker = uploadDate.getValue();
        if (datePicker != null) {
            if (rbCustomImage.isSelected()) {
                if (imageBytes != null) {
                    CustomImage customImage = new CustomImage();
                    customImage.setName(txtName.getText());
                    customImage.setLocation(txtLocation.getText());
                    Date date = Date.from(datePicker.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    customImage.setUploadDate(date);
                    customImage.setBytes(imageBytes);
                    CustomImageInterface customImageInterface = CustomImageFactory.getModel();
                    try {
                        customImageInterface.createCustomImage_XML(customImage);
                        new Alert(Alert.AlertType.INFORMATION, "Content Added", ButtonType.OK).showAndWait();
                        txtLocation.setText("");
                        txtName.setText("");
                        uploadDate.getEditor().clear();
                        imageBytes = null;
                        imagePreview.setImage(null);
                        btnModifyContent.setDisable(true);
                        btnDeleteContent.setDisable(true);
                        tgType.selectToggle(null);
                        client = ContentFactory.getModel();
                        clientsData = FXCollections.observableArrayList(client.findAllContents_XML(new GenericType<List<Content>>() {
                        }));
                        tableCustomImage.setItems(clientsData);
                        tableCustomImage.refresh();
                    } catch (ClientErrorException ex) {
                        Logger.getLogger(ContentWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "The Image is not set", ButtonType.OK).showAndWait();
                }
            } else if (rbCustomText.isSelected()) {
                //Charge the CustomText with values
                CustomText customText = new CustomText();
                customText.setName(txtName.getText());
                customText.setLocation(txtLocation.getText());
                Date date = Date.from(datePicker.atStartOfDay(ZoneId.systemDefault()).toInstant());
                customText.setUploadDate(date);
                customText.setText(taDescription.getText());
                CustomTextInterface customTextInterface = CustomTextFactory.getModel();
                try {
                    //Starting the talking with the server
                    customTextInterface.create_XML(customText);
                    new Alert(Alert.AlertType.INFORMATION, "Content Added", ButtonType.OK).showAndWait();
                    txtLocation.setText("");
                    txtName.setText("");
                    uploadDate.getEditor().clear();
                    taDescription.setText("");
                    btnModifyContent.setDisable(true);
                    btnDeleteContent.setDisable(true);
                    tgType.selectToggle(null);
                    client = ContentFactory.getModel();
                    clientsData = FXCollections.observableArrayList(client.findAllContents_XML(new GenericType<List<Content>>() {
                    }));
                    tableCustomImage.setItems(clientsData);
                    tableCustomImage.refresh();
                } catch (ClientErrorException ex) {
                    Logger.getLogger(ContentWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "The Upload Date is not set", ButtonType.OK).showAndWait();
        }
    }

    @FXML
    private void handleModifyContentButtonAction(ActionEvent event
    ) {
    }

    @FXML
    private void handleDeleteContentButtonAction(ActionEvent event
    ) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete Content");
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to delete this content?");
        Optional<ButtonType> action = alert.showAndWait();
        //If you click on OK
        if (action.get() == ButtonType.OK) {

        }
    }

    @FXML
    private void handleFindButtonAction(ActionEvent event
    ) {
        if (cboxParameter.getSelectionModel().getSelectedItem().equals("Location")) {

        }

        if (cboxParameter.getSelectionModel().getSelectedItem().equals("Name")) {

        }

        if (cboxParameter.getSelectionModel().getSelectedItem().equals("Upload Date")) {

        }
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
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Open Resource File");
        File image = fileChooser.showOpenDialog(stage);
        Image testing = new Image(image.toURI().toString());
        imagePreview.setImage(testing);
        String path = image.getAbsolutePath();
        try {
            BufferedImage buffImage = ImageIO.read(new File(path));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buffImage, "jpg", baos);
            baos.flush();
            byte[] imageInbytes = baos.toByteArray();
            imageBytes = byteToByte(imageInbytes);
        } catch (IOException ex) {
            Logger.getLogger(ContentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        btnDeleteContent.setDisable(true);
        btnAddContent.setDisable(true);
        btnModifyContent.setDisable(true);
        btnFileChooser.setDisable(true);
        txtName.setDisable(true);
        taDescription.setDisable(true);
        txtLocation.setDisable(true);
        uploadDate.setDisable(true);
        btnFind.setDisable(true);
        cboxParameter.getItems().addAll(
                "Location",
                "Upload Date",
                "Name");
        // Handle action events for the radio buttons. 
        rbCustomImage.setOnAction(e -> {
            btnFileChooser.setDisable(false);
            txtName.setDisable(false);
            txtLocation.setDisable(false);
            uploadDate.setDisable(false);
            taDescription.setDisable(true);
        });

        rbCustomText.setOnAction(e -> {
            btnFileChooser.setDisable(true);
            txtName.setDisable(false);
            txtLocation.setDisable(false);
            uploadDate.setDisable(false);
            taDescription.setDisable(false);
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

        //If text fields values are longer than 25/100 (max value in Database), show error message and disable 
        //button
        if (txtName.getText().trim().length() > 25) {
            txtName.setText(txtName.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the name is 25 characters", ButtonType.OK).showAndWait();
            btnAddContent.setDisable(true);
        }
        if (txtLocation.getText().trim().length() > 25) {
            txtLocation.setText(txtLocation.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the location is 25 characters", ButtonType.OK).showAndWait();
            btnAddContent.setDisable(true);
        }
        if (rb.equals(rbCustomText) && taDescription.getText().trim().length() > 100) {
            taDescription.setText(taDescription.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the description is 100 characters", ButtonType.OK).showAndWait();
            btnAddContent.setDisable(true);
        }
        //If text fields are empty disable buttton
        if (txtName.getText().trim().isEmpty()
                || txtLocation.getText().trim().isEmpty()
                || (rb.equals(rbCustomText) && taDescription.getText().trim().isEmpty())) {
            btnAddContent.setDisable(true);
        } //Else, enable button
        else {
            btnAddContent.setDisable(false);
            btnDeleteContent.setDisable(false);
            btnModifyContent.setDisable(false);
        }

    }

    private void searchTextPropertyChange(ObservableValue observable,
            String oldValue,
            String newValue) {
        if (cboxParameter.getSelectionModel().getSelectedItem() == null && txtValue.getText().trim().isEmpty()) {
            btnFind.setDisable(true);
        }

        //If text field is empty disable  buttton
        if (txtValue.getText().trim().isEmpty()) {
            btnFind.setDisable(true);
        } //Else, enable  button
        else {
            btnFind.setDisable(false);
        }
    }

    public Byte[] byteToByte(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        Arrays.setAll(bytes, n -> bytesPrim[n]);
        return bytes;
    }
}
