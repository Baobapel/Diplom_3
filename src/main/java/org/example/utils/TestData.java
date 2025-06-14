package org.example.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.pages.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.UUID;

public class TestData {
    private final WebDriver driver;
    private static final String API_URL = "https://stellarburgers.nomoreparties.site/api";

    public TestData(WebDriver driver) {
        this.driver = driver;
    }

    public String getName() {
        return "Тест" + UUID.randomUUID().toString().substring(0, 5);
    }

    public String getEmail() {
        return "test_" + UUID.randomUUID().toString().substring(0, 5) + "@example.com";
    }

    public String getPassword() {
        return "Qwerty123";
    }

    // Регистрация через API
    public User registerUserViaApi() {
        String email = getEmail();
        String password = getPassword();
        String name = getName();

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(String.format("{\"email\":\"%s\",\"password\":\"%s\",\"name\":\"%s\"}",
                        email, password, name))
                .post(API_URL + "/auth/register");

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to register user via API");
        }

        return new User(email, password, response.path("accessToken"));
    }

    // Удаление пользователя через API
    public void deleteUserViaApi(String accessToken) {
        RestAssured.given()
                .header("Authorization", accessToken)
                .delete(API_URL + "/auth/user")
                .then()
                .statusCode(202);
    }

    // Внутренний класс для хранения данных пользователя
    public static class User {
        public final String email;
        public final String password;
        public final String accessToken;

        public User(String email, String password, String accessToken) {
            this.email = email;
            this.password = password;
            this.accessToken = accessToken;
        }
    }

    public void registerNewUser(String email, String password) {
        String name = getName();
        new MainPage(driver)
                .open()
                .clickPersonalAccountButton()
                .clickRegisterLink()
                .fillName(name)
                .fillEmail(email)
                .fillPassword(password)
                .clickRegister();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("login"));

    }
}

