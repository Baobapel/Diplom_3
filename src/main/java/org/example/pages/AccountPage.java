package org.example.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountPage {
    private final WebDriver driver;

    // Локаторы
    private final By logoutButton = By.xpath("//button[contains(text(), 'Выход')]");
    private final By constructorLink = By.xpath("//*[@id='root']/div/header/nav/ul/li[1]/a");
    private final By logo = By.xpath("//div[contains(@class, 'AppHeader_header__logo')]");


    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public By getLogoutButtonLocator() {
        return logoutButton;
    }


    @Step("Нажать кнопку 'Выход'")
    public void clickLogout() {
        driver.findElement(logoutButton).click();
        new LoginPage(driver);
    }

    @Step("Перейти в конструктор")
    public void navigateToConstructor() {
        driver.findElement(constructorLink).click();
        new MainPage(driver);
    }

    @Step("Вернуться в конструктор через логотип")
    public void navigateToConstructorViaLogo() {
        driver.findElement(logo).click();
        new MainPage(driver);
    }

    @Step("Проверить видимость кнопки 'Выход'")
    public boolean isLogoutButtonVisible() {
        return driver.findElement(logoutButton).isDisplayed();
    }



}

