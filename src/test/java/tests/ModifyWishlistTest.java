package tests;

import com.github.javafaker.Faker;
import helpers.JsonReader;
import helpers.PropertiesUtil;
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

public class ModifyWishlistTest {

    private static final String EXISTING_EMAIL = "user.mail";
    private static final String EXISTING_PWD = "user.password";
    private static String existingUserMail = PropertiesUtil.get(EXISTING_EMAIL);
    private static String existingUserPassword = PropertiesUtil.get(EXISTING_PWD);

    private static LoginPage loginPage;
    private static MyAccountPage myAccountPage;
    private static AuthenticationPage authenticationPage;
    private static WishlistPage wishlistPage;
    private static CatalogPage catalogPage;
    private static User user;
    private static Faker faker;
    private static String wishlistName;

    @BeforeAll
    public static void setup() {
        loginPage = new LoginPage();
        user = JsonReader.readJsonData("userInfo");
        wishlistName = faker.funnyName().name();
    }

    @AllureId("AP-4-01")
    @Test
    @Order(1)
    public void createWishlist() {
        myAccountPage = loginPage.logIn(existingUserMail, existingUserPassword);
        wishlistPage = myAccountPage.openWishlists();
        wishlistPage.createWishlist(wishlistName);
        assertAll("New wishlist",
                () -> assertTrue(wishlistPage.isWishlistExist(), "Wishlist is not created"),
                () -> assertEquals(wishlistName, wishlistPage.getWishlistName(), "Wishlist name is not same as entered")
        );
    }

    @AllureId("AP-4-02")
    @Test
    @Order(2)
    public void abilityToModifyWishlist() {
        myAccountPage = loginPage.logIn(existingUserMail, existingUserPassword);
        catalogPage = myAccountPage.openCatalogPage("T-Shirts");
        catalogPage.addProductToWishlist();
        myAccountPage.clickOnUsername();
        myAccountPage.openWishlists();
        wishlistPage = myAccountPage.openWishlists();
        assertAll("Modified wishlist",
                () -> assertEquals(1, wishlistPage.numberOfWishlists(), "Number of wishlist is not 1"),
                () -> assertEquals("1", wishlistPage.productQuantity(), "Number of products in wishlist is not equal 1")
        );
    }

    @AfterAll
    public static void cleanup() {
        wishlistPage.deleteWishlist();
        WebDriverSingleton.getInstance().quitDriver();
    }

}
