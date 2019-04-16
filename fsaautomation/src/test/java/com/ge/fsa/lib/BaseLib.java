
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
import com.ge.fsa.pageobjects.browser.Br_CalendarPO;
import com.ge.fsa.pageobjects.browser.Br_LoginHomePO;
import com.ge.fsa.pageobjects.phone.Ph_CalendarPO;
import com.ge.fsa.pageobjects.phone.Ph_ChecklistPO;
import com.ge.fsa.pageobjects.phone.Ph_CreateNewPO;
import com.ge.fsa.pageobjects.phone.Ph_ExploreSearchPO;
import com.ge.fsa.pageobjects.phone.Ph_LoginHomePO;
import com.ge.fsa.pageobjects.phone.Ph_MorePO;
import com.ge.fsa.pageobjects.phone.Ph_RecentsItemsPO;
import com.ge.fsa.pageobjects.phone.Ph_WorkOrderPO;
import com.ge.fsa.pageobjects.tablet.CalendarPO;
import com.ge.fsa.pageobjects.tablet.ChecklistPO;
import com.ge.fsa.pageobjects.tablet.CreateNewPO;
import com.ge.fsa.pageobjects.tablet.ExploreSearchPO;
import com.ge.fsa.pageobjects.tablet.InventoryPO;
import com.ge.fsa.pageobjects.tablet.LoginHomePO;
import com.ge.fsa.pageobjects.tablet.RecentItemsPO;
import com.ge.fsa.pageobjects.tablet.TasksPO;
import com.ge.fsa.pageobjects.tablet.ToolsPO;
import com.ge.fsa.pageobjects.tablet.WorkOrderPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class BaseLib {

	public AppiumDriver driver = null;
	public WebDriver chromeDriver = null;

	public GenericLib genericLib = null;
	public RestServices restServices = null;
	public LoginHomePO loginHomePo = null;
	public ExploreSearchPO exploreSearchPo = null;
	public WorkOrderPO workOrderPo = null;
	public CommonUtility commonUtility = null;
	public ChecklistPO checklistPo = null;
	public ToolsPO toolsPo = null;
	public CreateNewPO createNewPO = null;
	public RecentItemsPO recenItemsPO = null;
	public CalendarPO calendarPO = null;
	public TasksPO tasksPo = null;
	public InventoryPO inventoryPo = null;
	
	//iphone
	
	public Ph_LoginHomePO ph_LoginHomePo = null;
	public Ph_MorePO ph_MorePo = null;
	public Ph_CalendarPO ph_CalendarPo = null;
	public Ph_RecentsItemsPO ph_RecentsItemsPo = null;
	public Ph_WorkOrderPO ph_WorkOrderPo = null;
	public Ph_ExploreSearchPO ph_ExploreSearchPo = null;
	public Ph_ChecklistPO ph_ChecklistPO = null;
	public Ph_CreateNewPO ph_CreateNewPo = null;
	

	//browser
	public Br_CalendarPO br_CalendarPO =null;
	public Br_LoginHomePO br_LoginHomePO =null;

	



	DesiredCapabilities capabilities = null;
	public String sAppPath = null;
	File app = null;
	public static String sOSName = null;
	public static String sDeviceType =null;
	public static String sSelectConfigPropFile = null;
	public static String sSuiteTestName = null;
	public static String sSalesforceServerVersion = null;
	public static String sBuildNo = null;
	public static String sTestName = null;
	public static String sBaseTimeStamp = null;
	public static long lInitTimeStartMilliSec;
	public static long lInitTimeEndMilliSec;
	public static String sOrgType = null;
	public static String sUDID = null;
	public static String sAndroidDeviceName = null;
	public static String sURL = null;
	public static String sXcode_SigninID = null;
	public static String sXcode_OrgID = null;
	public static String sAutomation_Name = null;
	public static String sUpdate_BundleID = null;
	public static String sApp_BundleID = null;
	public static String sNo_Reset = null;

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
	public void startServer(ITestContext context) throws IOException {

		// For report naming purpose, does not impact executions
		sTestName = context.getCurrentXmlTest().getClasses().toString().replaceAll("XmlClass class=", "").replaceAll("\\[\\[", "").replaceAll("\\]\\]", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("com.ge.fsa.tests.", "");
		sSuiteTestName = context.getSuite().getName();

		sSuiteTestName = sSuiteTestName.equalsIgnoreCase("Default suite") ? sTestName : sSuiteTestName;
		System.out.println("[BaseLib] Excuting SUITE : " + sSuiteTestName);
		System.out.println("[BaseLib] Excuting Tests : " + sTestName);

		// Get all jenkins parameters from env
		//Check if jenkins is setting the Select_Config_Properties_For_Build else use local file
		
		sOrgType = System.getenv("Org_Type") != null ? System.getenv("Org_Type") : "base";
		System.out.println("[BaseLib] Server Org Type = " + sOrgType);
		
		// Select the appropriate config file On setting USE_PROPERTY_FILE to "config_automation_build" the config_automation_build excel sheet will be used to get data 
		sSelectConfigPropFile = System.getenv("Select_Config_Properties_For_Build") != null ? System.getenv("Select_Config_Properties_For_Build").toLowerCase() : GenericLib.getConfigValue(GenericLib.sConfigFile, "USE_PROPERTY_FILE").toLowerCase();

		System.out.println("[BaseLib] Running On Profile : " + sSelectConfigPropFile);
		System.out.println("[BaseLib] Reading Config Properties From : " + GenericLib.sConfigFile);

		// Select the OS from Run_On_Platform from jenkins or local
		sOSName = System.getenv("Run_On_Platform") != null?System.getenv("Run_On_Platform").toLowerCase():GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "PLATFORM_NAME").toLowerCase();//GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "PLATFORM_NAME").toLowerCase();
		System.out.println("[BaseLib] OS Name = " + sOSName);

		sDeviceType = System.getenv("Device_Type") != null ? System.getenv("Device_Type") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "DEVICE_TYPE").toLowerCase();
		System.out.println("[BaseLib] Device Type = " + sDeviceType);
		
		// Get the build number from jenkins
		sBuildNo = System.getenv("BUILD_NUMBER") != null ? System.getenv("BUILD_NUMBER") : "local";
		System.out.println("[BaseLib] BUILD_NUMBER : " + sBuildNo);
		
		//Get UDID
		sUDID = System.getenv("UDID") != null ? System.getenv("UDID") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "UDID").toLowerCase();
		System.out.println("[BaseLib] UDID_IOS : " + sUDID);
		
		sAndroidDeviceName = System.getenv("ANDROID_DEVICE_NAME") != null ? System.getenv("ANDROID_DEVICE_NAME") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "ANDROID_DEVICE_NAME").toLowerCase();
		System.out.println("[BaseLib] ANDROID_DEVICE_NAME : " + sAndroidDeviceName);
		
		sAndroidDeviceName = System.getenv("IOS_PLATFORM_VERSION") != null ? System.getenv("IOS_PLATFORM_VERSION") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "IOS_PLATFORM_VERSION").toLowerCase();
		System.out.println("[BaseLib] IOS_PLATFORM_VERSION : " + sAndroidDeviceName);
		
		sAndroidDeviceName = System.getenv("ANDROID_PLATFORM_VERSION") != null ? System.getenv("ANDROID_PLATFORM_VERSION") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "ANDROID_PLATFORM_VERSION").toLowerCase();
		System.out.println("[BaseLib] ANDROID_PLATFORM_VERSION : " + sAndroidDeviceName);
		
		sAndroidDeviceName = System.getenv("OAUTH_URL") != null ? System.getenv("OAUTH_URL") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "OAUTH_URL").toLowerCase();
		System.out.println("[BaseLib] OAUTH_URL : " + sAndroidDeviceName);
		
		sAndroidDeviceName = System.getenv("CLIENT_ID") != null ? System.getenv("CLIENT_ID") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "CLIENT_ID").toLowerCase();
		System.out.println("[BaseLib] CLIENT_ID : " + sAndroidDeviceName);
		
		sAndroidDeviceName = System.getenv("CLIENT_SECRET") != null ? System.getenv("CLIENT_SECRET") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "CLIENT_SECRET").toLowerCase();
		System.out.println("[BaseLib] CLIENT_SECRET : " + sAndroidDeviceName);
	
		sAndroidDeviceName = System.getenv("CREATE_URL") != null ? System.getenv("CREATE_URL") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "CREATE_URL").toLowerCase();
		System.out.println("[BaseLib] CREATE_URL : " + sAndroidDeviceName);
		
		sURL = System.getenv("URL") != null ? System.getenv("URL") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "URL").toLowerCase();
		System.out.println("[BaseLib] URL : " + sURL);
		
		sXcode_SigninID = System.getenv("XCODE_SIGNID") != null ? System.getenv("XCODE_SIGNID") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "XCODE_SIGNID").toLowerCase();
		System.out.println("[BaseLib] XCODE_SIGNID : " + sXcode_SigninID);
		
		sXcode_OrgID = System.getenv("XCODE_ORGID") != null ? System.getenv("XCODE_ORGID") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "XCODE_ORGID").toLowerCase();
		System.out.println("[BaseLib] XCODE_ORGID : " + sXcode_OrgID);
		
		sAutomation_Name = System.getenv("AUTOMATION_NAME") != null ? System.getenv("AUTOMATION_NAME") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "AUTOMATION_NAME").toLowerCase();
		System.out.println("[BaseLib] AUTOMATION_NAME : " + sAutomation_Name);
		
		sUpdate_BundleID = System.getenv("UPDATE_BUNDLEID") != null ? System.getenv("UPDATE_BUNDLEID") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "UPDATE_BUNDLEID").toLowerCase();
		System.out.println("[BaseLib] UPDATE_BUNDLEID : " + sUpdate_BundleID);
		
		
		sApp_BundleID = System.getenv("APP_BUNDLEID") != null ? System.getenv("APP_BUNDLEID") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "APP_BUNDLEID").toLowerCase();
		System.out.println("[BaseLib] APP_BUNDLEID : " + sApp_BundleID);
		
		sNo_Reset = System.getenv("NO_RESET") != null ? System.getenv("NO_RESET") : GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "NO_RESET").toLowerCase();
		System.out.println("[BaseLib] NO_RESET : " + sNo_Reset);
		
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
				sAppPath = GenericLib.sResources + "//" + GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "APP_NAME") + ".apk";
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(MobileCapabilityType.APP, sAppPath);
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "PLATFORM_NAME"));
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "ANDROID_PLATFORM_VERSION"));
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "ANDROID_DEVICE_NAME"));
				if(sDeviceType.equalsIgnoreCase("phone")) {
					//Ignore the AutoWebview setting for phone
						System.out.println("Setting AUTO_WEBVIEW to false");
						capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, false);
						capabilities.setCapability("appPackage", "com.servicemaxinc.fsa");
						capabilities.setCapability("appActivity", "com.servicemaxinc.fsa.MainActivity");
						capabilities.setCapability("ignoreUnimportantViews",true);

					}else{
						capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
						capabilities.setCapability("appPackage", "com.servicemaxinc.svmxfieldserviceapp");
						capabilities.setCapability("appActivity", "com.servicemaxinc.svmxfieldserviceapp.ServiceMaxMobileAndroid");
					}
				capabilities.setCapability("noReset", Boolean.parseBoolean(GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "NO_RESET")));
				// capabilities.setCapability("nativeWebTap", true);
				
				capabilities.setCapability("autoGrantPermissions", true);
				capabilities.setCapability("locationServicesAuthorized", true);
				capabilities.setCapability("locationServicesEnabled", true);
				capabilities.setCapability("clearSystemFiles", true);
				capabilities.setCapability("newCommandTimeout", 5000);
				//capabilities.setCapability("setWebContentsDebuggingEnabled", true);
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

				sAppPath = GenericLib.sResources + "//" + GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "APP_NAME") + ".ipa";
				app = new File(sAppPath);
				capabilities = new DesiredCapabilities();
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "PLATFORM_NAME"));
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "IOS_PLATFORM_VERSION"));
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "IOS_DEVICE_NAME"));
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "AUTOMATION_NAME"));
				capabilities.setCapability(MobileCapabilityType.APP, sAppPath);
				capabilities.setCapability(MobileCapabilityType.UDID, GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "UDID"));
				if(sDeviceType.equalsIgnoreCase("phone")) {
				//Ignore the AutoWebview setting for phone
					System.out.println("Setting AUTO_WEBVIEW to false");
					capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, false);
					capabilities.setCapability("sendKeyStrategy", "setValue");
					capabilities.setCapability("wdaEventloopIdleDelay",5);
					capabilities.setCapability("useNewWDA",false);
					capabilities.setCapability("waitForQuiescence",false);
					capabilities.setCapability("ignoreUnimportantViews",true);
				
				}else{
					//Only For Ipad
					capabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, true);
					capabilities.setCapability("sendKeyStrategy", "grouped");

				}
				capabilities.setCapability(MobileCapabilityType.NO_RESET, Boolean.parseBoolean(GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "NO_RESET")));
				capabilities.setCapability(MobileCapabilityType.SUPPORTS_ALERTS, true);
				capabilities.setCapability("xcodeOrgId", GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "XCODE_ORGID"));
				capabilities.setCapability("xcodeSigningId", GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "XCODE_SIGNID"));
				capabilities.setCapability("updatedWDABundleId", GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "UPDATE_BUNDLEID"));
				capabilities.setCapability("startIWDP", true);
				capabilities.setCapability("autoGrantPermissions", true);
				capabilities.setCapability("locationServicesAuthorized", true);
				capabilities.setCapability("locationServicesEnabled", true);
				capabilities.setCapability("clearSystemFiles", true);
				capabilities.setCapability("newCommandTimeout", 5000);
				capabilities.setCapability("autoAcceptAlerts", true);
				//capabilities.setCapability("showXcodeLog", true);
				//Use this capability for fixing slow launch of app
				//capabilities.setCapability("useNewWDA",true);
				
				
				
				driver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

				Thread.sleep(2000);
			} catch (Exception e) {
				throw e;
			}

			break;
			
		case "browser":
			
			System.out.println("OS type = "+System.getProperty("os.name"));
			
			if(//For Mac OS X
				System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
				System.setProperty("webdriver.chrome.driver","/usr/local/bin/chromedriver/");
			}else {
				//For Windows
				System.setProperty("webdriver.chrome.driver","C:/List_of_Jar/chromedriver.exe");

			}

			// Initialize browser
			chromeDriver= new ChromeDriver();
			 
			// Initialize the driver
			chromeDriver.get(sURL);
			break;
		}

		// Initialize all the page objects and libraries
		genericLib = new GenericLib();
		restServices = new RestServices();
		loginHomePo = new LoginHomePO(driver);
		exploreSearchPo = new ExploreSearchPO(driver);
		workOrderPo = new WorkOrderPO(driver);
		toolsPo = new ToolsPO(driver);
		commonUtility = new CommonUtility(driver);
		restServices = new RestServices();
		createNewPO = new CreateNewPO(driver);
		recenItemsPO = new RecentItemsPO(driver);
		calendarPO = new CalendarPO(driver);
		tasksPo = new TasksPO(driver);
		checklistPo = new ChecklistPO(driver);
		inventoryPo = new InventoryPO(driver);
		
		//iPhone
		ph_LoginHomePo = new Ph_LoginHomePO(driver);
		ph_MorePo = new Ph_MorePO(driver);
		ph_CalendarPo = new Ph_CalendarPO(driver);
		ph_RecentsItemsPo = new Ph_RecentsItemsPO(driver);
		ph_WorkOrderPo = new Ph_WorkOrderPO(driver);
		ph_ChecklistPO = new Ph_ChecklistPO(driver);
		ph_ExploreSearchPo = new Ph_ExploreSearchPO(driver);
		ph_CreateNewPo = new Ph_CreateNewPO(driver);

		//browser
		br_CalendarPO = new Br_CalendarPO(chromeDriver);
		br_LoginHomePO = new Br_LoginHomePO(chromeDriver);

		



		try {
			sSalesforceServerVersion = commonUtility.servicemaxServerVersion(restServices, genericLib);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(sOSName.equalsIgnoreCase("browser")) {
			ExtentManager.getInstance(chromeDriver);
			chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}else{
			ExtentManager.getInstance(driver);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}

	}

	/**
	 * Launch the APP either by re-installing (false) or by re-launching
	 * existing(true) APP, by passing the sResetMode parameter as "true" or "false"
	 * 
	 * @param sResetMode
	 * @throws IOException 
	 * @throws Exception
	 */
	public void lauchNewApp(String sResetMode) throws IOException {

		switch(sOSName) {
		case "browser":
			try {
				setAPP();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		default:
			
		// Installing fresh by default
		GenericLib.writeExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "NO_RESET", sResetMode);
		System.out.println("[BaseLib] Initialized App Start mode to = " + GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "NO_RESET")+" : [false is reinstall and true is reuse]");

		try {
			setAPP();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Resetting to true always first for next execution
		GenericLib.writeExcelData(GenericLib.sConfigPropertiesExcelFile,sSelectConfigPropFile, "NO_RESET", "true");
		System.out.println("[BaseLib] Initialized Driver ** = " + driver.toString() + "** ");
		}
	}

	@BeforeMethod
	public void startReport(ITestResult result, ITestContext context) throws IOException {
		lInitTimeStartMilliSec = System.currentTimeMillis();
		lauchNewApp("true");
		// Use after launch app as it will be null before this
		commonUtility.injectJenkinsPropertiesForSahi();
		if (sSuiteTestName != null) {
			System.out.println(getBaseTimeStamp() + " -- RUNNING TEST SUITE : " + sSuiteTestName);
		}
		System.out.println(getBaseTimeStamp() + " "+sRunningSymbol+" RUNNING TEST CLASS : " + result.getMethod().getRealClass().getSimpleName());
		ExtentManager.logger(sSuiteTestName + " : " + result.getMethod().getRealClass().getSimpleName());
		
		if(sOSName.equalsIgnoreCase("browser")) {
			//do nothing
		}else{
		if(sDeviceType.equalsIgnoreCase("phone")) {
			//Ignore rotation
			}else{
		driver.rotate((ScreenOrientation.LANDSCAPE));
		driver.rotate((ScreenOrientation.PORTRAIT));
			}
		}
	}

	@AfterMethod
	public void endReport(ITestResult result, ITestContext context) {
		lInitTimeEndMilliSec = System.currentTimeMillis();
		long sTimeDiff = getDateDiffInMin(lInitTimeStartMilliSec, lInitTimeEndMilliSec);
		//System.out.println("[BaseLib] Last Context Exited From : " + driver.getContext());
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
		try {
			chromeDriver.quit();
		} catch (Exception e) {
		}

	}

	@AfterClass
	public void tearDownDriver() {

		// try{driver.quit();}catch(Exception e) {};
	}

}
