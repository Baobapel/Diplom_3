import io.qameta.allure.junit4.DisplayName;
import org.example.driver.WebDriverCreator;
import org.example.pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class ConstructorNavigationTest {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        driver = WebDriverCreator.createWebDriver();
        mainPage = new MainPage(driver).open();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Проверка перехода к разделу 'Булки'")
    public void testBunsSectionNavigation() {
        mainPage.clickFillingsSection(); // так как раздел булки активирован по умолчанию, кликаем на вкладку с соусами и только потом на булки, иначе раздел булки не определяется
        assertTrue("Раздел 'Булки' не активирован",
                mainPage.clickBunsSection().isBunsSectionActive());
    }

    @Test
    @DisplayName("Проверка перехода к разделу 'Соусы'")
    public void testSaucesSectionNavigation() {
        assertTrue("Раздел 'Соусы' не активирован",
                mainPage.clickSaucesSection().isSaucesSectionActive());
    }


    @Test
    @DisplayName("Проверка перехода к разделу 'Начинки'")
    public void testFillingsSectionNavigation() {
        assertTrue("Раздел 'Начинки' не активирован",
                mainPage.clickFillingsSection().isFillingsSectionActive());
    }
}
