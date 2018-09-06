package com.ge.fsa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.ExtentManager;

import io.appium.java_client.AppiumDriver;

public class ChecklistPO{
	
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

	private WebElement eleStartNewLnk;
	public WebElement geteleStartNew()
	{
		return eleStartNewLnk;
	}
	
	
    @FindBy(xpath="//span[text()='Next']")
    private WebElement eleNextLnk;
    public WebElement geteleNext()
    {
    	return eleNextLnk;
    }
    
    @FindBy(xpath="//span[text()='Submit']")
    private WebElement eleSubmitLnk;
    public WebElement geteleSubmitLnk()
    {
    	return eleSubmitLnk;
    }
    
    @FindBy(xpath="//*[text()='OK']")
	private WebElement eleChecklistokPopUp;
	public WebElement geteleChecklistokPopUp()
	{
		return eleChecklistokPopUp;
	}
    
    private WebElement eleSectionNextBtn;
	public WebElement geteleSectionNextBtn(int iChecklistSectionNo)
	{
		
		eleSectionNextBtn = driver.findElement(By.xpath("(//span[text()='Next'])["+iChecklistSectionNo+"]"));
		return eleSectionNextBtn;
	}
    
    
  /* @FindBy(xpath= "//*[text()='Submit'][2]")
    private WebElement eleSubmitbutton;
    public WebElement eleSubmitbutton()
    {
    	return eleSubmitbutton;
    }*/
    
    
	@FindBy(xpath="//div[@class='x-component x-button x-button-no-icon x-button-button-tools-msgbox x-component-button-tools-msgbox x-button-button-highlight x-component-button-highlight svmx-msg-button-default x-haslabel x-layout-box-item x-layout-hbox-item x-flexed x-stretched']/span[text()='Submit']")
	private WebElement eleChecklistPopupSubmitBtn;
    public WebElement geteleChecklistPopupSubmit()
    {
    	return eleChecklistPopupSubmitBtn;
    }
    
    @FindBy(xpath="//span[text()='Show completed checklists']")
    private WebElement eleShowCompletedChecklistLnk;
    public WebElement geteleShowCompletedChecklist()
    {
    	return eleShowCompletedChecklistLnk;
    }
    
