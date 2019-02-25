/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domainTests;

import java.util.List;
import models.Account;
import models.Role;
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
public class AccountTest {
    private Account testAccount;
    private String longString;
    private String shortString;
    private String sentence;

    public AccountTest() {
        testAccount = new Account(Role.USER, "user@gmail.com", "username", "password");
        longString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        shortString = "Hello";
        sentence = "hello world!";
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
     * Test of getId method, of class Account.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Account instance = new Account();
        Long expResult = null;
        Long result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(expResult, testAccount.getId());
    }

    /**
     * Test of setId method, of class Account.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = null;
        Account instance = new Account();
        Long expResult = null;
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(expResult, instance.getId());
        testAccount.setId(1L);
        expResult = 1L;
        assertEquals(expResult, testAccount.getId());
    }

    /**
     * Test of getRole method, of class Account.
     */
    @Test(expected=NullPointerException.class)
    public void testGetRole() {
        System.out.println("getRole");
        
        Role expResult = Role.USER;
        assertEquals(expResult, testAccount.getRole());
        
        Account instance = new Account();
        assertNull(instance.getRole());
    }

    /**
     * Test of setRole method, of class Account.
     */
    @Test
    public void testSetRole() {
        System.out.println("setRole");
        String role = "";
        Account instance = new Account();
        
        instance.setRole(role);
        Role expResult = Role.USER;
        assertEquals(expResult, instance.getRole());
        
        expResult = Role.USER;
        assertEquals(expResult, testAccount.getRole());
       
        testAccount.setRole(Role.ADMIN.toString());
        expResult = Role.ADMIN;
        assertEquals(expResult, testAccount.getRole());        
    }

    /**
     * Test of getEmail method, of class Account.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        Account instance = new Account();
        String expResult = "";
        String result = instance.getEmail();
        assertNull(result);

        expResult = "user@gmail.com";
        assertEquals(expResult, testAccount.getEmail());
    }

    /**
     * Test of setEmail method, of class Account.
     */
    @Test
    public void testSetEmail() {
        System.out.println("setEmail");
        String email = "";
        Account instance = new Account();
        instance.setEmail(email);
        String expResult = "";
        assertEquals(expResult, instance.getEmail());
        
        testAccount.setEmail("admin@gmail.com");
        expResult = "admin@gmail.com";
        assertEquals(expResult, testAccount.getEmail());
        
        instance.setEmail(longString);
        assertEquals(longString, instance.getEmail());
        
        instance.setEmail(shortString);
        assertEquals(shortString, instance.getEmail());
        
        instance.setEmail(sentence);
        assertEquals(sentence, instance.getEmail());
    }

    /**
     * Test of getUsername method, of class Account.
     */
    @Test
    public void testGetUsername() {
        System.out.println("getUsername");
        Account instance = new Account();
        String expResult = "";
        String result = instance.getUsername();
        assertNull(result);

        expResult = "username";
        assertEquals(expResult, testAccount.getUsername());
    }

    /**
     * Test of setUsername method, of class Account.
     */
    @Test
    public void testSetUsername() {
        System.out.println("setUsername");
        String username = "";
        Account instance = new Account();
        String expResult = "";
        instance.setUsername(username);

        assertEquals(expResult, instance.getUsername());
        
        testAccount.setUsername(longString);
        assertEquals(longString, testAccount.getUsername());
        
        testAccount.setUsername(shortString);
        assertEquals(shortString, testAccount.getUsername());
        
        testAccount.setUsername(sentence);
        assertEquals(sentence, testAccount.getUsername());
    }

    /**
     * Test of getPassword method, of class Account.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        Account instance = new Account();
        String expResult = "";
        String result = instance.getPassword();
        assertNull(result);

        expResult = "password";
        assertEquals(expResult, testAccount.getPassword());
    }

    /**
     * Test of setPassword method, of class Account.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "";
        Account instance = new Account();
        String expResult = "";
        instance.setPassword(password);

        assertEquals(expResult, instance.getPassword());
        
        testAccount.setPassword(longString);
        assertEquals(longString, testAccount.getPassword());
        
        testAccount.setPassword(shortString);
        assertEquals(shortString, testAccount.getPassword());
        
        testAccount.setPassword(sentence);
        assertEquals(sentence, testAccount.getPassword());    }

    /**
     * Test of getLocation method, of class Account.
     */
    @Test
    public void testGetLocation() {
        System.out.println("getLocation");
        Account instance = new Account();
        String expResult = "";
        String result = instance.getLocation();
        assertNull(result);

        assertNull(expResult, testAccount.getLocation());
    }

    /**
     * Test of setLocation method, of class Account.
     */
    @Test
    public void testSetLocation() {
        System.out.println("setLocation");
        String location = "";
        Account instance = new Account();
        instance.setLocation(location);
        assertEquals(location, instance.getLocation());
        
        testAccount.setLocation(longString);
        assertEquals(longString, testAccount.getLocation());
    }

    /**
     * Test of getWebsite method, of class Account.
     */
    @Test
    public void testGetWebsite() {
        System.out.println("getWebsite");
        Account instance = new Account();
        String expResult = "";
        String result = instance.getWebsite();
        assertNull(expResult, result);

        assertNull(testAccount.getWebsite());
    }

    /**
     * Test of setWebsiteUrl method, of class Account.
     */
    @Test
    public void testSetWebsiteUrl() {
        System.out.println("setWebsiteUrl");
        String websiteUrl = "";
        Account instance = new Account();
        instance.setWebsiteUrl(websiteUrl);
        
        testAccount.setWebsiteUrl(longString);
        assertEquals(longString, testAccount.getWebsite());
        
        testAccount.setWebsiteUrl(shortString);
        assertEquals(shortString, testAccount.getWebsite());
        
        testAccount.setWebsiteUrl(sentence);
        assertEquals(sentence, testAccount.getWebsite());
    }

    /**
     * Test of getBio method, of class Account.
     */
    @Test
    public void testGetBio() {
        System.out.println("getBio");
        Account instance = new Account();
        String expResult = "";
        String result = instance.getBio();
        assertNull(expResult, result);

        assertNull(testAccount.getBio());
    }

    /**
     * Test of setBio method, of class Account.
     */
    @Test
    public void testSetBio() {
        System.out.println("setBio");
        String bio = "";
        Account instance = new Account();
        instance.setBio(bio);


        
        testAccount.setBio(longString);
        assertEquals(longString, testAccount.getBio());
        
        testAccount.setBio(shortString);
        assertEquals(shortString, testAccount.getBio());
        
        testAccount.setBio(sentence);
        assertEquals(sentence, testAccount.getBio());    
    }

}
