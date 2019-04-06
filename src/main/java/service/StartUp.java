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
import models.Tweet;

/**
 * Init for database and tests
 *
 * @author teren
 */
@Singleton
@Startup
public class StartUp {

    @Inject
    private AccountService accountService;
    @Inject
    private RoleService roleService;

    @Inject
    private TweetService tweetService;

    @PostConstruct
    public void initData() {
        try {
            Account testUser = new Account("user@mail.com", "user", "password");
            Account testAdmin = new Account("admin@mail.com", "admin", "password");
            Role roleAdmin = new Role("Admin");
            Role roleUser = new Role("User");
//            roleAdmin.addAccount(testAdmin);
//            roleUser.addAccount(testUser);


            roleService.create(roleAdmin);
            roleService.create(roleUser);
            testUser.setRole(roleService.getRoleByName("User"));
            testAdmin.setRole(roleService.getRoleByName("Admin"));
            
            accountService.create(testUser);
            accountService.create(testAdmin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
