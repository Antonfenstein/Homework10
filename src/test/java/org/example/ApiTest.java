package org.example;

import com.google.gson.Gson;
import dto.OrderTestDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest {

    @BeforeEach
    public void setup() {
        System.out.println("---> test start");
        RestAssured.baseURI = "http://51.250.6.164";
        RestAssured.port = 8080;
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 9, 10})
    public void simplePositiveTest(int id) {

        given().
                log().
                all().
                when().
                get("test-orders/{id}", id).
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 11, -1})
    public void simpleNegativeTest(int id) {

        given().
                log().
                all().
                when().
                get("test-orders/{id}", id).
                then().
                log().
                all().
                statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    //    String order = "{\"customerName\":\"name\",\"customerPhone\":\"123456\",\"comment\":\"comment\"}";


    @Test
    public void createOrderAndCheckStatusCode() {
//        OrderDto orderDto = new OrderDto("testname", "1234567", "no");

        OrderTestDto orderTestDtoRandom = new OrderTestDto();
        orderTestDtoRandom.setCustomerName(generateRandomName());
        orderTestDtoRandom.setCustomerPhone(generateRandomPhone());
        orderTestDtoRandom.setComment(generateRandomComment());
        Gson gson = new Gson();
        Response response = given()
                .header("Content-type", "application/json")
                .body(orderTestDtoRandom)
                .log()
                .all()
                .post("/test-orders")
                .then()
                .log()
                .all()
                .extract().response();

OrderTestDto orderTestDtoReceived = gson.fromJson(response.asString(), OrderTestDto.class);


               assertEquals(orderTestDtoRandom.getCustomerName(), orderTestDtoReceived.getCustomerName());
               assertEquals(orderTestDtoRandom.getCustomerPhone(), orderTestDtoReceived.getCustomerPhone());
               assertEquals(orderTestDtoRandom.getComment(), orderTestDtoReceived.getComment());

               Assertions.assertNotNull(orderTestDtoReceived.getId());
               Assertions.assertNotNull(orderTestDtoReceived.getStatus());


        assertAll(
                "Grouped Assertions of User",
                () -> assertEquals("noo", orderTestDtoReceived.getComment(), "1 st Assert"),
                () -> assertEquals("testnamee", orderTestDtoReceived.getCustomerName(), "2nd Assert")
        );
    }

    String body = "{\"customerName\":\"name\",\"customerPhone\":\"123456\",\"comment\":\"comment\"}";

    @Test
    public void negativeCreateOrderAndCheckStatusCode() {

        given()
                .header("", "")
                .body(body)
                .log()
                .all()
                .post("/test-orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5, 9, 10})
    public void bodyPositiveTest(int id) {

        String status = given().
                log().
                all().
                when().
                get("test-orders/{id}", id).
                then().
                log().
                all().
                statusCode(HttpStatus.SC_OK).
                and().
                extract().
                path("status");
        Assertions.assertTrue(status.contains("OPEN"));

    }

    public String generateRandomName() {
        return RandomStringUtils.random(10, true, false);

    }

    public String generateRandomPhone() {
        return RandomStringUtils.random(10, false, true);
    }

    public String generateRandomComment() {
        return RandomStringUtils.random(5, true, false);
    }

}

