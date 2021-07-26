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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Driver {

    public static final String REMOTE_WEBDRIVER_URL = "http://192.168.1.217:4444/wd/hub";

    private static final String BROWSER_CONFIG_FILE = "browser.properties";
    private static final int PAGE_TIME_OUT = 30;
    private static WebDriver driver;

    private static final Logger log = LoggerFactory.getLogger(Driver.class);

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
        String headlessMode = appProps.getProperty("headless").toLowerCase();

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--ignore-certificate-errors");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");

                if (headlessMode.equals("true")) {
                    chromeOptions.addArguments("--headless");
                }

                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headlessMode.equals("true")) {
                    firefoxOptions.addArguments("-headless");
                }

                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new EdgeDriver(edgeOptions);
                break;

            case "safari":
                driver = new SafariDriver();
                break;

            case "remote":
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("chrome");
                try {
                    driver = new RemoteWebDriver(new URL(REMOTE_WEBDRIVER_URL), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                log.info(String.format("Using Remote Webdriver @ %s", REMOTE_WEBDRIVER_URL));

                break;

            default:
                throw new InvalidArgumentException("No browser was selected in the configuration file!");
        }

        driver.manage().timeouts().implicitlyWait(PAGE_TIME_OUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        return driver;
    }

    public static WebDriver getDriver() {
        return (driver == null) ? init() : driver;
    }
}
