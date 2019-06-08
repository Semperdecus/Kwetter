/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleniumTests.PageObjects;

import seleniumTests.PageObjects.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author teren
 */
public class AdminDashboard extends PageObject {

    @FindBy(tagName = "h5")
    private WebElement header;

    public AdminDashboard(WebDriver driver) {
        super(driver);
    }

    public String confirmationHeader() {
        return header.getText();
    }

    public boolean isInitialized() {
        return header.isDisplayed();
    }

}
