package listeners;

import com.aventstack.extentreports.*;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;
import utilities.ExtentManager;
import utilities.TestUtil;

public class CustomListeners implements ITestListener {

    ExtentReports extent = ExtentManager.getExtent();
    ExtentTest test;

    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        extentTest.get().fail(result.getThrowable());

        String path = TestUtil.captureScreenshot(result.getName());

        extentTest.get().addScreenCaptureFromPath(path, result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}