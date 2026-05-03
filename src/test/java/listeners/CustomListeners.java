package listerners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

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
        System.out.println("Starting Test: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        Reporter.log("Capture Screenshot");
        Reporter.log("<a target=\"_blank\" href=\"C:\\Users\\Ntanga101\\Documents\\home.png\">Error Screenshot</a>");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test Skipped: " + result.getName());
    }
}