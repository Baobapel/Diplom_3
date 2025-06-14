package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;

    // Локаторы
    private final By mainLoginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath("//*[@id='root']/div/header/nav/a/p");
    private final By orderButton = By.xpath("//*[@id='root']/div/main/section[2]/div/button");
    private final By bunsSection = By.xpath("//*[@id='root']/div/main/section[1]/div[1]/div[1]/span");
    private final By saucesSection = By.xpath("//span[text()='Соусы']/..");
    private final By fillingsSection = By.xpath("//span[text()='Начинки']/..");
    private final By activeSection = By.xpath("//div[contains(@class, 'tab_tab_type_current')]");


    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть главную страницу")
    public MainPage open() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        return this;
    }

    @Step("Кликнуть на 'Личный кабинет'")
    public LoginPage clickPersonalAccountButton() {
        driver.findElement(personalAccountButton).click();
        return new LoginPage(driver);
    }

    public boolean isOrderButtonDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(orderButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public LoginPage clickMainLoginButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(mainLoginButton))
                .click();
        return new LoginPage(driver);
    }

    @Step("Кликнуть раздел 'Булки'")
    public MainPage clickBunsSection() {
        driver.findElement(bunsSection).click();
        return this;
    }

    @Step("Кликнуть раздел 'Соусы'")
    public MainPage clickSaucesSection() {
        driver.findElement(saucesSection).click();
        return this;
    }

    @Step("Кликнуть раздел 'Начинки'")
    public MainPage clickFillingsSection() {
        driver.findElement(fillingsSection).click();
        return this;
    }

    @Step("Проверить активность раздела 'Булки'")
    public boolean isBunsSectionActive() {
        return driver.findElement(activeSection).getText().contains("Булки");
    }

    @Step("Проверить активность раздела 'Соусы'")
    public boolean isSaucesSectionActive() {
        return driver.findElement(activeSection).getText().contains("Соусы");
    }

    @Step("Проверить активность раздела 'Начинки'")
    public boolean isFillingsSectionActive() {
        return driver.findElement(activeSection).getText().contains("Начинки");
    }

}

