/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleniumTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import seleniumTest.PageObjects.AdminDashboard;
import seleniumTest.PageObjects.LoginPage;
import seleniumTest.PageObjects.ProfilePage;
/**
 *
 * @author teren
 */
public class LoginTest extends FunctionalTest {

    @Test
    public void login_ExistingUser_RedirectToUserProfile(){
        driver.get("http://localhost:8080/Kwetter/");

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.enterUsername("User");
        loginPage.enterPassword("password");

        ProfilePage profilePage = loginPage.loginUser();
        assertTrue(profilePage.isInitialized());

        assertEquals("Frontend for user will be made in Angular soon!", profilePage.confirmationHeader());
    }
    
    @Test
    public void login_ExistingAdmin_RedirectToAdminDashboard(){
        driver.get("http://localhost:8080/Kwetter/");

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.enterUsername("admin");
        loginPage.enterPassword("password");

        AdminDashboard adminPage = loginPage.loginAdmin();
        assertTrue(adminPage.isInitialized());

        assertEquals("List of all accounts", adminPage.confirmationHeader());
    }
    
    
}
