package tests;

import helpers.JsonReader;
import helpers.PropertiesUtil;
import helpers.User;
import helpers.WebDriverSingleton;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.AuthenticationPage;
import pages.LoginPage;
import pages.MyAccountPage;
import reporting.TestListener;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestListener.class)

public class CreateAccountTest {

    private static final String EXISTING_EMAIL = "user.mail";
    private static String existingUserMail = PropertiesUtil.get(EXISTING_EMAIL);

    private static LoginPage loginPage;
    private static AuthenticationPage authenticationPage;
    private static MyAccountPage myAccountPage;
    private static User user;

    @BeforeAll
    public static void setup() {
        loginPage = new LoginPage();
        authenticationPage = new AuthenticationPage();
        myAccountPage = new MyAccountPage();
        user = JsonReader.readJsonData("userInfo");
    }

    @AllureId("AP-1-01")
    @Test
    public void createAccountTest() {
        loginPage.fillNewEmailForm(user.getEmail());
        authenticationPage.enterAndRegisterData(user);
        assertTrue(myAccountPage.isAccountCreated(user), "Account is not created");
    }

    @AllureId("AP-1-02")
    @Test
    public void createAccountWithExistingEmail() {
        loginPage.fillNewEmailForm(existingUserMail);
        assertEquals("An account using this email address has already been registered. Please enter a valid password or request a new one.", loginPage.getSignUpErrorMessage(),
                "There is no error message for new account with existing email");
    }

    @AllureId("AP-1-03")
    @Test
    public void createAccountWithEmptyData() {
        User user2 = JsonReader.readJsonData("user2Info");
        loginPage.fillNewEmailForm(user2.getEmail());
        authenticationPage.enterAndRegisterData(user2);
        assertTrue(authenticationPage.isErrorAvailable(), "There is no error message for new account with not filled required fields");
    }

    @AfterAll
    public static void cleanup() {
        WebDriverSingleton.getInstance().quitDriver();
    }

}
