/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrationTests.steps;

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import org.junit.Assert;

/**
 *
 * @author teren
 */
public class RegisterSteps {

    private static String baseUrl = "http://localhost:8080/Kwetter/api";
    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;

    @Given("^I want to create an account$")
    public void i_want_to_create_an_account() throws Exception {
        request = given().parameters("username", "John", "accountPassword", "Doe123", "email", "johndoe@mail.nl");
    }

    @When("^I submit my account information$")
    public void i_submit_my_account_information() throws Exception {
        response = request.when().post(baseUrl + "/account/?email=johndoe@mail.nl&username=John&password=Doe123");
    }

    @Then("^The status code is (\\d+)$")
    public void the_status_code_is(int arg1) throws Exception {
        json = response.then().statusCode(arg1);
    }
}
