package tests;

import com.github.javafaker.Faker;
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
import pages.WishlistPage;

import static org.junit.jupiter.api.Assertions.*;

public class AutoCreatedWishlistTest {

    private static final String EXISTING_EMAIL = "user.mail";
    private static final String EXISTING_PWD = "user.password";
    private static String existingUserMail = PropertiesUtil.get(EXISTING_EMAIL);
    private static String existingUserPassword = PropertiesUtil.get(EXISTING_PWD);

    private static LoginPage loginPage;
    private static MyAccountPage myAccountPage;
    private static AuthenticationPage authenticationPage;
    private static WishlistPage wishlistPage;
    private static User user;
    private static Faker faker;

    @BeforeAll
    public static void setup() {
        loginPage = new LoginPage();
        user = JsonReader.readJsonData("userInfo");
    }

    @AllureId("AP-1-03")
    @Test
    public void addToAutoCreatedWishlistTest() {
        authenticationPage = loginPage.createNewAccount(faker.funnyName() + "@test.test");
        authenticationPage.enterAndRegisterData(user);
        wishlistPage = myAccountPage.openWishlists();
        assertFalse(wishlistPage.isWishlistExist(), "New user has wishlists");


    }

    @AfterAll
    public static void cleanup() {
        WebDriverSingleton.getInstance().quitDriver();
        wishlistPage.deleteWishlist();
    }

}
