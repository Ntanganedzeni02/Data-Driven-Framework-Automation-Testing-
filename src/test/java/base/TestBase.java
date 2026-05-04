package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;


public class TestBase {

    //Webdriver-done
    //Properties-done
    //logs- log4j, .log, log4j2.properties - done
    //extentReport-
    //DB
    //excel - done
    //Mail
    //ReportNG,extentReport
    //Jenkins

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static Logger log = LogManager.getLogger(TestBase.class);
    public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testdata.xlsx");
    public static WebDriverWait wait;

    @BeforeSuite
    public void setUp() {
        if (driver == null) {
            //FileInputStream fis;
            try {
                fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                config.load(fis);
                log.debug("config file loaded");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                OR.load(fis);
                log.info("OR loaded");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (config.getProperty("browser").equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();

        } else if (config.getProperty("browser").equals("firefox")) {
            driver = new FirefoxDriver();

        } else if (config.getProperty("browser").equals("ie")) {
            driver = new InternetExplorerDriver();
        }

        driver.get(config.getProperty("testsiteurl"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
        wait = new WebDriverWait(driver,Duration.ofSeconds(10));

    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;

        }
        catch (NoSuchElementException ex) {
            return false;
        }
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
