package org.example.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class LoginPage {
    private final WebDriver driver;

    // Локаторы элементов
    private final By emailField = By.xpath("//input[@name='name']");
    private final By passwordField = By.xpath("//input[@name='Пароль']");
    private final By loginButton = By.xpath("/html/body/div/div/main/div/form/button");
    private final By registerLink = By.xpath("//a[text()='Зарегистрироваться']");
    private final By restorePasswordLink = By.xpath("//a[text()='Восстановить пароль']");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Заполнить поле 'Email'")
    public LoginPage fillEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    @Step("Заполнить поле 'Пароль'")
    public LoginPage fillPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    @Step("Нажать кнопку 'Войти'")
    public LoginPage clickLogin() {
        driver.findElement(loginButton).click();
        return this;
    }

    @Step("Кликнуть ссылку 'Зарегистрироваться'")
    public RegisterPage clickRegisterLink() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(registerLink))
                .click();
        return new RegisterPage(driver);
    }

    @Step("Нажать ссылку 'Восстановить пароль'")
    public RestorePasswordPage clickRestorePasswordLink() {
        driver.findElement(restorePasswordLink).click();
        return new RestorePasswordPage(driver);
    }

    @Step("Проверить отображение страницы входа")
    public boolean isLoginPageDisplayed() {
        return driver.findElement(loginButton).isDisplayed();
    }

    @Step("Дождаться загрузки страницы входа")
    public void waitForLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
    }

    public boolean isOrderButtonDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//button[text()='Оформить заказ']")
                    ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
