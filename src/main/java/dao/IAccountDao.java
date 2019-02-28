/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import exceptions.AccountException;
import java.util.List;
import models.Account;

/**
 *
 * @author teren
 */
public interface IAccountDao {

    /**
     *
     * @param id
     * @return
     */
    Account findById(long id);
    
    /**
     *
     * @param email
     * @return
     */
    Account findByEmail(String email);
    
    /**
     *
     * @param username
     * @return
     */
    Account findByUsername(String username);
    
    /**
     *
     * @return
     */
    List<Account> findAll();
    
    /**
     *
     * @param entity
     * @return
     */
    Account create(Account entity) throws AccountException;
    
    /**
     *
     * @param entity
     * @return
     */
    Account update(Account entity);
    
    /**
     *
     * @param entity
     */
    void delete(Account entity);

}
