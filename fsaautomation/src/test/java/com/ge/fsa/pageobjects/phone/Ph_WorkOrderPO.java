package com.ge.fsa.pageobjects.phone;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.GenericLib;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;



public class Ph_WorkOrderPO extends BaseLib
{
	public Ph_WorkOrderPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	

	@FindAll({@FindBy(xpath="/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[2]/android.view.ViewGroup"),
	@FindBy(xpath="((//XCUIElementTypeOther[contains(@name,\"Work Order\")])/../../../../../XCUIElementTypeOther[2])[4]")})
	private WebElement eleActionsLnk;
	public WebElement getEleActionsLnk()
	{
		return eleActionsLnk;
	}
	
	private WebElement eleActionsTxt;
	public WebElement getEleActionsTxt(String sActionsName)
	{
		
		switch(BaseLib.sOSName.toLowerCase()) {
		case "android":
			eleActionsTxt = driver.findElement(By.xpath("//*[@text='"+sActionsName+"']"));
			return eleActionsTxt;
		case "ios":
			eleActionsTxt=driver.findElement(By.xpath("(//XCUIElementTypeOther[@name='"+sActionsName+"'])[3]"));
			return eleActionsTxt;

		}
		return eleActionsLnk;
		}
	@FindAll({@FindBy(xpath="//*[@text='StartDateTime*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
	//@FindAll({@FindBy(xpath="//*[@text='StartDateTime*']"),
	@FindBy(xpath="(//XCUIElementTypeOther[contains(@name,\"Information\")])/XCUIElementTypeOther[3]/XCUIElementTypeOther")})
	private WebElement eleStartDateTimeTxtFld;
	public WebElement getEleStartDateTimeTxtFld()
	{
		return eleStartDateTimeTxtFld;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='EndDateTime*']"),
	@FindBy(xpath="(//XCUIElementTypeOther[contains(@name,\"Information\")])/XCUIElementTypeOther[5]/XCUIElementTypeOther")})
	private WebElement eleEndDateTimeTxtFld;
	public WebElement getEleEndDateTimeTxtFld()
	{
		return eleEndDateTimeTxtFld;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Subject*']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Subject*\"])[3]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeTextField")})
	private WebElement eleSubjectTxtFld;
	public WebElement getEleSubjectTxtFld()
	{
		return eleSubjectTxtFld;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Save']"),
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Save\"])[3]")})
	private WebElement eleSaveLnk;
	public WebElement getEleSaveLnk()
	{
		return eleSaveLnk;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='ADD SELECTED']"),
	@FindBy(xpath="//XCUIElementTypeOther[@name=\"ADD SELECTED\"]")})
	private WebElement eleAddSelected;
	public WebElement getEleAddSelected()
	{
		return eleAddSelected;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Finalize']"),
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Finalize\"])[3]")})
	private WebElement eleFinalize;
	public WebElement getEleFinalize()
	{
		return eleFinalize;
	}
	
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Create New\"])[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeScrollView/XCUIElementTypeOther/XCUIElementTypeOther[15]")
	private WebElement eleselectprocesscreateevent;
	public WebElement getEleselectprocesscreateevent()
	{
		return eleselectprocesscreateevent;
	}

	
	public void selectAction(CommonUtility commonUtility,Ph_CalendarPO ip_CalendarPo ,String sActionsName) throws InterruptedException
	{
		getEleActionsLnk().click();	
		commonUtility.custScrollToElementAndClick(ip_CalendarPo.getEleselectprocess(sActionsName));
		//getEleActionsTxt(sActionsName).click();		
	}

	public void createNewEvent(CommonUtility commonUtility,String sSubject,Ph_CalendarPO ip_CalendarPo) throws InterruptedException
	{
		selectAction(commonUtility,ip_CalendarPo,"Create New Event From Work Order");
		
		commonUtility.setDateTime24hrs(getEleStartDateTimeTxtFld(), 0,"05", "00"); //set start time to Today
		commonUtility.setDateTime24hrs(getEleEndDateTimeTxtFld(), 0,"06","00"); //set end time
		getEleSubjectTxtFld().click();
		getEleSubjectTxtFld().sendKeys(sSubject);
		Thread.sleep(2000);
		getEleSaveLnk().click();
		Thread.sleep(5000);
	
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Add Parts']"),
	@FindBy(xpath="//XCUIElementTypeOther[@name=\"Add Parts\"]")})
	private WebElement elePartLnk;
	public WebElement getElePartLnk()
	{
		return elePartLnk;
	}
	@FindAll({@FindBy(xpath="//*[@text='Add Labor']"),
	@FindBy(xpath="//XCUIElementTypeOther[@name=\"Add Labor\"]"),
	@FindBy(xpath="//XCUIElementTypeOther[@label=\"Add Labor\"]")})
	private WebElement eleLaborLnk;
	public WebElement getEleLaborLnk()
	{
		return eleLaborLnk;
	}
	
	@FindAll({@FindBy(xpath="//*[@text=''][1]"),
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"\"])[1]")})
	private WebElement elePartcheckbox;
	public WebElement getElePartcheckbox()
	{
		return elePartcheckbox;}
	
	@FindAll({@FindBy(xpath="//*[@text='Part']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Part Part Lookup\"])[2]")})
	private WebElement elepartlookup;
	public WebElement getElepartlookup()
	{
		return elepartlookup;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Activity Type']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
	@FindBy(xpath="//XCUIElementTypeStaticText[@name=\"Activity Type\"]/../XCUIElementTypeOther")})
	private WebElement eleActivityType;
	public WebElement getEleActivityType()
	{
		return eleActivityType;
		}
	
	@FindAll({@FindBy(xpath="//*[@text='Calibration']"),
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Calibration\"])")})
	private WebElement eleCalibration;
	public WebElement getEleCalibration()
	{
		return eleCalibration;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Start Date and Time']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
	@FindBy(xpath="((//XCUIElementTypeOther[contains(@name,\"Part\")])/XCUIElementTypeOther[3]/XCUIElementTypeOther)[2]")})
	private WebElement eleLaborstartdatetime;
	public WebElement getEleLaborstartdatetime()
	{
		return eleLaborstartdatetime;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='End Date and Time']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.TextView']"),
	@FindBy(xpath="(//XCUIElementTypeOther[contains(@name,\"Part\")])/XCUIElementTypeOther[4]/XCUIElementTypeOther")})
	private WebElement eleLaborenddatetime;
	public WebElement getEleLaborenddatetime()
	{
		return eleLaborenddatetime;
	}
	
	@FindAll({@FindBy(xpath="//*[@text='Line Qty']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
	@FindBy(xpath="//XCUIElementTypeStaticText[@name=\"Line Qty\"]/../XCUIElementTypeOther")})
	private WebElement eleLineQtyTxtFld;
	public WebElement getEleLineQtyTxtFld()
	{
		return eleLineQtyTxtFld;
	}
	@FindAll({@FindBy(xpath="//*[@text='Line Price Per Unit']//following-sibling::*[@class='android.view.ViewGroup'][1]//*[@class='android.widget.EditText']"),
	@FindBy(xpath="//XCUIElementTypeStaticText[@name=\"Line Price Per Unit\"]/../XCUIElementTypeOther")})
	private WebElement eleLinePerUnitTxtFld;
	public WebElement getEleLinePerUnitTxtFld()
	{
		return eleLinePerUnitTxtFld;
	}
	
	@FindBy(xpath="//div[@class='x-innerhtml'][text()='This record does not meet the qualification criteria for this SFM Transaction']")
	private WebElement eleThisRecordDoesNotPopup;
	public  WebElement getEleThisRecordDoesNotPopup()
	{

		return eleThisRecordDoesNotPopup;
	}

	@FindBy(xpath="//span[text()='OK']")
	private WebElement eleOKBtn;
	public  WebElement getEleOKBtn()
	{

		return eleOKBtn;
	}
	@FindBy(xpath="(//*[text()='Billing Type']/../..//div[@class='x-input-body-el']/input)[2]")
	private WebElement eleBillingTypeLst;
	public WebElement getEleBillingTypeLst()
	{
		return eleBillingTypeLst;
	}

	@FindBy(xpath="//span[@class='x-button-label'][text()='Save']")
	private WebElement eleClickSave;
	public  WebElement getEleClickSave()
	{

		return eleClickSave;
	}
	@FindBy(xpath="//*[contains(text(),'Saved successfully')]")
	private WebElement eleSavedSuccessTxt;
	public WebElement getEleSavedSuccessTxt()
	{
		return eleSavedSuccessTxt;
	}
	@FindBy(xpath="//div[contains(text(),'Issue')]")
	private WebElement eleIssueFoundTxt;
	public WebElement getEleIssueFoundTxt()
	{
		return eleIssueFoundTxt;
	}
	
	@FindBy(xpath="//span[@class='x-button-label'][text()='Discard Changes']")
	private WebElement eleDiscardBtn;
	public  WebElement getEleDiscardBtn()
	{
		return eleDiscardBtn;
	}
	
	private WebElement eleIssuePopupTxt;
	public WebElement getEleIssuePopupTxt(String sIssueTxt)
	{
		eleIssuePopupTxt = driver.findElement(By.xpath("//span[@class='x-button-label'][text()='"+sIssueTxt+"']"));
		return eleIssuePopupTxt;
	}
	@FindBy(xpath="//*[text() = 'Cancel']")
	private WebElement eleCancelLnk;
	public WebElement getEleCancelLink()
	{
		return eleCancelLnk;
	}
	

	private WebElement elelaborpartresult;
	public WebElement getElelaborpartresult(String sProductName1)
	{
		switch (BaseLib.sOSName.toLowerCase()) {

		case "android":
			elelaborpartresult = driver.findElement(By.xpath("//*[@text='"+sProductName1+"'][@class='android.widget.TextView']"));
			return elelaborpartresult;
		case "ios":
			return elelaborpartresult = driver.findElement(By.xpath("(//XCUIElementTypeOther[@name=\" RESULTS\"])[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther"));
		}
			return elelaborpartresult;
		
	}
	@FindAll({@FindBy(xpath="//*[@text='Save']"),
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Save\"])[3]")})
	private WebElement elesave;
	public WebElement getElesave()
	{
		return elesave;
	}
	
	public void addParts(Ph_CalendarPO ip_CalendarPo,String sProductName1) throws InterruptedException 
	{
		getElePartLnk().click();
		Thread.sleep(3000);
		ip_CalendarPo.getElelookupsearhproduct().click();
		Thread.sleep(3000);
		ip_CalendarPo.getElelookupsearhproduct().sendKeys(sProductName1);
		getElePartcheckbox().click();
		Thread.sleep(3000);
		getEleAddSelected().click();
	}

	public void addLabor(CommonUtility commonUtility,Ph_CalendarPO ip_CalendarPo,String sProductName1) throws InterruptedException 
	{
		commonUtility.custScrollToElementAndClick(getEleLaborLnk());
		Thread.sleep(1000);
		getElepartlookup().click();
		ip_CalendarPo.getElelookupsearhproduct().click();
		Thread.sleep(2000);
		ip_CalendarPo.getElelookupsearhproduct().sendKeys(sProductName1);
		Thread.sleep(3000);
		ip_CalendarPo.getEleSearchListItem(sProductName1).click();
		getElelaborpartresult(sProductName1).click();
		Thread.sleep(2000);
		
		getEleActivityType().click();
		getEleCalibration().click();
		
		commonUtility.setDateTime24hrs(getEleLaborstartdatetime(), 0,"0", "0"); //set start time to Today
		commonUtility.setDateTime24hrs(getEleLaborenddatetime(),  1,"09","00"); 
		
		getEleLineQtyTxtFld().click();
		getEleLineQtyTxtFld().sendKeys("10");
		
		getEleLinePerUnitTxtFld().click();
		getEleLinePerUnitTxtFld().sendKeys("1000");
		ip_CalendarPo.getEleAdd().click();
		
		
	}
	//Navigation to WorkOrder SFM with child search	
	public void navigateToWOSFM( Ph_ExploreSearchPO ph_ExploreSearchPO, String sExploreSearch, String sExploreChildSearchTxt, String sWOName) throws InterruptedException
	{
		try {
				ph_ExploreSearchPO.geteleExploreIcn().click();
				Thread.sleep(GenericLib.iHighSleep);
				ph_ExploreSearchPO.getEleSearchNameTxt(sExploreSearch).click();
				Thread.sleep(3000);
				ph_ExploreSearchPO.getEleExploreChildSearchTxt(sExploreChildSearchTxt).click();
		
				// Select the Work Order
				ph_ExploreSearchPO.selectWorkOrder(sWOName);
				
				}catch(Exception e)
				{
					throw e;
				}
		
			}
	
	}	

