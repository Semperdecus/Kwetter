/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domainTests;

import models.Account;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author teren
 */
public class AccountTest {

    @Test
    public void setPassword_shouldBeEncrypted() {
        // arrange
        String password = "password";
        Account instance = new Account();
        
        // act
        instance.setPassword(password);
        
        // assert
        assertNotEquals(instance.getAccountPassword(), password);
    }
    
    @Test
    public void setBio_whenLengthGreaterThan160_shouldNotEqual() {
        // arrange
        String longBio = "This string is 161 characters ...................................................................................................................................";
        Account instance = new Account();
        
        // act
        instance.setBio(longBio);

        // assert
        assertNotEquals(longBio, instance.getBio());
    }
}
