package com.ge.fsa.pageobjects.phone;

import java.util.Iterator;
import java.util.List;

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
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Ph_ExploreSearchPO {
	public Ph_ExploreSearchPO(AppiumDriver driver) {
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	WebDriverWait wait = null;
	AppiumDriver driver = null;
	TouchAction touchAction = null;
	Iterator<String> iterator = null;

	@FindBy(xpath = "//*[@*='TAB_BAR.EXPLORE_TAB']")
	private WebElement eleExploreIcn;

	public WebElement geteleExploreIcn() {
		return eleExploreIcn;
	}

	@FindBy(xpath = "//*[@*='AUTOMATION SEARCH']")
	private WebElement eleAutomationSearch;

	public WebElement geteleAutomationSearch() {
		return eleAutomationSearch;
	}

	public WebElement getEleSearchListItem(String sName) {

		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='" + sName + "']"));
		} else {
			return driver.findElement(By.xpath("(//*[@label= '"+sName + "'])[last()]"));
		}
	}

	public WebElement getEleSearchChildListName(String sName) {

		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='" + sName + "']"));
		} else {
			return driver
					.findElement(By.xpath("(//*[@label='"+sName+"']//ancestor::*[@*[contains(.,'Item')]])[last()]"));
		}
	}
	
	@FindAll({ @FindBy(xpath = "//*[@*='EXPLORE.SEARCH_BAR']"), @FindBy(xpath = "//*[@*[contains(.,'SEARCH_BAR')]]") })
	// @FindBy(xpath="//*[@*='EXPLORE.SEARCH_BAR']")
	private WebElement eleExploreSearchTxtFld;

	public WebElement getEleExploreSearchTxtFld() {
		return eleExploreSearchTxtFld;
	}

	@FindBy(xpath = "//div[@class='x-component x-button x-button-button-sfmsearch-search x-component-button-sfmsearch-search x-button-no-icon x-stretched sfmsearch-search-button x-haslabel x-layout-box-item x-layout-hbox-item']//span[@class='x-button-label'][text()='Reset filter']")
	// @FindBy(xpath="//span[text()='Reset filter']")
	private WebElement eleResetFilerBtn;

	public WebElement getEleResetFilerBtn() {
		return eleResetFilerBtn;
	}

	@FindAll({ @FindBy(xpath = "//*[@class='android.widget.ScrollView']//*[@class='android.widget.TextView']"),
			@FindBy(xpath = "//XCUIElementTypeScrollView//*[contains(@name,'Item')]") })
	private List<WebElement> searchListItems;

	public List<WebElement> getSearchListItems() {
		return searchListItems;
	}

	@FindAll({ @FindBy(xpath = "//*[@class='android.widget.Switch'][@text='OFF']"),
			@FindBy(xpath = "//*[@name='EXPLORE.SEARCH.INCLUDE_ONLINE_SWITCH']") })
	private WebElement EleOnline;

	public WebElement getEleOnline() {
		return EleOnline;
	}

	private WebElement eleDownloadIcon;

	public WebElement getDownloadIcon(String workOrder) {
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			eleDownloadIcon = driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='" + workOrder
					+ "']/following-sibling::*[@class='android.view.ViewGroup']"));
			return eleDownloadIcon;
		} else {
			eleDownloadIcon = driver.findElement(By.xpath("(//*[@*[contains(.,'" + workOrder + "')]])[last()]"));
			return eleDownloadIcon;

		}
	}

	/**
	 * Select and search any element from explore search list
	 * 
	 * @param commonsUtility
	 *            TODO
	 * @param wElement
	 * @param Record
	 * @throws InterruptedException
	 */
	public void selectFromLookupSearchList(CommonUtility commonsUtility, WebElement wElement, String Record)
			throws InterruptedException {

		System.out.println("Selecting From Lookup Search");
		commonsUtility.custScrollToElementAndClick(wElement);
		// commonsUtility.waitforElement(getEleExploreSearchTxtFld(), 4);
		// getEleExploreSearchTxtFld().click();

		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			getEleExploreSearchTxtFld().sendKeys(Record + "\\n");
		} else {
			getEleExploreSearchTxtFld().sendKeys(Record + "\n");
		}

		Thread.sleep(1000);
		getEleSearchListItem(Record).click();

	}

	/**
	 * Navigate to SFM from explore from child search
	 * 
	 * @param commonUtility
	 * @param ph_WorkOrderPO
	 *            TODO
	 * @param sExploreSearch
	 * @param sExploreChildSearchTxt
	 * @param sSearchListElementTxt
	 * @param sFieldServiceName
	 * @throws InterruptedException
	 */
	public void navigateToSFM(CommonUtility commonUtility, Ph_WorkOrderPO ph_WorkOrderPO, String sExploreSearch,
			String sExploreChildSearchTxt, String sSearchListElementTxt, String sFieldServiceName)
			throws InterruptedException {
		System.out.println("Navigating to SFM for --> " + sExploreSearch + " --> " + sExploreChildSearchTxt + " --> "
				+ sSearchListElementTxt + " --> " + sFieldServiceName + "");
		geteleExploreIcn().click();

		if (commonUtility.waitforElement(ph_WorkOrderPO.getEleBackButton(), 1)) {
			ph_WorkOrderPO.getEleBackButton().click();
			geteleExploreIcn().click();
			Thread.sleep(3000);
		}
		getEleSearchListItem(sExploreSearch).click();
		selectFromLookupSearchList(commonUtility, getEleSearchChildListName(sExploreChildSearchTxt),
				sSearchListElementTxt);
		Thread.sleep(5000);
		if (sFieldServiceName != null) {
			ph_WorkOrderPO.selectAction(commonUtility, sFieldServiceName);
		}

	}

	/**
	 * Navigate to SFM from explore without child
	 * 
	 * @param commonUtility
	 * @param ph_WorkOrderPO
	 *            TODO
	 * @param sExploreSearch
	 * @param sSearchListElementTxt
	 * @param sFieldServiceName
	 * @param sExploreChildSearchTxt
	 * @throws InterruptedException
	 */
	public void navigateToSFM(CommonUtility commonUtility, Ph_WorkOrderPO ph_WorkOrderPO, String sExploreSearch,
			String sSearchListElementTxt, String sFieldServiceName) throws InterruptedException {
		System.out.println("Navigating to SFM for --> " + sExploreSearch + " --> " + sSearchListElementTxt + " --> "
				+ sFieldServiceName + "");
		geteleExploreIcn().click();
		selectFromLookupSearchList(commonUtility, getEleSearchListItem(sExploreSearch), sSearchListElementTxt);
		ph_WorkOrderPO.selectAction(commonUtility, sFieldServiceName);

	}

	private WebElement eleSearchNameTxt;

	public WebElement getEleSearchNameTxt(String sSearchTxt) {
		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			eleSearchNameTxt = driver.findElement(By.xpath("//*[@text='" + sSearchTxt + "']"));
			return eleSearchNameTxt;
		} else {
			return driver.findElement(By.xpath("(//*[@*[contains(.,'" + sSearchTxt + "')]])[last()]"));
		}

	}

	private WebElement eleExploreChildSearchTxt;

	public WebElement getEleExploreChildSearchTxt(String sExploreChildSearchTxt) {

		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			eleExploreChildSearchTxt = driver
					.findElement(By.xpath("//*[contains(@text,'" + sExploreChildSearchTxt + "')]"));
			return eleExploreChildSearchTxt;
		} else {
			eleExploreChildSearchTxt = driver.findElement(By.xpath(
					"(//*[contains(@label,'" + sExploreChildSearchTxt + "')]/*[contains(@name,'Item')])[last()]"));
			return eleExploreChildSearchTxt;
		}

	}
	
	@FindBy(xpath="//*[@*='EXPLORE.SEARCH.RESULT_TEXT'][@*='0 RESULT(S)']")
	private WebElement eleNoRecords;
	public WebElement getEleNoRecords() {
		return eleNoRecords;
	}
	
	public WebElement getEleSearchListItemCount(String sName) {

		if (BaseLib.sOSName.equalsIgnoreCase("android")) {
			return driver.findElement(By.xpath("//*[@class='android.widget.TextView'][@text='" + sName + "']/following-sibling::*[1]"));
		} else {
			return driver.findElement(By.xpath("(//*[@*[contains(.,'" + sName + "')]])[last()]/following-sibling::*[1]"));
		}
	}

}
