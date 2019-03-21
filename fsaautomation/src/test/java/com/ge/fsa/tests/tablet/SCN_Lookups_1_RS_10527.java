package com.ge.fsa.tests.tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class SCN_Lookups_1_RS_10527 extends BaseLib {
	
	String sTestCaseID = "RS_10527";
	String sScriptName = "Scenario_10527";
	String sExploreSearch = "AUTOMATION SEARCH";
	String sExploreChildSearch = "Work Orders";
	String sProcessName = "Auto_Reg_10527";
//	String sProcessName = "AutoReg10529";
	String sAccountName = "AshwiniAutoAcc";
	String sAccId = "";
	String sLocName = "Ricardo";
	String sLocName1 = "Nashville";
	String sProdName = "SampleProd";
	String sSearchTxt = "Ronald Ross";
	
	@Test(retryAnalyzer=Retry.class)
	public void RS_10527() throws Exception {
		
		commonsPo.execSahi(genericLib, sScriptName, sTestCaseID);
		
		// Create Account
		String sAccCount = restServices.restGetSoqlValue("SELECT+Count()+from+Account+Where+name+=\'"+sAccountName+"\'", "totalSize");
//		System.out.println(sAccCount);
		if(Integer.parseInt(sAccCount)==0) {
			sAccId = restServices.restCreate("Account?","{\"Name\": \""+sAccountName+"\"}");
			System.out.println("The Acc Id of "+sAccountName+" is "+sAccId);
			// Create Contact
			String sConId = restServices.restCreate("Contact?","{\"FirstName\": \"Shane\", \"LastName\": \"Shalken\", \"AccountId\": \""+sAccId+"\"}");
			System.out.println("SS "+sConId);
			String sConId1 = restServices.restCreate("Contact?","{\"FirstName\": \"Tim\", \"LastName\": \"Shalken\", \"AccountId\": \""+sAccId+"\"}");
			System.out.println("TS "+sConId1);
			String sConId2 = restServices.restCreate("Contact?","{\"FirstName\": \"Ronald\", \"LastName\": \"Ross\", \"AccountId\": \""+sAccId+"\"}");
			System.out.println("RR "+sConId2);
		}
		
		//Create Location
		String sLoc = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName+"\'", "totalSize");
		if(Integer.parseInt(sLoc)==0) {
			String sLocId = restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName+"\",\"SVMXC__Country__c\": \"France\"}");
			System.out.println("Loc Id is "+sLocId);
		}
		
		
		//Create Location 2
		String sLoc1 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName1+"\'", "totalSize");
		if(Integer.parseInt(sLoc1)==0) {
			String sLocId1 = restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName1+"\",\"SVMXC__Country__c\": \"Qatar\"}");
			System.out.println("Loc Id is "+sLocId1);
		}
		
		//Create Product
		String sProd = restServices.restGetSoqlValue("SELECT+Count()+from+Product2+Where+name+=\'"+sProdName+"\'", "totalSize");
		if(Integer.parseInt(sProd)==0) {
			String sProdId = restServices.restCreate("Product2?","{\"Name\": \""+sProdName+"\"}");
			System.out.println("Product Id is "+sProdId);
		}
		
		//******Creating Work Order******
		String sWoID  = restServices.restCreate("SVMXC__Service_Order__c?","{}");
//		System.out.println("Wo ID "+sWoID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWoID+"\'", "Name");
//		System.out.println("WO no ="+sWOName);
//		String sProdName = "a1";
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		String sAllCon = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Name+=\'"+sSearchTxt+"\'", "totalSize");
		toolsPo.configSync(commonsPo);
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, sExploreSearch, sExploreChildSearch, sWOName, sProcessName);
		//******Validate 1st Case******
		commonsPo.tap(workOrderPo.getLblContact());
		List<WebElement> contactList = new ArrayList<WebElement>();
		contactList = workOrderPo.getcontactListInLkp();
		System.out.println("Contacts without Account "+contactList.size());
//		for(WebElement we:contactList) {
//			System.out.println(we.getText());
//		}
		String sConWoAcc = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Id+=null", "totalSize");
		System.out.println("Contacts Without Accounts fetched from Database ="+sConWoAcc);
		if(sOSName.equals("android")) {
			Assert.assertEquals(commonsPo.getHeaderCount(workOrderPo), Integer.parseInt(sConWoAcc));
		}
		else {
			Assert.assertEquals(contactList.size(), Integer.parseInt(sConWoAcc));
		}
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		//******Validate 2nd Case******
		commonsPo.tap(workOrderPo.getLblAccount());
		commonsPo.lookupSearch(sAccountName);
		commonsPo.tap(workOrderPo.getLblContact());
		contactList = workOrderPo.getcontactListInLkp();
//		System.out.println("Contacts with Account Acme "+contactList.size());
		String sConWithAcme = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Name+=\'"+sAccountName+"\'", "totalSize");
