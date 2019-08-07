package com.ge.fsa.tests.phone;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.BaseLib;
import com.ge.fsa.lib.ExtentManager;
import com.ge.fsa.lib.Retry;

import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class Ph_SCN_Attachments_2_RS_10522 extends BaseLib {

	String sWorkOrderID;
	String sWOName;
	String sProcessname = "EditWoAutoTimesstamp";
	String imageName = "TestImage_10522";
	String videoName = "TestVideo_10522";
	String cameraRollName = "TestCameraRoll_10522";

	@Test(retryAnalyzer = Retry.class)
	public void RS_10522() throws Exception {

		sWorkOrderID = restServices.restCreate("SVMXC__Service_Order__c?",
				"{\"SVMXC__Order_Status__c\":\"Open\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\",\"SVMXC__Scheduled_Date__c\":\"2018-08-28\",\"SVMXC__Scheduled_Date_Time__c\":\"2018-08-28T09:42:00.000+0000\",\"SVMXC__Idle_Time__c\":\"30\",\"SVMXC__Priority__c\":\"Low\"}");
		System.out.println(sWorkOrderID);
		sWOName = restServices.restGetSoqlValue("SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'" + sWorkOrderID + "\'", "Name");
		ExtentManager.logger.log(Status.INFO, "Work Order created through webservice. Returned Work Order : "+sWOName);
		ph_LoginHomePo.login(commonUtility, ph_MorePo);
		ph_MorePo.syncData(commonUtility);

		// Navigate to WorkOrder and Images & Videos tab
		ph_ExploreSearchPo.navigateToSFM(commonUtility, ph_WorkOrderPo, "AUTOMATION SEARCH", "Work Orders", sWOName, sProcessname);
		commonUtility.gotToTabHorizontal("IMAGES & VIDEOS");

		ph_WorkOrderPo.workOrderAttach(commonUtility, "video", videoName);
		ExtentManager.logger.log(Status.INFO, "Video attachment is attached.");
		ph_WorkOrderPo.workOrderAttach(commonUtility, "image", imageName);
		ExtentManager.logger.log(Status.INFO, "Image attachment is attached.");
		ph_WorkOrderPo.workOrderAttach(commonUtility, "cameraRoll", cameraRollName);
		ExtentManager.logger.log(Status.INFO, "Attachment is attached from cameraroll.");
		// saving and datasync
		ph_WorkOrderPo.getElesave().click();
		ExtentManager.logger.log(Status.INFO, "Work order is saved after adding attachements");
		ph_MorePo.syncData(commonUtility);

		Thread.sleep(30000);

		String attachmentId = restServices.restGetSoqlValue("SELECT+Id+FROM+Attachment+WHERE+ParentId='" + sWorkOrderID + "'+AND+Name+LIKE+'%25" + imageName + "%25'", "Id");
		Assert.assertNotNull(attachmentId, "Photo taken from camara is not synced correctly");
		ExtentManager.logger.log(Status.INFO, "Image attachment is correctly synced to server.");
		attachmentId = restServices.restGetSoqlValue("SELECT+Id+FROM+Attachment+WHERE+ParentId='" + sWorkOrderID + "'+AND+Name+LIKE+'%25" + videoName + "%25'", "Id");
		Assert.assertNotNull(attachmentId, "Video taken from camara is not synced correctly");
		ExtentManager.logger.log(Status.INFO, "Video attachment is correctly synced to server.");
		attachmentId = restServices.restGetSoqlValue("SELECT+Id+FROM+Attachment+WHERE+ParentId='" + sWorkOrderID + "'+AND+Name+LIKE+'%25" + cameraRollName + "%25'", "Id");
		Assert.assertNotNull(attachmentId, "Photo added from camera roll is not synced correctly");
		ExtentManager.logger.log(Status.INFO, "Attachment added from cameraroll is correctly synced to server.");
	}

}
