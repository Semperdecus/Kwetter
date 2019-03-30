/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.facade;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import models.Account;
import models.Role;

/**
 *
 * @author teren
 */
public class RoleDaoColl {

    CopyOnWriteArrayList<Role> roles = new CopyOnWriteArrayList<>();

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public ArrayList<Role> getAllRoles() {
        return new ArrayList<>(roles);
    }

    public ArrayList<Account> getAllAccountsWithRole(String roleName) {
        for (Role role : roles) {
            if (role.getName().contentEquals(roleName)) {
                return new ArrayList<>(role.getAccountsWithThisRole());
            }
        }
        return null;
    }
}
