package com.ge.fsa.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.kirwa.nxgreport.NXGReports;
import com.kirwa.nxgreport.logging.LogAs;

import io.appium.java_client.AppiumDriver;

public class TasksPO {
	
	public TasksPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	AppiumDriver driver = null;
	
	@FindBy(xpath="//div[.='Tasks']")
	private WebElement eleTasksIcn;
	public WebElement getEleTasksIcn()
	{
		return eleTasksIcn;
	}
	
	@FindBy(xpath="//span[.='Add Task']")
	private WebElement eleAddTasksBtn;
	public WebElement getEleAddTasksBtn()
	{
		return eleAddTasksBtn;
	}
	
	@FindBy(xpath="//span[.='Save']")
	private WebElement eleSaveBtn;
	public WebElement getEleSaveBtn()
	{
		return eleSaveBtn;
	}
	
	@FindBy(xpath="//span[.='High']/following::div[contains(@id,'radioinput')]")
	private WebElement eleHighRadioBtn;
	public WebElement getEleHighRadioBtn()
	{
		return eleHighRadioBtn;
	}
	
	@FindBy(xpath="//span[.='Description']/following::textarea[1]")
	private WebElement eleDescriptionTxtArea;
	public WebElement getEleDescriptionTxtArea()
	{
		return eleDescriptionTxtArea;
	}
	
	@FindBy(xpath="//span[.='Tasks'])[1]")
	private WebElement eleTasksLbl;
	public WebElement getEleTasksLbl()
	{
		return eleTasksLbl;
	}
	
	
	public void addTask(CommonsPO commonsPo) throws InterruptedException {
		commonsPo.tap(getEleTasksIcn());	
		Assert.assertTrue(getEleTasksLbl().isDisplayed(), "Tasks screen is not displayed");
		NXGReports.addStep("Tasks screen is displayed successfully", LogAs.PASSED, null);
		
	}
	
	
	

}
