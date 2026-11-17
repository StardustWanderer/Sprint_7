package org.example.config;

public class ApiEndpoints {
    //Эндпоинты курьера
    public static final String COURIER_CREATE_PATH = "/api/v1/courier";
    public static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";
    public static final String COURIER_DELETE_PATH = "/api/v1/courier/{id}";

    //Эндпоинты заказа
    public static final String CREATE_ORDER_URL = "/api/v1/orders";
    public static final String CANCEL_ORDER_URL = "/api/v1/orders/cancel";
    public static final String GET_ORDER_URL = "/api/v1/orders";
}
