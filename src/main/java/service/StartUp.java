/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import models.Account;
import models.Role;

/**
 * Init for database and tests
 * @author teren
 */
@Singleton
@Startup
public class StartUp {

    @Inject
    private AccountService accountService;

    @Inject
    private TweetService tweetService;

    @PostConstruct
    public void initData() {
        try {
            Account adminAccount = new Account("adminStartUp@admin.com", "adminStartUp", "password");
            adminAccount.setRole(new Role("ADMIN"));
            accountService.create(new Account("userStartUp@mail.com", "userStartUp", "password"));
            accountService.create(adminAccount);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
