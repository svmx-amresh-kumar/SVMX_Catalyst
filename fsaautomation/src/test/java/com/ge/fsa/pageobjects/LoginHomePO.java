/*
 *  @author lakshmibs
 */
package com.ge.fsa.pageobjects;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;



public class LoginHomePO extends BaseLib
{
	public LoginHomePO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	public static String sNativeApp = null;
	public static String sWebView = null;		
	int xOffset = 15;
	int yOffset = 18;
	int iWhileCnt =0;
	long lElapsedTime=0L;
	
	@FindBy(id="svmx_splash_signin")
	private WebElement eleSignInBtn;
	public WebElement getEleSignInBtn()
	{
		return eleSignInBtn;
	}
	
	@FindBy(id="username")
	private WebElement eleUserNameTxtFld;
	public WebElement getEleUserNameTxtFld()
	{
		return eleUserNameTxtFld;
	}
	
	@FindBy(id="password")
	private WebElement elePasswordTxtFld;
	public WebElement getElePasswordTxtFld()
	{
		return elePasswordTxtFld;
	}
	
	@FindBy(id="Login")
	private WebElement eleLoginBtn;
	public WebElement getEleLoginBtn()
	{
		return eleLoginBtn;
	}
	
	@FindBy(id="oaapprove")
	private WebElement eleAllowBtn;
	public WebElement getEleAllowBtn()
	{
		return eleAllowBtn;
	}
	
	
	
	

	
	public void login(CommonsPO commonsPO, ExploreSearchPO exploreSearchPo) {
		try 
		{
			Assert.assertTrue(getEleSignInBtn().isDisplayed());
			NXGReports.addStep("FSA app is successfully installed", LogAs.PASSED, null);		
			//SignIn to App
			getEleSignInBtn().click();
			Thread.sleep(10000);
			getEleUserNameTxtFld().sendKeys(GenericLib.getCongigValue(GenericLib.sConfigFile, "TECH_USN"));
			getElePasswordTxtFld().sendKeys(GenericLib.getCongigValue(GenericLib.sConfigFile, "TECH_PWD"));
			getEleLoginBtn().click();
			try{getEleAllowBtn().click();}catch(Exception e) {}
			commonsPO.waitforElement(exploreSearchPo.getEleExploreIcn(), 20*60*1000);
			/*while(true)
			{
				try{
					if(getEleExploreIcn().isDisplayed()|| (lElapsedTime==20*60*1000))
					{ System.out.println("Logged is successful");break;}
				}catch(Exception ex) {}
			}*/
			
		}catch(Exception e)
		{		
		wait = new WebDriverWait(driver, 40000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Explore']")));
		Assert.assertTrue(exploreSearchPo.getEleExploreIcn().isDisplayed());
		NXGReports.addStep("Logged into FSA app successfully", LogAs.PASSED, null);		
		System.out.println("Already installed and logged in");		
	}

	}
	/*
	//Customised touch Tap
	public void tap(Point point) throws InterruptedException
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
	public void longPress(Point point) throws InterruptedException
	{
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
	public void pickerWheel(WebElement element, String sValue) throws InterruptedException
	{
		element.click();
		switchContext("Native");
		getElePickerWheelPopUp().sendKeys(sValue);		
		tap(getEleDonePickerWheelBtn().getLocation());
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
		}
		
		
	}*/
}
