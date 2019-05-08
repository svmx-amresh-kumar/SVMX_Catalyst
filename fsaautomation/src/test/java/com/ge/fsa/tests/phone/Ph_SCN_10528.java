package com.ge.fsa.tests.phone;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Ph_SCN_10528 extends BaseLib {
	
	String sTestCaseID = "RS_10528";
	String sScriptName = "Scenario_10528";
	
	@Test//(retryAnalyzer=Retry.class)
	public void RS_10528() throws Exception {
		
	// ******Creating Work Order******
	String sWoID  = restServices.restCreate("SVMXC__Service_Order__c?","{}");
	// System.out.println("Wo ID "+sWoID);
	String sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWoID+"\'", "Name");
	System.out.println("Wo ID "+sWOName);
	
//	commonUtility.execSahi(genericLib, sScriptName, sTestCaseID);
	ph_LoginHomePo.login(commonUtility, ph_MorePo);
//	ph_MorePo.syncData(commonUtility);
//	ph_MorePo.configSync(commonUtility, ph_CalendarPo);
	
	ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo,  "AUTOMATION SEARCH", "Work Orders","WO-00015399","AutoReg10528");
	
	
} 

}
