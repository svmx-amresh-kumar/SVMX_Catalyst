package com.ge.fsa.pageobjects.phone;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;

import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.tablet.ExploreSearchPO;
import com.ge.fsa.pageobjects.tablet.ToolsPO;
import com.ge.fsa.pageobjects.phone.Ph_CalendarPO;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSBy;
import io.appium.java_client.pagefactory.iOSFindAll;
import io.appium.java_client.pagefactory.iOSFindBy;
import io.appium.java_client.pagefactory.iOSXCUITBy;
import io.appium.java_client.pagefactory.iOSXCUITFindAll;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;

public class Ph_MorePO {
	public Ph_MorePO(AppiumDriver driver) {
		this.driver = driver;
		//PageFactory.initElements(driver, this);
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	
	@AndroidFindAll({
		@AndroidBy(xpath="//*[@*='TAB_BAR.']"),
		@AndroidBy(xpath="//*[@*='TAB_BAR.MORE_TAB']"),
	})
	@iOSXCUITFindAll({
		@iOSXCUITBy(xpath="//*[@*='TAB_BAR.']"),
		@iOSXCUITBy(xpath="//*[@*='TAB_BAR.MORE_TAB']")
	})
	@FindBy(xpath="//*[@*='TAB_BAR.MORE_TAB']")
	private WebElement eleMoreBtn;
	public WebElement getEleMoreBtn()
	{
		return eleMoreBtn;
	}
	
	@FindBy(xpath="//*[@*='SETTINGS.SYNC']")
	private WebElement eleSyncBtn;
	public WebElement getEleSyncBtn()
	{
//		return eleSyncBtn = driver.findElementByAccessibilityId("SETTINGS.SYNC");
//		try {
//			return eleMoreBtn = driver.findElementByAccessibilityId("Sync");
//
//		} catch (Exception e)
//		{eleMoreBtn = driver.findElement(By.xpath("//*[@text='More']"));}
		return eleSyncBtn;

	}
	
	@FindBy(xpath = "//*[@*='SETTINGS.GENERAL']")
	private WebElement eleGeneralBtn;

	public WebElement getEleGeneralBtn() {
		return eleGeneralBtn;
	}

	// return
	@AndroidFindBy(xpath = "//*[@text='Sync Now']")
	@iOSXCUITFindBy(xpath = "//*[@*='Sync Now']/*")
	private WebElement eleSyncNow;

	public WebElement getEleSyncNow() {
		return eleSyncNow;
	}
	
	
	@AndroidFindAll({
		@AndroidBy(xpath="//*[@text='Sign Out']")})
	@iOSXCUITFindAll({
		@iOSXCUITBy(xpath="//XCUIElementTypeOther[@name=\"Sign Out\"]"),
		@iOSXCUITBy(xpath="//XCUIElementTypeButton[@name=\"Sign Out\"]")})

	private WebElement eleSignOut;

	public WebElement getEleSignOut() {
		return eleSignOut;
	}

	@AndroidFindAll({
		@AndroidBy(xpath="//*[@text='Sign Out']")})
	@iOSXCUITFindAll({
		@iOSXCUITBy(xpath="(//*[@*=\"Sign Out\"])[2]")})
private WebElement eleSignOutpopup;

	public WebElement getEleSignOutpopup() {
		return eleSignOutpopup;
	}
	
	@FindBy(xpath = "//*[@*='Syncing...']")
	private WebElement eleSyncing;

	public WebElement getEleSyncing() {
		return eleSyncing;
	}
	@FindBy(xpath = "//*[@*='Preparing download...']")
	private WebElement elePreparingDownload;

	public WebElement getElePreparingDownload() {
		return elePreparingDownload;
	}
	@FindBy(xpath = "//*[@*[contains(.,'Downloading...')]]")
	private WebElement eleDownloading;

	public WebElement getEleDownloading() {
		return eleDownloading;
	}

	@FindBy(xpath = "//*[@*='APP.TOOLBAR.SYNC_STATUS.BUTTON']")
	private WebElement eleDataSync;

	public WebElement getEleDataSync() {
		return eleDataSync;
	}

	@FindBy(xpath = "//*[@*='SETTING.SYNC.CONFIG.ITEM_BUTTON']")
	private WebElement eleRunConfigSync;

	public WebElement getEleRunConfigSync() {
		return eleRunConfigSync;
	}
	
	@FindBy(xpath = "//*[@*='SETTING.GENERAL.RESETAPP.ITEM_BUTTON']")
	private WebElement eleReset;

	public WebElement getEleReset() {
		return eleReset;
	}
	
	@FindBy(xpath = "//*[@*='Erase and Reinitialize']")
	private WebElement eleEraseReinitialize;

	public WebElement getEleEraseReinitialize() {
		return eleEraseReinitialize;
	}
	
	@AndroidFindBy(xpath = "//*[contains(@text,'Sync completed')]")
	@iOSXCUITFindBy(xpath = "(//*[contains(@label,'Sync completed Last sync time')])")
	private WebElement eleDataSynccompleted;

	public WebElement getEleDataSynccompleted() {
		return eleDataSynccompleted;
	}

	@AndroidFindBy(xpath = "//*[contains(@text,'successful sync')]")
	@iOSXCUITFindBy(xpath = "(//*[contains(@label,'successful sync')])")
	private WebElement eleConfigSyncCompleted;

	public WebElement getEleConfigSyncCompleted() {
		return eleConfigSyncCompleted;
	}

	@AndroidFindBy(xpath = "//*[@text='Configuration sync in progress']")
	@iOSXCUITFindBy(xpath = "(//*[contains(@label,'Configuration sync in progress')])")
	private WebElement eleConfigSyncInProgress;

	public WebElement getEleConfigSyncInProgress() {
		return eleConfigSyncInProgress;
	}

	public void OptionalConfigSync(CommonUtility commonUtility, Ph_CalendarPO ph_CalendarPo,
			Boolean bProcessCheckResult) throws InterruptedException, IOException {
		if (bProcessCheckResult.booleanValue() == true) {
			configSync(commonUtility, ph_CalendarPo);

		} else {
			ExtentManager.logger.log(Status.INFO, "SFM process check return false: SFM Process exists");
			System.out.println("skipping config sync as SFM process check return false: SFM Process exists");
		}

	}

	// @FindBy(xpath="//*[@text='Perform Config Sync']")
	// private WebElement elePerformConfigSync;
	// public WebElement getElePerformConfigSync()
	// {
	// return elePerformConfigSync;
	// }

	@FindBy(xpath = "//*[@text='Cancel']")
	private WebElement eleCancel;

	public WebElement geteleCancel() {
		return eleCancel;
		}
	
	
	@FindBy(xpath="//*[@*='Perform Config Sync']")
	private WebElement elePerformConfigSync;
	public WebElement getElePerformConfigSync()
	{
		return elePerformConfigSync;// = driver.findElementByAccessibilityId("Perform Config Sync");
	}

	public void configSync(CommonUtility commonUtility, Ph_CalendarPO ph_CalendarPo) throws InterruptedException, IOException {
	getEleMoreBtn().click();
	getEleSyncBtn().click();
	System.out.println("Clicked on Sync button");
	getEleRunConfigSync().click();
	System.out.println("Clicked on run config button");
	try {getElePerformConfigSync().click();}catch(Exception e) {}
	commonUtility.waitForElementNotVisible(getEleConfigSyncInProgress(),800);
	//System.out.println("clicking perform config sync now waiting for calandar new");
	commonUtility.waitforElement(ph_CalendarPo.getEleCalendarViewMenu(), 200);
	getEleMoreBtn().click();
	commonUtility.waitforElement(getEleSyncBtn(),3);
	getEleSyncBtn().click();
	commonUtility.waitForElementNotVisible(getEleSyncBtn(),3);
	System.out.println("Clicked on Sync button and waiting...");
	ExtentManager.logger.info("After Config Sync", MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
	if(commonUtility.waitforElement(getEleConfigSyncCompleted(), 3)) {
	ExtentManager.logger.log(Status.INFO,"Config Sync Completed sucessfully");
}else {
	ExtentManager.logger.log(Status.FAIL,"Config Sync Failed");
}

}

	
	public void syncData(CommonUtility commonUtility) throws InterruptedException, IOException
	{
		
		
		//Navigation to more screen
		getEleMoreBtn().click();
		System.out.println("Begining Data Sync");
		getEleDataSync().click();
		Thread.sleep(200);
		getEleSyncNow().click();
		System.out.println("Clicked on Sync Now and waiting...");
		Thread.sleep(200);
		commonUtility.waitForElementNotVisible(getEleSyncing(), 300);
		ExtentManager.logger.info("After Data Sync", MediaEntityBuilder.createScreenCaptureFromPath(commonUtility.takeScreenShot()).build());
		
		if(commonUtility.isDisplayedCust(getEleDataSynccompleted())) {
			System.out.println("Data Sync Completed Sucessfully");
			ExtentManager.logger.log(Status.INFO,"Data Sync is successfull");
		}else {
			System.out.println("Data Sync Failed");
			//Verification of successful sync
			ExtentManager.logger.log(Status.FAIL,"Data Sync Failed");
			Assert.assertTrue(2<1, "Data Sync Failed");
		}
		
		commonUtility.press(getEleMoreBtn().getLocation());

	}
	public void resetApp(CommonUtility commonUtility) throws InterruptedException, IOException {
		getEleMoreBtn().click();
		getEleGeneralBtn().click();
		System.out.println("Clicked on General button");
		commonUtility.clickPopup(getEleReset(), getEleEraseReinitialize());
		System.out.println("Clicked on Erase and reinitialize data button");
		commonUtility.waitForElementNotVisible(getEleSyncing(), 20);
		commonUtility.waitForElementNotVisible(getElePreparingDownload(), 20);
		commonUtility.waitforElement(getEleDownloading(), 20);
		if (commonUtility.waitforElement(getEleMoreBtn(), 20)) {
			ExtentManager.logger.log(Status.INFO, "Erasing and reinitializing data Completed sucessfully");
		} else {
			ExtentManager.logger.log(Status.FAIL, "Erasing and reinitializing data  Failed");
		}
	}
	@FindAll({@FindBy(xpath="//android.view.ViewGroup[@content-desc=\"SYNC.STATUS_DROPDOWN\"]/android.view.ViewGroup[2]/android.view.ViewGroup/android.widget.TextView"),
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Resolve\"])[2]")})
	private WebElement eleResolveBtn;
	public WebElement getEleResolveBtn() {
		return eleResolveBtn;
	}
	
	
	@FindAll({@FindBy(xpath="(//android.view.ViewGroup[@content-desc=\"APP.CONNECTION_TYPE.0\"])[3]/android.widget.TextView"),
	@FindBy(xpath="//XCUIElementTypeOther[@name=\"Remove record permanently\"]")})
	private WebElement eleResoveConflict;
	public WebElement getEleResolveConflict() {
		return eleResoveConflict;
	}
}
