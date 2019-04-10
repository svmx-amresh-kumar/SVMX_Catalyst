package com.ge.fsa.pageobjects.phone;


import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	
	@FindBy(xpath="//*[@*='Work Orders']")
	private WebElement eleWorkOrdersChildSearch;
	public WebElement geteleWorkOrdersChildSearch()
	{
		return eleWorkOrdersChildSearch;
	}

	@FindBy(xpath ="//*[@*='Search Keyword...']")
	private WebElement eleSearchKeyword;
	public WebElement geteleSearchKeyword()
	{
		return eleSearchKeyword;
	}
	
	private WebElement eleSearchListItem;
	public WebElement getEleSearchListItem(String sName)
	{
			return eleSearchListItem = driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='"+sName+"']"));
	
	}
	
		
	private WebElement eleSearchNameTxt;
	public WebElement getEleSearchNameTxt(String sSearchTxt)
	{
		 eleSearchNameTxt=driver.findElement(By.xpath("//*[@text='"+sSearchTxt+"']"));
		 return eleSearchNameTxt;
	}
	private WebElement eleExploreChildSearchTxt;
	public WebElement getEleExploreChildSearchTxt(String sExploreChildSearchTxt)
	{
		//eleExploreChildSearchTxt=driver.findElement(By.xpath("//*[contains(@text,'"+sExploreChildSearchTxt+"')]"));
		eleExploreChildSearchTxt=driver.findElement(By.xpath("//*[@text='"+sExploreChildSearchTxt+"']"));
		return eleExploreChildSearchTxt;
	}
	@FindBy(xpath="//*[@*='Search Keyword...']")
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
	
	@FindAll({@FindBy(xpath="//*[@class='android.widget.ScrollView']//*[@class='android.widget.TextView']")})
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
	
	public WebElement getDownloadIcon(String workOrder) {
		return driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='"+workOrder+"']/following-sibling::*[@class='android.view.ViewGroup']"));
	}
	
	
	@FindBy(xpath="//*[@text='Search']")
	private WebElement eleExploreSearchBtn;
	public WebElement getEleExploreSearchBtn()
	{
		return eleExploreSearchBtn;
	}
	private WebElement eleWorkOrderIDTxt;
	public WebElement getEleWorkOrderIDTxt(String sWorkOrderIDTxt)
	{
		eleWorkOrderIDTxt=driver.findElement(By.xpath("//*[@text='"+sWorkOrderIDTxt+"']"));
		 return eleWorkOrderIDTxt;
	}
	public void selectWorkOrder(String sWOName) throws InterruptedException
	{		
		geteleSearchKeyword().click();
		geteleSearchKeyword().clear();
		
		geteleSearchKeyword().sendKeys(sWOName);
		Thread.sleep(4000);
		getEleSearchListItem(sWOName).click();
	}
	
	
	@FindBy(xpath="//*[@text='Search Keyword...']")
	private WebElement eleExploreSearchTxt;
	public WebElement getEleExploreSearchTxt()
	{
		return eleExploreSearchTxt;
	}
	
	
	public void commonlookupsearch(String Record) throws InterruptedException
	{
		getEleExploreSearchTxt().click();
		Thread.sleep(3000);			
		getEleExploreSearchTxt().sendKeys(Record);
		getEleSearchListItem(Record).click();

		
	}
	
}
