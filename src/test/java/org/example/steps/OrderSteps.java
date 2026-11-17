package org.example.steps;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.config.ApiEndpoints;
import org.example.model.OrderModel;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Создание заказа")
    public static Response createOrder(OrderModel order){
        return given()
                .filter(new AllureRestAssured())
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(ApiEndpoints.CREATE_ORDER_URL)
                .then()
                .extract().response();
    }

    @Step("Отмена заказа")
    public static Response cancelOrder(Map<String, Integer> cancelOrderModel){
        return given()
                .filter(new AllureRestAssured())
                .log().all()
                .contentType(ContentType.JSON)
                .body(cancelOrderModel)
                .when()
                .put(ApiEndpoints.CANCEL_ORDER_URL)
                .then()
                .extract().response();
    }

    @Step("Получение списка заказов")
    public static Response getOrderList(){
        return given()
                .filter(new AllureRestAssured())
                .log().all()
                .queryParam("limit", 5)
                .queryParam("page", 0)
                .get(ApiEndpoints.GET_ORDER_URL)
                .then()
                .extract().response();
    }
}
