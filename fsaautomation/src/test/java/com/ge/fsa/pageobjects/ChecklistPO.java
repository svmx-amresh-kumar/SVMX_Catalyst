package com.ge.fsa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;

public class ChecklistPO {
	
	public ChecklistPO(AppiumDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	AppiumDriver driver = null;
	
	
	private WebElement eleChecklistName;
	public WebElement geteleChecklistName(String checklistname)
	{
		eleChecklistName = driver.findElement(By.xpath("(//div[text()='"+checklistname+"'])[1]"));
		return eleChecklistName;
	}

	
	@FindBy(xpath="//div[@class='x-component x-button x-button-svmx-button-square x-component-svmx-button-square x-button-svmx-save-btn x-component-svmx-save-btn x-button-no-icon sfm-checklist-completed-btn x-layout-box-item x-layout-hbox-item x-stretched']")
	private WebElement eleChecklistSubmit;
	public WebElement eleChecklistSubmit()
	{
		return eleChecklistSubmit;
	}
	/*
	@FindBy(xpath="//a[@class='checklist-lineup-list-item-actionlable'][text()='Start New']")

	private WebElement eleStartNew;
	public WebElement eleStartNew()
	{
		return eleStartNew;
	}*/
	
/*	@FindBy(xpath="//div[@class='x-container x-unsized x-panel x-component x-msgbox x-msgbox-messagebox x-panel-messagebox x-container-messagebox x-component-messagebox x-floated']")
	//@FindBy(xpath="//div[@class='x-dock x-dock-vertical x-unsized']//div[@class='x-component x-button x-button-no-icon x-button-button-tools-msgbox x-component-button-tools-msgbox x-button-button-highlight x-component-button-highlight svmx-msg-button-default x-haslabel x-layout-box-item x-layout-hbox-item x-flexed x-stretched']")
  //  @FindBy(xpath="//div[@class='x-component x-button x-button-no-icon x-button-button-tools-msgbox x-component-button-tools-msgbox x-button-button-highlight x-component-button-highlight svmx-msg-button-default x-haslabel x-layout-box-item x-layout-hbox-item x-flexed x-stretched']")
    private WebElement eleChecklistPopupSubmit;
    public WebElement eleChecklistPopupSubmit()
    {
    	return eleChecklistPopupSubmit;
    }*/
	
    @FindBy(xpath="//span[text()='Next']")
    private WebElement eleNext;
    public WebElement eleNext()
    {
    	return eleNext;
    }
    
    
  /* @FindBy(xpath= "//*[text()='Submit'][2]")
    private WebElement eleSubmitbutton;
    public WebElement eleSubmitbutton()
    {
    	return eleSubmitbutton;
    }*/
    
    
	@FindBy(xpath="//div[@class='x-component x-button x-button-no-icon x-button-button-tools-msgbox x-component-button-tools-msgbox x-button-button-highlight x-component-button-highlight svmx-msg-button-default x-haslabel x-layout-box-item x-layout-hbox-item x-flexed x-stretched']/span[text()='Submit']")
	private WebElement eleChecklistPopupSubmit;
    public WebElement eleChecklistPopupSubmit()
    {
    	return eleChecklistPopupSubmit;
    }
    
    @FindBy(xpath="//span[text()='Show completed checklists']")
    private WebElement eleShowCompletedChecklist;
    public WebElement eleShowCompletedChecklist()
    {
    	return eleShowCompletedChecklist;
    }
    
    private WebElement eleCompletedChecklistName;
	public WebElement eleCompletedChecklistName(String checklistname)
	{
		
		eleCompletedChecklistName = driver.findElement(By.xpath("(//div[@class='checklist-lineup-list-item-name'][text()='"+checklistname+"'])[2]"));
		return eleCompletedChecklistName;
	}
    
}

