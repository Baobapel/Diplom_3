package org.example.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
    private final WebDriver driver;

    // Локаторы
    private final By nameField = By.xpath("//input[@name='name']");
    private final By emailField = By.xpath("//*[@id='root']/div/main/div/form/fieldset[2]/div/div/input");
    private final By passwordField = By.xpath("//input[@name='Пароль']");
    private final By registerButton = By.xpath("/html/body/div/div/main/div/form/button");
    private final By errorMessage = By.xpath("//p[contains(@class, 'input__error')]");
    private final By loginLink = By.xpath("//a[text()='Войти']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Заполнить поле 'Имя'")
    public RegisterPage fillName(String name) {
        driver.findElement(nameField).sendKeys(name);
        return this;
    }

    @Step("Заполнить поле 'Email'")
    public RegisterPage fillEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    @Step("Заполнить поле 'Пароль'")
    public RegisterPage fillPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    @Step("Проверить сообщение об ошибке")
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    @Step("Кликнуть ссылку 'Войти'")
    public LoginPage clickLoginLink() {
        driver.findElement(loginLink).click();
        return new LoginPage(driver);
    }

}
