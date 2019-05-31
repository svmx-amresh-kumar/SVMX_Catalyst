/*
 *  @author lakshmibs
 */
package com.ge.fsa.tests.phone;

import org.testng.annotations.Test;
import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_SrctoTrgt_RS_10542 extends BaseLib {
	
	int iWhileCnt = 0;
	String sTestID = null;
	String sCaseWOID = null;
	String sCaseSahiFile = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sFieldServiceName = null;
	String sIBName1 = null;
	String sIBName2 = null;
	String sIBRecord = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sObjectAccID = null;
	String sSerialNumber = null;
	String sSqlQuery = null;
	String sAccountName = null;
	String sObjectProID = null;
	String sProductName = null;
	String sQualificationCriteriaValidation = null;
	//For SFM Process Sahi Script name
	String sScriptName="SCN_SrctoTrgt_RS_10542_prerequisite";
	Boolean bProcessCheckResult  = false;
	String sTestCaseID = null;
	
	private void preRequiste() throws Exception { 
		
		sTestCaseID = "SCN_SrctoTrgt_RS_10542";
		restServices.getAccessToken();
		sSerialNumber = commonUtility.generaterandomnumber("IB_10542_");
		
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"account\"}";
		sObjectAccID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Account+Where+id+=\'"+sObjectAccID+"\'";				
		sAccountName =restServices.restGetSoqlValue(sSqlQuery,"Name"); 

		// Create product
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"product\", \"IsActive\": \"true\"}";
		sObjectApi = "Product2?";
		sObjectProID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+Product2+Where+id+=\'"+sObjectProID+"\'";				
		sProductName  =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//Creation of dynamic IB1
		sJsonData = "{\"Name\": \""+sSerialNumber+"\", \"SVMXC__Serial_Lot_Number__c\": \""+sSerialNumber+"\", \"SVMXC__Product__c\": \""+sObjectProID+"\", \"SVMXC__Country__c\": \"United States\", \"SVMXC__City__c\": \"Liver Pool\"}";
		sObjectApi = "SVMXC__Installed_Product__c?";
		sIBRecord=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Installed_Product__c+Where+id+=\'"+sIBRecord+"\'";				
		sIBName1 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		System.out.println(sIBName1);

		//Creation of dynamic IB2
		sJsonData = "{\"SVMXC__Company__c\": \""+sObjectAccID+"\", \"Name\": \""+commonUtility.generaterandomnumber("IB_10542_")+"\", \"SVMXC__Serial_Lot_Number__c\": \""+commonUtility.generaterandomnumber("IB_10542_")+"\", \"SVMXC__Product__c\": \""+sObjectProID+"\", \"SVMXC__Country__c\": \"Italy\", \"SVMXC__City__c\": \"Vatican\"}";
		sObjectApi = "SVMXC__Installed_Product__c?";
		sIBRecord=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Installed_Product__c+Where+id+=\'"+sIBRecord+"\'";				
		sIBName2 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		System.out.println(sIBName2);
		
		
/*		genericLib.executeSahiScript("appium/SCN_SrctoTrgt_RS_10542_prerequisite.sah", sTestID);
		Assert.assertTrue(commonUtility.verifySahiExecution(), "Failed to execute Sahi script");
		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
*/		
		bProcessCheckResult =commonUtility.ProcessCheck(restServices, genericLib, sFieldServiceName, sScriptName, sTestCaseID);		

	}

	//@Test()
	@Test(enabled = true, retryAnalyzer=Retry.class)
	public void RS_10542Test() throws Exception {
		sTestID = "RS_10542";	
		sExploreSearch = GenericLib.readExcelData(GenericLib.sTestDataFile,sTestID, "ExploreSearch");
		sExploreChildSearchTxt = GenericLib.readExcelData(GenericLib.sTestDataFile, sTestID,"ExploreChildSearch");
		sFieldServiceName = GenericLib.readExcelData(GenericLib.sTestDataFile,sTestID, "ProcessName");
		preRequiste();
			
		//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		
		ph_MorePo.OptionalConfigSync(commonUtility,ph_CalendarPo,bProcessCheckResult);
		Thread.sleep(GenericLib.iMedSleep);
		
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep);
		
		//navigating to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt,
				sIBName1, sFieldServiceName);

		//Validation of not qualifying Work Order
		Thread.sleep(2000);
		sQualificationCriteriaValidation =ph_WorkOrderPo.geteleAccountnotNUll().getText().replaceAll("'","");
		Assert.assertTrue(sQualificationCriteriaValidation.equals("Account cant be NULL"), "Qualification Criteria validation failed");
		ExtentManager.logger.log(Status.PASS,"Qualification Criteria message Account can't be null displayed successfully");
		
		ph_WorkOrderPo.getEleBackButton().click();
		
		//Navigating to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, sExploreChildSearchTxt,
				sIBName2, sFieldServiceName);
		
		//Validation of auto update process
		Assert.assertTrue(ph_WorkOrderPo.getEleAccount().getText().equals(sAccountName), "Account is not displayed.");
		ExtentManager.logger.log(Status.PASS,"IB Account is displayed successfully");
		
		//Validation of auto update process
		Assert.assertTrue(ph_WorkOrderPo.getEleComponent().getText().equals(sIBName2), "Component is not displayed");
		ExtentManager.logger.log(Status.PASS,"Component is  displayed");
		
		/*		
		//Set the schedule Date for future date by 2 days
		if(BaseLib.sOSName.equals("ios") ){
			workOrderPo.getEleIBScheduledTxtFld().click();
			Thread.sleep(GenericLib.iMedSleep);
			commonUtility.setDatePicker(1, 2);
			commonUtility.getEleDonePickerWheelBtn().click();
			commonUtility.switchContext("Webview");}
		else{
			commonUtility.tap(workOrderPo.getEleIBScheduledTxtFld());
			Thread.sleep(GenericLib.iMedSleep);
			commonUtility.setDatePicker(1, 2);
			commonUtility.switchContext("native");
			commonUtility.getCalendarDone().click();
			commonUtility.switchContext("Webview");
			}
		
*/		
		//Save the case created by IB
		  	ph_WorkOrderPo.getEleSaveLnk().click();
		  	
			ph_MorePo.syncData(commonUtility);

		//driver.activateApp(GenericLib.sAppBundleID);
	
		//Validation of WorkOrder from IB
		sSqlQuery="SELECT+Name+FROM+SVMXC__Service_Order__c+WHERE+SVMXC__Company__r.Name+=\'"+sSerialNumber+"account"+"\'";
		Assert.assertTrue(restServices.restGetSoqlValue(sSqlQuery,"Name")!=null, "IB to WorkOrder is not created");
		ExtentManager.logger.log(Status.PASS,"IB to WorkOrder is successfully created.");
	}
}
