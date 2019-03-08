/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiTest;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author teren
 */
public class accountApiTest {

    private static String baseUrl = "http://localhost:8080/kwetter/api";

    // @GET
    // @Path("/{id}")
    // public User getById(@PathParam("id") long id);
    // Case 1 - Test existing User
    //@Test
    public void userGetTest1() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(baseUrl + "/user/" + 1);
        Response response = target.request().get();
        System.out.println(response.toString());

        try {
            Assert.assertEquals(200, response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
}
