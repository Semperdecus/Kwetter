/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoTests;

import dao.facade.TweetDaoJPA;
import exceptions.TweetException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import models.Account;
import models.Role;
import models.Tweet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author teren
 */
public class TweetDaoJPATest {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("kwetterTestPU");
    private EntityManager entityManager;
    private TweetDaoJPA dao;

    private Account account;
    private Account accountBart;
    private Account accountEmma;
    private Account accountAdmin;

    private boolean isInitialized = false;

    public TweetDaoJPATest() {

    }

    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();

        if (!isInitialized) {
            dao = new TweetDaoJPA(entityManager);

            accountBart = new Account(Role.USER, "bart@mail.nl", "bart", "password");
            accountEmma = new Account(Role.USER, "emma@mail.nl", "emma", "password");
            account = new Account(Role.USER, "user@mail.nl", "user", "password");
            accountAdmin = new Account(Role.ADMIN, "admin@mail.nl", "admin", "password");

            entityManager.persist(account);
            entityManager.persist(accountEmma);
            entityManager.persist(accountBart);
            entityManager.persist(accountAdmin);

            isInitialized = true;
        }
    }

    /*
    Case 1: Correct tweet
     */
    @Test
    public void createTest1() throws Exception {
        // Arrange
        Tweet tweet = new Tweet("Tweet message", account);

        // Act
        Tweet daoTweet = dao.create(tweet);

        // Assert 
        assertEquals("Tweet message", daoTweet.getMessage());
    }

    /*
    Case 2: Invalid Tweet (empty)
     */
    @Test(expected = TweetException.class)
    public void createTest2() throws Exception {
        Tweet tweetEmpty = dao.create(new Tweet("", account));
        assertNull(tweetEmpty);
    }

    /*
    Case 3: Correct tweet (140 characters)
     */
    @Test
    public void createTest3() throws Exception {
        String message140Char = "Hello World! Hello World! Hello World! Hello "
                + "World! Hello World! Hello World! Hello World! Hello World! "
                + "Hello World! Hello World! Hello Worl"; //140 characters
        Tweet tweet140Chars = dao.create(new Tweet(message140Char, account));
        assertNotNull(tweet140Chars);
    }

    /*
    Case 4: Too long tweet (141 characters)
     */
    @Test(expected = TweetException.class)
    public void createTest4() throws Exception {

        String message141Char = "Hello World! Hello World! Hello World! Hello "
                + "World! Hello World! Hello World! Hello World! Hello World! "
                + "Hello World! Hello World! Hello World"; //141 characters
        Tweet tweet141Chars = dao.create(new Tweet(message141Char, account));
        assertNull(tweet141Chars);
    }

    /*
    Case 5: Persist tweet and search for tweet by id
     */
    @Test
    public void findByIdTest() throws Exception {
        // Create Tweet and persist to database
        Tweet tweet = new Tweet("Hello world!", account);
        Tweet createdTweet = dao.create(tweet);

        // Find tweet by id
        Tweet tweetFound = dao.findById(createdTweet.getId());
        assertEquals(createdTweet, tweetFound);
    }

    /*
    Case 6: Persist tweet and search for tweet by message
     */
    @Test
    public void findByMessageTest1() throws Exception {
        // Create tweet
        Tweet tweet = new Tweet("Hello world!", account);
        Tweet createdTweet = dao.create(tweet);

        // Find by message
        List<Tweet> tweetResults = dao.findByMessage(createdTweet.getMessage());
        assertEquals(1, tweetResults.size());
    }

    /*
    Case 7: Persist tweet and search for tweet by username
     */
    @Test
    public void findByUsernameTest1() throws Exception {
        // Create tweet
        Tweet tweet = new Tweet("Hello world!", account);
        Tweet createdTweet = dao.create(tweet);

        // Find by username
        List<Tweet> tweetResults = dao.findByUsername(createdTweet.getAccount().getUsername());
        assertEquals(1, tweetResults.size());

        // Case insensitive
        List<Tweet> tweetResultsUppercase = dao.findByUsername(createdTweet.getAccount().getUsername().toUpperCase());
        assertEquals(1, tweetResultsUppercase.size());
    }

    /*
    Case 8: Persist tweet and search for tweet by username
     */
    @Test
    public void findByUsernameTest2() throws Exception {

        // Create 3 tweets
        dao.create(new Tweet("Hello world!", accountBart));
        dao.create(new Tweet("Hello world!", accountBart));
        dao.create(new Tweet("Hello world!", accountEmma));

        // Find by username "bart" with 2 tweets
        List<Tweet> tweetResultsBart = dao.findByUsername("bart");
        assertEquals(2, tweetResultsBart.size());

        // Find by username "emma" with 1 tweet
        List<Tweet> tweetResultsEmma = dao.findByUsername("Emma");
        assertEquals(1, tweetResultsEmma.size());
    }

    /*
    Case 9: Delete tweet by id (also tests findAll)
     */
    @Test
    public void deleteByIdTest() throws Exception {
        // Create tweet
        Tweet tweet = new Tweet("Hello world!", account);
        Tweet createdTweet = dao.create(tweet);

        // 1 Tweet found
        List<Tweet> tweetFound = dao.findAll();
        assertEquals(1, tweetFound.size());

        // Delete tweet
        dao.deleteById(tweet.getId(), accountAdmin);

        // 0 Tweets found
        List<Tweet> tweetNotFound = dao.findAll();
        assertEquals(0, tweetNotFound.size());
    }

    /*
    Case 10: Test link between Tweet and Account tables
     */
    @Test
    public void linkTweetAccountTest() throws Exception {
        // Create tweet from Account Emma (id 3)
        dao.create(new Tweet("emma's Tweet!", accountEmma));

        // Find by username "emma" with 1 tweet
        List<Tweet> tweetResultsEmma = dao.findByUsername("emma");
        assertEquals("emma", tweetResultsEmma.get(0).getAccount().getUsername());
    }

    /*
    Case 11: Delete tweet
     */
    @Test
    public void deleteTweetTest1() throws Exception {
        // Create tweet from Account Emma
        Tweet deletableTweet = new Tweet("emma's Tweet!", accountEmma);
        dao.create(deletableTweet);

        // Find by username "emma" with 1 tweet
        dao.delete(deletableTweet, accountAdmin);

        assertEquals(0, accountEmma.getTweets().size());
    }

    /*
    Case 12: Delete tweet without adminaccount
     */
    @Test(expected = TweetException.class)
    public void deleteTweetTest2() throws Exception {
        // Create tweet from Account Emma
        Tweet deletableTweet = new Tweet("emma's Tweet!", accountEmma);
        dao.create(deletableTweet);

        // Delete tweet 
        dao.delete(deletableTweet, account);
    }
}
