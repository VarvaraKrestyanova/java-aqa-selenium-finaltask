package pages;

import helpers.WebDriverSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CatalogPage {

    @FindBy(xpath = "//span[.='Add to cart'][1]")
    WebElement addToCardFirstBtn;

    @FindBy(css = ".icon-ok")
    WebElement toCardOkCheckMark;

    @FindBy(css = ".cross")
    WebElement closeToCardConfirmationPopupBtn;

    @FindBy(xpath = "//a[contains(.,'Add to Wishlist')][1]")
    WebElement addFirstToWishlistBtn;

    @FindBy(css = ".fancybox-item")
    WebElement toWishlistConfirmationPopupCloseBtn;

    @FindBy(css = ".layer_cart_product_info > .product-name")
    WebElement addedToCartProductName;

    private static WebDriver driver;

    public CatalogPage() {
        this.driver = WebDriverSingleton.getInstance().getDriver();
        PageFactory.initElements(driver, this);
    }

    public CatalogPage addProductToWishlist() {
        addFirstToWishlistBtn.click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(toWishlistConfirmationPopupCloseBtn));
        toWishlistConfirmationPopupCloseBtn.click();
        return new CatalogPage();
    }

    public String addProductToCart() {
        addToCardFirstBtn.click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(toCardOkCheckMark));
        String productName = addedToCartProductName.getText();
        closeToCardConfirmationPopupBtn.click();
        return productName;
    }

}
