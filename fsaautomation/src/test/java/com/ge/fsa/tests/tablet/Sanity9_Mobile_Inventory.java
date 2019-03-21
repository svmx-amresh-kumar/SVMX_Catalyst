package com.ge.fsa.tests.tablet;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.lib.Retry;

public class Sanity9_Mobile_Inventory extends BaseLib {
	
	
@Test(retryAnalyzer=Retry.class)
public void Scenario9Test() throws Exception
{
	
	commonsUtility.preReqSetup(genericLib);
	// Resinstall the app
	lauchNewApp("false");
	
			//Pre Login to app
			loginHomePo.login(commonsUtility, exploreSearchPo);
			//toolsPo.configSync(commonsUtility);
			
			Thread.sleep(GenericLib.iMedSleep);
			Assert.assertTrue(inventoryPo.geteleInventoryIcn().isDisplayed(), "Inventory Icon Mobile Inventory is not dipslayed");		
			//NXGReports.addStep("Parts icon Mobile Inventory is visible", LogAs.PASSED, null);
			ExtentManager.logger.log(Status.PASS,"Parts icon Mobile Inventory is visible");

			System.out.println("mobile inventory tab is visible....");
			commonsUtility.tap(inventoryPo.geteleInventoryIcn());
			Thread.sleep(GenericLib.iLowSleep);
			
			Assert.assertTrue(inventoryPo.geteleMyStockTab().isDisplayed(), "My Stock tab is not dipslayed");		
			//NXGReports.addStep("My Stock tab is visible", LogAs.PASSED, null);
			ExtentManager.logger.log(Status.PASS,"My Stock tab is visible");

			Assert.assertTrue(inventoryPo.geteleCatalogTab().isDisplayed(), "My Stock tab is not dipslayed");		
			//NXGReports.addStep("Catalog tab is visible", LogAs.PASSED, null);
			ExtentManager.logger.log(Status.PASS,"Catalog tab is visible");

			commonsUtility.tap(inventoryPo.geteleCatalogTab());
			
			
}

}
