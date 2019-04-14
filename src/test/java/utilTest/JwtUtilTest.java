/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilTest;

import gherkin.deps.com.google.gson.JsonObject;
import java.io.IOException;
import models.Account;
import models.Tweet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.JwtUtil;

/**
 *
 * @author teren
 */
public class JwtUtilTest {

    private JwtUtil jwtUtil;
    
    public JwtUtilTest() {
        jwtUtil = new JwtUtil();
    }

    @Test
    public void getProperties_NotNull() throws IOException {
        // arrange
        String apiKey;

        // act
        apiKey = jwtUtil.apiKey();

        // assert
        assertNotNull(apiKey);
    }

    @Test
    public void getJSONAccount() {
        // arrange
        Account testAccount = new Account("bas.de.zot@gmail.com", "user", "password");
        Account followerAccount = new Account("admin@mail.com", "admin", "password");
        Account followingAccount = new Account("mod@mail.com", "mod", "password");
        Tweet userTweet = new Tweet("test tweet", testAccount);

        testAccount.setBio("bio");
        testAccount.setLocation("test location");
        testAccount.addTweet(userTweet);
        testAccount.addFollower(followerAccount);
        testAccount.addFollowing(followingAccount);
        testAccount.setPicture("testpicture");

        // act
        String result = jwtUtil.createJSONAccount(testAccount);
        System.out.println(result);
        
        // assert
        assertTrue(result.contains("accountPassword"));
        assertTrue(result.contains("id"));
        assertTrue(result.contains("email"));
        assertTrue(result.contains("testpicture"));
        assertTrue(result.contains("bas.de.zot@gmail.com"));
    }
    
    @Test
    public void createAccountJWT() throws IOException {
        // arrange
        String id = "1";
        String issuer = "user";
        Account account = new Account("bas.de.zot@gmail.com", "user", "password");
        String result = null;
        
        // act
        result = jwtUtil.makeAccountJwtToken(id, issuer, account);
        System.out.println(result);
        
        // assert
        assertNotNull(result);
    }
}
