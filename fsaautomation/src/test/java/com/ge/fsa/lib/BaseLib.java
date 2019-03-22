
package com.ge.fsa.lib;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.ge.fsa.phone.pageobjects.Ph_CalendarPO;
import com.ge.fsa.phone.pageobjects.Ph_ChecklistPO;
import com.ge.fsa.phone.pageobjects.Ph_ExploreSearchPO;
import com.ge.fsa.phone.pageobjects.Ph_LoginHomePO;
import com.ge.fsa.phone.pageobjects.Ph_MorePO;
import com.ge.fsa.phone.pageobjects.Ph_RecentsItemsPO;
import com.ge.fsa.phone.pageobjects.Ph_WorkOrderPO;
import com.ge.fsa.tablet.pageobjects.CalendarPO;
import com.ge.fsa.tablet.pageobjects.ChecklistPO;
import com.ge.fsa.tablet.pageobjects.CreateNewPO;
import com.ge.fsa.tablet.pageobjects.ExploreSearchPO;
import com.ge.fsa.tablet.pageobjects.InventoryPO;
import com.ge.fsa.tablet.pageobjects.LoginHomePO;
import com.ge.fsa.tablet.pageobjects.RecentItemsPO;
import com.ge.fsa.tablet.pageobjects.TasksPO;
import com.ge.fsa.tablet.pageobjects.ToolsPO;
import com.ge.fsa.tablet.pageobjects.WorkOrderPO;

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
	public CommonUtility commonsUtility = null;
	public ChecklistPO checklistPo = null;
	public ToolsPO toolsPo = null;
	public CreateNewPO createNewPO = null;
	public RecentItemsPO recenItemsPO = null;
	public CalendarPO calendarPO = null;
	public TasksPO tasksPo = null;
	public InventoryPO inventoryPo = null;
	
	//iphone
	
	public Ph_LoginHomePO ip_LoginHomePo = null;
	public Ph_MorePO ip_MorePo = null;
	public Ph_CalendarPO ip_CalendarPo = null;
	public Ph_RecentsItemsPO ip_RecentsPo = null;
	public Ph_WorkOrderPO ip_WorkOrderPo = null;
	public Ph_ExploreSearchPO ip_ExploreSearchPO = null;
	public Ph_ChecklistPO ip_ChecklistPO = null;
	
	DesiredCapabilities capabilities = null;
	public String sAppPath = null;
	File app = null;
	public static String sOSName = null;
	public static String sDeviceType =null;
	public static String sUsePropertyFile = null;
	public static String sSuiteTestName = null;
	public static String sSalesforceServerVersion = null;
	public static String sBuildNo = null;
	public static String sTestName = null;
	public static String sBaseTimeStamp = null;
	public static long lInitTimeStartMilliSec;
	public static long lInitTimeEndMilliSec;
	public static String sSelectConfigPropFile = null;
	public static String sOrgType = null;
	public static String sUDID = null;
	public static String sAndroidDeviceName = null;
	
	//Execution legends
	public static String sRunningSymbol = ">>";
	public static String sCompletedSymbol = "^^";
	public static String sRetrySymbol = "<<";
	public static String sRetryState = "";


	public long getDateDiffInMin(long lInitTimeStart, long lInitTimeEnd) {

		long diffInMillies = Math.abs(lInitTimeStart - lInitTimeEnd);
		long sDiff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return sDiff;
	}

	public String getBaseTimeStamp() {
		sBaseTimeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
		// System.out.println(sBaseTimeStamp);
		return sBaseTimeStamp;
	}

	@BeforeSuite
	public void startServer(ITestContext context) {

		// For report naming purpose, does not impact executions
		sTestName = context.getCurrentXmlTest().getClasses().toString().replaceAll("XmlClass class=", "").replaceAll("\\[\\[", "").replaceAll("\\]\\]", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("com.ge.fsa.tests.", "");
		sSuiteTestName = context.getSuite().getName();

		sSuiteTestName = sSuiteTestName.equalsIgnoreCase("Default suite") ? sTestName : sSuiteTestName;
		System.out.println("[BaseLib] Excuting SUITE : " + sSuiteTestName);
		System.out.println("[BaseLib] Excuting Tests : " + sTestName);

		// Get all jenkins parameters from env
		
		sOrgType = System.getenv("Org_Type") != null ? System.getenv("Org_Type") : "base";
		System.out.println("[BaseLib] Server Org Type = " + sOrgType);

		// Select the appropriate config file On setting USE_PROPERTY_FILE to "config_automation_build" the config_automation_build.properties file will be used to get data and config_local.properties file will be IGNORED Check if jenkins is setting the Select_Config_Properties_For_Build else uselocal
		sSelectConfigPropFile = System.getenv("Select_Config_Properties_For_Build") != null ? System.getenv("Select_Config_Properties_For_Build").toLowerCase() : GenericLib.getConfigValue(GenericLib.sConfigFile, "USE_PROPERTY_FILE").toLowerCase();
		GenericLib.sConfigFile = System.getProperty("user.dir") + "/resources" + "/" + sSelectConfigPropFile + ".properties";
		sUsePropertyFile = GenericLib.getConfigValue(GenericLib.sConfigFile, "USE_PROPERTY_FILE").toLowerCase();

		System.out.println("[BaseLib] Running On Profile : " + sUsePropertyFile);
		System.out.println("[BaseLib] Reading Config Properties From : " + GenericLib.sConfigFile);

		// Select the OS from Run_On_Platform from jenkins or local
		sOSName = System.getenv("Run_On_Platform") != null?System.getenv("Run_On_Platform").toLowerCase():GenericLib.getConfigValue(GenericLib.sConfigFile, "PLATFORM_NAME").toLowerCase();
		System.out.println("[BaseLib] OS Name = " + sOSName);

		sDeviceType = System.getenv("Device_Type") != null ? System.getenv("Device_Type") : GenericLib.getConfigValue(GenericLib.sConfigFile, "DEVICE_TYPE").toLowerCase();
		System.out.println("[BaseLib] Device Type = " + sDeviceType);
		
		// Get the build number from jenkins
		sBuildNo = System.getenv("BUILD_NUMBER") != null ? System.getenv("BUILD_NUMBER") : "local";
		System.out.println("[BaseLib] BUILD_NUMBER : " + sBuildNo);
		
		//Get UDID
		sUDID = System.getenv("UDID") != null ? System.getenv("UDID") : GenericLib.getConfigValue(GenericLib.sConfigFile, "UDID").toLowerCase();
		System.out.println("[BaseLib] UDID_IOS : " + sUDID);
		
		sAndroidDeviceName = System.getenv("ANDROID_DEVICE_NAME") != null ? System.getenv("ANDROID_DEVICE_NAME") : GenericLib.getConfigValue(GenericLib.sConfigFile, "ANDROID_DEVICE_NAME").toLowerCase();
		System.out.println("[BaseLib] ANDROID_DEVICE_NAME : " + sAndroidDeviceName);

	}

	// @BeforeClass
	public void setAPP() throws Exception {
		File file = new File(System.getProperty("user.dir") + "/../Executable");
		try {
			file.mkdir();
		} catch (Exception e) {
			System.out.println("[BaseLib] Exception in creating Executable directory for Sahi " + e);
		}
		File file1 = new File(System.getProperty("user.dir") + "/ExtentReports");
		try {
			file1.mkdir();
		} catch (Exception e) {
			System.out.println("[BaseLib] Exception in creating ExtentReports directory for Reports " + e);
		}

		switch (sOSName) {
		case "android":
			try { // Android Drivers
				sAppPath = GenericLib.sResources + "//" + GenericLib.getConfigValue(GenericLib.sConfigFile, "APP_NAME") + ".apk";
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(MobileCapabilityType.APP, sAppPath);
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, GenericLib.getConfigValue(GenericLib.sConfigFile, "PLATFORM_NAME"));
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, GenericLib.getConfigValue(GenericLib.sConfigFile, "ANDROID_PLATFORM_VERSION"));
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, GenericLib.getConfigValue(GenericLib.sConfigFile, "ANDROID_DEVICE_NAME"));
				if(sDeviceType.equalsIgnoreCase("phone")) {
					//Ignore the AutoWebview setting for phone
						System.out.println("Setting AUTO_WEBVIEW to false");
						capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, false);
						capabilities.setCapability("appPackage", "com.servicemaxinc.fsa");
						capabilities.setCapability("appActivity", "com.servicemaxinc.fsa.MainActivity");
					}else{
						capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
						capabilities.setCapability("appPackage", "com.servicemaxinc.svmxfieldserviceapp");
						capabilities.setCapability("appActivity", "com.servicemaxinc.svmxfieldserviceapp.ServiceMaxMobileAndroid");
					}
				capabilities.setCapability("noReset", Boolean.parseBoolean(GenericLib.getConfigValue(GenericLib.sConfigFile, "NO_RESET")));
				// capabilities.setCapability("nativeWebTap", true);
				
				capabilities.setCapability("autoGrantPermissions", true);
				capabilities.setCapability("locationServicesAuthorized", true);
				capabilities.setCapability("locationServicesEnabled", true);
				capabilities.setCapability("clearSystemFiles", true);
				capabilities.setCapability("newCommandTimeout", 5000);
				capabilities.setCapability("setWebContentsDebuggingEnabled", true);
				capabilities.setCapability("automationName", "uiautomator2");
				capabilities.setCapability("unicodeKeyboard", true);
				capabilities.setCapability("resetKeyboard", true);
				capabilities.setCapability("autoAcceptAlerts", true);

				driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

				Thread.sleep(2000);
			} catch (Exception e) {
				throw e;
			}
			break;

		case "ios":

			try { // IOS Drivers

				sAppPath = GenericLib.sResources + "//" + GenericLib.getConfigValue(GenericLib.sConfigFile, "APP_NAME") + ".ipa";
				app = new File(sAppPath);
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, GenericLib.getConfigValue(GenericLib.sConfigFile, "PLATFORM_NAME"));
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, GenericLib.getConfigValue(GenericLib.sConfigFile, "IOS_PLATFORM_VERSION"));
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, GenericLib.getConfigValue(GenericLib.sConfigFile, "IOS_DEVICE_NAME"));
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, GenericLib.getConfigValue(GenericLib.sConfigFile, "AUTOMATION_NAME"));
				capabilities.setCapability(MobileCapabilityType.APP, sAppPath);
				capabilities.setCapability(MobileCapabilityType.UDID, GenericLib.getConfigValue(GenericLib.sConfigFile, "UDID"));
				if(sDeviceType.equalsIgnoreCase("phone")) {
				//Ignore the AutoWebview setting for phone
					System.out.println("Setting AUTO_WEBVIEW to false");
					capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, false);
					capabilities.setCapability("useNewWDA",true);
					capabilities.setCapability("waitForQuiescence",false);
					capabilities.setCapability("sendKeyStrategy", "setValue");



				}else{
					//Only For Ipad
					capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
					capabilities.setCapability("sendKeyStrategy", "grouped");

				}
				capabilities.setCapability(MobileCapabilityType.NO_RESET, Boolean.parseBoolean(GenericLib.getConfigValue(GenericLib.sConfigFile, "NO_RESET")));
				capabilities.setCapability(MobileCapabilityType.SUPPORTS_ALERTS, true);
				capabilities.setCapability("xcodeOrgId", GenericLib.getConfigValue(GenericLib.sConfigFile, "XCODE_ORGID"));
				capabilities.setCapability("xcodeSigningId", GenericLib.getConfigValue(GenericLib.sConfigFile, "XCODE_SIGNID"));
				capabilities.setCapability("updatedWDABundleId", GenericLib.getConfigValue(GenericLib.sConfigFile, "UPDATE_BUNDLEID"));
				capabilities.setCapability("startIWDP", true);
				capabilities.setCapability("autoGrantPermissions", true);
				capabilities.setCapability("locationServicesAuthorized", true);
				capabilities.setCapability("locationServicesEnabled", true);
				capabilities.setCapability("clearSystemFiles", true);
				capabilities.setCapability("newCommandTimeout", 5000);
				capabilities.setCapability("autoAcceptAlerts", true);
				capabilities.setCapability("showXcodeLog", true);
				
				
				driver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

				Thread.sleep(2000);
			} catch (Exception e) {
				throw e;
			}

			break;

		}

		// Initialize all the page objects and libraries
		genericLib = new GenericLib();
		restServices = new RestServices();
		loginHomePo = new LoginHomePO(driver);
		exploreSearchPo = new ExploreSearchPO(driver);
		workOrderPo = new WorkOrderPO(driver);
		toolsPo = new ToolsPO(driver);
		commonsUtility = new CommonUtility(driver);
		restServices = new RestServices();
		createNewPO = new CreateNewPO(driver);
		recenItemsPO = new RecentItemsPO(driver);
		calendarPO = new CalendarPO(driver);
		tasksPo = new TasksPO(driver);
		checklistPo = new ChecklistPO(driver);
		inventoryPo = new InventoryPO(driver);
		
		//iPhone
		ip_LoginHomePo = new Ph_LoginHomePO(driver);
		ip_MorePo = new Ph_MorePO(driver);
		ip_CalendarPo = new Ph_CalendarPO(driver);
		ip_RecentsPo = new Ph_RecentsItemsPO(driver);
		ip_WorkOrderPo = new Ph_WorkOrderPO(driver);
		ip_ChecklistPO = new Ph_ChecklistPO(driver);
		ip_ExploreSearchPO = new Ph_ExploreSearchPO(driver);
		
		try {
			sSalesforceServerVersion = commonsUtility.servicemaxServerVersion(restServices, genericLib);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExtentManager.getInstance(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	/**
	 * Launch the APP either by re-installing (false) or by re-launching
	 * existing(true) APP, by passing the sResetMode parameter as "true" or "false"
	 * 
	 * @param sResetMode
	 * @throws Exception
	 */
	public void lauchNewApp(String sResetMode) {

		// Installing fresh by default
		GenericLib.setConfigValue(GenericLib.sConfigFile, "NO_RESET", sResetMode);
		System.out.println("[BaseLib] Initialized App Start mode to = " + GenericLib.getConfigValue(GenericLib.sConfigFile, "NO_RESET")+" : [false is reinstall and true is reuse]");

		try {
			setAPP();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Resetting to true always first for next execution
		GenericLib.setConfigValue(GenericLib.sConfigFile, "NO_RESET", "true");
		System.out.println("[BaseLib] Initialized Driver ** = " + driver.toString() + "** ");
	}

	@BeforeMethod
	public void startReport(ITestResult result, ITestContext context) {
		lInitTimeStartMilliSec = System.currentTimeMillis();
		lauchNewApp("true");
		// Use after launch app as it will be null before this
		commonsUtility.injectJenkinsPropertiesForSahi();
		if (sSuiteTestName != null) {
			System.out.println(getBaseTimeStamp() + " -- RUNNING TEST SUITE : " + sSuiteTestName);
		}
		System.out.println(getBaseTimeStamp() + " "+sRunningSymbol+" RUNNING TEST CLASS : " + result.getMethod().getRealClass().getSimpleName());
		ExtentManager.logger(sSuiteTestName + " : " + result.getMethod().getRealClass().getSimpleName());
		if(sDeviceType.equalsIgnoreCase("phone")) {
			//Ignore rotation
			}else{
		driver.rotate((ScreenOrientation.LANDSCAPE));
		driver.rotate((ScreenOrientation.PORTRAIT));
			}

	}

	@AfterMethod
	public void endReport(ITestResult result, ITestContext context) {
		lInitTimeEndMilliSec = System.currentTimeMillis();
		long sTimeDiff = getDateDiffInMin(lInitTimeStartMilliSec, lInitTimeEndMilliSec);
		System.out.println("[BaseLib] Last Context Exited From : " + driver.getContext());
		System.out.println("[BaseLib] Total time for execution : " + sTimeDiff + " min");

		if (result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SKIP) {
			System.out.println(getBaseTimeStamp() + " "+sCompletedSymbol+" COMPLETED TEST CLASS : " + result.getMethod().getRealClass().getSimpleName() + " STATUS : FAILED"+" "+sRetryState);
			if (sOSName.toLowerCase().equals("android")) {
				Set contextNames = driver.getContextHandles();
				driver.context(contextNames.toArray()[0].toString());
			}
			String temp = ExtentManager.getScreenshot();

			try {
				ExtentManager.logger.fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			System.out.println(getBaseTimeStamp() + " "+sCompletedSymbol+" COMPLETED TEST CLASS : " + result.getMethod().getRealClass().getSimpleName() + " STATUS : PASSED"+" "+sRetryState);

		}

		// Avoid duplicate test results in reports on retry
		if (Retry.isRetryRun) {
			// Reset the isRetryRun to false to accept the next run
			Retry.isRetryRun = false;
			// Remove the failed first try
			ExtentManager.extent.removeTest(ExtentManager.logger);
			sCompletedSymbol = "<<^^";
			sRetryState = "RETRY";

		} else {
			sCompletedSymbol = "^^";
			sRetryState = "";
			// Add the retry log
			ExtentManager.extent.flush();
		}

		try {
			driver.quit();
		} catch (Exception e) {
		}
		;

	}

	@AfterClass
	public void tearDownDriver() {

		// try{driver.quit();}catch(Exception e) {};
	}

}
