package com.codafit.api.bindings;

import com.codafit.api.utilities.EndPoint;
import com.fasterxml.jackson.core.JsonProcessingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.io.FileNotFoundException;

/**
 * The class HealthCheck is a glu between cucumbers file and steps.
 * Calling methods of HealthCheckSteps and making code more readable and understandable
 */
public class HealthCheck extends BaseBinding{

    @Given("User trigger test API for {string}")
    public void userTriggerTestAPIFor(String param) {
        EndPoint endPoint = EndPoint.valueOf(param);

        healthCheckSteps.triggerAPIRequest(endPoint.getText());
    }

    @Then("User validate the API status code is {string}")
    public void userValidateTheAPIStatusCodeIs(String statusCode) {
        healthCheckSteps.validateStatusCode(statusCode);
    }

    @And("Validate the json response with json schema save at {string}")
    public void validateTheJsonResponseWithJsonSchemaSaveAt(String filePath) throws FileNotFoundException, JsonProcessingException {
        healthCheckSteps.validateJsonSchema(filePath);
    }


}
