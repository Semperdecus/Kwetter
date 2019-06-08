/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceTests;

import dao.IAccountDao;
import dao.ITweetDao;
import exceptions.AccountException;
import models.Account;
import models.Tweet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.*;
import service.TweetService;

/**
 *
 * @author teren
 */
public class TweetServiceTest {

    @InjectMocks
    private TweetService tweetService;

    @Mock
    private ITweetDao tweetDao;

    @Mock
    private IAccountDao accountDao;

    @Before
    public void setUp() {
        initMocks(this);
    }

    //@Test
    public void ServiceTest() {
        assertNotNull(tweetService);
    }

    //@Test
    public void whenUserExists_shouldCreateTweet() throws Exception {
        // arrange
        Account user = new Account("email@mail.com", "username", "password");
        when(accountDao.findById(1l)).thenReturn(user);
        user.setId(1l);
        
        // act
        tweetService.create(new Tweet("message", user));
        
        // assert
        verify(accountDao, atLeastOnce()).update(user);
    }

    //@Test
    public void whenUserDoesNotExist_shouldNeverCreateTweet() throws Exception {
        // arrange
        Account user = new Account("email@mail.com", "username", "password");
        when(accountDao.findById(1l)).thenReturn(null);
        user.setId(1l);
        
        // act
        tweetService.create(new Tweet("message", user));
        
        // assert
        verify(accountDao, never()).update(user);
    }

    //@Test
    public void whenOwnTweet_shouldDeleteTweet() throws Exception {
        // arrange
        Account user = new Account("email@mail.com", "username", "password");
        Tweet tweet = new Tweet("message", user);
        user.setId(1l);
        tweet.setId(1l);
        when(tweetDao.findById(tweet.getId())).thenReturn(tweet);
        
        // act
        tweetService.deleteOwnTweet(1l);

        // assert
        verify(tweetDao, atLeastOnce()).delete(tweet);
    }
}
