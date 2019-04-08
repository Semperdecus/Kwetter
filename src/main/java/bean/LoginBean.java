/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.security.Security;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import models.Account;
import service.AccountService;
import utils.RedirectUtil;

/**
 * @RequestScope: Beans are created for the duration of the request
 * @author teren
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private AccountService accountService;
    @Inject
    private SessionBean sessionBean;

    private String username;
    private String password;

    public void init() throws ServletException {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        if (request.getUserPrincipal() == null) {
            request.logout();
        } else {
            redirect(request);
        }
    }

    public void login() throws ServletException {
        String username = this.username.toLowerCase();

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.login(username, this.password);
        } catch (ServletException e) {
            RedirectUtil.redirect("/error.xhtml");
            request.logout();
            e.printStackTrace();
        }

        redirect(request);
    }

    public void redirect(HttpServletRequest request) throws ServletException {
        boolean isUser = request.isUserInRole("User");
        boolean isAdmin = request.isUserInRole("Admin");
        boolean isMod = request.isUserInRole("Moderator");

        if (isUser) {
            request.logout();
            RedirectUtil.redirect("/pages/user/profile.xhtml");
        } else if (isAdmin || isMod) {
            RedirectUtil.redirect("/pages/admin/dashboard.xhtml");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
