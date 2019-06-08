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

    @Test
    public void setMessage_shouldNotEqual_whenLengthGreaterThan140() {
        // arrange
        String longMessage = "This string is 141 characters ...............................................................................................................";
        Tweet instance = new Tweet();
        
        // act
        instance.setMessage(longMessage);
        
        // assert
        assertNotEquals(longMessage, instance.getMessage());
    }
    
}
