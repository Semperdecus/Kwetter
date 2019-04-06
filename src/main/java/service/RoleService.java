/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.IAccountDao;
import dao.IRoleDao;
import dao.JPA;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import models.Account;
import models.Role;

/**
 *
 * @author teren
 */
@Stateless
public class RoleService {

    @Inject
    @JPA
    private IRoleDao roleDao;

    @Inject
    @JPA
    private IAccountDao accountDao;

    public RoleService() {
        super();
    }

    public Role create(Role role) {
        return this.roleDao.create(role);
    }

    public void remove(Role role) {
        this.roleDao.delete(role);
    }

    public ArrayList<Role> getAllRoles() {
        return this.roleDao.getAll();
    }

    public Role getRoleByName(String roleName) {
        return this.roleDao.getRoleByName(roleName);
    }

    public void update(Role entity) throws Exception {
        Role role = roleDao.findById(entity.getId());
        roleDao.update(role);
    }
}
