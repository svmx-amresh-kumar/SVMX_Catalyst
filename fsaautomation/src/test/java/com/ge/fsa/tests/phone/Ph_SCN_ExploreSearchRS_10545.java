package com.ge.fsa.tests.phone;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_ExploreSearchRS_10545 extends BaseLib{


	int iWhileCnt = 0;
	String sTestID = null;
	String sExploreSearch = null;
	String sSerialNumber = null;//"21022019140256";//null;
	String sObjectApi = null;
	String sJsonData = null;
	String sObjectID = null;
	String sSqlQuery = null;
	String sAccountNameA = null;//sSerialNumber+"AccA";
	String sAccountNameB = null;//sSerialNumber+"AccB";
	String sAccountNameC = null;//sSerialNumber+"AccC";	
	String sLocationA = null;//sSerialNumber+"LocA";
	String sLocationB = null;//sSerialNumber+"LocB";
	String sLocationC = null;//sSerialNumber+"LocC";
	String sLocationD = null;//sSerialNumber+"LocD";
	String sLocationE = null;//sSerialNumber+"LocE";
	String sLocationId = null;
	String sWOName1 = null;//"WO-00010448";
	String sWOName2 = null;//"WO-00010449";
	String sObjectIDWO2 = null;
	String sWOName3 = null;//"WO-00010450";
	String sWOName4 = null;//"WO-00010451";
	String sWOName5 = null;//"WO-00010451";
	
	String sAutoEditWO = "AUTO_EDIT_WORKORDER";
	String[] sDate=null;
	String sCompletedDateTxt=null;
	String sActualDateTxt=null;
	String sTomDateTxt = null;
	String sTodayDateTxt = null;
	String sDateAfterTomTxt = null;
	int iDay=0;
	int iMonth=0;
	
	private void preRequiste() throws Exception  
	{
		restServices.getAccessToken();
		sSerialNumber = commonUtility.generaterandomnumber("");
		
		//create AccountA with Hyderabad
		sObjectApi = "Account?";
		sJsonData =  "{\"Name\": \""+sSerialNumber+""+"AccA\"}";
		sAccountNameA=restServices.restCreate(sObjectApi,sJsonData);
		Thread.sleep(GenericLib.iMedSleep);
		
		sObjectApi = "Account";
		sJsonData="{\"BillingCity\":\"Hyderabad\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sAccountNameA );

		//create AccountB with Bangalore
		sObjectApi = "Account?";
		sJsonData =  "{\"Name\": \""+sSerialNumber+""+"AccB\"}";
		sAccountNameB=restServices.restCreate(sObjectApi,sJsonData);
		Thread.sleep(GenericLib.iMedSleep);
		sObjectApi = "Account";
		sJsonData="{\"BillingCity\":\""+"Bangalore"+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sAccountNameB );

		System.out.println("**************"+sAccountNameB);
		//create AccountC with Manchester
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"AccC\"}";
		sAccountNameC=restServices.restCreate(sObjectApi,sJsonData);
		sObjectApi = "Account";
		sJsonData="{\"BillingCity\":\"Manchester\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sAccountNameC );

		//Create Location
		sLocationA = sSerialNumber+"LocA";
		sObjectApi = "SVMXC__Site__c?";
		sJsonData = "{\"Name\": \""+sLocationA+"\", \"SVMXC__Stocking_Location__c\": false,\"SVMXC__Street__c\": \"Lewes\",\"SVMXC__Country__c\": \"United Kingdom\"}";
		sLocationA = restServices.restCreate(sObjectApi,sJsonData);
		
		//Create Location
		sLocationB = sSerialNumber+"LocB";
		sJsonData = "{\"Name\": \""+sLocationB+"\", \"SVMXC__Stocking_Location__c\": true,\"SVMXC__Street__c\": \"Rome\",\"SVMXC__Country__c\": \"Italy\"}";
		sLocationB = restServices.restCreate(sObjectApi,sJsonData);
		
		//Create Location
		sLocationC = sSerialNumber+"LocC";
		sJsonData = "{\"Name\": \""+sLocationC+"\", \"SVMXC__Stocking_Location__c\": true,\"SVMXC__Street__c\": \"Bangalore Area\",\"SVMXC__Country__c\": \"India\"}";
		sLocationC = restServices.restCreate(sObjectApi,sJsonData);
		
		//Create Location
		sLocationD = sSerialNumber+"LocD";
		sJsonData ="{\"Name\": \""+sLocationD+"\", \"SVMXC__Stocking_Location__c\": false,\"SVMXC__Street__c\": \"Berlin\",\"SVMXC__Country__c\": \"Germany\"}" ;
		sLocationD = restServices.restCreate(sObjectApi,sJsonData);
		
		//Create Location
		sLocationE = sSerialNumber+"LocE";
		sJsonData ="{\"Name\": \""+sLocationE+"\", \"SVMXC__Stocking_Location__c\": false,\"SVMXC__Street__c\": \"Berlin\",\"SVMXC__Country__c\": \"Germany\"}" ;
		sLocationE = restServices.restCreate(sObjectApi,sJsonData);
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Priority__c\":\"High\",\"Number__c\":\"46\",\"SVMXC__Site__c\":\""+sLocationA+"\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName1 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Priority__c\":\"High\",\"Number__c\":\"199\",\"SVMXC__Site__c\":\""+sLocationB+"\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sObjectIDWO2=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectIDWO2+"\'";				
		sWOName2 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Priority__c\":\"Medium\",\"Number__c\":\"14\",\"SVMXC__Site__c\":\""+sLocationC+"\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName3 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Priority__c\":\"High\",\"Number__c\":\"66\",\"SVMXC__Site__c\":\""+sLocationD+"\",\"SVMXC__Company__c\":\""+sAccountNameA+"\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName4 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Priority__c\":\"Low\",\"Number__c\":\"44\",\"SVMXC__Billing_Type__c\":\"Contract\",\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName5 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		/*
		//Updating Technician with LocationE
		sObjectApi = "SVMXC__Service_Group_Members__c?";
		sJsonData="{\"SVMXC__Inventory_Location__c\":\""+sSerialNumber+"LocE"+"\"}";
		restServices.restUpdaterecord(sObjectApi, sJsonData, "a263D000000AagdQAC");
		*/
		
//		genericLib.executeSahiScript("appium/SCN_Explore_RS_10545_prerequisite.sah", sTestID);
//		Assert.assertTrue(commonUtility.verifySahiExecution(), "Execution of Sahi script is failed");
//		ExtentManager.logger.log(Status.PASS,"Testcase " + sTestID + "Sahi verification is successful");
		
	}

	@Test(enabled = true, retryAnalyzer=Retry.class)
	public void RS_10545Test() throws Exception 
	{
		sTestID = "RS_10545";
		sExploreSearch = GenericLib.getExcelData(sTestID, sTestID,"ExploreSearch");
		sDate=new java.sql.Date(System.currentTimeMillis()).toString().split("-");
		if(Integer.parseInt(sDate[2])>28)
		{
			sDate[2]="1";
			iMonth=Integer.parseInt(sDate[1])+1;
			if(iMonth>12) {
				iMonth=01;
			}
			sDate[1]=""+iMonth;
		}
		sTodayDateTxt= sDate[0]+"-"+sDate[1]+"-"+sDate[2];
	
		iDay=Integer.parseInt(sDate[2])+1;
		sDate[2]=""+iDay;
		sTomDateTxt = sDate[0]+"-"+sDate[1]+"-"+sDate[2];
		
		iDay=Integer.parseInt(sDate[2])+1;
		sDate[2]=""+iDay;
		sDateAfterTomTxt = sDate[0]+"-"+sDate[1]+"-"+sDate[2];
	
		
	try {
		preRequiste();
		
		//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		
		//Config Sync for process
		ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		 
//		//Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Navigation to Search
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Work Orders").isDisplayed(), "Work Orders for RS_10545 SFM Search  is not displayed");
		ExtentManager.logger.log(Status.PASS," Work Orders for RS_10545 Multi Field WO Search text is successfully displayed");
	
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Accounts").isDisplayed(), "Accounts for RS_10545 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Accounts for RS_10545 Multi Field WO Search text is successfully displayed");
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Locations").isDisplayed(), "Locations for RS_10545 SFM Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Products for RS_10545 Multi Field WO Search text is successfully displayed");
	
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Work Orders (USERTRUNK)").isDisplayed(), "Serial Number Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Orders (USERTRUNK) Search text is successfully displayed");
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Work Orders (DATE LITERALS)").isDisplayed(), "SVMXSTD: Account Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Orders (DATE LITERALS) Search text is successfully displayed");
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem("Work Orders (CURRENTUSERID)").isDisplayed(), "Serial Number Search is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Orders (CURRENTUSERID) Search text is successfully displayed");
		//Thread.sleep(GenericLib.iMedSleep); 
		//driver.activateApp(GenericLib.sAppBundleID);
//		System.out.println("  ************** Taping on WO **********");
//		System.out.println(exploreSearchPo.getEleExploreWOSearchLst().size());
//		System.out.println(exploreSearchPo.getEleExploreWOSearchLst().get(0).getText());
//		System.out.println(exploreSearchPo.getEleExploreWOSearchLst().get(1).getText());
//		System.out.println(exploreSearchPo.getEleExploreWOSearchLst().get(2).getText());
//		System.out.println(exploreSearchPo.getEleExploreWOSearchLst().get(3).getText());
		
		ph_ExploreSearchPo.getEleSearchListItem("Work Orders").click();
		
		
		Thread.sleep(GenericLib.iMedSleep); 
		validateSearch("WO");
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName3).isDisplayed(), "Work Order3 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order3 Record is successfully displayed");
		//validateSearch(sWOName4);
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName4).isDisplayed(), "Work Order4 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order4 Record is successfully displayed");
		//validateSearch(sWOName5);
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName5).isDisplayed(), "Work Order5 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order5 Record is successfully displayed");
		
		//Validation of WO1 not to downloaded in search
		validateSearch(sWOName1);
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), sWOName1 +" --> No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,sWOName1 +" -->No Records to display text is successfully displayed");
		
		//Validation of WO2 not to downloaded in search
		validateSearch(sWOName2);
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), sWOName2 +" --> No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,sWOName2 +" -->No Records to display text is successfully displayed");
		
		//Navigation to Accounts Search
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		ph_ExploreSearchPo.getEleSearchListItem("Accounts").click();
		Thread.sleep(GenericLib.iMedSleep); 
		//if(BaseLib.sOSName.equals("ios")) {driver.activateApp(GenericLib.sAppBundleID);}
		
		validateSearch(sSerialNumber+"AccB");
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sSerialNumber+"AccB").isDisplayed(), sSerialNumber+"AccB is not displayed");
		ExtentManager.logger.log(Status.PASS,sSerialNumber+"AccB Record is successfully displayed");
		
		//Validation of AccA not to be downloaded in search
		validateSearch(sSerialNumber+"AccA");
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), sSerialNumber+"AccA --> No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,sSerialNumber +"AccA -->No Records to display text is successfully displayed");
		
		//Validation of AccA not to be downloaded in search
		validateSearch(sSerialNumber+"AccC");
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), sSerialNumber +"AccC --> No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,sSerialNumber +"AccC -->No Records to display text is successfully displayed");
		
		//Navigation to Location Search
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		ph_ExploreSearchPo.getEleSearchListItem("Locations").click();
		Thread.sleep(GenericLib.iMedSleep); 
		
		validateSearch(sSerialNumber+"LocB");
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sSerialNumber+"LocB").isDisplayed(), sSerialNumber+"LocB is not displayed");
		ExtentManager.logger.log(Status.PASS,sSerialNumber+"LocB Record is successfully displayed");
		
		//Validation of sLocationA not to be downloaded in search
		validateSearch(sSerialNumber+"LocA");
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), sSerialNumber +"LocA --> No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,sSerialNumber +"LocA -->No Records to display text is successfully displayed");
		
		//Validation of sLocationC not to be downloaded in search
		validateSearch(sSerialNumber+"LocC");
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), sSerialNumber +"LocC --> No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,sSerialNumber +"LocC -->No Records to display text is successfully displayed");
		
		//Validation of sLocationD not to be downloaded in search
		validateSearch(sSerialNumber+"LocD");
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), sSerialNumber +"LocD --> No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,sSerialNumber +"LocD -->No Records to display text is successfully displayed");
		
		//Navigation to Work Orders (USERTRUNK) Search
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		ph_ExploreSearchPo.getEleSearchListItem("Work Orders (USERTRUNK)").click();
		Thread.sleep(GenericLib.iMedSleep); 
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), "Work Orders (USERTRUNK) --> No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Orders (USERTRUNK) -->No Records to display text is successfully displayed");
		
		//Navigation to Work Orders (CURRENTUSERID) Search
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		ph_ExploreSearchPo.getEleSearchListItem("Work Orders (CURRENTUSERID)").click();
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), "Work Orders (CURRENTUSERID) --> No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Orders (CURRENTUSERID) -->No Records to display text is successfully displayed");
		
		//Navigation to Work Orders (DATE LITERALS) Search
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		ph_ExploreSearchPo.getEleSearchListItem("Work Orders (DATE LITERALS)").click();
		Thread.sleep(GenericLib.iMedSleep); 
		validateSearch("WO");
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName1).isDisplayed(), "Work Order1 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order1 Record is successfully displayed");
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName2).isDisplayed(), "Work Order2 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order2 Record is successfully displayed");
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName3).isDisplayed(), "Work Order3 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order3 Record is successfully displayed");
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName4).isDisplayed(), "Work Order4 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order4 Record is successfully displayed");
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName5).isDisplayed(), "Work Order5 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order5 Record is successfully displayed");
		
		//Navigation to SFM
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, sExploreSearch, "Work Orders", sWOName5, sAutoEditWO);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(ph_WorkOrderPo.getEleHeaderTitle().getText().equals(sAutoEditWO), sAutoEditWO +" is not displayed");
		ExtentManager.logger.log(Status.PASS,sAutoEditWO+" is successfully displayed");
		
		//Update the WO5 with priority HIGH
		ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getEleBillingTypeField(), "High");
		ph_WorkOrderPo.getElesave().click();
		Thread.sleep(GenericLib.iLowSleep);
		
		ExtentManager.logger.log(Status.PASS,"Update process is successful");
		//Validation of WO search after updating WO2
		ph_ExploreSearchPo.geteleExploreIcn().click();
				
		
		//Updating WorkOrder2 with Account B
		sObjectApi = "SVMXC__Service_Order__c";
		sJsonData="{\"SVMXC__Company__c\":\""+sAccountNameB+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sObjectIDWO2 );

		//Updating AccountC with Bangalore city
		sObjectApi = "Account";
		sJsonData="{\"BillingCity\":\"Bangalore\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sAccountNameC );

		//Update Location with Stocking location set to true
		sObjectApi = "SVMXC__Site__c";
		sJsonData = "{\"SVMXC__Stocking_Location__c\": true}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sLocationA );

		//Update Location with country India
		sObjectApi = "SVMXC__Site__c";
		sJsonData = "{\"SVMXC__Country__c\": \"India\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sLocationB );

		//Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		
		//if(BaseLib.sOSName.equals("ios")) {driver.activateApp(GenericLib.sAppBundleID);}
		//Validation of WO search after updating WO2
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		ph_ExploreSearchPo.getEleSearchListItem("Work Orders").click();
		Thread.sleep(GenericLib.iMedSleep); 
		validateSearch(sWOName2);
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName2).isDisplayed(), "Work Order2 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order2 Record is successfully displayed");
		
		validateSearch(sWOName3);
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName3).isDisplayed(), "Work Order3 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order3 Record is successfully displayed");
		
		validateSearch(sWOName4);
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName4).isDisplayed(), "Work Order4 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order4 Record is successfully displayed");
		
		validateSearch(sWOName5);
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), sWOName5 +" --> No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,sWOName5 +" -->No Records to display text is successfully displayed");
		
		//Navigation to Accounts Search
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		ph_ExploreSearchPo.getEleSearchListItem("Accounts").click();
		Thread.sleep(GenericLib.iMedSleep); 
		
		//Clearing search text
		validateSearch(sSerialNumber+"AccB");
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sSerialNumber+"AccB").isDisplayed(), sSerialNumber+"AccB is not displayed");
		ExtentManager.logger.log(Status.PASS,sSerialNumber+"AccB Record is successfully displayed");
		
		//Clearing search text
		validateSearch(sSerialNumber+"AccC");
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sSerialNumber+"AccC").isDisplayed(), sSerialNumber+"AccB is not displayed");
		ExtentManager.logger.log(Status.PASS,sSerialNumber+"AccC Record is successfully displayed");
		
		//Navigation to Location Search
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		ph_ExploreSearchPo.getEleSearchListItem("Locations").click();
		Thread.sleep(GenericLib.iMedSleep); 
		//Clearing search text
		validateSearch(sSerialNumber+"LocA");		
		//Validation of LocationA to be displayed in search
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sSerialNumber+"LocA").isDisplayed(), sSerialNumber+"LocA is not displayed");
		ExtentManager.logger.log(Status.PASS,sSerialNumber+"LocA Record is successfully displayed");
		
		validateSearch(sSerialNumber+"LocB");
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), sSerialNumber+"LocB --> No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,sSerialNumber +"LocB -->No Records to display text is successfully displayed");
	
		//Updating WorkOrder2 with Location5 and scheduled date to tomorrow
		sObjectApi = "SVMXC__Service_Order__c";
		sJsonData="{\"SVMXC__Scheduled_Date_Time__c\":\""+sDateAfterTomTxt+"\",\"SVMXC__Site__c\":\""+sLocationE+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sObjectIDWO2 );
		
		//Updating WorkOrder5 with Location5 and scheduled date to tomorrow
		sObjectApi = "SVMXC__Service_Order__c";
		sJsonData="{\"SVMXC__Scheduled_Date_Time__c\":\""+sTomDateTxt+"\",\"SVMXC__Site__c\":\""+sLocationE+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sObjectIDWO2 );

		//Updating WorkOrder3  and scheduled date to today
		sObjectApi = "SVMXC__Service_Order__c";
		sJsonData="{\"SVMXC__Scheduled_Date_Time__c\":\""+sTodayDateTxt+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sObjectIDWO2 );

		//Updating WorkOrder4 with scheduled Day after tomorrow
		sObjectApi = "SVMXC__Service_Order__c";
		sJsonData="{\"SVMXC__Scheduled_Date_Time__c\":\""+sDateAfterTomTxt+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sObjectIDWO2 );

		//Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		Thread.sleep(GenericLib.iMedSleep); 
		//if(BaseLib.sOSName.equals("ios")) {driver.activateApp(GenericLib.sAppBundleID);}
		
		/*
		//Navigation to Work Orders (USERTRUNK) Search
		commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
		commonsUtility.tap(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		commonsUtility.tap(exploreSearchPo.getEleExploreChildSearchTxt("Work Orders (USERTRUNK)"));
		Thread.sleep(GenericLib.iMedSleep); 
		
		//Validation of WO2 and WO5
		validateSearch("WO");
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName2).isDisplayed(), "Work Order2 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order2 Record is successfully displayed");
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName5).isDisplayed(), "Work Order5 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order5 Record is successfully displayed");
		*/
		
		//Navigation to Work Orders (DATE LITERALS) Search
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		ph_ExploreSearchPo.getEleSearchListItem("Work Orders (DATE LITERALS").click();
		Thread.sleep(GenericLib.iHighSleep); 
		
		//Validation of WO1, WO3 and WO5
		validateSearch(sWOName1);
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName1).isDisplayed(), "Work Order1 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order1 Record is successfully displayed");
		
		validateSearch(sWOName3);
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName3).isDisplayed(), "Work Order3 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order3 Record is successfully displayed");
		
		validateSearch(sWOName5);
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName5).isDisplayed(), "Work Order5 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order5 Record is successfully displayed");
		
		/*
		//Navigation to SFM
		workOrderPo.navigateToWOSFM(commonsUtility, exploreSearchPo, sExploreSearch, "Work Orders", sWOName5, sAutoEditWO);
		Thread.sleep(GenericLib.iLowSleep);
		Assert.assertTrue(workOrderPo.getEleActionsTxt(sAutoEditWO).isDisplayed(), sAutoEditWO +" is not displayed");
		ExtentManager.logger.log(Status.PASS,sAutoEditWO+" is successfully displayed");
		
		//Update the WO5 with priority Medium
		commonsUtility.setPickerWheelValue(workOrderPo.getElePriorityLst(), "Medium");
		Thread.sleep(GenericLib.iLowSleep);
		commonsUtility.tap(workOrderPo.getEleSaveLnk());
		Thread.sleep(GenericLib.iLowSleep);
	
		//Navigation to Work Orders (CURRENTUSERID) Search
		commonsUtility.tap(exploreSearchPo.getEleExploreIcn());
		commonsUtility.tap(exploreSearchPo.getEleSearchNameTxt(sExploreSearch));
		commonsUtility.tap(exploreSearchPo.getEleExploreChildSearchTxt("Work Orders (CURRENTUSERID)"));
		Thread.sleep(GenericLib.iMedSleep); 
		Assert.assertTrue(exploreSearchPo.getEleWorkOrderIDTxt(sWOName1).isDisplayed(), "Work Order1 is not displayed");
		ExtentManager.logger.log(Status.PASS,"Work Order1 Record is successfully displayed");
		*/
				
	}catch(Exception e)
	{	ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestID + " Testcase failed");
		throw e;
		
	}

}
	
	private void validateSearch(String sObjectValue) throws InterruptedException
	{
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().clear();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sObjectValue);
		Thread.sleep(GenericLib.iMedSleep);		
	}
	
	

}
