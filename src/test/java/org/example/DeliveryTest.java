package org.example;

import com.google.gson.Gson;
import dto.CourierCreationDto;
import dto.OrderRealDto;
import helpers.SetupFunctions;
import io.restassured.RestAssured;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class DeliveryTest {
    public static String token;

    @BeforeAll
    public static void setup() {
        System.out.println("---> test start");

        SetupFunctions setupFunctions = new SetupFunctions();
//        String baseUrl = setupFunctions.getBaseUrl();
//        String username = setupFunctions.getUsername();
//        String pwd = setupFunctions.getPassword();
        System.out.println("token: " + setupFunctions.getToken());
        token = setupFunctions.getToken();
        RestAssured.baseURI = setupFunctions.getBaseUrl();


    }

    @Test
    public void createOrderTest() {
        OrderRealDto orderRealDto = new OrderRealDto("testname", "1234567", "no");

        // 1
        Gson gson = new Gson();


        // 2
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(orderRealDto))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

    }

    @Test
    public void createOrderNoCommentTest() {
        OrderRealDto orderRealDto = new OrderRealDto("testname", "1234567", "");

        // 1
        Gson gson = new Gson();


        // 2
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(orderRealDto))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    public void createOrderNoTokenTest() {
        OrderRealDto orderRealDto = new OrderRealDto("testname", "1234567", "");

        // 1
        Gson gson = new Gson();


        // 2
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer ")
                .body(gson.toJson(orderRealDto))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);

    }

    @Test
    public void getOrderbyId() {
        int id = orderCreationPrecondition();
        //create order
        int receivedId = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders" + "/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .path("id");
        Assertions.assertEquals(receivedId, id);
    }

    @Test
    public void notExistingOrderbyId() {
        int id = 3000;
        //create order
        String response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders" + "/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response()
                .asString();
        Assertions.assertEquals("", response);

    }

    @Test
    public void getOrders() {
        int id = orderCreationPrecondition();
        OrderRealDto[] orderRealDtoArray = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders")
                .then()
                .log()
                .all()
                .extract()
                .as(OrderRealDto[].class);

        for (int i = 0; i < orderRealDtoArray.length; i++) {
            // System.out.println(orderRealDtoArray[i].getId());
            deleteOrderById(orderRealDtoArray[i].getId());
        }
        OrderRealDto[] orderRealDtoArrayDeleted = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders")
                .then()
                .log()
                .all()
                .extract()
                .as(OrderRealDto[].class);
        Assertions.assertEquals(0, orderRealDtoArrayDeleted.length);
    }

    public int orderCreationPrecondition() {

        OrderRealDto orderRealDto = new OrderRealDto("testname", "1234567", "no");
        Gson gson = new Gson();

        int id = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(orderRealDto))
                .log()
                .all()
                .post("/orders")
                .then()
                .log()
                .all()
                .extract()
                .path("id");

        return id;

    }

    public void deleteOrderById(long id) {


        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .delete("/orders/" + id)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200);


    }

    public Response executeGetMethodByStudent(String path) {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get(path)
                .then()
                .log()
                .all()
                .extract()
                .response();
        return response;

    }

    public Response executePutMethodByStudent(String path) {
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .put(path)
                .then()
                .log()
                .all()
                .extract()
                .response();
        return response;

    }

    @Test
    public void deleteOrder() {

        int orderId = orderCreationPrecondition();
       deleteOrderById(orderId);
        given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log()
                .all()
                .get("/orders" + "/" + orderId)
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void courierOrderAvailabilityForbiddenStudent() {
       Response response = executeGetMethodByStudent("/orders/available");
        Assertions.assertEquals(response.statusCode(), HttpStatus.SC_FORBIDDEN);


    }

    @Test
    public void courierOrderAssignForbiddenStudent() {
        int orderId = orderCreationPrecondition();
       Response response = executePutMethodByStudent(String.format("/orders/%s/assign", orderId));
        Assertions.assertEquals(response.statusCode(), HttpStatus.SC_FORBIDDEN);
    }

    @Test
    public void createCourier() {
        Response response = CourierCreation();
        Assertions.assertEquals(response.statusCode(), HttpStatus.SC_OK);


    }

    public Response CourierCreation() {
        String courierName = generateRandomName();
        String courierPassword = generateRandomPassword();
        String courierLogin = generateRandomLogin();
        CourierCreationDto courierBody = new CourierCreationDto(courierLogin, courierPassword, courierName);
        Gson gson = new Gson();
        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(gson.toJson(courierBody))
                .log()
                .all()
                .post("/users/courier")
                .then()
                .log()
                .all()
                .extract().response();
        return response;

    }

    public String generateRandomName() {
        return RandomStringUtils.random(10, true, false);

    }

    public String generateRandomPassword() {
        return RandomStringUtils.random(10, true, true);
    }

    public String generateRandomLogin() {
        return RandomStringUtils.random(8, true, false);
    }

}
