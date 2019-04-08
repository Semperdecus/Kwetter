/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import utils.EmailUtil;
import exceptions.AccountException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.AccessLocalException;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;
import models.Account;
import service.AccountService;
import utils.RedirectUtil;

/**
 *
 * @author teren
 */
@Named(value = "accountBean")
@ViewScoped
public class AccountBean implements Serializable {

    @Inject
    private AccountService accountService;

    @Inject
    private EmailUtil emailBean;

    private List<Account> accounts;
    private Account account;

    @PostConstruct
    public void init() {
        this.accounts = this.accountService.findAll();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void deleteAccount(Account account) throws AccountException, MessagingException, UnsupportedEncodingException {
        try {
            accountService.delete(account.getId());
            emailBean.sendEmail_AccountDeleted(account);
            RedirectUtil.redirect("/pages/admin/dashboard.xhtml");
        } catch (AccessLocalException e) {
            System.out.println("NO PERMISSION");
        }
    }

}
