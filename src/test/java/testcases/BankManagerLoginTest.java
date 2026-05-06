package testcases;

import base.Keywords;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BankManagerLoginTest extends Keywords {

    @Test
    public void LoginAsBankManager() {

        click("bmlBtn");
        Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))));
        log.debug("Login successful");
    }
}
