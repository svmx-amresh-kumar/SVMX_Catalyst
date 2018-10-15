/*
*@author vinaya
 *  The link to the JIRA for the Scenario = "https://servicemax.atlassian.net/browse/RS-10565"
 */
package com.ge.fsa.tests;

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

public class SCN_Recent_Items_RS_10565 extends BaseLib {

	int iWhileCnt = 0;
	String sTestIBID = null;
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

	@Test(enabled = true)
	public void Recent_Items_RS_10565() throws Exception {
		sSheetName ="RS_10565";
		sDeviceDate = driver.getDeviceTime().split(" ");
		
		String sProformainVoice = commonsPo.generaterandomnumber("AUTO");
		String sTestIB="RS_10565_Recent_Items";
		sTestIBID = sProformainVoice;
		
		
		//read from file
		sExploreSearch = GenericLib.getExcelData(sTestIB,sSheetName, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.getExcelData(sTestIB,sSheetName, "ExploreChildSearch");
		sFieldServiceName = GenericLib.getExcelData(sTestIB,sSheetName, "ViewProcessNameCustom");
		String sFieldServiceName2 = GenericLib.getExcelData(sTestIB,sSheetName, "CreateNewCustomrecord");
		//String WOname1=GenericLib.getExcelData(sTestIB, "WorkOrder");
		
		String sRandomNumber = commonsPo.generaterandomnumber("");
	    sProformainVoice = sRandomNumber;
		
		//Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
			
			//config sync
			toolsPo.configSync(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
			toolsPo.Resetapp(commonsPo,exploreSearchPo);
			//datasync
			toolsPo.syncData(commonsPo);
			Thread.sleep(GenericLib.iMedSleep);
	
			//crete a wo
			commonsPo.tap(createNewPO.getEleCreateNew());
			commonsPo.tap(createNewPO.getEleCreateNewWorkOrder());
			commonsPo.pickerWheel(createNewPO.getEleClickPriorityPicklist(), "High");
			commonsPo.pickerWheel(createNewPO.getEleClickBillingTypePicklist(), "Loan");
			createNewPO.getEleproformainvoicevalue().click();
			commonsPo.tap(createNewPO.getEleproformainvoicevalue());
			createNewPO.getEleproformainvoicetextarea().sendKeys(sProformainVoice);
			commonsPo.tap(createNewPO.getEleupdatethetextfield());
			Thread.sleep(1000);
			commonsPo.tap(createNewPO.getEleSaveWorkOrdert());

			
			toolsPo.syncData(commonsPo);
			
			// Collecting the Work Order number from the Server.
			String sSoqlQuery = "SELECT+Name+from+SVMXC__Service_Order__c+Where+SVMXC__Proforma_Invoice__c+=\'"+sProformainVoice+"\'";
			restServices.getAccessToken();
			String sworkOrderName = restServices.restGetSoqlValue(sSoqlQuery,"Name");	
			System.out.println(sworkOrderName);
			
		
			//recenItemsPO.clickonWorkOrder(commonsPo, sworkOrderName);
			commonsPo.tap(recenItemsPO.getEleClickRecentItems());
			Thread.sleep(1000);
			String fetchedWOfromrecents =recenItemsPO.getEleworkorderrecentused().getText();
			System.out.println(fetchedWOfromrecents);
			Assert.assertTrue(fetchedWOfromrecents.equals(sworkOrderName), "workOrderName value  is not displayed");
			
			
			//open case
			workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, "Cases", "00001003", null);//case to be changed to global create case        
			Thread.sleep(2000);
			commonsPo.tap(recenItemsPO.getEleClickRecentItems());
			Thread.sleep(1000);
			commonsPo.tap(recenItemsPO.gettaponobject("Case ("));
			String fetchedcasefromrecents =recenItemsPO.geteleChecklistName("Case (").getText();
			System.out.println(fetchedcasefromrecents);
			Thread.sleep(5000);
			//create new custom record
			commonsPo.tap(createNewPO.getEleCreateNew());
			commonsPo.tap(createNewPO.getEleCreateNew());
			commonsPo.tap(createNewPO.getEleCreateNewcustomrecord());
			(workOrderPo.geteleAuto_TextBox_c()).sendKeys(sProformainVoice);	
			commonsPo.tap(createNewPO.getEleSaveWorkOrdert());
			Thread.sleep(3000);
			
			toolsPo.syncData(commonsPo);
			
			commonsPo.tap(recenItemsPO.getEleClickRecentItems());
			commonsPo.tap(recenItemsPO.gettaponobject("Auto_Custom_Object2 ("));
			String fetchedCustom_Objectfromrecents =recenItemsPO.geteleChecklistName("Auto_Custom_Object2 (").getText();
			System.out.println(fetchedCustom_Objectfromrecents);
			
			//a370t000002JrlJ

			String sSoqlQuery1 = "SELECT+Name+from+Auto_Custom_Object2__c+Where+Auto_TextBox_c__c+=\'"+sProformainVoice+"\'";
			restServices.getAccessToken();
			String Custom_ObjectName = restServices.restGetSoqlValue(sSoqlQuery1,"Name");	
			System.out.println(Custom_ObjectName);
				Assert.assertTrue(fetchedCustom_Objectfromrecents.equals(Custom_ObjectName), "workOrderName value  is not displayed");
			
			
			toolsPo.Resetapp(commonsPo,exploreSearchPo);
			Thread.sleep(2000);
			
			commonsPo.tap(recenItemsPO.getEleClickRecentItems());
			recenItemsPO.getelecheckrecentitemisempty().isDisplayed();
			
			ExtentManager.logger.log(Status.PASS,"Recent Items validation is successful");
			
			
	}

}
