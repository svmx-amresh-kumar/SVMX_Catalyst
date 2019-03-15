package com.ge.fsa.iphone.pageobjects;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.CommonsPO;
import com.ge.fsa.pageobjects.ExploreSearchPO;
import com.ge.fsa.pageobjects.WorkOrderPO;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;



public class Ip_WorkOrderPO extends BaseLib
{
	public Ip_WorkOrderPO(AppiumDriver driver)
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
	@FindAll({@FindBy(xpath="//*[@text='StartDateTime*']"),
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
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Finalize\"])[3]")
	private WebElement eleFinalize;
	public WebElement getEleFinalize()
	{
		return eleFinalize;
	}
	
	
	
	public void selectAction(CommonsPO commonsPo, String sActionsName) throws InterruptedException
	{
		Thread.sleep(5000);
		getEleActionsLnk().click();	
		commonsPo.swipeGeneric("up");
		getEleActionsTxt(sActionsName).click();		
		Thread.sleep(5000);
	}

	public void createNewEvent(CommonsPO commonsPo,String sSubject) throws InterruptedException
	{
		selectAction(commonsPo,"Create New Event From Work Order");
		Thread.sleep(3000);
		commonsPo.setDateTime24hrs(getEleStartDateTimeTxtFld(), 0,"05", "00"); //set start time to Today
		commonsPo.setDateTime24hrs(getEleEndDateTimeTxtFld(), 0,"06","00"); //set end time
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
	@FindBy(xpath="//XCUIElementTypeOther[@name=\"Add Labor\"]")})
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
	

	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Part Part Lookup\"])[2]")
	private WebElement elepartlookup;
	public WebElement getElepartlookup()
	{
		return elepartlookup;
		}
	@FindBy(xpath="//XCUIElementTypeStaticText[@name=\"Activity Type\"]/../XCUIElementTypeOther")
	private WebElement eleActivityType;
	public WebElement getEleActivityType()
	{
		return eleActivityType;
		}
	
	
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Calibration\"])")
	private WebElement eleCalibration;
	public WebElement getEleCalibration()
	{
		return eleCalibration;
		}
	
	@FindBy(xpath="((//XCUIElementTypeOther[contains(@name,\"Part\")])/XCUIElementTypeOther[3]/XCUIElementTypeOther)[2]")
	private WebElement eleLaborstartdatetime;
	public WebElement getEleLaborstartdatetime()
	{
		return eleLaborstartdatetime;
		}
	
	@FindBy(xpath="(//XCUIElementTypeOther[contains(@name,\"Part\")])/XCUIElementTypeOther[4]/XCUIElementTypeOther")
	private WebElement eleLaborenddatetime;
	public WebElement getEleLaborenddatetime()
	{
		return eleLaborenddatetime;
		}
	
	@FindBy(xpath="//XCUIElementTypeStaticText[@name=\"Line Qty\"]/../XCUIElementTypeOther")
	private WebElement eleLineQtyTxtFld;
	public WebElement getEleLineQtyTxtFld()
	{
		return eleLineQtyTxtFld;
		}
	@FindBy(xpath="//XCUIElementTypeStaticText[@name=\"Line Price Per Unit\"]/../XCUIElementTypeOther")
	private WebElement eleLinePerUnitTxtFld;
	public WebElement getEleLinePerUnitTxtFld()
	{
		return eleLinePerUnitTxtFld;
		}
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\" RESULTS\"])[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther")
	private WebElement elelaborpartresult;
	public WebElement getElelaborpartresult()
	{
		return elelaborpartresult;
		}
	
	@FindBy(xpath="(//XCUIElementTypeOther[@name=\"Save\"])[3]")
	private WebElement elesave;
	public WebElement getElesave()
	{
		return elesave;
		}
	
	public void addParts(Ip_CalendarPO ip_CalendarPo,String sProductName1) throws InterruptedException 
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

	public void addLabor(CommonsPO commonsPo,Ip_CalendarPO ip_CalendarPo,String sProductName1) throws InterruptedException 
	{
		
		getEleLaborLnk().click();
		Thread.sleep(1000);
		getElepartlookup().click();
		ip_CalendarPo.getElelookupsearhproduct().click();
		Thread.sleep(2000);
		ip_CalendarPo.getElelookupsearhproduct().sendKeys(sProductName1);
		Thread.sleep(3000);
		ip_CalendarPo.getEleSearchListItem().click();
		getElelaborpartresult().click();
		Thread.sleep(2000);
		
		getEleActivityType().click();
		getEleCalibration().click();
		
		commonsPo.setDateTime24hrs(getEleLaborstartdatetime(), 0,"0", "0"); //set start time to Today
		commonsPo.setDateTime24hrs(getEleLaborenddatetime(),  1,"9","00"); 
		
		getEleLineQtyTxtFld().click();
		getEleLineQtyTxtFld().sendKeys("10");
		
		getEleLinePerUnitTxtFld().click();
		getEleLinePerUnitTxtFld().sendKeys("1000");
		ip_CalendarPo.getEleAdd().click();
		
		
	}
	
	}	

