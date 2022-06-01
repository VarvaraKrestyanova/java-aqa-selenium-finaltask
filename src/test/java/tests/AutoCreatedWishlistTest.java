package tests;

import com.github.javafaker.Faker;
import helpers.JsonReader;
import helpers.User;
import helpers.WebDriverSingleton;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.*;
import reporting.TestListener;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestListener.class)

public class AutoCreatedWishlistTest {

    private static LoginPage loginPage;
    private static MyAccountPage myAccountPage;
    private static AuthenticationPage authenticationPage;
    private static WishlistPage wishlistPage;
    private static CatalogPage tShirtsPage;
    private static User user;
    private static Faker faker;

    @BeforeAll
    public static void setup() {
        loginPage = new LoginPage();
        user = JsonReader.readJsonData("userInfo");
        user.setEmail(faker.funnyName() + "@test.test");
    }

    @AllureId("AP-3-01")
    @Test
    @Order(1)
    public void lackOfWishlistsForNewUserTest() {
        System.out.println(user.getEmail());
        authenticationPage = loginPage.createNewAccount(user.getEmail());
        myAccountPage = authenticationPage.enterAndRegisterData(user);
        wishlistPage = myAccountPage.openWishlists();
        assertFalse(wishlistPage.isWishlistExist(), "New user has wishlists");
    }

    @AllureId("AP-3-02")
    @Test
    @Order(2)
    public void addToAutoCreatedWishlistTest2() {
        System.out.println(user.getEmail());
        loginPage.logIn(user.getEmail(), user.getPassword());
        tShirtsPage = myAccountPage.openCatalogPage("T-Shirts");
        tShirtsPage.addProductToWishlist();
        myAccountPage.clickOnUsername();
        myAccountPage.openWishlists();
        assertTrue(wishlistPage.isWishlistExist(), "Wishlist is not auto-created");
    }

    @AfterAll
    public static void cleanup() {
        wishlistPage.deleteWishlist();
        WebDriverSingleton.getInstance().quitDriver();
    }

}
