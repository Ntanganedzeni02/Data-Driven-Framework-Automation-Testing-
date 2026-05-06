package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilities.ExcelReader;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;

public class TestBase {

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static Logger log = LogManager.getLogger(TestBase.class);

    public static ExcelReader excel;
    public static WebDriverWait wait;

    @BeforeSuite
    public void setUp() {

        try {
            FileInputStream fis = new FileInputStream(
                    System.getProperty("user.dir") + "/src/test/resources/properties/Config.properties");
            config.load(fis);
            log.info("Config loaded");

            fis = new FileInputStream(
                    System.getProperty("user.dir") + "/src/test/resources/properties/OR.properties");
            OR.load(fis);
            log.info("OR loaded");

        } catch (Exception e) {
            throw new RuntimeException("Failed to load config files", e);
        }

        excel = new ExcelReader(
                System.getProperty("user.dir") + "/src/test/resources/excel/testdata.xlsx"
        );

        String browser = config.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();

        } else if (browser.equalsIgnoreCase("ie")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        }

        driver.get(config.getProperty("testsiteurl"));
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait")))
        );

        wait = new WebDriverWait(driver,
                Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait")))
        );
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            log.info("Driver closed");
        }
    }
}