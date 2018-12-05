package com.ge.fsa.pageobjects;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static org.testng.Assert.assertTrue;


public class CommonsPO
{
	public CommonsPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	AppiumDriver driver = null;
	TouchAction touchAction = null;	
	IOSTouchAction iosTouchAction = null;
	Iterator<String> iterator =null;
	public static String sNativeApp = null;
	public static String sWebView = null;		
	int xOffset = 15;
	int yOffset = 18;
	int iWhileCnt =0;
	long lElapsedTime=0L;
	Point point = null;
	public BaseLib baseLib = new BaseLib();		

	@FindBy(className="XCUIElementTypePickerWheel")	
	//	@FindBy(className="android.widget.ListView")	
	private WebElement elePickerWheelPopUp;
	public  WebElement getElePickerWheelPopUp()
	{
		return elePickerWheelPopUp;
	}

	@FindBy(xpath="//*[@name='Done']")
	private WebElement eleDonePickerWheelBtn;
	public WebElement getEleDonePickerWheelBtn()
	{
		return eleDonePickerWheelBtn;
	}

	@FindBy(xpath="//div[@class='x-inner x-container-inner x-align-center x-pack-start x-layout-vbox x-vertical x-layout-box x-component-inner x-widthed x-heighted']//input[@placeholder='Search'][@class='x-input-el']")
	private WebElement elesearchTap;
	public WebElement getElesearchTap()
	{
		return elesearchTap;
	}

	@FindBy(xpath="//span[text()='Search']")
	private WebElement elesearchButton;
	public WebElement getElesearchButton()
	{
		return elesearchButton;
	}



	private WebElement eleSearchListItem;
	public WebElement getElesearchListItem(String searchName)
	{
		//eleSearchListItem = driver.findElement(By.xpath("//div[@class='x-inner-el'][text()='"+searchName+"']"));
		eleSearchListItem = driver.findElement(By.xpath("//*[.='"+searchName+"'][@class = 'x-gridcell']"));
		return eleSearchListItem;
	}





	//Customized touch Tap
	/**
	 * Tap an element by location points, passing the optional parameters optionalOffsetPointsxy will tap on the x&y offset provided 
	 * useage : tap(element,pointx,pointy)
	 * 
	 * @param el
	 * @param optionalOffsetPointsxy
	 * @throws InterruptedException
	 */
	public void tap(WebElement  wElement, int... optionalOffsetPointsxy) throws InterruptedException{
		Thread.sleep(3000);

		Boolean clickPassed = false;
		Boolean tapPassed = false;
		Exception tapExp = null;
		int x = 0;
		int y = 0;
		
		Integer xNewOffset = optionalOffsetPointsxy.length > 0 ? optionalOffsetPointsxy[0] : null;
		Integer yNewOffset = optionalOffsetPointsxy.length > 1 ? optionalOffsetPointsxy[1] : null;

		try {
			//Wait for the complete coordinates to be generated, E.g if (0,0) (0,231) (12,0), then we will wait for both coordinates to be non-zero.
			for (int i = 0; i < 3; i++) {

				try{point =  wElement.getLocation();}catch(Exception e) {}

				if (point.getX() == 0 || point.getY() == 0) {
					System.out.println("Waiting... for Coordinates ¯\\_(ツ)_/¯ : " + point.getX() + "---" + point.getY());
					Thread.sleep(1000);

				} else {
					System.out.println("Found Coordinates ヽ(´▽`)/ : " + point.getX() + "---" + point.getY());
					break;
				}

			}

			String printElement = StringUtils.substringAfter(wElement.toString(), "->");
			System.out.println("Acting on element : "+ printElement +" " +  wElement.getText() + " " +  wElement.getTagName()+" "+wElement.getLocation());

			//Set the custom or default offsets to x & y
			if (xNewOffset != null) {
				x = point.getX()+xNewOffset;
				y = point.getY()+yNewOffset;
				System.out.println("Using Custom Offset Points xNewOffset = "+(xNewOffset)+" yNewOffset = "+(yNewOffset)+ " on "+x + "," + y);
			} else {
				x = point.getX()+xOffset;
				y = point.getY()+yOffset;
				System.out.println("Using Offset Points xOffset  = "+(xOffset)+" yOffset s= "+(yOffset)+ " on "+x + "," + y);

			}
			
			//Switch the tap based on ANDROID or WINDOWS
			switch(BaseLib.sOSName) {
			case "android":
				//For Android add *2 if real device
				//Since in android now has clicks and taps alternatively do a click then a tap
				try {wElement.click(); clickPassed = true;System.out.println("Click passed"); }catch(Exception e) { System.out.println("Click failed");clickPassed = false; tapExp = e;}
				
				switchContext("Native");
				System.out.println("Android Tapping ");
				switchContext("Native");
				TouchAction andyTouchAction = new TouchAction(driver);
				andyTouchAction.tap(new PointOption().withCoordinates(x, y)).perform();
				switchContext("Webview");
				break;
				
			case "ios":
				//For IOS
				//Since in IOS now has clicks and taps alternatively do a click then a tap
				try {wElement.click(); clickPassed = true;System.out.println("Click passed"); }catch(Exception e) { System.out.println("Click failed");clickPassed = false; tapExp = e;}
		
				System.out.println("IOS Tapping ");
				TouchAction iosTouchAction = new TouchAction(driver);
				iosTouchAction.tap(new PointOption().withCoordinates(x, y)).perform();
				
			default:
				System.out.println("OS Error");
				break;
			}

			System.out.println("Tap passed");
			tapPassed = true;
		}

		catch(Exception e) {
			tapExp = e;
			tapPassed = false;
			System.out.println("Tap failed");

		}


		Thread.sleep(3000);
		if( clickPassed == false && tapPassed == false) {
			System.out.println("Tap Exception : " + tapExp);

			Assert.assertTrue(1<2, ""+ExtentManager.logger.log(Status.FAIL,"Tap Exception : " + tapExp));
		}

	}



