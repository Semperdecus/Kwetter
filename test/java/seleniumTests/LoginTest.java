/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleniumTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import seleniumTests.PageObjects.AdminDashboard;
import seleniumTests.PageObjects.LoginPage;
import seleniumTests.PageObjects.ProfilePage;
/**
 *
 * @author teren
 */
public class LoginTest extends HTMLDriverSetup {

    //@Test
    public void login_ExistingUser_RedirectToUserProfile(){
        driver.get("http://localhost:8080/Kwetter/");

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isInitialized());

        loginPage.enterUsername("User");
        loginPage.enterPassword("password");

        ProfilePage profilePage = loginPage.loginUser();
        assertTrue(profilePage.isInitialized());

        assertEquals("Your role is insufficient to access this dashboard.", profilePage.confirmationHeader());
    }
    
    //@Test
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
