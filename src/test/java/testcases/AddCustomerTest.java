package testcases;

import base.Keywords;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.TestUtil;

public class AddCustomerTest extends Keywords {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
    public void addCustomerTest(String firstname, String surname, String postcode, String alerttext) {

        click("addCustBtn");
        type("firstname", firstname);
        type("surname", surname);
        type("postcode", postcode);
        click("addBtn");

        // Wait for alert
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String actualText = alert.getText();
        log.info("Alert text: {}", actualText);

        Assert.assertTrue(actualText.contains(alerttext),
                "Expected alert to contain: " + alerttext);

        alert.accept();
    }
}