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

    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getName());
        extentTest.set(test);
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        extentTest.get().fail(result.getThrowable());

        try {
            String path = TestUtil.captureScreenshot(result.getName());
            extentTest.get().addScreenCaptureFromPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentTest test = extentTest.get();

        if (test != null) {
            test.skip("Test Skipped: " + result.getName());
        } else {
            System.out.println("ExtentTest was null for skipped test: " + result.getName());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}