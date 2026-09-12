package org.example.tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static org.example.steps.OrderSteps.getOrderList;
import static org.hamcrest.Matchers.*;

public class GetOrderListTest extends BaseApiTest {
    @Test
    @Description("Получение списка заказов возвращает код 200 и JSON с непустым массивом orders")
    public void getOrderListReturn200AndArrayWithOrders(){
        Response orderListResponse = getOrderList();
        orderListResponse
                .then()
                .statusCode(200)
                .body("orders", isA(List.class))
                .body("orders", not(empty()));
    }
}
