/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.IRoleDao;
import dao.JPA;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.json.JsonObject;
import models.Role;

/**
 *
 * @author teren
 */
public class RoleService {

    @Inject
    @JPA
    private IRoleDao roleDao;

    public RoleService() {
    }

    public RoleService(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public Role create(Role role) {
        return this.roleDao.add(role);
    }

    public void remove(Role role) {
        this.roleDao.delete(role);
    }

    public ArrayList<Role> getAllRoles() {
        return this.roleDao.getAll();
    }

    public List<Role> getAllAccountsWithRole(String roleName) {
        return this.roleDao.getAccountsWithRole(roleName);
    }

    public List<JsonObject> convertAllToJson(List<Role> roles) {
        List<JsonObject> convertedObjects = new ArrayList<>();
        for (Role role : roles) {
            convertedObjects.add(role.convertToJson());
        }
        return convertedObjects;
    }
}
