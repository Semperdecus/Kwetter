/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import utils.RedirectUtil;

/**
 *
 * @author teren
 */
@Named(value = "redirectBean")
public class RedirectBean implements Serializable{

    public void createAccounts() {
        RedirectUtil.redirect("/pages/admin/create.xhtml");
    }

    public void viewAccounts() {
        RedirectUtil.redirect("/pages/admin/dashboard.xhtml");
    }
}
