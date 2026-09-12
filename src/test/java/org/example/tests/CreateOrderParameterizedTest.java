package org.example.tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.example.model.OrderModel;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.data.OrderData.*;
import static org.example.steps.OrderSteps.cancelOrder;
import static org.hamcrest.Matchers.isA;
import static org.example.steps.OrderSteps.createOrder;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest extends BaseApiTest {

    private final List<String> color;
    private boolean isOrderCreated = false;
    private Integer orderTrack;

    public CreateOrderParameterizedTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Создание заказа с цветом {0}")
    public static Object[][] getColor() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("GREY", "BLACK")},
                {List.of()}
        };
    }

    @Test
    @Description("Создание заказа с разными цветами или отсутсвием выбранного цвета возвращает код 201 и трек номер типа integer")
    public void createOrderWithDifferentColorsReturns201AndTrackNumber() {
        OrderModel order = new OrderModel(
                CLIENT_FIRST_NAME,
                CLIENT_LAST_NAME,
                CLIENT_ADDRESS,
                METRO_STATION,
                CLIENT_PHONE,
                RENT_TIME,
                DELIVERY_DATE,
                COMMENT,
                color);

        Response response = createOrder(order);
        if (response.statusCode() == 201) {
            isOrderCreated = true;
            orderTrack = response.body().path("track");
        }

        response.then()
                .log().all()
                .statusCode(201)
                .body("track", isA(Integer.class));
    }

    @After
    public void cleanUp() {
        if (!isOrderCreated || orderTrack == null) {
            return;
        }

        Map<String, Integer> cancelOrderModel = new HashMap<>();
        cancelOrderModel.put("track", orderTrack);

        Response cancelOrderResponse = cancelOrder(cancelOrderModel);
        cancelOrderResponse.then().log().all();
        }
    }
