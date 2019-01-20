/*
 *  @author Vinod Tharavath
 */
package com.ge.fsa.tests;


import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.testng.annotations.Test;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.offset.PointOption;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SCN_Checklist_Attachment_RS_10584 extends BaseLib {
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
	String sSection1Name="Section One";
	String sSheetName =null;
	String sWORecordID = null;
	String sWOName = null;
	
	//Attachment questions
	
	String sAttachmentQuestion1 = null;
	public void prereq() throws Exception
	{	
		sSheetName ="RS_10584";
		System.out.println("SCN_RS10584_Checklist_Attachment");
		sTestCaseID = "SCN_ChecklistAttachment_RS-10584";
		sCaseWOID = "Data_SCN_ChecklistAttachment_RS_10584";

		// Reading from the Excel sheet
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ProcessName");
		sChecklistName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ChecklistName");
		sEditProcessName = GenericLib.getExcelData(sTestCaseID,sSheetName, "EditProcessName");
		
		// Rest to Create Workorder -Standard Work Order - Satisfies Qualification Criteria and Checklist Entry Criteria
		
	/*	sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"High\"}");
		System.out.println(sWORecordID);
		sWOName= restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWORecordID + "\'", "Name");
		System.out.println("WO no =" + sWOName);*/				
		sWOName = "WO-00004603";
	
	}
	
	//@SuppressWarnings("unchecked")
	@Test(retryAnalyzer=Retry.class)
	public void RS_10584() throws Exception {
	
		
		
		// Pre Login to app
		loginHomePo.login(commonsPo, exploreSearchPo);
		prereq();
		System.out.println("NOW");
		//toolsPo.configSyncCheckVT(commonsPo);
		//workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
		//toolsPo.configSyncCheckVT(commonsPo);
		
//		toolsPo.configSync(commonsPo);
	//	workOrderPo.navigatetoWO(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearchTxt, sWOName);
	//	toolsPo.configSync(commonsPo);

		
		Thread.sleep(15000);
		prereq();
		// Data Sync for WO's created
		//toolsPo.syncData(commonsPo);
		// Thread.sleep(GenericLib.iMedSleep);
		// toolsPo.configSync(commonsPo);

		// Navigation to WO


		// Navigate to Field Service process
		workOrderPo.selectAction(commonsPo, sFieldServiceName);

		// Navigating to the checklist
		commonsPo.tap(checklistPo.geteleChecklistName(sChecklistName));
		Thread.sleep(GenericLib.iLowSleep);
		try {
			//commonsPo.tap(checklistPo.geteleChecklistAttach(sAttachmentQuestion1));
			//checklistPo.geteleChecklistAttach(sAttachmentQuestion1).click();
			//commonsPo.switchContext("Native");
			System.out.println("Trying to click Attach");
			commonsPo.switchContext("Native");
			driver.findElementByAccessibilityId("Attach").click();
			Thread.sleep(10000);
			driver.findElementByAccessibilityId("Choose from Library").click();
			Thread.sleep(10000);
			//driver.findElementBy
			//driver.findElementByAccessibilityId("Choose from Library").click();
			
			//commonsPo.tap(driver.findElement(By.xpath("//*[contains(@label,'Photo, Portrait, November 07, 15:14')]")));

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(MobileBy.AccessibilityId("PhotosGridView")));
			el.click();
			System.out.println("Clicked on Momemts");
			List<WebElement> photos = driver.findElements(MobileBy.className("XCUIElementTypeImage"));
			int numPhotos = photos.size();
			photos = driver.findElements(MobileBy.className("XCUIElementTypeImage"));
			System.out.println("There were " + numPhotos + " photos before, and now there are " +
			photos.size() + "!");
			commonsPo.switchContext("Native");
			WebElement elem = null;
			List<WebElement> mel = (List<WebElement>) driver.findElementByAccessibilityId("PhotosGridView").findElements(By.xpath("//*[contains(@label,'Photo')]"));
			for(int i =0;i<mel.size();i++) {
			try {
		    elem =  mel.get(i);
		    System.out.println("  picpic = "+i+"----"+elem.getText()+" remotewebele = "+((RemoteWebElement) elem).getId());

				WebElement pgv = driver.findElement(By.xpath("//*[contains(@label,'Moments')]"));
				Point pointPhotoGrid = pgv.getLocation();
				int pgx = pointPhotoGrid.getX() + 2;
				int pgy = pointPhotoGrid.getY() + 5;
				System.out.println("Moments  pgv = "+i+"----"+pointPhotoGrid);
			}catch(Exception e) {
				System.out.println("In catch ##### "+e);

			}


		}
		//	elem =  mel.get(i);	
		driver.findElement(By.xpath("//*[contains(@label,'"+elem.getText()+"')]")).click();

			//driver.findElement(By.xpath("//*[contains(@label,'Photo, Portrait, November 07, 15:14')]")).click();
			System.out.println("finished Clicking");
		//	driver.findElement(By.xpath("(//span[contains(text(),'Choose from Library')])[3]")).click();;
			try {
				commonsPo.tap(driver.findElement(By.xpath("//*[contains(@label,'Photo, Portrait, November 07, 15:14')]")));
				}
				catch(Exception e) {
				//	commonsPo.tap(driver.findElement(By.xpath("//*[contains(@label,'Photo')]")));
				
				}
				////XCUIElementTypePickerWheel[@type='XCUIElementTypePickerWheel']
				//List<IOSElement> picPic = (List<IOSElement>) driver.findElements(By.xpath("//XCUIElementTypeOther[@type='XCUIElementTypeOther']"));

					//commonsPo.switchContext("Webview");

					//List<IOSElement> picPic1 = (List<IOSElement>) driver.findElements(By.xpath("//XCUIElementTypeApplication[@name=\"ServiceMax\"]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[2]/XCUIElementTypeOther[3]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther[2]"));
					//List<IOSElement> picPic1 = (List<IOSElement>) driver.findElements(By.xpath("//*[@type='XCUIElementTypeOther'][contains(@label,'Photo')]"));

					
			Thread.sleep(10000);
		} catch (Exception e) {
			//checklistPo.geteleChecklistAttach(sAttachmentQuestion1).click();
			// TODO: handle exception
		}
			//------------------SERVER SIDE VALIDATIONS

}
}