package com.ge.fsa.pageobjects.phone;


import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
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



public class Ph_ExploreSearchPO
{
	public Ph_ExploreSearchPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator =null;
	


	
	@FindBy(xpath="//*[@*='TAB_BAR.EXPLORE_TAB']")
	private WebElement eleExploreIcn;
	public WebElement geteleExploreIcn()
	{
		return eleExploreIcn;
	}
	
	@FindBy(xpath="//*[@*='AUTOMATION SEARCH']")
	private WebElement eleAutomationSearch;
	public WebElement geteleAutomationSearch()
	{
		return eleAutomationSearch;
	}


	public WebElement getEleSearchListItem(String sName)
	{

		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			return  driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='"+sName+"']"));
		}else {
			return driver.findElement(By.xpath("(//*[@*[contains(.,'"+sName+"')]])[last()]"));
		}
	}
	
	public WebElement getEleSearchChildListName(String sName)
	{

		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='"+sName+"']"));
		}else {
			return driver.findElement(By.xpath("(//*[contains(@label,'"+sName+"')]/*[contains(@name,'Item')])[last()]"));
		}
	}
		
	
	@FindAll({@FindBy(xpath="//*[@*='Search Keyword...']"),
		@FindBy(xpath="//*[contains(@name,'SEARCH_BAR')]")
	})
	private WebElement eleExploreSearchTxtFld;
	public WebElement getEleExploreSearchTxtFld()
	{
		return eleExploreSearchTxtFld;
	}
	

	
	
	
	@FindBy(xpath="//div[@class='x-component x-button x-button-button-sfmsearch-search x-component-button-sfmsearch-search x-button-no-icon x-stretched sfmsearch-search-button x-haslabel x-layout-box-item x-layout-hbox-item']//span[@class='x-button-label'][text()='Reset filter']")
	//@FindBy(xpath="//span[text()='Reset filter']")
	private WebElement eleResetFilerBtn;
	public WebElement getEleResetFilerBtn()
	{
		return eleResetFilerBtn;
	}
	
	@FindAll({@FindBy(xpath="//*[@class='android.widget.ScrollView']//*[@class='android.widget.TextView']"),
		@FindBy(xpath="//XCUIElementTypeScrollView//*[contains(@name,'Item')]")})
	private List<WebElement> searchListItems;
	public List<WebElement> getSearchListItems(){
		return searchListItems;
	}
	
	@FindAll({@FindBy(xpath="//*[@class='android.widget.Switch'][@text='OFF']"),
			@FindBy(xpath="//*[@name='EXPLORE.SEARCH.INCLUDE_ONLINE_SWITCH']")})	
	private WebElement EleOnline;
	public WebElement getEleOnline() {
		return EleOnline;
	}
	
	private WebElement eleDownloadIcon;
	public WebElement getDownloadIcon(String workOrder) {
		if(BaseLib.sOSName.equalsIgnoreCase("android")) {
			 eleDownloadIcon = driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='"+workOrder+"']/following-sibling::*[@class='android.view.ViewGroup']"));
			 return eleDownloadIcon;
		}else {
			 eleDownloadIcon = driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='"+workOrder+"']/..//../XCUIElementTypeOther/XCUIElementTypeOther"));
			 return eleDownloadIcon;

		}
	}
	
	
	/**
	 * Select and search any element from explore search list
	 * @param commonsUtility TODO
	 * @param wElement
	 * @param Record
	 * @throws InterruptedException
	 */
	public void selectFromLookupSearchList(CommonUtility commonsUtility,WebElement wElement, String Record) throws InterruptedException
	{
		
		System.out.println("Selecting From Lookup Search");
		commonsUtility.custScrollToElementAndClick(wElement);
		commonsUtility.waitforElement(getEleExploreSearchTxtFld(), 4);
		getEleExploreSearchTxtFld().sendKeys(Record);
		Thread.sleep(1000);
		getEleSearchListItem(Record).click();

		
	}


	/**
	 * Navigate to SFM from explore from child search
	 * @param commonUtility
	 * @param ph_WorkOrderPO TODO
	 * @param sExploreSearch
	 * @param sExploreChildSearchTxt
	 * @param sSearchListElementTxt
	 * @param sFieldServiceName
	 * @throws InterruptedException
	 */
		public void navigateToSFM(CommonUtility commonUtility, Ph_WorkOrderPO ph_WorkOrderPO, String sExploreSearch, String sExploreChildSearchTxt, String sSearchListElementTxt, String sFieldServiceName) throws InterruptedException
		{
			System.out.println("Navigating to SFM for --> "+sExploreSearch+" --> "+sExploreChildSearchTxt+" --> "+sSearchListElementTxt+" --> "+sFieldServiceName+"");
			geteleExploreIcn().click();
			
			if (commonUtility.waitforElement(ph_WorkOrderPO.getEleBackButton(), 1)) {
				ph_WorkOrderPO.getEleBackButton().click();
			}
			geteleExploreIcn().click();
			Thread.sleep(3000);
			getEleSearchListItem(sExploreSearch).click();
			selectFromLookupSearchList(commonUtility,getEleSearchChildListName(sExploreChildSearchTxt), sSearchListElementTxt);
			Thread.sleep(5000);
			ph_WorkOrderPO.selectAction(commonUtility,sFieldServiceName);			
			
		}
		
		/**
		 * Navigate to SFM from explore without child
		 * @param commonUtility
		 * @param ph_WorkOrderPO TODO
		 * @param sExploreSearch
		 * @param sSearchListElementTxt
		 * @param sFieldServiceName
		 * @param sExploreChildSearchTxt
		 * @throws InterruptedException
		 */
			public void navigateToSFM(CommonUtility commonUtility, Ph_WorkOrderPO ph_WorkOrderPO, String sExploreSearch, String sSearchListElementTxt, String sFieldServiceName) throws InterruptedException
			{
				System.out.println("Navigating to SFM for --> "+sExploreSearch+" --> "+sSearchListElementTxt+" --> "+sFieldServiceName+"");
				geteleExploreIcn().click();
				selectFromLookupSearchList(commonUtility,getEleSearchListItem(sExploreSearch), sSearchListElementTxt);
				ph_WorkOrderPO.selectAction(commonUtility,sFieldServiceName);			
				
			}
	
}
