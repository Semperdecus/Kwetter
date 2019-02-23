/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoTests;

import dao.facade.AccountDaoJPA;
import dao.facade.TweetDaoJPA;
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
    private EntityTransaction transaction;
    private AccountDaoJPA dao;

    public AccountDaoJPATest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
	entityManager = entityManagerFactory.createEntityManager();
	transaction = entityManager.getTransaction();

	dao = new AccountDaoJPA(entityManager);
    }
    
    @After
    public void tearDown() {
    }

    /*
    Case 1: Don't allow empty username
    */
    @Test
    public void usernameTest1() {
        // TODO - Don't allow empty username
        transaction.begin();
        Account acc = dao.create(new Account(Role.USER, "user@mail.nl", "username", "password"));
        transaction.commit();
        assertEquals(acc.getUsername(), "username");
    }
    
    /*
    Case 2: Don't allow strings longer than 20 characters
    */
    //@Test
    public void usernameTest2() throws Exception {
        transaction.begin();
        //Account account = dao.create(new Account(Role.USER, "user@mail.nl", "123456789012345678901", "password"));
        //assertNull(account);
        transaction.commit();
    }
    
    /*
    Case 3: correct username
    */
    //@Test
    public void usernameTest3() throws Exception {
        // TODO - Don't allow empty username
        transaction.begin();
        //Account account = dao.create(new Account(Role.USER, "user@mail.nl", "username", "password"));
        //assertEquals(account, dao.findByUsername(account.getUsername()));
        transaction.commit();
    }
    
    /*
    Case 4: don't allow emails without proper format
    */
    //@Test
    public void emailTest1() throws Exception {
        transaction.begin();
        //Account account = dao.create(new Account(Role.USER, "mail", "username", "password"));
        //assertNull(account);
        transaction.commit();
    }
}
