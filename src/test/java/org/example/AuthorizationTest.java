package org.example;

import dto.OrderDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.auth.Credentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class AuthorizationTest {

    @BeforeEach
    public void setup() {
        System.out.println("---> test start");
        RestAssured.baseURI = "http://51.250.6.164";
        RestAssured.port = 8080;
    }

    @Test
    public void successfulTest(){
        Credentials credentials = new Credentials();



    Response response = given()
            .header("Content-type", "application/json")
            .body(orderDto)
            .log()
            .all()
            .post("/login/student")
            .then()
            .log()
            .all()
            .extract()
            .response();
    OrderDto orderDtoReceived = gson.fromJson(response.asString(), OrderDto.class);
}}
