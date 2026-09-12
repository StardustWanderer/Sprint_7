package org.example.data;

import com.github.javafaker.Faker;

public class CourierData {
    private static final Faker user = new Faker();

    public static final String LOGIN = user.name().lastName() + user.regexify("[0-9]{6}");
    public static final String PASSWORD = user.regexify("[0-9]{6}");
    public static final String FIRST_NAME = "saske";

    public static final String COURIER_CREATE_PATH = "/api/v1/courier";
    public static final String COURIER_LOGIN_PATH = "/api/v1/courier/login";
    public static final String COURIER_DELETE_PATH = "/api/v1/courier/{id}";
}
