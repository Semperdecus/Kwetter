/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import models.Role;

/**
 *
 * @author teren
 */
public interface IRoleDao extends ICrudDao<Role>{
    List<Role> getAccountsWithRole(String Rolename);
    
    ArrayList<Role> getAll();
    
}
