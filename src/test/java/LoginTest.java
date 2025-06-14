import io.qameta.allure.junit4.DisplayName;
import org.example.driver.WebDriverCreator;
import org.example.pages.*;
import org.example.utils.TestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class LoginTest {
    private WebDriver driver;
    private TestData testData;
    private String email;
    private String password;

    @Before
    public void setUp() {
        driver = WebDriverCreator.createWebDriver();
        testData = new TestData(driver);
        email = testData.getEmail();
        password = testData.getPassword();
        testData.registerUserViaApi();
    }

    @Test
    @DisplayName("Проверка входа ранее зарегистрированным пользователем через API")
    public void fullRegistrationAndLoginFlow() {
        // 1. Регистрируем пользователя через API
        TestData.User user = testData.registerUserViaApi();

        try {
            // 2. Открываем главную страницу
            MainPage mainPage = new MainPage(driver).open();

            // 3. Нажимаем "Войти в аккаунт"
            LoginPage loginPage = mainPage.clickMainLoginButton();

            // 4. Вводим данные зарегистрированного пользователя
            loginPage.fillEmail(user.email)
                    .fillPassword(user.password)
                    .clickLogin();

            // 5. Проверяем успешный вход
            assertTrue("Кнопка 'Оформить заказ' должна отображаться после входа",
                    new MainPage(driver).isOrderButtonDisplayed());
        } finally {
            // 6. Удаляем пользователя после теста
            testData.deleteUserViaApi(user.accessToken);
        }
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    public void loginViaPersonalAccount_ShouldSuccess() {
        // UI-регистрация
        email = testData.getEmail();
        password = testData.getPassword();
        testData.registerNewUser(email, password);

        // Тест входа
        boolean isLoggedIn = new MainPage(driver)
                .open()
                .clickPersonalAccountButton()
                .fillEmail(email)
                .fillPassword(password)
                .clickLogin()
                .isOrderButtonDisplayed();

        assertTrue("Вход не выполнен", isLoggedIn);
    }

    @Test
    @DisplayName("Вход через ссылку в форме регистрации")
    public void loginViaRegistrationForm() {
        // UI-регистрация
        email = testData.getEmail();
        password = testData.getPassword();
        testData.registerNewUser(email, password);

        // Тест входа
        boolean isLoggedIn = new MainPage(driver)
                .open()
                .clickPersonalAccountButton()
                .clickRegisterLink()
                .clickLoginLink()
                .fillEmail(email)
                .fillPassword(password)
                .clickLogin()
                .isOrderButtonDisplayed();

        assertTrue("Вход не выполнен", isLoggedIn);
    }

    @Test
    @DisplayName("Вход через ссылку в форме восстановления пароля")
    public void loginViaPasswordRestore() {
        // UI-регистрация
        email = testData.getEmail();
        password = testData.getPassword();
        testData.registerNewUser(email, password);

        // Тест входа
        boolean isLoggedIn = new MainPage(driver)
                .open()
                .clickPersonalAccountButton()
                .clickRestorePasswordLink()
                .clickLoginLink()
                .fillEmail(email)
                .fillPassword(password)
                .clickLogin()
                .isOrderButtonDisplayed();

        assertTrue("Вход не выполнен", isLoggedIn);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}