package com.ge.fsa.tests;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import io.appium.java_client.TouchAction;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.CommonUtility;
import com.ge.fsa.lib.GenericLib;
import com.ge.fsa.tablet.pageobjects.CreateNewPO;

public class POC_AndroidTap extends BaseLib {
	
	@Test
	public void dummyTest() throws InterruptedException {
		loginHomePo.login(commonsUtility, exploreSearchPo);
		Thread.sleep(5000);
	  	commonsUtility.tap(createNewPO.getEleCreateNew());
		commonsUtility.tap(createNewPO.getEleCreateNewWorkOrder());
		commonsUtility.setPickerWheelValue(createNewPO.getEleClickPriorityPicklist(), "Medium");
		Thread.sleep(2000);
//		System.out.println(calendarPO.geteleNewClick().isDisplayed());
//		calendarPO.geteleNewClick().click();
//		System.out.println("Waiting ");
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript("return arguments[0].click()", calendarPO.geteleNewClick());
//		System.out.println("Clicked");
		
		
//	commonsUtility.tap(calendarPO.geteleNewClick());
//		Thread.sleep(15000);
		//*[text()='Priority']
		
	}

}
