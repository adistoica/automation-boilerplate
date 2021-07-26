package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static utils.UrlConstants.BASE_URL;
import static utils.UrlConstants.ADD_USER_PAGE;

import org.testng.Assert;
import utils.UrlConstants;

public class RegisterPage extends BasePage {

    @FindBy(css = "[name='username']")
    private WebElement username;

    @FindBy(css = "[name='password']")
    private WebElement password;

    @FindBy(css = "[value='save']")
    private WebElement submitButton;

    @FindBy(xpath = "//blockquote[2]")
    private WebElement mainContentContainer;

    public RegisterPage open() {
        driver.get(BASE_URL + ADD_USER_PAGE);
        return this;
    }

    public void register(String name, String password) {
        this.username.sendKeys(name);
        this.password.sendKeys(password);
        this.submitButton.click();
    }

    public void checkValidUserRegistration(String name, String password) {
        // check that the current Url changed after submitting the registration form
        Assert.assertTrue(driver.getCurrentUrl().contains(UrlConstants.SAVE_USER_PAGE));

        Assert.assertTrue(mainContentContainer.getText().contains(name));
        Assert.assertTrue(mainContentContainer.getText().contains(password));
    }
}