    private WebElement eleCompletedChecklistNameLnk;
	public WebElement geteleCompletedChecklistName(String checklistname)
	{
		
		eleCompletedChecklistNameLnk = driver.findElement(By.xpath("(//div[@class='checklist-lineup-list-item-name'][text()='"+checklistname+"'])[2]"));
		return eleCompletedChecklistNameLnk;
	}
    
	
	private WebElement eleChecklistAnswerText;
	public WebElement  geteleChecklistAnswerTextArea(String ChecklistTextQuestion)
	{
		return eleChecklistAnswerText = driver.findElement(By.xpath("//div[text()='"+ChecklistTextQuestion+"'][@class='x-innerhtml']/../..//textarea"));
	}
	
	
	private WebElement eleChecklistAnsPicklist;
	public WebElement  geteleChecklistAnsPicklist(String ChecklistTextQuestion)
	{
		return eleChecklistAnsPicklist = driver.findElement(By.xpath("//div[text()='"+ChecklistTextQuestion+"'][@class='x-innerhtml']/../..//input"));
	}
	
	
	private WebElement eleChecklistAnsNumber;
	public WebElement  geteleChecklistAnsNumber(String checklistNumberQuestion)
	{
		return eleChecklistAnsNumber = driver.findElement(By.xpath("//div[text()='"+checklistNumberQuestion+"'][@class='x-innerhtml']/../..//input"));
	}
	
	
	private WebElement eleChecklistAnsDate;
	public WebElement  geteleChecklistAnsDate(String checklistDateQuestion)
	{
		return eleChecklistAnsDate = driver.findElement(By.xpath("//div[text()='"+checklistDateQuestion+"'][@class='x-innerhtml']/../..//input"));
	}
	
	
	private WebElement eleChecklistAnsradio;
	public WebElement  geteleChecklistAnsradio(String checklistRadioQuestion)
	{
		
		
		return eleChecklistAnsradio = driver.findElement(By.xpath("//div[text()='"+checklistRadioQuestion+"'][@class='x-innerhtml']/../..//span"));
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
	
	@FindBy(xpath="//span[text()='< Back']")
	private WebElement eleBacklnk;
	public WebElement geteleBacklnk()
	{
		return eleBacklnk;
	}
	
	@FindBy(xpath="//span[text()='< Checklists']")
	private WebElement eleBacktoChecklistslnk;
	public WebElement geteleBacktoChecklistslnk()
	{
		return eleBacktoChecklistslnk;
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

		  eleWONumberTxt=driver.findElement(By.xpath("//div[@class='workorder-details']//td[2][contains(text(), '"+sWorkOrder+"')]"));
		//eleWONumberTxt=driver.findElement(By.xpath("//div[@class='workorder-details']//td[text()='"+sWorkOrder+"']"));
		return eleWONumberTxt;
	}
	
	 @FindBy(xpath="//input[@id='svmx_finalize']")
	//@FindBy(xpath="//input[@value='Done']")
	private WebElement eleopDoneLnk;
	public WebElement getEleopDoneLnk()
	{
		return eleopDoneLnk;
	}
	
	@FindBy(xpath="//span[text() = 'Actions']")
	private WebElement eleActionsLnk;
	public WebElement getEleActionsLnk()
	{
		return eleActionsLnk;
	}
	
	@FindBy(xpath="//div[contains(text(), 'Date Should not be Today')]")
	private WebElement eleChecklistDVRtxt;
	public WebElement geteleChecklistDVRtxt()
	{
		return eleChecklistDVRtxt;
	}
	
	@FindBy(xpath="//div[contains(text(), 'Number Cannot be 10')]")
	private WebElement eleChecklistDVRConfirmationtxt;
	public WebElement geteleChecklistDVRConfirmationtxt()
	{
		return eleChecklistDVRConfirmationtxt;
	}
	
	@FindBy(xpath="//div[contains(text(), 'Number cannot be greater than 100')]")
	private WebElement eleChecklistDVRNoGreaterthan100txt;
	public WebElement geteleChecklistDVRNoGreaterthan100txt()
	{
		return eleChecklistDVRNoGreaterthan100txt;
	}
	
	
	@FindBy(xpath="//div[@class='x-component x-button x-button-svmx-default x-component-svmx-default x-button-no-icon checklist-warning-submit x-layout-box-item x-layout-hbox-item x-stretched']//span[@class='x-button-label'][text()='Confirm']")
	private WebElement eleDVRConfirmBtn;
	public WebElement geteleDVRConfirmBtn()
	{
		return eleDVRConfirmBtn;
	}
	
	//getting the entire row in checklistopdoc checklist,completed,status EntireTable.
	@FindBy(xpath="//div//th[@class='theader'][contains(text(), 'Checklist')]/../../..//tbody")
	private WebElement eleChecklistOPDOCRow;
	public WebElement geteleChecklistOPDOCRow()
	{
		return eleChecklistOPDOCRow;
	}
	
	//getting the Checklist Answers in OPDOC
	@FindBy(xpath="//div[@class='part-details']//th//strong[text()='Question']/../../../../../../../..")
    private WebElement eleChecklistAnswerOPDOCtbl;
    public WebElement geteleChecklistAnswerOPDOCtbl()
    {
    	return eleChecklistAnswerOPDOCtbl;
    }
    
    private WebElement eleSectionNametxt;
    public WebElement  geteleChecklistSectionNametab(String sSectionName)
	{
		return eleSectionNametxt = driver.findElement(By.xpath("//span[@class='x-button-label'][text()='"+sSectionName+"']"));
	}
    
    
    
    @FindBy(xpath="//div[@class='x-unsized x-component x-button x-button-svmx-default x-component-svmx-default x-button-no-icon svmx-help-url x-layout-auto-item']")
	private WebElement eleChecklistHelpIcn;
	public WebElement geteleChecklistHelpIcn()
	{
		return eleChecklistHelpIcn;
	}
	
	public void validateChecklistServiceReport(CommonsPO commonsPo,WorkOrderPO workOrderPo, String sPrintReportSearch, String sWorkOrderID ) throws InterruptedException
	{	
	
		workOrderPo.selectAction(commonsPo, sPrintReportSearch);
		Thread.sleep(4000);
		Assert.assertTrue(geteleChecklistReporttxt().isDisplayed(), "Checklist Report is not displayed in OPDOC.");
		ExtentManager.logger.log(Status.PASS,"Checklist Report OPDOC is displayed successfully");
		Assert.assertTrue(getEleWONumberTxt(sWorkOrderID).isDisplayed(),"Work Order no is not displayed in OPDOC report");
		ExtentManager.logger.log(Status.PASS,"Work order updated details for the work order "+sWorkOrderID);
		System.out.println(sWorkOrderID);
		
	}	
	
	
	public void navigateBacktoWorkOrder(CommonsPO commonsPo) throws InterruptedException
	{
		
		
		try {
			commonsPo.tap(geteleBacktoChecklistslnk());
			commonsPo.tap(geteleBacktoWorkOrderlnk());
			
		} catch (Exception e) {
			commonsPo.tap(geteleBacklnk());
			commonsPo.tap(geteleBacktoChecklistslnk());
			commonsPo.tap(geteleBacktoWorkOrderlnk());
		}
		
		
		
		
		
	}
	
	
}

