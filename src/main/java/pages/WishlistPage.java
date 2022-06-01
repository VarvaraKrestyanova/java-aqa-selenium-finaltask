package pages;

import helpers.WebDriverSingleton;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class WishlistPage {

    @FindBy(css = "#mywishlist")
    WebElement pageSection;

    @FindBy(css = "#block-history")
    WebElement wishlistTable;

    @FindBy(css = ".wishlist_delete a")
    WebElement deleteWishlistButton;

    @FindBy(xpath = "//h3[text()='New wishlist']")
    WebElement newWishlistTitle;

    @FindBy(css = "#name")
    WebElement wishlistNameField;

    @FindBy(css = "#submitWishlist > span")
    WebElement saveWishlistBtn;

    @FindBy(css = ".table td:nth-of-type(1) a")
    WebElement wishlistName;

    @FindBy(css = "tbody > tr")
    List<WebElement> wishlistsInTheTable;

    @FindBy(className = "bold align_center")
    WebElement qtyValue;

    private static WebDriver driver;

    public WishlistPage() {
        this.driver = WebDriverSingleton.getInstance().getDriver();
        PageFactory.initElements(driver, this);
    }

    public boolean isWishlistExist() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(pageSection));
        try {
            return wishlistTable.isDisplayed();
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    public WishlistPage deleteWishlist() {
        deleteWishlistButton.click();
        acceptAlert();
        return new WishlistPage();
    }

    private WishlistPage acceptAlert() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
        return new WishlistPage();
    }

    public WishlistPage createWishlist(String wishlistName) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(newWishlistTitle));
        wishlistNameField.sendKeys(wishlistName);
        saveWishlistBtn.click();
        return new WishlistPage();
    }

    public String getWishlistName() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(wishlistTable));
        return wishlistName.getText();
    }

    public int numberOfWishlists() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(wishlistTable));
        return wishlistsInTheTable.size();
    }

    public String productQuantity() {
        return qtyValue.getText();
    }
}