//		System.out.println("Contacts with Account Acme fetched from Database ="+sConWithAcme);
		if(sOSName.equals("android")) {
			Assert.assertEquals(commonsPo.getHeaderCount(workOrderPo), Integer.parseInt(sConWithAcme));
		}
		else {
		Assert.assertEquals(contactList.size(), Integer.parseInt(sConWithAcme));
		}
		//******Validate 3rd Case******
		commonsPo.tap(workOrderPo.getLnkFilters());
		Thread.sleep(GenericLib.iLowSleep);
//		WebElement toTap=driver.findElement(By.xpath("//span[contains(text(),'Account:')]/ancestor::div[2]"));
//		commonsPo.tap(toTap);
//		WebElement toTap1=driver.findElement(By.xpath("//span[contains(text(),'Account:')]/ancestor::div[1]"));
//		commonsPo.tap(toTap1);
		if(workOrderPo.getCheckBoxAccount().isSelected()&&sOSName.equals("ios")) {
			commonsPo.tap(workOrderPo.getcheckBoxAccount01(),20,20);
		}       
		else {
			commonsPo.tap(workOrderPo.getcheckBoxAccount02());
		}
		commonsPo.tap(workOrderPo.getBtnApply());
		commonsPo.lookupSearchOnly(sSearchTxt);
		Thread.sleep(GenericLib.iHighSleep);
		contactList = workOrderPo.getcontactListInLkp();
//		System.out.println("All Contacts displayed on FSA "+contactList.size());
//		System.out.println("All Contacts fetched from Database ="+sAllCon);
//		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		if(sOSName.equals("android")) {
			Assert.assertEquals(commonsPo.getHeaderCount(workOrderPo), Integer.parseInt(sAllCon));
		}
		else {
		Assert.assertEquals(contactList.size(), Integer.parseInt(sAllCon));
		}
		commonsPo.tap(workOrderPo.getBtnReset());
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		//******Validate 7th Case******
		commonsPo.setPickerWheelValue(workOrderPo.geteleCountry_Edit_Lst(), "France");
		commonsPo.tap(workOrderPo.getlblSite());
		List<WebElement> locList = new ArrayList<WebElement>();
		locList = workOrderPo.getLocListInLkp();
//		System.out.println(locList.size());
		String sLocCnt = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+SVMXC__Country__c+=\'France\'", "totalSize");
		if(sOSName.equals("android")) {
			Assert.assertEquals(commonsPo.getLocHeaderCount(workOrderPo),Integer.parseInt(sLocCnt));
		}
		else {
			Assert.assertEquals(Integer.parseInt(sLocCnt),locList.size());
		}	
		//		for(WebElement w:locList) {
//			System.out.println("Test is "+w.getText());
//			Assert.assertTrue(w.getText().contains(sLocName));
//		}
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		//******Validate 8th Case******
		workOrderPo.addParts(commonsPo, workOrderPo, sProdName);
		workOrderPo.getLblChildPart(sProdName).click();
		commonsPo.tap(workOrderPo.getLblChildPart(sProdName));
		Thread.sleep(GenericLib.iMedSleep);
		commonsPo.tap(workOrderPo.getlblToLocation());
		List<WebElement> locList1 = new ArrayList<WebElement>();
		locList1 = workOrderPo.getLocListInLkp();
		if(sOSName.equals("android")) {
			Assert.assertEquals(commonsPo.getLocHeaderCount(workOrderPo),Integer.parseInt(sLocCnt));
		}
		else {
			Assert.assertEquals(Integer.parseInt(sLocCnt),locList1.size());
		}
		
//		System.out.println(locList1.size());
//		for(WebElement w:locList1) {
//			Assert.assertTrue(w.getText().contains(sLocName));
//		}
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		commonsPo.tap(workOrderPo.getEleDoneBtn());
		//******Validate 9th Case******
		
		commonsPo.setPickerWheelValue(workOrderPo.geteleCountry_Edit_Lst(), "Qatar");
		Thread.sleep(3000);
		workOrderPo.getLblChildPart(sProdName).click();
		commonsPo.tap(workOrderPo.getLblChildPart(sProdName));
		commonsPo.tap(workOrderPo.getlblToLocation());
		List<WebElement> locList2 = new ArrayList<WebElement>();
		locList2 = workOrderPo.getLocListInLkp();
		String sLocCnt2 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+SVMXC__Country__c+=\'Qatar\'", "totalSize");
		if(sOSName.equals("android")) {
			Assert.assertEquals(commonsPo.getLocHeaderCount(workOrderPo),Integer.parseInt(sLocCnt2));
		}
		else {
		Assert.assertEquals(Integer.parseInt(sLocCnt2),locList2.size());
		}
//		System.out.println(locList2.size());
//		for(WebElement w:locList2) {
//			Assert.assertTrue(w.getText().contains(sLocName1));
//		}
	}
}

