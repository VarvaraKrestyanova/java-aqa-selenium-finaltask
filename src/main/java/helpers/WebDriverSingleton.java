package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverSingleton {

    private static final String DRIVER_BROWSER = "driver.browser";
    private static String browserName = PropertiesUtil.get(DRIVER_BROWSER);

    private WebDriver driver;
    private static WebDriverSingleton instance;

    private WebDriverSingleton() {}

    public static WebDriverSingleton getInstance() {
        if (instance == null) {
            instance = new WebDriverSingleton();
        }
        return instance;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            switch (browserName) {
                case "chrome": driver = new ChromeDriver();
                case "ff": driver = new FirefoxDriver();
            }
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        return driver;
    }

    public void quitDriver() {
        driver.quit();
        driver = null;
    }

}