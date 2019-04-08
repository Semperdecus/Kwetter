/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleniumTest.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author teren
 */
public class ProfilePage extends PageObject {

    @FindBy(tagName = "h5")
    private WebElement header;

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    public String confirmationHeader() {
        return header.getText();
    }

    public boolean isInitialized() {
        return header.isDisplayed();
    }

}
