package com.ge.fsa.lib;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.ge.fsa.pageobjects.tablet.CalendarPO;
import com.ge.fsa.pageobjects.tablet.WorkOrderPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSBy;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;

public class CommonUtility {
	public CommonUtility(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	AppiumDriver driver = null;
	TouchAction touchAction = null;
	IOSTouchAction iosTouchAction = null;
	Iterator<String> iterator = null;
	public static String sNativeApp = null;
	public static String sWebView = null;
	int xOffset = BaseLib.sOSName.equalsIgnoreCase("android")?30:15;
	int yOffset = BaseLib.sOSName.equalsIgnoreCase("android")?36:18;
	int iWhileCnt = 0;
	long lElapsedTime = 0L;
	Point point = null;
	
	
	private WebElement pheleSearchListItem;
	public static String sAppBundleID = BaseLib.sApp_BundleID;
	public static long lWaitTime=2000;
	public static int iProcessStatus=0;
	public static int iAttachmentSleep = 120000;
	public static int iLowSleep = 2000;
	public static int iMedSleep = 5000;
	public static int iHighSleep = 8000;
	public static int i30SecSleep = 10000;
	public static int iVHighSleep = 10000;
	public static String sDirPath = System.getProperty("user.dir");
	public static String sResources = sDirPath+"//resources";
	public static String sConfigPropertiesExcelFile = sResources + "//config_properties.xlsx";
	public static String sTestDataFile = sResources + "//TestData.xlsx";
	public static String sConfigFile =  sResources+"//select_config_file.properties";//sResources+"//config_local.properties";
	public static String sShellFile = sDirPath+"//..//Executable//sahiExecutable.sh";
	public static String sDataFile =  sDirPath+"//..//Executable//data.properties";
	public static String sXmlFilePath = "/auto/SVMX_Catalyst/fsaautomation/suites/failedSuite/failedTests.xml";

	@FindBy(className = "XCUIElementTypePickerWheel")
	// @FindBy(className="android.widget.ListView")
	private WebElement elePickerWheelPopUp;

	public WebElement getElePickerWheelPopUp() {
		return elePickerWheelPopUp;
	}

	@FindAll({@FindBy(id = "Done"),@FindBy(xpath = "//XCUIElementTypeButton[@name='Done']"),@FindBy(xpath = "//*[@name='Done']"),@FindBy(id="SFM.LAYOUT.DATETIMEFIELD.DATEPICKER_DONE")})
	private WebElement eleDonePickerWheelBtn;

	public WebElement getEleDonePickerWheelBtn() throws InterruptedException {
		return eleDonePickerWheelBtn;
	}

	@FindBy(xpath = "//div[@class='x-inner x-container-inner x-align-center x-pack-start x-layout-vbox x-vertical x-layout-box x-component-inner x-widthed x-heighted']//input[@placeholder='Search'][@class='x-input-el']")
	private WebElement elesearchTap;

	public WebElement getElesearchTap() {
		return elesearchTap;
	}

	@FindBy(xpath = "//span[text()='Search']")
	private WebElement elesearchButton;

	public WebElement getElesearchButton() {
		return elesearchButton;
	}



	private WebElement eleSearchListItem;

	public WebElement getElesearchListItem(String searchName) {
		// eleSearchListItem =
		// driver.findElement(By.xpath("//div[@class='x-inner-el'][text()='"+searchName+"']"));
		eleSearchListItem = driver.findElement(By.xpath("//*[.='" + searchName + "'][@class = 'x-gridcell']"));


		return eleSearchListItem;

	}

	// Customized touch Tap
	/**
	 * Tap an element by location points, passing the optional parameters
	 * optionalOffsetPointsxy will tap on the x&y offset provided useage :
	 * tap(element,pointx,pointy)
	 * 
	 * @param el
	 * @param optionalOffsetPointsxy
	 * @throws InterruptedException
	 */
	public void tap(WebElement wElement, int... optionalOffsetPointsxy) throws InterruptedException {
		Thread.sleep(3000);

		Boolean clickPassed = false;
		Boolean tapPassed = false;
		Exception tapExp = null;
		int x = 0;
		int y = 0;

		Integer xNewOffset = optionalOffsetPointsxy.length > 0 ? optionalOffsetPointsxy[0] : null;
		Integer yNewOffset = optionalOffsetPointsxy.length > 1 ? optionalOffsetPointsxy[1] : null;

		try {



			// Wait for the complete coordinates to be generated, E.g if (0,0) (0,231)
			// (12,0), then we will wait for both coordinates to be non-zero.
			for (int i = 0; i < 3; i++) {
				// Since in android or IOS now has clicks and taps alternatively do a click then a tap
				try {
					wElement.click();	
					clickPassed = true;
					//System.out.println("Click passed");
				} catch (Exception e) {
					System.out.println("Click failed");
					clickPassed = false;
					tapExp = e;
				}

				try {
					point = wElement.getLocation();
				} catch (Exception e) {
				}

				if (point.getX() == 0 || point.getY() == 0) {
					System.out
					.println("Waiting... for Coordinates ¯\\_(ツ)_/¯ : " + point.getX() + "---" + point.getY());
					Thread.sleep(1000);

				} else {
					System.out.println("Found Coordinates ヽ(´▽`)/ : " + point.getX() + "---" + point.getY());
					break;
				}

			}
			try {
				String printElement = StringUtils.substringAfter(wElement.toString(), "->");
				System.out.println("Acting on element : " + printElement + " " + wElement.getText() + " "
						+ wElement.getTagName() + " " + wElement.getLocation());
			} catch (Exception e) {
				System.out.println("Acting on element : " + e);
			}

			// Set the custom or default offsets to x & y
			if (xNewOffset != null) {
				x = point.getX() + xNewOffset;
				y = point.getY() + yNewOffset;
				System.out.println("Using Custom Offset Points xNewOffset = " + (xNewOffset) + " yNewOffset = "
						+ (yNewOffset) + " on " + x + "," + y);
			} else {
				x = point.getX() + xOffset;
				y = point.getY() + yOffset;
				System.out.println("Using Offset Points xOffset  = " + (xOffset) + " yOffset = " + (yOffset) + " on "
						+ x + "," + y);

			}

			// Switch the tap based on ANDROID or IOS
			switch (BaseLib.sOSName) {
			case "android":
				// For Android add *2 if real device
				//Overriding offsets for android as it works always with 30,36
				x = point.getX() + xOffset;
				y = point.getY() + yOffset;
				switchContext("Native");
				//System.out.println("Android Tapping ");
				TouchAction andyTouchAction = new TouchAction(driver);
				andyTouchAction.tap(new PointOption().withCoordinates(x, y)).perform();
				switchContext("Webview");
				break;

			case "ios":
				// For IOS
				// Since in IOS now has clicks and taps alternatively do a click then a tap

				//System.out.println("IOS Tapping ");
				TouchAction iosTouchAction = new TouchAction(driver);
				iosTouchAction.tap(new PointOption().withCoordinates(x, y)).perform();
				break;

			default:
				System.out.println("OS Error");
				break;
			}

			//System.out.println("Tap passed");
			tapPassed = true;
		}

		catch (Exception e) {
			tapExp = e;
			tapPassed = false;
			System.out.println("Tap failed");

		}

		Thread.sleep(3000);
		if (clickPassed == false && tapPassed == false) {
			System.out.println("Tap Exception : " + tapExp);
			switchContext("Webview");
			Assert.fail("" + ExtentManager.logger.log(Status.FAIL, "Tap Exception : " + tapExp));
			//Assert.assertTrue(1 < 2, "" + ExtentManager.logger.log(Status.FAIL, "Tap Exception : " + tapExp));
		}

		switchContext("Webview");
	}
	


	public void singleTap(Point point) throws InterruptedException {
		touchAction = new TouchAction(driver);
		touchAction.tap(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset)).perform();
		Thread.sleep(CommonUtility.iLowSleep);
	}



