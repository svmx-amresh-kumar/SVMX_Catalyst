package com.ge.fsa.iphone.pageobjects;


import java.util.Iterator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.*;
import com.ge.fsa.iphone.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;



public class Ip_LoginHomePO
{
	public Ip_LoginHomePO(AppiumDriver driver)
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

	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"SIGN IN WITH SALESFORCE\"])[2]")
	private WebElement eleSignInBtn;
	public WebElement getEleSignInBtn()
	{
		return eleSignInBtn;
	}

	@FindBy(xpath="//XCUIElementTypeOther[@name=\"Login | Salesforce\"]/XCUIElementTypeTextField")
	private WebElement eleUserNameTxtFld;
	public WebElement getEleUserNameTxtFld()
	{
		return eleUserNameTxtFld;
	}

	@FindBy(xpath="//XCUIElementTypeOther[@name=\"Login | Salesforce\"]/XCUIElementTypeSecureTextField")
	private WebElement elePasswordTxtFld;
	public WebElement getElePasswordTxtFld()
	{
		return elePasswordTxtFld;
	}

	@FindBy(id="Log In")
	private WebElement eleLoginBtn;
	public WebElement getEleLoginBtn()
	{
		return eleLoginBtn;
	}

	@FindBy(id=" Allow ")
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


	/**
	 * Login to FSA app based on values from config.properties. (** For any other property file machine set the RUN_MACHINE=automation_build , which will pick up the data from config_automation_build.properties file)
	 * Default is "TECH_USN"
	 * @param commonsPo
	 * @param exploreSearchPo
	 * @param sUserTypeFromPropertiesFile
	 * @throws InterruptedException
	 */
	public void login(CommonsPO commonsPo, Ip_MorePO ip_MorePo, String... sUserTypeFromPropertiesFile) throws InterruptedException {

		String sUn = null;
		String sPwd = null;
		
		String sUser = sUserTypeFromPropertiesFile.length>1?sUserTypeFromPropertiesFile[0]:"";
		
		if(sUser.equalsIgnoreCase("TECH_USN_1")) {
			 sUn = GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_USN_1");
			 sPwd = GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_PWD_1");
		}else if(sUser.equalsIgnoreCase("TECH_USN")){
		 sUn = GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_USN");
		 sPwd = GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_PWD");
		}else {
			//default
			 sUn = GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_USN");
			 sPwd = GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_PWD");
		}
		
		switch (BaseLib.sOSName) {
		case "android":

			//For Android
			System.out.println("Login For Android UN = "+sUn+" PWD = "+sPwd);

			if(commonsPo.waitforElement(ip_MorePo.getEleMoreBtn(), 1)){
				System.out.println("Logged in Already");

			}else {
				try {

					//Login from Sign in Page
					//commonsPO.switchContext("Webview");
					try {
						getEleSignInBtn().click();
					}catch(Exception e) {
						System.out.println("ByPassing 'Sign In' Button");
					}

					//Change to SandBox from native mode
					commonsPo.switchContext("Native");
					commonsPo.waitforElement(getEleMenuIcn(), 2);
					getEleMenuIcn().click();
					commonsPo.waitforElement(getEleSandBxRdBtn(), 2);
					getEleSandBxRdBtn().click();

					touchAction = new TouchAction(driver);
					touchAction.tap(new PointOption().withCoordinates(150, 150)).perform();

					//Enter Credentials in Webview Mode
					commonsPo.switchContext("Webview");
					commonsPo.waitforElement(getEleUserNameTxtFld(), 2);
					getEleUserNameTxtFld().sendKeys(sUn);
					getElePasswordTxtFld().sendKeys(sPwd);
					getEleLoginBtn().click();


				} catch (Exception e) {}

				//Either click Allow or Skip it without an exception
				try {
					getEleAllowBtn().click();
				} catch (Exception e1) {}

				commonsPo.switchContext("Webview");
				//Check if username field is not displayed
				Assert.assertTrue(!commonsPo.waitforElement(getEleUserNameTxtFld(), 1),"Login Failed");
				//Wait for the Explore button to be visible
				Assert.assertTrue(commonsPo.waitforElement(ip_MorePo.getEleMoreBtn(), 1000),"Login Failed");

			}
			ExtentManager.logger.log(Status.PASS, "Logged into Android FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
			System.out.println("Already installed and logged in");
			break;

		case "ios":

			System.out.println("Login For Iphone IOS UN = "+sUn+" PWD = "+sPwd);

			if(commonsPo.waitforElement(ip_MorePo.getEleMoreBtn(), 1)){
				System.out.println("Logged in Already");

			}else {
				try {//For IOS
					//Login from Sign in Page
					
					
					Thread.sleep(4000);
					getEleSignInBtn().click();
					
					getEleUserNameTxtFld().click();
					getEleUserNameTxtFld().sendKeys("rkong@t.com");
					Thread.sleep(4000);

					getElePasswordTxtFld().click();
					getElePasswordTxtFld().sendKeys("servicemax1");
					
					getEleLoginBtn().click();
					//Either click Allow or Skip it without an exception
					try {
						commonsPo.waitforElement(getEleAllowBtn(), 5);

						getEleAllowBtn().click();
						} catch (Exception e1) {}

				
					Thread.sleep(2000);

									
					//Check if username field is not displayed
					Assert.assertTrue(!commonsPo.waitforElement(getEleUserNameTxtFld(), 1),"Login Failed");
					//Wait for the Explore button to be visible
					Assert.assertTrue(commonsPo.waitforElement(ip_MorePo.getEleMoreBtn(), 1000),"Login Failed");
					
				} catch (Exception e) {}

			ExtentManager.logger.log(Status.PASS, "Logged into IOS iPhone FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
			System.out.println("Logged in Successfully");
		
			}
			break;
		}

	}

	
}
