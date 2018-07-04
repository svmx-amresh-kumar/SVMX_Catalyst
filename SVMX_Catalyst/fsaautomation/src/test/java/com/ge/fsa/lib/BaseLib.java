/*
 *  @author lakshmibs
 */

package com.ge.fsa.lib;
import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen.ScreenshotOf;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseLib {
	{
		System.setProperty("KIRWA.reporter.config",	GenericLib.sResources+"//KIRWA.properties");

	}
	public AppiumDriver driver = null;	
	
	DesiredCapabilities capabilities = null;
	String sAppPath = null;
	File app = null;
	
	@BeforeSuite
	public void startServer()
	{

	}

	@BeforeClass
	public void setAPP() throws Exception
	{
		try { 
			sAppPath = GenericLib.sResources+"//"+GenericLib.getCongigValue(GenericLib.sConfigFile, "APP_NAME")+".ipa";
			app = new File(sAppPath);
			capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, GenericLib.getCongigValue(GenericLib.sConfigFile, "PLATFORM_NAME"));
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, GenericLib.getCongigValue(GenericLib.sConfigFile, "PLATFORM_VERSION"));
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, GenericLib.getCongigValue(GenericLib.sConfigFile, "DEVICE_NAME"));
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, GenericLib.getCongigValue(GenericLib.sConfigFile, "AUTOMATION_NAME"));
			capabilities.setCapability(MobileCapabilityType.APP, sAppPath);
			capabilities.setCapability(MobileCapabilityType.UDID, GenericLib.getCongigValue(GenericLib.sConfigFile, "UDID"));
			capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
			capabilities.setCapability(MobileCapabilityType.NO_RESET,true);
			capabilities.setCapability(MobileCapabilityType.SUPPORTS_ALERTS,true);		
			capabilities.setCapability("xcodeOrgId", GenericLib.getCongigValue(GenericLib.sConfigFile, "XCODE_ORGID"));
			capabilities.setCapability("xcodeSigningId", GenericLib.getCongigValue(GenericLib.sConfigFile, "XCODE_SIGNID"));
			capabilities.setCapability("updatedWDABundleId", GenericLib.getCongigValue(GenericLib.sConfigFile, "UPDATE_BUNDLEID"));
			capabilities.setCapability("startIWDP", true);
			capabilities.setCapability("sendKeyStrategy", "grouped");
			capabilities.setCapability("autoGrantPermissions", true);
			capabilities.setCapability("locationServicesAuthorized", true);
			capabilities.setCapability("locationServicesEnabled",true);
			capabilities.setCapability("clearSystemFiles", true);
			capabilities.setCapability("newCommandTimeout", 1000000);
			
			driver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			Thread.sleep(20000);
			NXGReports.setWebDriver(driver);
			NXGReports.addStep("App is launched successfully", LogAs.PASSED, null);
			
		} catch (Exception e) {
			NXGReports.addStep("Failed to LAUNCH the App", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			throw e;
		}   
	}   


	@AfterMethod
	public void tearDownDriver()
	{
		driver.quit();
	}

}
