package helpers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class WebDriverSingleton {

    private static final String DRIVER_BROWSER = "driver.browser";
    private static final String RUN_CONFIG = "run.config";
    private static String browserName = PropertiesUtil.get(DRIVER_BROWSER);
    private static String runConfig = PropertiesUtil.get(RUN_CONFIG);

    private WebDriver driver;
    private static WebDriverSingleton instance;
    private DesiredCapabilities desiredCapabilities;

    private WebDriverSingleton() {}

    public static WebDriverSingleton getInstance() {
        if (instance == null) {
            instance = new WebDriverSingleton();
        }
        return instance;
    }

    public WebDriver getDriver() {
        
        if (driver == null) {
            if (runConfig == "locally") {
                switch (browserName) {
                    case "chrome":
                        WebDriverManager.chromedriver().setup();
                        driver = new ChromeDriver(); break;
                    case "ff":
                        WebDriverManager.firefoxdriver().setup();
                        driver = new FirefoxDriver(); break;
                }

            } else if (runConfig == "grid") {
                desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setBrowserName("chrome");
                desiredCapabilities.setPlatform(Platform.WINDOWS);
                try {
                    driver = new RemoteWebDriver(new URL("http://192.168.100.25:4444/wd/hub"), desiredCapabilities);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
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