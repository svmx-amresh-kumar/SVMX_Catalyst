/*
 *  @author Vinod Tharavath
 *  
 *  
 */
package com.ge.fsa.tests;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;
import com.ge.fsa.pageobjects.ChecklistPO;

import io.appium.java_client.MobileBy;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SCN_Checklist_Attachment_RS_10581 extends BaseLib {
	String sTestCaseID = null;
	String sCaseWOID = null;
	String sExploreSearch = null;
	String sChecklistName = null;
	String sFieldServiceName = null;
	String sWorkOrderID = null;
	String sWOJsonData = null;
	String sExploreChildSearchTxt = null;
	String sOrderStatusVal = null;
	String sEditProcessName = null;
	String sSheetName =null;
	String sWORecordID = null;
	String sWOName = null;
	
	//Attachment questions
	
	String sAttachText ="AttachmentChecklistupload";

	String sAttachmentQuestion1 = null;
	public void prereq() throws Exception
	{	
		//SanityPreReq sp = new SanityPreReq();
		//sp.SanityPreReq();
		sSheetName ="RS_10584";
		System.out.println("SCN_RS10581_Checklist_Attachment");
		sTestCaseID = "SCN_ChecklistAttachment_RS-10584";
		sCaseWOID = "Data_SCN_ChecklistAttachment_RS_10584";

		// Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID,sSheetName, "EditProcessName");
		
		// Rest to Create Workorder -Standard Work Order - Satisfies Qualification Criteria and Checklist Entry Criteria
		
		sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName= restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);			
		//sWOName = "WO-00004603";
	
	}
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10581() throws Exception {
		// Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		prereq();
		
		// Data Sync for WO's created
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);

		//Navigation to WO
	    workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);							
		// Navigate to Field Service process
		workOrderPo.selectAction(commonsPo, sFieldServiceName);
		// Navigating to the checklist
		commonsPo.tap(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(GenericLib.iLowSleep);
		assertFalse(commonsPo.isDisplayedCust(checklistPo.eleAttachNew()));
		ExtentManager.logger.log(Status.PASS,"Attach New link is not displayed");
		checklistAttach();
		assertEquals(checklistPo.eleAttachNew().isDisplayed(),true);
		ExtentManager.logger.log(Status.PASS,"Attachment New link is displayed post attaching image");
		//commonsPo.isDisplayedCust(wElement)
		commonsPo.tap(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1"));
		checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").sendKeys("AttachmentChecklistupload");
		commonsPo.tap(checklistPo.geteleNext());
		commonsPo.tap(checklistPo.eleChecklistSubmit());
		commonsPo.tap(checklistPo.geteleChecklistPopupSubmit());
		ExtentManager.logger.log(Status.PASS,"Checklist is submitted sucessfully for Work Order"+sWOName);

		//Navigating back to work Orders
		commonsPo.tap(checklistPo.geteleBacktoWorkOrderlnk());
		workOrderPo.selectAction(commonsPo, sFieldServiceName);
		System.out.println("Tapped on default title for checklist");
		// Navigating to the checklist
		//commonsPo.tap(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(GenericLib.iLowSleep);

		commonsPo.tap(checklistPo.geteleShowCompletedChecklist(),15,18);
		commonsPo.tap(checklistPo.geteleCompletedChecklistName(sChecklistName));
		
		Assert.assertEquals(checklistPo.geteleChecklistAnswerInput("AttachmentQuestion1").getAttribute("value"), sAttachText, "Attachment Text is displayed");
		ExtentManager.logger.log(Status.PASS,"Attachment text is visible in completed Checklist:"+sChecklistName);
		Thread.sleep(GenericLib.iHighSleep);
		commonsPo.tap(checklistPo.eleChecklistImage(),20,20);
		System.out.println("After attachment image");
		commonsPo.switchContext("Native");
		WebElement eleDoneButton = driver.findElement(By.xpath("//*[contains(@label,'Done')]"));
		eleDoneButton.click();
		ExtentManager.logger.log(Status.PASS,"done button was clicked below the image");
		commonsPo.switchContext("WebView");
		
		//Navigating back to WorkOrder Screen as Tools button will not be visible from checklists
		checklistPo.navigateBacktoWorkOrder(commonsPo);
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.i30SecSleep);
	  	Thread.sleep(GenericLib.i30SecSleep);

		//------------------SERVER SIDE VALIDATIONS
		System.out.println("Validating if  attachment is syned to server.");
	  	Thread.sleep(GenericLib.i30SecSleep);
	  	Thread.sleep(GenericLib.i30SecSleep);
	  	Thread.sleep(GenericLib.iMedSleep);
	  	String sSoqlchecklistid ="SELECT SVMXC__What_Id__c,ID FROM SVMXC__Checklist__c where SVMXC__Work_Order__c in (select id from SVMXC__Service_Order__c where name =\'"+sWOName+"\')";
	  	String schecklistid = restServices.restGetSoqlValue(sSoqlchecklistid, "Id");	
	  	String sSoqlAttachment = "SELECT Id FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid+"\')"; 
	  	//String sSoqlqueryAttachment = "Select+Id+from+Attachment+where+ParentId+In(Select+Id+from+SVMXC__Service_Order__c+Where+Name+=\'"+sWOName+"\')";
		restServices.getAccessToken();
		String sAttachmentIDAfter = restServices.restGetSoqlValue(sSoqlAttachment, "Id");	
		assertNotNull(sAttachmentIDAfter); 
		ExtentManager.logger.log(Status.PASS,"attachment is synced to Server");
	  	String sSoqlAttachmentName = "SELECT Name FROM Attachment where ParentId in(select Id from SVMXC__Checklist__c where id =\'"+schecklistid+"\')"; 
	  	String sAttachmentNameAfter = restServices.restGetSoqlValue(sSoqlAttachmentName, "Name");
		ExtentManager.logger.log(Status.INFO,"Attachment uplaoded is"+sAttachmentNameAfter);

}
	
	public void checklistAttach() throws Exception
	{
		System.out.println("Trying to click Attach");
		commonsPo.switchContext("Native");
		driver.findElementByAccessibilityId("Attach").click();
		Thread.sleep(10000);
		driver.findElementByAccessibilityId("Choose from Library").click();
		Thread.sleep(10000);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("PhotosGridView")));
		el.click();
		List<WebElement> photos = driver.findElements(MobileBy.className("XCUIElementTypeImage"));
		int numPhotos = photos.size();
		commonsPo.switchContext("Native");
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
			commonsPo.switchContext("Webview");
			Thread.sleep(GenericLib.i30SecSleep);
	
	
	}
	
}