package pages;

import helpers.WebDriverSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage {

    @FindBy(css = "#order_step")
    WebElement stepsTitle;

    @FindBy(className = "cart_item")
    List<WebElement> cartProducts;

    @FindBy(css = ".price > .price")
    List<WebElement> allProductsPrices;

    @FindBy(css = ".cart_description > .product-name > a")
    List<WebElement> allProductsNames;

    @FindBy(css = "#total_product")
    WebElement totalProductsValue;

    @FindBy(css = ".icon-trash")
    List<WebElement> trashIcons;

    private static WebDriver driver;

    public CartPage() {
        this.driver = WebDriverSingleton.getInstance().getDriver();
        PageFactory.initElements(driver, this);
    }

    public int countProductQuantity() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(stepsTitle));
        return cartProducts.size();
    }

    public Double countTotalProductsPrice() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(stepsTitle));
        double productsPriceSum = 0;
        for (WebElement productPrice: allProductsPrices) {
            productsPriceSum += Double.valueOf(productPrice.getText().substring(1));
        }
        return productsPriceSum;
    }

    public List<String> getProductNames() {
        return allProductsNames.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public Double getTotalProductsValueFromTable() {
        return Double.valueOf(totalProductsValue.getText().substring(1));
    }

    public CartPage clearCart() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(stepsTitle));
        for (WebElement trash: trashIcons) {
            trash.click();
        }
        return new CartPage();
    }

}
