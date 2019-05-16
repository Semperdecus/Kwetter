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
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import models.Account;
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
    private EntityTransaction transaction;
    private TweetDaoJPA dao;

    private Account account;
    private Account accountBart;
    private Account accountEmma;
    private Account accountAdmin;

    private Tweet tweet;
    private Tweet tweetBart1;
    private Tweet tweetBart2;
    private Tweet tweetEmma;

    private boolean isInitialized = false;

    public TweetDaoJPATest() {

    }

    @Before
    public void setUp() {
        // Set up basic data for database to test DAO layer
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();

        if (!isInitialized) {
            transaction.begin();
            dao = new TweetDaoJPA(entityManager);

            accountBart = new Account("bart@mail.nl", "bart", "password");
            accountEmma = new Account("emma@mail.nl", "emma", "password");
            account = new Account("user@mail.nl", "user", "password");
            accountAdmin = new Account("admin@mail.nl", "admin", "password");
            tweet = new Tweet("Hello world!", account);
            tweetBart1 = new Tweet("Hello world bart 1!", accountBart);
            tweetBart2 = new Tweet("Hello world bart 2!", accountBart);
            tweetEmma = new Tweet("Hello world emma!", accountEmma);

            entityManager.persist(account);
            entityManager.persist(accountEmma);
            entityManager.persist(accountBart);
            entityManager.persist(accountAdmin);
            entityManager.persist(tweet);
            entityManager.persist(tweetBart1);
            entityManager.persist(tweetBart2);
            entityManager.persist(tweetEmma);

            transaction.commit();
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
        // Find tweet by id
        transaction.begin();
        Tweet tweetFound = dao.findById(tweet.getId());
        assertEquals(tweet, tweetFound);
        transaction.commit();
    }

    /*
    Case 6: Persist tweet and search for tweet by message
     */
    @Test
    public void findByMessageTest1() throws Exception {
        // Find by message
        transaction.begin();
        List<Tweet> tweetResults = dao.findByMessage(tweet.getMessage());
        assertEquals(1, tweetResults.size());
        transaction.commit();

    }

    /*
    Case 7: Persist tweet and search for tweet by username
     */
    @Test
    public void findByUsernameTest1() throws Exception {
        // Find by username
        transaction.begin();
        List<Tweet> tweetResults = dao.findByUsername(tweet.getAccount().getUsername());
        assertEquals(1, tweetResults.size());
        transaction.commit();

        // Case insensitive
        transaction.begin();
        List<Tweet> tweetResultsUppercase = dao.findByUsername(tweet.getAccount().getUsername().toUpperCase());
        assertEquals(1, tweetResultsUppercase.size());
        transaction.commit();

    }

    /*
    Case 8: Persist tweet and search for tweet by username
     */
    @Test
    public void findByUsernameTest2() throws Exception {
        // Find by username "bart" with 2 tweets
        transaction.begin();
        List<Tweet> tweetResultsBart = dao.findByUsername("bart");
        assertEquals(2, tweetResultsBart.size());
        transaction.commit();

        // Find by username "emma" with 1 tweet
        transaction.begin();
        List<Tweet> tweetResultsEmma = dao.findByUsername("Emma");
        assertEquals(1, tweetResultsEmma.size());
        transaction.commit();
    }

    /*
    Case 9: Delete tweet by id (also tests findAll)
     */
    @Test
    public void deleteByIdTest() throws Exception {
        transaction.begin();
        List<Tweet> tweetFound = dao.findAll();
        transaction.commit();
        assertEquals(4, tweetFound.size());

        // Delete tweet
        transaction.begin();
        dao.deleteById(tweet.getId());
        transaction.commit();

        // 3 Tweets found
        transaction.begin();
        List<Tweet> tweetNotFound = dao.findAll();
        transaction.commit();
        assertEquals(3, tweetNotFound.size());
    }

    /*
    Case 10: Test link between Tweet and Account tables
     */
    @Test
    public void linkTweetAccountTest() throws Exception {
        // Find by username "emma" with 1 tweet
        transaction.begin();
        List<Tweet> tweetResultsEmma = dao.findByUsername("emma");
        transaction.commit();

        assertEquals("emma", tweetResultsEmma.get(0).getAccount().getUsername());
    }

    /*
    Case 11: Delete tweet
     */
    @Test
    public void deleteTweetTest1() throws Exception {
        // Find by username "emma" with 1 tweet
        transaction.begin();
        dao.delete(tweetEmma);
        transaction.commit();

        assertEquals(0, accountEmma.getTweets().size());
    }

    /*
    Case 12: Delete tweet without adminaccount
     */
    //@Test
    public void deleteTweetTest2() throws Exception {
        // Create tweet from Account Emma
        Tweet deletableTweet = new Tweet("emma's Tweet!", accountEmma);
        dao.create(deletableTweet);

        // Delete tweet 
        dao.delete(deletableTweet);
    }
}
