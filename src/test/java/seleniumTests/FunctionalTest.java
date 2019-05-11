/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seleniumTests;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 *
 * @author teren
 */
public class FunctionalTest {

    protected static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "D:/Terence/Documents/NetBeansProjects/Kwetter/src/test/resources/geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "D:/Terence/Documents/NetBeansProjects/Kwetter/src/test/resources/chromedriver.exe");

//        driver = new HtmlUnitDriver(BrowserVersion.CHROME ,true);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //driver = new FirefoxDriver();
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
