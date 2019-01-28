
package com.ge.fsa.lib;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
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
	public static String sConfigFile =  sResources+"//config.properties";
	public static String sTestDataFile = sResources + "//TestData.xlsx";
	public static int iVHighSleep = 60000;
	public static int i30SecSleep = 30000;
	public static int iHighSleep = 8000;
	public static int iMedSleep = 5000;
	public static int iLowSleep = 2000;
	public static int iProcessStatus=0;
	public static long lWaitTime=0L;
	public static String sAppBundleID = "com.servicemaxinc.svmxfieldserviceapp";
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
	
	
	public void executeSahiScript(String sSahiScript, String sTestCaseID ) throws Exception
	{	
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
			ExtentManager.logger.log(Status.PASS,"Sahi script for case "+sTestCaseID+" executed successfully");
				
		} catch (Exception e) {
			//Assert.assertTrue(iProcessStatus==0, "Sahi executed successfully");
			ExtentManager.logger.log(Status.FAIL,"Testcase " + sTestCaseID + "Sahi execution failure");
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
	
//	public static void main(String[] args) throws IOException {
//		GenericLib gen = new GenericLib();
//		GenericLib.setExcelData("RS_10543","RS_10543", "ExploreSearch");
//		//GenericLib.getExcelData("RS_10543","RS_10543", "ExploreSearch");
//	}
	
}
