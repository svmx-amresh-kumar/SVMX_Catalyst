package com.ge.fsa.tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import org.testng.annotations.Test;

public class trial {
	@Test(enabled = true)
	public  void main() {
 
		/*Date today = Calendar.getInstance().getTime();
 
		// Constructs a SimpleDateFormat using the given pattern
		SimpleDateFormat crunchifyFormat = new SimpleDateFormat("MMM dd yyyy HH:mm:ss.SSS zzz");
 
		// format() formats a Date into a date/time string.
		String currentTime = crunchifyFormat.format(today);
		log("Current Time = " + currentTime);
 
		try {
 
			// parse() parses text from the beginning of the given string to produce a date.
			Date date = crunchifyFormat.parse(currentTime);
 
			// getTime() returns the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this Date object.
			long epochTime = date.getTime();
 
			log("Current Time in Epoch: " + epochTime);
 
		} catch (ParseException e) {
			e.printStackTrace();
		}
 
		// Local ZoneID
		ZoneId defaultZoneId = ZoneId.systemDefault();
		log("defaultZoneId: " + defaultZoneId);
 
		Date date = new Date();
 
		// Default Zone: UTC+0
		Instant instant = date.toInstant();
		System.out.println("instant : " + instant);
		 
		
		
		// Local TimeZone
		LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
		System.out.println("localDateTime : " + localDateTime);
 
	}
 
	// Simple logging
	private static void log(String string) {
		System.out.println(string);
 
	}*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		 Calendar now = Calendar.getInstance();
	        now.set(Calendar.HOUR, 0);
	        now.set(Calendar.MINUTE, 0);
	        now.set(Calendar.SECOND, 0);
	        System.out.println(sdf.format(now.getTime()));
	        now.set(Calendar.HOUR_OF_DAY, 0);
	     String  timezero=  sdf.format(now.getTime());
	     System.out.println(timezero);
}
	
}	