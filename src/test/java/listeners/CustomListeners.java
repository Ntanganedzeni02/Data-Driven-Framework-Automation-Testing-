package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import utilities.TestUtil;

import java.io.IOException;

public class CustomListeners implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite Started");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite Finished");
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test Result Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        System.setProperty("org.uncommons.reportng.escape-output", "false");

        String screenshotPath = TestUtil.captureScreenshot(result.getName());

        Reporter.log("Test Failed: " + result.getName());
        Reporter.log("<br>");

        Reporter.log("<a target=\"_blank\" href=\"" + screenshotPath + "\">View Screenshot</a>");
        Reporter.log("<br>");

        Reporter.log("<a target=\"_blank\" href=\"" + screenshotPath + "\">" +
                "<img src=\"" + screenshotPath + "\" height=\"200\" width=\"200\"/>" +
                "</a>");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getName());
    }
}