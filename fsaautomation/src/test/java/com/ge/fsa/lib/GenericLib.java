
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
