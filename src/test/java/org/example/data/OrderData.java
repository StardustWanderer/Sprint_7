package org.example.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Locale;

public class OrderData {
    private static final Faker faker = new Faker(new Locale("ru"));

    public static final String CLIENT_FIRST_NAME = faker.name().firstName();
    public static final String CLIENT_LAST_NAME = faker.name().lastName();
    public static final String CLIENT_ADDRESS = faker.address().streetAddress();
    public static final String METRO_STATION = String.valueOf(faker.number().numberBetween(1, 100));
    public static final String CLIENT_PHONE = "+79" + faker.number().digits(9);
    public static final int RENT_TIME = faker.number().numberBetween(1, 8);
    // Завтрашняя дата в стандартном ISO-формате YYYY-MM-DD
    public static final String DELIVERY_DATE = LocalDate.now().plusDays(1).toString();
    public static final String COMMENT = "Комментарий к заказу";

    public static final String CREATE_ORDER_URL = "/api/v1/orders";
    public static final String CANCEL_ORDER_URL = "/api/v1/orders/cancel";
    public static final String GET_ORDER_URL = "/api/v1/orders";
}
