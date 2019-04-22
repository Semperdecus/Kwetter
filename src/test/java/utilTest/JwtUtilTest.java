/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilTest;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import models.Account;
import models.Tweet;
import org.junit.Test;
import static org.junit.Assert.*;
import org.primefaces.json.JSONObject;
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

    @Test
    public void validateJwt_False() throws IOException {
        // arrange
        String jws = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0IiwiaWF0IjoxNTU1MzMxNzIyLCJzdWIiOiJ7XCJpZFwiOjQsXCJlbWFpbFwiOlwiYmFzLmRlLnpvdEBnbWFpbC5jb21cIixcInVzZXJuYW1lXCI6XCJ1c2VyXCIsXCJhY2NvdW50UGFzc3dvcmRcIjpcIlhvaEltTm9vQkhGUjBPVnZqY1lwSjNOZ1BRMXFxNzNXS2hIdmNoMFZRdGc9XCIsXCJsb2NhdGlvblwiOm51bGwsXCJ3ZWJzaXRlXCI6bnVsbCxcInBpY3R1cmVcIjpudWxsLFwiYmlvXCI6bnVsbCxcInR3ZWV0c1wiOlt7XCJpZFwiOjcsXCJtZXNzYWdlXCI6XCJvZmZlbnNpdmUgdHdlZXQhISFcIixcImRhdGVcIjoxNTU1MzMxNjE3NjE5fSx7XCJpZFwiOjgsXCJtZXNzYWdlXCI6XCJvZmZlbnNpdmUgdHdlZXQyISEhXCIsXCJkYXRlXCI6MTU1NTMzMTYxNzYxOX0se1wiaWRcIjo5LFwibWVzc2FnZVwiOlwib2ZmZW5zaXZlIHR3ZWV0MyEhIVwiLFwiZGF0ZVwiOjE1NTUzMzE2MTc2MTl9XSxcInBhc3N3b3JkXCI6XCJYb2hJbU5vb0JIRlIwT1Z2amNZcEozTmdQUTFxcTczV0toSHZjaDBWUXRnPVwifSIsImlzcyI6InVzZXIiLCJleHAiOjE1NTU5MzY1MjJ9.HYqNPRibvh5za1v3uohRyjIEA9phMJMopoEx5enQ-44";

        // act
        boolean result = true;
        try {
            result = jwtUtil.validateJwt(jws);
        } catch (ExpiredJwtException e) {
            result = false;
        }

        // assert
        assertFalse(result);
    }

    @Test
    public void validateJwtBearer_False() throws IOException {
        // arrange
        String jws = "bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0IiwiaWF0IjoxNTU1MzMxNzIyLCJzdWIiOiJ7XCJpZFwiOjQsXCJlbWFpbFwiOlwiYmFzLmRlLnpvdEBnbWFpbC5jb21cIixcInVzZXJuYW1lXCI6XCJ1c2VyXCIsXCJhY2NvdW50UGFzc3dvcmRcIjpcIlhvaEltTm9vQkhGUjBPVnZqY1lwSjNOZ1BRMXFxNzNXS2hIdmNoMFZRdGc9XCIsXCJsb2NhdGlvblwiOm51bGwsXCJ3ZWJzaXRlXCI6bnVsbCxcInBpY3R1cmVcIjpudWxsLFwiYmlvXCI6bnVsbCxcInR3ZWV0c1wiOlt7XCJpZFwiOjcsXCJtZXNzYWdlXCI6XCJvZmZlbnNpdmUgdHdlZXQhISFcIixcImRhdGVcIjoxNTU1MzMxNjE3NjE5fSx7XCJpZFwiOjgsXCJtZXNzYWdlXCI6XCJvZmZlbnNpdmUgdHdlZXQyISEhXCIsXCJkYXRlXCI6MTU1NTMzMTYxNzYxOX0se1wiaWRcIjo5LFwibWVzc2FnZVwiOlwib2ZmZW5zaXZlIHR3ZWV0MyEhIVwiLFwiZGF0ZVwiOjE1NTUzMzE2MTc2MTl9XSxcInBhc3N3b3JkXCI6XCJYb2hJbU5vb0JIRlIwT1Z2amNZcEozTmdQUTFxcTczV0toSHZjaDBWUXRnPVwifSIsImlzcyI6InVzZXIiLCJleHAiOjE1NTU5MzY1MjJ9.HYqNPRibvh5za1v3uohRyjIEA9phMJMopoEx5enQ-44";

        // act
        boolean result = true;
        try {
            result = jwtUtil.validateJwt(jws);
        } catch (ExpiredJwtException e) {
            result = false;
        }
        // assert
        assertFalse(result);
    }

    @Test
    public void validateJwtMalformed_False() throws IOException {
        // arrange
        String jws = "eJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0IiwiaWF0IjoxNTU1MzMxNzIyLCJzdWIiOiJ7XCJpZFwiOjQsXCJlbWFpbFwiOlwiYmFzLmRlLnpvdEBnbWFpbC5jb21cIixcInVzZXJuYW1lXCI6XCJ1c2VyXCIsXCJhY2NvdW50UGFzc3dvcmRcIjpcIlhvaEltTm9vQkhGUjBPVnZqY1lwSjNOZ1BRMXFxNzNXS2hIdmNoMFZRdGc9XCIsXCJsb2NhdGlvblwiOm51bGwsXCJ3ZWJzaXRlXCI6bnVsbCxcInBpY3R1cmVcIjpudWxsLFwiYmlvXCI6bnVsbCxcInR3ZWV0c1wiOlt7XCJpZFwiOjcsXCJtZXNzYWdlXCI6XCJvZmZlbnNpdmUgdHdlZXQhISFcIixcImRhdGVcIjoxNTU1MzMxNjE3NjE5fSx7XCJpZFwiOjgsXCJtZXNzYWdlXCI6XCJvZmZlbnNpdmUgdHdlZXQyISEhXCIsXCJkYXRlXCI6MTU1NTMzMTYxNzYxOX0se1wiaWRcIjo5LFwibWVzc2FnZVwiOlwib2ZmZW5zaXZlIHR3ZWV0MyEhIVwiLFwiZGF0ZVwiOjE1NTUzMzE2MTc2MTl9XSxcInBhc3N3b3JkXCI6XCJYb2hJbU5vb0JIRlIwT1Z2amNZcEozTmdQUTFxcTczV0toSHZjaDBWUXRnPVwifSIsImlzcyI6InVzZXIiLCJleHAiOjE1NTU5MzY1MjJ9.HYqNPRibvh5za1v3uohRyjIEA9phMJMopoEx5enQ-44";

        // act
        boolean result = true;

        try {
            result = jwtUtil.validateJwt(jws);
        } catch (MalformedJwtException e) {
            result = false;
        }

        // assert
        assertFalse(result);
    }

    @Test
    public void validateJwt_True() throws IOException {
        // arrange
        String id = "1";
        String issuer = "user";
        Account account = new Account("bas.de.zot@gmail.com", "user", "password");
        String json = jwtUtil.makeAccountJwtToken(id, issuer, account);
        JSONObject jsonObject = new JSONObject(json);
        String jws = jsonObject.getString("token");
        boolean result = false;

        // act
        result = jwtUtil.validateJwt(jws);

        // assert
        assertTrue(result);
    }
    
    @Test
    public void validateJwtBearer_True() throws IOException {
        // arrange
        String id = "1";
        String issuer = "user";
        Account account = new Account("bas.de.zot@gmail.com", "user", "password");
        String json = jwtUtil.makeAccountJwtToken(id, issuer, account);
        JSONObject jsonObject = new JSONObject(json);
        String jws = "bearer " + jsonObject.getString("token");
        boolean result = false;

        // act
        result = jwtUtil.validateJwt(jws);

        // assert
        assertTrue(result);
    }
}
