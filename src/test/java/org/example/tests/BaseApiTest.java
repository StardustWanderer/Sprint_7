package org.example.tests;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.Before;

import static org.example.config.Urls.BASE_URL;

public abstract class BaseApiTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;

        // Настройка таймаута: если сервер не отвечает за 40000 мс (30 сек), запрос обрывается
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 40000)
                        .setParam("http.socket.timeout", 40000));
    }
}
