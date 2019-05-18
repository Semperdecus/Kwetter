/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import models.Account;
import service.AccountService;
import service.TweetService;
import utils.JwtUtil;

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

    private JwtUtil jwtUtil = new JwtUtil();

    @GET
    public Response get(@Context UriInfo uriInfo) {
        List<Account> accounts = accountService.findAll();
        for(Account a : accounts){
            initLinks(a, uriInfo);
        }

        GenericEntity<List<Account>> genericEntity
                = new GenericEntity<List<Account>>(accounts) {
        };

        Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
                .rel("self").build();

        return Response.ok(genericEntity).links(self).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") long id, @Context UriInfo uriInfo) {

        Account account = accountService.findById(id);

        Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
                .rel("self").build();
        if (account == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return Response.ok(account)
                .links(self).build();
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
    public Response post(@QueryParam("email") String email,
            @QueryParam("username") String username,
            @QueryParam("password") String password) throws Exception {
        Account user = accountService.create(new Account(email, username, password));
        if (user == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.status(201)
                .contentLocation(new URI("/account/" + user.getId())).build();
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
    @Path("/update")
    public void updateJWT(@HeaderParam("Authorization") String bearer,
            @QueryParam("location") String location,
            @QueryParam("website") String website,
            @QueryParam("bio") String bio,
            @QueryParam("email") String email,
            @QueryParam("picture") String picture,
            @QueryParam("username") String username) throws Exception {
        if (jwtUtil.validateJwt(bearer)) {
            Account user = accountService.findByUsername(username);
            if (user == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
            if (!email.isEmpty()) {
                user.setEmail(email);
            }
            user.setBio(bio);
            user.setWebsiteUrl(website);
            user.setLocation(location);
            user.setPicture(picture);

            accountService.update(user);
        } else {
            System.out.println("No valid jwt token");
        }
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
    public void updateFollowing(@HeaderParam("Authorization") String bearer, @PathParam("id") long id, @PathParam("followingId") long followingId) throws Exception {
        System.out.println(bearer);
        if (jwtUtil.validateJwt(bearer)) {
            accountService.addFollowing(followingId, id);
        } else {
            System.out.println("No valid jwt token");
        }
    }

    @PUT
    @Path("{id}/follower/{followerId}")
    public void updateFollower(@HeaderParam("Authorization") String bearer, @PathParam("id") long id, @PathParam("followerId") long followerId) throws Exception {
        if (jwtUtil.validateJwt(bearer)) {
            accountService.removeFollowing(followerId, id);
        } else {
            System.out.println("No valid jwt token");
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) throws Exception {
        accountService.deleteById(id);
        return Response.status(200).build();
    }

    @GET
    @Path("/following")
    public List<Account> findFollowing(@QueryParam("username") String username) {
        Account user = accountService.findByUsername(username);
        if (user == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return accountService.getFollowing(user.getId());
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

    @GET
    @Path("/search")
    public List<Account> search(@QueryParam("username") String username) {
        return accountService.search(username);
    }

    /**
     * create self link
     *
     * @param account
     * @param uriInfo
     */
    private void initLinks(Account account, UriInfo uriInfo) {
        UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
        uriBuilder = uriBuilder.path(Long.toString(account.getId()));
        Link.Builder linkBuilder = Link.fromUriBuilder(uriBuilder);
        Link selfLink = linkBuilder.rel("self").build();
        //also we can add other meta-data by using: linkBuilder.param(..),
        // linkBuilder.type(..), linkBuilder.title(..)
        account.setLinks(Arrays.asList(selfLink));
    }
}
