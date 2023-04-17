package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import static utils.UrlConstants.*;

public class LoginPage extends BasePage {

    private static final String SUCCESS_LOGIN_MESSAGE = "**Successful Login**";

    @FindBy(css = "[name='username']")
    private WebElement username;

    @FindBy(css = "[name='password']")
    private WebElement password;

    @FindBy(css = "[value='Test Login']")
    private WebElement loginButton;

    @FindBy(css = "[color='#008000']")
    private WebElement successMessage;

    public LoginPage open() {
        driver.get(BASE_URL + LOGIN_PAGE);
        return this;
    }

    public void login(String username, String password) {
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        this.loginButton.click();

        Assert.assertTrue(this.successMessage.getText().contains(SUCCESS_LOGIN_MESSAGE), "Login was not successful");
    }
}
