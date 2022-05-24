package pages;

import helpers.User;
import helpers.WebDriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AuthenticationPage {

    String STATE_OPTION_XPATH_PATTERN = "//option[.='%s']";

    @FindBy(id = "id_gender1")
    WebElement mrGenderRadioButton;

    @FindBy(id = "id_gender2")
    WebElement mrsGenderRadioButton;

    @FindBy(id = "customer_firstname")
    WebElement firstName;

    @FindBy(id = "customer_lastname")
    WebElement lastName;

    @FindBy(id = "email")
    WebElement authEmail;

    @FindBy(id = "passwd")
    WebElement pwd;

    @FindBy(id = "firstname")
    WebElement adressFirstName;

    @FindBy(id = "lastname")
    WebElement adressLastName;

    @FindBy(id = "address1")
    WebElement address;

    @FindBy(id = "city")
    WebElement city;

    @FindBy(id = "id_state")
    WebElement stateDdl;

    @FindBy(id = "postcode")
    WebElement postcode;

    @FindBy(id = "phone_mobile")
    WebElement mobilePhone;

    @FindBy(id = "alias")
    WebElement addressAlias;

    @FindBy(xpath = "//span[.='Register']")
    WebElement registerBtn;

    private static WebDriver driver;

    public AuthenticationPage() {
        this.driver = WebDriverSingleton.getInstance().getDriver();
        PageFactory.initElements(driver, this);
    }

    private AuthenticationPage fillRequiredFields(User user) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(registerBtn));

        firstName.sendKeys(user.getFirstName());
        lastName.sendKeys(user.getLastName());
        pwd.sendKeys(user.getPassword());
        address.sendKeys(user.getAdress());
        city.sendKeys(user.getCity());
        chooseState(user.getState());
        postcode.sendKeys(user.getPostCose());
        mobilePhone.sendKeys(user.getPhone());

        return new AuthenticationPage();
    }

    private AuthenticationPage chooseGender(boolean isMr) {
        if (isMr == true) {
            mrGenderRadioButton.click();
        } else {
            mrsGenderRadioButton.click();
        }
        return new AuthenticationPage();
    }

    private AuthenticationPage chooseState(String state) {
        stateDdl.click();
        String xpath = String.format(STATE_OPTION_XPATH_PATTERN, state);
        WebElement element = driver.findElement(By.xpath(xpath));
        element.click();
        return new AuthenticationPage();
    }

    private MyAccountPage pushData() {
        registerBtn.click();
        return new MyAccountPage();
    }

    public MyAccountPage enterAndRegisterData(User user) {
        fillRequiredFields(user);
        pushData();
        return new MyAccountPage();
    }

}

