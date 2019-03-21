/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10565"
 */
package com.ge.fsa.tests.tablet;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_Recent_Items_RS_10565 extends BaseLib {

	int iWhileCnt = 0;
	String sTestCaseIDID = null;
	String sObjectIBID =null ;
	
	
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectAccID = null;
	String sObjectProID=null;
	String sObjectWOID1=null;
	String sObjectWOID2=null;
	String sObjectWOID3=null;
	String sObjectApi = null;
	String sJsonData = null;
	String WOname1 = null;
	String WOname2 = null;
	String WOname3 = null;
	String sFieldServiceName = null;
	String sSqlQuery = null;
	String[] sDeviceDate = null;
	String[] sAppDate = null;
	WebElement productname=null;
	String sSheetName =null;
	
	@BeforeMethod
	public void initializeObject() throws IOException { 
		
	} 

	@Test(retryAnalyzer=Retry.class)
	public void Recent_Items_RS_10565() throws Exception {
		sSheetName ="RS_10565";
		sDeviceDate = driver.getDeviceTime().split(" ");
		
		String sProformainVoice = commonsUtility.generaterandomnumber("AUTO");
		String sTestCaseID="RS_10565_Recent_Items";
		
		//sahi
  		/*genericLib.executeSahiScript("appium/SCN_RecentItems_RS_10565.sah", "sTestCaseID");
  		if(commonsUtility.verifySahiExecution()) {
  			
  			System.out.println("PASSED");
  		}
  		else 
  		{
  			System.out.println("FAILED");
  			

  			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi verification failure");
  			assertEquals(0, 1);
  		}
  		lauchNewApp("true");
  		System.out.println("RS_10565");*/
		
		
		//read from file
		sExploreSearch = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestCaseID,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestCaseID,sSheetName, "ViewProcessNameCustom");
		String sFieldServiceName2 = GenericLib.getExcelData(sTestCaseID,sSheetName, "CreateNewCustomrecord");
		//String WOname1=GenericLib.getExcelData(sTestCaseID, "WorkOrder");
		
		String sRandomNumber = commonsUtility.generaterandomnumber("");
	    sProformainVoice = sRandomNumber;
		
	 
	    
		//Pre Login to app
			loginHomePo.login(commonsUtility, exploreSearchPo);
			
		
			Thread.sleep(GenericLib.iMedSleep);
			toolsPo.Resetapp(commonsUtility,exploreSearchPo);
			Thread.sleep(GenericLib.iMedSleep);
	
			//crete a wo
			commonsUtility.tap(createNewPO.getEleCreateNew());
			commonsUtility.tap(createNewPO.getEleCreateNewWorkOrder());
			commonsUtility.setPickerWheelValue(createNewPO.getEleClickPriorityPicklist(), "High");
			commonsUtility.setPickerWheelValue(createNewPO.getEleClickBillingTypePicklist(), "Loan");
			//createNewPO.getEleproformainvoicevalue().click();
			//commonsUtility.tap(createNewPO.getEleproformainvoicevalue());
			createNewPO.getEleproformainvoicetextarea().sendKeys(sProformainVoice);
		//	commonsUtility.tap(createNewPO.getEleupdatethetextfield());
			Thread.sleep(1000);
			commonsUtility.tap(createNewPO.getEleSaveWorkOrdert());
//create one case
			sJsonData = "{\"Origin\": \"phone\", \"Subject\": \"Recent_Item\", \"Priority\": \"High\", \"Description\": \"Description of Recent_item \",\"Status\": \"Escalated\"}";
			sObjectApi = "Case?";
			String sObjectID = restServices.restCreate(sObjectApi,sJsonData);
			sSqlQuery ="SELECT+CaseNumber+from+Case+Where+id+=\'"+sObjectID+"\'";				
			String sCaseID = restServices.restGetSoqlValue(sSqlQuery,"CaseNumber"); 
			
			
			
			toolsPo.syncData(commonsUtility);
			
			// Collecting the Work Order number from the Server.
			String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
			restServices.getAccessToken();
			String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
			System.out.println(sworkOrderName);
			
		
			//recenItemsPO.clickonWorkOrder(commonsUtility, sworkOrderName);
			commonsUtility.tap(recenItemsPO.getEleClickRecentItems());
			Thread.sleep(1000);
			String fetchedWOfromrecents =recenItemsPO.getEleworkorderrecentused().getText();
			System.out.println(fetchedWOfromrecents);
			Assert.assertTrue(fetchedWOfromrecents.equals(sworkOrderName), "workOrderName value  is not displayed");
			ExtentManager.logger.log(Status.PASS,"Workorder valaditation in recent item is successful");
			
			//open case
			workOrderPo.navigateToWOSFM(commonsUtility, exploreSearchPo, sExploreSearch, "Cases", sCaseID, null);//case to be changed to global create case        
			Thread.sleep(2000);
			commonsUtility.tap(recenItemsPO.getEleClickRecentItems());
			Thread.sleep(1000);
			//commonsUtility.tap(recenItemsPO.gettaponobject("Case ("));
			String fetchedcasefromrecents =recenItemsPO.geteleChecklistName("Case (").getText();
			System.out.println(fetchedcasefromrecents);
			Assert.assertTrue(sCaseID.equals(fetchedcasefromrecents), "case value  is not displayed");
			ExtentManager.logger.log(Status.PASS," case valaditation in recent item is successful");
			
			Thread.sleep(5000);
			//create new custom record
			commonsUtility.tap(createNewPO.getEleCreateNew());
			commonsUtility.tap(createNewPO.getEleCreateNew());
			commonsUtility.tap(createNewPO.getEleCreateNewcustomrecord());
			(workOrderPo.geteleAuto_TextBox_c()).sendKeys(sProformainVoice);	
			commonsUtility.tap(createNewPO.getEleSaveWorkOrdert());
			Thread.sleep(3000);
			
			toolsPo.syncData(commonsUtility);
			
			commonsUtility.tap(recenItemsPO.getEleClickRecentItems());
			//commonsUtility.tap(recenItemsPO.gettaponobject("Auto_Custom_Object2 ("));
			String fetchedCustom_Objectfromrecents =recenItemsPO.geteleChecklistName("Auto_Custom_Object2 (").getText();
			System.out.println(fetchedCustom_Objectfromrecents);
			
			String sSoqlQuery1 = "SELECT+Name+from+Auto_Custom_Object2__c+Where+Auto_TextBox_c__c+=\'"+sProformainVoice+"\'";
			restServices.getAccessToken();
			String Custom_ObjectName = restServices.restGetSoqlValue(sSoqlQuery1,"Name");	
			System.out.println(Custom_ObjectName);
				Assert.assertTrue(fetchedCustom_Objectfromrecents.equals(Custom_ObjectName), "Custom object  value  is not displayed");
				ExtentManager.logger.log(Status.PASS," Custom object valaditation in recent item is successful");
			
			toolsPo.Resetapp(commonsUtility,exploreSearchPo);
			Thread.sleep(2000);
			
			commonsUtility.tap(recenItemsPO.getEleClickRecentItems());
			recenItemsPO.getelecheckrecentitemisempty().isDisplayed();
			
			ExtentManager.logger.log(Status.PASS,"Recent Items validation is successful");
			
			
	}

}