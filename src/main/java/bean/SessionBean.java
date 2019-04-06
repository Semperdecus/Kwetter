/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import models.Account;
import org.omnifaces.util.Faces;
import static org.omnifaces.util.Faces.invalidateSession;

/**
 * logout bean
 * @author teren
 */
@Named(value = "sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

    private Account loggedInUser;

    public void logout() throws ServletException {
        loggedInUser = null;

        Faces.logout();
        invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/JEA_Kwetter");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Account getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Account loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

}
