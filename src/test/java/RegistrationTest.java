
import io.qameta.allure.junit4.DisplayName;
import org.example.pages.LoginPage;
import org.example.pages.MainPage;
import org.example.pages.RegisterPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.driver.WebDriverCreator;
import org.openqa.selenium.WebDriver;


import org.example.utils.TestData;
import static org.junit.Assert.assertTrue;

public class RegistrationTest {
    private WebDriver driver;
    private TestData testData;
    private String email;
    private String password;

    @Before
    public void setup() {
        driver = WebDriverCreator.createWebDriver();
        testData = new TestData(driver);
    }

    @Test
    @DisplayName("Успешная регистрация")
    public void testSuccessfulRegistration() {
        TestData.User user = testData.registerUserViaApi();
        // 1. Регистрируем нового пользователя
        testData.registerUserViaApi();
        // 2. Создаем объект LoginPage для проверки
        LoginPage loginPage = new MainPage(driver)
                .open()
                .clickMainLoginButton();
         // 3. Проверяем, что страница входа отображается
        assertTrue("Должны видеть страницу входа",
                loginPage.isLoginPageDisplayed());
    }

    @Test
    @DisplayName("Ошибка при коротком пароле")
    public void testShortPasswordError() {
        // Получаем тестовые данные
        email = testData.getEmail();
        password = "12345"; // Некорректный пароль

        // Переходим на страницу регистрации
        RegisterPage registerPage = new MainPage(driver)
                .open()
                .clickPersonalAccountButton()
                .clickRegisterLink();

        // Пытаемся зарегистрироваться с коротким паролем
        registerPage.fillName(testData.getName())
                .fillEmail(email)
                .fillPassword(password)
                .clickRegister();

        assertTrue("Должно отображаться сообщение об ошибке",
                registerPage.getErrorMessage().contains("Некорректный пароль"));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
