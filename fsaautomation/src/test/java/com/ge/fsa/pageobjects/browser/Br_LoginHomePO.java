package com.ge.fsa.pageobjects.browser;


import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.tablet.ExploreSearchPO;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;



public class Br_LoginHomePO
{
	public Br_LoginHomePO(WebDriver chromeDriver)
	{
		this.chromeDriver = chromeDriver;
		PageFactory.initElements(chromeDriver, this);
	}
	WebDriver chromeDriver = null;
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


	public void login(CommonUtility commonUtility, ExploreSearchPO exploreSearchPo, String... sUserTypeFromPropertiesFile) throws InterruptedException, IOException {

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
		
		
			//For Android
			System.out.println("Login For Web UN = "+sUn+" PWD = "+sPwd);

			
					//Login from Sign in Page
					
					//Enter Credentials in Webview Mode
					commonUtility.waitforElement(getEleUserNameTxtFld(), 2);
					getEleUserNameTxtFld().sendKeys(sUn);
					getElePasswordTxtFld().sendKeys(sPwd);
					getEleLoginBtn().click();
					Thread.sleep(3000);

		

	}

	
}
