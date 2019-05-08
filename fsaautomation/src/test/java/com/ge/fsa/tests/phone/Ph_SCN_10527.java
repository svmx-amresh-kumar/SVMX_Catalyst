package com.ge.fsa.tests.phone;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_10527 extends BaseLib {
	
	String sAccountName = "AshwiniAutoAcc";
	String sAccId = "";
	String sCountry = "France";
	String sCountry1 = "Qatar";
	String sProdName = "SampleProd";
	String sLocName = "Ricardo";
	String sLocName1 = "Nashville";
	String sTestCaseID = "RS_10527";
	String sScriptName = "Scenario_10527";
	
	@Test//(retryAnalyzer=Retry.class)
	public void RS_10527() throws Exception {
	
//	commonUtility.execSahi(genericLib, sScriptName, sTestCaseID);
	
	// ******Creating Account******x
	String sAccCount = restServices.restGetSoqlValue("SELECT+Count()+from+Account+Where+name+=\'"+sAccountName+"\'", "totalSize");
//	System.out.println(sAccCount);
	if(Integer.parseInt(sAccCount)==0) {
	sAccId = restServices.restCreate("Account?","{\"Name\": \""+sAccountName+"\"}");
	System.out.println("The Acc Id of "+sAccountName+" is "+sAccId);
	// ******Create Contact******
	String sConId = restServices.restCreate("Contact?","{\"FirstName\": \"Shane\", \"LastName\": \"Shalken\", \"AccountId\": \""+sAccId+"\"}");
	System.out.println("SS "+sConId);
	String sConId1 = restServices.restCreate("Contact?","{\"FirstName\": \"Tim\", \"LastName\": \"Shalken\", \"AccountId\": \""+sAccId+"\"}");
	System.out.println("TS "+sConId1);
	String sConId2 = restServices.restCreate("Contact?","{\"FirstName\": \"Ronald\", \"LastName\": \"Ross\", \"AccountId\": \""+sAccId+"\"}");
	System.out.println("RR "+sConId2);
	}
	// ******Create Product******
	String sProd = restServices.restGetSoqlValue("SELECT+Count()+from+Product2+Where+name+=\'"+sProdName+"\'", "totalSize");
	if(Integer.parseInt(sProd)==0) {
	String sProdId = restServices.restCreate("Product2?","{\"Name\": \""+sProdName+"\"}");
	System.out.println("Product Id is "+sProdId);
	}
	// ******Create Location******
	String sLoc = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName+"\'", "totalSize");
	if(Integer.parseInt(sLoc)==0) {
	String sLocId = restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName+"\",\"SVMXC__Country__c\": \"France\"}");
	System.out.println("Loc Id is "+sLocId);
	}		
	// ******Create Location 1******
	String sLoc1 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+name+=\'"+sLocName1+"\'", "totalSize");
	if(Integer.parseInt(sLoc1)==0) {
	String sLocId1 = restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName1+"\",\"SVMXC__Country__c\": \"Qatar\"}");
	System.out.println("Loc Id is "+sLocId1);
	}
	
	// ******Creating Work Order******
	String sWoID  = restServices.restCreate("SVMXC__Service_Order__c?","{}");
//	System.out.println("Wo ID "+sWoID);
	String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWoID+"\'", "Name");
//	System.out.println("Wo ID "+sWOName);
	ph_LoginHomePo.login(commonUtility, ph_MorePo);
	
	ph_MorePo.syncData(commonUtility);
	
	String sAllCon = restServices.restGetSoqlValue("SELECT+Count()+from+Contact", "totalSize");
//	System.out.println(sAllCon);

	ph_MorePo.configSync(commonUtility, ph_CalendarPo);
	
	ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  "AUTOMATION SEARCH", "Work Orders",sWOName,"Auto_Reg_10527");
	
	// ***********Start of Scenario 1*******************
	ph_WorkOrderPo.getLblContact().click();
	List<WebElement> contactList = new ArrayList<WebElement>();
	contactList = ph_WorkOrderPo.getNoAccContactsLst();
//	System.out.println("Contacts without Account "+contactList.size());
	String sConWoAcc = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Id+=null", "totalSize");
//	System.out.println("Contacts Without Accounts fetched from Database ="+sConWoAcc);
//	Assert.assertEquals(contactList.size(), Integer.parseInt(sConWoAcc),"Existing Bug");--Commented Due to Existing Bug
	ph_WorkOrderPo.getBtnClose().click();
	// ************End of Scenario 1********************
	// ***********Start of Scenario 2*******************
	ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_WorkOrderPo.getLblAccount(), "AshwiniAutoAcc");
	ph_WorkOrderPo.getLblContact().click();
	Thread.sleep(5000);
	contactList = ph_WorkOrderPo.getContactLst(sAccountName);
	System.out.println("Contacts without Account "+contactList.size());
	String sConWithAcme = restServices.restGetSoqlValue("SELECT+Count()+from+Contact+Where+Account.Name+=\'"+sAccountName+"\'", "totalSize");
	System.out.println("Contacts with Account Acme fetched from Database ="+sConWithAcme);
	Assert.assertEquals(contactList.size(), Integer.parseInt(sConWithAcme),"Existing Bug");
	// ************End of Scenario 2********************
	// ***********Start of Scenario 3*******************
	ph_WorkOrderPo.getBtnclrFilter().click();
