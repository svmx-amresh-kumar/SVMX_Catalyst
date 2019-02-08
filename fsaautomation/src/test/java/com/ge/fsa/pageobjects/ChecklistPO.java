package com.ge.fsa.pageobjects;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
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
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.touch.offset.PointOption;

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

	
	 private WebElement eleStartNewLnk;
		public WebElement getEleStartNewLnk(String sCheckListName )
		{
			
			eleStartNewLnk = driver.findElement(By.xpath("//div[@class='checklist-lineup-list-item-description'][text()='"+sCheckListName+"']/following-sibling::a[@class='checklist-lineup-list-item-actionlable'][text()='Start New']"));
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
	
	@FindBy(xpath="//span[@class='x-button-label'][text()='OK']")
	private WebElement eleChecklistOK;
	public WebElement geteleChecklistOK()
	{
		return eleChecklistOK;
	}
	
	 @FindBy(xpath="//span[text()='Save']")
	    private WebElement eleSavePopUp;
	    public WebElement geteleSavePopUp()
	    {
	    	return eleSavePopUp;
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
	
	private WebElement eleChecklistInput;
	public WebElement  geteleChecklistAnswerInput(String ChecklistInput)
	{
		return eleChecklistInput = driver.findElement(By.xpath("//div[text()='"+ChecklistInput+"'][@class='x-innerhtml']/../..//input"));
	}
	
	@FindBy(xpath="//div[@class='x-innerhtml'][text()='Attach New']")
	private WebElement geteleAttachNew;
	public WebElement eleAttachNew()
	{
		return geteleAttachNew;
	}
	 @FindBy(xpath="//div[@class='x-component x-img x-sized x-widthed x-heighted x-floating ']")
	private WebElement  geteleChecklistImage;
	public WebElement eleChecklistImage()
	{
		return geteleChecklistImage;
	}

	
	 @FindBy(xpath="//div[@class='x-component x-video x-sized x-widthed x-heighted x-floating sfm-checklist-attachment-thumb']")
		private WebElement  geteleChecklistVideo;
		public WebElement eleChecklistVideo()
		{
			return geteleChecklistVideo;
		}

	
	private WebElement eleChecklistAnsPicklist;
	public WebElement  geteleChecklistAnsPicklist(String ChecklistTextQuestion)
	{
		return eleChecklistAnsPicklist = driver.findElement(By.xpath("//div[text()='"+ChecklistTextQuestion+"'][@class='x-innerhtml']/../..//input"));
	}
	
	//Can be used for multipicklist as well
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
	
	
	//$x("//span[@class='x-label-text-el'][text()='CheckBoxOne']/../..//div[@class='x-unsized x-checkboxinput x-input x-component x-font-icon']//input")
	private WebElement eleChecklistCheckboxValue;
	public WebElement  geteleChecklistCheckboxValue(String checklistCheckbox)
	{
		return eleChecklistCheckboxValue = driver.findElement(By.xpath("//span[@class='x-label-text-el'][text()='"+checklistCheckbox+"']/../..//div[@class='x-unsized x-checkboxinput x-input x-component x-font-icon']//input"));
	}
	
	private WebElement eleChecklistAttach;
	public WebElement geteleChecklistAttach(String checklistq)
	{
		return eleChecklistAttach = driver.findElement(By.xpath("//div[text()='"+checklistq+"']/../..//div[@class='x-component x-label x-stretched x-widthed sfm-checklist-attach-label x-layout-box-item x-layout-hbox-item x-flexed']/div"));
	}
	
	//"//span[@class='x-label-text-el'][text()='RadioOne']/../..//input")
	private WebElement eleChecklistRadioButtonValue;
	public WebElement  geteleChecklistRadioButtonValue(String checklistRadiobutton)
	{
		return eleChecklistRadioButtonValue = driver.findElement(By.xpath("//span[@class='x-label-text-el'][text()='"+checklistRadiobutton+"']/../..//input"));
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
	
	
	//@FindBy(xpath="//span[text()='< Work Order']")
	@FindBy(xpath="//div[@class='x-component x-button x-button-svmx-toolbar-btn x-component-svmx-toolbar-btn x-button-svmx-cancel-btn x-component-svmx-cancel-btn x-button-no-icon x-layout-box-item x-layout-hbox-item x-stretched']/span[@class='x-button-label'][contains(text(),'Work Order')]")
	
	private WebElement eleBacktoWorkOrderlnk;
	public WebElement geteleBacktoWorkOrderlnk()
	{
		return eleBacktoWorkOrderlnk;
	}
	
	//@FindBy(xpath="//span[contains(text(),'Back')]")
	
	@FindBy(xpath="//div[@class='x-component x-button x-button-svmx-toolbar-btn x-component-svmx-toolbar-btn x-button-svmx-cancel-btn x-component-svmx-cancel-btn x-button-no-icon x-layout-box-item x-layout-hbox-item x-stretched']/span[@class='x-button-label']")
	private WebElement eleBacklnk;
	public WebElement geteleBacklnk()
	{
		return eleBacklnk;
	}
	//@FindBy(xpath="//span[text()='<Checklists')]")
	
	@FindBy(xpath="//div[@class='x-component x-button x-button-svmx-toolbar-btn x-component-svmx-toolbar-btn x-button-svmx-cancel-btn x-component-svmx-cancel-btn x-button-no-icon x-layout-box-item x-layout-hbox-item x-stretched']/span[@class='x-button-label'][contains(text(),'Checklists')]")
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
	
	@FindBy(xpath ="//*[contains(@label,'Done')]")
	private WebElement eleDoneattachment;
	public WebElement geteleDoneAttachment()
	{
		return eleDoneattachment;
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
	
	@FindBy(xpath="//div[contains(text(), 'Advanced DVR value should be more than 200')]")
	private WebElement eleChecklistAdvanceDVR;
	public WebElement geteleChecklistAdvanceDVR()
	{
		return eleChecklistAdvanceDVR;
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
    
    //Checklist badge error 
    @FindBy(xpath="//span[@class='sfm-checklist-validation-error-badge']")
    private WebElement eleChecklistErrorBadge;
    public WebElement geteleChecklistErrorBadge()
    {
    	return eleChecklistErrorBadge;
    }
    
   
    
    private WebElement eleSectionNametxt;
    public WebElement  geteleChecklistSectionNametab(String sSectionName)
	{
		return eleSectionNametxt = driver.findElement(By.xpath("//span[@class='x-button-label'][text()='"+sSectionName+"']"));
	}
    
    
    private WebElement eleDoneAccessibilityid;
    public WebElement geteleDoneAccessibilityid()
    {
    	return eleDoneAccessibilityid = driver.findElementByAccessibilityId("Done");
    }
    
    
    
    /*
    @FindBy(xpath="//div[@class='x-unsized x-component x-button x-button-svmx-default x-component-svmx-default x-button-no-icon svmx-help-url x-layout-auto-item']")
	private WebElement eleChecklistHelpIcn;
	public WebElement geteleChecklistHelpIcn()
	{
		return eleChecklistHelpIcn;
	}*/
	
	
	private WebElement eleChecklistHelpIcn;
    public WebElement  geteleChecklistHelpIcn(String sQuestionName)
	{
		return eleChecklistHelpIcn = driver.findElement(By.xpath("//div[text()='"+sQuestionName+"']/../../..//../../../..//div[@class='x-unsized x-component x-button x-button-svmx-default x-component-svmx-default x-button-no-icon svmx-help-url x-layout-auto-item']"));
		
	}
    
    
 
	
	private WebElement eleChecklistStatuslbl;
    public WebElement  getEleChecklistStatusLbl(String sChecklistNames)
	{
		return eleChecklistStatuslbl = driver.findElement(By.xpath("//div[@class='checklist-lineup-list-item-name'][text()='"+sChecklistNames+"']/following-sibling::div[@class='checklist-lineup-list-item-statuslabel']"));
		
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
			System.out.println("Try block for back");
			commonsPo.tap(geteleBacklnk());
            commonsPo.tap(geteleBacktoChecklistslnk());
            commonsPo.tap(geteleBacktoWorkOrderlnk());
            
           
      } catch (Exception e) {
			System.out.println("Catch block for back");
            commonsPo.tap(geteleBacktoChecklistslnk());
            commonsPo.tap(geteleBacktoWorkOrderlnk());
      }

	
	
		
		/*Point point = geteleBacklnk().getLocation();
		try {
			System.out.println("***** USing Single Tap in TRY BLock*******");
		commonsPo.singleTap(geteleBacklnk());
		commonsPo.singleTap(geteleBacktoChecklistslnk());
		
		
		commonsPo.singleTap(geteleBacktoWorkOrderlnk());
		System.out.println("***** USing Single Tap in TRY BLock*******");
		
		}*/
		
		
		
		
	}
	
	
	public void Allowlocationbutton() throws InterruptedException
	{
		Thread.sleep(GenericLib.iHighSleep);
		try{driver.findElement(By.xpath("//XCUIElementTypeAlert//XCUIElementTypeButton[@name='Allow']")).click();}catch(Exception e) {}
		Thread.sleep(GenericLib.iLowSleep);
		
	}
	
	
	public void AllowCamerabutton(CommonsPO commonsPo) throws Exception
	{
		commonsPo.switchContext("Native");
		Thread.sleep(GenericLib.iHighSleep);
try {
	driver.findElementByAccessibilityId("OK").click();

} catch (Exception e) {
	System.out.println("Could not Find OK Popup for camera");
	commonsPo.switchContext("WebView");

}


	}
	
	
	public void checklistAttach(CommonsPO commonsPo, String AttachmentAction, String checklistq) throws Exception
	{
		System.out.println("Trying to click Attach");
		geteleChecklistAttach(checklistq);
		commonsPo.switchContext("Native");
		driver.findElementByAccessibilityId("Attach").click();
		Thread.sleep(10000);
		driver.findElementByAccessibilityId(AttachmentAction).click();
		AllowCamerabutton(commonsPo);

		
		if (AttachmentAction=="Choose from Library"||AttachmentAction=="choose from library") {
		
			Thread.sleep(10000);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			AllowCamerabutton(commonsPo);
			commonsPo.switchContext("Native");
			WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("PhotosGridView")));
			el.click();
			List<WebElement> photos = driver.findElements(MobileBy.className("XCUIElementTypeImage"));
			int numPhotos = photos.size();
			//commonsPo.switchContext("Native");
			WebElement elem = null;
			List<WebElement> lsPhotoGrid = (List<WebElement>) driver.findElementByAccessibilityId("PhotosGridView").findElements(By.xpath("//*[contains(@label,'Photo')]"));
			int count = lsPhotoGrid.size();
				if (count==0 || count ==1 ) {
					 ExtentManager.logger.log(Status.FAIL,"PLease add photos to Device and reexecute tests");
				} else {
					count = lsPhotoGrid.size()-1;
					elem =lsPhotoGrid.get(count);
					driver.findElement(By.xpath("//*[contains(@label,'"+elem.getText()+"')]")).click();
					System.out.println("finished Clicking");
					Thread.sleep(10000);	
					
				}
		} 
		if(AttachmentAction=="Take Photo"||AttachmentAction=="take photo") {
			Thread.sleep(GenericLib.iMedSleep);
			AllowCamerabutton(commonsPo);
			commonsPo.switchContext("Native");
			driver.findElementByAccessibilityId("Take Picture").click();
			Thread.sleep(3000);
			driver.findElementByAccessibilityId("Use Photo").click();
		}
			
		if(AttachmentAction=="Take Video"||AttachmentAction=="take video") {
			Thread.sleep(GenericLib.iMedSleep);
			AllowCamerabutton(commonsPo);;
			//com.apple.camera
			commonsPo.switchContext("Native");
			driver.findElementByAccessibilityId("Record Video").click();
			Thread.sleep(5000);
			System.out.println("Recording 5 secs video");
			driver.findElementByAccessibilityId("Stop Recording Video").click();
			driver.findElementByAccessibilityId("Use Video").click();
		}
		
		
			commonsPo.switchContext("Webview");
			Thread.sleep(GenericLib.i30SecSleep);
	
	
	}
	
}

