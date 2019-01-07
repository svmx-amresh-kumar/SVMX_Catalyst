package com.ge.fsa.lib;

import org.testng.IRetryAnalyzer;

import org.testng.ITestResult;

// implement IRetryAnalyzer interface

public class Retry implements IRetryAnalyzer {

	public static boolean isRetryRun = false;
	// set counter to 0

	int minretryCount = 0;

	// set maxcounter value this will execute our test 3 times

	int maxretryCount = 1;

	// override retry Method

	public boolean retry(ITestResult result) {

		// this will run until max count completes if test pass within this frame it
		// will come out of for loop

		if (minretryCount < maxretryCount)

		{

			System.out.println("<< Retrying failed test : " + result.getName());

			minretryCount++;
			//Set the is isRetryRun flag to true
			isRetryRun = true;
			
			return true;

		}

		return false;

	}

}
