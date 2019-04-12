/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dtos.LoginRequestObject;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import models.Account;
import service.AccountService;

/**
 *
 * @author teren
 */
@Stateless
@Path("/auth")
public class AuthController {

    @Inject
    private AccountService accountService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Account login(LoginRequestObject request) {
        try {
            Account response = accountService.login(request.getUsername(), request.getPassword());

            if (response == null) {
                return null;
            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @POST
    @Path("/register")
    public Account create(@QueryParam("username") String username,
            @QueryParam("password") String password,
            @QueryParam("email") String email,
            @QueryParam("bio") String bio,
            @QueryParam("location") String location,
            @QueryParam("picture") String picture) {
        try {
            Account account = new Account();
            account.setUsername(username);
            account.setEmail(email);
            account.setPassword(password);
            account.setBio(bio);
            account.setLocation(location);
            account.setPicture(picture);

            Account response = accountService.create(account);

            if (response == null) {
                return null;
            }

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
