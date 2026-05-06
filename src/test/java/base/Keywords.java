package base;

import listeners.CustomListeners;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import utilities.TestUtil;

import java.time.Duration;

public class Keywords extends TestBase {

    public void click(String locator) {
        try {
            WebElement element = getElement(locator);
            element.click();

            CustomListeners.extentTest.get()
                    .info("Clicked on: " + locator);

        } catch (Exception e) {
            failStep("Click failed on: " + locator, e);
        }
    }

    public void type(String locator, String value) {
        try {
            WebElement element = getElement(locator);
            element.clear();
            element.sendKeys(value);

            CustomListeners.extentTest.get()
                    .info("Typed '" + value + "' into: " + locator);

        } catch (Exception e) {
            failStep("Typing failed on: " + locator, e);
        }
    }

    public void waitForElement(String locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(getElement(locator)));

            CustomListeners.extentTest.get()
                    .info("Waited for element: " + locator);

        } catch (Exception e) {
            failStep("Wait failed for: " + locator, e);
        }
    }

    private WebElement getElement(String locator) {
        return driver.findElement(By.cssSelector(OR.getProperty(locator)));
    }

    private void failStep(String message, Exception e) {

        CustomListeners.extentTest.get().fail(message);

        try {
            String path = TestUtil.captureScreenshot("FAIL_" + System.currentTimeMillis());
            CustomListeners.extentTest.get().addScreenCaptureFromPath(path);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        throw new RuntimeException(message, e);
    }
}