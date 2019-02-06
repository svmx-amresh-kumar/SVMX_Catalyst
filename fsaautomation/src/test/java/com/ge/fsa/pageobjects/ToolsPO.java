package com.ge.fsa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;

public class ToolsPO  
{
	public ToolsPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	
	@FindBy(xpath="//div[text()='Tools']")
	private WebElement eleToolsIcn;
	public WebElement getEleToolsIcn()
	{
		return eleToolsIcn;
	}
	@FindBy(xpath="//*[text() = 'Sync Data Now']")
	private WebElement eleSyncDataNowLnk;
	public WebElement getEleSyncDataNowLnk()
	{
		return eleSyncDataNowLnk;
	}
	
	/*@FindBy(xpath="//*[text() = 'Sync Config Now']")
	private WebElement eleSyncConfigNowLnk;
	public WebElement geteleSyncConfigNowLnk()
	{
		return eleSyncConfigNowLnk;
	}	*/
		
	@FindBy(xpath="//span[@class='x-button-label'][text()='Sync Config Now']")
	private WebElement eleSyncConfigNowLnk;
	public WebElement geteleSyncConfigNowLnk()
	{
		return eleSyncConfigNowLnk;
	}

	
	
	@FindBy(xpath="//span[text() ='Reset Application'][@class='x-button-label']")
	private WebElement eleResetAppLnk;
	public WebElement geteleResetAppLnkk()
	{
		return eleResetAppLnk;
	}	
	
	@FindBy(xpath="//*[text()='Start Sync']")
	private WebElement eleStartSyncBtn;
	public WebElement getEleStartSyncBtn()
	{
		return eleStartSyncBtn;
	}
	@FindBy(xpath="//*[text()='Success']")
	private WebElement eleSuccessTxt;
	public WebElement getEleSuccessTxt()
	{
		return eleSuccessTxt;
	}
	
	@FindBy(xpath="//*[text()='Refreshing view']")
	private WebElement eleRefreshingViewTxt;
	public WebElement getEleRefreshingViewTxt()
	{
		return eleRefreshingViewTxt;
	}
	
	/*@FindBy(xpath="//*[text()='OK']")
	private WebElement eleOkBtn;
	public WebElement getEleOkBtn()
	{
		return eleOkBtn;
	}*/
	
	@FindBy(xpath="//span[@class='x-button-label'][text()='OK']")
	private WebElement eleOkBtn;
	public WebElement getEleOkBtn()
	{
		return eleOkBtn;
	}
	
	@FindBy(xpath="//*[text()='Yes']")
	private WebElement eleYesBtn;
	public WebElement getEleYesBtn()
	{
		return eleYesBtn;
	}
	
	@FindBy(xpath = "//*[text()='Config Sync in Progress']")
	private WebElement eleConfigSyncinProgressTxt;
	public WebElement geteleConfigSyncinProgressTxt()
	{
		return eleConfigSyncinProgressTxt;
	}
	
	@FindBy(xpath="//span[text()='Config Sync']")
	private WebElement eleConfigSyncBarMenuLnk;
	public WebElement geteleConfigSyncBarMenuLnk()
	{
		return eleConfigSyncBarMenuLnk;
	}	
	
//	@FindBy(xpath="//span[@class='x-button-label'][text()='Cancel']")
	@FindBy(xpath="//div[@data-componentid='configCancel']")
	//@FindBy(xpath="//*[text()='Cancel']")
	private WebElement eleCancelConfigSyncBtn;
	public WebElement geteleCancelConfigSyncBtn()
	{
		return eleCancelConfigSyncBtn;
	}
	
	@FindBy(xpath="//*[text()='Sign Out']//..//span[@class='x-button-label']")
	private WebElement eleSignOutBtn;
	public WebElement geteleSignOutBtn()
	{
		return eleSignOutBtn;
	}
	
