package automation;

import driver.Driver;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import pages.BasePage;
import pages.LoginPage;
import pages.RegisterPage;
import pages.BasePageFactory;
import pages.HomePage;

import java.io.ByteArrayInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseTest {
    private static final Logger LOG = LogManager.getLogger(BaseTest.class);

    protected HomePage homePage;
    protected RegisterPage registerPage;
    protected LoginPage loginPage;

    protected WebDriver driver = Driver.getDriver();

    public abstract void init();

    protected <T extends BasePage> T createInstance(Class<T> page) {
        return BasePageFactory.createInstance(driver, page);
    }

    @BeforeClass
    public void setup() {
        init();

        // instantiate POM pages
        homePage = createInstance(HomePage.class);
        registerPage = createInstance(RegisterPage.class);
        loginPage = createInstance(LoginPage.class);
    }

    @AfterMethod
    public void assessTestStatus(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            Allure.addAttachment("Step screenshot: ", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        } else if (result.getStatus() == ITestResult.SKIP) {
            Allure.addAttachment("Step screenshot: ", new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            // do nothing
        }
    }

    @AfterSuite
    public void tearDown() {
        driver.quit();
    }
}
