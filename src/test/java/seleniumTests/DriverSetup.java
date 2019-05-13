/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleniumTests;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.session.FirefoxFilter;

/**
 *
 * @author teren
 */
public class DriverSetup {

    protected static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        //System.setProperty("webdriver.gecko.driver", "D:/Terence/Documents/NetBeansProjects/Kwetter/src/test/resources/geckodriver.exe");
        //System.setProperty("webdriver.chrome.driver", "D:/Terence/Documents/NetBeansProjects/Kwetter/src/test/resources/chromedriver.bat");

        driver = new HtmlUnitDriver(BrowserVersion.CHROME ,true);
//        FirefoxOptions options = new FirefoxOptions();
//        options.setLogLevel(Level.ALL);
//        driver = new FirefoxDriver(options);

        driver.close();
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
