package pages;

import helpers.User;
import helpers.WebDriverSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(xpath = "//input[@id='email']")
    WebElement signInEmailField;

    @FindBy(xpath = "//input[@id='email_create']")
    WebElement newEmailField;

    @FindBy(css = "#SubmitCreate > span")
    WebElement createAnAccountBtn;

    @FindBy(css = "#create_account_error > ol > li")
    WebElement signUpErrorMessage;

    private static WebDriver driver;

    public LoginPage() {
        this.driver = WebDriverSingleton.getInstance().getDriver();
        PageFactory.initElements(driver, this);
    }

    public AuthenticationPage fillNewEmailForm(String email) {
        driver.navigate().to("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        newEmailField.sendKeys(email);
        createAnAccountBtn.click();
        return new AuthenticationPage();
    }

    public String getSignUpErrorMessage() {
        return signUpErrorMessage.getText();
    }

    public LoginPage createNewAccount(User user) {
        fillNewEmailForm(user.getEmail());

        return new LoginPage();
    }

    public LoginPage fillLogInForm(String username, String password) {
        driver.navigate().to("http://automationpractice.com/index.php?controller=authentication&back=my-account");



        return new LoginPage();
    }

}
