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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestListener.class)

public class AddToCartTest {

    private static final String EXISTING_EMAIL = "user.mail";
    private static final String EXISTING_PWD = "user.password";
    private static String existingUserMail = PropertiesUtil.get(EXISTING_EMAIL);
    private static String existingUserPassword = PropertiesUtil.get(EXISTING_PWD);

    private static LoginPage loginPage;
    private static MyAccountPage myAccountPage;
    private static CatalogPage catalogPage;
    private static CartPage cartPage;
    private static User user;
    private static Faker faker;

    @BeforeAll
    public static void setup() {
        loginPage = new LoginPage();
        user = JsonReader.readJsonData("userInfo");
    }

    @AllureId("AP-5-01")
    @Test
    @Order(2)
    public void addProductsToCartTest() {
        List<String> productNames = new ArrayList<>();
        myAccountPage = loginPage.logIn(existingUserMail, existingUserPassword);
        catalogPage = myAccountPage.openCatalogPage("T-Shirts");
        productNames.add(catalogPage.addProductToCart());
        myAccountPage.openCatalogPage("Dresses");
        productNames.add(catalogPage.addProductToCart());
        myAccountPage.openCatalogPage("Women");
        productNames.add(catalogPage.addProductToCart());
        cartPage = myAccountPage.openCartPageFromMenu();
        assertAll("Cart products",
                () -> assertEquals(3, cartPage.countProductQuantity(), "There are not 3 cart products"),
                () -> assertEquals(cartPage.countTotalProductsPrice(), cartPage.getTotalProductsValueFromTable(), "Total price value is wrong"),
                () -> assertTrue(productNames.containsAll(cartPage.getProductNames()), "Product names are wrong")
        );
    }

    @AfterAll
    public static void cleanup() {
        cartPage.clearCart();
        WebDriverSingleton.getInstance().quitDriver();
    }

}
