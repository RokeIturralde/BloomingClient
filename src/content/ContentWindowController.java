/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package content;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.util.logging.Logger;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class ContentWindowController {

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
    public void initSignUp(Parent root) {
        LOGGER.info("Initializing Content window");
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
        
        /**
        //Establish the values of each field in the table
        tbcolName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbcolUploadDate.setCellValueFactory(new PropertyValueFactory<>("uploadDate"));
        tbcolLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        tbcolImage.setCellValueFactory(new PropertyValueFactory<>("bytes"));
        tbcolDescription.setCellValueFactory(new PropertyValueFactory<>("text"));
        * **/
    }
}
