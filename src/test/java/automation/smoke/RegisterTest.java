package automation.smoke;

import automation.BaseTest;
import org.testng.annotations.Test;

public class RegisterTest extends BaseTest {

    @Override
    public void init() { }

    @Test(description = "Register a new user test")
    public void testRegister() {
        registerPage.open();
        registerPage.register("username", "password");

        // check that the registered credentials are displayed on the next page
        registerPage.checkValidUserRegistration("username", "password");
    }
}
