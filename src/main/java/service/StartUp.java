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
 *
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
            accountService.create(new Account(Role.USER, "user@mail.com", "user", "password"));
            accountService.create(new Account(Role.ADMIN, "user@admin.com", "admin", "password"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
