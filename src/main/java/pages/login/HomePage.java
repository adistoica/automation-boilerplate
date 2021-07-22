package pages.login;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;

import static utils.UrlConstants.BASE_URL;

public class HomePage extends BasePage {

    @FindBy(css = "[href='login.php']")
    private WebElement loginLink;

    @FindBy(css = "[href='addauser.php']")
    private WebElement addUserLink;

    public HomePage open() {
        driver.get(BASE_URL);
        return this;
    }

    public void clickLogin() {
        loginLink.click();
    }

    public void clickRegister() {
        addUserLink.click();
    }

    public String getPageUrl() {
        return driver.getCurrentUrl();
    }
}
