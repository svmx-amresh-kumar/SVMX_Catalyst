package com.ge.fsa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.ge.fsa.lib.GenericLib;
import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;

import io.appium.java_client.AppiumDriver;

public class ChecklistPO {
	
	public ChecklistPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	AppiumDriver driver = null;
	
	
	private WebElement eleChecklistName;
	public WebElement geteleChecklistName(String checklistname)
	{
		eleChecklistName = driver.findElement(By.xpath("(//div[text()='"+checklistname+"'])[1]"));
		return eleChecklistName;
	}

	
	@FindBy(xpath="//div[@class='x-component x-button x-button-svmx-button-square x-component-svmx-button-square x-button-svmx-save-btn x-component-svmx-save-btn x-button-no-icon sfm-checklist-completed-btn x-layout-box-item x-layout-hbox-item x-stretched']")
	private WebElement eleChecklistSubmit;
	public WebElement eleChecklistSubmit()
	{
		return eleChecklistSubmit;
	}
	
	@FindBy(xpath="//a[@class='checklist-lineup-list-item-actionlable'][text()='Start New']")

	private WebElement eleStartNew;
	public WebElement geteleStartNew()
	{
		return eleStartNew;
	}
	
	
    @FindBy(xpath="//span[text()='Next']")
    private WebElement eleNext;
    public WebElement eleNext()
    {
    	return eleNext;
    }
    
    
  /* @FindBy(xpath= "//*[text()='Submit'][2]")
    private WebElement eleSubmitbutton;
    public WebElement eleSubmitbutton()
    {
    	return eleSubmitbutton;
    }*/
    
    
	@FindBy(xpath="//div[@class='x-component x-button x-button-no-icon x-button-button-tools-msgbox x-component-button-tools-msgbox x-button-button-highlight x-component-button-highlight svmx-msg-button-default x-haslabel x-layout-box-item x-layout-hbox-item x-flexed x-stretched']/span[text()='Submit']")
	private WebElement eleChecklistPopupSubmit;
    public WebElement eleChecklistPopupSubmit()
    {
    	return eleChecklistPopupSubmit;
    }
    
    @FindBy(xpath="//span[text()='Show completed checklists']")
    private WebElement eleShowCompletedChecklist;
    public WebElement eleShowCompletedChecklist()
    {
    	return eleShowCompletedChecklist;
    }
    
    private WebElement eleCompletedChecklistName;
	public WebElement eleCompletedChecklistName(String checklistname)
	{
		
		eleCompletedChecklistName = driver.findElement(By.xpath("(//div[@class='checklist-lineup-list-item-name'][text()='"+checklistname+"'])[2]"));
		return eleCompletedChecklistName;
	}
    
	
	private WebElement eleChecklistAnswerText;
	public WebElement  geteleChecklistAnswerTextArea(String ChecklistTextQuestion)
	{
		return eleChecklistAnswerText = driver.findElement(By.xpath("//div[text()='"+ChecklistTextQuestion+"'][@class='x-innerhtml']/../..//textarea"));
	}
	
	
	
	private WebElement eleChecklistrequiredTxt;
	public WebElement  geteleChecklistrequiredTxt(String ChecklistTextQuestion)
	{
		return eleChecklistrequiredTxt = driver.findElement(By.xpath("//div[contains(text(), '"+ChecklistTextQuestion+"')][@class='x-innerhtml']/../..//textarea"));
	}
	              
	@FindBy(xpath="//*[text()='Please fill this required field and submit again.']")
	private WebElement elefillrequiredfieldlbl;
	public WebElement getelefillrequiredfieldlbl()
	{
		return elefillrequiredfieldlbl;
	}
	
	@FindBy(xpath="//*[contains(text(),'Issue found')]")
	private WebElement eleissuefoundlbl;
	public WebElement geteleissuefoundlbl()
	{
		return eleissuefoundlbl;
	}
	
	
	@FindBy(xpath="//span[text()='< Work Order']")
	private WebElement eleBacktoWorkOrderlnk;
	public WebElement geteleBacktoWorkOrderlnk()
	{
		return eleBacktoWorkOrderlnk;
	}
	
	
	
	@FindBy(xpath="//strong[text()='Checklist Report']")
	private WebElement eleChecklistReporttxt;
	public WebElement geteleChecklistReporttxt()
	{
		return eleChecklistReporttxt;
	}
			
	private WebElement eleWONumberTxt;
	public WebElement getEleWONumberTxt(String sWorkOrder)
	{
		eleWONumberTxt=driver.findElement(By.xpath("//div[@class='workorder-details']//td[text()='"+sWorkOrder+"']"));
		return eleWONumberTxt;
	}
	
	
	@FindBy(xpath="//input[@value='Done']")
	private WebElement eleDoneLnk;
	public WebElement getEleDoneLnk()
	{
		return eleDoneLnk;
	}
	
	@FindBy(xpath="//span[text() = 'Actions']")
	private WebElement eleActionsLnk;
	public WebElement getEleActionsLnk()
	{
		return eleActionsLnk;
	}
	
	public void validateChecklistServiceReport(CommonsPO commonsPo,WorkOrderPO workOrderPo, String sPrintReportSearch, String sWorkOrderID ) throws InterruptedException
	{	
	
		workOrderPo.selectAction(commonsPo, sPrintReportSearch);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(geteleChecklistReporttxt().isDisplayed(), "Checklist Report is not displayed in OPDOC.");
		NXGReports.addStep("Checklist Report OPDOC is displayed successfully", LogAs.PASSED, null);		
		Assert.assertTrue(getEleWONumberTxt(sWorkOrderID).isDisplayed(),"Work Order no is not displayed in OPDOC report");
		NXGReports.addStep("Work order updated details for the work order "+sWorkOrderID, LogAs.PASSED, null);	
		getEleDoneLnk().click();
		commonsPo.tap(getEleDoneLnk());
		Thread.sleep(GenericLib.iLowSleep);
		((Rotatable)driver).rotate(ScreenOrientation.LANDSCAPE);
		((Rotatable)driver).rotate(ScreenOrientation.PORTRAIT);
		Thread.sleep(GenericLib.iLowSleep);
	
		//Navigation back to Work Order after Service Report
		Assert.assertTrue(getEleActionsLnk().isDisplayed(), "Work Order screen is displayed");
		NXGReports.addStep("Creation of Checklist OPDOC passed", LogAs.PASSED, null);		
	}
	
	//for shadow
	public  String getText(AppiumDriver driver, WebElement element){
	    return (String) ((JavascriptExecutor) driver).executeScript(
	        "return (arguments[0]).shadowRoot;", element);
	}
	
	
	//for shadow
	  @FindBy(xpath="//div[@class='x-unsized x-textareainput x-textinput x-input x-component x-has-width x-widthed x-disabled']/div[@class='x-input-body-el']")
	    private WebElement eledivtextarea;
	    public WebElement geteledivtextarea()
	    {
	    	return eledivtextarea;
	    }
	
	//for shadow
	public WebElement expandRootElement(WebElement element) {
		WebElement ele = (WebElement) ((JavascriptExecutor) driver)
.executeScript("return arguments[0].shadowRoot",element);
		return ele;
	}

	
	//for shadow
	@FindBy(css="textarea[id^='ext-element']")
	private WebElement elecsstextarea;
	public WebElement getelecsstextarea()
	{
		return elecsstextarea;
	}
	
	//for shadow
	@FindBy(css="div[contenteditable='false']")
	private WebElement elecsstextarea2;
	public WebElement getelecsstextarea2()
	{
		return elecsstextarea;
	}
	
	
	
	
	
}