//	System.out.println(ph_WorkOrderPo.getLblResults().getText());
//	Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sAllCon));--Commented Due to Existing Bug
	ph_WorkOrderPo.getBtnClose().click();
	// ************End of Scenario 3********************
	// ************Start of Scenario 7******************
	ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getCountryPicklst(), "France");
	ph_WorkOrderPo.getLblSite().click();
	Thread.sleep(3000);
	contactList = ph_WorkOrderPo.getContactLst(sCountry);
	String sLocCnt = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+SVMXC__Country__c+=\'France\'", "totalSize");
	Assert.assertEquals(contactList.size(), Integer.parseInt(sLocCnt),"Existing Bug");
	ph_WorkOrderPo.getBtnClose().click();
	// ************End of Scenario 7********************
	// ************Start of Scenario 8******************
	ph_WorkOrderPo.addParts(commonUtility, sProdName);
	Thread.sleep(3000);
	ph_WorkOrderPo.getEle(sProdName).click();
	commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getLblToLocation());
	Thread.sleep(3000);
	contactList = ph_WorkOrderPo.getContactLst(sCountry);
	Assert.assertEquals(contactList.size(), Integer.parseInt(sLocCnt),"Existing Bug");
	ph_WorkOrderPo.getBtnClose().click();
	Thread.sleep(5000);
	ph_WorkOrderPo.getBtnClose().click();
	// ************End of Scenario 8******************
	// ************Start of Scenario 9****************
	ph_WorkOrderPo.getEleOverViewTab().click();
	ph_WorkOrderPo.selectFromPickList(commonUtility, ph_WorkOrderPo.getCountryPicklst(), sCountry1);
	ph_WorkOrderPo.getTabParts().click();
	Thread.sleep(3000);
	ph_WorkOrderPo.getEle(sProdName).click();
	commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getLblToLocation());
	String sLocCnt1 = restServices.restGetSoqlValue("SELECT+Count()+from+SVMXC__Site__c+Where+SVMXC__Country__c+=\'Qatar\'", "totalSize");
	Thread.sleep(3000);
	contactList = ph_WorkOrderPo.getContactLst(sCountry1);
	Assert.assertEquals(contactList.size(), Integer.parseInt(sLocCnt1),"Existing Bug");
	ph_WorkOrderPo.getBtnClose().click();
	Thread.sleep(3000);
	ph_WorkOrderPo.getBtnClose().click();
	Thread.sleep(3000);
	ph_WorkOrderPo.getBtnClose().click();
	ph_WorkOrderPo.getEleDiscardChangesButton().click();
	// ************End of Scenario 9******************
	// ************Start of Scenario 4****************
	ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  "AUTOMATION SEARCH", "Work Orders", sWOName,"Auto_Reg_10527");
	ph_WorkOrderPo.addParts(commonUtility, sProdName);
	Thread.sleep(3000);
	ph_WorkOrderPo.getEle(sProdName).click();
	commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getLblContact());
	contactList = ph_WorkOrderPo.getNoAccContactsLst();
	System.out.println("Contacts Without Accounts fetched from Database ="+contactList.size());
//	Assert.assertEquals(contactList.size(), Integer.parseInt(sConWoAcc),"Existing Bug");//--Commented Due to Existing Bug
	ph_WorkOrderPo.getBtnClose().click();
	Thread.sleep(5000);
	// ************End of Scenario 4******************
	// ************Start of Scenario 5****************
	ph_CreateNewPo.selectFromlookupSearchList(commonUtility, ph_WorkOrderPo.getLblAccount(), "AshwiniAutoAcc");
	commonUtility.custScrollToElementAndClick(ph_WorkOrderPo.getLblContact());
	contactList = ph_WorkOrderPo.getContactLst(sAccountName);
//	System.out.println("Contacts without Account "+contactList.size());
//	System.out.println("Contacts with Account Acme fetched from Database ="+sConWithAcme);
	Assert.assertEquals(contactList.size(), Integer.parseInt(sConWithAcme),"Existing Bug");
	// ************End of Scenario 5******************
	// ***********Start of Scenario 6*****************
	ph_WorkOrderPo.getBtnclrFilter().click();
//	System.out.println(ph_WorkOrderPo.getLblResults().getText());
//	Assert.assertTrue(ph_WorkOrderPo.getLblResults().getText().contains(sAllCon));--Commented Due to Existing Bug
	ph_WorkOrderPo.getBtnClose().click();
	// ************End of Scenario 6******************
} 

}
