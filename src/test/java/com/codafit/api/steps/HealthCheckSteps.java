package com.codafit.api.steps;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.codafit.api.utilities.Helper;
import io.restassured.response.ResponseBody;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import java.io.FileNotFoundException;
import java.util.List;

import static com.codafit.api.utilities.RestHelper.RequestType.GET;


public class HealthCheckSteps extends BaseSteps {

    /**
     * Trigger the API call using SerenityRest.
     * SerenityRest is wrapper over Rest Assured API handler.
     */
    @Step
    public void triggerAPIRequest(String endPoint){
        REST.triggerRestRequest(GET,endPoint);
    }

    @Step
    public void validateStatusCode (String statusCode){
//        ResponseBody body = response.getBody();
//        System.out.println("Response Body is: " + body.asString());
        Assert.assertEquals( "Status code mismatch: ",Integer.parseInt(statusCode) , REST.getStatusCode());
    }

}
