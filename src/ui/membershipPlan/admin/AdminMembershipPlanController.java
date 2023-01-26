/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.membershipPlan.admin;

import businessLogic.membership.MembershipPlanFactory;
import businessLogic.membership.MembershipPlanInterface;
import java.util.List;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.core.GenericType;
import logic.objects.MembershipPlan;
import logic.objects.User;

/**
 *
 * @author minyb
 */
public class AdminMembershipPlanController {
    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtaDescription;
    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtAlbumLimit;
    @FXML
    private TextField txtDuration;
    @FXML
    private CheckBox cbShareable;
    @FXML
    private TextField txtPrice;
    @FXML
    private ComboBox cboxParameter;
    @FXML
    private Button btnSearch;
    @FXML
    private Button  btnDelete;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnPrint;
    @FXML
    private TableView tbPlans;
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
    @FXML
    private Button btnMembership;
    @FXML
    private Button btnUsers;
    @FXML
    private Button btnLogo;
    
    private ObservableList<MembershipPlan> membershipPlanData;
    private Stage stage;
    private MembershipPlanInterface membershipClient;
    private static final Logger LOGGER = Logger.getLogger("package membership.admin");
    
    public void initStage(Parent root) {
        LOGGER.info("Initializing SignIn window");
        //Creates an scene
        Scene scene = new Scene(root);
        //Establishes the scenary
        stage.setScene(scene);
        //Set window title
        stage.setTitle("AdminMembershipPlan");
        //Set resizability
        stage.setResizable(false);
        //Set the Event handlers
        stage.setOnShowing(this::handlerWindowShowing);
        //Listeners
        txtName.textProperty().addListener(this::textChanged);
        txtaDescription.textProperty().addListener(this::textChanged);
        txtAlbumLimit.textProperty().addListener(this::textChanged);
        txtDuration.textProperty().addListener(this::textChanged);
        txtPrice.textProperty().addListener(this::textChanged);
        tbPlans.setEditable(false);
        //Values of the columns
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        cLimit.setCellValueFactory(new PropertyValueFactory<>("albumLimit"));
        cDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        cShareable.setCellValueFactory(new PropertyValueFactory<>("shareable"));
        cUsers.setCellValueFactory(new PropertyValueFactory<>("members"));
        cPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
                
        try{
            membershipClient = MembershipPlanFactory.getModel();
            membershipPlanData = FXCollections.observableArrayList(membershipClient.findAll_XML(new GenericType<List<MembershipPlan>>() {
            }));
            tbPlans.setItems(membershipPlanData);
            tbPlans.refresh();
        } catch(Exception e){
            e.printStackTrace();
        }
        stage.show();   
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
    
    
    
    public void textChanged(ObservableValue observable, 
            String oldValue,
            String newValue){
        
        if(txtName.getText().trim().length() > 25){
            txtName.setText(txtName.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum lenght for the login is 25 characters.", ButtonType.OK).showAndWait();
            btnCreate.setDisable(true);
        }
    }
    
    public void handlerWindowShowing(WindowEvent event){
        LOGGER.info("Iniciando AdminMembershipPlanController::handlerWindowShowing");
        btnCreate.setDisable(true);
        btnModify.setDisable(true);
        btnSearch.setDisable(true);
        btnDelete.setDisable(true);        
    }
}