	public void singleTap(Point point) throws InterruptedException
	{
		touchAction = new TouchAction(driver);
		touchAction.tap(new PointOption().withCoordinates(point.getX()+xOffset, point.getY()+yOffset)).perform();
		Thread.sleep(GenericLib.iLowSleep);
	}

	//Customised touch tap version 2.0
	//	public void tap(WebElement element) throws InterruptedException
	//	{
	//
	//			waitforElement(element, GenericLib.i30SecSleep);
	//		point = element.getLocation();
	//						iosTouchAction = new IOSTouchAction(driver);
	//					iosTouchAction.tap(new PointOption().withCoordinates(point.getX()+xOffset, point.getY()+yOffset)).perform();
	//
	//
	///*
	//				touchAction = new TouchAction(driver);
	//				touchAction.tap(new PointOption().withCoordinates(point.getX()+xOffset, point.getY()+yOffset)).perform();
	//*/
	//			Thread.sleep(GenericLib.iLowSleep);
	//			}

	//			public void tap(WebElement element, int...iOffset) throws InterruptedException
	//			{
	//
	//				System.out.println(iOffset[0] +  "       y cordi"+iOffset[1]);
	//				waitforElement(element, GenericLib.i30SecSleep);
	//				//point = element.getLocation();
	//				IOSTouchAction touchAction= new IOSTouchAction(driver);
	//				touchAction.tap(PointOption.point(element.getLocation().getX()+iOffset[0], element.getLocation().getY()+iOffset[1])).perform();
	//				//iosTouchAction.tap(new PointOption().withCoordinates(point.getX()+iOffset[0], point.getY()+iOffset[1])).perform();
	//
	//
	//				touchAction = new TouchAction(driver);
	//				touchAction.tap(new PointOption().withCoordinates(point.getX()+xOffset, point.getY()+yOffset)).perform();
	//
	//				Thread.sleep(GenericLib.iLowSleep);
	//			}


	//Customised touch Tap
	public void fingerTap(Point point, int iTapCount) throws InterruptedException
	{
		touchAction = new TouchAction(driver);
		touchAction.moveTo(new PointOption().withCoordinates(point.getX()+xOffset, point.getY()+yOffset)).tap(new TapOptions().withTapsCount(iTapCount)).perform();
		Thread.sleep(GenericLib.iLowSleep);
	}

