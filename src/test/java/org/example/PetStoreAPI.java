package org.example;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;

public class PetStoreAPI {
    @BeforeEach
    public void setup() {
        System.out.println("---> test start");
        RestAssured.baseURI = "https://petstore.swagger.io/v2/store/";
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 7})
    public void simplePositiveTest(int id) {

        given().
                log().
                all().
                when().
                get("order/{id}", id).
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK);
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 7})
    public void placedStatusTest(int id) {

        String status = given().
                log().
                all().
                when().
                get("order/{id}", id).
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK).
                and().
                extract().
                path("status");
        Assertions.assertTrue(status.contains("placed"));

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 8})
    public void notFoundTest(int id) {

        given().
                log().
                all().
                when().
                get("test-orders/{id}", id).
                then().
                log().
                all().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 7})
    public void completeStatusTest(int id) {

        Boolean complete = given().
                log().
                all().
                when().
                get("order/{id}", id).
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK).
                and().
                extract().
                path("complete");
        Assertions.assertTrue(complete == true);

    }

}
