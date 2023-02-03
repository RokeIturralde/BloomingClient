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
import changePassword.ChangePasswordController;
import exceptions.ClientErrorException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import javafx.stage.Stage;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
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
import javafx.util.Callback;
import javax.imageio.ImageIO;
import javax.swing.WindowConstants;
import javax.ws.rs.core.GenericType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import objects.CustomImage;
import objects.Content;
import objects.CustomText;
import objects.User;
import ui.album.AlbumsViewController;
import ui.content.help.HelpController;

/**
 * FXML Controller class
 *
 * @author Roke
 */
public class ContentWindowController {

    //Defined the attributes for the window controller
    @FXML
    private Button btnLogo;
    @FXML
    private Button btnChangePassword;
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
    @FXML
    private Button btnClearSelection;

    private ObservableList<Content> contentList;
    private ArrayList<CustomImage> customImageList;
    private ArrayList<CustomText> customTextList;
    private final String tableImage = "Show Image";
    private Byte[] imageBytes = null;
    private Stage stage;
    private ContentInterface client;
    private static final Logger LOGGER = Logger.getLogger("package ui.content");
    private boolean customImageTableIsSelected = false;
    private boolean customTextTableIsSelected = false;

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
                /**
                 * If the table of Image is selected the fields are introduced
                 * in the designated textfields and an image preview appears
                 */
                btnFileChooser.setDisable(false);
                rbCustomText.setDisable(true);
                rbCustomImage.setDisable(false);
                tableCustomText.getSelectionModel().clearSelection();
                rbCustomImage.setSelected(true);
                txtValue.setDisable(false);
                int selectedRow = tableCustomImage.getSelectionModel().getSelectedIndex();
                txtName.setText(customImageList.get(selectedRow).getName());
                txtName.setDisable(false);
                txtLocation.setText(customImageList.get(selectedRow).getLocation());
                txtLocation.setDisable(false);
                uploadDate.setValue(customImageList.get(selectedRow).getUploadDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                uploadDate.setDisable(false);
                customImageTableIsSelected = true;
                customTextTableIsSelected = false;
                taDescription.setDisable(true);
                taDescription.setText("");
                //Here the image is printed
                imageBytes = customImageList.get(selectedRow).getBytes();
                byte[] byteArray = new byte[imageBytes.length];
                for (int i = 0; i < imageBytes.length; i++) {
                    byteArray[i] = imageBytes[i];
                }
                InputStream inputStream = new ByteArrayInputStream(byteArray);
                imagePreview.setImage(new Image(inputStream));

            }
        });
        tableCustomText.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                /**
                 * If the Text table is selected something similar happens, but
                 * the image part is eluded
                 */
                rbCustomText.setDisable(false);
                rbCustomImage.setDisable(true);
                tableCustomImage.getSelectionModel().clearSelection();
                rbCustomText.setSelected(true);
                btnAddContent.setDisable(true);
                int selectedRow = tableCustomText.getSelectionModel().getSelectedIndex();
                txtName.setText(customTextList.get(selectedRow).getName());
                txtName.setDisable(false);
                txtLocation.setText(customTextList.get(selectedRow).getLocation());
                txtLocation.setDisable(false);
                uploadDate.setValue(customTextList.get(selectedRow).getUploadDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                uploadDate.setDisable(false);
                taDescription.setText(customTextList.get(selectedRow).getText());
                taDescription.setDisable(false);
                imagePreview.setImage(null);
                btnFileChooser.setDisable(true);
                customImageTableIsSelected = false;
                customTextTableIsSelected = true;

            }
        });
        //Set the tables with values
        tableCustomImage.setEditable(false);
        tableCustomText.setEditable(false);
        customImageName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        customImageLocation.setCellValueFactory(
                new PropertyValueFactory<>("location"));
        customImageImage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Content, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Content, String> uploadDate) {
                SimpleStringProperty property = new SimpleStringProperty();
                property.setValue("View Image");
                return property;
            }
        });
        customTextName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        customTextLocation.setCellValueFactory(
                new PropertyValueFactory<>("location"));
        customImageUploadDate.setCellValueFactory(
                new PropertyValueFactory<>("uploadDate"));

        customImageUploadDate.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<objects.Content, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<objects.Content, String> param) {
                SimpleStringProperty property = new SimpleStringProperty();
                if (param.equals(null)) {
                    property.setValue(null);
                } else {
                    property.setValue(formatDate(param.getValue().getUploadDate()).toString());
                }
                return property;
            }
        });

        customTextUploadDate.setCellValueFactory(
                new PropertyValueFactory<>("uploadDate"));
        customTextDescription.setCellValueFactory(
                new PropertyValueFactory<>("text"));
        /**
         * Here the tables are loaded with data
         */
        try {
            /**
             * After getting the interface, which has the REST, we call to the
             * server in order to gather the information
             */
            client = ContentFactory.getModel();
            contentList = FXCollections.observableArrayList(client.findAllContents_XML(new GenericType<List<Content>>() {
            }));
            customImageList = findAllCustomImages(contentList);
            customTextList = findAllCustomTexts(contentList);
            ObservableList<CustomImage> listCustomImage = FXCollections.observableArrayList(customImageList);
            ObservableList<CustomText> listCustomText = FXCollections.observableArrayList(customTextList);
            tableCustomImage.setItems(listCustomImage);
            tableCustomText.setItems(listCustomText);
        } catch (ClientErrorException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed connecting to the server, try again later please", ButtonType.OK).showAndWait();
        }

        LOGGER.info("Iniciando ContentWindowController::handlerWindowShowing");
        btnDeleteContent.setDisable(true);
        btnAddContent.setDisable(true);
        btnModifyContent.setDisable(true);
        btnFileChooser.setDisable(true);
        txtName.setDisable(true);
        txtValue.setDisable(true);
        taDescription.setDisable(true);
        txtLocation.setDisable(true);
        uploadDate.setDisable(true);
        btnFind.setDisable(true);
        cboxParameter.getItems().addAll(
                "",
                "Name"
        );
        cboxParameter.valueProperty().addListener((ObservableList, oldValue, newValue) -> {
            if (newValue.equals("Name")) {
                txtValue.setDisable(false);
            } else {
                txtValue.setDisable(true);
            }
        });
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

        stage.show();
    }

    /**
     * This method clears the searching part of the window (The value and the
     * Combobox) and returns the tables to their original state
     *
     * @param event
     */
    @FXML
    private void handleClearButtonAction(ActionEvent event
    ) {
        //Empty all the fields 
        txtValue.setText("");
        txtValue.setDisable(true);
        cboxParameter.getSelectionModel().selectFirst();
        //Return the tables to the standard mode (all info)
        try {
            client = ContentFactory.getModel();
            contentList = FXCollections.observableArrayList(client.findAllContents_XML(new GenericType<List<Content>>() {
            }));
            customImageList = findAllCustomImages(contentList);
            customTextList = findAllCustomTexts(contentList);
            ObservableList<CustomImage> listCustomImage = FXCollections.observableArrayList(customImageList);
            ObservableList<CustomText> listCustomText = FXCollections.observableArrayList(customTextList);
            tableCustomImage.setItems(listCustomImage);
            tableCustomText.setItems(listCustomText);
        } catch (ClientErrorException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * This method creates a Content, either being an Image or a Text
     *
     * @param event
     */
    @FXML
    private void handleAddContentButtonAction(ActionEvent event
    ) {
        /**
         * First we check if the Data Picker has any data, if not the user gets
         * a notification
         */
        LocalDate datePicker = uploadDate.getValue();
        if (datePicker != null) {
            /**
             * Here we do the check for which radio button is selected in order
             * to enable some fields or others
             */
            if (rbCustomImage.isSelected()) {
                /**
                 * First we check that the image is loaded
                 */
                if (imageBytes != null) {
                    /**
                     * We charge the CustomImage object with the data provided
                     * by the user
                     */
                    CustomImage customImage = new CustomImage();
                    customImage.setName(txtName.getText());
                    customImage.setLocation(txtLocation.getText());
                    Date date = Date.from(datePicker.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    customImage.setUploadDate(date);
                    customImage.setBytes(imageBytes);
                    /**
                     * After getting the REST we call the method to create a new
                     * Image Content
                     */
                    CustomImageInterface customImageInterface = CustomImageFactory.getModel();
                    try {
                        customImageInterface.createCustomImage_XML(customImage);
                        /**
                         * After the method is done the user gets a notification
                         * of the successfully added content and all the data is
                         * cleared, also tables are refreshed, showing the new
                         * created content
                         */
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
                        contentList = FXCollections.observableArrayList(client.findAllContents_XML(new GenericType<List<Content>>() {
                        }));
                        customImageList = findAllCustomImages(contentList);
                        ObservableList<CustomImage> listCustomImage = FXCollections.observableArrayList(customImageList);
                        tableCustomImage.setItems(listCustomImage);
                        tableCustomImage.refresh();

                    } catch (ClientErrorException ex) {
                        Logger.getLogger(ContentWindowController.class
                                .getName()).log(Level.SEVERE, null, ex);
                        new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, "Failed connecting to the server, try again later please", ButtonType.OK).showAndWait();
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
                    //Starting the talking with the server and clear of the fields if the method is successfull
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
                    contentList = FXCollections.observableArrayList(client.findAllContents_XML(new GenericType<List<Content>>() {
                    }));
                    customTextList = findAllCustomTexts(contentList);
                    ObservableList<CustomText> listCustomText = FXCollections.observableArrayList(customTextList);
                    tableCustomText.setItems(listCustomText);
                    tableCustomText.refresh();

                } catch (ClientErrorException ex) {
                    Logger.getLogger(ContentWindowController.class
                            .getName()).log(Level.SEVERE, null, ex);
                    new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Failed connecting to the server, try again later please", ButtonType.OK).showAndWait();
                }
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "The Upload Date is not set", ButtonType.OK).showAndWait();
        }
    }

    /**
     * This method is attached to Modify Content button and does what it means
     *
     * @param event
     */
    @FXML
    private void handleModifyContentButtonAction(ActionEvent event
    ) {
        /**
         * First we check which of the table is selected in order to call a
         * method or other
         */
        if (customImageTableIsSelected) {
            /**
             * In this case the image one is, so we charge all the data to the
             * Object
             */
            int selectedRow = tableCustomImage.getSelectionModel().getSelectedIndex();
            CustomImageInterface customImageInterface = CustomImageFactory.getModel();
            CustomImage customImage = new CustomImage();
            customImage.setContentId(customImageList.get(selectedRow).getContentId());
            customImage.setName(txtName.getText());
            customImage.setLocation(txtLocation.getText());
            Date date = Date.from(uploadDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            customImage.setUploadDate(date);
            customImage.setBytes(imageBytes);
            /**
             * Once all is set up we call the REST method and if successfull,
             * the user gets notified
             */
            try {
                customImageInterface.edit_XML(customImage);
                contentList = FXCollections.observableArrayList(client.findAllContents_XML(new GenericType<List<Content>>() {
                }));
                new Alert(Alert.AlertType.INFORMATION, "Content Modified", ButtonType.OK).showAndWait();
            } catch (ClientErrorException ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                Logger
                        .getLogger(ContentWindowController.class
                                .getName()).log(Level.SEVERE, null, ex);
            }
            /**
             * After the call we refresh the table to show the modified data
             */
            customImageList = findAllCustomImages(contentList);
            ObservableList<CustomImage> listCustomImage = FXCollections.observableArrayList(customImageList);
            tableCustomImage.setItems(listCustomImage);
            tableCustomImage.refresh();

        } else if (customTextTableIsSelected) {
            /**
             * In this case the Text Table is selected, and same process here
             */
            int selectedRow = tableCustomText.getSelectionModel().getSelectedIndex();
            CustomTextInterface customTextInterface = CustomTextFactory.getModel();
            CustomText customText = new CustomText();
            customText.setContentId(customTextList.get(selectedRow).getContentId());
            customText.setName(txtName.getText());
            customText.setLocation(txtLocation.getText());
            Date date = Date.from(uploadDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            customText.setUploadDate(date);
            customText.setText(taDescription.getText());
            /**
             * After the object is filled with data, is sent to the REST and if
             * done correctly, a notification is shown to the user and also the
             * tables are refreshed
             */
            try {
                customTextInterface.edit_XML(customText);
                contentList = FXCollections.observableArrayList(client.findAllContents_XML(new GenericType<List<Content>>() {
                }));
                new Alert(Alert.AlertType.INFORMATION, "Content Modified", ButtonType.OK).showAndWait();
            } catch (ClientErrorException ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                Logger
                        .getLogger(ContentWindowController.class
                                .getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed connecting to the server, try again later please", ButtonType.OK).showAndWait();
            }
            customTextList = findAllCustomTexts(contentList);
            ObservableList<CustomText> listCustomText = FXCollections.observableArrayList(customTextList);
            tableCustomText.setItems(listCustomText);
            tableCustomText.refresh();

        }
    }

    /**
     * This method Deletes a Content from the tables
     *
     * @param event
     */
    @FXML
    private void handleDeleteContentButtonAction(ActionEvent event
    ) {
        /**
         * First we ask the user for a confirmation
         */
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete Content");
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to delete this content?");
        Optional<ButtonType> action = alert.showAndWait();
        //If you click on OK
        if (action.get() == ButtonType.OK) {
            if (customImageTableIsSelected) {
                /**
                 * If the Image table is selected we get the content id and then
                 * we call the method
                 */
                int selectedRow = tableCustomImage.getSelectionModel().getSelectedIndex();
                ContentInterface contentInterface = ContentFactory.getModel();
                try {
                    contentInterface.remove(customImageList.get(selectedRow).getContentId() + "");
                    contentList = FXCollections.observableArrayList(contentInterface.findAllContents_XML(new GenericType<List<Content>>() {
                    }));
                } catch (ClientErrorException ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                    Logger
                            .getLogger(ContentWindowController.class
                                    .getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Failed connecting to the server, try again later please", ButtonType.OK).showAndWait();
                }
                /**
                 * Once the method is finnished the table refreshes and the
                 * fields are cleared
                 */
                customImageList = findAllCustomImages(contentList);
                ObservableList<CustomImage> listCustomImage = FXCollections.observableArrayList(customImageList);
                tableCustomImage.setItems(listCustomImage);
                tableCustomImage.refresh();
                //Clear all 
                imagePreview.setImage(null);
                txtLocation.setText("");
                txtName.setText("");
                uploadDate.setValue(null);
                btnModifyContent.setDisable(true);
                btnDeleteContent.setDisable(true);
            } else if (customTextTableIsSelected) {
                /**
                 * Same method as before but getting the id from the other table
                 */
                int selectedRow = tableCustomText.getSelectionModel().getSelectedIndex();
                ContentInterface contentInterface = ContentFactory.getModel();
                try {
                    contentInterface.remove(customTextList.get(selectedRow).getContentId() + "");
                    contentList = FXCollections.observableArrayList(contentInterface.findAllContents_XML(new GenericType<List<Content>>() {
                    }));
                } catch (ClientErrorException ex) {
                    new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                    Logger
                            .getLogger(ContentWindowController.class
                                    .getName()).log(Level.SEVERE, null, ex);
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Failed connecting to the server, try again later please", ButtonType.OK).showAndWait();
                }
                /**
                 * Refreshing the table and clearing fields
                 */
                customTextList = findAllCustomTexts(contentList);
                ObservableList<CustomText> listCustomText = FXCollections.observableArrayList(customTextList);
                tableCustomText.setItems(listCustomText);
                tableCustomText.refresh();
                //Clear all 
                taDescription.setText("");
                txtLocation.setText("");
                txtName.setText("");
                uploadDate.setValue(null);
                btnModifyContent.setDisable(true);
                btnDeleteContent.setDisable(true);
            }
        }
    }

    /**
     * This method Finds Content by the selected parameter
     *
     * @param event
     */
    @FXML
    private void handleFindButtonAction(ActionEvent event
    ) {
        if (cboxParameter.getSelectionModel().getSelectedItem().equals("Name")) {
            try {
                /**
                 * As the search is wanted by name we call the given method
                 */
                contentList = FXCollections.observableArrayList(client.findContentByName_XML(new GenericType<List<Content>>() {
                }, txtValue.getText()));
                /**
                 * Here the table is loaded with the data if the method is done
                 * correctly
                 */
                customImageList = findCustomImageByName(contentList, txtValue.getText());
                //customTextList = findCustomTextByName(contentList, txtValue.getText());
                ObservableList<CustomImage> listCustomImage = FXCollections.observableArrayList(customImageList);
                // ObservableList<CustomText> listCustomText = FXCollections.observableArrayList(customTextList);
                tableCustomImage.setItems(listCustomImage);
                tableCustomImage.refresh();
                //tableCustomText.setItems(listCustomText);
                //tableCustomText.refresh();
            } catch (ClientErrorException ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                Logger
                        .getLogger(ContentWindowController.class
                                .getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed connecting to the server, try again later please", ButtonType.OK).showAndWait();
            }
        }
    }

    /**
     * This method leads you to the main page of the app (Album)
     *
     * @param event
     */
    @FXML
    private void handleLogoButtonAction(ActionEvent event
    ) {
    }

    /**
     * This method leads you to the Album page
     *
     * @param event
     */
    @FXML
    private void handleAlbumButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ui/album/UIAlbum.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the Sign In window controller
            AlbumsViewController controller = (AlbumsViewController) loader.getController();

            controller.setStage(stage);
            User user = new User();
            user.setLogin("u1");
            controller.initStage(root, user);
        } catch (IOException ex) {
            Logger.getLogger(ContentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method leads you to About Us page
     *
     * @param event
     */
    @FXML
    private void handleAboutUsButtonAction(ActionEvent event
    ) {
    }

    /**
     * This method leads you to My Profile window
     *
     * @param event
     */
    @FXML
    private void handleChangeButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/changePassword/ChangePasswordWindow.fxml"));
            Parent root = (Parent) loader.load();
            Stage stageAlbum = new Stage();
            //Obtain the Sign In window controller
            ChangePasswordController controller = (ChangePasswordController) loader.getController();
            controller.setStage(stageAlbum);
            controller.initStage(root);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * This method leads you to Membership window
     *
     * @param event
     */
    @FXML
    private void handleMembershipButtonAction(ActionEvent event
    ) {
    }

    @FXML
    private void handleHelpButtonAction(ActionEvent event
    ) {
        try {
            LOGGER.info("Loading help view...");
            //Load node graph from fxml file
            FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("/ui/content/help/Help.fxml"));
            Parent root = (Parent) loader.load();
            HelpController helpController
                    = ((HelpController) loader.getController());
            //Initializes and shows help stage
            helpController.initAndShowStage(root);
        } catch (Exception ex) {
            //If there is an error show message and
            //log it.
            LOGGER.log(Level.SEVERE,
                    "UI ContentWindowController: Error loading help window: {0}",
                    ex.getMessage());
        }
    }

    /**
     * This method deals with the file chooser button
     *
     * @param event
     */
    @FXML
    private void handleFileChooserButtonAction(ActionEvent event
    ) {
        /**
         * Here we state that we only accept jpg images, as they are the most
         * common for albums
         */
        FileChooser.ExtensionFilter imageFilter
                = new FileChooser.ExtensionFilter("Image Files", "*.jpg");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(imageFilter);
        fileChooser.setTitle("Open Resource File");
        File image = fileChooser.showOpenDialog(stage);
        Image testing = new Image(image.toURI().toString());
        // Here the image selected is shown to the user
        imagePreview.setImage(testing);
        String path = image.getAbsolutePath();
        /**
         * Here the image is converted into a Byte[] and stored in a variable
         * (imageBytes)
         */
        try {
            BufferedImage buffImage = ImageIO.read(new File(path));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buffImage, "jpg", baos);
            baos.flush();
            byte[] imageInbytes = baos.toByteArray();
            imageBytes = byteToByte(imageInbytes);

        } catch (IOException ex) {
            Logger.getLogger(ContentWindowController.class
                    .getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }

    }

    /**
     * This method creates the Report of the Custom Image Table
     *
     * @param event
     */
    @FXML
    private void handlePrintCustomImageButtonAction(ActionEvent event) {
        /**
         * We select the jrxml and the report is loaded with the table
         * information
         */
        try {
            InputStream input = getClass().getResourceAsStream("CustomImage.jrxml");
            JasperReport report = JasperCompileManager.compileReport(input);
            input.close();
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Content>) this.tableCustomImage.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {
            Logger.getLogger(ContentWindowController.class
                    .getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(ContentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method creates a report with the Custom Text Table
     *
     * @param event
     */
    @FXML
    private void handlePrintCustomTextButtonAction(ActionEvent event
    ) {
        new Alert(Alert.AlertType.INFORMATION, "This part is not implemented yet, be sure to check out our new updates!", ButtonType.OK).showAndWait();
        /**
         * We select the jrxml and the report is loaded with the table
         * information
         */
        /*  try {
            JasperReport report = JasperCompileManager.compileReport("src/report/CustomText.jrxml");
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Content>) this.tableCustomText.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ContentWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    /**
     * This method clears all the data from the creation/modification part of
     * the window, returning it to his original state
     *
     * @param event
     */
    @FXML
    private void btnClearSelectionButtonAction(ActionEvent event) {
        /**
         * We deselect the radio buttons, clear all the text, and disable the
         * crud buttons
         */
        customTextTableIsSelected = false;
        customTextTableIsSelected = false;
        rbCustomImage.setDisable(false);
        rbCustomText.setSelected(false);
        rbCustomText.setDisable(false);
        rbCustomImage.setSelected(true);
        imagePreview.setImage(null);
        //Clear all 
        taDescription.setText("");
        txtLocation.setText("");
        txtName.setText("");
        uploadDate.setValue(null);
        btnModifyContent.setDisable(true);
        btnDeleteContent.setDisable(true);
        taDescription.setText("");
        btnAddContent.setDisable(true);
        btnFileChooser.setDisable(true);
        uploadDate.setDisable(true);
        txtLocation.setDisable(true);
        txtName.setDisable(true);
    }

    /**
     * Prepare the stage for a change of scene
     *
     * @param stage where the window shows
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * This method makes the buttons to be disabled and also fill the combo with
     * values and set actions to the radio buttons
     *
     * @param event
     */
    private void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Iniciando ContentWindowController::handlerWindowShowing");
        btnDeleteContent.setDisable(true);
        btnAddContent.setDisable(true);
        btnModifyContent.setDisable(true);
        btnFileChooser.setDisable(true);
        txtName.setDisable(true);
        txtValue.setDisable(true);
        taDescription.setDisable(true);
        txtLocation.setDisable(true);
        uploadDate.setDisable(true);
        btnFind.setDisable(true);
        cboxParameter.getItems().addAll(
                "",
                "Name"
        );
        cboxParameter.valueProperty().addListener((ObservableList, oldValue, newValue) -> {
            if (newValue.equals("Name")) {
                txtValue.setDisable(false);
            } else {
                txtValue.setDisable(true);
            }
        });
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
            btnDeleteContent.setDisable(true);
            btnModifyContent.setDisable(true);
        } //Else, enable button
        else {
            btnAddContent.setDisable(false);
            btnDeleteContent.setDisable(false);
            btnModifyContent.setDisable(false);
        }

    }

    /**
     * This method listens for changes in the search part to enable or disable
     * the find button
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void searchTextPropertyChange(ObservableValue observable,
            String oldValue,
            String newValue) {
        //If text field is empty disable  buttton
        if (txtValue.getText().trim().isEmpty()) {
            btnFind.setDisable(true);
        } //Else, enable  button
        else {
            btnFind.setDisable(false);
        }

    }

    /**
     * This method converts a byte[] to Byte[]
     *
     * @param bytesPrim
     * @return a Byte[] converted
     */
    public Byte[] byteToByte(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        Arrays.setAll(bytes, n -> bytesPrim[n]);
        return bytes;
    }

    /**
     * This method finds all the CustomImages
     *
     * @param contentList a list of all Contents
     * @return a list of all the Custom Images
     */
    public ArrayList<CustomImage> findAllCustomImages(ObservableList<Content> contentList) {
        ArrayList<CustomImage> customImageList = new ArrayList<>();
        CustomImage customImage = new CustomImage();
        CustomImageInterface customImageInterface = CustomImageFactory.getModel();
        for (int i = 0; i < contentList.size(); i++) {
            try {
                customImage = customImageInterface.findCustomTextById_XML(CustomImage.class,
                        contentList.get(i).getContentId() + "");

            } catch (ClientErrorException ex) {
                Logger.getLogger(ContentWindowController.class
                        .getName()).log(Level.SEVERE, null, ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
            if (customImage != null) {
                customImageList.add(customImage);
            }
        }
        return customImageList;
    }

    /**
     * This method Finds all the Custom Texts
     *
     * @param contentList a list of all Contents
     * @return a list of all the Custom Texts
     */
    public ArrayList<CustomText> findAllCustomTexts(ObservableList<Content> contentList) {
        ArrayList<CustomText> customTextList = new ArrayList<>();
        CustomText customText = new CustomText();
        CustomTextInterface customTextInterface = CustomTextFactory.getModel();
        for (int i = 0; i < contentList.size(); i++) {
            try {
                customText = customTextInterface.findCustomTextById_XML(CustomText.class,
                        contentList.get(i).getContentId() + "");

            } catch (ClientErrorException ex) {
                Logger.getLogger(ContentWindowController.class
                        .getName()).log(Level.SEVERE, null, ex);
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            }
            if (customText != null) {
                customTextList.add(customText);
            }
        }
        return customTextList;
    }

    /**
     * Finds all the Custom Images by Name
     *
     * @param contentList a list with all the contents
     * @param txtValue the text with the name to be searched
     * @return a list with the Custom Images with the given name
     */
    private ArrayList<CustomImage> findCustomImageByName(ObservableList<Content> contentList, String txtValue) {
        ArrayList<CustomImage> customImageList = new ArrayList<>();
        CustomImage customImage = new CustomImage();
        CustomImageInterface customImageInterface = CustomImageFactory.getModel();
        for (int i = 0; i < contentList.size(); i++) {
            if (contentList.get(i).getName().equalsIgnoreCase(txtValue)) {
                try {
                    customImage = customImageInterface.findCustomTextById_XML(CustomImage.class,
                            contentList.get(i).getContentId() + "");

                } catch (ClientErrorException ex) {
                    Logger.getLogger(ContentWindowController.class
                            .getName()).log(Level.SEVERE, null, ex);
                    new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
                }
                customImageList.add(customImage);
            }
        }
        return customImageList;
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public LocalDate formatDate(Date dateToFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        convertToLocalDateViaInstant(dateToFormat);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //return sdf.format(dateToFormat);
        LocalDate localDate = LocalDate.parse(sdf.format(dateToFormat), formatter);
        LOGGER.info(localDate.toString());
        return localDate;
    }
}
