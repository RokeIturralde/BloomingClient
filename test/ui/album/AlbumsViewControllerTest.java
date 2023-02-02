/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.album;

import app.App;
import java.awt.TextField;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import javafx.scene.control.PasswordField;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Usuario
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AlbumsViewControllerTest extends ApplicationTest {

    private static final String VACIO = "";

    /**
     * Starts application to be tested.
     *
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @Override
    public void start(Stage stage) throws Exception {
        new App().start(stage);
        clickOn("#txtLogin");
        write("u1");
        clickOn("#cpPassword");
        write("u1");
        clickOn("#btnSignIn");
    }

    /**
     * Test of initStage method, of class AlbumsViewController.
     */
    @Test
    public void testInitStage() {
        Assert.assertEquals(VACIO, "#txtValue");
        Assert.assertEquals(VACIO, "#txtAlbumName");
        Assert.assertEquals("u1", "#txtAlbumCreator");
        Assert.assertEquals(VACIO, "#txtAddUser");
        Assert.assertEquals(VACIO, "#taUsers");
        verifyThat("#btnFind", isDisabled());
        verifyThat("#btnAdd", isDisabled());
        verifyThat("#btnCreateAlbum", isDisabled());
        verifyThat("#btnModifyAlbum", isDisabled());
        verifyThat("#btnDeleteAlbum", isDisabled());
        verifyThat("#taUsers", isDisabled());

    }

    /**
     * Test of setStage method, of class AlbumsViewController.
     */
    @Test
    public void test1_testSetStage() {
        Stage stage = null;
        AlbumsViewController instanceOf = new AlbumsViewController();
        instanceOf.setStage(stage);
        verifyThat("#albumPane", isVisible());
    }

    /**
     * Test of handleCreateAlbumButtonAction method, of class
     * AlbumsViewController.
     */
    @Test
    public void test2_handleCreateAlbumButtonAction() {
        clickOn("#txtAlbumName");
        write("albumTest1");
        clickOn("#btnCreateAlbum");
        verifyThat("Album added successfully", isVisible());
        clickOn("OK");
    }

    /**
     * Test of handleModifyAlbumButtonAction method, of class
     * AlbumsViewController.
     */
    @Test
    public void test3_handleModifyAlbumButtonAction() {
        clickOn("#tbMyAlbums");
        clickOn("#txtAlbumName");
        write("2");
        clickOn("#btnModifyAlbum");
        verifyThat("Album Modified", isVisible());
        clickOn("OK");
    }

    /**
     * Test of handleDeleteAlbumButtonAction method, of class
     * AlbumsViewController.
     */
    @Test
    public void test3_handleDeleteAlbumButtonAction() {
        clickOn("#txtAlbumName");
        write("albumTest2");
        clickOn("#btnDeleteAlbum");
        verifyThat("Album deleted", isVisible());
        clickOn("OK");
    }

    /**
     * Test of handleDeleteAlbumButtonAction method, of class
     * AlbumsViewController.
     */
    @Test
    public void test4_handleClearShearchButtonAction() {
        clickOn("#btnClearSearch");
        Assert.assertEquals(VACIO, "#txtValue");
        verifyThat("#btnFind", isDisabled());
    }

    /**
     * Test of handleDeleteAlbumButtonAction method, of class
     * AlbumsViewController.
     */
    @Test
    public void test5_handleClearInfoButtonAction() {
        clickOn("#btnClearInfo");
        Assert.assertEquals(VACIO, "#txtAlbumName");
        Assert.assertEquals("u1", "#txtAlbumCreator");
        Assert.assertEquals(VACIO, "#txtAddUser");
        Assert.assertEquals(VACIO, "#taUsers");
        verifyThat("#btnAdd", isDisabled());
        verifyThat("#btnCreateAlbum", isDisabled());
        verifyThat("#btnModifyAlbum", isDisabled());
        verifyThat("#btnDeleteAlbum", isDisabled());
        verifyThat("#taUsers", isDisabled());
    }

    /**
     * Test of handleDeleteAlbumButtonAction method, of class
     * AlbumsViewController.
     */
    @Test
    public void test6_searchTextPropertyChange() {
        clickOn("#txtValue");
        write("a");
        Assert.assertNotEquals("#btnFind", isDisabled());
        write("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        verifyThat("The maximum lenght for the login is 25 characters.", isVisible());
        clickOn("OK");
    }
    /**
     * Test of handleDeleteAlbumButtonAction method, of class
     * AlbumsViewController.
     */
    @Test
    public void test7_handleLogoButtonAction() {
        clickOn("#btnLogo");
        verifyThat("#albumPane", isVisible());
    }
    /**
     * Test of handleDeleteAlbumButtonAction method, of class
     * AlbumsViewController.
     */
    @Test
    public void test8_handleLogoButtonAction() {
        clickOn("#btnAlbums");
        verifyThat("#albumPane", isVisible());
    }
    
    /**
     * Test of handleDeleteAlbumButtonAction method, of class
     * AlbumsViewController.
     */
    @Test
    public void test9_handleHelpButtonAction() {
        clickOn("#btnHelp");
        verifyThat("#HelpPane", isVisible());
    }

}
