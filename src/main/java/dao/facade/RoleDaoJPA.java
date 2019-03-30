/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.facade;

import dao.IRoleDao;
import dao.JPA;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import models.Role;

/**
 *
 * @author teren
 */
@Stateless
@JPA
public class RoleDaoJPA extends CrudDaoJPA<Role> implements IRoleDao {

    public List<Role> getAccountsWithRole(String RoleName) {
        TypedQuery<Role> query = em.createNamedQuery("role.accountsWithRole", Role.class);
        query.setParameter("name", RoleName);
        List<Role> result = query.getResultList();
        return result;
    }

    @Override
    public ArrayList<Role> getAll() {
        Query query = em.createQuery("SELECT a FROM Role a");
        return new ArrayList<>(query.getResultList());
    }


}
