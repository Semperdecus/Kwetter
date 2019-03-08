/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoTests;

import dao.facade.AccountDaoJPA;
import dao.facade.TweetDaoJPA;
import exceptions.AccountException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
public class AccountDaoJPATest {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("kwetterTestPU");
    private EntityManager entityManager;
    private AccountDaoJPA dao;

    private Account account;
    private Account accountBart;
    private Account accountEmma;

    private boolean isInitialized = false;

    public AccountDaoJPATest() {
    }

    @Before
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();

        if (!isInitialized) {
            dao = new AccountDaoJPA(entityManager);

            accountBart = new Account(Role.USER, "bart@mail.nl", "bart", "password");
            accountEmma = new Account(Role.USER, "emma@mail.nl", "emma", "password");
            account = new Account(Role.USER, "user@mail.nl", "user", "password");

            accountEmma.addFollowing(accountBart);
            accountEmma.addFollowing(account);
            accountBart.addFollowing(account);

            account.addFollower(accountEmma);
            account.addFollower(accountBart);
            accountBart.addFollower(accountEmma);

            entityManager.persist(account);
            entityManager.persist(accountEmma);
            entityManager.persist(accountBart);

            isInitialized = true;
        }
    }

    @After
    public void tearDown() {
    }

    /*
    Case 1: Don't allow empty username
     */
    @Test(expected=AccountException.class)
    public void usernameTest1() throws Exception {
        // TODO - Don't allow empty username
        Account accountUsername = dao.create(new Account(Role.USER, "user@mail.nl", "", "password"));
        assertNull(accountUsername);
    }

    /*
    Case 2: Don't allow strings longer than 20 characters
     */
    @Test(expected=AccountException.class)
    public void usernameTest2() throws Exception {
        Account accountUsername = dao.create(new Account(Role.USER, "user@mail.nl", "123456789012345678901", "password"));
        assertNull(accountUsername);
    }

    /*
    Case 3: correct username
     */
    @Test
    public void usernameTest3() throws Exception {
        Account accountUsername = dao.create(new Account(Role.USER, "user@mail.com", "username", "password"));
        assertEquals(accountUsername, dao.findByUsername(accountUsername.getUsername()));
    }

    /*
    Case 4: don't allow emails without proper format
     */
    @Test(expected=AccountException.class)
    public void emailTest1() throws Exception {
        Account accountEmail = dao.create(new Account(Role.USER, "mail", "username", "password"));
        assertNull(accountEmail);
    }

    /*
    Case 5: don't allow empty email
     */
    @Test(expected=AccountException.class)
    public void emailTest2() throws Exception {
        Account accountEmail = dao.create(new Account(Role.USER, "", "username", "password"));
        assertNull(accountEmail);
    }

    /*
    Case 6: proper email
     */
    //@Test
    public void emailTest3() throws Exception {
        Account accountEmail = dao.create(new Account(Role.USER, "user@mail.nl", "username", "password"));
        assertEquals(accountEmail, dao.findByEmail("user@mail.nl"));
    }
    
    /*
    Case 7: new following and get following/followers
     */
    @Test
    public void followingTest1() throws Exception {
        // Test via list
        System.out.println(accountEmma.getFollowers());
        
        assertEquals("bart", dao.findByUsername(accountEmma.getFollowing().get(0).getUsername()).getUsername());
        assertEquals("user@mail.nl", dao.findByUsername(accountEmma.getFollowing().get(dao.findByUsername("user").getId().intValue()).getUsername()).getEmail());
        
        assertEquals(2, dao.findByUsername(accountEmma.getUsername()).getFollowing().size());
        assertEquals(1, dao.findByUsername(accountBart.getUsername()).getFollowers().size());
    }
}
