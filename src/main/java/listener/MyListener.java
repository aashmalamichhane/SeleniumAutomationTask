package listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentReporter;
import utils.Utilities;

import java.io.IOException;

public class MyListener implements ITestListener {
    private final Logger LOG=Logger.getLogger(MyListener.class);

    ExtentReports extentReport;
    ExtentTest extentTest;
    @Override
    public void onStart(ITestContext context)
    {
        try {
            extentReport = ExtentReporter.generateExtentReport();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getName();
        extentTest = extentReport.createTest(testName);
        extentTest.log(Status.INFO, testName + "started executing");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getName();
        extentTest.log(Status.PASS , testName + "got successfully executed");
        LOG.info(testName + " test is passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {

        WebDriver driver = null;

        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        }
        catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }

        if (driver != null) {
            String destinationScreenshotPath = null;
            try {
                destinationScreenshotPath = Utilities.captureScreenshot(driver, result.getName());
            } catch (IOException e) {
                LOG.error("Screenshot File Initialization Error.");
                throw new RuntimeException(e);
            }
            extentTest.addScreenCaptureFromPath(destinationScreenshotPath);
        }
        extentTest.log(Status.INFO, result.getThrowable());
        extentTest.log(Status.FAIL, result.getName() + " got failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getName();
        extentTest.log(Status.INFO, result.getThrowable());
        extentTest.log(Status.SKIP, testName+ " got skipped");
        LOG.info(testName + " test is skipped");
        LOG.info(result.getThrowable());

    }

    @Override
    public void onFinish(ITestContext context) {
        extentReport.flush();
    }


}


