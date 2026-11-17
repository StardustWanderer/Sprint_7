package org.example.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Locale;

public class OrderData {
    private static final Faker userRus = new Faker(new Locale("ru"));
    private static final Faker userEng = new Faker(Locale.ENGLISH);

    public static String generateClientFirstName() {
        return userRus.name().firstName();
    }

    public static String generateClientLastName() {
        return userRus.name().lastName();
    }

    public static String generateClientAddress() {
        return userRus.address().streetAddress();
    }

    public static String generateMetroStationId() {
        return String.valueOf(userEng.number().numberBetween(1, 200));
    }

    public static String generateClientPhoneNumber() {
        return userRus.numerify("+79#########");
    }

    public static int generateRentTimeDuration() {
        return userRus.number().numberBetween(1, 8);
    }

    // Дата доставки от одного до семи дней от текущей даты
    public static String generateDeliveryDate() {
        return LocalDate.now().plusDays(userEng.number().numberBetween(1, 8)).toString();
    }

    public static String generateClientComment(){
        return userRus.witcher().quote();
    }
}
