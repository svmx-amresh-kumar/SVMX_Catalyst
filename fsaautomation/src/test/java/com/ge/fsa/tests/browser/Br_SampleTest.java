package com.ge.fsa.tests.browser;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ge.fsa.lib.BaseLib;




public class Br_SampleTest extends BaseLib{
	
	@Test
	public void testScenario() throws InterruptedException {
		
		String sTime = "4:00 PM";
		String sTechName = "Lee";
		String sMeetingName = "Dentist Appointment";
		initDriver();
		loginPagePo.geLogin(geUsn, gePwd);
		Thread.sleep(10000);
		homePagePo.moveMeeting(sTime, sTechName, sMeetingName, actionTest);
		homePagePo.resizeMeeting(sMeetingName, js, "120Px", actionTest);
//		homePagePo.rightClick("11:00 AM","Rob",sMeetingName, actionTest);
		homePagePo.rightClick(sMeetingName,1, actionTest);
		Thread.sleep(5000);
		homePagePo.setEventDetails("Test","Jong","07/22/2017","3:00 PM","07/22/2017","4:30 PM");
//		Assert.assertTrue(homePagePo.getStartDateTime().contains(sTime));
//		System.out.println("Assert Successfull");

		
		
	}
	

}
