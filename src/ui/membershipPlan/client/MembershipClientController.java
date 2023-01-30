/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.membershipPlan.client;

import businessLogic.MemberInterface;
import businessLogic.membership.MembershipPlanInterface;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.objects.MembershipPlan;

/**
 * FXML Controller class
 *
 * @author minyb
 */
public class MembershipClientController{

    @FXML
    private TableView tbPlans;
    @FXML
    private ComboBox cbParameter;
    @FXML
    private TextArea txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnClear;
    @FXML
    private TableColumn cName;
    @FXML
    private TableColumn cDescription;
    @FXML
    private TableColumn cLimit;
    @FXML
    private TableColumn cDuration;
    @FXML
    private TableColumn cShareable;
    @FXML
    private TableColumn cUsers;
    @FXML
    private TableColumn cPrice;
    
    private ObservableList<MembershipPlan> membershipPlanData;
    private Stage stage;
    private MembershipPlanInterface membershipClient;
    private MemberInterface memberClient;
    private static final Logger LOGGER = Logger.getLogger("package membership.client");
    
    public void initStage(Parent root) {
        LOGGER.info("Initializing MembershipPlanClient window");   
        Scene scene = new Scene(root);
        //Establishes the scenary
        stage.setScene(scene);
        //Set window title
        stage.setTitle("ClientMembershipPlan");
        //Set resizability
        stage.setResizable(false);
        //Set the Event handlers
        stage.setOnShowing(this::handlerWindowShowing);
    }
    
     
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Iniciando AdminMembershipPlanController::handlerWindowShowing");
        btnSearch.setDisable(true);
        cbParameter.getItems().addAll("Name", "Price", "Duration");
    }
    
}
