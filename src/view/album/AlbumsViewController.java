/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package view.album;

import album.AlbumFactory;
import album.AlbumInterface;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.objects.Album;
import logic.objects.User;

/**
 *
 * @author Nerea
 */
public class AlbumsViewController {

    //Text Filds
    @FXML
    private TextField txtAlbumName;
    @FXML
    private TextField txtAlbumCreator;
    @FXML
    private TextField txtAddUser;
    @FXML
    private TextField txtValue;

    //Text Area
    @FXML
    private TextArea taAlbumDesc;
    @FXML
    private TextArea taUsers;

    //Date Picker
    @FXML
    private DatePicker dpCreationDate;

    //Check Boxes
    @FXML
    private CheckBox checkShare;
    @FXML
    private CheckBox checkShared;

    //Combo Box
    @FXML
    private ComboBox cbSearchType;

    //Tables
    @FXML
    private TableView tbMyAlbums;
    @FXML
    private TableView tbSharedAlbums;

    //Table Columns
    @FXML
    private TableColumn columnNameMyAlbums;
    @FXML
    private TableColumn columnCreationDateMyAlbums;
    @FXML
    private TableColumn columnPeopleMyAlbums;
    @FXML
    private TableColumn columnDescMyAlbums;
    @FXML
    private TableColumn columnNameSharedAlbums;
    @FXML
    private TableColumn columnCreatorSharedAlbums;
    @FXML
    private TableColumn columnCreationDateSharedAlbums;
    @FXML
    private TableColumn columnPeopleSharedAlbums;
    @FXML
    private TableColumn columnDescSharedAlbums;

    //Buttons
    @FXML
    private Button btnCreateAlbum;
    @FXML
    private Button btnModifyAlbum;
    @FXML
    private Button btnDeleteAlbum;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnClearInfo;
    @FXML
    private Button btnClearShearch;
    @FXML
    private Button btnFind;
    @FXML
    private Button btPrintMyAlbums;
    @FXML
    private Button btPrintSharedAlbums;
    @FXML
    private Button btnHelp;
    @FXML
    private Button btnLogo;
    @FXML
    private Button btnAlbums;
    @FXML
    private Button btnContent;
    @FXML
    private Button btnMembership;
    @FXML
    private Button btnAboutUs;
    @FXML
    private Button btnProfile;

    //Image Views
    @FXML
    private ImageView imageLogo;
    @FXML
    private ImageView imageProfile;

    //The stage of the window.
    private Stage stage;
    //Logger for the aplication. 
    private static final Logger LOGGER = Logger.getLogger("package view.Album");
    private ObservableList<Album> clientsData;
     User user;

    /**
     * Initializing the window method
     *
     * @param root root object with the DOM charged
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing Albums window");
        //Creates an scene
        Scene scene = new Scene(root);
        //Establishes an scene
        stage.setScene(scene);
        //Window title
        stage.setTitle("Albums");
        //Not resizable window
        stage.setResizable(false);
        //Set the Event handlers
        stage.setOnShowing(this::handlerWindowShowing);
        //Set the textfields with a listener
        txtValue.textProperty().addListener(this::textChanged);
        txtAlbumName.textProperty().addListener(this::textChanged);
        txtAlbumCreator.textProperty().addListener(this::textChanged);
        txtAddUser.textProperty().addListener(this::textChanged);
        taAlbumDesc.textProperty().addListener(this::textChanged);
        
        //Charge tables data
        try {
            AlbumInterface client = AlbumFactory.getModel();
            clientsData = FXCollections.observableArrayList(client.findMyAllAlbums_XML(ArrayList.class, "u1"));
            tbMyAlbums.setItems(clientsData);
            tbMyAlbums.refresh();
            
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        //Show the window
        stage.show();
    }

    /**
     * Method that handles the startup of the Albums view
     *
     * @param event event of showing the window
     */
    private void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Iniciando AlbumsViewController::handlerWindowShowing");
        //Initializing tables
        tbMyAlbums.setEditable(false);
        tbSharedAlbums.setEditable(false);
        //Initializing fields on blank
        txtValue.setText("");
        txtAlbumName.setText("");
        txtAlbumCreator.setText("");
        txtAddUser.setText("");
        taAlbumDesc.setText("");
        taUsers.setText("");
        //All buttons despite the menus ones, prints and help, are disable at first
        btnFind.setDisable(true);
        btnClearShearch.setDisable(true);
        btnAdd.setDisable(true);
        btnCreateAlbum.setDisable(true);
        btnModifyAlbum.setDisable(true);
        btnDeleteAlbum.setDisable(true);
        btnAdd.setDisable(true);
        btnClearInfo.setDisable(true);

