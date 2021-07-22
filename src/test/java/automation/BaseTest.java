package automation;

import driver.Driver;
import io.qameta.allure.Allure;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import pages.BasePage;
import pages.login.LoginPage;
import pages.login.RegisterPage;
import pages.BasePageFactory;
import pages.login.HomePage;

import java.io.ByteArrayInputStream;

public abstract class BaseTest {
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
        Logger.getRootLogger().setLevel(Level.OFF);
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
