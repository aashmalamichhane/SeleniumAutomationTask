package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;

public class Utilities {
    public static final int Implicit_Wait_time = 10;
    public static final int Page_Load_time = 10;

    public static String captureScreenshot(WebDriver driver, String testName) throws IOException {
        String parentPath="test-output/ExtentReports/" ;
        String screenShotPath= "Screenshots/"+testName+".png";
        String fileFullPath = parentPath + screenShotPath;
        FileUtils.initializeFile(fileFullPath);
        File srcScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        try {
            FileHandler.copy(srcScreenshot,new File(fileFullPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return screenShotPath;
    }
}
