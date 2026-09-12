package org.example.tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.example.model.CourierModel;
import org.junit.After;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.example.data.CourierData.*;
import static org.example.steps.CourierSteps.createCourier;
import static org.example.steps.CourierSteps.deleteCourier;
import static org.example.steps.CourierSteps.loginCourier;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest extends BaseApiTest {

    private boolean courierCreated = false;
    String courierId = null;

    @Test
    @Description("Проверка успешного создания курьера с корректными данными: ручка возвращает статус-код 201 и ok: true.")
    public void createCourierWithValidDataReturns201() {

        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);

        // Отправляем запрос на создание
        Response createResponse = createCourier(courier);

        // НЮАНС: Фиксируем факт создания ДО проверок RestAssured.
        // Если проверка ниже упадет (например, статус окажется 200 вместо 201),
        // курьер все равно УЖЕ создан в БД, и метод @After обязателен к выполнению.
        courierCreated = createResponse.statusCode() == 201;

        // Выполняем проверки ответа (Assertion)
        createResponse.then()
                .log().all()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @Description("Создание курьера с уже существующим логином: ручка возвращает статус-код 409 и message: Этот логин уже используется")
    public void createCourierWithOccupiedLoginReturns409() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);

        // НЮАНС: Тест состоит из двух этапов.
        // Этап 1: Сначала принудительно создаем первого курьера, чтобы занять логин в БД
        Response firstCreate = createCourier(courier);
        courierCreated = firstCreate.statusCode() == 201;

        firstCreate.then()
                .log().all()
                .statusCode(201)
                .body("ok", equalTo(true));

        // Этап 2: Пробуем создать ВТОРОГО курьера с тем же логином и проверяем ошибку 409
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @Description("Создание курьера без firstName: ручка возвращает статус-код 201 и ok: true")
    public void createCourierWithoutFirstNameReturns201() {

        CourierModel courier = new CourierModel(LOGIN, PASSWORD);

        Response createResponse = createCourier(courier);
        courierCreated = createResponse.statusCode() == 201;

        createResponse
                .then()
                .log().all()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @Description("Создание курьера без обязательного поля login: ручка возвращает статус-код 400 и message: Недостаточно данных для создания учетной записи")
    public void createCourierWithoutLoginReturns400() {
        // НЮАНС: Используем HashMap вместо POJO-модели.
        // Это позволяет легко передать JSON без ключа "login" (а не с логином null),
        // не плодя лишние конструкторы в классе CourierModel.
        Map<String, String> courier = new HashMap<>();
        courier.put("password", PASSWORD);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Создание курьера без обязательного поля password: ручка возвращает статус-код 400 и message: Недостаточно данных для создания учетной записи")
    public void createCourierWithoutPasswordReturns400() {
        // Аналогично: отправляем JSON, в котором есть только ключ "login"
        Map<String, String> courier = new HashMap<>();
        courier.put("login", LOGIN);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void deleteCourierAfterTest() {

        if (!courierCreated) {
            return;
        }

        CourierModel courier = new CourierModel(LOGIN, PASSWORD);
        Response loginResponse = loginCourier(courier);

        if (loginResponse.statusCode() == 200) {
            courierId = loginResponse
                    .body()
                    .path("id")
                    .toString();
        }

        if(courierId != null){
            CourierModel delete = new CourierModel(courierId);
            Response responseCourierDelete = deleteCourier(delete);
            responseCourierDelete.then().log().all();
        }
    }
}