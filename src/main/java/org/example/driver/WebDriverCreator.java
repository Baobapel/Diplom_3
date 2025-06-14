package org.example.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverCreator {
    public static WebDriver createWebDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {
            case "yandex":
                return createYandexDriver();
            case "chrome":
            default:
                return createChromeDriver();
        }
    }

    //mvn test -Dbrowser=yandex
    private static WebDriver createYandexDriver() {

        String yandexBrowserPath = "C:\\Users\\bra06\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";


        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\yandexdriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.setBinary(yandexBrowserPath);
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");

        return new ChromeDriver(options);
    }
    //mvn test -Dbrowser=chrome
    private static WebDriver createChromeDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-extensions");
        return new ChromeDriver(options);
    }
}
//mvn clean test -Dbrowser=yandex -Dtest=RegistrationTest - для запуска конкретного теста отдельно