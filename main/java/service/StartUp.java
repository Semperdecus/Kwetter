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
            Account testUser = new Account("bas.de.zot@gmail.com", "user", "password");
            testUser.setBio("I am a dog person but I preffer the cat emoji! 🐱 " );
            testUser.setLocation("South");
            testUser.setWebsiteUrl("http://www.ihatejsf.com/");
            testUser.setPicture("https://images-na.ssl-images-amazon.com/images/I/51VmmRDO1EL._SX425_.jpg");
            
            Account testAdmin = new Account("admin@mail.com", "admin", "password");
            Account testMod = new Account("mod@mail.com", "mod", "password");
            testMod.setPicture("https://cutecatshq.com/wp-content/uploads/2013/09/This-Kitten-Is-Super-Super-Cute-.jpg");

            Tweet userTweet = new Tweet("offensive tweet!!!", testUser);
            Tweet userTweet2 = new Tweet("offensive tweet2!!!", testUser);
            Tweet userTweet3 = new Tweet("offensive tweet3!!!", testUser);
            Tweet adminTweet = new Tweet("I am admin", testAdmin);
            Tweet modTweet = new Tweet("I am el moderator.", testMod);

            Role roleAdmin = new Role("Admin");
            Role roleUser = new Role("User");
            Role roleModerator = new Role("Moderator");

            roleService.create(roleAdmin);
            roleService.create(roleUser);
            roleService.create(roleModerator);

            testUser.setRole(roleService.getRoleByName("User"));
            testAdmin.setRole(roleService.getRoleByName("Admin"));
            testMod.setRole(roleService.getRoleByName("Moderator"));

            accountService.create(testUser);
            accountService.create(testAdmin);
            accountService.create(testMod);

            tweetService.create(userTweet);
            tweetService.create(userTweet2);
            tweetService.create(userTweet3);
            tweetService.create(adminTweet);
            tweetService.create(modTweet);

            accountService.addFollowing(testUser.getId(), testAdmin.getId());
            accountService.addFollowing(testUser.getId(), testMod.getId());
            accountService.addFollowing(testMod.getId(), testUser.getId());
            accountService.removeFollowing(testUser.getId(), testAdmin.getId());
//            accountService.create(new Account("user2@mail.com", "user2", "password"));
//            accountService.create(new Account("user24@mail.com", "user23", "password"));
//            accountService.create(new Account("user25@mail.com", "user24", "password"));
//            accountService.create(new Account("user26@mail.com", "user25", "password"));
//            accountService.create(new Account("user245@mail.com", "user231", "password"));
//            accountService.create(new Account("user254@mail.com", "user242", "password"));
//            accountService.create(new Account("user266@mail.com", "user253", "password"));
//            accountService.create(new Account("user24@mai2l.com", "user232", "password"));
//            accountService.create(new Account("use235r25@mail.com", "user124", "password"));
//            accountService.create(new Account("user23256@mail.com", "user325", "password"));
//            accountService.create(new Account("user2452135@mail.com", "use25r231", "password"));
//            accountService.create(new Account("user252134@mail.com", "user325242", "password"));
//            accountService.create(new Account("user221366@mail.com", "use2315r253", "password"));
//            accountService.create(new Account("user22314@mail.com", "us2321er23", "password"));
//            accountService.create(new Account("user12525@mail.com", "user23524", "password"));
//            accountService.create(new Account("user513126@mail.com", "use32153r25", "password"));
//            accountService.create(new Account("user2513245@mail.com", "use213r231", "password"));
//            accountService.create(new Account("user2351254@mail.com", "us52er242", "password"));
//            accountService.create(new Account("user235266@mail.com", "us3256er253", "password"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