	@FindBy(xpath="//*[text()='Sign Out']//..//span[@class='x-button-icon x-font-icon x-hidden']//..//span[@class='x-button-label']")
	private WebElement elepopSignOutBtn;
	public WebElement getelepopSignOutBtn()
	{
		return elepopSignOutBtn;
	}
	
	
	@FindBy(xpath="//div[@class='x-inner x-container-inner x-align-start x-pack-start x-layout-vbox x-vertical x-layout-box x-component-inner']//div[@class='x-component x-label x-label-svmx-default x-component-svmx-default label-tools-card-description x-layout-box-item x-layout-vbox-item x-stretched'][2]//div[@class='x-innerhtml'][1]")
	private WebElement eleConfigSyncStatusTxt;
	public WebElement geteleConfigSyncStatusTxt()
	{
		return eleConfigSyncStatusTxt;
	}
	
	
	@FindBy(xpath="//div[@class='x-panel-title-text'][text()='Configuration sync is due']")
	private WebElement eleConfigSyncDue;
	public WebElement geteleConfigSyncDue()
	{
		return eleConfigSyncDue;
	}
	
	@FindBy(xpath="//span[@class='svmx-conflict-badge']")
	private WebElement eleConflictBadge;
	public WebElement geeteleConflictBadge()
	{
		return eleConflictBadge;
	}
	
	
	@FindBy(xpath="//span[@class='tools-sync-button-label-largemodern'][text()='Config Sync']/../span[@class='x-badge']")
	private WebElement eleConfigSyncAlertBadge;
	public WebElement geteleConfigSyncAlertBadge()
	{
		return eleConfigSyncAlertBadge;
	}
	
	/**
	 * Perform Data Sync and Wait for the Data sync to complete in 900 seconds/ 15 min, beyond which we fail it
	 * @param commonsPo
	 * @throws InterruptedException
	 */
	public void syncData(CommonsPO commonsPo) throws InterruptedException
	{
		GenericLib.lWaitTime=3*60*1000;
		
		//Navigation to Tools screen
		commonsPo.tap(getEleToolsIcn());	
		Assert.assertTrue(getEleSyncDataNowLnk().isDisplayed(), "Tools screen is not displayed");
		ExtentManager.logger.log(Status.PASS,"Tools screen is displayed successfully");
		
		commonsPo.tap(getEleSyncDataNowLnk());
		commonsPo.tap(getEleStartSyncBtn());
		System.out.println("Begining Data Sync");

		commonsPo.waitforElement(getEleRefreshingViewTxt(),240);

//		//Verification of successful sync
//		Assert.assertTrue(getEleSuccessTxt().isDisplayed(), "Data sync is not successfull");
//		ExtentManager.logger.log(Status.PASS,"Data Sync is successfull");
//		Thread.sleep(GenericLib.iHighSleep);
		
		//Wait for the data sync to complete in 900 seconds/ 15 min, beyond which we fail it
		if( commonsPo.waitForString(getEleSuccessTxt(), "Success", 900)) {
			System.out.println("Data Sync Completed Sucessfully");
			ExtentManager.logger.log(Status.PASS,"Data Sync is successfull");
		}else {
			System.out.println("Data Sync Failed");
			//Verification of successful sync
			ExtentManager.logger.log(Status.FAIL,"Data Sync Failed");
			Assert.assertTrue(2<1, "Data Sync Failed");
		}
		
		driver.activateApp(GenericLib.sAppBundleID);
		
	}
	
