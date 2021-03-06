/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleniumTests;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *
 * @author teren
 */
public class ChromeDriverSetup {

    protected static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:/Terence/Documents/NetBeansProjects/Kwetter/src/test/resources/chromedriver.bat");

        driver = new ChromeDriver();

        //driver.close();
    }

    @After
    public void cleanUp() {
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public static void tearDown() {
        driver.close();
    }
}
