/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serviceTests;

import dao.IAccountDao;
import exceptions.AccountException;
import models.Account;
import models.Role;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import service.AccountService;

/**
 *
 * @author teren
 */
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private IAccountDao accountDao;

    @Before
    public void setUp() {
        initMocks(this);
    }

    //@Test
    public void ServiceTest() {
        assertNotNull(accountService);
    }

    //@Test
    public void shouldCreateAccount() throws Exception {
        // arrange
        Account account = new Account("email@mail.com", "username", "password");
        account.setId(1l);
        account.setRole(new Role("User"));

        // act
        accountService.create(account);

        // assert
        verify(accountDao, atLeastOnce()).create(account);
    }

    //@Test
    public void givenEmailExists_shouldNotCreateAccount() throws Exception {
        // arrange
        Account account = new Account("email@mail.com", "username", "password");
        account.setId(1l);
        account.setRole(new Role("User"));
        given(accountDao.findByEmail("email@mail.com"))
                .willReturn(account);

        // act
        Account result = accountService.create(account);

        // assert
        assertNotEquals(result, account);
    }

    //@Test(expected = AccountException.class)
    public void whenEmptyEmail_shouldThrowAccountException() throws Exception {
        // arrange
        Account account = new Account("", "username", "password");
        account.setId(1l);
        account.setRole(new Role("User"));

        // act
        accountService.create(account);
    }

    //@Test(expected = AccountException.class)
    public void whenEmptyUsername_shouldThrowAccountException() throws Exception {
        // arrange
        Account account = new Account("email@email.com", "", "password");
        account.setId(1l);
        account.setRole(new Role("User"));

        // act
        accountService.create(account);
    }

    //@Test
    public void shouldUpdateUsername() throws Exception {
        // arrange
        Account user = new Account("user113@mail.com", "user113", "password");
        user.setId(1l);
        String newUsername = "username2";

        // act
        accountService.updateUsername(newUsername, user);

        // assert
        verify(accountDao, atLeastOnce()).update(user);
    }

    //@Test
    public void givenEmptyUsername_shouldNotUpdateUsername() throws Exception {
        Account user = new Account("user113@mail.com", "user113", "password");
        user.setId(1l);
        String newUsername = "";

        accountService.updateUsername(newUsername, user);
        verify(accountDao, never()).update(user);
    }

    //@Test
    public void whenUsernameExist_shouldNotUpdateUsername() throws Exception {
        // arrange
        Account user = new Account("user113@mail.com", "user113", "password");
        user.setId(1l);
        String newUsername = "username2";
        when(accountDao.findByUsername(newUsername)).thenReturn(user);

        // act
        accountService.updateUsername(newUsername, user);
        
        // assert
        verify(accountDao, never()).update(user);
    }

    // Older tests
    
    //@Test
    public void addFollowingTest1() throws Exception {
        Account user1 = new Account("user500@mail.com", "user500", "password");
        Account user2 = new Account("user510@mail.com", "user510", "password");
        user1.setId(1l);
        user2.setId(2l);

        when(accountDao.findById(1l)).thenReturn(user1);
        when(accountDao.findById(2l)).thenReturn(user2);

        accountService.addFollowing(user1.getId(), user2.getId());

        verify(accountDao, atLeastOnce()).update(user1);
        verify(accountDao, atLeastOnce()).update(user2);
    }

    /*
    Case 7 - add yourself to following
     */
    //@Test
    public void addFollowingTest2() throws Exception {
        Account user1 = new Account("user500@mail.com", "user500", "password");
        user1.setId(1l);

        when(accountDao.findById(1l)).thenReturn(user1);

        accountService.addFollowing(user1.getId(), user1.getId());
        verify(accountDao, never()).update(user1);
    }

    /*
    Case 8 - add non existing account to following
     */
    //@Test
    public void addFollowingTest3() throws Exception {
        Account user1 = new Account("user500@mail.com", "user500", "password");
        user1.setId(1l);

        when(accountDao.findById(1l)).thenReturn(user1);
        when(accountDao.findById(3l)).thenReturn(null);

        accountService.addFollowing(user1.getId(), 3l);
        verify(accountDao, never()).update(user1);
    }

    /*
    Case 9 - remove follower
     */
    //@Test
    public void removeFollowingTest1() throws Exception {
        Account user1 = new Account("user500@mail.com", "user500", "password");
        Account user2 = new Account("user510@mail.com", "user510", "password");
        user1.setId(1l);
        user2.setId(2l);

        when(accountDao.findById(1l)).thenReturn(user1);
        when(accountDao.findById(2l)).thenReturn(user2);

        accountService.removeFollowing(user1.getId(), user2.getId());
        verify(accountDao, atLeastOnce()).update(user1);
    }

    /*
    Case 10 - Remove yourself as follower
     */
    //@Test
    public void removeFollowingTest2() throws Exception {
        Account user1 = new Account("user500@mail.com", "user500", "password");
        user1.setId(1l);

        when(accountDao.findById(1l)).thenReturn(user1);

        accountService.removeFollowing(user1.getId(), user1.getId());
        verify(accountDao, never()).update(user1);
    }

    /*
    Case 11 - Remove non existing user from following
     */
    //@Test
    public void removeFollowingTest3() throws Exception {
        Account user1 = new Account("user500@mail.com", "user500", "password");
        user1.setId(1l);

        when(accountDao.findById(1l)).thenReturn(user1);
        when(accountDao.findById(3l)).thenReturn(null);

        accountService.removeFollowing(user1.getId(), 3l);
    }
}
