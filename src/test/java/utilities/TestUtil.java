package utilities;

import base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

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
}