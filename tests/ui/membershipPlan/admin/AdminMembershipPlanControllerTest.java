/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.membershipPlan.admin;

import java.util.concurrent.TimeoutException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import main.mainMembershipPlan;
import objects.MembershipPlan;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author minyb
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminMembershipPlanControllerTest extends ApplicationTest {
    
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
    private TableView tbPlans = lookup("#tbPlans").query();
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
    
    @BeforeClass
    public static void setUpClass() throws TimeoutException{
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(mainMembershipPlan.class);
    }
    
    @Test
    public void test1_initStage(){
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnCreate", isDisabled());
        verifyThat("#btnSearch", isDisabled());
        verifyThat("#btnShowUsers", isDisabled());
    }
    
    @Test
    public void test2_createPlan(){
        int cont = tbPlans.getItems().size();
        clickOn("#txtName");
        write("Test plan");
        clickOn("#txtaDescription");
        write("Test plan description");
        clickOn("#txtAlbumLimit");
        write("5");
        clickOn("#txtDuration");
        write("9");
        clickOn("#txtPrice");
        write("6.4");
        verifyThat("#btnCreate", isEnabled());
        clickOn("#btnCreate");
        assertEquals(tbPlans.getItems().size(), cont+1);
    }
    
    @Test
    public void test3_modify(){
        Node row = lookup(".table-row-cell").nth(tbPlans.getItems().size()-1).query();
        clickOn(row);
        MembershipPlan plan = (MembershipPlan) tbPlans.getSelectionModel().getSelectedItem();
        String name = plan.getName();
        clickOn("#txtName");
        write(" cambiado");
        clickOn("#btnModify");
        row = lookup(".table-row-cell").nth(tbPlans.getItems().size()-1).query();
        clickOn(row);
        MembershipPlan planModified = (MembershipPlan) tbPlans.getSelectionModel().getSelectedItem();
        String newName = planModified.getName();
        assertNotEquals(name, newName);
    }
    
    @Test
    public void test4_delete(){
        Integer cont = tbPlans.getItems().size();
        Node row = lookup(".table-row-cell").nth(tbPlans.getItems().size()-1).query();
        clickOn(row);
        clickOn("#btnDelete");
        assertEquals(tbPlans.getItems().size(), cont-1);
    }
    
    @Test
    public void test5_onlyInteger(){
        Node row = lookup(".table-row-cell").nth(tbPlans.getItems().size()-1).query();
        clickOn(row);
        clickOn("#txtDuration");
        write("a");
        clickOn("#btnModify");
        verifyThat("Duration field has to be a digit", isVisible());
    }
    
    
    
    public AdminMembershipPlanControllerTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testSomeMethod() {
    }
    
}
