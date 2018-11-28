package com.ge.fsa.pageobjects;


import java.time.Duration;
import java.util.Iterator;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;



public class LoginHomePO
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
	
	
	@FindBy(xpath="(//span[text()='Login'])[1]")
	private WebElement eleMotoGpLogin;
	public WebElement geteleMotoGpLogin()
	{
		return eleMotoGpLogin;
	}
	
	@FindBy(id="com.servicemaxinc.svmxfieldserviceapp:id/menu_host")
	private WebElement eleMenuIcn;
	public WebElement getEleMenuIcn() 
	{
		return eleMenuIcn;
	}
	
	@FindBy(id="com.servicemaxinc.svmxfieldserviceapp:id/radio_sandbox")
	private WebElement eleSandBxRdBtn;
	public WebElement getEleSandBxRdBtn() 
	{
		return eleSandBxRdBtn;
	}
	
	
	
	public void login(CommonsPO commonsPO, ExploreSearchPO exploreSearchPo) throws InterruptedException {

		switch (GenericLib.getConfigValue(GenericLib.sConfigFile, "PLATFORM_NAME").toLowerCase()) {
		case "android":
			

			try {
				//commonsPO.switchContext("Webview");

				Assert.assertTrue(getEleSignInBtn().isDisplayed());
				getEleSignInBtn().click();
				commonsPO.switchContext("Native");
				getEleMenuIcn().click();
				Thread.sleep(3000);

				getEleSandBxRdBtn().click();
				
					touchAction = new TouchAction(driver);
					touchAction.tap(new PointOption().withCoordinates(150, 150)).perform();

			

				Thread.sleep(10000);
				commonsPO.switchContext("Webview");

				getEleUserNameTxtFld().sendKeys(GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_USN"));
				getElePasswordTxtFld().sendKeys(GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_PWD"));
				getEleLoginBtn().click();
				Thread.sleep(GenericLib.iHighSleep);
				try {
					getEleAllowBtn().click();
				} catch (Exception e) {
					System.out.println("Allow not present " + e);
				}
		
				commonsPO.waitforElement(exploreSearchPo.getEleExploreIcn(), 20 * 60 * 1000);
			} catch (Exception e) {
				System.out.println("Already logged in exception ");
				Thread.sleep(10000);

				commonsPO.switchContext("Webview");

				wait = new WebDriverWait(driver, 4000);
				commonsPO.waitforElement(exploreSearchPo.getEleExploreIcn(), 2000);
				//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Explore']")));
				Assert.assertTrue(exploreSearchPo.getEleExploreIcn().isDisplayed());
				ExtentManager.logger.log(Status.PASS, "Logged into FSA app successfully");
				System.out.println("Already installed and logged in");
			}
			break;

		default:
			try {
				Assert.assertTrue(getEleSignInBtn().isDisplayed());
				ExtentManager.logger.log(Status.PASS, "FSA app is successfully installed");
				// SignIn to App
				getEleSignInBtn().click();
				Thread.sleep(10000);
				getEleUserNameTxtFld().sendKeys(GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_USN"));
				getElePasswordTxtFld().sendKeys(GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_PWD"));
				getEleLoginBtn().click();
				Thread.sleep(GenericLib.iLowSleep);
				try {
					getEleAllowBtn().click();
				} catch (Exception e) {
				}
				commonsPO.waitforElement(exploreSearchPo.getEleExploreIcn(), 20 * 60 * 1000);
				/*
				 * while(true) { try{ if(getEleExploreIcn().isDisplayed()||
				 * (lElapsedTime==20*60*1000)) {
				 * System.out.println("Logged is successful");break;} }catch(Exception ex) {} }
				 */

			} catch (Exception e) {
				wait = new WebDriverWait(driver, 4000);
				commonsPO.waitforElement(exploreSearchPo.getEleExploreIcn(), 2000);

				//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Explore']")));
				Assert.assertTrue(exploreSearchPo.getEleExploreIcn().isDisplayed());
				ExtentManager.logger.log(Status.PASS, "Logged into FSA app successfully");
				System.out.println("Already installed and logged in");
			}
			break;
		
		}

	}
	
	
	public void login_tech2(CommonsPO commonsPO, ExploreSearchPO exploreSearchPo) {
		try 
		{
			
			
			//SignIn to App
			getEleSignInBtn().click();
			Thread.sleep(10000);
			getEleUserNameTxtFld().sendKeys(GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_USN_1"));
			getElePasswordTxtFld().sendKeys(GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_PWD_1"));
			getEleLoginBtn().click();
			Thread.sleep(GenericLib.iLowSleep);
			try{getEleAllowBtn().click();}catch(Exception e) {}
			commonsPO.waitforElement(exploreSearchPo.getEleExploreIcn(), 20*60*1000);

			
		}catch(Exception e)
		{		
		wait = new WebDriverWait(driver, 40000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Explore']")));
		Assert.assertTrue(exploreSearchPo.getEleExploreIcn().isDisplayed());
		ExtentManager.logger.log(Status.PASS,"Logged into FSA app successfully");			
	}

	}
	
	
}
