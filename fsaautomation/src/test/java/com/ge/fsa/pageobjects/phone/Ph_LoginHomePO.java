package com.ge.fsa.pageobjects.phone;


import java.io.IOException;
import java.util.Iterator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.tablet.*;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.touch.offset.PointOption;



public class Ph_LoginHomePO
{
	public Ph_LoginHomePO(AppiumDriver driver)
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
	
	@FindAll({@FindBy(xpath="//*[@text='SIGN IN WITH SALESFORCE']"),
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"SIGN IN WITH SALESFORCE\"])[2]")})
//	@AndroidFindBy(xpath="//*[@text='SIGN IN WITH SALESFORCE']")
//		@iOSFindBy(xpath="(//XCUIElementTypeOther[@name=\"SIGN IN WITH SALESFORCE\"])[2]")
	private WebElement eleSignInBtn;
	public WebElement getEleSignInBtn()
	{
		return eleSignInBtn;
	}
	

	@FindAll({@FindBy(xpath="//*[@resource-id='username']"),
		@FindBy(xpath="//XCUIElementTypeOther[@name=\"Login | Salesforce\"]/XCUIElementTypeTextField")})
	private WebElement eleUserNameTxtFld;
	public WebElement getEleUserNameTxtFld()
	{
		return eleUserNameTxtFld;
	}

	
	@FindAll({@FindBy(xpath="//*[@resource-id='password']"),
		@FindBy(xpath="//XCUIElementTypeOther[@name=\"Login | Salesforce\"]/XCUIElementTypeSecureTextField")})
	private WebElement elePasswordTxtFld;
	public WebElement getElePasswordTxtFld()
	{
		return elePasswordTxtFld;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Log In to Sandbox']"),
	@FindBy(id="Log In to Sandbox"),
	@FindBy(xpath="//*[@content-desc='Log In to Sandbox']")})
	private WebElement eleLoginBtn;
	public WebElement getEleLoginBtn()
	{
		return eleLoginBtn;
	}

	@FindAll({@FindBy(id="//*[@content-desc=' Allow  Allow']"),
	@FindBy(id=" Allow "),
	@FindBy(xpath="//*[@resource-id='oaapprove']"),
	@FindBy(id="oaapprove"),
	@FindBy(xpath="//*[@text='Allow']")})
	//@FindBy(xpath="//*[@content-desc=' Allow  Allow']")
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
	
	@FindAll({@FindBy(xpath="//*[@text='Production']"),
	@FindBy(id="Production")})
	//@FindBy(xpath="//*[@text='Production']")
	private WebElement eleProductionBtn;
	public WebElement getEleProductionBtn() 
	{
		return eleProductionBtn;
	}
	@FindAll({@FindBy(xpath="//*[@text='Sandbox']"),
	@FindBy(id="Sandbox")})
	private WebElement eleSandboxBtn;
	public WebElement getEleSandboxBtn() 
	{
		return eleSandboxBtn;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='https://test.salesforce.com']"),
	@FindBy(id="Sandbox https://test.salesforce.com")})
	private WebElement eleSandbocURlbtn;
	public WebElement getEleSandbocURlbtn()
	{
		return eleSandbocURlbtn;
	}
	
	@FindAll({@FindBy(xpath="//XCUIElementTypeStaticText[@name='']"),
		@FindBy(xpath="//*[@text='']")})
	private WebElement eleSettingsbtn;
	public WebElement getEleSettingsbtn()
	{
		return eleSettingsbtn;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='']"),
	@FindBy(xpath="(//XCUIElementTypeOther[@name='Change Environment'])[3]/XCUIElementTypeOther[1]")})
	private WebElement eleChangeEnvironmentBackbtn;
	public WebElement getEleChangeEnvironmentBackbtn()
	{
		return eleChangeEnvironmentBackbtn;
	}

	@FindAll({@FindBy(xpath="//*[@text='']"),
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Sign In Settings\"])[7]/XCUIElementTypeOther[1]")})
	private WebElement eleSignInSettingsBackbtn;
	public WebElement getEleSignInSettingsBackbtn()
	{
		return eleSignInSettingsBackbtn;
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
	public void login(CommonUtility commonUtility, Ph_MorePO ip_MorePo, String... sUserTypeFromPropertiesFile) throws InterruptedException, IOException {

		String sUn = null;
		String sPwd = null;
		
		String sUser = sUserTypeFromPropertiesFile.length>1?sUserTypeFromPropertiesFile[0]:"";
		
		if(sUser.equalsIgnoreCase("TECH_USN_1")) {
			 sUn = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,BaseLib.sSelectConfigPropFile, "TECH_USN_1");
			 sPwd = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,BaseLib.sSelectConfigPropFile, "TECH_PWD_1");
		}else if(sUser.equalsIgnoreCase("TECH_USN")){
		 sUn = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,BaseLib.sSelectConfigPropFile, "TECH_USN");
		 sPwd = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,BaseLib.sSelectConfigPropFile, "TECH_PWD");
		}else {
			//default
			 sUn = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,BaseLib.sSelectConfigPropFile, "TECH_USN");
			 sPwd = GenericLib.readExcelData(GenericLib.sConfigPropertiesExcelFile,BaseLib.sSelectConfigPropFile, "TECH_PWD");
		}
		
		switch (BaseLib.sOSName) {
		case "android":

			System.out.println("Login For Iphone android UN = "+sUn+" PWD = "+sPwd);
			System.out.println("TEST "+getEleSignInBtn().toString());
			if(commonUtility.waitforElement(ip_MorePo.getEleMoreBtn(), 1)){
				System.out.println("Logged in Already");

			}else {
				try {//For Android
					//Login from Sign in Page
					
					
					Thread.sleep(1000);
					getEleSignInBtn().click();
					Thread.sleep(1000);
					//Change to sandbox first if not already
					if(!commonUtility.waitforElement(getEleLoginBtn(),2)) {
						getEleSettingsbtn().click();
						getEleProductionBtn().click();
						getEleSandbocURlbtn().click();
						getEleChangeEnvironmentBackbtn().click();
						getEleSignInSettingsBackbtn().click();	
					}
									
					commonUtility.waitforElement(getEleUserNameTxtFld(),2);
					getEleUserNameTxtFld().click();
					getEleUserNameTxtFld().sendKeys(sUn);
					Thread.sleep(2000);

					getElePasswordTxtFld().click();
					getElePasswordTxtFld().sendKeys(sPwd);
					
					getEleLoginBtn().click();
					//Either click Allow or Skip it without an exception
					try {
						commonUtility.waitforElement(getEleAllowBtn(), 5);
						getEleAllowBtn().click();
		

						} catch (Exception e1){System.out.println(e1);}

				
					Thread.sleep(2000);

					
					
				} catch (Exception e) {System.out.println(e);}


				//Check if username field is not displayed
				Assert.assertTrue(!commonUtility.waitforElement(getEleUserNameTxtFld(), 1),"Login Failed");
				//Wait for the Explore button to be visible
				Assert.assertTrue(commonUtility.waitforElement(ip_MorePo.getEleMoreBtn(), 1000),"Login Failed");

			}
			
			ExtentManager.logger.log(Status.PASS, "Logged into IOS Android FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
			System.out.println("Logged in Successfully");
			break;

		case "ios":

			System.out.println("Login For Iphone IOS UN = "+sUn+" PWD = "+sPwd);

			if(commonUtility.waitforElement(ip_MorePo.getEleMoreBtn(), 1)){
				System.out.println("Logged in Already");

			}else {
				try {//For IOS
					//Login from Sign in Page
					
					
					Thread.sleep(4000);
					getEleSignInBtn().click();
					Thread.sleep(4000);
					if(!commonUtility.waitforElement(getEleLoginBtn(),1)) {
						getEleSettingsbtn().click();
						getEleProductionBtn().click();
						getEleSandbocURlbtn().click();
						getEleChangeEnvironmentBackbtn().click();
						getEleSignInSettingsBackbtn().click();	
					}
									
					
					getEleUserNameTxtFld().click();
					getEleUserNameTxtFld().sendKeys(sUn);
					Thread.sleep(4000);

					getElePasswordTxtFld().click();
					getElePasswordTxtFld().sendKeys(sPwd);
					
					getEleLoginBtn().click();
					//Either click Allow or Skip it without an exception
					try {
						commonUtility.waitforElement(getEleAllowBtn(), 5);

						getEleAllowBtn().click();
						} catch (Exception e1) {}

				
					Thread.sleep(2000);

					
					
				} catch (Exception e) {}


				//Check if username field is not displayed
				Assert.assertTrue(!commonUtility.waitforElement(getEleUserNameTxtFld(), 1),"Login Failed");
				//Wait for the Explore button to be visible
				Assert.assertTrue(commonUtility.waitforElement(ip_MorePo.getEleMoreBtn(), 1000),"Login Failed");

			}
			
			ExtentManager.logger.log(Status.PASS, "Logged into IOS iPhone FSA app successfully for : UN = "+ sUn +" : PWD = "+sPwd);
			System.out.println("Logged in Successfully");
			
			break;
		}

	}

	
}
