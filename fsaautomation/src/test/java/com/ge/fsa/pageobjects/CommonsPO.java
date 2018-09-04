package com.ge.fsa.pageobjects;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class CommonsPO
{
	public CommonsPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	AppiumDriver driver = null;
	TouchAction touchAction = null;	
	Iterator<String> iterator =null;
	public static String sNativeApp = null;
	public static String sWebView = null;		
	int xOffset = 15;
	int yOffset = 18;
	int iWhileCnt =0;
	long lElapsedTime=0L;
	@FindBy(className="XCUIElementTypePickerWheel")	
	private WebElement elePickerWheelPopUp;
	public  WebElement getElePickerWheelPopUp()
	{
		return elePickerWheelPopUp;
	}
	
	@FindBy(name="Done")
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
	public void tap(WebElement  wElement, int... optionalOffsetPointsxy) throws InterruptedException {

		Integer xNewOffset = optionalOffsetPointsxy.length > 0 ? optionalOffsetPointsxy[0] : null;
		Integer yNewOffset = optionalOffsetPointsxy.length > 1 ? optionalOffsetPointsxy[1] : null;

		Point point =  wElement.getLocation();
		System.out.println("Tapping element " +  wElement.getText() + " " +  wElement.getTagName());

		for (int i = 0; i < 10; i++) {
			if (point.getX() == 0 || point.getY() == 0) {
				System.out.println("waiting... for element \n" + 
						"¯\\_(ツ)_/¯" + point.getX() + "---" + point.getY());
				Thread.sleep(2000);
				point =  wElement.getLocation();
				System.out.println("New fetch \n" + 
						"ヽ(´▽`)/" + point.getX() + "---" + point.getY());
			} else {
				break;
			}

		}

		touchAction = new TouchAction(driver);
		if (xNewOffset != null) {
			System.out.println("Tapping on Custom Offset Points xNewOffset = "+xNewOffset+" yNewOffset = "+yNewOffset+ " on "+point.getX() + "---" + point.getY());
			touchAction.tap(new PointOption().withCoordinates(point.getX() + xNewOffset, point.getY() + yNewOffset)).perform();

		} else {
			touchAction.tap(new PointOption().withCoordinates(point.getX() + xOffset, point.getY() + yOffset)).perform();

		}
		Thread.sleep(GenericLib.iLowSleep);
	}
	//Customised touch Tap
			public void singleTap(Point point) throws InterruptedException
			{
				touchAction = new TouchAction(driver);
				touchAction.tap(new PointOption().withCoordinates(point.getX()+xOffset, point.getY()+yOffset)).perform();
				Thread.sleep(GenericLib.iLowSleep);
			}
			
		
		//Customised touch Tap
		public void fingerTap(Point point, int iTapCount) throws InterruptedException
		{
			touchAction = new TouchAction(driver);
			touchAction.moveTo(new PointOption().withCoordinates(point.getX()+xOffset, point.getY()+yOffset)).tap(new TapOptions().withTapsCount(iTapCount)).perform();
			Thread.sleep(GenericLib.iLowSleep);
		}
		
		//Customised touch LongPress
		public void longPress(WebElement  wElement) throws InterruptedException
		{Point point =  wElement.getLocation();
		System.out.println("x "+point.getX()+" y "+point.getY());
			touchAction = new TouchAction(driver);
			touchAction.longPress(new PointOption().withCoordinates(point.getX()+xOffset, point.getY()+yOffset)).perform();
			Thread.sleep(GenericLib.iLowSleep);
		}
		
		//Customised touch Doubletap
		public void doubleTap(WebElement  wElement) throws InterruptedException
		{
			touchAction = new TouchAction(driver);
			touchAction.tap(new TapOptions().withTapsCount(2).withElement((ElementOption) wElement)).perform();
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
		

		//To search the element scrolling
		public void getSearch(WebElement wElement)
		{
			while(iWhileCnt<=7) 
			{	
				try {
					Assert.assertTrue(wElement.isDisplayed(),"Failed to scroll to search");
					ExtentManager.logger.log(Status.PASS,"Search is successfull");
					//System.out.println("Search is displayed");
					break;
				}catch(Exception e) {swipeUp();}			
				iWhileCnt++;
			}
		}
		
		//To switch context between Native and Webview
		public void switchContext(String sContext)
		{
			iterator = driver.getContextHandles().iterator();
			while(iterator.hasNext()){
				sNativeApp = iterator.next();
				sWebView = iterator.next();
			}
			if(sContext.equalsIgnoreCase("Native"))
			{driver.context(sNativeApp);}
			else {driver.context(sWebView);}
		}
		
		//To set the value in PickerWheel native app
		public void pickerWheel( WebElement wElement, String sValue) throws InterruptedException
		{
			wElement.click();
			Thread.sleep(2000);
			switchContext("Native");
			getElePickerWheelPopUp().sendKeys(sValue);		
			tap(getEleDonePickerWheelBtn());
			switchContext("WebView");
		}
		
		
		//Wait for element until the element is displayed or time elapsed
		public void waitforElement(WebElement wElement, long lTime)
		{ lElapsedTime=0L;
			while(true)
			{
				try{
					if(wElement.isDisplayed()|| (lElapsedTime==lTime))
					{ break;}
				}catch(Exception ex) {}
				lElapsedTime++;
			}
			
			
		}
		
		// This method will search the required value and then click on it 
		public void lookupSearch(String value)throws InterruptedException
		{
			
			tap(getElesearchTap());
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
			datePicker(0,iDaysToScroll);
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
		 * Set the 24 hrs time form the date picker wheels, passing string for dateFormatToSelect , setting 0 for sYear,sTimeMin will set the present date
		 *
		 * @param wElement
		 * @param dateFormatToSelect
		 * @param sTimeHrs
		 * @param sTimeMin
		 * @param sTimeAMPM
		 * @throws InterruptedException
		 */
			public void setDateYear( WebElement wElement, String dateFormatToSelect, String sDay,String sYear) throws InterruptedException
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
		 * Set the time form the date picker wheels	, passing 0 for sTimeHrs,sTimeMin,sTimeAMPM will set the present date
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
				datePicker(0,iDaysToScroll);
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
		 * Set the specific date picker wheel by scrolling up or down based on +ve or -ve value to be used with setDateTime24hrs / setDateTime12hrs
		 * 
		 * @param iDateWheelIndex
		 * @param scrollNum
		 */
		public void datePicker(int iDateWheelIndex, int scrollNum)
		{ 	int i=0;
			for(i=0;i<scrollNum;i++)
			{JavascriptExecutor js = (JavascriptExecutor) driver;
		    Map<String, Object> params = new HashMap<>();
		    params.put("order", "next");
		    params.put("offset", 0.15);
		    params.put("element", getEleDatePickerPopUp().get(iDateWheelIndex));
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
		 * Wait for element until the element is displayed or time elapsed
		 * @param wElement
		 * @param sExpectedValue
		 * @param lTime
		 * @return
		 */
		public boolean waitForString(WebElement wElement, String sExpectedValue,long lTime)
		{ 	
		
			String op = null;
			String sd=null;
			lElapsedTime=0L;
			while(true)
			{
				waitforElement(wElement, GenericLib.lWaitTime);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				op = wElement.getText();
				sd=sExpectedValue;
				try{
					if(!op.equals(sd) && (lElapsedTime==lTime))
					{ 
						return false;
						}
				}catch(Exception ex) {
					return false;
				}
				lElapsedTime++;
				
				if(op.equals(sd)) {
					return true;
				}
			
			}
			
		
			
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

			System.out.println("resultCommon.txt file read as = " + data);
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
			System.out.println("resultCommon.txt file Write as = " + data);

		}
		 
		/**
		 * Verify if the sahi execution was a success based on the sahResultCommon.txt file having true or false
		 * @return
		 */
		public Boolean verifySahiExecution() {
			String resultCommon=null;
			Boolean result=false;
			try {
				 resultCommon = this.readTextFile("/auto/SVMX_Catalyst/Executable/sahiResultCommon.txt");

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String[] arrValues = resultCommon.split(",");
			int i = 0;
			for (String arrValRead : arrValues) {
				System.out.println("use  arrValues[" + i + "] = " + arrValRead);
				i++;
			}

			if (arrValues[0].toLowerCase().equals("true")) {

				System.out.println("Its a Match , Read File = " + resultCommon);
				// In case you want to stop even if the script passes
				result = true;

			} else {
				System.out.println("Its Not a Match , Read File = " + resultCommon);
				result = false;
			}
			
			return result;
		}
}
	

		
