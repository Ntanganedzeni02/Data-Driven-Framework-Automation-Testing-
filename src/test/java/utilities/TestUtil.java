package utilities;

import base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class TestUtil extends TestBase {

    public static String captureScreenshot(String testName) {

        String screenshotDir = System.getProperty("user.dir") + "/target/screenshots/";

        String screenshotName = testName + "_" + System.currentTimeMillis() + ".png";

        String fullPath = screenshotDir + screenshotName;

        try {
            File dir = new File(screenshotDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(fullPath));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return fullPath;
    }

    @DataProvider(name = "dp")
    public Object[][] getData(Method m){

        String sheetName = m.getName();

        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        Object[][] data = new Object[rows-1][cols];

        for ( int rowNum = 2; rowNum <= rows; rowNum++ ) {
            for ( int colNum = 0; colNum < cols; colNum++ ) {

                data[rowNum -2][colNum]=excel.getCellData(sheetName, colNum, rowNum);
            }
        }
        return data;
    }
}