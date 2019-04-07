/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import exceptions.AccountException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import models.Account;
import service.AccountService;
import service.RoleService;
import utils.RedirectUtil;

/**
 *
 * @author teren
 */
@Named(value = "createAccountBean")
@ViewScoped
public class CreateAccountBean implements Serializable {

    @Inject
    private AccountService accountService;

    @Inject
    private RoleService roleService;

    private String username;
    private String password;
    private String email;

    @PostConstruct
    public void init() {

    }

    public void createAccount() throws AccountException {
        if (!this.username.isEmpty() && !this.password.isEmpty() && !this.email.isEmpty()) {
            Account newAccount = new Account(this.email, this.username, this.password);
            newAccount.setRole(roleService.getRoleByName("User"));
            this.accountService.create(newAccount);
        }
        RedirectUtil.redirect("/pages/admin/dashboard.xhtml");
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
