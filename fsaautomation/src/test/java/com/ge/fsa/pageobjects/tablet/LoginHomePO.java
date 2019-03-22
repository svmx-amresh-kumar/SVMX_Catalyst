package com.ge.fsa.pageobjects.tablet;


import java.io.IOException;
import java.util.Iterator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
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
	 * Login to FSA app based on values from config.properties. (** For any other property file machine set the RUN_MACHINE=automation_build , which will pick up the data from config_automation_build.properties file)
	 * Default is "TECH_USN"
	 * @param commonsUtility
	 * @param exploreSearchPo
	 * @param sUserTypeFromPropertiesFile
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void login(CommonUtility commonUtility, ExploreSearchPO exploreSearchPo, String... sUserTypeFromPropertiesFile) throws InterruptedException, IOException {

		String sUn = null;
		String sPwd = null;
		
		String sUser = sUserTypeFromPropertiesFile.length>1?sUserTypeFromPropertiesFile[0]:"";
		
		if(sUser.equalsIgnoreCase("TECH_USN_1")) {
			 sUn = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,BaseLib.sUsePropertyFile, "TECH_USN_1");
			 sPwd = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,BaseLib.sUsePropertyFile, "TECH_PWD_1");
		}else if(sUser.equalsIgnoreCase("TECH_USN")){
		 sUn = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,BaseLib.sUsePropertyFile, "TECH_USN");
		 sPwd = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,BaseLib.sUsePropertyFile, "TECH_PWD");
		}else {
			//default
			 sUn = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,BaseLib.sUsePropertyFile, "TECH_USN");
			 sPwd = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,BaseLib.sUsePropertyFile, "TECH_PWD");
		}
		
		switch (BaseLib.sOSName) {
		case "android":

			//For Android
			System.out.println("Login For Android UN = "+sUn+" PWD = "+sPwd);

			if(commonUtility.waitforElement(exploreSearchPo.getEleExploreIcn(), 1)){
				System.out.println("Logged in Already");

			}else {
				try {

					//Login from Sign in Page
					//commonUtility.switchContext("Webview");
					try {
						getEleSignInBtn().click();
					}catch(Exception e) {
						System.out.println("ByPassing 'Sign In' Button");
					}

					//Change to SandBox from native mode
					commonUtility.switchContext("Native");
					commonUtility.waitforElement(getEleMenuIcn(), 2);
					getEleMenuIcn().click();
					commonUtility.waitforElement(getEleSandBxRdBtn(), 2);
					getEleSandBxRdBtn().click();

					touchAction = new TouchAction(driver);
					touchAction.tap(new PointOption().withCoordinates(150, 150)).perform();

					//Enter Credentials in Webview Mode
					commonUtility.switchContext("Webview");
					commonUtility.waitforElement(getEleUserNameTxtFld(), 2);
					getEleUserNameTxtFld().sendKeys(sUn);
					getElePasswordTxtFld().sendKeys(sPwd);
					getEleLoginBtn().click();


				} catch (Exception e) {}

				//Either click Allow or Skip it without an exception
				try {
					getEleAllowBtn().click();
				} catch (Exception e1) {}

				commonUtility.switchContext("Webview");
				//Check if username field is not displayed
				Assert.assertTrue(!commonUtility.waitforElement(getEleUserNameTxtFld(), 1),"Login Failed");
				//Wait for the Explore button to be visible
				Assert.assertTrue(commonUtility.waitforElement(exploreSearchPo.getEleExploreIcn(), 1000),"Login Failed");

			}
			ExtentManager.logger.log(Status.PASS, "Logged into Android FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
			System.out.println("Already installed and logged in");
			break;

		case "ios":

			System.out.println("Login For IOS UN = "+sUn+" PWD = "+sPwd);

			if(commonUtility.waitforElement(exploreSearchPo.getEleExploreIcn(), 1)){
				System.out.println("Logged in Already");

			}else {
				try {//For IOS
					//Login from Sign in Page
					try {
						getEleSignInBtn().click();
					}catch(Exception e) {
						System.out.println("ByPassing 'Sign In' Button");
					}

					//Enter Credentials
					commonUtility.waitforElement(getEleUserNameTxtFld(), 2);
					getEleUserNameTxtFld().sendKeys(sUn);
					getElePasswordTxtFld().sendKeys(sPwd);
					getEleLoginBtn().click();

				} catch (Exception e) {}

				//Either click Allow or Skip it without an exception
				try {
					commonUtility.waitforElement(getEleAllowBtn(), 1);
					getEleAllowBtn().click();
				} catch (Exception e2) {
				}
				//Check if username field is not displayed
				Assert.assertTrue(!commonUtility.waitforElement(getEleUserNameTxtFld(), 1),"Login Failed");
				//Wait for the Explore button to be visible
				Assert.assertTrue(commonUtility.waitforElement(exploreSearchPo.getEleExploreIcn(), 1000),"Login Failed");
			}

			ExtentManager.logger.log(Status.PASS, "Logged into IOS Ipad FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
			System.out.println("Logged in Successfully");
			break;

		default:
			System.out.println("OS Error");
			break;

		}

	}

	
}
