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
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Account;
import models.Tweet;
import service.AccountService;
import service.TweetService;

/**
 *
 * @author teren
 */
@Stateless
@Path("/tweet")
@Produces({MediaType.APPLICATION_JSON})
public class TweetController {

    @Resource
    private SessionContext context;

    @Inject
    private TweetService tweetService;

    @Inject
    private AccountService userService;

    @GET
    public List<Tweet> get() {
        return tweetService.findAll();
    }

    @GET
    @Path("/{id}")
    public Tweet getById(@PathParam("id") long id) {
        Tweet tweet = tweetService.findById(id);
        if (tweet == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return tweet;
    }

    @GET
    @Path("/message")
    public List<Tweet> getByMessage(@QueryParam("arg") String arg) {
        return tweetService.findByMessage(arg);
    }

    @POST
    public Tweet post(@QueryParam("message") String message, @QueryParam("username") String username) throws Exception {
        Account user = userService.findByUsername(username);
        if (user == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        return tweetService.create(new Tweet(message, user));
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long id) throws Exception {
        Account user = userService.findByUsername(context.getCallerPrincipal().getName());
        if (user == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        tweetService.deleteOwnTweet(id, user);
    }

    @GET
    @Path("/following")
    public List<Tweet> getFollowingTweets(@QueryParam("username") String username) {
        Account user = userService.findByUsername(username);
        if (user == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        return tweetService.getFollowingTweets(user.getId());
    }
     
    @GET
    @Path("/search")
    public List<Tweet> search(@QueryParam("message") String message) {
        return tweetService.search(message);
    }
}
