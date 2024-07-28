package utils;

import base.Base;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.*;
import java.util.Properties;

public class ExtentReporter {

    public static ExtentReports generateExtentReport() throws IOException
    {
        String extentReportPath="test-output/ExtentReports/extentReport.html";
        File extentReportFile = FileUtils.initializeFile(extentReportPath);
        ExtentReports extentReport = new ExtentReports();
        ExtentSparkReporter sparkReport = new ExtentSparkReporter(extentReportFile);

        sparkReport.config().setTheme(Theme.DARK);
        sparkReport.config().setReportName("Automaton Framework Report");
        sparkReport.config().setDocumentTitle("Automation Report Title URL");
        sparkReport.config().setTimeStampFormat("DD/MM/YYY HH:MM:SS");

        extentReport.attachReporter(sparkReport);

        //To show additional data in reports regarding automation
        Properties configProp = new Properties();
        InputStream conFile = Base.class.getClassLoader().getResourceAsStream("config/config.properties");
        configProp.load(conFile);

        extentReport.setSystemInfo("APPLICATION URL", configProp.getProperty("url"));
        extentReport.setSystemInfo("APPLICATION BROWSER NAME ", configProp.getProperty("browser"));
        extentReport.setSystemInfo("AUTOMATING USER ", System.getProperty("os.name"));

        return extentReport;
    }

}



