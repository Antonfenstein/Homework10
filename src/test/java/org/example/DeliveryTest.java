package org.example;

import com.google.gson.Gson;
import dto.OrderRealDto;
import helpers.SetupFunctions;
import io.restassured.RestAssured;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
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
                .extract()
                .response();

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
                .statusCode(200)
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
                .statusCode(200)
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

       for(int i =0; i < orderRealDtoArray.length; i ++){
          // System.out.println(orderRealDtoArray[i].getId());
           deleteOrderById(orderRealDtoArray[i].getId());
       }
        System.out.println();
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
    @Test
    public void deleteOrder(){
        deleteOrderById(2778);
    }
}
