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
import utils.JwtUtil;

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
    private AccountService accountService;

    private JwtUtil jwtUtil = new JwtUtil();

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

    @GET
    @Path("/username")
    public List<Tweet> getByUsername(@QueryParam("username") String username) {
        Account a = this.accountService.findByUsername(username);
        List<Tweet> t = this.tweetService.findByAccountId(a);
        return t;
    }

    @POST
    public Tweet post(@QueryParam("message") String message, @QueryParam("username") String username) throws Exception {
        Account user = accountService.findByUsername(username);
        if (user == null) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        return tweetService.create(new Tweet(message, user));
    }

    @DELETE
    @Path("/{id}")
    public void delete(@HeaderParam("Authorization") String bearer, @PathParam("id") long id) throws Exception {
        if (jwtUtil.validateJwt(bearer)) {
            tweetService.deleteOwnTweet(id);
        } else {
            // Deleting tweet through jms interface
            Account user = accountService.findByUsername(context.getCallerPrincipal().getName());
            if (user == null) {
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
            }
            tweetService.deleteOwnTweet(id);
        }
    }

    @GET
    @Path("/following")
    public List<Tweet> getFollowingTweets(@QueryParam("username") String username) {
        Account user = accountService.findByUsername(username);
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
