package org.example.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class CourierData {
    private static final Faker userRus = new Faker(new Locale("ru"));
    private static final Faker userEng = new Faker(Locale.ENGLISH);

    public static String generateCourierLogin(){
        String lastName = userEng.name()
                .lastName()
                .toLowerCase()
                .replaceAll("[^a-z]", "");

        return "test_" + lastName + userRus.regexify("[0-9]{7}");
    }

    public static String generateCourierPassword(){
        return userEng.internet().password(8, 16);
    }

    public static String generateCourierFirstName() {
        return userRus.name().firstName();
    }
}
