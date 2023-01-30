/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.membershipPlan.admin;

import businessLogic.membership.MembershipPlanFactory;
import businessLogic.membership.MembershipPlanInterface;
import businessLogic.user.FactoryMember;
import businessLogic.user.MemberInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import logic.objects.Member;
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
    private Button btnDelete;
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
    @FXML
    private Button btnShowUsers;

    private ObservableList<MembershipPlan> membershipPlanData;
    private Stage stage;
    private MembershipPlanInterface membershipClient;
    private MemberInterface memberClient;
    private static final Logger LOGGER = Logger.getLogger("package membership.admin");

    public void initStage(Parent root) {
        LOGGER.info("Initializing MembershipPlanAdminController window");
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
        txtName.textProperty().addListener(this::textPropertyChange);
        txtaDescription.textProperty().addListener(this::textPropertyChange);
        txtAlbumLimit.textProperty().addListener(this::textPropertyChange);
        txtDuration.textProperty().addListener(this::textPropertyChange);
        txtPrice.textProperty().addListener(this::textPropertyChange);
        txtSearch.textProperty().addListener(this::textPropertyChange);
        tbPlans.setEditable(false);
        tbPlans.getSelectionModel().selectedItemProperty().addListener(this::handlePlansTableSelectionChanged);
        //Values of the columns
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        cLimit.setCellValueFactory(new PropertyValueFactory<>("albumLimit"));
        cDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        cShareable.setCellValueFactory(new PropertyValueFactory<>("shareable"));
        cUsers.setCellValueFactory(new PropertyValueFactory<>("cont"));
        cPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        membershipClient = MembershipPlanFactory.getModel();
        memberClient = FactoryMember.get();
        refreshPlus();
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    
    private void handlePlansTableSelectionChanged(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        if (newValue != null) {
            MembershipPlan plan = (MembershipPlan) newValue;
            txtName.setText(plan.getName());
            txtaDescription.setText(plan.getDescription());
            txtPrice.setText(plan.getPrice() + "");
            txtAlbumLimit.setText(plan.getAlbumLimit() + "");
            txtDuration.setText(plan.getDuration() + "");
            cbShareable.setSelected(plan.getShareable());
            btnShowUsers.setDisable(false);
            if(plan.getMembers().isEmpty()){
                btnDelete.setDisable(false);
            }
            else{
                btnDelete.setDisable(true);
            }
        } else {
            txtName.setText("");
            txtaDescription.setText("");
            txtPrice.setText("");
            txtAlbumLimit.setText("");
            txtDuration.setText("");
            cbShareable.setSelected(false);
            btnShowUsers.setDisable(true);
            reset();
        }
    }

    public void refreshPlus() {
        try {
            membershipPlanData = FXCollections.observableArrayList(membershipClient.findAll_XML(new GenericType<List<MembershipPlan>>() {
            }));
            for (int i = 0; i < membershipPlanData.size(); i++) {
                membershipPlanData.get(i).setMembers(memberClient.findMembersByPlan(membershipPlanData.get(i).getId() + ""));
            }
            tbPlans.setItems(membershipPlanData);
            tbPlans.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void textPropertyChange(ObservableValue observable,
            String oldValue,
            String newValue) {

        if (txtName.getText().trim().length() > 25) {
            txtName.setText(txtName.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the name is 25 characters", ButtonType.OK).showAndWait();
        }

        if (txtaDescription.getText().trim().length() > 150) {
            txtaDescription.setText(txtaDescription.getText().substring(0, 150));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the description is 150 characters", ButtonType.OK).showAndWait();
        }

        if (txtAlbumLimit.getText().trim().length() > 6) {
            txtAlbumLimit.setText(txtAlbumLimit.getText().substring(0, 6));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the album limit is 6 characters", ButtonType.OK).showAndWait();
        }

        if (txtDuration.getText().trim().length() > 6) {
            txtDuration.setText(txtDuration.getText().substring(0, 6));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the plan's duration is 6 characters", ButtonType.OK).showAndWait();
        }

        if (txtPrice.getText().trim().length() > 6) {
            txtPrice.setText(txtPrice.getText().substring(0, 6));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the plan's price is 6 characters", ButtonType.OK).showAndWait();
        }

        //If text areas are empty disable the buttons
        if (txtName.getText().trim().isEmpty()
                || txtaDescription.getText().trim().isEmpty()
                || txtAlbumLimit.getText().trim().isEmpty()
                || txtDuration.getText().trim().isEmpty()
                || txtPrice.getText().trim().isEmpty()) {

            btnDelete.setDisable(true);
            btnModify.setDisable(true);
            btnCreate.setDisable(true);
        } //Else enable the buttons
        else {
            btnDelete.setDisable(false);
            btnModify.setDisable(false);
            btnCreate.setDisable(false);
        }

        if (txtSearch.getText().trim().isEmpty()) {
            btnSearch.setDisable(true);
        } else {
            btnSearch.setDisable(false);
        }

    }

    public void handlerWindowShowing(WindowEvent event) {
        LOGGER.info("Iniciando AdminMembershipPlanController::handlerWindowShowing");
        btnCreate.setDisable(true);
        btnModify.setDisable(true);
        btnSearch.setDisable(true);
        btnDelete.setDisable(true);
        btnShowUsers.setDisable(true);
        //txtSearch.setDisable(true);
        cbShareable.setSelected(false);
        cboxParameter.getItems().addAll("Name", "Price", "Duration");
    }
    
    private void fieldValidator(){
        for(int i = 0; i < txtDuration.getText().length();i++){
            if(!Character.isDigit(txtDuration.getText().charAt(i))){
                new Alert(Alert.AlertType.ERROR, "Gotta be a digit man", ButtonType.OK).showAndWait();
            }
        }
        
        for(int i = 0; i < txtAlbumLimit.getText().length();i++){
            if(!Character.isDigit(txtAlbumLimit.getText().charAt(i))){
                new Alert(Alert.AlertType.ERROR, "Gotta be a digit man", ButtonType.OK).showAndWait();
            }
        }
        
        String floatPattern = "^([0-9]*[.])?[0-9]+$";
        if (!Pattern.matches(floatPattern, txtPrice.getText())) {
                new Alert(Alert.AlertType.ERROR, "Floating", ButtonType.OK).showAndWait();
            }
    }
    
    @FXML
    public void handleShowUsersButtonAction(ActionEvent event){
        MembershipPlan plan = (MembershipPlan) tbPlans.getSelectionModel().getSelectedItem();
        ArrayList<Member> members = (ArrayList<Member>) plan.getMembers();
        String membersString = "";
        for (int i = 0; i < members.size(); i++){
            membersString = membersString + members.get(i).getLogin()+", ";
        }
        new Alert(Alert.AlertType.INFORMATION, membersString, ButtonType.OK).showAndWait();
        //new Alert(Alert.AlertType.ERROR, "The maximum length for the plan's price is 6 characters", ButtonType.OK).showAndWait();
    }
    
    @FXML
    public void handleSearchButtonAction(ActionEvent event) {
        //If name is selected
        if (cboxParameter.getValue().toString().equals("Name")) {
            try {                
                membershipPlanData = FXCollections.observableArrayList(membershipClient.findPlanByName_XML(new GenericType<List<MembershipPlan>>(){}, txtSearch.getText()));
                for (int i = 0; i < membershipPlanData.size(); i++) {
                    membershipPlanData.get(i).setMembers(memberClient.findMembersByPlan(membershipPlanData.get(i).getId() + ""));
                }
                tbPlans.setItems(membershipPlanData);
                tbPlans.refresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        //If duration is selected
        if (cboxParameter.getValue().toString().equals("Duration")) {
            try {                
                membershipPlanData = FXCollections.observableArrayList(membershipClient.findPlanByDuration_XML(new GenericType<List<MembershipPlan>>(){}, txtSearch.getText()));
                for (int i = 0; i < membershipPlanData.size(); i++) {
                    membershipPlanData.get(i).setMembers(memberClient.findMembersByPlan(membershipPlanData.get(i).getId() + ""));
                }
                tbPlans.setItems(membershipPlanData);
                tbPlans.refresh();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        //If price is selected
        if(cboxParameter.getValue().toString().equals("Price")){
           try {                
                membershipPlanData = FXCollections.observableArrayList(membershipClient.findPlanByPrice_XML(new GenericType<List<MembershipPlan>>(){}, txtSearch.getText()));
                for (int i = 0; i < membershipPlanData.size(); i++) {
                    membershipPlanData.get(i).setMembers(memberClient.findMembersByPlan(membershipPlanData.get(i).getId() + ""));
                }
                tbPlans.setItems(membershipPlanData);
                tbPlans.refresh();
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
    }

    
    @FXML
    public void handleDeleteButtonAction(ActionEvent event) {
        MembershipPlan plan = (MembershipPlan) tbPlans.getSelectionModel().getSelectedItem();
        membershipClient.remove(plan.getId() + "");
        reset();
    }

    @FXML
    public void handleModifyDeleteButtonAction(ActionEvent event) {
        fieldValidator();
        MembershipPlan plan = (MembershipPlan) tbPlans.getSelectionModel().getSelectedItem();
        plan.setName(txtName.getText());
        plan.setAlbumLimit(Integer.parseInt(txtAlbumLimit.getText()));
        plan.setPrice(Float.parseFloat(txtPrice.getText()));
        plan.setDescription(txtaDescription.getText());
        plan.setDuration(Integer.parseInt(txtDuration.getText()));
        plan.setShareable(cbShareable.isSelected());
        membershipClient.edit_XML(plan);
        tbPlans.setSelectionModel(null);
        txtName.requestFocus();
        reset();
    }

    @FXML
    public void handleCreateButtonAction(ActionEvent event) {
        fieldValidator();
        MembershipPlan plan = new MembershipPlan();
        plan.setName(txtName.getText());
        plan.setDuration(Integer.parseInt(txtDuration.getText()));
        plan.setAlbumLimit(Integer.parseInt(txtAlbumLimit.getText()));
        plan.setPrice(Float.parseFloat(txtPrice.getText()));
        plan.setShareable(cbShareable.isSelected());
        plan.setDescription(txtaDescription.getText());
        try {
            membershipClient.create_XML(plan);
            reset();
        } catch (ClientErrorException ex) {
            Logger.getLogger(AdminMembershipPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reset() {
        //Empty fields
        txtName.setText("");
        txtDuration.setText("");
        txtAlbumLimit.setText("");
        txtPrice.setText("");
        cbShareable.setSelected(false);
        txtaDescription.setText("");

        //Disable controll buttons
        btnCreate.setDisable(true);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);

        refreshPlus();
    }

    @FXML
    public void handleClearButtonAction(ActionEvent event) {
        refreshPlus();
        txtSearch.setText("");
        btnSearch.setDisable(true);
        cboxParameter.setValue("");
    }

    @FXML
    public void handlePrintButtonAction(ActionEvent event) {
    }

    @FXML
    public void handleMembershipButtonAction(ActionEvent event) {

    }

    @FXML
    public void handleUsersButtonAction(ActionEvent event) {
        
    }

    @FXML
    public void handleLogoButtonAction(ActionEvent event) {

    }
}