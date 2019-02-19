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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
