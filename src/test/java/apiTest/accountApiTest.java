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

    private static String baseUrl = "http://localhost:8080/Kwetter/api";

    // Case 1 - Get user by id
    @Test
    public void userGetTest1() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(baseUrl + "/account/" + 1);
        Response response = target.request().get();
        System.out.println(response.toString());

        try {
            Assert.assertEquals(200, response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
    
    // Case 2 - Get user by username
    @Test
    public void userGetTest2() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(baseUrl + "/?username=userStartUp");
        Response response = target.request().get();
        System.out.println(response.toString());

        try {
            Assert.assertEquals(200, response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }

    // Case 3 - post non-existing email and username
    @Test
    public void userPostTest1() throws Exception {
        String input = "";
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(baseUrl + "/account/?email=user1@mail.com&username=user1&password=password");
        Invocation.Builder builder = target.request();

        Response response = builder.post(Entity.json(input));
        System.out.println(response.toString());

        try {
            Assert.assertEquals(200, response.getStatus());
        } finally {
            response.close();
            client.close();
        }
    }
    
}
