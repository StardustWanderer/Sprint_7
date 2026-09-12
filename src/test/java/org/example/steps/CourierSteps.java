package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.qameta.allure.restassured.AllureRestAssured;
import org.example.model.CourierModel;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.example.data.CourierData.COURIER_CREATE_PATH;
import static org.example.data.CourierData.COURIER_LOGIN_PATH;
import static org.example.data.CourierData.COURIER_DELETE_PATH;

public class CourierSteps {
    @Step("Создание курьера")
    public static Response createCourier(CourierModel courierModel){
        return given()
                .filter(new AllureRestAssured())
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(COURIER_CREATE_PATH)
                .then()
                .extract().response();
    }

    @Step("Создание курьера(нестандартный JSON через Map)")
    public static Response createCourier(Map<String, String> courierData){
        return given()
                .filter(new AllureRestAssured())
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierData)
                .when()
                .post(COURIER_CREATE_PATH)
                .then()
                .extract().response();
    }

    @Step("Логин курьера")
    public static Response loginCourier(CourierModel courierModel){
        return given()
                .filter(new AllureRestAssured())
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then()
                .extract().response();
    }

    @Step("Логин курьера курьера(нестандартный JSON через Map)")
    public static Response loginCourier(Map<String, String> courierData){
        return given()
                .filter(new AllureRestAssured())
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierData)
                .when()
                .post(COURIER_LOGIN_PATH)
                .then()
                .extract().response();
    }

    @Step("Удаление курьера")
    public static Response deleteCourier(CourierModel courierModel){
        return given()
                .filter(new AllureRestAssured())
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierModel)
                .pathParam("id", courierModel.getId())
                .when()
                .delete(COURIER_DELETE_PATH)
                .then()
                .extract().response();
    }
}
