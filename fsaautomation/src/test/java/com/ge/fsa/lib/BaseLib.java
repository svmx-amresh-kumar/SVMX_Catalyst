
package com.ge.fsa.lib;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.ge.fsa.pageobjects.CalendarPO;
import com.ge.fsa.pageobjects.ChecklistPO;
import com.ge.fsa.pageobjects.CommonsPO;
import com.ge.fsa.pageobjects.CreateNewPO;
import com.ge.fsa.pageobjects.ExploreSearchPO;
import com.ge.fsa.pageobjects.InventoryPO;
import com.ge.fsa.pageobjects.LoginHomePO;
import com.ge.fsa.pageobjects.RecentItemsPO;
import com.ge.fsa.pageobjects.TasksPO;
import com.ge.fsa.pageobjects.ToolsPO;
import com.ge.fsa.pageobjects.WorkOrderPO;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen;
import com.kirwa.nxgreport.selenium.reports.CaptureScreen.ScreenshotOf;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseLib {
	{
		System.setProperty("KIRWA.reporter.config",	GenericLib.sResources+"//KIRWA.properties");

	}
	public AppiumDriver driver = null;
	public GenericLib genericLib = null;
	public RestServices restServices = null;
	public LoginHomePO loginHomePo = null;
	public ExploreSearchPO exploreSearchPo = null;
	public WorkOrderPO workOrderPo = null;
	public CommonsPO commonsPo = null;
	public ChecklistPO checklistPo = null;
	public ToolsPO toolsPo = null;
	public CreateNewPO createNewPO=null;
	public RecentItemsPO recenItemsPO = null;
	public CalendarPO calendarPO = null;
	public TasksPO tasksPo = null;
	public InventoryPO inventoryPo = null;
	
	
	DesiredCapabilities capabilities = null;
	public String sAppPath = null;
	File app = null;

	@BeforeSuite
	public void startServer()
	{

	}
	@BeforeClass
	public void setAPP() throws Exception
	{

		try { 
			//Resetting to true always first
			GenericLib.setConfigValue(GenericLib.sConfigFile, "NO_RESET", "true");

			sAppPath = GenericLib.sResources+"//"+GenericLib.getConfigValue(GenericLib.sConfigFile, "APP_NAME")+".ipa";
			app = new File(sAppPath);
			capabilities = new DesiredCapabilities();
			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, GenericLib.getConfigValue(GenericLib.sConfigFile, "PLATFORM_NAME"));
			capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, GenericLib.getConfigValue(GenericLib.sConfigFile, "PLATFORM_VERSION"));
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, GenericLib.getConfigValue(GenericLib.sConfigFile, "DEVICE_NAME"));
			capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, GenericLib.getConfigValue(GenericLib.sConfigFile, "AUTOMATION_NAME"));
			capabilities.setCapability(MobileCapabilityType.APP, sAppPath);
			capabilities.setCapability(MobileCapabilityType.UDID, GenericLib.getConfigValue(GenericLib.sConfigFile, "UDID"));
			capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
			capabilities.setCapability(MobileCapabilityType.NO_RESET,Boolean.parseBoolean(GenericLib.getConfigValue(GenericLib.sConfigFile, "NO_RESET")));
			capabilities.setCapability(MobileCapabilityType.SUPPORTS_ALERTS,true);		
			capabilities.setCapability("xcodeOrgId", GenericLib.getConfigValue(GenericLib.sConfigFile, "XCODE_ORGID"));
			capabilities.setCapability("xcodeSigningId", GenericLib.getConfigValue(GenericLib.sConfigFile, "XCODE_SIGNID"));
			capabilities.setCapability("updatedWDABundleId", GenericLib.getConfigValue(GenericLib.sConfigFile, "UPDATE_BUNDLEID"));
			capabilities.setCapability("startIWDP", true);
			capabilities.setCapability("sendKeyStrategy", "grouped");
			capabilities.setCapability("autoGrantPermissions", true);
			capabilities.setCapability("locationServicesAuthorized", true);
			capabilities.setCapability("locationServicesEnabled",true);
			capabilities.setCapability("clearSystemFiles", true);
			capabilities.setCapability("newCommandTimeout", 1000000);
			
			driver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
			
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			
			ExtentManager.getInstance(driver);
			Thread.sleep(2000);
			NXGReports.setWebDriver(driver);
			NXGReports.addStep("App is launched successfully", LogAs.PASSED, null);
			
		} catch (Exception e) {
			ExtentManager.createInstance(ExtentManager.sReportPath);
			 ExtentManager.logger("BaseLib Failure");
			 ExtentManager.logger.fail("Failed to LAUNCH the App "+e);
			 ExtentManager.extent.flush();
			NXGReports.addStep("Failed to LAUNCH the App", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			throw e;
		} 
		
		
	
		
		genericLib=new GenericLib();
		restServices = new RestServices();
		loginHomePo = new LoginHomePO(driver);
		exploreSearchPo = new ExploreSearchPO(driver);	
		workOrderPo = new WorkOrderPO(driver);
		toolsPo = new ToolsPO(driver);	
		commonsPo = new CommonsPO(driver);
		restServices = new RestServices();
		createNewPO = new CreateNewPO(driver);
		recenItemsPO = new RecentItemsPO(driver);
		calendarPO = new CalendarPO(driver);
		tasksPo = new TasksPO(driver);
		checklistPo = new ChecklistPO(driver);
		inventoryPo = new InventoryPO(driver);
		

	}   

	/**
	 * Launch the app either by re-installing (false) or by re-launching existing(true) app, by passing the sResetMode parameter as true or false
	 * 
	 * @param sResetMode
	 * @throws Exception
	 */
	public void lauchNewApp(String sResetMode) {
//		System.out.println("App removed");
//		driver.removeApp(GenericLib.getCongigValue(GenericLib.sConfigFile, "UPDATE_BUNDLEID"));
//		System.out.println("App removed");
//		driver.installApp(sAppPath);
//		System.out.println("App installed");

		//Installing fresh
				GenericLib.setConfigValue(GenericLib.sConfigFile, "NO_RESET", sResetMode);
				System.out.println("Set App Start mode "+GenericLib.getConfigValue(GenericLib.sConfigFile, "NO_RESET"));
		
				
				try {
					setAPP();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	@BeforeMethod
	public void startReport(ITestResult result) {
		 ExtentManager.logger(result.getMethod().getRealClass().getSimpleName());
		 
	}
	
	@AfterMethod
	public void endReport(ITestResult result)
	{
		if(result.getStatus()==ITestResult.FAILURE || result.getStatus()==ITestResult.SKIP)
		{
			String temp= ExtentManager.getScreenshot();
			
			try {
				ExtentManager.logger.fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		 ExtentManager.extent.flush();
			
	}
	
	@AfterClass
	public void tearDownDriver()
	{
		
		driver.quit();
	}

}
