/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleniumTests.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import seleniumTests.PageObjects.AdminDashboard;
import seleniumTests.PageObjects.ProfilePage;

/**
 *
 * @author teren
 */
public class LoginPage extends PageObject {

    @FindBy(id = "j_idt8:username")
    private WebElement username;

    @FindBy(id = "j_idt8:password")
    private WebElement password;

    @FindBy(id = "j_idt8:login")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isInitialized() {
        return username.isDisplayed();
    }

    public void enterUsername(String username) {
        this.username.clear();
        this.username.sendKeys(username);
    }

    public void enterPassword(String password) {
        this.password.clear();
        this.password.sendKeys(password);
    }

    public ProfilePage loginUser() {
        loginButton.click();
        return new ProfilePage(driver);
    }

    public AdminDashboard loginAdmin() {
        loginButton.click();
        return new AdminDashboard(driver);
    }
}