	// Customised touch Tap
	public void fingerTap(Point point, int iTapCount) throws InterruptedException {
		touchAction = new TouchAction(driver);
		touchAction.moveTo(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset))
		.tap(new TapOptions().withTapsCount(iTapCount)).perform();
		Thread.sleep(CommonUtility.iLowSleep);
	}

	// Customised touch LongPress
	public void longPress(WebElement wElement,int... optionalOffsetPointsxy) throws InterruptedException {
		Thread.sleep(3000);
		int x = 0;
		int y = 0;
		Point point = wElement.getLocation();
		System.out.println("x " + point.getX() + " y " + point.getY());
		
		Integer xNewOffset = optionalOffsetPointsxy.length > 0 ? optionalOffsetPointsxy[0] : null;
		Integer yNewOffset = optionalOffsetPointsxy.length > 1 ? optionalOffsetPointsxy[1] : null;
		System.out.println("x " + point.getX() + " y " + point.getY());
		
		if (xNewOffset != null) {
			x = point.getX() + xNewOffset;
			y = point.getY() + yNewOffset;
			System.out.println("Using Custom Offset Points xNewOffset = " + (xNewOffset) + " yNewOffset = "
					+ (yNewOffset) + " on " + x + "," + y);
		} else {
			x = point.getX() + xOffset;
			y = point.getY() + yOffset;
			System.out.println("Using Offset Points xOffset  = " + (xOffset) + " yOffset = " + (yOffset) + " on "
					+ x + "," + y);

		}
		
	
		switch (BaseLib.sOSName) {
		case "android":

			// For Android add *2 if real device
			switchContext("Native");
			touchAction = new TouchAction(driver);
			touchAction.longPress(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(10000))).release().perform();
//			touchAction.longPress(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset))
//			.perform();
//			touchAction.longPress(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset)).release()
//			.perform();
			Thread.sleep(CommonUtility.iLowSleep);
			switchContext("Webview");
			break;

		case "ios":
			switchContext("Webview");
			// For IOS
			touchAction = new TouchAction(driver);
			touchAction.longPress(new PointOption().withCoordinates(x,y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(10000))).release().perform();
//			touchAction.longPress(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset)).release()
//			.perform();
			Thread.sleep(CommonUtility.iLowSleep);
			break;

		}
	}

	// Customised touch Doubletap
	public void doubleTap(WebElement element) throws InterruptedException {
		waitforElement(element, CommonUtility.i30SecSleep);

		point = element.getLocation();
		touchAction = new TouchAction(driver);

		// touchAction.tap(new TapOptions().withTapsCount(2).withElement((ElementOption)
		// element)).perform();
		touchAction.tap(new TapOptions().withTapsCount(2).withElement((ElementOption) element)).perform();
		Thread.sleep(CommonUtility.iLowSleep);

	}

	// Customised touch Press
	public void press(Point point) throws InterruptedException {
		switch (BaseLib.sOSName) {
	case "android":
		// For Android add *2 if real device
		switchContext("Native");
		touchAction = new TouchAction(driver);
		touchAction.press(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset)).perform();
		Thread.sleep(CommonUtility.iLowSleep);
	case "ios":
		touchAction = new TouchAction(driver);
		touchAction.press(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset)).perform();
		Thread.sleep(CommonUtility.iLowSleep);
		break;
		
		}
		
	}

	public void swipeUp() {
		touchAction = new TouchAction(driver);
		touchAction.longPress(new PointOption().withCoordinates(132, 674))
		.moveTo(new PointOption().withCoordinates(162, 1385)).release();
	}
	public void swipeDown() {
		touchAction = new TouchAction(driver);
		touchAction.longPress(new PointOption().withCoordinates(132, 674))
		.moveTo(new PointOption().withCoordinates(1385,162)).release();
	}

	public void swipeLeft(WebElement wElement) {
	
		switchContext("Native");
		Point point = wElement.getLocation();
		System.out.println(point);
		int x = point.getX()+200;
		System.out.println(x);
		int y = point.getY();
		System.out.println(y);

		int xOff = x - 200;
		// int yOff = y-100;
		touchAction = new TouchAction(driver);
		touchAction.press(new PointOption().withCoordinates(x, y))
		//touchAction.longPress(new PointOption().withCoordinates(x, y))
		.waitAction(new WaitOptions().withDuration(Duration.ofMillis(3000)))
		.moveTo(new PointOption().withCoordinates(xOff, y+20)).release().perform();
		System.out.println(xOff);

	}

	
	
	public void Enablepencilicon(WebElement wElement) {
		int offset = 30;
		Point point = wElement.getLocation();
		int x = point.getX()+xOffset;
		int y = point.getY()+yOffset;

		int xOff = x + 200;
		touchAction = new TouchAction(driver);
		
		switch (BaseLib.sOSName) {
		case "android":

			// For Android add *2 if real device
			switchContext("Native");
			touchAction.longPress(new PointOption().withCoordinates(x, y))
			.waitAction(new WaitOptions().withDuration(Duration.ofMillis(2000)))
			.moveTo(new PointOption().withCoordinates(x, y)).release().perform();
			switchContext("webview");
			break;

		case "ios":

			touchAction.longPress(new PointOption().withCoordinates(x, y))
			.waitAction(new WaitOptions().withDuration(Duration.ofMillis(2000)))
			.moveTo(new PointOption().withCoordinates(x, y+5)).release().perform();
			break;

		}
	}

	// To search the element scrolling
	public void getSearch(WebElement wElement) throws InterruptedException {
		//		while(iWhileCnt<=7) 
		//		{	
		//			try {
		waitforElement(wElement, 30);
		Assert.assertTrue(wElement.isDisplayed(), "Failed to scroll to search");
		ExtentManager.logger.log(Status.INFO, "Search is successfull");
		System.out.println("Search is displayed");
		// break;
		//			}catch(Exception e) {swipeUp();}			
		//			iWhileCnt++;
		//		}
		//		Thread.sleep(5000);
	}

	/**
	 * To switch context between Native and Webview, defaults to Webview always
	 * 
	 * @param sContext
	 */
	public void switchContext(String sContext) {

		if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
			//Do Nothing
		}else {

			// prints out something like NATIVE_APP \n WEBVIEW_1 since each time the
			// WEBVIEW_2,_3,_4 name is appended by a new number we need to store is a
			// global variable to access across
			try {
				Set<String> availableContextNames = driver.getContextHandles();
				//System.out.println("Available Contexts = " + availableContextNames);

				for (String retreivedContext : availableContextNames) {
					if (sContext.toLowerCase().contains("chrome")) {
						if (retreivedContext.toLowerCase().contains("chrome")) {
							//System.out.println("Setting Context = " + retreivedContext);
							driver.context(retreivedContext);
						}

					} else {
						if (retreivedContext.toLowerCase().contains(sContext.toLowerCase()) && !retreivedContext.toLowerCase().contains("chrome")) {
							//System.out.println("Setting Context = " + retreivedContext);
							driver.context(retreivedContext);
						}
					}
				}

			} catch (Exception e) {
				// TODO: handle exceptions
				System.out.println("Could not switch the context" + e);
			}
		}
	}

	private WebElement elePicklistValue;

	public WebElement getElePicklistValue(String sPickListValue) {
		elePicklistValue = driver.findElement(
				By.xpath("//*[@class='android.widget.CheckedTextView'][contains(@text,'" + sPickListValue + "')]"));

		return elePicklistValue;
	}

	/**
	 * Set the pickerwheel(ios)/picklist(android) value in ios or android
	 * 
	 * @param wElement
	 * @param sValue
	 * @throws InterruptedException
	 */
	public void setPickerWheelValue(WebElement wElement, String sValue) throws InterruptedException {

		switch (BaseLib.sOSName) {
		case "android":
			//			tap(wElement,16,20);
			tap(wElement, 30, 36);
			Thread.sleep(2000);
			switchContext("Native");
			try {
				//if element is visible clicks it else throws error
				getElePicklistValue(sValue).click();
			}
			catch(Exception e) {
				//scroll till the element till element is visible
				driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\""+sValue+"\"))"));
				getElePicklistValue(sValue).click();
			}
			switchContext("WebView");
			break;

		case "ios":
			wElement.click();
			Thread.sleep(2000);
			switchContext("Native");
			getElePickerWheelPopUp().sendKeys(sValue);
			getEleDonePickerWheelBtn().click();
			switchContext("WebView");
			break;

		default:
			System.out.println("OS error");
			break;
		}
	}

	/**
	 * Wait for element until the element is displayed or time elapsed in seconds
	 * NOTE : The Actual time is inclusive of Multiple object find time as well so it may be more
	 * 
	 * @param wElement
	 * @param lTime
	 * @throws InterruptedException
	 */

	public boolean waitforElement(WebElement wElement, int lTime) throws InterruptedException {
		int lElapsedTime = 0;
		int reCalculatedWaitTime =0;
		if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
			//Setting wait for 1 sec only for this method reverting when exiting
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			//If we Set 3 sec wait, then Actual time is 6 sec + inclusive of Multiple object find time as well so it may be more , so reducing to lTime/2 to make it close to exact and iterate only for 3 sec
			 reCalculatedWaitTime = Math.abs((lTime/2)+1);
			//System.out.println("ABS "+reCalculatedWaitTime);

		}else {
			//For Tablet			
			 reCalculatedWaitTime = lTime;
		}
		
		System.out.println("Waiting For : " + lTime + " sec");
		//		String context=driver.getContext();
		//		switchContext("native");
		//		System.out.println("Time to Wait : " + lTime + " sec");
		//		if(wElement.toString().contains("->")) {
		//		String printElement = StringUtils.substringAfter(wElement.toString(), "->");
		//		System.out.println("Waiting For Element : " + printElement);
		//		}else {
		//		System.out.println("Waiting For Element : " + wElement.toString());
		//		}
		//		switchContext(context);
		//long lInitTimeStartMilliSec = System.currentTimeMillis();
		//long lInitTimeEndMilliSec = 0;
		
		while (lElapsedTime != reCalculatedWaitTime) {		
			Thread.sleep(1000);
			try {
				if (wElement.isDisplayed()) {// If element is displayed break
					System.out.println("Element is displayed");
					//switchContext(context);
					if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
						//Setting wait for 1 sec only for this method reverting when exiting
						driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}else {
					//reverting wait to 10 seconds
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					}
					//lInitTimeEndMilliSec = System.currentTimeMillis();
					//long sTimeDiff = getDateDiffInSec(lInitTimeStartMilliSec, lInitTimeEndMilliSec);
					//System.out.println("[BaseLib] Last Context Exited From : " + driver.getContext());
					//System.out.println("Actual Time Waited : " + sTimeDiff + " sec");
					return true;
				}
			} catch (Exception ex) {
			}

			lElapsedTime++;
		}
		//lInitTimeEndMilliSec = System.currentTimeMillis();
		//long sTimeDiff = getDateDiffInSec(lInitTimeStartMilliSec, lInitTimeEndMilliSec);
		//System.out.println("[BaseLib] Last Context Exited From : " + driver.getContext());
		//System.out.println("Actual Time Waited : " + sTimeDiff + " sec");
		System.out.println("Element is not displayed");
		//switchContext(context);
		if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
			//Setting wait for 1 sec only for this method reverting when exiting
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}else {
		//reverting wait to 10 seconds
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		return false;

	}

	/**
	 * Wait for the element to not be displayed on the screen
	 * @param wElement
	 * @param lTime
	 * @return
	 * @throws InterruptedException
	 */
	public boolean waitForElementNotVisible(WebElement wElement, int lTime) throws InterruptedException {
		int lElapsedTime = 0;
		System.out.println("Waiting until element is not visible for : " + lTime + " sec");

		while (lElapsedTime != lTime) {
			Thread.sleep(1000);
			try {
				if (wElement.isDisplayed()) {// If element is not displayed break

				}else {
					System.out.println("Element is not displayed anymore");
					//switchContext(context);
					return true;
				}
			} catch (Exception ex) {
				System.out.println("Element is not displayed anymore");
				//switchContext(context);
				return true;
			}

			lElapsedTime++;
		}
		System.out.println("Element is still displayed and timed out");
		//switchContext(context);
		return false;

	}


	// This method will search the required value and then click on it
	public void lookupSearch(String value) throws InterruptedException {

		tap(getElesearchTap());
		getElesearchTap().clear();
		getElesearchTap().sendKeys(value);
		tap(getElesearchButton());
		//tap(getElesearchButton(),30,36);
		Thread.sleep(5000);
		tap(getElesearchListItem(value));

	}

	/*
	 * This method is used to generate the Random value times stamped with current
	 * time
	 */
	public String generateRandomNumber(String value) {

		String date = new SimpleDateFormat("ddMMyyyyHHmmss").format(System.currentTimeMillis());
		System.out.println(date);
		String randomstring = value + date;
		System.out.println(randomstring);

		return randomstring;
	}

	/**
	 * This method is used to parse the string to the passed format to return date
	 * @param dateFormat
	 * @param date
	 * @return Date
	 * Eg:Date currentDate=convertStringToDate("E, MMM dd yyyy", date);
	 */
	public Date convertStringToDate(String dateFormat,String date) {
		SimpleDateFormat df=new SimpleDateFormat(dateFormat);
		Date parsedDate=null;
		try {
			parsedDate = df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parsedDate;

	}
	/**
	 * This method is used to format the passed date to string.
	 * @param dateFormat
	 * @param date
	 * @return String
	 * Eg:String selectDate = converDateToString("dd MMMM yyyy",newDate);
	 */
	public String converDateToString(String dateFormat,Date date) {
		SimpleDateFormat df=new SimpleDateFormat(dateFormat);
		return df.format(date);
	}

	@FindBy(id = "android:id/date_picker_header_date")
	private WebElement datePicker;
	public WebElement getDatePicker() {
		return datePicker;
	}

	@FindBy(id = "android:id/date_picker_header_year")
	private WebElement yearPicker;

	public WebElement getYearPicker() {
		return yearPicker;
	}

	@FindBy(id = "android:id/button1")
	private WebElement calendarDone;
	public WebElement getCalendarDone() {
		return calendarDone;
	}
	/**
	 * This object can be used for any field that can be identified by accessibilityId
	 * eg: 
	 * getCalendarEdit("11").click();
	 * getCalendarEdit("30").click();
	 */
	private WebElement accessibleElement;
	public WebElement getAccessibleElement(String accessibilityID) {
		return accessibleElement =  driver.findElementByAccessibilityId(accessibilityID);
	}

	/**
	 * <pre>
	 * Set the 24 Hrs time by moving no of day's ahead(+ve) or behind(-ve) followed by Hrs and Min.
	 * 
	 * FOR ANDROID :
	 * 
	 * For adding one day ahead followed by Hrs and Min: 
	 * setDateTime24hrs( workOrderPo.getEleIBScheduledTxtFld(),1, “02”,”30”)
	 *
	 * For only moving no of days +ve or -ve and leaving default Hrs and Min : 
	 * setDateTime24hrs(workOrderPo.getEleIBScheduledTxtFld(), 1, “0”,”0”)
	 * 
	 * For current Day, Hrs and Min : 
	 * setDateTime24hrs(workOrderPo.getEleIBScheduledTxtFld(), 0, “0”,”0”)
	 *
	 *=====================================================================
	 *
	 * FOR IOS :
	 * 
	 * For adding one day ahead followed by Hrs and Min: 
	 * setDateTime24hrs( workOrderPo.getEleIBScheduledTxtFld(),1, “02”,”30”)
	 *
	 * For only moving no of day +ve or -ve and leaving default Hrs and Min : 
	 * setDateTime24hrs(workOrderPo.getEleIBScheduledTxtFld(), 1, “0”,”0”)
	 * 
	 * For current Day, Hrs and Min : 
	 * setDateTime24hrs(workOrderPo.getEleIBScheduledTxtFld(), 0, “0”,”0”)
	 *
	 * @param wElement
	 * @param iDaysToScroll
	 * @param sTimeHrs
	 * @param sTimeMin
	 * @param sTimeAMPM
	 * @throws InterruptedException
	 * @throws ParseException
	 * </pre>
	 */
	public void setDateTime24hrs(WebElement wElement, int iDaysToScroll, String sTimeHrs, String sTimeMin)
			throws InterruptedException {
		System.out.println("Setting 24hrs Date Time For iDaysToScroll = "+iDaysToScroll+" sTimeHrs = "+sTimeHrs+" sTimeMin = "+sTimeMin);
		switch (BaseLib.sOSName) {
		case "android":
			switchContext("Webview");
			if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
				custScrollToElementAndClick(wElement);
			}else {
				tap(wElement, 30, 36);

			}
			Date currentDate = null;
			switchContext("Native");
			Thread.sleep(3000);
			String date = getDatePicker().getText();
			date = date + " " + getYearPicker().getText();
			if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
			//UK Date Format
			currentDate=convertStringToDate("E dd MMM yyyy", date);
			}
			else
			{
			//US DateFormat
			currentDate=convertStringToDate("E, MMM dd yyyy", date);
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			cal.add(Calendar.DAY_OF_MONTH, iDaysToScroll);
			Date newDate = cal.getTime();
			String selectDate = converDateToString("dd MMMM yyyy",newDate);
			int count=0;
			//Check only for 10 year ahead or behind in calendar
			while (driver.findElementsByAccessibilityId(selectDate).size() == 0 && count<120) {
				if (currentDate.before(newDate)) {
					getAccessibleElement("Next month").click();;
				} else {
					getAccessibleElement("Previous month").click();
				}
				count++;
			}
			//selecting the date, hour and minutes 
			getAccessibleElement(selectDate).click();;
			getCalendarDone().click();
			//set current or specific time
			Thread.sleep(1000);
			if (sTimeHrs == "0" && sTimeMin == "0") {
				getCalendarDone().click();

			} else {
				getAccessibleElement(Integer.valueOf(sTimeHrs).toString()).click();
				getAccessibleElement(Integer.valueOf(sTimeMin).toString()).click();
				getCalendarDone().click();
			}
			switchContext("Webview");
			break;
		case "ios":
			if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
				custScrollToElementAndClick(wElement);
			}
			else {
				switchContext("Webview");
				wElement.click();
			}
			setDatePicker(0, iDaysToScroll);
			switchContext("Native");
			if (sTimeHrs == "0" && sTimeMin == "0") {
				getEleDonePickerWheelBtn().click();

			} else {
				timeSetter(sTimeHrs, sTimeMin, "", true,true);
				getEleDonePickerWheelBtn().click();
			}

			switchContext("Webview");
			Thread.sleep(CommonUtility.iLowSleep);
			break;
		}
		switchContext("Webview");
	}

	/**
	 * <pre>
	 * This is an overloaded method. @see setDateTime24hrs(WebElement wElement, int iDaysToScroll, String sTimeHrs, String sTimeMin)
	 * 
	 * For setting the DateTime. !! NOTE : Only for this method Since the Date
	 * Formats for IOS and Android differ the user needs to switch sDateFormat
	 * parameter when passing from the test script based on OS!
	 * 
	 * FOR ANDROID : Supported Format is ((Month date Year),Hrs,Min)
	 * 
	 * For adding Month Day and Year : 
	 * setDateTime24hrs(workOrderPo.getEleIBScheduledTxtFld(), “January 10 2018”, “10”,”30”)
	 *
	 * For current Month, day and year : 
	 * setDateTime24hrs(workOrderPo.getEleIBScheduledTxtFld(), “0”, “0”,”0”)
	 * 
	 * =====================================================================
	 * 
	 * FOR IOS : Supported Format is ((Day Month Date),Hrs,Min)
	 * 
	 * For adding DateFormat, hrs and min : 
	 * setDateTime24hrs(workOrderPo.getEleIBScheduledTxtFld(), “Mon Oct 15”, “09”,”30”)
	 *
	 * For adding DateFormat and leaving default hrs and min : 
	 * setDateTime24hrs(workOrderPo.getEleIBScheduledTxtFld(), “Mon Oct 15”, “0”,”0”)
	 * 
	 * For current Month, day and year : 
	 * setDateTime24hrs(workOrderPo.getEleIBScheduledTxtFld(), “0”, “0”,”0”)
	 *
	 * @param wElement
	 * @param sDateFormat
	 * @param sTimeHrs
	 * @param sTimeMin
	 * @throws InterruptedException
	 */

	public void setDateTime24hrs(WebElement wElement, String sDateFormat, String sTimeHrs, String sTimeMin)
			throws InterruptedException {
		System.out.println("Setting 24hrs Date Time For sDateFormat = "+sDateFormat+" sTimeHrs = "+sTimeHrs+" sTimeMin = "+sTimeMin);
		switch (BaseLib.sOSName) {
		case "android":
			switchContext("Webview");
			tap(wElement, 30, 36);
			switchContext("Native");
			// Set current date if all paramters are "0"
			if (sDateFormat == "0" && sTimeHrs == "0" && sTimeMin == "0") {
				getCalendarDone().click();
				Thread.sleep(1000);
				getCalendarDone().click();
			} else {
				// For android extract the month day and year from sDateFormat
				String[] sAndyDateFormat = sDateFormat.split(" ");
				String sMonth = sAndyDateFormat[0];
				String sDay = sAndyDateFormat[1];
				String sYear = sAndyDateFormat[2];

				String date = getDatePicker().getText();
				date = date + " " + getYearPicker().getText();
				Date currentDate = convertStringToDate("E, MMM dd yyyy", date);
				Date monthDate = convertStringToDate("MMMM", sMonth);
				Calendar cal = Calendar.getInstance();
				cal.setTime(monthDate);
				cal.set(Integer.valueOf(sYear), cal.get(Calendar.MONTH), Integer.valueOf(sDay));
				Date newDate = cal.getTime();
				String selectDate = converDateToString("dd MMMM yyyy", newDate);
				int count = 0;
				// Check only for 10 year ahead or behind in calendar
				while (driver.findElementsByAccessibilityId(selectDate).size() == 0 && count < 120) {
					if (currentDate.before(newDate)) {
						getAccessibleElement("Next month").click();
					} else {
						getAccessibleElement("Previous month").click();
					}
					count++;
				}
				// Select the date
				getAccessibleElement(selectDate).click();
				getCalendarDone().click();
				// set current or specific time
				Thread.sleep(1000);
				if (sTimeHrs == "0" && sTimeMin == "0") {
					getCalendarDone().click();

				} else {
					getAccessibleElement(Integer.valueOf(sTimeHrs).toString()).click();
					getAccessibleElement(Integer.valueOf(sTimeMin).toString()).click();
					getCalendarDone().click();
				}

			}
			switchContext("Webview");
			break;
		case "ios":
			wElement.click();
			switchContext("Native");
			if (sDateFormat == "0" && sTimeHrs == "0" && sTimeMin == "0") {
				getEleDonePickerWheelBtn().click();
				Thread.sleep(1000);
				//getEleDonePickerWheelBtn().click();
			} else {
				getEleDatePickerPopUp().get(0).sendKeys(sDateFormat);
				if (sTimeHrs == "0" && sTimeMin == "0") {
					getEleDonePickerWheelBtn().click();

				} else {
					timeSetter(sTimeHrs, sTimeMin, "", true,true);
					getEleDonePickerWheelBtn().click();
				}

			}
			switchContext("Webview");
			Thread.sleep(CommonUtility.iLowSleep);
			break;
		}
		switchContext("Webview");
	}

	/**
	 * For setting the Date Only. !!NOTE : for dateTime use "setSpecificDateTime" method or "setDateTime24hrs" method
	 * 
	 * FOR ANDROID : Supported Format is (Month,Day,Year)
	 * 
	 * For adding Month Day and Year : setSpecificDateYear(
	 * workOrderPo.getEleIBScheduledTxtFld(), “January”, “10”,”2018”)
	 *
	 * For current Month, day and year : setSpecificDateYear(
	 * workOrderPo.getEleIBScheduledTxtFld(), “0”, “0”,”0”)
	 * 
	 * =====================================================================
	 * 
	 * FOR IOS : Supported Format is (Month,Day,Year)
	 * 
	 * For adding Month, Day and Year : setSpecificDateYear(
	 * workOrderPo.getEleIBScheduledTxtFld(), “January”, “09”,”2018”)
	 * 
	 * For current Month, day and year : setSpecificDateYear(
	 * workOrderPo.getEleIBScheduledTxtFld(), “0”, “0”,”0”)
	 * 
	 * @param wElement
	 * @param sMonth
	 * @param sDay
	 * @param sYear
	 * @throws InterruptedException
	 */

	public void setSpecificDate(WebElement wElement, String sMonth, String sDay, String sYear)
			throws InterruptedException {
		switch (BaseLib.sOSName) {
		case "android":
			//switchContext("webview");
			if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
				custScrollToElementAndClick(wElement);
			}else {
				switchContext("webview");
				tap(wElement, 30, 36);

			}
			switchContext("Native");
			//Set current date if all paramters are "0"
			Date currentDate = null;
			if(sMonth =="0" && sDay == "0" && sYear =="0") {
				getCalendarDone().click();
			}else {
				String date = getDatePicker().getText();
				date = date + " " + getYearPicker().getText();
				if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
					//UK Date Format
					currentDate=convertStringToDate("E dd MMM yyyy", date);
					}
					else
					{
					//US DateFormat
					currentDate=convertStringToDate("E, MMM dd yyyy", date);
					}
				
				Date monthDate=convertStringToDate("MMMM", sMonth);
				Calendar cal = Calendar.getInstance();
				cal.setTime(monthDate);
				cal.set(Integer.valueOf(sYear),cal.get(Calendar.MONTH) , Integer.valueOf(sDay));
				Date newDate = cal.getTime();
				String selectDate = converDateToString("dd MMMM yyyy",newDate);
				int count=0;
				//Check only for 10 year ahead or behind in calendar
				while (driver.findElementsByAccessibilityId(selectDate).size() == 0 && count<120) {
					if (currentDate.before(newDate)) {
						getAccessibleElement("Next month").click();
					} else {
						getAccessibleElement("Previous month").click();
					}
					count++;
				}
				//Select the date
				getAccessibleElement(selectDate).click();
				getCalendarDone().click();
			}
			break;
		case "ios":
			wElement.click();
			switchContext("Native");
			//Select Current Date
			if(sMonth =="0" && sDay == "0" && sYear =="0") {
				getEleDonePickerWheelBtn().click();
			} else {
				int cylinderPosition=BaseLib.sDeviceType.equalsIgnoreCase("phone")?1:0;
				getEleDatePickerPopUp().get(cylinderPosition).sendKeys(sMonth);
				timeSetter(sDay, sYear, "", true,false);
				getEleDonePickerWheelBtn().click();
			}

			Thread.sleep(CommonUtility.iLowSleep);
		}
		switchContext("Webview");
	}



	/**
	 * Set the time form the date picker wheels in 12hrs format, passing 0 for
	 * sTimeHrs,sTimeMin,sTimeAMPM will set the present date
	 *
	 * @param wElement
	 * @param iDaysToScroll
	 * @param sTimeHrs
	 * @param sTimeMin
	 * @param sTimeAMPM
	 * @throws InterruptedException
	 */
	public void setDateTime12Hrs(WebElement wElement, int iDaysToScroll, String sTimeHrs, String sTimeMin,
			String sTimeAMPM) throws InterruptedException {
		System.out.println("Setting 12hrs Date Time For iDaysToScroll = "+iDaysToScroll+" sTimeHrs = "+sTimeHrs+" sTimeMin = "+sTimeMin+"sTimeAMPM = "+sTimeAMPM);
		switch (BaseLib.sOSName) {
		case "android":
			switchContext("Webview");
			if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
				custScrollToElementAndClick(wElement);
			}else {
				tap(wElement, 30, 36);

			}
			switchContext("Native");
			Thread.sleep(3000);
			String date = getDatePicker().getText();
			date = date + " " + getYearPicker().getText();
			Date currentDate=convertStringToDate("E, MMM dd yyyy", date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(currentDate);
			cal.add(Calendar.DAY_OF_MONTH, iDaysToScroll);
			Date newDate = cal.getTime();
			String selectDate = converDateToString("dd MMMM yyyy",newDate);
			int count=0;
			//Check only for 10 year ahead or behind in calendar
			while (driver.findElementsByAccessibilityId(selectDate).size() == 0 && count<120) {
				if (currentDate.before(newDate)) {
					getAccessibleElement("Next month").click();;
				} else {
					getAccessibleElement("Previous month").click();
				}
				count++;
			}
			//selecting the date, hour and minutes 
			getAccessibleElement(selectDate).click();;
			getCalendarDone().click();
			//set current or specific time
			Thread.sleep(1000);
			if (sTimeHrs == "0" && sTimeMin == "0") {
				getCalendarDone().click();

			} else {
				getAccessibleElement(Integer.valueOf(sTimeHrs).toString()).click();
				getAccessibleElement(Integer.valueOf(sTimeMin).toString()).click();
				if(sTimeAMPM.equalsIgnoreCase("am")) {
					driver.findElement(By.id("android:id/am_label")).click();
				}else {
					driver.findElement(By.id("android:id/pm_label")).click();
				}

				getCalendarDone().click();
			}
			switchContext("Webview");
			break;
		case "ios":
			if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
				custScrollToElementAndClick(wElement);
			}

			switchContext("Webview");
			wElement.click();
			setDatePicker(0, iDaysToScroll);
			switchContext("Native");
			if (sTimeHrs == "0" && sTimeMin == "0") {
				getEleDonePickerWheelBtn().click();

			} else {
				timeSetter(sTimeHrs, sTimeMin, sTimeAMPM, false,true);
				getEleDonePickerWheelBtn().click();
			}

			switchContext("Webview");
			Thread.sleep(CommonUtility.iLowSleep);
			break;
		}
		switchContext("Webview");


	}

	@FindBy(xpath = "//XCUIElementTypePickerWheel[@type='XCUIElementTypePickerWheel']")
	private List<WebElement> eleDatePickerPopup;

	public List<WebElement> getEleDatePickerPopUp() {
		return eleDatePickerPopup;
	}

	/**
	 * Set the specific date picker wheel (Day/month/year) by scrolling up or down
	 * based on +ve or -ve value to be used with setDateTime24hrs / setDateTime12hrs
	 * 
	 * @param iWheelIndex
	 * @param scrollNum
	 */
	public void setDatePicker(int iWheelIndex, int scrollNum) {

		switch (BaseLib.sOSName) {
		case "android":
			switchContext("Native");
			Calendar calendar=Calendar.getInstance();
			Date currentDate=calendar.getTime();
			if(iWheelIndex==1) {
				calendar.add(Calendar.DATE, scrollNum);
			}
			else if(iWheelIndex==2) {
				calendar.add(Calendar.MONTH, scrollNum);
			}
			else {
				calendar.add(Calendar.YEAR, scrollNum);
			}
			Date newDate = calendar.getTime();
			String selectDate = converDateToString("dd MMMM yyyy",newDate);
			int count=0;
			//Check only for 10 year ahead or behind in calendar
			while (driver.findElementsByAccessibilityId(selectDate).size() == 0 && count<120) {
				if (currentDate.before(newDate)) {
					getAccessibleElement("Next month").click();
				} else {
					getAccessibleElement("Previous month").click();
				}
				count++;
			}
			//Select the date
			getAccessibleElement(selectDate).click();
			//getCalendarDone().click();
			switchContext("Webview");
			break;
		case "ios":
			switchContext("Native");
			int i = 0;
			int newTempVal = scrollNum;
			scrollNum = Math.abs(scrollNum);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Map<String, Object> params = new HashMap<>();
			if (newTempVal < 0) {
				System.out.println("Scrolling Down " + scrollNum);
				params.put("order", "previous");

			} else {
				System.out.println("Scrolling Up " + scrollNum);
				params.put("order", "next");

			}
			params.put("offset", 0.15);
			params.put("element", getEleDatePickerPopUp().get(iWheelIndex));
			for (i = 0; i < scrollNum; i++) {	
				js.executeScript("mobile: selectPickerWheelValue", params);
			}

			break;
		}

	}

	@FindBy(id = "android:id/hours")
	private WebElement calendarHours;

	public WebElement getCalendarHours() {
		return calendarHours;
	}

	@FindBy(id = "android:id/minutes")
	private WebElement calendarMinutes;

	public WebElement getCalendarMinutes() {
		return calendarMinutes;
	}

	@FindBy(id = "android:id/am_label")
	private WebElement calendarAM;

	public WebElement getCalendarAM() {
		return calendarAM;
	}

	@FindBy(id = "android:id/pm_label")
	private WebElement calendarPM;

	public WebElement getCalendarPM() {
		return calendarPM;
	}

	@FindBy(xpath = "//*[@class='android.widget.CheckedTextView'][@checked='true']")
	private WebElement eleAndroidWheelPopUp;
	public WebElement getEleAndroidWheelPopUp() {
		return eleAndroidWheelPopUp;
	}
	public String getDate(WebElement wElement,String dateTime) throws InterruptedException {
		String date="";
		switch (BaseLib.sOSName) {
		case "android":
			tap(wElement, 30, 36);
			switchContext("Native");
			date = getDatePicker().getText();
			date = date + " " + getYearPicker().getText();
			Date currentDate=convertStringToDate("E, MMM dd yyyy", date);
			date= new SimpleDateFormat("MM/dd/yy").format(currentDate);
			getCalendarDone().click();
			if(dateTime.equalsIgnoreCase("datetime")) {
				Thread.sleep(1000);
				String sHours=getCalendarHours().getText();
				String sMinutes=getCalendarMinutes().getText();
				String sAMPM;
				try{
					sAMPM=getCalendarAM().isSelected()?getCalendarAM().getText():getCalendarPM().getText();
				}
				catch (Exception e) {
					sAMPM=Integer.parseInt(sHours)>11?"PM":"AM";
				}				date=date+" "+sHours+":"+sMinutes+" "+sAMPM;
				getCalendarDone().click();
			}
			switchContext("webview");
			break;
		case "ios":
			setSpecificDate(wElement, "0", "0", "0");
			date=wElement.getAttribute("value");
			break;
		}
		switchContext("webview");
		return date;
	}

	/**
	 * Set the time, for the hrs, min, AMPM values, for 24hrs set the is24hrs to
	 * true, if 0 value is passed then it will be skipped to be used with
	 * setDateTime24hrs / setDateTime12hrs
	 * 
	 * @param sTimeHrs
	 * @param sTimeMin
	 * @param sTimeAMPM
	 * @param is24hrs
	 */
	public void timeSetter(String sTimeHrs, String sTimeMin, String sTimeAMPM, Boolean is24hrs,Boolean isUkRegionDateTime) {
		int cylinderPosition=0;
		if (sTimeHrs != "0") {
			if(isUkRegionDateTime==true) {
				 cylinderPosition=1;

			}else {
				 cylinderPosition=BaseLib.sDeviceType.equalsIgnoreCase("phone")?0:1;

			}
			int breakCount=0;
			if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
				//Phone needs multiple calls to date picker to set the correct date
				while(!getEleDatePickerPopUp().get(cylinderPosition).getText().replaceAll("[^0-9]", "").equals(sTimeHrs) && breakCount<20) {
					getEleDatePickerPopUp().get(cylinderPosition).sendKeys(sTimeHrs);
					breakCount++;
				}
			}
			else {
				//Tablet
				while(!getEleDatePickerPopUp().get(cylinderPosition).getText().replaceAll("[^0-9]", "").equals(sTimeHrs) && breakCount<20) {
					getEleDatePickerPopUp().get(cylinderPosition).sendKeys(sTimeHrs);
					breakCount++;
				}

			}

		}
		if (sTimeMin != "0") {
			int breakCount=0;
			if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
			//Phone needs multiple calls to date picker to set the correct date
				while(!getEleDatePickerPopUp().get(2).getText().replaceAll("[^0-9]", "").equals(sTimeMin) && breakCount<20) {
					getEleDatePickerPopUp().get(2).sendKeys(sTimeMin);
					breakCount++;
				}
			}
			else {
				while(!getEleDatePickerPopUp().get(2).getText().replaceAll("[^0-9]", "").equals(sTimeMin) && breakCount<20) {
					getEleDatePickerPopUp().get(2).sendKeys(sTimeMin);
					breakCount++;
				}
			}

		}
		if (is24hrs == false) {
			if (sTimeAMPM != "0") {
				getEleDatePickerPopUp().get(3).sendKeys(sTimeAMPM);

			}
		}

	}

	@AndroidFindBy(id="android:id/select_dialog_listview")
	@iOSBy(xpath ="//XCUIElementTypePickerWheel[@type='XCUIElementTypePickerWheel']")
	private WebElement elPicklistValues;
	public WebElement getElPicklistValues() {

		return elePicklistValue;
	}

	/**
	 * To get all the values from the picklist and verify with the given set of
	 * values in the UI
	 * 
	 * @return
	 * @throws InterruptedException
	 * @param sActualValues = Values sent to verify the picklist fields on the UI.
	 */
	public String[] getAllPicklistValues(CommonUtility commonUtility, WorkOrderPO workOrderPO, String[] sActualValues)
			throws InterruptedException {
		String[] sVals = new String[sActualValues.length];
		String sPrevVal = "";
		String sCurrVal = "";

		switch(BaseLib.sOSName.toLowerCase()) {
		case "android":
			switchContext("Native");
			//List<WebElement> listElemets = driver.findElements(By.id("android:id/select_dialog_listview"));
			List<WebElement> listElemets = driver.findElements(By.id("android:id/text1"));

			System.out.println("Size of pl = "+listElemets.size());
			for (int i = 0; i < listElemets.size(); i++) {
				WebElement listItem = listElemets.get(i);
				sCurrVal = listItem.getText();
				Thread.sleep(10000);
				try {
					System.out.println("-----------" + listItem);

					System.out.println("sCurrVal -----------" + sCurrVal);

				} catch (Exception e) {
					System.out.println("The picklist Values couldn't be fetched" + e);
				}

				sPrevVal = sCurrVal;
				sVals[i] = sPrevVal;
			}
			for (String string : sVals) {
				System.out.println("Array read = " + string);
			}
			switchContext("WebView");
			break;



		case "ios":

			switchContext("Native");
			for (int i = 0; i <= sActualValues.length; i++) {
				IOSElement PFS = (IOSElement) driver
						.findElement(By.xpath("//XCUIElementTypePickerWheel[@type='XCUIElementTypePickerWheel']"));
				sCurrVal = PFS.getText();
				Thread.sleep(10000);
				try {
					System.out.println("-----------" + PFS);

					System.out.println("sCurrVal -----------" + sCurrVal);

				} catch (Exception e) {
					System.out.println("The picklist Values couldn't be fetched" + e);
				}

				sPrevVal = sCurrVal;
				sVals[i] = sPrevVal;

				try {
					commonUtility.scrollPickerWheel(i, 1);
				} catch (Exception e) {
					break;
				}

			}
			for (String string : sVals) {
				System.out.println("Array read = " + string);

			}
			switchContext("WebView");
			break;

		}

		return sVals;
	}

	/**
	 * For Phone : To get all the values from the picklist and verify with the given set of
	 * values in the UI
	 * 
	 * @return
	 * @throws InterruptedException
	 * @param sActualValues = Values sent to verify the picklist fields on the UI.
	 */
	public String[] getAllPicklistValues(CommonUtility commonUtility, String[] sActualValues) throws InterruptedException {
		String[] sVals = new String[sActualValues.length];
		String sCurrVal = "";
		List<WebElement> listElemets=null;
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			listElemets = driver.findElements(By.xpath("//*[@*='FORM.PICKLIST.SCROLLVIEW']//*[@class='android.widget.TextView']"));

		}else {
			listElemets = driver.findElements(By.xpath("//XCUIElementTypeOther[contains(@name,'FORM.PICKLIST.ITEM')]"));

		}

		System.out.println("Size of pl = "+listElemets.size());
		for (int i = 0; i < listElemets.size(); i++) {
			WebElement listItem = listElemets.get(i);
			sCurrVal = listItem.getText();
			Thread.sleep(1000);
			try {
				System.out.println("-----------" + listItem);

				System.out.println("sCurrVal -----------" + sCurrVal);

			} catch (Exception e) {
				System.out.println("The picklist Values couldn't be fetched" + e);
			}
			sVals[i] = sCurrVal;
		}
		for (String string : sVals) {
			System.out.println("Array read = " + string);
		}
		return sVals;
	}

	/**
	 * Wait until the string is displayed or time elapsed in seconds and returns
	 * true or false
	 * 
	 * @param wElement
	 * @param sExpectedValue
	 * @param lTime
	 * @return
	 * @throws InterruptedException
	 */
	public boolean waitForString(WebElement wElement, String sExpectedValue, int lTime) throws InterruptedException {

		String sSuccessString = null;
		int lElapsedTime = 0;

		while (lElapsedTime != lTime) {
			waitforElement(wElement, 1);
			// Ignore Errors from string not found and wait
			try {
				sSuccessString = wElement.getText();
			} catch (Exception e) {
			}
			if (sSuccessString.equals(sExpectedValue)) {
				return true;
			}
			lElapsedTime++;

		}
		// If string not found after waiting return false
		return false;

	}

	/**
	 * Read a text file
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public String readTextFile(String filePath) throws Exception {
		String data = "";
		data = new String(Files.readAllBytes(Paths.get(filePath)));

		System.out.println("PATH: "+filePath+" file read as = " + data);
		return data;

	}

	/**
	 * Write to a text file
	 * 
	 * @param filePath
	 * @param data
	 * @throws IOException
	 */
	public void writeTextFile(String filePath, String data,Boolean... bppend) throws IOException {
		
		Boolean appendFlag = bppend.length>0?bppend[0]:false;

		File file = new File(filePath);
		// Create the file
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
		}
		
		

		// Write Content an create the shell or bat file
		FileWriter writer = new FileWriter(file,appendFlag);
		writer.write(data);
		writer.close();
		System.out.println("File path "+filePath+" Written data = " + data);

	}

	/**
	 * Verify if the sahi execution was a success based on the sahResultCommon.txt
	 * file having true or false
	 * 
	 * @return
	 */
	public Boolean verifySahiExecution() {
		String sahiResultCommon = null;
		String sFilePath = "/auto/SVMX_Catalyst/Executable/sahiResultCommon.txt";
		Boolean result = false;
		try {
			sahiResultCommon = readTextFile(sFilePath);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// if we add "true,false,true" in the results file as comma separated values
		String[] arrValues = sahiResultCommon.split(",");
		int i = 0;
		for (String arrValRead : arrValues) {
			System.out.println("File boolean state values in arrValues[" + i + "] = " + arrValRead);
			i++;
		}

		if (arrValues[0].toLowerCase().equals("true")) {

			System.out.println("Its a Match , Sahi exceution state in file = " + sahiResultCommon);
			// In case you want to stop even if the script passes
			result = true;

		} else {
			System.out.println("Its Not a Match , Sahi exceution state in file = " + sahiResultCommon);
			result = false;
		}
		File file = new File(sFilePath);
		if (file.delete()) {
			System.out.println("Resetting State by deleting file : " + sFilePath);
		} else
			System.out.println("No file to reset : " + sFilePath);

		return result;
	}


	public boolean ProcessCheck(RestServices restServices, String sProcessName, String sScriptName,
			String sTestCaseId) throws Exception {
		String sProcessCheck = restServices.restGetSoqlValue(
				"SELECT+SVMXC__Dispatch_Process_Status__c+FROM+SVMXC__ServiceMax_Processes__c+WHERE SVMXC__Name__c =\'"
						+ sProcessName + "\'"+"ORDER+BY+SVMXC__Dispatch_Process_Status__c+ASC",
				"SVMXC__Dispatch_Process_Status__c");
		System.out.println("sProcess check" + sProcessCheck);

		try {
			if (sProcessCheck.equals("Incomplete")) {
				System.out.println("Process in InComplete Status");
				ExtentManager.logger.log(Status.FAIL,
						"SFM PROCESS :'"+sProcessName+"':is in InComplete State: PLEASE RECHECK SFM PROCESS!!!!");
				Assert.assertFalse(sProcessCheck.equals("Incomplete"), "Status is in Incomplete State");
				return false;

			} else if (sProcessCheck.equals("Complete")) {
				System.out.println(" SFM Process '"+sProcessName+"' already exists:Proceeding to FSA Automation:");
				ExtentManager.logger.log(Status.INFO, "SFM PROCESS '"+sProcessName+"' Already Exists and hence proceeding to FSA Client");
				return false;

			}

		} catch (NullPointerException e) {

			System.out.println("SFM Process returned is null, Creating SFM Process!");
			executeSahiScript(sScriptName);
			ExtentManager.logger.log(Status.PASS,"Testcase " + sTestCaseId + "Sahi verification is successful");
			return true;

		}
		return false;
	}

	/**
	 * Function to click on Allow Pop Up , use try catch in the calling script if
	 * needed to avoid false positives
	 * 
	 * @throws InterruptedException
	 */
	public void clickAllowPopUp() throws InterruptedException {

		Thread.sleep(CommonUtility.iLowSleep);
		switchContext("Native");
		try {
			try {
				System.out.println("Attempting to Click on Always Allow Pop up");
				driver.findElement(By.xpath("//*[text()= 'Always Allow'")).click();
				System.out.println("Sucessfully Clicked on Always Allow Pop up");


			} catch (Exception e) {
				System.out.println("Attempting to Click on Allow Pop up");
				driver.findElement(By.xpath("//*[text()= 'Allow'")).click();
				System.out.println("Sucessfully Clicked on Allow Pop up");

			}

		} catch (Exception e) {
			System.out.println("  ***** Suppresed exception as popups not displayed");
		} 
		Thread.sleep(CommonUtility.iLowSleep);
		switchContext("Webview");
		Thread.sleep(CommonUtility.iLowSleep);

	}

	public void lookupSearchOnly(String value) throws InterruptedException {
		tap(getElesearchTap());
		getElesearchTap().clear();
		getElesearchTap().sendKeys(value);
		tap(getElesearchButton());
	}

	/**
	 * General Function to simply scroll any picker wheel based on -ve or +ve index
	 * and number times to scroll, see getAllPicklistValues() method for usage
	 * example
	 * 
	 * @param iWheelIndex
	 * @param scrollNum
	 */
	public void scrollPickerWheel(int iWheelIndex, int scrollNum) {
		switchContext("Native");
		int i = 0;
		int newTempVal = scrollNum;
		scrollNum = Math.abs(scrollNum);
		for (i = 0; i < scrollNum; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			Map<String, Object> params = new HashMap<>();
			if (newTempVal < 0) {
				System.out.println("Scrolling Down " + scrollNum);
				params.put("order", "previous");

			} else {
				System.out.println("Scrolling Up " + scrollNum);
				params.put("order", "next");

			}
			params.put("offset", 0.15);
			params.put("element", getElePickerWheelPopUp());
			js.executeScript("mobile: selectPickerWheelValue", params);
		}
	}

	/**
	 * Custom function to return the boolean value for isDiplayed
	 * 
	 * @param wElement
	 * @return
	 */
	public boolean isDisplayedCust(WebElement wElement) {
		boolean isDis = false;
		if(!BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
			switchContext("Webview");
		}
		
		try {
			
			isDis = wElement.isDisplayed();
			if (isDis) {
				System.out.println("Element is visible");
				return true;
			} else {
				System.out.println("Element is not visible");
				return false;

			}
		} catch (Exception e) {
			System.out.println("Element is not visible");
			return false;
		}

	}

	public void verticalSwipe() {
		switchContext("native");
		Dimension dim = driver.manage().window().getSize();
		int height = dim.getHeight();
		int width = dim.getWidth();
		System.out.println("Height of the Screen is " + height);
		System.out.println("Width of the Screen is " + width);
		int x = width / 2;
		int starty = (int) (height * 0.8);
		int endy = (int) (height * 0.2);
		//		new TouchAction(driver).longPress(new PointOption().withCoordinates(x,starty)).waitAction(new WaitOptions().withDuration(Duration.ofMillis(2000))).moveTo(new PointOption().withCoordinates(x,endy)).release();
		new TouchAction(driver).longPress(new PointOption().withCoordinates(150, 900))
		.waitAction(new WaitOptions().withDuration(Duration.ofMillis(2000)))
		.moveTo(new PointOption().withCoordinates(150, 60)).release();
	}

	/**
	 * Should be called before running any tests/suites if needed. 
	 * Function to initialize and run and pre requisites, please add the relevant
	 * pre requisites here.
	 * 
	 * @param genericLib
	 * @throws Exception
	 */
	public void preReqSetup() throws Exception {

		// running the Sahi Script Pre-requisites - To make My Records to All Records in
		// Mobile Configuration
		executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah");

		executeSahiScript("appium/Scenario_RS_10561_ConfigSync_Alert_Post.sah");

		executeSahiScript("appium/Scenario_RS_10569_ScheduledDataSync_Post.sah");

	}


	public String servicemaxServerVersion(RestServices restServices) throws Exception
	{
		String sMajorVersion = "";
		String sMinorVersion = "";
		String[] salesforceversion = new String[2];

		sMajorVersion = restServices.restGetSoqlValue("SELECT+MajorVersion+FROM+Publisher+WHERE+NamespacePrefix='SVMXC'","MajorVersion");
		sMinorVersion = restServices.restGetSoqlValue("SELECT+MinorVersion+FROM+Publisher+WHERE+NamespacePrefix='SVMXC'","MinorVersion");
		salesforceversion[0] = sMajorVersion;
		salesforceversion[1] = sMinorVersion;
		String sversionv = Arrays.toString(salesforceversion).replaceAll(",",".");
		System.out.println("Sales Force Version : "+sversionv);

		return sversionv;

	}

	public void deleteCalendarEvents(RestServices restServices, CalendarPO calendarPO, String objapi) throws Exception
	{
		System.out.println("Entered Deletion of Calendar Events");

		ArrayList<String> mylist = new ArrayList<String>();
		String queryeventcount = "SELECT count() FROM "+objapi+"";
		restServices.getAccessToken();
		String  eventcount = restServices.restGetSoqlValue(queryeventcount, "totalSize");	
		System.out.println(eventcount);
		int eventcountint=Integer.parseInt(eventcount);

		for (int i = 0; i<eventcountint; i++) {
			String Query = "SELECT Id from "+objapi+" limit 1 offset "+i+"";
			mylist.add(restServices.restGetSoqlValue(Query,"Id")); 
		}

		System.out.println(Arrays.toString(mylist.toArray()));


		for (int i = 0; i < mylist.size(); i++) { System.out.println("deleting ID"+
				mylist.get(i));
		restServices.restDeleterecord(objapi,mylist.get(i)); }

	}

	/**
	 * Take screenshot and return the screenshot path to be consumed in Extent logger
	 * 
	 * e.g: ExtentManager.logger.fail("Fail", MediaEntityBuilder.createScreenCaptureFromPath(commonsUtility.takeScreenShot()).build());
	 * @return
	 */
	public String takeScreenShot() {
		String filePath;
		if(BaseLib.sOSName.toLowerCase().equals("android")) {	
			switchContext("Native");
		}
		filePath = ExtentManager.getScreenshot();
		if(BaseLib.sOSName.toLowerCase().equals("android")) {	
			switchContext("webview");
		}
		return filePath;
	}


	public int getHeaderCount(Object obj) {
		int count = 0;
		if(obj instanceof WorkOrderPO) {
			WorkOrderPO wO = (WorkOrderPO)obj;
			count = Integer.parseInt(wO.getTxtFullNameHeader().getText().substring(wO.getTxtFullNameHeader().getText().indexOf('(')+1,wO.getTxtFullNameHeader().getText().indexOf(')')));
		}

		return count;

	}

	public int getLocHeaderCount(Object obj) {
		int count = 0;
		if(obj instanceof WorkOrderPO) {
			WorkOrderPO wO = (WorkOrderPO)obj;
			count = Integer.parseInt(wO.getTxtLocNameHeader().getText().substring(wO.getTxtLocNameHeader().getText().indexOf('(')+1,wO.getTxtLocNameHeader().getText().indexOf(')')));
		}

		return count;

	}

	/**
	 * Rotate the screen to Portrait
	 * @throws InterruptedException
	 */
	public void custRotateScreenPortrait() throws InterruptedException {
		switchContext("Native");
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		Thread.sleep(3000);
		switchContext("Webview");

	}

	/**
	 * Rotate the screen to Landscape
	 * @throws InterruptedException
	 */
	public void custRotateScreenLandscape() throws InterruptedException {
		switchContext("Native");
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		Thread.sleep(3000);
		switchContext("Webview");


	}

	/**
	 * Injecting properties set from jenkins build into sahi for consumption
	 */
	public void injectJenkinsPropertiesForSahi() {
		String sFilePath = "/auto/sahi_pro/userdata/scripts/Sahi_Project_Lightning/svmx/project_config/appium_config/jenkinsProp_config.sah";
		String sConfigExcelPath = CommonUtility.sConfigPropertiesExcelFile;
		String sConfigExcelSheetName = BaseLib.sSelectConfigPropFile;
		try {
	
			//this.writeTextFile(sFilePath,"var $catalyst_orgtype = \""+System.getenv("Org_Type")+"\"; var $catalyst_appiumOrg = \""+System.getenv("Select_Config_Properties_For_Build")+"\";");
			this.writeTextFile(sFilePath,"var $catalyst_orgtype = \""+BaseLib.sOrgType+"\"; var $catalyst_appiumOrg = \""+BaseLib.sSelectConfigPropFile+"\"; var $catalyst_appiumConfigExcelFilePath = \""+sConfigExcelPath+"\"; var $catalyst_appiumConfigExcelSheetName = \""+sConfigExcelSheetName+"\";");
			System.out.println("Injected jenkins env properties for Sahi in 'jenkinsProp_config.sah' file : "+sFilePath);

		}catch(Exception e) {

		}
	}

	public String getDeviceDate() throws Exception{
		String returnDate="";
		if (BaseLib.sOSName.equalsIgnoreCase("Android")) {
			switchContext("native");
			Map<String, Object> args = new HashMap<>();
			args.put("command", "date");
			returnDate=(driver.executeScript("mobile: shell", args).toString().replace("  ", " "));
			switchContext("webview");
		}
		else if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
			String value="";
			String dateInString=executeLibiMobileDeviceExecFile("idevicedate");
//			String _cmd ="";
//			if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
//				 dateInString=execCommand("/usr/local/Cellar/libimobiledevice/1.2.0_3/bin/idevicedate").trim();
//				 _cmd = execCommand("/usr/local/Cellar/libimobiledevice/1.2.0_3/bin/ideviceinfo");
//					
//			}else {
//			 dateInString=execCommand("/usr/local/Cellar/libimobiledevice/HEAD-8510a9b_3/bin/idevicedate").trim();
//			 _cmd = execCommand("/usr/local/Cellar/libimobiledevice/HEAD-8510a9b_3/bin/ideviceinfo");
//			}
			String[] IOBufferList = executeLibiMobileDeviceExecFile("ideviceinfo").split("\n"); 
			for (String item: IOBufferList) { 
				String[] subItemList = item.split(":"); 
				if (subItemList.length == 2 && subItemList[0].equals("TimeZone")) { 
					value=subItemList[1].trim();
					break;
				} 
			}
			String dateFormat="E MMM d HH:mm:ss z yyyy";
			ZoneId defaultZoneId = TimeZone.getDefault().toZoneId();
			System.out.println("TimeZone : " + defaultZoneId);
			LocalDateTime ldt = LocalDateTime.parse(dateInString.trim().replaceAll("  ", " 0"), DateTimeFormatter.ofPattern(dateFormat));
			ZonedDateTime fromDateTime = ldt.atZone(defaultZoneId);
			ZoneId toZoneId = ZoneId.of(value);
			ZonedDateTime toDateTime = fromDateTime.withZoneSameInstant(toZoneId);
			DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
			returnDate=format.format(toDateTime);
		}
		return returnDate;
	}
	public static String execCommand (String commands) { 
		Process p; 
		try { 
			ProcessBuilder pb = new ProcessBuilder(commands); 
			p = pb.start(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
			return null; 
		} 

		byte[] b = new byte[1024]; 
		int readBytes; 
		StringBuilder sb = new StringBuilder(); 
		InputStream in = p.getInputStream(); 
		try { 
			while ((readBytes = in.read(b)) != -1) { 
				sb.append(new String(b, 0, readBytes)); 
			} 

		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally { 
			try { 
				in.close(); 
			} catch (IOException e) { 
				e.printStackTrace(); 
			} 
			try { 
				p.destroy(); 
			}catch (IllegalThreadStateException e){ 
				e.printStackTrace(); 
			} 
		} 
		return sb.toString(); 
	}

	/**
	 * Function to do a generic swipe up or down
	 * @param sDirectionUpDown
	 * @param iNoOfTimes
	 */
	public void swipeGeneric(String sDirectionUpDown) {

		System.out.println("Scrolling...");
//		switch (BaseLib.sOSName.toLowerCase()) {
//		case "android":

			Dimension dim = driver.manage().window().getSize();
			int height = dim.getHeight();
			int width = dim.getWidth();
			int x = width / 2;
			int top_y;
			int bottom_y;

			if (sDirectionUpDown.equalsIgnoreCase("up")) {
				top_y = (int) (height * 0.80);
				bottom_y = (int) (height * 0.50);
			} else {
				// Reversing
				top_y = (int) (height * 0.50);
				bottom_y = (int) (height * 0.80);
			}

			System.out.println("coordinates :" + x + "  " + top_y + " " + bottom_y);
			TouchAction ts = new TouchAction(driver);
			ts.press(new PointOption().withCoordinates(x, top_y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(new PointOption().withCoordinates(x, bottom_y)).release().perform();

			
			/*	case "ios":
			
			 * JavascriptExecutor js = (JavascriptExecutor) driver; Map<String, Object>
			 * params = new HashMap<>(); if (sDirectionUpDown.equalsIgnoreCase("up")) {
			 * params.put("direction", "up"); } else { params.put("direction", "down");
			 * 
			 * } js.executeScript("mobile: swipe", params);
			 */
		
		//}

	}
	
	/**
	 * Navigate to a horizontal tab based on the tab name
	 * @param sTabName
	 * @return
	 */
	public boolean gotToTabHorizontal(String sTabName) {
		System.out.println("Scrolling Horizontally");
		List<WebElement> wElementNav=null;
		int tabNo =0;
		int i;
		for (i = 0; i < 5; i++) {
			if(BaseLib.sOSName.equalsIgnoreCase("android")) {//For Android
			wElementNav = driver.findElements(By.xpath("//android.widget.HorizontalScrollView//android.widget.TextView"));
			tabNo = wElementNav.size();

			System.out.println("Curernt no of Tabs = "+tabNo);
			try {
				Thread.sleep(300);
				WebElement tabElement = driver.findElement(By.xpath("//android.widget.HorizontalScrollView//*[@text='"+sTabName+"']"));
				if(waitforElement(tabElement,3))
				{
					tabElement.click();
					System.out.println("Tab found after scrolling Horizontally");
					return true;
					//break;
				}	
			}catch(Exception e){}
			System.out.println("Scrolling Horizontally for index "+tabNo);
			wElementNav.get(Math.abs(tabNo-1)).click();
		}
		else {//For IOS
			wElementNav = driver.findElements(By.xpath("//XCUIElementTypeScrollView //XCUIElementTypeButton"));
			tabNo = wElementNav.size();
			System.out.println("Curernt no of Tabs = "+tabNo);
			try {
				Thread.sleep(300);
				WebElement tabElement = driver.findElement(By.xpath("//XCUIElementTypeScrollView //XCUIElementTypeButton[@name='"+sTabName+"']"));
				if(waitforElement(tabElement,3))
				{
					tabElement.click();
					System.out.println("Tab found after scrolling Horizontally");
					return true;
					//break;
				}	
			}catch(Exception e){}
			System.out.println("Scrolling Horizontally for index "+tabNo);
			wElementNav.get(2+i).click();
			}
		}
		System.out.println("Tab Not found after scrolling Horizontally");
		return false;

	}

	/**
	 * TO scroll for IOS or android page wise until element is found and if horizontal tab scroll is needed add the tab name, if it fails then try using the overloaded method "public WebElement custScrollToElementAndClick(String androidTextInElement) "
	 * @param wElement
	 * @param sTabName
	 * @throws InterruptedException
	 */
	public void custScrollToElementAndClick(WebElement wElement,String... sTabName){
		String sTabNameVal = sTabName.length>0?sTabName[0]:"";
		System.out.println("Scrolling to element and clicking");
		if(!sTabNameVal.equals("")) {
			if(!gotToTabHorizontal(sTabNameVal)) {
				System.out.println("Tab name "+sTabNameVal+" not found please pass correct tab name");
				return;
			}
		}
		//If element clicked then return immediately
		try {
			Thread.sleep(300);
			if(waitforElement(wElement,1)) {
				wElement.click();
				System.out.println("Element found and clicked after scrolling");
				return;
			}
		}catch(Exception e){}

		//Try to scroll and click element
		int i;
		for (i = 0; i < 10; i++) {
			swipeGeneric("up");
			try {
				Thread.sleep(300);
				if(waitforElement(wElement,1)) {
					wElement.click();
					System.out.println("Element found and clicked after scrolling");
					return;
					//break;
				}
			}catch(Exception e){}
		}
		System.out.println("Element not found to click after scrolling");
		ExtentManager.logger.log(Status.INFO, "Element not found after scrolling");

	}


	/**
	 * For certain elements to scroll and click we can pass the text/xpath directly to scroll, first try the overloaded method "custScrollToElementAndClick(WebElement webElement) " if it fails use this method
	 * @param androidTextInElementOrXpath
	 * @param isXpath
	 * @throws InterruptedException
	 */
	public void custScrollToElementAndClick(String androidTextInElementOrXpath,boolean... isXpath) throws InterruptedException {

		boolean isXpathOrNot = isXpath.length>0?isXpath[0]:false;
		switch(BaseLib.sOSName.toLowerCase()) {
		case "android":
			Thread.sleep(300);
			WebElement wElement = null;
			System.out.println("Android scrolling");
			if(isXpathOrNot == true) {
				try {
					Thread.sleep(300);
					wElement = driver.findElement(By.xpath(androidTextInElementOrXpath));
					if(waitforElement(wElement,1)) {
						wElement.click();
						System.out.println("Element found after scrolling");
						return;
					}
				}catch(Exception e1){}

				//Try to scroll and click element
				int i;
				for (i = 0; i < 10; i++) {
					swipeGeneric("up");
					try {
						Thread.sleep(300);
						wElement = driver.findElement(By.xpath(androidTextInElementOrXpath));
						if(waitforElement(wElement,1)) {
							point = wElement.getLocation();
							wElement.click();
							System.out.println("Found Coordinates ヽ(´▽`)/ : " + point.getX() + "---" + point.getY());
							System.out.println("Element found and clicked after scrolling");
							return;
							//break;
						}
					}catch(Exception e2){};
					System.out.println("Element not found and clicked after scrolling");
				}

			}else {
				driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\""+androidTextInElementOrXpath+"\"))")).click();
			}
			break;
		case "ios":
			String xpathString = "//*[@label='"+androidTextInElementOrXpath+"']";
			try {
				Thread.sleep(300);
				wElement = driver.findElement(By.xpath(xpathString));
				if(waitforElement(wElement,1)) {
					wElement.click();
					System.out.println("Element found and clicked after scrolling");
					return;
				}
			}catch(Exception e1){}

			//Try to scroll and click element
			int i;
			for (i = 0; i < 10; i++) {
				swipeGeneric("up");
				try {
					Thread.sleep(300);
					wElement = driver.findElement(By.xpath(xpathString));
					if(waitforElement(wElement,1)) {
						point = wElement.getLocation();
						wElement.click();
						System.out.println("Found Coordinates ヽ(´▽`)/ : " + point.getX() + "---" + point.getY());
						System.out.println("Element found and clicked after scrolling");
						return;
						//break;
					}
				}catch(Exception e2){};
				System.out.println("Element not found to click after scrolling");
			}
		}
	}

	/**
	 * For certain elements to only scroll we can pass the text/xpath directly to scroll, first try the overloaded method "custScrollToElementAndClick(WebElement webElement) " if it fails use this method
	 * @param androidTextInElementOrXpath
	 * @param isXpath
	 * @throws InterruptedException
	 */
	public void custScrollToElement(String androidTextInElementOrXpath,boolean... isXpath) throws InterruptedException {
		boolean isXpathOrNot = isXpath.length>0?isXpath[0]:false;

		switch(BaseLib.sOSName.toLowerCase()) {
		case "android":
			Thread.sleep(300);
			WebElement wElement = null;
			System.out.println("Android scrolling");
			if(isXpathOrNot== true) {
				try {
					Thread.sleep(300);
					wElement = driver.findElement(By.xpath(androidTextInElementOrXpath));
					if(waitforElement(wElement,1)) {
						System.out.println("Element found after scrolling");
						return;
					}
				}catch(Exception e1){}

				//Try to scroll
				int i;
				for (i = 0; i < 10; i++) {
					swipeGeneric("up");
					try {
						Thread.sleep(300);
						wElement = driver.findElement(By.xpath(androidTextInElementOrXpath));
						if(waitforElement(wElement,1)) {
							point = wElement.getLocation();
							System.out.println("Found Coordinates ヽ(´▽`)/ : " + point.getX() + "---" + point.getY());
							System.out.println("Element found after scrolling");
							return;
							//break;
						}
					}catch(Exception e2){};
					System.out.println("Element not found after scrolling");
				}

			}else {
				driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\""+androidTextInElementOrXpath+"\"))"));
			}
			break;
		case "ios":
			String xpathString = "//*[@label='"+androidTextInElementOrXpath+"']";
			try {
				Thread.sleep(300);
				wElement = driver.findElement(By.xpath(xpathString));
				if(waitforElement(wElement,3)) {
					System.out.println("Element found after scrolling");
					return;
				}
			}catch(Exception e1){}

			//Try to scroll and click element
			int i;
			for (i = 0; i < 10; i++) {
				swipeGeneric("up");
				try {
					Thread.sleep(300);
					wElement = driver.findElement(By.xpath(xpathString));
					if(waitforElement(wElement,1)) {
						point = wElement.getLocation();
						wElement.click();
						System.out.println("Found Coordinates ヽ(´▽`)/ : " + point.getX() + "---" + point.getY());
						System.out.println("Element found after scrolling");
						return;
						//break;
					}

				}catch(Exception e2){};
				System.out.println("Element not found after scrolling");
			}
		}
	}

	/**
	 * Scroll to a element
	 * @param wElement
	 * @throws InterruptedException
	 */
	public void custScrollToElement(WebElement wElement) throws InterruptedException {
		try {
			Thread.sleep(300);
			if(waitforElement(wElement,1)) {
				System.out.println("Element found and clicked after scrolling");
				return;
			}
		}catch(Exception e){}

		//Try to scroll
		int i;
		for (i = 0; i <= 10; i++) {
			swipeGeneric("up");
			try {
				Thread.sleep(300);
				if(waitforElement(wElement,1)) {
					point = wElement.getLocation();
					System.out.println("Found Coordinates ヽ(´▽`)/ : " + point.getX() + "---" + point.getY());
					System.out.println("Element found aafter scrolling");
					return;
					//break;
				}
			}catch(Exception e){}
		}
		
		for (i = 0; i <=10; i++) {
			swipeGeneric("down");
			try {
				Thread.sleep(300);
				if(waitforElement(wElement,1)) {
					point = wElement.getLocation();
					System.out.println("Found Coordinates ヽ(´▽`)/ : " + point.getX() + "---" + point.getY());
					System.out.println("Element found aafter scrolling");
					return;
					//break;
				}
			}catch(Exception e){}
		}
		System.out.println("Element not found to click after scrolling");
	}

	@FindBy(xpath="//*[@class='android.widget.EditText'][@text='Search Keyword...']")
	private WebElement searchLookup;
	public WebElement getSearchLookup() {
		return searchLookup;
	}

	
	public WebElement getEleSearchListItem(String sName)
	{
		return eleSearchListItem = driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='"+sName+"']"));

	}

	public WebElement getSearchLookupWithText(String sName)
	{
		return eleSearchListItem = driver.findElement(By.xpath("//*[@class='android.widget.EditText'][@text='"+sName+"']"));

	}

	public long getDateDiffInMin(long lInitTimeStart, long lInitTimeEnd) {

		long diffInMillies = Math.abs(lInitTimeStart - lInitTimeEnd);
		long sDiff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
		System.out.println("Time Diff in Min = "+sDiff);
		return sDiff;
	}
	
	public long getDateDiffInSec(long lInitTimeStart, long lInitTimeEnd) {

		long diffInMillies = Math.abs(lInitTimeStart - lInitTimeEnd);
		long sDiff = TimeUnit.SECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		System.out.println("Time Diff in Sec = "+sDiff);

		return sDiff;
	}
	
	public long getDateDiffInMilSec(long lInitTimeStart, long lInitTimeEnd) {

		long diffInMillies = Math.abs(lInitTimeStart - lInitTimeEnd);
		long sDiff = TimeUnit.MILLISECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		System.out.println("Time Diff in MilliSec = "+sDiff);
		return sDiff;
	}

	
	public String gethrsfromdevicetime() throws Exception {
		System.out.println(getDeviceDate());
		String[] time =getDeviceDate().split(" ");
		System.out.println("############"+time[3]);
		String hrs=time[3].substring(0,2);
		//String hrs=time[3];
		System.out.println("hours"+hrs);
		return hrs;
	}
	
	public void clickPopup(WebElement wElement, WebElement popupElement) {
		custScrollToElementAndClick(wElement);
		try {
			popupElement.click();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * Function to return the offset day as string
	 * @param value
	 * @return
	 * @throws Exception 
	 */ 
	public String adddaystocurrentday(int value) throws Exception {

		String[] date = getDeviceDate().split(" ");
		Calendar cal=Calendar.getInstance();
		//System.out.println(date[3]);
		try {
			cal.set(Calendar.DATE,Integer.parseInt(date[3].trim()));
			cal.set(Calendar.YEAR, Integer.parseInt(date[6].trim()));
		} catch (Exception e) {
			cal.set(Calendar.DATE,Integer.parseInt(date[2].trim()));
			cal.set(Calendar.YEAR, Integer.parseInt(date[5].trim()));
		}
		Date date1=new SimpleDateFormat("MMM").parse(date[1].trim());
		cal.set(Calendar.MONTH,date1.getMonth());
		cal.add(Calendar.DATE, value);
		System.out.println("Offset to current date is = "+cal.get(Calendar.DATE));
		String date2=String.valueOf(cal.get(Calendar.DATE));

		return date2;
	
	
	
	
	}

	/**
	 * Function to execute and return the values for libimobiledevice contents like for devicedate,device id etc
	 * @param sLibMobileDeviceExecFile
	 * @param commonUtility
	 * @return
	 * @throws Exception
	 */
	public String executeLibiMobileDeviceExecFile(String sLibMobileDeviceExecFile) throws Exception
	{
		String sDirPath = System.getProperty("user.dir");
		String sShellPath = sDirPath+"//..//Executable//sLibMobileDeviceCommandFileExecutable.sh";
		String sOutPutFile = "/auto/SVMX_Catalyst/Executable/tempFileToRead.txt";
		
		File file = new File(sShellPath);
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write("#!/bin/bash \n cd /usr/local/Cellar/libimobiledevice/ \nfilename=$(ls)\necho $filename  \n$filename/bin/"+sLibMobileDeviceExecFile+" > "+sOutPutFile);
		writer.flush();
		writer.close();
		
		
		// File file2 = new File(sShellPath);
		Runtime.getRuntime().exec("chmod 777 " + file);

		File fileLibDivice = new File("/usr/local/Cellar/libimobiledevice/*/bin/" + sLibMobileDeviceExecFile);
		Runtime.getRuntime().exec("chmod 777 " + fileLibDivice);

		ProcessBuilder processBuilder = new ProcessBuilder(file.getPath());
		Process process = processBuilder.start(); // Start the process.
		process.waitFor(); // Wait for the process to finish.

		String sDataRead = readTextFile(sOutPutFile);
		return sDataRead;
	}

	public static void writeExcelData(String sFilePath, String sSheetName, String sKey, String sValue) throws IOException {
		String sData = null;
		FileInputStream fis = null;
		FileOutputStream fos =null;
		try {
	
			fis = new FileInputStream(sFilePath);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sSheetName);
			int iRowNum = sht.getLastRowNum();
			int k = 0;
			for (int i = 0; i <= iRowNum; i++) {
				int iCellNum = sht.getRow(i).getLastCellNum();
					
					for(int j=0;j<iCellNum;j++)
					{
						//System.out.println("row"+i+"  column"+j);
						if(sht.getRow(i).getCell(j).getStringCellValue().equals(sKey))
							{//sData = sht.getRow(i+1).getCell(j).getStringCellValue();}
							sht.getRow(i+1).createCell(j).setCellValue(sValue);
							break;
							}
					}
					break;
				
			}
			fos = new FileOutputStream(sFilePath);
			wb.write(fos);
			
			wb.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fos.close();
		
	}

	public static String readExcelData(String sFilePath, String sSheetName, String sKey) throws IOException {
		String sData = null;
		FileInputStream fis = new FileInputStream(sFilePath);
		
		try {
	
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sSheetName);
			int iRowNum = sht.getLastRowNum();
			int k = 0;
			for (int i = 0; i < iRowNum; i++) {
				
				//if (sht.getRow(i).getCell(0).toString().equals(sTestCaseID)) {
					int iCellNum = sht.getRow(i).getLastCellNum();
					
					for(int j=0;j<iCellNum;j++)
					{
						if(sht.getRow(i).getCell(j).getStringCellValue().equals(sKey))
							{sData = sht.getRow(i+1).getCell(j).toString();
							break;
							}
							
					}
					
				
				//}
			}
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fis.close();
		return sData;
	}

	/*
	 * @author: LAKSHMI BS Description: To write test data from excel sheet
	 */
	public static void setExcelData(String sTestCaseID, String sKey, String sValue) throws IOException {
		String sData = null;
		FileInputStream fis = null;
		FileOutputStream fos =null;
		try {
	
			fis = new FileInputStream(CommonUtility.sTestDataFile);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet("sTestCaseID");
			int iRowNum = sht.getLastRowNum();
			int k = 0;
			for (int i = 1; i <= iRowNum; i++) {
				if (sht.getRow(i).getCell(0).toString().equals(sTestCaseID)) {
					int iCellNum = sht.getRow(i).getLastCellNum();
					
					for(int j=0;j<iCellNum;j++)
					{
						if(sht.getRow(i).getCell(j).getStringCellValue().equals(sKey))
							{//sData = sht.getRow(i+1).getCell(j).getStringCellValue();}
							sht.getRow(i+1).createCell(j).setCellValue(sValue);
							}
					}
					break;
				}
			}
			fos = new FileOutputStream(CommonUtility.sTestDataFile);
			wb.write(fos);
			
			wb.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fos.close();
		
	}

	/*
	 * @author: LAKSHMI BS Description: To set the settings data in dc_config.properties
	 * data from config file
	 */
	public static void setConfigValue(String sFile, String sKey, String sValue) {
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(new File(sFile));
			prop.load(fis);
			fis.close();
	
			FileOutputStream fos = new FileOutputStream(new File(sFile));
			prop.setProperty(sKey, sValue);
			prop.store(fos, "Updating folder path");
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * @author: LAKSHMI BS Description: To read the basic environment settings from dc_config.properties
	 * data from config file
	 */
	public static String getConfigValue(String sFile, String sKey) {
		Properties prop = new Properties();
		String sValue = null;
		try {
			InputStream input = new FileInputStream(sFile);
			prop.load(input);
			sValue = prop.getProperty(sKey);
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sValue;
	}
	

	public void createShellFile(String sSahiFile) throws IOException
	{
		String sSahiShellFile = System.getProperty("user.dir")+"//..//Executable//sahiExecutable.sh";
		File file = new File(sSahiShellFile);
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write("#!/bin/bash \ncd /auto/sahi_pro/userdata/bin \n./testrunner.sh /auto/sahi_pro/userdata/scripts/Sahi_Project_Lightning/svmx/test_lab/test_cases/" + sSahiFile + " https://test.salesforce.com chrome");
		writer.flush();
		writer.close();
	}
	
	
	
	public boolean executeSahiScript(String sSahiScript,Boolean... bSoftFail ) throws Exception
	{	
		
		boolean bSoftFailFlag = bSoftFail.length > 0 ? bSoftFail[0] : false;
		String sMessage = sSahiScript;

		String sSahiLogPath = "/auto/sahi_pro/userdata/scripts/Sahi_Project_Lightning/offlineSahiLogs/";
		//If not triggered from jenkins use local path
		String sActualLogPath = System.getenv("Run_On_Platform") == null ? "/auto/sahi_pro/userdata/scripts/Sahi_Project_Lightning/offlineSahiLogs/" :  "offlineSahiLogs/" ;
		
		System.out.println("Executing Sahi Pro Script : "+sSahiScript);
		//Create Shell script to execute Sahi file
		createShellFile(sSahiScript);
		File file = new File(CommonUtility.sShellFile);
		Runtime.getRuntime().exec("chmod u+x " +file);
		
		try {
			ProcessBuilder processBuilder= new ProcessBuilder(file.getPath());
			Process process = processBuilder.start(); // Start the process.
			process.waitFor(); // Wait for the process to finish.
			
			Assert.assertTrue(process.exitValue()==0, "Sahi script Passed");
			//Set the path  of sahi reports
			
			if(BaseLib.sSelectConfigPropFile.equalsIgnoreCase("automation_build") || BaseLib.sSelectConfigPropFile.equalsIgnoreCase("fsa_track_build")) {
				//first reference the log file name from local path ,then build a path for the build machine where we archive these logs
				sActualLogPath = sActualLogPath+getLastModifiedFile(sSahiLogPath, "*__*.","html");

			}else {
				sActualLogPath = sActualLogPath+getLastModifiedFile(sSahiLogPath, "*__*.","html");

			}
			
			ExtentManager.logger.log(Status.PASS,"Sahi script [ <a href='"+sActualLogPath+" '>"+sMessage+" </a> ] executed successfully");
				
		} catch (Exception e) {
			//Assert.assertTrue(iProcessStatus==0, "Sahi executed successfully");
			ExtentManager.logger.log(Status.FAIL,"Sahi script [ <a href='"+sActualLogPath+" '>"+sMessage+" </a> ] failed");
			throw e;
		}
		
		//Verifying after the complete Sahi run with no internal errors or failures
		if(verifySahiExecution()) {
		return true;
		}else {
		ExtentManager.logger.log(Status.FAIL,"Sahi script [ <a href='"+sActualLogPath+" '>"+sMessage+" </a> ] failed");
		//if you want the execution to continue after sahi failure
		if(bSoftFailFlag) {
			System.out.println("Execution of Sahi script has failed, but continuing execution as bSoftFlag is set to true");
		}else {
		//Stop execution completely
		Assert.assertTrue(false, "Execution of Sahi script has failed, stopping execution ");
		}
		return false;
		}
		 

	}
	
	/**
	 * Return the last modified file name for a specific extension
	 * 
	 * @param filePath
	 * @param pattern
	 * @param ext
	 * @return
	 */
	public String getLastModifiedFile(String filePath, String pattern, String ext) {
		File lastModifiedFile = null;
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter(pattern + ext);
		File[] files = dir.listFiles(fileFilter);

		if (files.length > 0) {
			// The newest file comes first after sorting
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			lastModifiedFile = files[0];
		}

		System.out.println("Fetching Last Modified File : " + lastModifiedFile.getName().toString());
		return lastModifiedFile.getName().toString();
	}

//Generation of XML files START
	private static Map<String, List<String>> transformToMap(String[][] data) {
        Map<String, List<String>> map = new HashMap<>();
        for (String[] aData : data) {
            String key = aData[1];
            List<String> classes = map.computeIfAbsent(key, k -> new ArrayList<>());
            classes.add(aData[0]);
        }
        return map;
    }

    private static void generateTestSuiteXML(String sXmlFilePath, Map<String, List<String>> dbArr, Map<String, String> parameters) throws IOException {
		

        //Create an instance on TestNG
        TestNG myTestNG = new TestNG();

        //Create an instance of XML Suite and assign a name for it.
        XmlSuite suite = new XmlSuite();
        suite.setName("Failed suite");
        suite.setParameters(parameters);
        dbArr.forEach((key, value) -> {
            XmlTest xmlTest = new XmlTest(suite);
            xmlTest.setName(key);
            value.forEach(eachValue -> {
                XmlClass xmlClass = new XmlClass(eachValue, false);
                xmlTest.getClasses().add(xmlClass);
            });
        }); 

        //Add the suite to the list of suites.
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);

        System.out.println(suite.toXml());
        
        File fileDir = new File(System.getProperty("user.dir") + "/suites/failedSuite");
		try {
			fileDir.mkdir();
		} catch (Exception e) {
			System.out.println("[BaseLib] Exception in creating ExtentReports directory for Reports " + e);
		}
		
        File file = new File(sXmlFilePath);
		// Create the file
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
		}
		
		FileWriter writer = new FileWriter(file,false);
		writer.write(suite.toXml());
		writer.close();
		
        //Set the list of Suites to the testNG object you created earlier.
        myTestNG.setXmlSuites(suites);
    }
    
    public void createFailedTestSuiteXML(ArrayList<String> sFailedTestNameArray){
   	 String[][] data = new String[sFailedTestNameArray.size()][2];
     try {
    	 for(int i=0;i<sFailedTestNameArray.size();i++) {
    		 data[i][0]=sFailedTestNameArray.get(i);
    		 data[i][1]="failedTest";
    	 }

   
		generateTestSuiteXML(sXmlFilePath,transformToMap(data), Collections.emptyMap());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
  //Generation of XML files END
}
