package org.example.tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.model.CourierModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.example.data.CourierData.LOGIN;
import static org.example.data.CourierData.PASSWORD;
import static org.example.steps.CourierSteps.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;

public class LoginCourierTest extends BaseApiTest {

    protected CourierModel courier;
    protected String courierId;
    protected boolean isCourierCreated = false;

    @Before
    public void createCourierBeforeTest() {
        courier = new CourierModel(LOGIN, PASSWORD);
        Response createResponse = createCourier(courier);

         if(createResponse.statusCode() == 201){
             isCourierCreated = true;
         }

        createResponse.then()
                .log().all()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    // НЮАНС: В аннотацию @Description стоит передавать понятное описание для Allure-отчета
    @Description("Успешный логин курьера с валидными данными возвращает status 200 и id курьера")
    public void loginCourierWithValidDataReturns200() {

        // Переменная 'courier' получена из базового класса (подготовлена в @Before)
        Response responseCourierLogin = loginCourier(courier);

        // Проверяем статус 200 и то, что id вернулся в виде целого числа (Integer)
        responseCourierLogin.then()
                .log().all()
                .statusCode(200)
                .body("id", isA(Integer.class));

        // НЮАНС: Извлекаем id для последующего удаления в @After.
        // ВАЖНО: Если проверка .statusCode(200) выше упадет, эта строка НЕ выполнится,
        // и courierId останется равен null.
        courierId = responseCourierLogin
                .body()
                .path("id")
                .toString();
    }

    @Test
    @Description("Попытка залогиниться без поля password возвращает status 400 и message: Недостаточно данных для входа")
    public void loginCourierWithoutPasswordReturns400() {
        Map<String, String> courierWithoutPassword = new HashMap<>();
        courierWithoutPassword.put("login", LOGIN);

        loginCourier(courierWithoutPassword).then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Попытка залогиниться без поля login возвращает status 400 и message: Недостаточно данных для входа")
    public void loginCourierWithoutLoginReturns400() {
        Map<String, String> courierWithoutLogin = new HashMap<>();
        courierWithoutLogin.put("password", PASSWORD);

        loginCourier(courierWithoutLogin).then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Попытка залогиниться с неправильным паролем возвращает status 404 и message: Учетная запись не найдена")
    public void loginCourierWithInvalidPasswordReturns404(){
        CourierModel courierInvalidPassword = new CourierModel(LOGIN, "0invldpsswrd0");
        loginCourier(courierInvalidPassword).then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @Description("Попытка залогиниться с неправильным логином возвращает status 404 и message: Учетная запись не найдена")
    public void loginCourierWithInvalidLoginReturns404(){
        CourierModel courierInvalidLogin = new CourierModel("1111invldlogin0000", PASSWORD);

        loginCourier(courierInvalidLogin).then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    // Метод очистки тестовых данных после выполнения теста
    @After
    public void cleanUp() {
        if (!isCourierCreated) {
            return;
        }

        if (courierId == null) {
            Response loginResponse = loginCourier(courier);
            if (loginResponse.statusCode() == 200) {
                courierId = loginResponse.path("id").toString();
            }
        }

        if(courierId != null){
            CourierModel delete = new CourierModel(courierId);
            Response responseCourierDelete = deleteCourier(delete);
            responseCourierDelete.then().log().all();
        }
    }
}