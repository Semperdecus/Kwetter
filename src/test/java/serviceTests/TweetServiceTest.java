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
import models.Role;
import models.Tweet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

    @Test
    public void ServiceTest() {
        assertNotNull(tweetService);
    }

    // @RolesAllowed({"USER","ADMINISTRATOR", "MODERATOR"})
    // public Tweet create(Tweet tweet) throws Exception;
    @Test
    public void createTest() throws Exception {
        // Case 1 - Existing User
        Account user = new Account(Role.USER, "email@mail.com", "username", "password");
        when(accountDao.findById(1l)).thenReturn(user);
        user.setId(1l);
        tweetService.create(new Tweet("message", user));
        verify(accountDao, atLeastOnce()).update(user);
    }

    // @RolesAllowed({"USER","ADMINISTRATOR", "MODERATOR"})
    // public Tweet create(Tweet tweet) throws Exception;
    @Test
    public void createTest2() throws Exception {
        // Case 2 - Non-existing User
        Account user = new Account(Role.USER, "email@mail.com", "username", "password");
        when(accountDao.findById(1l)).thenReturn(null);
        user.setId(1l);
        tweetService.create(new Tweet("message", user));
        verify(accountDao, never()).update(user);
    }

    // @RolesAllowed({"USER","ADMINISTRATOR", "MODERATOR"})
    // public void delete(long id, long userId) throws Exception;
    @Test
    public void deleteTest() throws Exception {
        // Case 1 - Existing User
        Account user = new Account(Role.USER, "email@mail.com", "username", "password");
        Tweet tweet = new Tweet("message", user);
        user.setId(1l);
        tweet.setId(1l);

        when(tweetDao.findById(tweet.getId())).thenReturn(tweet);
        tweetService.delete(1l, user);
        verify(tweetDao, atLeastOnce()).delete(tweet, tweet.getAccount());
    }

    // @RolesAllowed({"USER","ADMINISTRATOR", "MODERATOR"})
    // public void delete(long id, long userId) throws Exception;
    @Test(expected = AccountException.class)
    public void deleteTest2() throws Exception {
        // Case 2 - Non-existing User
        Account user = new Account(Role.USER, "email@mail.com", "username", "password");
        Tweet tweet = new Tweet("message", user);
        user.setId(1l);
        tweet.setId(1l);

        when(tweetDao.findById(tweet.getId())).thenReturn(null);
        tweetService.delete(1l, user);
        verify(tweetDao, never()).delete(tweet, tweet.getAccount());
    }
}
