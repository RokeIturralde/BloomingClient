/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package changePassword;

import businessLogic.user.FactoryUser;
import encrypt.Cryptology;
import exceptions.ClientErrorException;
import exceptions.LoginDoesNotExistException;
import exceptions.NotThePasswordException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import objects.User;


/**
 *
 * @author Eneko
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
    private User user = new User();
    
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
        stage.show();
    }
    
    private void textPropertyChange(ObservableValue observable,
            String oldValue,
            String newValue) {
        
        if(pfCurrent.getText().trim().isEmpty()
                || pfNew.getText().trim().isEmpty()
                || pfConfirm.getText().trim().isEmpty()){
            btnChange.setDisable(true);
        }
        else{
            btnChange.setDisable(false);
        }
    }
    
    @FXML
    public void handleChangeButtonAction (ActionEvent event){
        String pass = pfNew.getText();
        Cryptology crypto = new Cryptology();
        byte[] passByte = crypto.encrypt(pass);
        String newPass = Cryptology.hexadecimal(passByte);
        
        try {
            user = FactoryUser.get().signIn(user.getLogin(), pfNew.getText());
            if(user != null){
               user.setPassword(newPass);
               FactoryUser.get().editUser(user); 
            }
            
            
        } catch (ClientErrorException ex) {
            Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LoginDoesNotExistException ex) {
            Logger.getLogger(ChangePasswordController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotThePasswordException ex) {
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
