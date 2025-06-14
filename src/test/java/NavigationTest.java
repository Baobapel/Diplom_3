
import io.qameta.allure.junit4.DisplayName;
import org.example.driver.WebDriverCreator;
import org.example.pages.*;
import org.example.utils.TestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class NavigationTest {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private AccountPage accountPage;
    private TestData testData;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = WebDriverCreator.createWebDriver();
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        accountPage = new AccountPage(driver);
        testData = new TestData(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Регистрация нового пользователя
        testData.registerUserViaApi();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Переход в личный кабинет по клику на 'Личный кабинет'")
    public void testNavigateToPersonalAccount() {
        mainPage.open()
                .clickPersonalAccountButton();

        assertTrue("Не удалось перейти на страницу входа",
                loginPage.isLoginPageDisplayed());
    }

    @Test
    @DisplayName("Переход из личного кабинета по клику на 'Конструктор'")
    public void testNavigateFromAccountToConstructor() {
        // Логин и переход в личный кабинет
        mainPage.open()
                .clickPersonalAccountButton()
                .fillEmail(testData.getEmail())
                .fillPassword(testData.getPassword())
                .clickLogin();

        // Переход в личный кабинет
        mainPage.clickPersonalAccountButton();

        // Переход в конструктор через ссылку
        accountPage.navigateToConstructor();

        assertTrue("Не удалось перейти в конструктор",
                mainPage.isOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Переход из личного кабинета на главную через логотип")
    public void testNavigateFromAccountToLogo() {
        // Логин и переход в личный кабинет
        mainPage.open()
                .clickPersonalAccountButton()
                .fillEmail(testData.getEmail())
                .fillPassword(testData.getPassword())
                .clickLogin();

        // Переход в личный кабинет
        mainPage.clickPersonalAccountButton();

        // Переход в конструктор через логотип
        accountPage.navigateToConstructorViaLogo();

        assertTrue("Не удалось перейти в конструктор через логотип",
                mainPage.isOrderButtonDisplayed());
    }

    @Test
    @DisplayName("Проверка выхода из аккаунта через личный кабинет")
    public void testSuccessfulLogout() {
        // Регистрация пользователя
        TestData.User user = testData.registerUserViaApi();

        // Вход
        mainPage.open()
                .clickMainLoginButton();
        // Вход с зарегистрированными данными
        loginPage.fillEmail(user.email)
                .fillPassword(user.password)
                .clickLogin();

        // Переход в личный кабинет
        mainPage.clickPersonalAccountButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(accountPage.getLogoutButtonLocator()));

        // Проверка наличия кнопки "Выход"
        assertTrue("Кнопка 'Выход' не отображается в личном кабинете",
                accountPage.isLogoutButtonVisible());

        // Клик на кнопку "Выход"
        accountPage.clickLogout();

        loginPage.waitForLoad();
        assertTrue("Выход не выполнен - кнопка 'Войти' не отображается",
                loginPage.isLoginPageDisplayed());
    }
}

