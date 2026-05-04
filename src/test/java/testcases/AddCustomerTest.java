package testcases;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddCustomerTest extends TestBase {

    @Test (dataProvider = "getData")
    public void AddCustomer( String firstname, String surname, String postcode, String alerttext) {
        driver.findElement(By.cssSelector(OR.getProperty("addCustBtn"))).click();
        driver.findElement(By.cssSelector(OR.getProperty("firstname"))).sendKeys(firstname);
        driver.findElement(By.cssSelector(OR.getProperty("surname"))).sendKeys(surname);
        driver.findElement(By.cssSelector(OR.getProperty("postcode"))).sendKeys(postcode);
        driver.findElement(By.cssSelector(OR.getProperty("addBtn"))).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        Assert.assertTrue(alert.getText().contains(alerttext));
        alert.accept();

    }

    @DataProvider
    public Object[][] getData()  {
        String sheetName = "AddCustomerTest";
        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        Object[][] data = new Object[rows-1][cols];

        for (int rowNum = 2; rowNum <= rows; rowNum++) {
            for (int colNum = 0; colNum < cols; colNum++) {

                //Data[0][0]
                data[rowNum-2][colNum] = excel.getCellData(sheetName,colNum, rowNum);
            }
        }
        return data;

    }
}
