package com.ge.fsa.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;

public class Sanity9_Mobile_Inventory extends BaseLib {
	
	
@Test
public void Scenario9Test() throws InterruptedException
{
			//Pre Login to app
			loginHomePo.login(commonsPo, exploreSearchPo);
			//toolsPo.configSync(commonsPo);
			
			Thread.sleep(GenericLib.iMedSleep);
			Assert.assertTrue(inventoryPo.getelePartsIcn().isDisplayed(), "Parts Icon Mobile Inventory is not dipslayed");		
			//NXGReports.addStep("Parts icon Mobile Inventory is visible", LogAs.PASSED, null);
			ExtentManager.logger.log(Status.PASS,"Parts icon Mobile Inventory is visible");

			System.out.println("mobile inventory tab is visible....");
			commonsPo.tap(inventoryPo.getelePartsIcn());
			
			Assert.assertTrue(inventoryPo.geteleMyStockTab().isDisplayed(), "My Stock tab is not dipslayed");		
			//NXGReports.addStep("My Stock tab is visible", LogAs.PASSED, null);
			ExtentManager.logger.log(Status.PASS,"My Stock tab is visible");

			Assert.assertTrue(inventoryPo.geteleCatalogTab().isDisplayed(), "My Stock tab is not dipslayed");		
			//NXGReports.addStep("Catalog tab is visible", LogAs.PASSED, null);
			ExtentManager.logger.log(Status.PASS,"Catalog tab is visible");

			commonsPo.tap(inventoryPo.geteleCatalogTab());
			
			
}

}