/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import exceptions.AccountException;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
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
@Named(value = "editAccountBean")
@ManagedBean
@SessionScoped
public class EditAccountBean implements Serializable {

    @Inject
    private AccountService accountService;
    @Inject
    private RoleService roleService;

    private Account account;

    private String username;
    private String password;
    private String email;
    private String role;
    private String bio;
    private String location;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void findAccount(String username) {
        Account a = accountService.findByUsername(username);
        this.account = a;
        this.email = a.getEmail();
        this.username = a.getUsername();
        this.role = a.getRole().getName();
        this.password = a.getPassword();
        this.bio = a.getBio();
        this.location = a.getLocation();
    }

    public void editAccount() throws AccountException, Exception {

        if (!this.username.isEmpty() && !this.password.isEmpty() && !this.email.isEmpty() && !this.role.isEmpty()) {
            System.out.println("editting account");

            Account edittedAccount = new Account(this.email, this.username, this.password);
            edittedAccount.setRole(roleService.getRoleByName(this.role));
            edittedAccount.setBio(this.bio);
            edittedAccount.setLocation(this.location);
            edittedAccount.setId(this.account.getId());
            
            this.accountService.update(edittedAccount);
        }
        RedirectUtil.redirect("/pages/admin/dashboard.xhtml");
    }

    public void editAccounts(String username) {
        RedirectUtil.redirect("/pages/admin/edit.xhtml");
        this.findAccount(username);
    }
}
