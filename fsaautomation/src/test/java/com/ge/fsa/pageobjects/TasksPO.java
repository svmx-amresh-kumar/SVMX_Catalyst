package com.ge.fsa.pageobjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.ge.fsa.lib.ExtentManager;

import io.appium.java_client.AppiumDriver;

public class TasksPO{
	
	public TasksPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	AppiumDriver driver = null;
	
	@FindBy(xpath="//div[text()='Tasks']")
	private WebElement eleTasksIcn;
	public WebElement getEleTasksIcn()
	{
		return eleTasksIcn;
	}
	
	@FindBy(xpath="//span[text()='Add Task']")
	private WebElement eleAddTasksBtn;
	public WebElement getEleAddTasksBtn()
	{
		return eleAddTasksBtn;
	}
	
	@FindBy(xpath="//span[text()='Save']")
	private WebElement eleSaveBtn;
	public WebElement getEleSaveBtn()
	{
		return eleSaveBtn;
	}
	
	@FindBy(xpath="//span[text()='High']")
	private WebElement eleHighRadioBtn;
	public WebElement getEleHighRadioBtn()
	{
		return eleHighRadioBtn;
	}
	
	@FindBy(xpath="//span[text()='Description']/following::textarea[1]")
	private WebElement eleDescriptionTxtArea;
	public WebElement getEleDescriptionTxtArea()
	{
		return eleDescriptionTxtArea;
	}
	
	@FindBy(xpath="(//span[text()='Tasks'])[1]")
	private WebElement eleTasksLbl;
	public WebElement getEleTasksLbl()
	{
		return eleTasksLbl;
	}
	
	@FindBy(xpath="//div[@class='tasks-list-item-subject']")
	private List<WebElement> eleInTasksList;
	public List<WebElement> getEleInTasksList() {
		return eleInTasksList;
	}
	
	private WebElement elePriorityIcon;
	public WebElement getElePriorityIcon(String taskName) {
		
		elePriorityIcon = driver.findElement(By.xpath("//div[text()='"+taskName+"']/following-sibling::span[contains(@class,'double-exclamation')]"));
		return elePriorityIcon;
	}
	
	/**
	 * Add task , if optional sDesc is not passed , default description value will be set.
	 * 
	 * @param commonsPo
	 * @param sDesc
	 * @throws InterruptedException
	 */

	public String addTask(CommonsPO commonsPo, String...sDesc) throws InterruptedException {
		String desc = sDesc.length > 0 ? sDesc[0] : commonsPo.generaterandomnumber("TaskDesc");
		commonsPo.tap(getEleTasksIcn());	
		Assert.assertTrue(getEleTasksLbl().isDisplayed(), "Tasks screen is not displayed");
		ExtentManager.logger.log(Status.PASS,"Tasks screen is displayed successfully");
		commonsPo.tap(getEleAddTasksBtn());
		getEleDescriptionTxtArea().sendKeys(desc);
		commonsPo.tap(getEleHighRadioBtn());
		commonsPo.tap(getEleSaveBtn());
		List<WebElement> tasksList = new ArrayList<WebElement>();
		tasksList = getEleInTasksList();
		int count = 0;
		for(WebElement we:tasksList) {
			if(we.getText().contains(desc)) {
				count++;
			}
		}
		Assert.assertEquals(count, 1);
		ExtentManager.logger.log(Status.PASS,"Tasks added successfully");
		Assert.assertTrue(getElePriorityIcon(desc).isDisplayed(),"High Priority Icon is not displayed");
		ExtentManager.logger.log(Status.PASS,"High Priority Icon is displayed successfully");
		return desc;
	}
}