        txtAddUser.setDisable(true);
        taUsers.setDisable(true);
        //Set factories for cell values in users table columns (My albums table)
        columnNameMyAlbums.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        columnCreationDateMyAlbums.setCellValueFactory(
                new PropertyValueFactory<>("creationDate"));
        columnPeopleMyAlbums.setCellValueFactory(
                new PropertyValueFactory<>("users"));
        columnDescMyAlbums.setCellValueFactory(
                new PropertyValueFactory<>("description"));
        //Set factories for cell values in users table columns (Shared albums table)
        columnNameSharedAlbums.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        columnCreatorSharedAlbums.setCellValueFactory(
                new PropertyValueFactory<>("creator"));
        columnCreationDateSharedAlbums.setCellValueFactory(
                new PropertyValueFactory<>("creationDate"));
        columnPeopleSharedAlbums.setCellValueFactory(
                new PropertyValueFactory<>("users"));
        columnDescSharedAlbums.setCellValueFactory(
                new PropertyValueFactory<>("description"));
       
    }

    /**
     * Method that handles the button "Create Album"
     *
     * @param event The action event object
     */
    @FXML
    private void handleCreateAlbumButtonAction(ActionEvent event) {
        LOGGER.info("Metodo de control del boton de Create Album");
    }

    /**
     * Text changed event handler. Validate that all the fields and areas are
     * not empty and that fields not surpass 25 characters and areas 150
     * characters.
     *
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
     */
    private void textChanged(ObservableValue observable,
            String oldValue,
            String newValue) {
        //Checks if the lenght is above 25 characters, showing an alert if happens and erasing the las character
        if (txtAlbumName.getText().trim().length() > 25) {
            txtAlbumName.setText(txtAlbumName.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum lenght for the login is 25 characters.", ButtonType.OK).showAndWait();
            btnCreateAlbum.setDisable(true);
            btnModifyAlbum.setDisable(true);
        }
        if (txtAlbumCreator.getText().trim().length() > 25) {
            txtAlbumCreator.setText(txtAlbumCreator.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum lenght for the password is 25 characters.", ButtonType.OK).showAndWait();
            btnCreateAlbum.setDisable(true);
            btnModifyAlbum.setDisable(true);
        }
        //TextFild Add user
        if (taAlbumDesc.getText().trim().length() > 150) {
            taAlbumDesc.setText(taAlbumDesc.getText().substring(0, 150));
            new Alert(Alert.AlertType.ERROR, "The maximum lenght for the login is 150 characters.", ButtonType.OK).showAndWait();
            btnCreateAlbum.setDisable(true);
            btnModifyAlbum.setDisable(true);
        }

        //TextFild Add user
        if (txtAddUser.getText().trim().length() > 25) {
            txtAddUser.setText(txtAddUser.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum lenght for the login is 25 characters.", ButtonType.OK).showAndWait();
            btnAdd.setDisable(true);
        }
    }

    /**
     * Enable button Create Album when all the filds to create an album are not
     * empty.
     *
     */
    private void enableCreateAlbumButton() {
        //Validates that the fields are not empty
        if (txtAlbumName.getText().trim().isEmpty() || txtAlbumCreator.getText().trim().isEmpty() || taAlbumDesc.getText().trim().isEmpty() && btnModifyAlbum.getText().equalsIgnoreCase("Accept")) {
            btnCreateAlbum.setDisable(true);
        } else {
            //All the data is filled correctly and the button is enabled
            btnCreateAlbum.setDisable(false);
        }
    }

    /**
     * Enable button Modify Album when all the filds to create an album are not
     * empty.
     *
     */
    private void enableModifyAlbumButton() {
        //Validates that the fields are not empty
        if (txtAlbumName.getText().trim().isEmpty() || txtAlbumCreator.getText().trim().isEmpty() || taAlbumDesc.getText().trim().isEmpty()) {
            btnModifyAlbum.setDisable(true);
        } else {
            //All the data is filled correctly and the button is enabled
            btnModifyAlbum.setDisable(false);
        }
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
