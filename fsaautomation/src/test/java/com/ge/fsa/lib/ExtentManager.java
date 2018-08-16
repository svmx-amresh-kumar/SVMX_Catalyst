/**
 * Custom Extent Report manager
 */
package com.ge.fsa.lib;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.appium.java_client.AppiumDriver;

/**
 * Usage:
 * 
 * Initialize in a before method :
 * ExtentManager.logger(result.getMethod().getRealClass().getSimpleName());
 * 
 * Flush in a after method and in test listener if required
 * ExtentManager.extent.flush();
 * 
 * Use in test case as follows ExtentManager.logger.pass("pass");
 * ExtentManager.logger.fail("details",MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.getScreenshot()).build());
 * ExtentManager.logger.log(Status.FAIL,"Failed"); assertTrue(1<0, "Failed");
 * 
 *
 */
public class ExtentManager {
	//All variables need to be global here as they will be used across all files
	public static ExtentReports extent = null;
	public static ExtentTest logger = null;
	public static AppiumDriver localDriver = null;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
    public static String sReportName = LocalDateTime.now().format(formatter)+ "_report.html";
	public static String sReportPath = System.getProperty("user.dir") + "/ExtentReports/";

	/**
	 * This will create return/get a instance of the ExtentReports and we pass the driver from baselib class here for screenshots
	 * @param driver
	 * @return
	 * @throws IOException 
	 */
	public static ExtentReports getInstance(AppiumDriver driver) throws IOException {
		File file = new File(sReportPath);
		file.mkdir();
		if (extent == null)
			createInstance(sReportPath+sReportName);
		localDriver = driver;
		return extent;
	}

	/**
	 * Create instance of Extent Reports
	 * @param fileName
	 * @return
	 */
	public static ExtentReports createInstance(String fileName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(fileName);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		return extent;
	}

	/**
	 * This will create the testname and log all test data in that particular testname
	 * @param stestName
	 * @return
	 */
	public static ExtentTest logger(String stestName) {

		logger = extent.createTest(stestName);

		return logger;

	}

	/**
	 * Method to get a screenshot, relies on the driver object for capturing the screen shots
	 * @return
	 */
	public static String getScreenshot() {

		TakesScreenshot ts = (TakesScreenshot) localDriver;

		File src = ts.getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "/Screenshot/" + System.currentTimeMillis() + ".png";

		File destination = new File(path);

		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			System.out.println("Capture Failed " + e.getMessage());
		}

		return path;
	}

}
