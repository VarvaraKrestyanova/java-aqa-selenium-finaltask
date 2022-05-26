package pages;

import helpers.WebDriverSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(xpath = "//input[@id='email_create']")
    WebElement newEmailField;

    @FindBy(css = "#SubmitCreate > span")
    WebElement createAnAccountBtn;

    @FindBy(css = "#create_account_error > ol > li")
    WebElement signUpErrorMessage;

    @FindBy(css = ".alert > ol > li")
    WebElement signInErrorMessage;

    @FindBy(css = "#email")
    WebElement signInEmailField;

    @FindBy(css = "#passwd")
    WebElement signInPwdField;

    @FindBy(css = "#SubmitLogin")
    WebElement signInBtn;

    private static WebDriver driver;

    public LoginPage() {
        this.driver = WebDriverSingleton.getInstance().getDriver();
        PageFactory.initElements(driver, this);
    }

    public AuthenticationPage createNewAccount(String email) {
        driver.navigate().to("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        newEmailField.sendKeys(email);
        createAnAccountBtn.click();
        return new AuthenticationPage();
    }

    public String getSignUpErrorMessage() {
        return signUpErrorMessage.getText();
    }

    public MyAccountPage logIn(String email, String password) {
        driver.navigate().to("http://automationpractice.com/index.php?controller=authentication&back=my-account");
        signInEmailField.sendKeys(email);
        signInPwdField.sendKeys(password);
        signInBtn.click();
        return new MyAccountPage();
    }

    public String getSignInErrorMessage() {
        return signInErrorMessage.getText();
    }

}