	//Customised touch LongPress
	public void longPress(WebElement wElement) throws InterruptedException {
		Point point = wElement.getLocation();
		System.out.println("x " + point.getX() + " y " + point.getY());
		switch (BaseLib.sOSName) {
		case "android":

			// For Android add *2 if real device
			switchContext("Native");
			touchAction = new TouchAction(driver);
			touchAction.longPress(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset)).perform();
			Thread.sleep(GenericLib.iLowSleep);
			switchContext("Webview");
			break;

		case "ios":

			// For IOS
			touchAction = new TouchAction(driver);
			touchAction.longPress(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset)).perform();
			Thread.sleep(GenericLib.iLowSleep);
			break;

		}
	}

	//Customised touch Doubletap
	public void doubleTap(WebElement  element) throws InterruptedException
	{
		waitforElement(element, GenericLib.i30SecSleep);

		point = element.getLocation();
		touchAction = new TouchAction(driver);

		//touchAction.tap(new TapOptions().withTapsCount(2).withElement((ElementOption) element)).perform();
		touchAction.tap(new TapOptions().withTapsCount(2).withElement((ElementOption)element)).perform(); 
		Thread.sleep(GenericLib.iLowSleep);

	}

	//Customised touch Press
	public void press(Point point) throws InterruptedException
	{
		touchAction = new TouchAction(driver);
		touchAction.press(new PointOption().withCoordinates(point.getX()+xOffset, point.getY()+yOffset)).perform();
		Thread.sleep(GenericLib.iLowSleep);
	}

	public void swipeUp()
	{	
		touchAction = new TouchAction(driver);
		touchAction.longPress(new PointOption().withCoordinates(150, 900)).moveTo(new PointOption().withCoordinates(150, 70)).release();
	}

	public void swipeLeft(WebElement  wElement)
	{	int offset = 30;
	Point point =  wElement.getLocation();
	int x = point.getX();
	int y = point.getY();

	int xOff = x+100;
	//int yOff = y-100;
	touchAction = new TouchAction(driver);
	touchAction.press(new PointOption().withCoordinates(x, y)).waitAction(new WaitOptions().withDuration(Duration.ofMillis(2000))).moveTo(new PointOption().withCoordinates((x-5), 0)).release().perform();
	}


	public void Enablepencilicon(WebElement  wElement)
	{	int offset = 30;
	Point point =  wElement.getLocation();
	int x = point.getX();
	int y = point.getY();

	int xOff = x+100;
	//int yOff = y-100;
	touchAction = new TouchAction(driver);
	//touchAction.press(new PointOption().withCoordinates(x, y)).moveTo(new PointOption().withCoordinates(20, 20)).release().perform();
	//touchAction.press(new PointOption().withCoordinates(x, y)).waitAction(new WaitOptions().withDuration(Duration.ofMillis(2000))).moveTo(new PointOption().withCoordinates((x-5), 0)).release().perform();

	touchAction.press(new PointOption().withCoordinates(x,y)).waitAction(new WaitOptions().withDuration(Duration.ofMillis(2000))).moveTo(new PointOption().withCoordinates(x,y)).release().perform();
	//touchAction.tap(new PointOption().withCoordinates(x,y));
	}


	//To search the element scrolling
	public void getSearch(WebElement wElement) throws InterruptedException
	{
		while(iWhileCnt<=7) 
		{	
			try {
				waitforElement(wElement, GenericLib.iLowSleep);
				Assert.assertTrue(wElement.isDisplayed(),"Failed to scroll to search");
				ExtentManager.logger.log(Status.PASS,"Search is successfull");
				System.out.println("Search is displayed");
				break;
			}catch(Exception e) {swipeUp();}			
			iWhileCnt++;
		}
		Thread.sleep(5000);
	}

	/**
	 * To switch context between Native and Webview, defaults to Webview always
	 * @param sContext
	 */
	public void switchContext(String sContext) {		

		Set contextNames = driver.getContextHandles();
		// prints out something like NATIVE_APP \n WEBVIEW_1 since each time the
		// WEBVIEW_2,_3,_4 name is appended by a new number we need to store is a
		// global variable to access across
		System.out.println("Available Contexts = " + contextNames);

		sNativeApp = contextNames.toArray()[0].toString();
		sWebView = contextNames.toArray()[1].toString();
		try {
			if (sContext.equalsIgnoreCase("Native")) {
				driver.context(sNativeApp);
				System.out.println("Setting Context = "+sNativeApp);
			} else {
				driver.context(sWebView);
				System.out.println("Setting Context = "+sWebView);

			}
		} catch (Exception e) {
			// TODO: handle exceptions
			System.out.println("Could not find switch the context");
		}

	}

	/**
	 * Set the pickerwheel(ios)/picklist(android) value in ios or android
	 * @param wElement
	 * @param sValue
	 * @throws InterruptedException
	 */
	public void setPickerWheelValue( WebElement wElement, String sValue) throws InterruptedException
	{

		switch(BaseLib.sOSName) {
		case "android":
			tap(wElement,16,20);
			Thread.sleep(2000);
			switchContext("Native");
			driver.findElement(By.xpath("//*[@class='android.widget.CheckedTextView'][contains(@text,'"+sValue+"')]")).click();
			switchContext("WebView");
			break;

		case "ios" :
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
	 * @param wElement
	 * @param lTime
	 * @throws InterruptedException
	 */

	public void waitforElement(WebElement wElement, long lTime) throws InterruptedException
	{ long lElapsedTime = 0;
	System.out.println("Time to Wait : "+lTime+" sec");
	String printElement = StringUtils.substringAfter(wElement.toString(), "->");
	System.out.println("Waiting For Element : "+printElement);
	while(lElapsedTime!=lTime)
	{
		Thread.sleep(1000);
		try{
			if(wElement.isDisplayed())
			{//If element is displayed break
				break;
			}
		}catch(Exception ex) {}
		lElapsedTime++;

	}


	}

	// This method will search the required value and then click on it 
	public void lookupSearch(String value)throws InterruptedException
	{

		tap(getElesearchTap());
		getElesearchTap().clear();
		getElesearchTap().sendKeys(value);
		tap(getElesearchButton());
		tap(getElesearchListItem(value));

	}
	/*
	 * This method is used to generate the Random value times stamped with current time
	 */
	public String generaterandomnumber(String value)
	{

		String date = new SimpleDateFormat("ddMMyyyyHHmmss").format(System.currentTimeMillis( ));
		System.out.println(date);
		String randomstring = value + date;
		System.out.println(randomstring);

		return randomstring;
	}

	/**
	 * Set the 24 hrs time form the date picker wheels, passing 0 for sTimeHrs,sTimeMin will set the present date
	 * 
	 *for specific values:
	 *setDateTime24hrs( workOrderPo.getEleIBScheduledTxtFld(), 1, “02”,”30”)
	 *
	 *for only moving day and leaving default hrs and min :
	 *setDateTime24hrs( workOrderPo.getEleIBScheduledTxtFld(), 1, “0”,”0”)
	 *
	 * @param wElement
	 * @param iDaysToScroll
	 * @param sTimeHrs
	 * @param sTimeMin
	 * @param sTimeAMPM
	 * @throws InterruptedException
	 */
	public void setDateTime24hrs( WebElement wElement, int iDaysToScroll, String sTimeHrs,String sTimeMin) throws InterruptedException
	{
		wElement.click();
		switchContext("Native");
		setDatePicker(0,iDaysToScroll);
		if(sTimeHrs == "0" && sTimeMin == "0" ) {
			getEleDonePickerWheelBtn().click();

		}else {
			timeSetter(sTimeHrs,sTimeMin,"",true);
			getEleDonePickerWheelBtn().click();
		}

		switchContext("Webview");
		Thread.sleep(GenericLib.iLowSleep);


	}

	/**
	 * Set the specific dateFormatToSelect (e.g "Mon Oct 15") , followed by day and year form the date picker wheels, passing string for setting 0 for sYear,sTimeMin will set the present date
	 *
	 *for specific values:
	 *setSpecificDateYear( workOrderPo.getEleIBScheduledTxtFld(), “Mon Oct 15”, “09”,”2022”) 
	 *
	 *for only moving day and leaving default day and year :
	 *setSpecificDateYear( workOrderPo.getEleIBScheduledTxtFld(), “Mon Oct 15”, “0”,”0”) 
	 * @param wElement
	 * @param dateFormatToSelect
	 * @param sTimeHrs
	 * @param sTimeMin
	 * @param sTimeAMPM
	 * @throws InterruptedException
	 */
	public void setSpecificDateYear( WebElement wElement, String dateFormatToSelect, String sDay,String sYear) throws InterruptedException
	{
		wElement.click();
		switchContext("Native");
		getEleDatePickerPopUp().get(0).sendKeys(dateFormatToSelect);
		if(sDay == "0" && sYear == "0" ) {
			getEleDonePickerWheelBtn().click();

		}else {
			timeSetter(sDay,sYear,"",true);
			getEleDonePickerWheelBtn().click();
		}

		switchContext("Webview");
		Thread.sleep(GenericLib.iLowSleep);


	}

	/**
	 * Set the time form the date picker wheels	in 12hrs format, passing 0 for sTimeHrs,sTimeMin,sTimeAMPM will set the present date
	 *
	 * @param wElement
	 * @param iDaysToScroll
	 * @param sTimeHrs
	 * @param sTimeMin
	 * @param sTimeAMPM
	 * @throws InterruptedException
	 */
	public void setDateTime12Hrs( WebElement wElement, int iDaysToScroll, String sTimeHrs,String sTimeMin,String sTimeAMPM) throws InterruptedException
	{
		wElement.click();
		switchContext("Native");
		setDatePicker(0,iDaysToScroll);
		if(sTimeHrs == "0" && sTimeMin == "0" && sTimeAMPM == "0") {
			getEleDonePickerWheelBtn().click();

		}else {
			timeSetter(sTimeHrs,sTimeMin,sTimeAMPM,false);
			getEleDonePickerWheelBtn().click();
		}

		switchContext("Webview");
		Thread.sleep(GenericLib.iLowSleep);


	}

	@FindBy(xpath="//XCUIElementTypePickerWheel[@type='XCUIElementTypePickerWheel']")	
	private List<WebElement> eleDatePickerPopup;
	public  List<WebElement> getEleDatePickerPopUp()
	{
		return eleDatePickerPopup;
	}

	/**
	 * Set the specific date picker wheel (Day/month/year) by scrolling up or down based on +ve or -ve value to be used with setDateTime24hrs / setDateTime12hrs
	 * 
	 * @param iWheelIndex
	 * @param scrollNum
	 */
	public void setDatePicker(int iWheelIndex, int scrollNum)
	{ 	switchContext("Native");
	int i=0;
	int newTempVal = scrollNum;
	scrollNum = Math.abs(scrollNum);
	for(i=0;i<scrollNum;i++)
	{JavascriptExecutor js = (JavascriptExecutor) driver;
	Map<String, Object> params = new HashMap<>();
	if(newTempVal<0) {
		System.out.println("Scrolling Down "+scrollNum);
		params.put("order", "previous");

	}else {
		System.out.println("Scrolling Up "+scrollNum);
		params.put("order", "next");

	}
	params.put("offset", 0.15);
	params.put("element", getEleDatePickerPopUp().get(iWheelIndex));
	js.executeScript("mobile: selectPickerWheelValue", params);	
	}
	}

	/**
	 * Set the time, for the hrs, min, AMPM values, for 24hrs set the is24hrs to true, if 0 value is passed then it will be skipped to be used with setDateTime24hrs / setDateTime12hrs
	 * 
	 * @param sTimeHrs
	 * @param sTimeMin
	 * @param sTimeAMPM
	 * @param is24hrs
	 */
	public void timeSetter(String sTimeHrs,String sTimeMin,String sTimeAMPM,Boolean is24hrs )
	{
		if(sTimeHrs !="0") {
			getEleDatePickerPopUp().get(1).sendKeys(sTimeHrs);
		}
		if(sTimeMin !="0") {
			getEleDatePickerPopUp().get(2).sendKeys(sTimeMin);
		}
		if(is24hrs == false) {
			if(sTimeAMPM !="0") {
				getEleDatePickerPopUp().get(3).sendKeys(sTimeAMPM);
			}
		}

	}				

	/**
	 * To get all the values from the picklist and verify with the given set of values in the UI
	 * @return 
	 * @throws InterruptedException 
	 * @param  sActualValues = Values sent to verify the picklist fields on the UI.
	 */
	public String[] getAllPicklistValues(CommonsPO commonsPo, WorkOrderPO workOrderPO, String[] sActualValues) throws InterruptedException
	{
		String[] sVals = new String[sActualValues.length];
		String sPrevVal = "";
		String sCurrVal = "";
		commonsPo.switchContext("Native");
		for(int i=0;i<=sActualValues.length;i++) {
			IOSElement PFS = (IOSElement) driver.findElement(By.xpath("//XCUIElementTypePickerWheel[@type='XCUIElementTypePickerWheel']"));
			sCurrVal = PFS.getText();
			Thread.sleep(10000);
			try {
				System.out.println("-----------"+PFS);

				System.out.println("sCurrVal -----------"+sCurrVal);

			}
			catch(Exception e)
			{
				System.out.println("The picklist Values couldn't be fetched"+e);
			}

			sPrevVal = sCurrVal;
			sVals[i] = sPrevVal;

			try{commonsPo.scrollPickerWheel(i, 1);}catch(Exception e) {break;}

		}
		for (String string : sVals) {
			System.out.println("Array read = "+string);

		}
		commonsPo.switchContext("WebView");
		return sVals;	

	}



	/**
	 * Wait until the string is displayed or time elapsed in seconds
	 * @param wElement
	 * @param sExpectedValue
	 * @param lTime
	 * @return
	 * @throws InterruptedException 
	 */
	public boolean waitForString(WebElement wElement, String sExpectedValue,int lTime) throws InterruptedException
	{ 	

		String sSuccessString = null;
		int lElapsedTime=0;

		while(lElapsedTime!=lTime)
		{
			waitforElement(wElement, 1);
			//Ignore Errors from string not found and wait
			try{sSuccessString = wElement.getText();}catch(Exception e) {}
			if(sSuccessString.equals(sExpectedValue))
			{ 
				return true;
			}
			lElapsedTime++;

		}
		//If string not found after waiting return false
		return false;

	}

	/**
	 * Read a text file
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
	 * Verify if the sahi execution was a success based on the sahResultCommon.txt file having true or false
	 * @return
	 */
	public Boolean verifySahiExecution() {
		String sahiResultCommon=null;
		Boolean result=false;
		try {
			sahiResultCommon = this.readTextFile("/auto/SVMX_Catalyst/Executable/sahiResultCommon.txt");

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[] arrValues = sahiResultCommon.split(",");
		int i = 0;
		for (String arrValRead : arrValues) {
			System.out.println("use  arrValues[" + i + "] = " + arrValRead);
			i++;
		}

		if (arrValues[0].toLowerCase().equals("true")) {

			System.out.println("Its a Match , Read File = " + sahiResultCommon);
			// In case you want to stop even if the script passes
			result = true;

		} else {
			System.out.println("Its Not a Match , Read File = " + sahiResultCommon);
			result = false;
		}

		return result;
	}

	/**
	 * Function to click on Allow Pop Up , use try catch in the calling script if needed to avoid false positives
	 * @throws InterruptedException 
	 */
	public void clickAllowPopUp() throws InterruptedException  {

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
			}

		}catch(Exception e) {System.out.println("  ***** Suppresed exception as popups not displayed");}
		finally {
			Thread.sleep(GenericLib.iLowSleep);
			switchContext("Webview");
			Thread.sleep(GenericLib.iLowSleep);}
	}

	public void lookupSearchOnly(String value)throws InterruptedException
	{
		tap(getElesearchTap());
		getElesearchTap().clear();
		getElesearchTap().sendKeys(value);
		tap(getElesearchButton());
	}

	/**
	 * General Function to simply scroll any picker wheel based on -ve or +ve index and number times to scroll, see getAllPicklistValues() method for usage example
	 * @param iWheelIndex
	 * @param scrollNum
	 */
	public void scrollPickerWheel(int iWheelIndex, int scrollNum)
	{ 	switchContext("Native");
	int i=0;
	int newTempVal = scrollNum;
	scrollNum = Math.abs(scrollNum);
	for(i=0;i<scrollNum;i++)
	{JavascriptExecutor js = (JavascriptExecutor) driver;
	Map<String, Object> params = new HashMap<>();
	if(newTempVal<0) {
		System.out.println("Scrolling Down "+scrollNum);
		params.put("order", "previous");

	}else {
		System.out.println("Scrolling Up "+scrollNum);
		params.put("order", "next");

	}
	params.put("offset", 0.15);
	params.put("element", getElePickerWheelPopUp());
	js.executeScript("mobile: selectPickerWheelValue", params);	
	}
	}

	/**
	 * Custom function to return the boolean value for isDiplayed
	 * @param wElement
	 * @return
	 */
	public boolean isDisplayedCust(WebElement wElement) {
		boolean isDis = false;
		switchContext("Webview");
		try{				System.out.println("Element Is displayed ===== "+wElement.isDisplayed());
		if(wElement.isDisplayed()) {
			System.out.println("Element Is displayed returning true");
			return true;
		}else{
			System.out.println("Element Not displayed returning false");
			return false;

		}
		}catch(Exception e) {
			System.out.println("Element Not displayed returning false");
			return false;
		}




	}



}



