package com.ge.fsa.tests.phone;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_ExploreSearchRS_10548 extends BaseLib{

	
	int iWhileCnt = 0;
	String sTestID = null;
	String sExploreSearch = null;
	String sExploreChildSearchTxt = null;
	String sObjectApi = null;
	String sJsonData = null;
	String sObjectID = null;	
	String sSerialNumber = null;
	String sSqlQuery = null;
	String sWOName1 = null;
	String sWOName2 = null;
	String sWOName3 = null;
	String sWOName4 = null;
	String sWOName5 = null;
	String sAccountNameA=null;
	String sAccountNameB=null;
	String sAccountNameC=null;
	String sLocationA = null;
	String sLocationB = null;
	String sLocationC = null;
	String sLocationD = null;
	String sLocationE = null;
	String sLocationId = null;
	boolean configSync;
	
	private void preRequiste() throws Exception { 

		sSerialNumber = commonUtility.generateRandomNumber("RS_10548_");
		
		//create Account
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"AccA\"}";
		sAccountNameA=restServices.restCreate(sObjectApi,sJsonData);
		ExtentManager.logger.log(Status.INFO, "Account has been created through rest web service with Name : "+sSerialNumber+"AccA and returned Account : "+sAccountNameA);
		System.out.println(sAccountNameA);
		sObjectApi = "Account";
		sJsonData="{\"BillingCity\":\""+"Hyderabad"+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sAccountNameA );
		ExtentManager.logger.log(Status.INFO, "Account has been updated through rest web service with Billing City as Hyderabad for Account : "+sAccountNameA);

	
		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"AccB\"}";
		sAccountNameB=restServices.restCreate(sObjectApi,sJsonData);
		ExtentManager.logger.log(Status.INFO, "Account has been created through rest web service with Name : "+sSerialNumber+"AccB and returned Account : "+sAccountNameB);
		sObjectApi = "Account";
		sJsonData="{\"BillingCity\":\""+"Bangalore"+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sAccountNameB );
		ExtentManager.logger.log(Status.INFO, "Account has been updated through rest web service with Billing City as Bangalore for Account : "+sAccountNameB);


		sObjectApi = "Account?";
		sJsonData = "{\"Name\": \""+sSerialNumber+""+"AccC\"}";
		sAccountNameC=restServices.restCreate(sObjectApi,sJsonData);
		ExtentManager.logger.log(Status.INFO, "Account has been created through rest web service with Name : "+sSerialNumber+"AccC and returned Account : "+sAccountNameC);
		sObjectApi = "Account";
		sJsonData="{\"BillingCity\":\""+"Bangalore"+"\"}";
		restServices.restUpdaterecord(sObjectApi,sJsonData,sAccountNameC );
		ExtentManager.logger.log(Status.INFO, "Account has been updated through rest web service with Billing City as Bangalore for Account : "+sAccountNameC);


		//Create Location
		sLocationA = sSerialNumber+"LocA";
		sObjectApi = "SVMXC__Site__c?";
		sJsonData = "{\"Name\": \""+sLocationA+"\", \"SVMXC__Stocking_Location__c\": false,\"SVMXC__Street__c\": \"Lewes\",\"SVMXC__Country__c\": \"United Kingdom\"}";
		sLocationA = restServices.restCreate(sObjectApi,sJsonData);
		ExtentManager.logger.log(Status.INFO, "Location has been created through rest web service with Name : "+sLocationA+" and returned Location : "+sLocationA);

		
		//Create Location
		sLocationB = sSerialNumber+"LocB";
		sJsonData = "{\"Name\": \""+sLocationB+"\", \"SVMXC__Stocking_Location__c\": true,\"SVMXC__Street__c\": \"Colombo\",\"SVMXC__Country__c\": \"SriLanka\"}";
		sLocationB = restServices.restCreate(sObjectApi,sJsonData);
		ExtentManager.logger.log(Status.INFO, "Location has been created through rest web service with Name : "+sLocationB+" and returned Location : "+sLocationB);

		//Create Location
		sLocationC = sSerialNumber+"LocC";
		sJsonData = "{\"Name\": \""+sLocationC+"\", \"SVMXC__Stocking_Location__c\": true,\"SVMXC__Street__c\": \"Bangalore Area\",\"SVMXC__Country__c\": \"India\"}";
		sLocationC = restServices.restCreate(sObjectApi,sJsonData);
		ExtentManager.logger.log(Status.INFO, "Location has been created through rest web service with Name : "+sLocationC+" and returned Location : "+sLocationC);

		//Create Location
		sLocationD = sSerialNumber+"LocD";
		sJsonData ="{\"Name\": \""+sLocationD+"\", \"SVMXC__Stocking_Location__c\": false,\"SVMXC__Street__c\": \"Berlin\",\"SVMXC__Country__c\": \"Germany\"}" ;
		sLocationD = restServices.restCreate(sObjectApi,sJsonData);
		ExtentManager.logger.log(Status.INFO, "Location has been created through rest web service with Name : "+sLocationD+" and returned Location : "+sLocationD);

		//Create Location
		sLocationE = sSerialNumber+"LocE";
		sJsonData ="{\"Name\": \""+sLocationE+"\", \"SVMXC__Stocking_Location__c\": false}" ;
		sLocationE = restServices.restCreate(sObjectApi,sJsonData);
		ExtentManager.logger.log(Status.INFO, "Location has been created through rest web service with Name : "+sLocationE+" and returned Location : "+sLocationE);

		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Priority__c\":\"High\",\"SVMXC__Site__c\":\""+sLocationA+"\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName1 =restServices.restGetSoqlValue(sSqlQuery,"Name");
		ExtentManager.logger.log(Status.INFO, "Work Order has been created through rest web service with location : "+sLocationA+" Work Order : "+sObjectID);

		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Company__c\":\""+sAccountNameB+"\",\"SVMXC__Priority__c\":\"High\",\"SVMXC__Site__c\":\""+sLocationE+"\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName2 =restServices.restGetSoqlValue(sSqlQuery,"Name");
		ExtentManager.logger.log(Status.INFO, "Work Order has been created through rest web service with location : "+sLocationA+" Work Order : "+sObjectID);

		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Priority__c\":\"Medium\",\"SVMXC__Site__c\":\""+sLocationC+"\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName3 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		ExtentManager.logger.log(Status.INFO, "Work Order has been created through rest web service with Location : "+sLocationC+" WorkOrder : "+sObjectID);

		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Company__c\":\""+sAccountNameA+"\",\"SVMXC__Priority__c\":\"High\",\"SVMXC__Site__c\":\""+sLocationD+"\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName4 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		ExtentManager.logger.log(Status.INFO, "Work Order has been created through rest web service with Company : "+ sAccountNameA+", Location : "+sLocationD+" WorkOrder : "+sObjectID);

		
		//Creation of dynamic Work Order
		sObjectApi="SVMXC__Service_Order__c?";
		sJsonData = "{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Priority__c\":\"High\",\"SVMXC__Site__c\":\""+sLocationE+"\"}";
		sObjectID=restServices.restCreate(sObjectApi,sJsonData);
		sSqlQuery ="SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sObjectID+"\'";				
		sWOName5 =restServices.restGetSoqlValue(sSqlQuery,"Name"); 
		ExtentManager.logger.log(Status.INFO, "Work Order has been created through rest web service with Location : "+sLocationE+" WorkOrder : "+sObjectID);

		
		configSync=commonUtility.ProcessCheck(restServices, genericLib, sExploreSearch, "SCN_Explore_RS_10548_prerequisite", sTestID);

	}

	@Test(retryAnalyzer=Retry.class)
	public void RS_10548Test() throws Exception {
		sTestID = "RS_10548";
		sExploreSearch = CommonUtility.readExcelData(GenericLib.sTestDataFile, sTestID,"ExploreSearch");
		
		preRequiste();
		
		//Pre Login to app
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		
		//Config Sync for process
		if(configSync) {
			ph_MorePo.configSync(commonUtility, ph_CalendarPo);
		}
			
		//Data Sync for WO's created
		ph_MorePo.syncData(commonUtility);
		
		//Navigation to SFM
		ph_ExploreSearchPo.geteleExploreIcn().click();
		ph_ExploreSearchPo.getEleSearchNameTxt(sExploreSearch).click();
		
		//Validation of WO with invalid country
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().click();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().clear();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys("Srilanka"+"\n");
		
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		//Validation of WO with invalid account
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().clear();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sSerialNumber+"AccC"+"\n");
		
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		//Validation of WO with invalid WO number purposefully hardcoded
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().clear();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys("WO-354300000"+"\n");
		
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleNoRecords().isDisplayed(), "No Records to display text is not displayed");
		ExtentManager.logger.log(Status.PASS,"No Records to display text is successfully displayed");
		
		//Validation of WO in search
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().clear();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys("WO"+"\n");
		
		
		Assert.assertTrue(ph_WorkOrderPo.getEleWoOrderNumberTxt().isDisplayed(), "Display field WO number is not displayed.");
		ExtentManager.logger.log(Status.PASS,"Display field WO number text is successfully displayed");
		
		Assert.assertTrue(ph_WorkOrderPo.getEleBillingCityTxt().isDisplayed(), "Display field Billing City Text is not displayed.");
		ExtentManager.logger.log(Status.PASS,"Display field Billing City Text is successfully displayed");
		
		Assert.assertTrue(ph_WorkOrderPo.getElePriorityTxt().isDisplayed(), "Display field Priority text is not displayed.");
		ExtentManager.logger.log(Status.PASS,"Display field Priority text is successfully displayed");
		
		//Validation of WO1
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName1).isDisplayed(), sWOName1+" Work Order is not displayed");
		ExtentManager.logger.log(Status.PASS,sWOName1+" Record is successfully displayed");
		
		String sPriority=ph_WorkOrderPo.getEleWoPriorityTxt(sWOName1).getText();
		Assert.assertTrue(sPriority.equals("High"), sWOName1+" priority is not displayed as High");
		ExtentManager.logger.log(Status.PASS,"Priority of work order "+sWOName1+" is Expected : High, Actual : "+sPriority);
		
		//Validation of WO2
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName2).isDisplayed(), sWOName2+" Work Order is not displayed");
		ExtentManager.logger.log(Status.PASS,sWOName2+" Record is successfully displayed");
		
		String sBillingCity=ph_WorkOrderPo.getEleWoLocationTxt(sWOName2).getText();
		Assert.assertTrue(sBillingCity.equals("Bangalore"), "Work Order2 Billing city is not displayed as Bangalore");
		ExtentManager.logger.log(Status.PASS,"Billing city of work order "+sWOName2+" is Expected : Bangalore, Actual : "+sBillingCity);
		
		sPriority=ph_WorkOrderPo.getEleWoPriorityTxt(sWOName2).getText();
		Assert.assertTrue(sPriority.equals("High"), sWOName2+" priority is not displayed as High");
		ExtentManager.logger.log(Status.PASS,"Priority of work order "+sWOName2+" is Expected : High, Actual : "+sPriority);
		
		//Validation of WO3
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName3).isDisplayed(), sWOName3+" Work Order is not displayed");
		ExtentManager.logger.log(Status.PASS,sWOName3+" Record is successfully displayed");
			
		sPriority=ph_WorkOrderPo.getEleWoPriorityTxt(sWOName3).getText();
		Assert.assertTrue(sPriority.equals("Medium"), sWOName3+" priority is not displayed as Medium");
		ExtentManager.logger.log(Status.PASS,"Priority of work order "+sWOName3+" is Expected : Medium, Actual : "+sPriority);
		
		//Validation of WO4
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName4).isDisplayed(), sWOName4+" Work Order is not displayed");
		ExtentManager.logger.log(Status.PASS,sWOName4+" Record is successfully displayed");
		
		sBillingCity=ph_WorkOrderPo.getEleWoLocationTxt(sWOName4).getText();
		Assert.assertTrue(sBillingCity.equals("Hyderabad"), "Work Order2 Billing city is not displayed as Hyderabad");
		ExtentManager.logger.log(Status.PASS,"Billing city of work order "+sWOName4+" is Expected : Hyderabad, Actual : "+sBillingCity);
		
		sPriority=ph_WorkOrderPo.getEleWoPriorityTxt(sWOName4).getText();
		Assert.assertTrue(sPriority.equals("High"), sWOName4+" priority is not displayed as Medium");
		ExtentManager.logger.log(Status.PASS,"Priority of work order "+sWOName4+" is Expected : High, Actual : "+sPriority);
		
		//Validation of WO5
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName5).isDisplayed(), sWOName5+" work order is not displayed");
		ExtentManager.logger.log(Status.PASS,sWOName5+" Record is successfully displayed");
		
		sPriority=ph_WorkOrderPo.getEleWoPriorityTxt(sWOName5).getText();
		Assert.assertTrue(sPriority.equals("High"), sWOName5+" priority is not displayed as Medium");
		ExtentManager.logger.log(Status.PASS,"Priority of work order "+sWOName5+" is Expected : High, Actual : "+sPriority);
		
		//Validation of WO with WO-number
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().click();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().clear();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sWOName5+"\n");
		
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName5).isDisplayed(), sWOName5+" Work Order in work order search is not displayed");
		ExtentManager.logger.log(Status.PASS,sWOName5+" Work Order in work order search is displayed");
		
		//Validation of WO4 with valid country
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().click();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().clear();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys("Germany"+"\n");
		
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName4).isDisplayed(), sWOName4+" Work Order is not displayed in country search with country as Germany");
		ExtentManager.logger.log(Status.PASS,sWOName4+" Work Order4 is displayed in country search with country as Germany");
		
		//Validation of WO1 & WO3 with valid country
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().click();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().clear();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys("in"+"\n");
		
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName1).isDisplayed(), sWOName1+" Work Order is not displayed in country search with country as in");
		ExtentManager.logger.log(Status.PASS,sWOName1+" Work Order is displayed in country search with country as in");
		
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName3).isDisplayed(), sWOName3+" Work Order is not displayed in country search with country as in");
		ExtentManager.logger.log(Status.PASS,sWOName3+" Work Order is not displayed in country search with country as in");
		
		//Validation of WO4 with valid account number
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().click();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().clear();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sSerialNumber+"AccA"+"\n");
		
				
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName4).isDisplayed(), sWOName4+" Work Order with account search is not displayed for account: "+sSerialNumber+"AccA");
		ExtentManager.logger.log(Status.PASS,sWOName4+"Work Order with account search is successfully displayed for Account : "+sSerialNumber+"AccA");
	
		//Validation of WO2 with valid account number
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().click();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().clear();
		ph_ExploreSearchPo.getEleExploreSearchTxtFld().sendKeys(sSerialNumber+"AccB"+"\n");
		
				
		Assert.assertTrue(ph_ExploreSearchPo.getEleSearchListItem(sWOName2).isDisplayed(), sWOName2+"Work Order2 with account search is not displayed for account: "+sSerialNumber+"AccB");
		ExtentManager.logger.log(Status.PASS,sWOName2+"Work Order with account search is successfully displayed for Account : "+sSerialNumber+"AccB");
		
	}

	
	
	
	

}
