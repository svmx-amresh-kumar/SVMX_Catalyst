package com.ge.fsa.pageobjects;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.ge.fsa.lib.GenericLib;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

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
	
	@FindBy(xpath="//input[@placeholder='Search'][@class='x-input-el']")
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
		eleSearchListItem=driver.findElement(By.xpath("//div[@class='x-inner-el'][text()='"+searchName+"']"));
		return eleSearchListItem;
	}
	
	

	

	//Customised touch Tap
		public void tap(WebElement el) throws InterruptedException
		{   Point point = el.getLocation();
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
			
			this.tap(this.getElesearchTap());
			this.getElesearchTap().sendKeys(value);
			this.tap(this.getElesearchButton());
			this.tap(this.getElesearchListItem(value));

			
		}
		
	
}
