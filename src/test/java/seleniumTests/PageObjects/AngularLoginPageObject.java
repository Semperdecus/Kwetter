/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleniumTests.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author teren
 */
public class AngularLoginPageObject extends PageObject {

    @FindBy(tagName = "h1")
    private WebElement h1;

    @FindBy(id = "mat-input-0")
    private WebElement usernameInputField;

    @FindBy(id = "mat-input-1")
    private WebElement passwordInputField;

    @FindBy(tagName = "button")
    private WebElement button;

    @FindBy(id = "mat-form-field-label-1")
    private WebElement usernameLabel;

    public AngularLoginPageObject(WebDriver driver) {
        super(driver);
    }

    public String h1() {
        return h1.getText();
    }

    public void enterUsername(String username) {
        usernameInputField.clear();
        usernameInputField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordInputField.clear();
        passwordInputField.sendKeys(password);
    }

    public void loginButtonClick() {
        button.click();
    }

    public boolean usernameLabelHasClass(String cssClassName) {
        return tagContainsClassName(usernameLabel, cssClassName);
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
}
