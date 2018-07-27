package com.ge.fsa.pageobjects;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.RestServices;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofSeconds;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import com.ge.fsa.lib.BaseLib;
import static io.appium.java_client.touch.WaitOptions.waitOptions;


public class CommonsPO extends BaseLib 
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
	public void tap(WebElement el, int... optionalOffsetPointsxy) throws InterruptedException {

		Integer xNewOffset = optionalOffsetPointsxy.length > 0 ? optionalOffsetPointsxy[0] : null;
		Integer yNewOffset = optionalOffsetPointsxy.length > 1 ? optionalOffsetPointsxy[1] : null;

		Point point = el.getLocation();
		System.out.println("Tapping element " + el.getText() + " " + el.getTagName());

		for (int i = 0; i < 10; i++) {
			if (point.getX() == 0 || point.getY() == 0) {
				System.out.println("waiting... for element \n" + 
						"¯\\_(ツ)_/¯" + point.getX() + "---" + point.getY());
				Thread.sleep(2000);
				point = el.getLocation();
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
		public void fingerTap(Point point, int iTapCount) throws InterruptedException
		{
			touchAction = new TouchAction(driver);
			touchAction.moveTo(new PointOption().withCoordinates(point.getX()+xOffset, point.getY()+yOffset)).tap(new TapOptions().withTapsCount(iTapCount)).perform();
			Thread.sleep(GenericLib.iLowSleep);
		}
		
		//Customised touch LongPress
		public void longPress(WebElement el) throws InterruptedException
		{Point point = el.getLocation();
			touchAction = new TouchAction(driver);
			touchAction.longPress(new PointOption().withCoordinates(point.getX()+xOffset, point.getY()+yOffset)).perform();
			Thread.sleep(GenericLib.iLowSleep);
		}
		
		//Customised touch Doubletap
		public void doubleTap(WebElement element) throws InterruptedException
		{
			touchAction = new TouchAction(driver);
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
		
		public void swipeLeft(WebElement ele)
		{	int offset = 30;
			Point point = ele.getLocation();
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
					NXGReports.addStep("Search is successfull", LogAs.PASSED, null);
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
		public void pickerWheel( WebElement element, String sValue) throws InterruptedException
		{
			element.click();
			Thread.sleep(2000);
			switchContext("Native");
			getElePickerWheelPopUp().sendKeys(sValue);		
			tap(getEleDonePickerWheelBtn());
			switchContext("WebView");
		}
		
		
		//Wait for element until the element is displayed or time elapsed
		public void waitforElement(WebElement element, long lTime)
		{ lElapsedTime=0L;
			while(true)
			{
				try{
					if(element.isDisplayed()|| (lElapsedTime==lTime))
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
	 * Set the time form the date picker wheels	, passing 0 for sTimeHrs,sTimeMin,sTimeAMPM will set the present date
	 *
	 * @param element
	 * @param iDaysToScroll
	 * @param sTimeHrs
	 * @param sTimeMin
	 * @param sTimeAMPM
	 * @throws InterruptedException
	 */
		public void setTime( WebElement element, int iDaysToScroll, String sTimeHrs,String sTimeMin,String sTimeAMPM) throws InterruptedException
		{
			element.click();
			switchContext("Native");
			datePicker(0,iDaysToScroll);
			if(sTimeHrs == "0" && sTimeMin == "0" && sTimeAMPM == "0") {
				getEleDonePickerWheelBtn().click();

			}else {
				timeSetter(1, sTimeHrs,sTimeMin,sTimeAMPM);
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
		 * Set the specific date picker wheel by scrolling up or down based on +ve or -ve value
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
		 * Set the time, if the hrs, min, AMPM values in 0 then it will be skipped
		 * 
		 * @param iIndex
		 * @param sTimeHrs
		 * @param sTimeMin
		 * @param sTimeAMPM
		 */
		public void timeSetter(int iIndex, String sTimeHrs,String sTimeMin,String sTimeAMPM )
		{
			if(sTimeHrs !="0") {
				getEleDatePickerPopUp().get(1).sendKeys(sTimeHrs);
			}
			if(sTimeMin !="0") {
				getEleDatePickerPopUp().get(2).sendKeys(sTimeMin);
			}
			if(sTimeAMPM !="0") {
				getEleDatePickerPopUp().get(3).sendKeys(sTimeAMPM);
			}
			
			
		}
		
		//Wait for element until the element is displayed or time elapsed
		public boolean waitForString(WebElement element, String sExpectedValue,long lTime)
		{ 	
		
			String op = null;
			String sd=null;
			lElapsedTime=0L;
			while(true)
			{
				waitforElement(element, GenericLib.lWaitTime);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				op = element.getText();
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
		
//		 /**
//		  * Based on the returned Parts Details JSONArray, in the calling method , use String  returnvalue= (String) value.get(verifyvalue1);
//
//		  * 
//		  * @param restservices
//		  * @param sworkordername
//		  * @param slinetype
//		  * @param requieredApiNAme
//		  * @param expectedValue
//		  * @return
//		  * @throws IOException
//		  */
//		 public JSONArray verifyPartsDetails(RestServices restservices, String sworkordername,String slineType)  throws IOException
//		 {
//			
//			 String soqlquery = "Select+SVMXC__Actual_Quantity2__c,+SVMXC__Actual_Price2__c,+SVMXC__Product__c,+SVMXC__Activity_Type__c,+SVMXC__Start_Date_and_Time__c,+SVMXC__End_Date_and_Time__c,+SVMXC__Expense_Type__c,+SVMXC__Work_Description__c+from+SVMXC__Service_Order_Line__c+where+SVMXC__Line_Type__c=\'"+slineType+"\'+AND+SVMXC__Service_Order__c+In(Select+Id+from+SVMXC__Service_Order__c+where+Name+=\'"+sworkordername+"\')";
//			 System.out.println(soqlquery);
//			 restservices.getAccessToken();
//			 JSONArray returnedvalues = restservices.restGetSoqlJsonArray(soqlquery);
//			 System.out.println(returnedvalues);
//
//			 return returnedvalues;
//		}
		 
}
	

		
