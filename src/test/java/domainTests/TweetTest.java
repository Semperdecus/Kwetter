/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domainTests;

import java.util.Date;
import models.Account;
import models.Role;
import models.Tweet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author teren
 */
public class TweetTest {
    private Account testAccount;
    
    public TweetTest() {
        testAccount = new Account("user@gmail.com", "username", "password");
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Tweet.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Tweet instance = new Tweet();
        Long expResult = null;
        Long result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Tweet.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = 1L;
        Tweet instance = new Tweet();
        instance.setId(id);
        assertEquals(id, instance.getId());
    }

    /**
     * Test of getMessage method, of class Tweet.
     */
    @Test
    public void testGetMessage() {
        System.out.println("getMessage");
        Tweet instance = new Tweet();
        String expResult = null;
        String result = instance.getMessage();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMessage method, of class Tweet.
     */
    @Test
    public void testSetMessage() {
        System.out.println("setMessage");
        String message = "test message*!\"@+#|_ABC";
        Tweet instance = new Tweet();
        
        // Test setMessage
        assertEquals(null, instance.getMessage());
        instance.setMessage(message);
        assertEquals(message, instance.getMessage());
        
        // Test setMessage in constructor
        Tweet instanceWithMessage = new Tweet(message, testAccount);
        assertEquals(message, instanceWithMessage.getMessage());
    }

    /**
     * Test of getDate method, of class Tweet.
     */
    @Test
    public void testGetDate() {
        System.out.println("getDate");
        Tweet instance = new Tweet();
        Date expResult = null;
        Date result = instance.getDate();
        assertEquals(expResult, result);
    }

    /**
     * Test of setDate method, of class Tweet.
     */
    @Test
    public void testSetDate() {
        System.out.println("setDate");
        Date date = new Date(2000, 10, 10);
        Tweet instance = new Tweet();
        instance.setDate(date);
        assertEquals(date, instance.getDate());
    }

    /**
     * Test of getAccount method, of class Tweet.
     */
    @Test
    public void testGetAccount() {
        System.out.println("getAccount");
        Tweet instance = new Tweet();
        Account expResult = null;
        Account result = instance.getAccount();
        assertEquals(expResult, result);
        
        Tweet instanceWithAccount = new Tweet("message", testAccount);
        assertEquals(testAccount, instanceWithAccount.getAccount());
    }

    /**
     * Test of setAccount method, of class Tweet.
     */
    @Test
    public void testSetAccount() {
        System.out.println("setAccount");
        Tweet instance = new Tweet();
        instance.setAccount(testAccount);
        assertEquals(testAccount, instance.getAccount());
    }
    
}
