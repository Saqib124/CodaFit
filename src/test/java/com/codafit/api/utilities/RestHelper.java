package com.codafit.api.utilities;

import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestHelper {

    public enum RequestType {
        GET,
        PUT,
        POST,
        DELETE;
    }

    private static RestHelper restHelper = null;

    private Response response;
    private String request;
    private Headers headers;
    private String baseUri;

    private static final String request_base_path = System.getProperty("user.dir") + "/src/test/resources/data/request/";

    private RestHelper(){
        this.request = "";
        this.headers = new Headers();

        /*Set Base url for APIs*/
        EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
        this.baseUri = environmentVariables.getProperty("base_url");
    }

    public static void reset() {
        restHelper = null;
    }

    public static RestHelper getInstance() {
        if (restHelper == null)
            restHelper = new RestHelper();
        return restHelper;
    }

    public int getStatusCode(){
        return response.getStatusCode();
    }

    public ResponseBody getResponseBody(){
        return response.getBody();
    }

    public void setValueInRequest(String key, String value){
        String temp = "\\{" + key + "}";
        request = request.replaceFirst(temp, value);

    }

    public void setRequest (String request_param) throws FileNotFoundException {
        if (request_param.equals("")) {
            return;
        }

        String[] requestParamArray = request_param.split("@");
        String filePath = request_base_path + requestParamArray[0] + ".json";
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(filePath));
        this.request = JsonParser.parseReader(bufferedReader).toString();

        for (int i=1 ; i<requestParamArray.length ; i++ ) {
            this.request = this.request.replaceFirst("%s", requestParamArray[i]);
        }
    }

    public void setHeaders(HashMap<String,String> headerMap) {
        List<Header> headerList = new ArrayList<>();
        headerList.add(new Header("Content-Type","application/json"));
        headerList.add(new Header("Accept","application/json"));
        for(Map.Entry m : headerMap.entrySet()){
            headerList.add(new Header((String) m.getKey(),(String) m.getValue()));
        }
        this.headers = new Headers(headerList);
    }

    public void triggerRestRequest(RequestType type, String endPoint, Object... params){
        switch (type){
            case GET:
                get(endPoint, params);
                break;
            case PUT:
                put(endPoint, params);
                break;
            case POST:
                post(endPoint, params);
                break;
            case DELETE:
                delete(endPoint, params);
                break;
            default:
                break;
        }

    }

    private void delete(String endPoint, Object... params){
        this.response = RestAssured.given()
                .headers(this.headers)
                .baseUri(baseUri)
                .delete(endPoint,params);
        enableRequestLogger(endPoint);
    }

    private void put(String endPoint, Object... params){
        this.response = RestAssured.given()
                .relaxedHTTPSValidation()
                .headers(this.headers)
                .body(this.request).log().body()
                .baseUri(baseUri)
                .basePath(endPoint)
                .when().put();
        enableRequestLogger(endPoint);
    }

    private void post(String endPoint, Object... params){
        this.response = RestAssured.given()
                .relaxedHTTPSValidation()
                .headers(this.headers)
                .body(this.request).log().body()
                .baseUri(baseUri)
                .when().post(endPoint,params);

        enableRequestLogger(endPoint);

    }

    private void get(String endPoint, Object... params){
        this.response = RestAssured.given()
                .relaxedHTTPSValidation()
                .headers(this.headers)
                .body("").log().body()
                .baseUri(baseUri)
                .when().get(endPoint, params);

        enableRequestLogger(endPoint);
    }

    private void enableRequestLogger(String endPoint){
        System.out.println("Base URI: " + baseUri);
        System.out.println("End Point: " + endPoint);
        System.out.println("Header: "+ this.headers.toString());
    }
}
