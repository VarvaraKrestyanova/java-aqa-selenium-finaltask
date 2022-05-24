package tests;

import helpers.JsonReader;
import helpers.PropertiesUtil;
import helpers.User;
import helpers.WebDriverSingleton;
import org.junit.jupiter.api.*;
import pages.AuthenticationPage;
import pages.LoginPage;
import pages.MyAccountPage;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void createAccountTest() {
        loginPage.fillNewEmailForm(user.getEmail());
        authenticationPage.enterAndRegisterData(user);
        assertTrue(myAccountPage.isAccountCreated(user));
    }

    @Test
    public void createAccountWithExistingEmail() {
        loginPage.fillNewEmailForm(existingUserMail);
        assertEquals("An account using this email address has already been registered. Please enter a valid password or request a new one.", loginPage.getSignUpErrorMessage());
    }

    @AfterAll
    public static void cleanup() {
        WebDriverSingleton.getInstance().quitDriver();
    }

}
