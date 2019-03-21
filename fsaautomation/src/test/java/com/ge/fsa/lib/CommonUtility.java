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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
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
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.ge.fsa.tablet.pageobjects.CalendarPO;
import com.ge.fsa.tablet.pageobjects.WorkOrderPO;

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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
	

	@FindBy(className = "XCUIElementTypePickerWheel")
	// @FindBy(className="android.widget.ListView")
	private WebElement elePickerWheelPopUp;

	public WebElement getElePickerWheelPopUp() {
		return elePickerWheelPopUp;
	}

	@FindBy(xpath = "//*[@name='Done']")
	private WebElement eleDonePickerWheelBtn;

	public WebElement getEleDonePickerWheelBtn() {
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
			Assert.assertTrue(1 < 2, "" + ExtentManager.logger.log(Status.FAIL, "Tap Exception : " + tapExp));
		}
		
		switchContext("Webview");
	}

	public void singleTap(Point point) throws InterruptedException {
		touchAction = new TouchAction(driver);
		touchAction.tap(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset)).perform();
		Thread.sleep(GenericLib.iLowSleep);
	}

	

	// Customised touch Tap
	public void fingerTap(Point point, int iTapCount) throws InterruptedException {
		touchAction = new TouchAction(driver);
		touchAction.moveTo(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset))
				.tap(new TapOptions().withTapsCount(iTapCount)).perform();
		Thread.sleep(GenericLib.iLowSleep);
	}

	// Customised touch LongPress
	public void longPress(WebElement wElement) throws InterruptedException {
		Point point = wElement.getLocation();
		System.out.println("x " + point.getX() + " y " + point.getY());
		switch (BaseLib.sOSName) {
		case "android":

			// For Android add *2 if real device
			switchContext("Native");
			touchAction = new TouchAction(driver);
			touchAction.longPress(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset))
			.perform();
			touchAction.longPress(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset)).release()
					.perform();
			Thread.sleep(GenericLib.iLowSleep);
			switchContext("Webview");
			break;

		case "ios":

			// For IOS
			touchAction = new TouchAction(driver);
			touchAction.longPress(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset))
					.perform();
			Thread.sleep(GenericLib.iLowSleep);
			break;

		}
	}

	// Customised touch Doubletap
	public void doubleTap(WebElement element) throws InterruptedException {
		waitforElement(element, GenericLib.i30SecSleep);

		point = element.getLocation();
		touchAction = new TouchAction(driver);

		// touchAction.tap(new TapOptions().withTapsCount(2).withElement((ElementOption)
		// element)).perform();
		touchAction.tap(new TapOptions().withTapsCount(2).withElement((ElementOption) element)).perform();
		Thread.sleep(GenericLib.iLowSleep);

	}

	// Customised touch Press
	public void press(Point point) throws InterruptedException {
		touchAction = new TouchAction(driver);
		touchAction.press(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset)).perform();
		Thread.sleep(GenericLib.iLowSleep);
	}

	public void swipeUp() {
		touchAction = new TouchAction(driver);
		touchAction.longPress(new PointOption().withCoordinates(150, 900))
				.moveTo(new PointOption().withCoordinates(150, 70)).release();
	}

	public void swipeLeft(WebElement wElement) {
		int offset = 30;
		Point point = wElement.getLocation();
		int x = point.getX();
		int y = point.getY();

		int xOff = x + 100;
		// int yOff = y-100;
		touchAction = new TouchAction(driver);
		touchAction.press(new PointOption().withCoordinates(x, y))
				.waitAction(new WaitOptions().withDuration(Duration.ofMillis(2000)))
				.moveTo(new PointOption().withCoordinates((x - 5), 0)).release().perform();
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
			.moveTo(new PointOption().withCoordinates(xOff, y)).release().perform();
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
		ExtentManager.logger.log(Status.PASS, "Search is successfull");
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
	 * 
	 * @param wElement
	 * @param lTime
	 * @throws InterruptedException
	 */

	public boolean waitforElement(WebElement wElement, int lTime) throws InterruptedException {
		int lElapsedTime = 0;
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
		
		while (lElapsedTime != lTime) {
			Thread.sleep(1000);
			try {
				if (wElement.isDisplayed()) {// If element is displayed break
					System.out.println("Element is displayed");
					//switchContext(context);
					return true;
				}
			} catch (Exception ex) {
			}

			lElapsedTime++;
		}
		System.out.println("Element is not displayed");
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
	public String generaterandomnumber(String value) {

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
		switch (BaseLib.sOSName) {
		case "android":
			switchContext("Webview");
			if(BaseLib.sDeviceType.equalsIgnoreCase("phone")) {
				wElement.click();
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
			getCalendarDone().click();
			}
			switchContext("Webview");
			break;
		case "ios":
			switchContext("Webview");
			wElement.click();
			setDatePicker(0, iDaysToScroll);
			switchContext("Native");
			if (sTimeHrs == "0" && sTimeMin == "0") {
				getEleDonePickerWheelBtn().click();

			} else {
				timeSetter(sTimeHrs, sTimeMin, "", true);
				getEleDonePickerWheelBtn().click();
			}

			switchContext("Webview");
			Thread.sleep(GenericLib.iLowSleep);
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
					timeSetter(sTimeHrs, sTimeMin, "", true);
					getEleDonePickerWheelBtn().click();
				}
				
			}
			switchContext("Webview");
			Thread.sleep(GenericLib.iLowSleep);
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
			tap(wElement, 30, 36);
			switchContext("Native");
			//Set current date if all paramters are "0"
			if(sMonth =="0" && sDay == "0" && sYear =="0") {
				getCalendarDone().click();
			}else {
			String date = getDatePicker().getText();
			date = date + " " + getYearPicker().getText();
			Date currentDate=convertStringToDate("E, MMM dd yyyy", date);
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
				getEleDatePickerPopUp().get(0).sendKeys(sMonth);
				timeSetter(sDay, sYear, "", true);
				getEleDonePickerWheelBtn().click();
			}
			
			Thread.sleep(GenericLib.iLowSleep);
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
		wElement.click();
		switchContext("Native");
		setDatePicker(0, iDaysToScroll);
		if (sTimeHrs == "0" && sTimeMin == "0" && sTimeAMPM == "0") {
			getEleDonePickerWheelBtn().click();

		} else {
			timeSetter(sTimeHrs, sTimeMin, sTimeAMPM, false);
			getEleDonePickerWheelBtn().click();
		}

		switchContext("Webview");
		Thread.sleep(GenericLib.iLowSleep);

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
				params.put("element", getEleDatePickerPopUp().get(iWheelIndex));
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
	public void timeSetter(String sTimeHrs, String sTimeMin, String sTimeAMPM, Boolean is24hrs) {
		if (sTimeHrs != "0") {
			getEleDatePickerPopUp().get(1).sendKeys(sTimeHrs);
		}
		if (sTimeMin != "0") {
			getEleDatePickerPopUp().get(2).sendKeys(sTimeMin);
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

		System.out.println("sahiResultCommon.txt file read as = " + data);
		return data;

	}

	/**
	 * Write to a text file
	 * 
	 * @param filePath
	 * @param data
	 * @throws IOException
	 */
	public void writeTextFile(String filePath, String data) throws IOException {

		File file = new File(filePath);
		// Create the file
		if (file.createNewFile()) {
			System.out.println("File is created!");
		} else {
			System.out.println("File already exists.");
		}

		// Write Content an create the shell or bat file
		FileWriter writer = new FileWriter(file);
		writer.write(data);
		writer.close();
		System.out.println("sahiResultCommon.txt file Write as = " + data);

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
			sahiResultCommon = this.readTextFile(sFilePath);

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

	public void execSahi(GenericLib genericLib, String sScriptName, String sTestCaseID) throws Exception {
		genericLib.executeSahiScript("appium/" + sScriptName + ".sah", sTestCaseID);
		if(verifySahiExecution()) {
			
			System.out.println("PASSED");
		}
		else 
		{
			System.out.println("FAILED");
			

			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
			assertEquals(0, 1);
	}
	}

	public boolean ProcessCheck(RestServices restServices, GenericLib genericLib, String sProcessName,
			String sScriptName, String sTestCaseId) throws Exception {
			String sProcessCheck = restServices.restGetSoqlValue(
				"SELECT+SVMXC__Dispatch_Process_Status__c+FROM+SVMXC__ServiceMax_Processes__c+WHERE SVMXC__Name__c =\'"
						+ sProcessName + "\'",
				"SVMXC__Dispatch_Process_Status__c");
		System.out.println("sProcess check" + sProcessCheck);

		try {
			if (sProcessCheck.equals("Incomplete")) {
				System.out.println("Process in InComplete Status");
				ExtentManager.logger.log(Status.FAIL,
						"SFM PROCESS in InComplete State: PLEASE RECHECK SFM PROCESS!!!!");
				Assert.assertFalse(sProcessCheck.equals("Incomplete"), "Status is in Incomplete State");
				return false;

			} else if (sProcessCheck.equals("Complete")) {
				System.out.println("Process already exists:Proceeding to FSA Automation");
				ExtentManager.logger.log(Status.PASS, "SFM PROCESS Already Exists and hence proceeding to FSA Client");
				return false;

			}

		} catch (NullPointerException e) {

			System.out.println("SFM Process returned is null, Creating SFM Process!");
			execSahi(genericLib, sScriptName, sTestCaseId);
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

		Thread.sleep(GenericLib.iLowSleep);
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
			Thread.sleep(GenericLib.iLowSleep);
			switchContext("Webview");
			Thread.sleep(GenericLib.iLowSleep);
		
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
		switchContext("Webview");
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
	public void preReqSetup(GenericLib genericLib) throws Exception {

		// running the Sahi Script Pre-requisites - To make My Records to All Records in
		// Mobile Configuration
		genericLib.executeSahiScript("appium/setDownloadCriteriaWoToAllRecords.sah");
		Assert.assertTrue(verifySahiExecution(), "Execution of Sahi script is failed");

		genericLib.executeSahiScript("appium/Scenario_RS_10561_ConfigSync_Alert_Post.sah");
		Assert.assertTrue(verifySahiExecution(), "Execution of Sahi script is failed");

		genericLib.executeSahiScript("appium/Scenario_RS_10569_ScheduledDataSync_Post.sah");
		Assert.assertTrue(verifySahiExecution(), "Execution of Sahi script is failed");

	}

	
	 public String servicemaxServerVersion(RestServices restServices, GenericLib genericLib) throws Exception
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
			try {
				this.writeTextFile(sFilePath,"var $catalyst_orgtype = \""+System.getenv("Org_Type")+"\"; var $catalyst_appiumOrg = \""+System.getenv("Select_Config_Properties_For_Build")+"\";");
				System.out.println("Injected jenkins env properties for Sahi in 'jenkinsProp_config.sah' file : "+sFilePath);

			}catch(Exception e) {
				
			}
		}
		public String getDeviceDate() throws ParseException{
			String returnDate="";
			if (BaseLib.sOSName.equalsIgnoreCase("Android")) {
				switchContext("native");
				Map<String, Object> args = new HashMap<>();
				args.put("command", "date");
				returnDate=(driver.executeScript("mobile: shell", args).toString());
				switchContext("webview");
			}
			else if(BaseLib.sOSName.equalsIgnoreCase("ios")) {
				String value="";
				String dateInString=execCommand("/usr/local/Cellar/libimobiledevice/HEAD-92c5462_3/bin/idevicedate").trim();
				String _cmd = execCommand("/usr/local/Cellar/libimobiledevice/HEAD-92c5462_3/bin/ideviceinfo");
				String[] IOBufferList = _cmd.split("\n"); 
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
		        LocalDateTime ldt = LocalDateTime.parse(dateInString.replaceAll("  ", " 0"), DateTimeFormatter.ofPattern(dateFormat));
		        ZonedDateTime fromDateTime = ldt.atZone(defaultZoneId);
		        ZoneId toZoneId = ZoneId.of(value);
		        ZonedDateTime toDateTime = fromDateTime.withZoneSameInstant(toZoneId);
		        DateTimeFormatter format = DateTimeFormatter.ofPattern(dateFormat);
		        System.out.println(format.format(toDateTime));
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
			switch (BaseLib.sOSName.toLowerCase()) {
			case "android":

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

				break;
			case "ios":
				JavascriptExecutor js = (JavascriptExecutor) driver;
				Map<String, Object> params = new HashMap<>();
				if (sDirectionUpDown.equalsIgnoreCase("up")) {
					params.put("direction", "up");
				} else {
					params.put("direction", "down");

				}
				js.executeScript("mobile: swipe", params);

				break;
			}

		}

		public void custScrollToElementAndClick(WebElement webElement) throws InterruptedException {
			Thread.sleep(3000);
			int i;
			for (i = 0; i < 5; i++) {
				try {
				webElement.click();
				if(BaseLib.sOSName.equalsIgnoreCase("android")) {
					return;
				}
				
				}catch(Exception e){}
				
				swipeGeneric("up");
				
				try {
					Thread.sleep(1000);
					webElement.click();
					return;
				}catch(Exception e){}
				
			}
			System.out.println("Element not found to click after scrolling");

		}
}
