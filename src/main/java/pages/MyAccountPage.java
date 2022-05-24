package pages;

import helpers.User;
import helpers.WebDriverSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyAccountPage {

    @FindBy(css = ".account > span")
    WebElement userName;

    @FindBy()

    private static WebDriver driver;

    public MyAccountPage() {
        this.driver = WebDriverSingleton.getInstance().getDriver();
        PageFactory.initElements(driver, this);
    }

    private String getAccountUserName() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(userName));
        return userName.getText();
    }

    public boolean isAccountCreated(User user) {
        String fullUserName = user.getFirstName() + " " + user.getLastName();
        return getAccountUserName().equals(fullUserName);
    }

}
