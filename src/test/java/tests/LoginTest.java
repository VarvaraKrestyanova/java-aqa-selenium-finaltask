package tests;

import helpers.JsonReader;
import helpers.PropertiesUtil;
import helpers.User;
import helpers.WebDriverSingleton;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.AuthenticationPage;
import pages.LoginPage;
import pages.MyAccountPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    private static final String EXISTING_EMAIL = "user.mail";
    private static final String EXISTING_PWD = "user.password";
    private static String existingUserMail = PropertiesUtil.get(EXISTING_EMAIL);
    private static String existingUserPassword = PropertiesUtil.get(EXISTING_PWD);

    private static LoginPage loginPage;
    private static MyAccountPage myAccountPage;
    private static User user;

    @BeforeAll
    public static void setup() {
        loginPage = new LoginPage();
        myAccountPage = new MyAccountPage();
        user = JsonReader.readJsonData("userInfo");
    }

    @AllureId("AP-1-02")
    @Test
    public void LogInTest() {
        loginPage.logIn(existingUserMail, existingUserPassword);
        assertTrue(myAccountPage.isAccountCreated(user.getFirstName(), user.getLastName()), "User is not logged in");
    }

    @AllureId("AP-2-02")
    @Test
    public void LogInWithWrongPwdTest() {
        loginPage.logIn(existingUserMail, "wrongPwd");
        assertEquals("Invalid password.", loginPage.getSignInErrorMessage(),
                "There is no error message");
    }

    @AfterAll
    public static void cleanup() {
        WebDriverSingleton.getInstance().quitDriver();
    }

}
