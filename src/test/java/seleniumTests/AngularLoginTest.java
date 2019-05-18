/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleniumTests;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author teren
 */
public class AngularLoginTest {

    private WebDriver driver;

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/Terence/Documents/NetBeansProjects/Kwetter/src/test/resources/chromedriver.bat");
    }

    @Before
    public void setUp() throws Exception {
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        driver.close();
    }

    private boolean tagContainsClassName(WebElement element, String classname) {
        String classes = element.getAttribute("class");
        for (String c : classes.split(" ")) {
            if (c.equals(classname)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void loginPageHasTextLogin() {
        driver.get("http://localhost:4200/login");

        assertThat(driver.findElement(By.tagName("h1")).getText(), containsString("Login"));
    }

    @Test
    public void validLogin_ShouldRedirectTimeline() {
        // arrange
        final String username = "User";
        final String password = "password";

        driver.get("http://localhost:4200/login");

        WebElement usernameInputField = driver.findElement(By.id("mat-input-0"));

        usernameInputField.sendKeys(username);

        WebElement passwordInputField = driver.findElement(By.id("mat-input-1"));
        passwordInputField.sendKeys(password);

        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(AngularLoginTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        WebElement submit = driver.findElement(By.tagName("button"));

        submit.click();

        // Check the sign up succeeded by checking that the randomized
        // email appears in the website's header bar.
        (new WebDriverWait(driver, 100)).until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                WebElement user = d.findElement(By.tagName("h5"));
                assertThat(user.getText().toUpperCase(), containsString(username.toUpperCase()));
                return user.getText().toUpperCase().contains(username.toUpperCase());
            }
        });
    }

    @Test
    public void invalidUsername_ShouldShowInvalidLabel() {
        // arrange
        final String username = "";
        final String password = "password";

        driver.get("http://localhost:4200/login");

        WebElement usernameInputField = driver.findElement(By.id("mat-input-0"));
        usernameInputField.sendKeys(username);

        WebElement passwordInputField = driver.findElement(By.id("mat-input-1"));
        passwordInputField.sendKeys(password);

        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(AngularLoginTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        WebElement usernameLabel = driver.findElement(By.id("mat-form-field-label-1"));

        Assert.assertTrue(tagContainsClassName(usernameLabel, "mat-empty"));
    }
}
