/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Account;
import models.Role;
import models.Tweet;
import service.AccountService;
import service.TweetService;

/**
 *
 * @author teren
 */
@Stateless
@Path("/account")
@Produces({MediaType.APPLICATION_JSON})
public class AccountController {

    @Resource
    private SessionContext context;

    @Inject
    private AccountService accountService;

    @Inject
    private TweetService tweetService;

    @GET
    public List<Account> get() {
        return accountService.findAll();
    }

    @GET
    @Path("/{username}/tweet")
    public List<Tweet> getTweetsByUser(@PathParam("username") String username) {
        return tweetService.findByUsername(username);
    }

    @GET
    @Path("/{id}")
    public Account getById(@PathParam("id") long id) {
        Account user = accountService.findById(id);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return user;
    }

    @GET
    @Path("/username")
    public Account getByUsername(@QueryParam("username") String username) {
        Account user = accountService.findByUsername(username);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return user;
    }

    @GET
    @Path("/email")
    public Account getByEmail(@QueryParam("email") String email) {
        Account user = accountService.findByEmail(email);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return user;
    }

    @POST
    public Account post(@QueryParam("email") String email,
            @QueryParam("username") String username,
            @QueryParam("password") String password) throws Exception {
        Account user = accountService.create(new Account(email, username, password));
        if (user == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return user;
    }

    @PUT
    public void update(@QueryParam("location") String location,
            @QueryParam("websiteURL") String websiteURL,
            @QueryParam("bio") String bio) throws Exception {
        Account user = accountService.findByUsername(context.getCallerPrincipal().getName());
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        user.setBio(bio);
        user.setWebsiteUrl(websiteURL);
        user.setLocation(location);

        accountService.update(user);
    }

    @PUT
    @Path("/{id}/username")
    public void updateUsername(@QueryParam("username") String username, @PathParam("id") long id) throws Exception {
        Account user = accountService.findById(id);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        accountService.updateUsername(username, user);
    }

    @PUT
    @Path("{id}/following/{followingId}")
    public void updateFollowing(@PathParam("id") long id, @PathParam("followingId") long followingId) throws Exception {
        accountService.addFollowing(followingId, id);
    }

    @PUT
    @Path("{id}/follower/{followerId}")
    public void updateFollower(@PathParam("id") long id, @PathParam("followerId") long followerId) throws Exception {
        accountService.removeFollowing(followerId, id);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long id) throws Exception {
        accountService.deleteById(id);
    }

    @GET
    @Path("/following")
    public List<Account> findFollowing(@QueryParam("username") String username) {
        Account user = accountService.findByUsername(username);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return accountService.getFollowers(user.getId());
    }
    
    @GET
    @Path("/followers")
    public List<Account> findFollowers(@QueryParam("username") String username) {
        Account user = accountService.findByUsername(username);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return accountService.getFollowers(user.getId());
    }
}
