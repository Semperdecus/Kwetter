/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleniumTests;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumTests.PageObjects.AngularLoginPageObject;

/**
 *
 * @author teren
 */
public class AngularLoginTest extends ChromeDriverSetup {

    @Test
    public void loginPageHasTextLogin() throws InterruptedException {
        // arrange
        AngularLoginPageObject loginPage = new AngularLoginPageObject(driver);

        // act
        driver.get("http://localhost:4200/login");

        Thread.sleep(500);
        
        // assert
        assertEquals(loginPage.h1(), "Login");
    }

    @Test
    public void invalidUsername_ShouldShowInvalidLabel() {
        // arrange
        final String username = "";
        final String password = "password";
        AngularLoginPageObject loginPage = new AngularLoginPageObject(driver);

        // act
        driver.get("http://localhost:4200/login");

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);

        // assert
        Assert.assertTrue(loginPage.usernameLabelHasClass("mat-empty"));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AngularLoginTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void validLogin_ShouldRedirectTimeline() {
        // arrange
        final String username = "user";
        final String password = "password";
        AngularLoginPageObject loginPage = new AngularLoginPageObject(driver);

        // act
        driver.get("http://localhost:4200/login");

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);

        loginPage.loginButtonClick();

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                WebElement user = d.findElement(By.tagName("h5"));
                
                // assert
                assertThat(user.getText().toUpperCase(), containsString(username.toUpperCase()));
                return user.getText().toUpperCase().contains(username.toUpperCase());
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AngularLoginTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
