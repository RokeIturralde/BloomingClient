/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package changePassword;

import businessLogic.user.FactoryUser;
import businessLogic.user.UserInterface;
import encrypt.Cryptology;
import exceptions.ClientErrorException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import logic.objects.User;

/**
 *
 * @author minyb
 */
public class ChangePasswordController {
    @FXML
    PasswordField pfCurrent;
    @FXML
    PasswordField pfNew;
    @FXML
    PasswordField pfConfirm;
    @FXML
    Button btnChange;
    private Stage stage;
    private static final Logger LOGGER = Logger.getLogger("package changePassword");
    private UserInterface uInterface;
    User user = new User();
    
    public void initStage(Parent root) {
        LOGGER.info("Initializing ChangePassword window");
        //Creates an scene
        Scene scene = new Scene(root);
        //Establishes the scenary
        stage.setScene(scene);
        //Set window title
        stage.setTitle("ChangePassword");
        //Set resizability
        stage.setResizable(false);
        //Set window as modal
        //stage.initModality(Modality.APPLICATION_MODAL);
        //Set the Event handlers
        stage.setOnShowing(this::handlerWindowShowing);
        //Listeners
        pfCurrent.textProperty().addListener(this::textPropertyChange);
        pfNew.textProperty().addListener(this::textPropertyChange);
        pfConfirm.textProperty().addListener(this::textPropertyChange);
        uInterface = FactoryUser.get();
        stage.show();
    }
    
    private void textPropertyChange(ObservableValue observable,
            String oldValue,
            String newValue) {
        
        //Check for password length
        if(pfCurrent.getText().trim().length() > 25){
            pfCurrent.setText(pfCurrent.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the name is 25 characters", ButtonType.OK).showAndWait();
        }
        
        if(pfNew.getText().trim().length() > 25){
            pfNew.setText(pfNew.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the name is 25 characters", ButtonType.OK).showAndWait();
        }
        
        if(pfConfirm.getText().trim().length() > 25){
            pfConfirm.setText(pfConfirm.getText().substring(0, 25));
            new Alert(Alert.AlertType.ERROR, "The maximum length for the name is 25 characters", ButtonType.OK).showAndWait();
        }
        
        //Check for spaces
        if(pfCurrent.getText().contains(" ")){
            pfCurrent.setText(pfCurrent.getText().substring(0, pfCurrent.getText().length()-1));
           new Alert(Alert.AlertType.ERROR, "Password fields cannot contain spaces", ButtonType.OK).showAndWait();
        }
        
        if(pfNew.getText().contains(" ")){
            pfNew.setText(pfNew.getText().substring(0, pfNew.getText().length()-1));
           new Alert(Alert.AlertType.ERROR, "Password fields cannot contain spaces", ButtonType.OK).showAndWait();
        }
        
        if(pfConfirm.getText().contains(" ")){
            pfConfirm.setText(pfConfirm.getText().substring(0, pfConfirm.getText().length()-1));
           new Alert(Alert.AlertType.ERROR, "Password fields cannot contain spaces", ButtonType.OK).showAndWait();
        }
        
        //Enable and disable button
        if(pfCurrent.getText().trim().isEmpty()
                || pfNew.getText().trim().isEmpty()
                || pfConfirm.getText().trim().isEmpty()){
            btnChange.setDisable(true);
        }
        else{
            btnChange.setDisable(false);
        }
        
        if(pfNew.getText().equals(pfConfirm.getText()) && pfNew.getText().length()>0){
            btnChange.setDisable(false);
        }
        else{
            btnChange.setDisable(true);
            }
    }
    
    @FXML
    public void handleChangeButtonAction (ActionEvent event){
        byte[] pass = pfNew.getText().getBytes();
        Cryptology crypto = new Cryptology();
        pass = crypto.encrypt(pass);
        String newPass = Cryptology.hexadecimal(pass);
        
        
        try {
            user = uInterface.findUserByLogin("u2");
            user.setPassword(newPass);
            uInterface.editUser(user);
            
        } catch (ClientErrorException ex) {
            Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void handlerWindowShowing(WindowEvent event) {
        btnChange.setDisable(true);
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
