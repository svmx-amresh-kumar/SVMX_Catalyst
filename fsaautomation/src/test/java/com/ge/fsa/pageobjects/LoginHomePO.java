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


	/**
	 * Login to FSA app based on values from config.properties. (** For BUILD machine set the RUN_MACHINE=build , which will pick up the data from config_build.properties file)
	 * @param commonsPo
	 * @param exploreSearchPo
	 * @throws InterruptedException
	 */
	public void login(CommonsPO commonsPo, ExploreSearchPO exploreSearchPo) throws InterruptedException {

		String sUn = GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_USN");
		String sPwd = GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_PWD");
		switch (BaseLib.sOSName) {
		case "android":


			try {//For Android
				System.out.println("Login For Android");

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
				//Either click Allow or Skip it without an exception
				try {
					commonsPo.waitforElement(getEleAllowBtn(), 1);
					getEleAllowBtn().click();
				} catch (Exception e) {
				}

				//Check if username field is not displayed
				Assert.assertTrue(!commonsPo.waitforElement(getEleUserNameTxtFld(), 1),"Login Failed");
				//Wait for the Explore button to be visible
				Assert.assertTrue(commonsPo.waitforElement(exploreSearchPo.getEleExploreIcn(), 2000),"Login Failed");
				ExtentManager.logger.log(Status.PASS, "Logged into Android FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
				System.out.println("Logged in Successfully");

			} catch (Exception e) {
				//The App may be already logged in so check directly for the Explore button to be visible
				try {
					commonsPo.waitforElement(getEleAllowBtn(), 1);
					getEleAllowBtn().click();
				} catch (Exception e1) {
				}
				commonsPo.switchContext("Webview");
				//Check if username field is not displayed
				Assert.assertTrue(!commonsPo.waitforElement(getEleUserNameTxtFld(), 1),"Login Failed");
				//Wait for the Explore button to be visible
				Assert.assertTrue(commonsPo.waitforElement(exploreSearchPo.getEleExploreIcn(), 2000),"Login Failed");
				ExtentManager.logger.log(Status.PASS, "Logged into Android FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
				System.out.println("Already installed and logged in");
			}
			break;

		case "ios":
			try {//For IOS
				System.out.println("Login For IOS");

				//Login from Sign in Page
				try {
					getEleSignInBtn().click();
				}catch(Exception e) {
					System.out.println("ByPassing 'Sign In' Button");
				}

				//Enter Credentials
				commonsPo.waitforElement(getEleUserNameTxtFld(), 2);
				getEleUserNameTxtFld().sendKeys(sUn);
				getElePasswordTxtFld().sendKeys(sPwd);
				getEleLoginBtn().click();
				try {
					commonsPo.waitforElement(getEleAllowBtn(), 1);
					getEleAllowBtn().click();
				} catch (Exception e) {
				}
				//Check if username field is not displayed
				Assert.assertTrue(!commonsPo.waitforElement(getEleUserNameTxtFld(), 1),"Login Failed");
				//Wait for the Explore button to be visible
				Assert.assertTrue(commonsPo.waitforElement(exploreSearchPo.getEleExploreIcn(), 2000),"Login Failed");
				ExtentManager.logger.log(Status.PASS, "Logged into IOS Ipad FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
				System.out.println("Logged in Successfully");


			} catch (Exception e) {

				//The App may be already logged in so check directly for the Explore button to be visible
				try {
					commonsPo.waitforElement(getEleAllowBtn(), 1);
					getEleAllowBtn().click();
				} catch (Exception e2) {
				}
				//Check if username field is not displayed
				Assert.assertTrue(!commonsPo.waitforElement(getEleUserNameTxtFld(), 1),"Login Failed");
				//Wait for the Explore button to be visible
				Assert.assertTrue(commonsPo.waitforElement(exploreSearchPo.getEleExploreIcn(), 2000),"Login Failed");
				ExtentManager.logger.log(Status.PASS, "Logged into IOS Ipad FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
				System.out.println("Logged in Successfully");

			}
			break;

		default:
			System.out.println("OS Error");
			break;

		}

	}


	/*
	 * public void login_tech(CommonsPO commonsPo, ExploreSearchPO exploreSearchPo)
	 * throws InterruptedException { try {
	 * 
	 * //SignIn to App getEleSignInBtn().click(); Thread.sleep(10000);
	 * getEleUserNameTxtFld().sendKeys(GenericLib.getConfigValue(GenericLib.
	 * sConfigFile, "TECH_USN_1"));
	 * getElePasswordTxtFld().sendKeys(GenericLib.getConfigValue(GenericLib.
	 * sConfigFile, "TECH_PWD_1")); getEleLoginBtn().click();
	 * Thread.sleep(GenericLib.iLowSleep);
	 * try{getEleAllowBtn().click();}catch(Exception e) {}
	 * commonsPo.waitforElement(exploreSearchPo.getEleExploreIcn(), 2000);
	 * 
	 * 
	 * }catch(Exception e) {
	 * commonsPo.waitforElement(exploreSearchPo.getEleExploreIcn(), 2000);
	 * 
	 * // wait = new WebDriverWait(driver, 40000); //
	 * wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
	 * "//div[text()='Explore']")));
	 * Assert.assertTrue(exploreSearchPo.getEleExploreIcn().isDisplayed());
	 * ExtentManager.logger.log(Status.PASS,"Logged into FSA app successfully"); }
	 * 
	 * }
	 */

	public void login_tech2(CommonsPO commonsPo, ExploreSearchPO exploreSearchPo) throws InterruptedException {

		String sUn = GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_USN_1");
		String sPwd = GenericLib.getConfigValue(GenericLib.sConfigFile, "TECH_PWD_1");
		switch (BaseLib.sOSName) {
		case "android":


			try {//For Android
				System.out.println("Login For Android");

				//Login from Sign in Page
				//commonsPO.switchContext("Webview");
				Assert.assertTrue(getEleSignInBtn().isDisplayed());
				getEleSignInBtn().click();

				//Change to SandBox from native mode
				commonsPo.switchContext("Native");
				getEleMenuIcn().click();
				Thread.sleep(3000);

				getEleSandBxRdBtn().click();

				touchAction = new TouchAction(driver);
				touchAction.tap(new PointOption().withCoordinates(150, 150)).perform();

				//Enter Credentials in Webview Mode
				Thread.sleep(10000);
				commonsPo.switchContext("Webview");

				getEleUserNameTxtFld().sendKeys(sUn);
				getElePasswordTxtFld().sendKeys(sPwd);
				getEleLoginBtn().click();
				Thread.sleep(GenericLib.iHighSleep);
				//Either click Allow or Skip it without an exception
				try {
					getEleAllowBtn().click();
				} catch (Exception e) {
					System.out.println("Allow not present " + e);
				}

				//Wait for the Explore button to be visible

				//commonsPO.waitforElement(exploreSearchPo.getEleExploreIcn(), 20 * 60 * 1000);
				commonsPo.waitforElement(exploreSearchPo.getEleExploreIcn(), 2000);

				//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Explore']")));
				Assert.assertTrue(exploreSearchPo.getEleExploreIcn().isDisplayed());
				ExtentManager.logger.log(Status.PASS, "Logged into Android FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
				System.out.println("Logged in Successfully");

			} catch (Exception e) {
				//The App may be already logged in so check directly for the Explore button to be visible
				Thread.sleep(10000);

				commonsPo.switchContext("Webview");

				//wait = new WebDriverWait(driver, 4000);
				//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Explore']")));

				commonsPo.waitforElement(exploreSearchPo.getEleExploreIcn(), 2000);
				Assert.assertTrue(exploreSearchPo.getEleExploreIcn().isDisplayed());
				ExtentManager.logger.log(Status.PASS, "Logged into Android FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
				System.out.println("Already installed and logged in");
			}
			break;

		case "ios":
			try {//For IOS
				System.out.println("Login For IOS");

				//Login from Sign in Page
				Assert.assertTrue(getEleSignInBtn().isDisplayed());
				ExtentManager.logger.log(Status.PASS, "FSA app is successfully installed");
				// SignIn to App
				getEleSignInBtn().click();

				//Enter Credentials

				Thread.sleep(10000);
				getEleUserNameTxtFld().sendKeys(sUn);
				getElePasswordTxtFld().sendKeys(sPwd);
				getEleLoginBtn().click();
				Thread.sleep(GenericLib.iLowSleep);
				try {
					getEleAllowBtn().click();
				} catch (Exception e) {
				}
				//commonsPO.waitforElement(exploreSearchPo.getEleExploreIcn(), 20 * 60 * 1000);

				//while(true) { try{ if(getEleExploreIcn().isDisplayed()||
				//(lElapsedTime==20*60*1000)) {
				//System.out.println("Logged is successful");break;} }catch(Exception ex) {} }
				//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Explore']")));


				//Wait for the Explore button to be visible

				commonsPo.waitforElement(exploreSearchPo.getEleExploreIcn(), 2000);
				Assert.assertTrue(exploreSearchPo.getEleExploreIcn().isDisplayed());
				ExtentManager.logger.log(Status.PASS, "Logged into IOS Ipad FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
				System.out.println("Logged in Successfully");


			} catch (Exception e) {

				//The App may be already logged in so check directly for the Explore button to be visible

				//wait = new WebDriverWait(driver, 4000);
				//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Explore']")));
				
				commonsPo.waitforElement(exploreSearchPo.getEleExploreIcn(), 2000);
				Assert.assertTrue(exploreSearchPo.getEleExploreIcn().isDisplayed());
				ExtentManager.logger.log(Status.PASS, "Logged into IOS Ipad FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
				System.out.println("Already installed and logged in");
			}
			break;
			
			default:
				System.out.println("OS Error");
				break;

		}

	}
	
	
	
	
	
	
	
	
	
	
	
}
