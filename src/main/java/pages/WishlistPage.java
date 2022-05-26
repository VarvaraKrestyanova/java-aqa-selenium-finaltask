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
import java.util.NoSuchElementException;

public class WishlistPage {

    @FindBy(css = "#mywishlist")
    WebElement pageSection;

    @FindBy(css = "#block-history")
    WebElement wishlistTable;

    @FindBy(css = ".wishlist_delete a")
    WebElement deleteWishlistButton;

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
}
