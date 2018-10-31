package com.ge.fsa.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.pageobjects.WorkOrderPO;

public class SCN_Lookups_3_RS_10529 extends BaseLib {
	
	@Test
	public void RS_10529() throws InterruptedException, IOException{
		
		// Create Location with Country
		String sLocName = "HCSLocation";
		restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName+"\",\"SVMXC__Country__c\": \"India\"}");
		// Create Location without Country
		String sLocName01 = "HarryLocation";
		restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLocName01+"\"}");
		// Create Product with Description
		String sProductName = "HCSProduct";
        restServices.restCreate("Product2?","{\"Name\":\""+sProductName+"\" }");
        // Create Product without Description
        String sProductName01 = "HarryProduct";
        restServices.restCreate("Product2?","{\"Name\":\""+sProductName01+"\",\"Description\":\"Hello World\" }");
        // Create Contact with Account and Last Name
        String sAccName = commonsPo.generaterandomnumber("AutoAcc");
		String sAccId = restServices.restCreate("Account?","{\"Name\": \""+sAccName+"\" }");
     	String sLastName = "HCSContact";
     	restServices.restCreate("Contact?","{\"LastName\": \""+sLastName+"\", \"AccountId\": \""+sAccId+"\"}");
//     	// Create Contact without Account and with Last Name
     	String sLastName01 = "HarryContact";
     	restServices.restCreate("SVMXC__Site__c?","{\"Name\": \""+sLastName01+"\"}");
     	
     	String sWORecordID = restServices.restCreate("SVMXC__Service_Order__c?","{}");
		System.out.println(sWORecordID);
		String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'", "Name");
		System.out.println("WO no ="+sWOName);
     	
		loginHomePo.login(commonsPo, exploreSearchPo);	
		toolsPo.syncData(commonsPo); // To get the work Order and Products
		Thread.sleep(GenericLib.iMedSleep);
		toolsPo.configSync(commonsPo); // To get the SFM Wizard
		Thread.sleep(GenericLib.iMedSleep);
		workOrderPo.navigateToWOSFM(commonsPo, exploreSearchPo, "AUTOMATION SEARCH", "Work Orders", "WO-00003756", "Auto_Reg_10529");
		commonsPo.tap(workOrderPo.getElePartLnk());
		commonsPo.tap(commonsPo.getElesearchTap());
		commonsPo.getElesearchTap().clear();
		commonsPo.getElesearchTap().sendKeys(sProductName);
		commonsPo.tap(commonsPo.getElesearchButton());
		String sProductOptn = workOrderPo.getLblLookupOptns().getText();
		System.out.println(sProductOptn);
		assertEquals(sProductOptn, sProductName); // Covers Step 1 & 2
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		commonsPo.longPress(workOrderPo.getProblemDescription());
		Thread.sleep(genericLib.iLowSleep);
		commonsPo.tap(workOrderPo.geteleProblemDesc_Edit_WorkOrder());
		workOrderPo.geteleProblemDesc_Edit_WorkOrderPopup().sendKeys("Hello World");
	//	workOrderPo.geteleProblemDesc_Edit_WorkOrderPopup().sendKeys(Keys.ENTER);
		commonsPo.tap(workOrderPo.getEleUpdateLnk()); 
		Thread.sleep(genericLib.iLowSleep);
		commonsPo.tap(workOrderPo.getElePartLnk());
		commonsPo.tap(commonsPo.getElesearchTap());
		commonsPo.getElesearchTap().clear();
		commonsPo.getElesearchTap().sendKeys(sProductName01);
		commonsPo.tap(commonsPo.getElesearchButton());
		String sProductOptn01 = workOrderPo.getLblLookupOptns().getText();
		System.out.println(sProductOptn01);
		assertEquals(sProductOptn01, sProductName01); //Covers Step 3
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		commonsPo.tap(workOrderPo.getlblSite()); 
		commonsPo.tap(commonsPo.getElesearchTap()); 
		commonsPo.getElesearchTap().clear(); 
		commonsPo.getElesearchTap().sendKeys(sLocName01); 
		commonsPo.tap(commonsPo.getElesearchButton()); 
//		String sLocationOptn01 = workOrderPo.getLblLookupOptns().getText(); 
//		System.out.println(sLocationOptn01); 
//		assertEquals(sLocationOptn01, sLocName01); //Covers Step 4
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		Thread.sleep(genericLib.iLowSleep);
		commonsPo.setPickerWheelValue(workOrderPo.geteleCountry_Edit_Lst(), "India");
		commonsPo.tap(workOrderPo.getlblSite()); 
		commonsPo.tap(commonsPo.getElesearchTap()); 
		commonsPo.getElesearchTap().clear(); 
		commonsPo.getElesearchTap().sendKeys(sLocName); 
		commonsPo.tap(commonsPo.getElesearchButton()); 
		String sLocationOptn02 = workOrderPo.getLblLookupOptns().getText(); 
		System.out.println(sLocationOptn02); 
		assertEquals(sLocationOptn02, sLocName); //Covers Step 5
		commonsPo.tap(workOrderPo.getLnkLookupCancel());
		Thread.sleep(genericLib.iMedSleep);
//		workOrderPo.getLblContact().click();
		commonsPo.tap(workOrderPo.getTxtContact()); 
		commonsPo.tap(commonsPo.getElesearchTap()); 
		commonsPo.getElesearchTap().clear(); 
		commonsPo.getElesearchTap().sendKeys(sLastName01); 
		commonsPo.tap(commonsPo.getElesearchButton());
		String sLastNameOptn01 = workOrderPo.getLblLookupOptns().getText(); 
		System.out.println(sLastNameOptn01); 
		assertEquals(sLastNameOptn01, sLastName01); //Covers Step 7
	}

}
