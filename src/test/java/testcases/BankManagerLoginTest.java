package testcases;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {

    @Test
    public void LoginAsBankManager() throws InterruptedException {
        driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
        Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))));

    }
}
