package automation.smoke;

import automation.BaseTest;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Override
    public void init() {
    }

    @Test(description = "Test login with valid credentials", priority = 1)
    public void testLoginWithValidCredentials() {
        loginPage.open();
        loginPage.login("username", "password");
    }
}