	/**
	 * Perform Config Sync and Wait for the config sync to complete in 2000 seconds/ 30 min, beyond which we fail it
	 * @param commonsPo
	 * @throws InterruptedException
	 */
		public void configSync(CommonsPO commonsPo) throws InterruptedException
		{
			GenericLib.lWaitTime=25*60*1000;
			
			//Navigation to Tools screen
			commonsPo.tap(getEleToolsIcn());	
			Assert.assertTrue(getEleSyncDataNowLnk().isDisplayed(), "Tools screen is not displayed");
			ExtentManager.logger.log(Status.PASS,"Tools screen is displayed successfully");

			
			commonsPo.tap(geteleSyncConfigNowLnk());
			Thread.sleep(GenericLib.iLowSleep);
			commonsPo.tap(getEleOkBtn());
			Thread.sleep(GenericLib.iMedSleep);
			//Canceling sync in order to reset the config sync status.
			commonsPo.waitforElement(eleCancelConfigSyncBtn, 30);
			commonsPo.tap(eleCancelConfigSyncBtn,20,20);
			Thread.sleep(3000);
			commonsPo.tap(geteleSyncConfigNowLnk());	
			commonsPo.tap(getEleOkBtn());
			System.out.println("Begining config sync");
	
			//Wait for the config sync to complete in 2000 seconds/ 30 min, beyond which we fail it
			if( commonsPo.waitForString(geteleConfigSyncStatusTxt(), "Success", 2000)) {
				System.out.println("Config Sync Completed Sucessfully");
				ExtentManager.logger.log(Status.PASS,"Config Sync is successfull");
			}else {
				System.out.println("Config Sync Failed");
				//Verification of successful sync
				ExtentManager.logger.log(Status.FAIL,"Config Sync Failed");
				Assert.assertTrue(2<1, "Config Sync Failed");
			}
																	
			driver.activateApp(GenericLib.sAppBundleID);
			
		}
	
		
		//reset app
		
		public void Resetapp(CommonsPO commonsPo,ExploreSearchPO exploreSearchPo) throws InterruptedException
		{
			GenericLib.lWaitTime=5*60*1000;
			commonsPo.tap(getEleToolsIcn());	
			Assert.assertTrue(getEleSyncDataNowLnk().isDisplayed(), "Tools screen is not displayed");
			ExtentManager.logger.log(Status.PASS,"Tools screen is displayed successfully");
			
			
			
			commonsPo.tap(geteleResetAppLnkk());
			commonsPo.longPress(geteleResetAppLnkk());	
			getEleYesBtn().click();
			commonsPo.longPress(getEleYesBtn());
			System.out.println("begining Reset App");
			
			commonsPo.waitforElement(exploreSearchPo.getEleExploreIcn(), 20*60*1000);
			
		wait = new WebDriverWait(driver, 40000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[text()='Explore']")));
		Assert.assertTrue(exploreSearchPo.getEleExploreIcn().isDisplayed());
		ExtentManager.logger.log(Status.PASS,"Rest app successfully");	
		System.out.println("Rest app successfully");		
	
			
			
		}
	
		@FindBy(xpath="//div[@class='x-unsized x-component x-label x-label-svmx-default x-component-svmx-default label-tools-card-description label-tools-sync-conflict-details x-layout-auto-item']//div[@class='x-innerhtml']")
		private WebElement elesyncconflicterror;
		public WebElement getelesyncconflicterror()
		{
			return elesyncconflicterror;
		}
		
		
		@FindBy(xpath="//span[text()='Resolve']")
		private WebElement eleResolve;
		public WebElement geteleResolve()
		{
			return eleResolve;
		}
		
		@FindBy(xpath="//span[text()='Remove record permanently']")
		private WebElement eleResolveissue;
		public WebElement geteleResolveissue()
		{
			return eleResolveissue;
		}
		@FindBy(xpath="//span[text()='Apply']")
		private WebElement eleApply;
		public WebElement geteleApply()
		{
			return eleApply;
		}
		
		public void OptionalConfigSync(ToolsPO toolsPo,CommonsPO commonsPo, Boolean bProcessCheckResult) throws InterruptedException
		{
		if(bProcessCheckResult.booleanValue()== true)
		{
			toolsPo.configSync(commonsPo);
		}
		else 
		{
			ExtentManager.logger.log(Status.INFO,"SFM process check return false: SFM Process exists");
			System.out.println("skipping config sync as SFM process check return false: SFM Process exists");
		}
		}
		
}
