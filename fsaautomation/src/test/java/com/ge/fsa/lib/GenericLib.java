
package com.ge.fsa.lib;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;


public class GenericLib 
{
	public static String sDirPath = System.getProperty("user.dir");
	public static String sDataFile =  sDirPath+"//..//Executable//data.properties";
	public static String sShellFile = sDirPath+"//..//Executable//sahiExecutable.sh";
	public static String sResources = sDirPath+"//resources";
	public static String sConfigFile =  sResources+"//select_config_file.properties";//sResources+"//config_local.properties";
	public static String sTestDataFile = sResources + "//TestData.xlsx";
	public static String sConfigPropertiesExcelFile = sResources + "//config_properties.xlsx";
	public static int iVHighSleep = 10000;
	public static int i30SecSleep = 10000;
	public static int iHighSleep = 8000;
	public static int iMedSleep = 5000;
	public static int iLowSleep = 2000;
	public static int iProcessStatus=0;
	public static long lWaitTime=2000;
	public static String sAppBundleID = BaseLib.sApp_BundleID;
	ProcessBuilder processBuilder = null;
	Process process=null;
	
	/*
	 * @author: LAKSHMI BS Description: To read the basic environment settings from dc_config.properties
	 * data from config file
	 */
	public static String getConfigValue(String sFile, String sKey) {
		Properties prop = new Properties();
		String sValue = null;
		try {
			InputStream input = new FileInputStream(sFile);
			prop.load(input);
			sValue = prop.getProperty(sKey);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sValue;
	}

	/*
	 * @author: LAKSHMI BS Description: To set the settings data in dc_config.properties
	 * data from config file
	 */
	public static void setConfigValue(String sFile, String sKey, String sValue) {
		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(new File(sFile));
			prop.load(fis);
			fis.close();

			FileOutputStream fos = new FileOutputStream(new File(sFile));
			prop.setProperty(sKey, sValue);
			prop.store(fos, "Updating folder path");
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/*
	 * @author: LAKSHMI BS Description: To read test data from excel sheet
	 */
	public static String getExcelData(String sTestCaseID, String sSheetName, String sKey) throws IOException {
		String sData = null;
		FileInputStream fis = new FileInputStream(sTestDataFile);
		
		try {

			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sSheetName);
			int iRowNum = sht.getLastRowNum();
			int k = 0;
			for (int i = 1; i <= iRowNum; i++) {
				
				if (sht.getRow(i).getCell(0).toString().equals(sTestCaseID)) {
					int iCellNum = sht.getRow(i).getLastCellNum();
					
					for(int j=0;j<iCellNum;j++)
					{
						if(sht.getRow(i).getCell(j).getStringCellValue().equals(sKey))
							{sData = sht.getRow(i+1).getCell(j).getStringCellValue();break;}
							
					}
					
					break;
				}
			}
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fis.close();
		return sData;
	}
	
	
	public void createShellFile(String sSahiFile) throws IOException
	{
		File file = new File(sShellFile);
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write("#!/bin/bash \ncd /auto/sahi_pro/userdata/bin \n./testrunner.sh /auto/sahi_pro/userdata/scripts/Sahi_Project_Lightning/svmx/test_lab/test_cases/" + sSahiFile + " https://test.salesforce.com chrome");
		writer.flush();
		writer.close();
	}
	
	
	public void executeSahiScript(String sSahiScript, String... sTestCaseID ) throws Exception
	{	
		
		String sMessage = sTestCaseID.length > 0 ? sTestCaseID[0] : sSahiScript;
		String sSahiLogPath = "/auto/sahi_pro/userdata/scripts/Sahi_Project_Lightning/offlineSahiLogs/";
		//If not triggered from jenkins use local path
		String sActualLogPath = System.getenv("Run_On_Platform") == null ? "/auto/sahi_pro/userdata/scripts/Sahi_Project_Lightning/offlineSahiLogs/" :  "offlineSahiLogs/" ;
		
		System.out.println("Executing Sahi Pro Script : "+sSahiScript);
		//Create Shell script to execute Sahi file
		createShellFile(sSahiScript);
		File file = new File(GenericLib.sShellFile);
		Runtime.getRuntime().exec("chmod u+x " +file);
		
		try {
			processBuilder= new ProcessBuilder(file.getPath());
			process = processBuilder.start(); // Start the process.
			process.waitFor(); // Wait for the process to finish.
			
			Assert.assertTrue(process.exitValue()==0, "Sahi script Passed");
			//Set the path  of sahi reports
			
			if(BaseLib.sSelectConfigPropFile.equalsIgnoreCase("automation_build") || BaseLib.sSelectConfigPropFile.equalsIgnoreCase("fsa_track_build")) {
				//first reference the log file name from local path ,then build a path for the build machine where we archive these logs
				sActualLogPath = sActualLogPath+getLastModifiedFile(sSahiLogPath, "*__*.","html");

			}else {
				sActualLogPath = sActualLogPath+getLastModifiedFile(sSahiLogPath, "*__*.","html");

			}
			
			ExtentManager.logger.log(Status.PASS,"Sahi script [ <a href='"+sActualLogPath+" '>"+sMessage+" </a> ] executed successfully");
				
		} catch (Exception e) {
			//Assert.assertTrue(iProcessStatus==0, "Sahi executed successfully");
			ExtentManager.logger.log(Status.FAIL,"Sahi script [ <a href='"+sActualLogPath+" '>"+sMessage+" </a> ] failed");
			throw e;
		}
	}
	
	/*
	 * @author: LAKSHMI BS Description: To write test data from excel sheet
	 */
	public static void setExcelData(String sTestCaseID, String sKey, String sValue) throws IOException {
		String sData = null;
		FileInputStream fis = null;
		FileOutputStream fos =null;
		try {

			fis = new FileInputStream(sTestDataFile);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet("sTestCaseID");
			int iRowNum = sht.getLastRowNum();
			int k = 0;
			for (int i = 1; i <= iRowNum; i++) {
				if (sht.getRow(i).getCell(0).toString().equals(sTestCaseID)) {
					int iCellNum = sht.getRow(i).getLastCellNum();
					
					for(int j=0;j<iCellNum;j++)
					{
						if(sht.getRow(i).getCell(j).getStringCellValue().equals(sKey))
							{//sData = sht.getRow(i+1).getCell(j).getStringCellValue();}
							sht.getRow(i+1).createCell(j).setCellValue(sValue);
							}
					}
					break;
				}
			}
			fos = new FileOutputStream(sTestDataFile);
			wb.write(fos);
			
			wb.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fos.close();
		
	}
	
	
	
	/**
	 * Return the last modified file name for a specific extension
	 * 
	 * @param filePath
	 * @param pattern
	 * @param ext
	 * @return
	 */
	public String getLastModifiedFile(String filePath, String pattern, String ext) {
		File lastModifiedFile = null;
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter(pattern + ext);
		File[] files = dir.listFiles(fileFilter);

		if (files.length > 0) {
			// The newest file comes first after sorting
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			lastModifiedFile = files[0];
		}

		System.out.println("Fetching Last Modified File : " + lastModifiedFile.getName().toString());
		return lastModifiedFile.getName().toString();
	}
	
	
	public static String readExcelData(String sFilePath, String sSheetName, String sKey) throws IOException {
		String sData = null;
		FileInputStream fis = new FileInputStream(sFilePath);
		
		try {

			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sSheetName);
			int iRowNum = sht.getLastRowNum();
			int k = 0;
			for (int i = 0; i <= iRowNum; i++) {
				
				//if (sht.getRow(i).getCell(0).toString().equals(sTestCaseID)) {
					int iCellNum = sht.getRow(i).getLastCellNum();
					
					for(int j=0;j<iCellNum;j++)
					{
						if(sht.getRow(i).getCell(j).getStringCellValue().equals(sKey))
							{sData = sht.getRow(i+1).getCell(j).toString();
							break;
							}
							
					}
					
					break;
				//}
			}
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fis.close();
		return sData;
	}
	
	
	public static void writeExcelData(String sFilePath, String sSheetName, String sKey, String sValue) throws IOException {
		String sData = null;
		FileInputStream fis = null;
		FileOutputStream fos =null;
		try {

			fis = new FileInputStream(sFilePath);
			Workbook wb = (Workbook) WorkbookFactory.create(fis);
			Sheet sht = wb.getSheet(sSheetName);
			int iRowNum = sht.getLastRowNum();
			int k = 0;
			for (int i = 0; i <= iRowNum; i++) {
				int iCellNum = sht.getRow(i).getLastCellNum();
					
					for(int j=0;j<iCellNum;j++)
					{
						//System.out.println("row"+i+"  column"+j);
						if(sht.getRow(i).getCell(j).getStringCellValue().equals(sKey))
							{//sData = sht.getRow(i+1).getCell(j).getStringCellValue();}
							sht.getRow(i+1).createCell(j).setCellValue(sValue);
							break;
							}
					}
					break;
				
			}
			fos = new FileOutputStream(sFilePath);
			wb.write(fos);
			
			wb.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fos.close();
		
	}
}
