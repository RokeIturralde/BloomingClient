/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package ui.album;

import businessLogic.album.FactoryAlbum;
import businessLogic.album.AlbumInterface;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import objects.Album;
import objects.User;

/**
 * Album window controller.
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
    private Button btnPrintMyAlbums;
    @FXML
    private Button btnPrintSharedAlbums;
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
    private static User loggedUser;
    private AlbumInterface client;

    /**
     * Initializing the window method
     *
     * @param root root object with the DOM charged
     * @param loggedUser
     */
    public void initStage(Parent root, User loggedUser) {
        LOGGER.info("Initializing Albums window");
        this.loggedUser = loggedUser;
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
        txtValue.textProperty().addListener(this::searchTextPropertyChange);
        txtAlbumName.textProperty().addListener(this::textChanged);
        txtAlbumCreator.textProperty().addListener(this::textChanged);
        txtAddUser.textProperty().addListener(this::textChanged);
        taAlbumDesc.textProperty().addListener(this::textChanged);
        tbMyAlbums.getSelectionModel().selectedItemProperty()
                .addListener(this::handleUsersTableSelectionChanged);

        //Charge tables data
        try {
            client = FactoryAlbum.getModel();
            clientsData = FXCollections.observableArrayList(client.findMyAllAlbums_XML(new GenericType<List<Album>>() {
            }, loggedUser.getLogin()));
            tbMyAlbums.setItems(clientsData);
            tbMyAlbums.refresh();

            clientsData = FXCollections.observableArrayList(client.findMyAllSharedAlbums_XML(new GenericType<List<Album>>() {
            }, loggedUser.getLogin()));
            tbSharedAlbums.setItems(clientsData);
            tbSharedAlbums.refresh();

        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        //Show the window
        stage.show();
    }

    /**
     * Method that handles the startup of the Albums view.
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
        //All buttons despite the menus ones, prints, clears and help, are disable at first
        btnFind.setDisable(true);
        btnAdd.setDisable(true);
        btnCreateAlbum.setDisable(true);
        btnModifyAlbum.setDisable(true);
        btnDeleteAlbum.setDisable(true);
        btnAdd.setDisable(true);

        //Filds disabled at first
        txtAddUser.setDisable(true);
        taUsers.setDisable(true);

        //Charge into the combobox the select actions and selecting the first.
        cbSearchType.getItems().set(1, "Name");
        cbSearchType.getItems().set(2, "Date");
        cbSearchType.getItems().set(3, "Creator");
        cbSearchType.getSelectionModel().selectFirst();

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
     * Method that handles the button "Find"
     *
     * @param event The action event object
     */
    @FXML
    private void handleFindAlbumButtonAction(ActionEvent event) {
        LOGGER.info("Metodo de control del boton de Find");
        String busqueda = (String) cbSearchType.getSelectionModel().getSelectedItem();
        String text = txtValue.getText();
        if (busqueda.equalsIgnoreCase("Name")) {
            if (checkShared.isSelected()) {
                clientsData = FXCollections.observableArrayList(client.findMySharedAlbumsByName_XML(new GenericType<List<Album>>() {
                }, loggedUser.getLogin(), text));
                tbMyAlbums.setItems(clientsData);
                tbMyAlbums.refresh();
            } else {
                clientsData = FXCollections.observableArrayList(client.findMyAlbumsByName_XML(new GenericType<List<Album>>() {
                }, loggedUser.getLogin(), text));
                tbSharedAlbums.setItems(clientsData);
                tbSharedAlbums.refresh();
            }
        } else if ((busqueda.equalsIgnoreCase("Date"))) {
            if (checkShared.isSelected()) {
                clientsData = FXCollections.observableArrayList(client.findMySharedAlbumsByDate_XML(new GenericType<List<Album>>() {
                }, loggedUser.getLogin(), text));
                tbMyAlbums.setItems(clientsData);
                tbMyAlbums.refresh();
            } else {
                clientsData = FXCollections.observableArrayList(client.findMyAlbumsByDate_XML(new GenericType<List<Album>>() {
                }, loggedUser.getLogin(), text));
                tbSharedAlbums.setItems(clientsData);
                tbSharedAlbums.refresh();
            }
        } else if ((busqueda.equalsIgnoreCase("Creator"))) {
            clientsData = FXCollections.observableArrayList(client.findMySharedAlbumsByCreator_XML(new GenericType<List<Album>>() {
            }, loggedUser.getLogin(), text));
            tbSharedAlbums.setItems(clientsData);
            tbSharedAlbums.refresh();
        }
    }

    /**
     * Method that handles the button "Add"
     *
     * @param event The action event object
     */
    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        LOGGER.info("Metodo de control del boton de Add");
        ArrayList<User> users = new ArrayList();
        try {
            String login = txtAddUser.getText();
            //buscar usuario por login
            User user = null;
            users.add(user);
            taUsers.setText(arrayToString(users));
        } catch (Exception e) {
        }
    }

    /**
     * Method that take the array of users and pass it to string
     *
     * @param users An array list of users with the users who can see the album
     * @return A String with the array passed to string.
     */
    private String arrayToString(ArrayList<User> users) {
        String array = "Users logins: "
                + "\n";
        String userLogin = null;
        for (int i = 0; i < users.size(); i++) {
            userLogin = users.get(i).getLogin();
            array = array.concat(userLogin + "\n");
        }
        return array;
    }

    /**
     * Method that handles the button "Create a new Album"
     *
     * @param event The action event object
     */
    @FXML
    private void handleCreateAlbumButtonAction(ActionEvent event) {
        LOGGER.info("Metodo de control del boton de Create a new Album");
        Album album = new Album();
        album.setName(txtAlbumName.getText());
        //album.setCreationDate(dpCreationDate.getValue());
        album.setCreator(loggedUser);
        taAlbumDesc.getText();
        taUsers.setText(arrayToString((ArrayList<User>) album.getUsers()));

        client = FactoryAlbum.getModel();
        client.createAlbum_XML(album);

        //Vaciar campos
        txtAlbumName.setText("");
        dpCreationDate.setValue(null);
        txtAlbumCreator.setText("");
        taAlbumDesc.setText("");
        checkShare.setSelected(false);
        txtAddUser.setText("");
        taUsers.setText("");

    }

    /**
     * Method that handles the button "Modify an Album"
     *
     * @param event The action event object
     */
    @FXML
    private void handleModifyAlbumButtonAction(ActionEvent event) {
        LOGGER.info("Metodo de control del boton de Modify an Album");
    }

    /**
     * Method that handles the button "Delete an Album"
     *
     * @param event The action event object
     */
    @FXML
    private void handleDeleteAlbumButtonAction(ActionEvent event) {
        LOGGER.info("Metodo de control del boton de Delete an Album");
    }

    /**
     * Method that handles the button "Albums" from menu
     *
     * @param event The action event object
     */
    @FXML
    private void handleAlbumsButtonAction(ActionEvent event) {
        LOGGER.info("Metodo de control del boton de Albums");
        this.stage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UIAlbum.fxml"));
            Parent root = (Parent) loader.load();
            //Obtain the Sign In window controller
            AlbumsViewController controller = (AlbumsViewController) loader.getController();
            controller.setStage(stage);
            controller.initStage(root, loggedUser);
        } catch (IOException ex) {
            Logger.getLogger(AlbumsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method that handles the btnClearShearch button.
     *
     * @param event The action event object
     */
    @FXML
    private void handleClearShearchButtonAction(ActionEvent event) {
        LOGGER.info("Metodo de control del boton de Clear search");
        cbSearchType.getSelectionModel().selectFirst();
        checkShared.setSelected(false);
        txtValue.setText("");
    }

    /**
     * Method that handles the btnClearInfo button.
     *
     * @param event The action event object
     */
    @FXML
    private void handleClearInfoButtonAction(ActionEvent event) {
        LOGGER.info("Metodo de control del boton de Clear info");
        txtAlbumName.setText("");
        dpCreationDate.setValue(null);
        txtAlbumCreator.setText("");
        taAlbumDesc.setText("");
        checkShare.setSelected(false);
        txtAddUser.setText("");
        taUsers.setText("");
    }

    /**
     * Text changed event handler. Validate that the combobox has something
     * selected to enable value fild and that value fild has text to enable the
     * find button.
     *
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
     */
    private void searchTextPropertyChange(ObservableValue observable, String oldValue, String newValue) {
        if (cbSearchType.getSelectionModel().getSelectedItem() == null && txtValue.getText().trim().isEmpty()) {
            btnFind.setDisable(true);
        } else {
            txtValue.setDisable(false);
        }

        //If text field is empty disable  buttton
        if (txtValue.getText().trim().isEmpty()) {
            btnFind.setDisable(true);
        } //Else, enable  button
        else {
            btnFind.setDisable(false);
        }

        if (txtValue.getText().trim().length() > 25) {
            txtValue.setText(txtValue.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum lenght for the login is 25 characters.", ButtonType.OK).showAndWait();
            btnCreateAlbum.setDisable(true);
            btnModifyAlbum.setDisable(true);
        }
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

        //If text fields are empty disable buttton
        if (txtAlbumName.getText().trim().isEmpty()
                || txtAlbumCreator.getText().trim().isEmpty()) {
            btnAdd.setDisable(true);
        } //Else, enable button
        else {
            btnAdd.setDisable(false);
            btnModifyAlbum.setDisable(false);
            btnDeleteAlbum.setDisable(false);
        }

    }

    /**
     * MyAlbums and sharedAlbums tables selection changed event handler. It
     * enables or disables buttons depending on selection state of the table.
     *
     * @param observable the property being observed: SelectedItem Property
     * @param oldValue old UserBean value for the property.
     * @param newValue new UserBean value for the property.
     */
    private void handleUsersTableSelectionChanged(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        //If there is a row selected, move row data to corresponding fields in the
        //window and enable create, modify and delete buttons
        if (newValue != null) {
            Album album = (Album) newValue;
            txtAlbumName.setText(album.getName());
            //dpCreationDate.setValue(album.getCreationDate());
            txtAlbumCreator.setText(album.getCreator().getLogin());
            taAlbumDesc.setText(album.getDescription());
            if (album.getUsers().isEmpty()) {
                checkShare.isSelected();
                taUsers.setText(arrayToString((ArrayList<User>) album.getUsers()));
            } else {
                btnCreateAlbum.setDisable(false);
            }
            btnModifyAlbum.setDisable(false);
            btnDeleteAlbum.setDisable(false);
        } else {
            //If there is not a row selected, clean window fields 
            //and disable create, modify and delete buttons
            txtAlbumName.setText("");
            dpCreationDate.setValue(null);
            txtAlbumCreator.setText("");
            taAlbumDesc.setText("");
            checkShare.setSelected(false);
            txtAddUser.setText("");
            taUsers.setText("");
            btnCreateAlbum.setDisable(true);
            btnModifyAlbum.setDisable(true);
            btnDeleteAlbum.setDisable(true);
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
