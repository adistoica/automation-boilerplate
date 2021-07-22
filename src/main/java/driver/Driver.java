package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Driver {
    private static final String BROWSER_CONFIG_FILE = "browser.properties";
    private static final int PAGE_TIME_OUT = 30;
    private static WebDriver driver;

    private Driver() {
    }

    private static WebDriver init() {

        // read browser name from 'resources/browser.properties'
        Properties appProps = new Properties();
        InputStream rootPath = Thread.currentThread().getContextClassLoader().getResourceAsStream(BROWSER_CONFIG_FILE);
        try {
            appProps.load(rootPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String browser = appProps.getProperty("browser").toLowerCase();

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--ignore-certificate-errors");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                // option.addArguments("--headless");

                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new EdgeDriver(edgeOptions);
                break;

            case "remote":
                // TODO: handle remote Webdriver
                break;

            default:
                throw new InvalidArgumentException("No browser was selected!");
        }

        driver.manage().timeouts().implicitlyWait(PAGE_TIME_OUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        return driver;
    }

    public static WebDriver getDriver() {
        return (driver == null) ? init() : driver;
    }
}
