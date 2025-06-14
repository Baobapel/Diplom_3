package org.example.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RestorePasswordPage {
    private final WebDriver driver;

    private final By loginLink = By.xpath("//a[text()='Войти']");


    public RestorePasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажать ссылку 'Войти'")
    public LoginPage clickLoginLink() {
        driver.findElement(loginLink).click();
        return new LoginPage(driver);
    }
}
