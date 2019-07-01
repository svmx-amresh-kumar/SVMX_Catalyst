
package com.ge.fsa.lib;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
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
	public static int iAttachmentSleep = 120000;
	public static int iProcessStatus=0;
	public static long lWaitTime=2000;
	public static String sAppBundleID = BaseLib.sApp_BundleID;
	ProcessBuilder processBuilder = null;
	Process process=null;
	
	

	
	/*
	 * @author: LAKSHMI BS Description: To read test data from excel sheet
	 */
//	public static String getExcelData(String sTestCaseID, String sSheetName, String sKey) throws IOException {
//		String sData = null;
//		FileInputStream fis = new FileInputStream(sTestDataFile);
//		
//		try {
//
//			Workbook wb = (Workbook) WorkbookFactory.create(fis);
//			Sheet sht = wb.getSheet(sSheetName);
//			int iRowNum = sht.getLastRowNum();
//			int k = 0;
//			for (int i = 1; i <= iRowNum; i++) {
//				
//				if (sht.getRow(i).getCell(0).toString().equals(sTestCaseID)) {
//					int iCellNum = sht.getRow(i).getLastCellNum();
//					
//					for(int j=0;j<iCellNum;j++)
//					{
//						if(sht.getRow(i).getCell(j).getStringCellValue().equals(sKey))
//							{sData = sht.getRow(i+1).getCell(j).getStringCellValue();break;}
//							
//					}
//					
//					break;
//				}
//			}
//			wb.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		fis.close();
//		return sData;
//	}
//	
	

}
