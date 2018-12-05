
package com.ge.fsa.lib;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
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

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseLib {

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
	public static String sOSName = null;
	public String runMachine = null;
	

	@BeforeSuite
	public void startServer()
	{


	}
	//@BeforeClass
	public void setAPP() throws Exception
	{
		File file = new File(System.getProperty("user.dir")+"/../Executable");
		try{file.mkdir();}catch(Exception e) {System.out.println("Exception in creating Executable directory for Sahi "+e);}
		File file1 = new File(System.getProperty("user.dir")+"/ExtentReports");
		try{file1.mkdir();}catch(Exception e) {System.out.println("Exception in creating ExtentReports directory for Reports "+e);}


		//On setting RUN_MACHINE to "build" this config.properties file will be IGNORED and the config_build.properties file will be used to get data
		runMachine = GenericLib.getConfigValue(GenericLib.sConfigFile, "RUN_MACHINE").toLowerCase();

		sOSName = GenericLib.getConfigValue(GenericLib.sConfigFile, "PLATFORM_NAME").toLowerCase();

		System.out.println("OS Name = "+sOSName.toLowerCase());
		System.out.println("Running On Machine : "+runMachine);
		if(runMachine.equals("build")) {
			GenericLib.sConfigFile = System.getProperty("user.dir")+"//resources"+"//config_build.properties";
		}
		switch (sOSName) {
		case "android":
			try { //Android Drivers
				sAppPath = "/auto/SVMX_Catalyst/fsaautomation/resources/FSA_AND.apk";
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(MobileCapabilityType.APP, sAppPath);
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, GenericLib.getConfigValue(GenericLib.sConfigFile, "PLATFORM_NAME"));
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, GenericLib.getConfigValue(GenericLib.sConfigFile, "ANDROID_PLATFORM_VERSION"));
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, GenericLib.getConfigValue(GenericLib.sConfigFile, "ANDROID_DEVICE_NAME"));
				capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
				capabilities.setCapability("noReset", Boolean.parseBoolean(GenericLib.getConfigValue(GenericLib.sConfigFile, "NO_RESET")));
				//capabilities.setCapability("nativeWebTap", true);
				capabilities.setCapability("appPackage", "com.servicemaxinc.svmxfieldserviceapp");
				capabilities.setCapability("appActivity", "com.servicemaxinc.svmxfieldserviceapp.ServiceMaxMobileAndroid");
				capabilities.setCapability("autoGrantPermissions", true);
				capabilities.setCapability("locationServicesAuthorized", true);
				capabilities.setCapability("locationServicesEnabled",true);
				capabilities.setCapability("clearSystemFiles", true);
				capabilities.setCapability("newCommandTimeout", 5000);
				capabilities.setCapability("setWebContentsDebuggingEnabled", true);
				capabilities.setCapability("automationName", "uiautomator2");
				capabilities.setCapability("unicodeKeyboard", true);
				capabilities.setCapability("resetKeyboard", true);
				capabilities.setCapability("autoAcceptAlerts", true);

				driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

				ExtentManager.getInstance(driver);
				Thread.sleep(2000);	
			} catch (Exception e) {
				ExtentManager.createInstance(ExtentManager.sReportPath+ExtentManager.sReportName);
				ExtentManager.logger("BaseLib Failure "+"Running On Machine :"+runMachine);
				ExtentManager.logger.fail("Failed to LAUNCH the App "+e);
				ExtentManager.extent.flush();
				throw e;
			} 
			break;

		case "ios":

			try { //IOS Drivers

				sAppPath = GenericLib.sResources+"//"+GenericLib.getConfigValue(GenericLib.sConfigFile, "APP_NAME")+".ipa";
				app = new File(sAppPath);
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, GenericLib.getConfigValue(GenericLib.sConfigFile, "PLATFORM_NAME"));
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, GenericLib.getConfigValue(GenericLib.sConfigFile, "IOS_PLATFORM_VERSION"));
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, GenericLib.getConfigValue(GenericLib.sConfigFile, "IOS_DEVICE_NAME"));
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
				capabilities.setCapability("newCommandTimeout", 5000);
				capabilities.setCapability("autoAcceptAlerts", true);


				driver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

				ExtentManager.getInstance(driver);
				Thread.sleep(2000);	
			} catch (Exception e) {
				ExtentManager.createInstance(ExtentManager.sReportPath+ExtentManager.sReportName);
				ExtentManager.logger("BaseLib Failure"+"Running On Machine :"+runMachine);
				ExtentManager.logger.fail("Failed to LAUNCH the App "+e);
				ExtentManager.extent.flush();
				throw e;
			} 

			break;

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
	 * Launch the APP either by re-installing (false) or by re-launching existing(true) APP, by passing the sResetMode parameter as "true" or "false"
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
		System.out.println("Initialized App Start mode to = "+GenericLib.getConfigValue(GenericLib.sConfigFile, "NO_RESET"));


		try {
			setAPP();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Resetting to true always first for next execution
		GenericLib.setConfigValue(GenericLib.sConfigFile, "NO_RESET", "true");
		System.out.println("Initialized Driver ** = "+driver.toString()+"** ");
	}

	@BeforeMethod
	public void startReport(ITestResult result) {
		lauchNewApp("true");
		System.out.println(" ► ► RUNNING TEST CLASS : "+result.getMethod().getRealClass().getSimpleName());
		ExtentManager.logger(result.getMethod().getRealClass().getSimpleName());

	}

	@AfterMethod
	public void endReport(ITestResult result)
	{
		if(result.getStatus()==ITestResult.FAILURE || result.getStatus()==ITestResult.SKIP)
		{
			System.out.println(" ☯ ☯ COMPLETED TEST CLASS : "+result.getMethod().getRealClass().getSimpleName()+" STATUS : FAILED");
			if(sOSName.toLowerCase().equals("android")) {	
				Set contextNames = driver.getContextHandles();
				driver.context(contextNames.toArray()[0].toString());
			}
			String temp= ExtentManager.getScreenshot();

			try {
				ExtentManager.logger.fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println(" ☯ ☯ COMPLETED TEST CLASS : "+result.getMethod().getRealClass().getSimpleName()+" STATUS : PASSED");

		}
		ExtentManager.extent.flush();
		try{driver.quit();}catch(Exception e) {};

	}

	@AfterClass
	public void tearDownDriver()
	{

		try{driver.quit();}catch(Exception e) {};
	}

}
