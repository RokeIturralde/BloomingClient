/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.content;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeoutException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import main.mainContent;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

/**
 *
 * @author Roke
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContentWindowControllerTest extends ApplicationTest {

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
    private RadioButton rbCustomImage;
    @FXML
    private TextField txtName;
    @FXML
    private DatePicker uploadDate;
    @FXML
    private TextField txtLocation;
    @FXML
    private Button btnFileChooser;
    @FXML
    private Button btnAddContent;
    @FXML
    private Button btnModifyContent;
    @FXML
    private Button btnDeleteContent;

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(mainContent.class);
    }

    @Test
    public void test1_initStage() {
        verifyThat("#btnAddContent", isDisabled());
        verifyThat("#btnModifyContent", isDisabled());
        verifyThat("#btnFind", isDisabled());
        verifyThat("#rbCustomImage", isEnabled());
        verifyThat("#btnClear", isEnabled());
        verifyThat("#txtName", isDisabled());
        verifyThat("#uploadDate", isDisabled());
        verifyThat("#btnFileChooser", isDisabled());
        verifyThat("#txtLocation", isDisabled());
        verifyThat("#txtValue", isDisabled());
    }

    /*  @Test
    public void test2_addContent() throws AWTException {
        clickOn("#rbCustomImage");
        verifyThat("#txtName", isEnabled());
        verifyThat("#uploadDate", isEnabled());
        verifyThat("#txtLocation", isEnabled());
        verifyThat("#btnFileChooser", isEnabled());
        clickOn("#txtName");
        write("Image Test");
        clickOn("#txtLocation");
        write("Erandio");
        clickOn("#btnFileChooser");
        // Use the FxRobot class to type the name of the file
        typeString(new FxRobot(), "testImage.jpg");

        // Use the Robot class to press the Enter key
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        verifyThat("#btnAddContent", isEnabled());
        verifyThat("#btnModifyContent", isEnabled());
        verifyThat("#btnDeleteContent", isEnabled());
        clickOn("#btnModifyContent");
        push(KeyCode.ENTER);
    }*/
    @Test
    public void test3_modifyContent() {
        Node row = lookup(".table-row-cell").nth(3).query();
        clickOn(row);
        verifyThat("#txtName", isEnabled());
        clickOn("#txtName");
        eraseText(15);
        write("ChangedName");
        verifyThat("#uploadDate", isEnabled());
        verifyThat("#txtLocation", isEnabled());
        clickOn("#txtLocation");
        eraseText(15);
        write("ChangedLocation");
        clickOn("#btnModifyContent");
        push(KeyCode.ENTER);
    }

    /*   private void typeString(FxRobot robot, String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            typeChar(robot, c);
        }
    }

    private void typeChar(FxRobot robot, char c) {
        robot.press(KeyCode.getKeyCode(Character.toString(c))).release(KeyCode.getKeyCode(Character.toString(c)));
    }*/
    @Test
    public void test4_CheckModification() {
        txtName = lookup("#txtName").query();
        txtLocation = lookup("#txtLocation").query();
        uploadDate = lookup("#uploadDate").query();
        Node row = lookup(".table-row-cell").nth(3).query();
        clickOn(row);
        assertEquals(txtName.getText(), "ChangedName");
        assertEquals(txtLocation.getText(), "ChangedLocation");
    }

    @Test
    public void test5_deleteContent() {
        tableCustomImage = lookup("#tableCustomImage").query();
        int numObjects = tableCustomImage.getItems().size();
        Node row = lookup(".table-row-cell").nth(3).query();
        clickOn(row);
        verifyThat("#txtName", isEnabled());
        verifyThat("#uploadDate", isEnabled());
        verifyThat("#txtLocation", isEnabled());
        verifyThat("#btnFileChooser", isEnabled());
        verifyThat("#btnAddContent", isEnabled());
        verifyThat("#btnModifyContent", isEnabled());
        verifyThat("#btnDeleteContent", isEnabled());
        clickOn("#btnDeleteContent");
        push(KeyCode.ENTER);
        int numObjectEliminated = tableCustomImage.getItems().size();
        assertNotEquals(numObjects, numObjectEliminated);
    }

}
