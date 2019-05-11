/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.HashMap;

/**
 * Properties that authorisation to JSF Admin dashboard
 * @author teren
 */
public class PrincipalRoles {

    // Create a HashMap object called capitalCities
    HashMap<String, String> roles = new HashMap<>();

    public PrincipalRoles() {
        roles.put("User", "Unauthorized");
        roles.put("Mod", "Authorized");
        roles.put("Admin", "Authorized");
    }

    public HashMap<String, String> getRoles() {
        return roles;
    }
}
