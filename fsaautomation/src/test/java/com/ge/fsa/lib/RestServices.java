/*
 *  @author lakshmibs
 */
package com.ge.fsa.lib;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import com.ge.fsa.lib.GenericLib;

import groovy.json.JsonParser;
import net.bytebuddy.asm.Advice.Return;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import javax.net.ssl.HttpsURLConnection;

public class RestServices 
{
	private final static String USER_AGENT = "Mozilla/5.0";
	public static String sAccessToken = null;
	public static String sWORecordID = null;
	public static String sWorkOrderName = null;
	public RestServices appServices = null;
	
	
	//To Fetch Access Token
	public void getAccessToken() throws IOException
	{
		URL url = new URL(GenericLib.getCongigValue(GenericLib.sConfigFile, "OAUTH_URL"));
        HttpsURLConnection httpsUrlCon = (HttpsURLConnection) url.openConnection();
		httpsUrlCon.setRequestMethod("POST");
		httpsUrlCon.setRequestProperty("User-Agent", USER_AGENT);
		httpsUrlCon.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		String urlParameters = "grant_type=password"
				+ "&client_id="+GenericLib.getCongigValue(GenericLib.sConfigFile, "CLIENT_ID")
				+ "&client_secret="+GenericLib.getCongigValue(GenericLib.sConfigFile, "CLIENT_SECRET")
				+ "&username="+GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_USN")
				+ "&password="+GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_PWD");
		httpsUrlCon.setDoOutput(true);
		
		DataOutputStream dataOpStream = new DataOutputStream(httpsUrlCon.getOutputStream());
		dataOpStream.writeBytes(urlParameters);
		dataOpStream.flush();
		dataOpStream.close();
		int responseCode = httpsUrlCon.getResponseCode();
		BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        try {
               bufferedReader = new BufferedReader(new InputStreamReader(httpsUrlCon.getInputStream(),  StandardCharsets.UTF_8));
               while ((line =bufferedReader.readLine())!=null){
                     //TOOL-1905 and TOOL-1906 not validating String(line) 
                     stringBuilder.append(line);
                     
               }
        } catch (IOException e) {
               e.printStackTrace();
        } finally {
               if (bufferedReader != null) {
                     try {
                            bufferedReader.close();
                     } catch (IOException e) {
                            e.printStackTrace();
                     }
               }
        }
        JSONObject json = new JSONObject(stringBuilder.toString());
		sAccessToken = (String) json.get("access_token");
		System.out.println("AccessToken: "+sAccessToken);
		httpsUrlCon.disconnect();
		
	}
	
	
	
	/**
	 * Method to return the JSONArray, to be used with getJsonValue() method for multiple value extraction after a single API call
	 * 
	 *  * usage: 
	  * jsonArray = restServices.restGetSoqlJsonArray(soqlquery);
	  *
	  * getJsonValue( jsonArray, sfieldName1)
	  * getJsonValue( jsonArray, sfieldName2)
	  * getJsonValue( jsonArray, sfieldName3)
	  * 
	 * @param soqlquery
	 * @return
	 * @throws IOException
	 */
	public JSONArray restGetSoqlJsonArray(String soqlquery ) throws IOException
	{
		soqlquery = parseQuery(soqlquery);
		String sURL = GenericLib.getCongigValue(GenericLib.sConfigFile, "WONAME_URL")+soqlquery;
		URL url = new URL(sURL);
		System.out.println(sURL);
		HttpsURLConnection httpsUrlCon = (HttpsURLConnection) url.openConnection();
		httpsUrlCon.setDoOutput(true);
		httpsUrlCon.setRequestMethod("GET");
		httpsUrlCon.setRequestProperty("Authorization", "OAuth "+sAccessToken);
		httpsUrlCon.setRequestProperty("Username",GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_USN") );
		httpsUrlCon.setRequestProperty("Password", GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_PWD"));
		String returnvalue = null;
		
		BufferedReader bufferedReader = null;
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		try {
		   bufferedReader = new BufferedReader(new InputStreamReader(httpsUrlCon.getInputStream(),StandardCharsets.UTF_8));
		   while ((line =bufferedReader.readLine())!=null){
		         stringBuilder.append(line);
		   }
		} catch (IOException e) {
		   e.printStackTrace();
		} finally {
		   if (bufferedReader != null) {
		         try {
		                bufferedReader.close();
		         } catch (IOException e) {
		                e.printStackTrace();
		         }
		   }
		

			}

		JSONObject json = new JSONObject(stringBuilder.toString());
		System.out.println(json);
		JSONArray msg = (JSONArray) json.get("records");
		return msg;
	
	}
  

	
	/**
	 * 
	 * This method will query other objects and will send the values which is requested by us
	 * 
	 * @param soqlquery
	 * @return
	 * @throws IOException
	 */
	
	
	public String restGetSoqlValue(String soqlquery , String getvalue) throws IOException
	{
		
		soqlquery = parseQuery(soqlquery);
		
		String sURL = GenericLib.getCongigValue(GenericLib.sConfigFile, "WONAME_URL")+soqlquery;
		URL url = new URL(sURL);
		System.out.println(sURL);
		HttpsURLConnection httpsUrlCon = (HttpsURLConnection) url.openConnection();
		httpsUrlCon.setDoOutput(true);
		httpsUrlCon.setRequestMethod("GET");
		httpsUrlCon.setRequestProperty("Authorization", "OAuth "+sAccessToken);
		httpsUrlCon.setRequestProperty("Username",GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_USN") );
		httpsUrlCon.setRequestProperty("Password", GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_PWD"));
		String returnvalue = null;
		
		BufferedReader bufferedReader = null;
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		try {
		   bufferedReader = new BufferedReader(new InputStreamReader(httpsUrlCon.getInputStream(),StandardCharsets.UTF_8));
		   while ((line =bufferedReader.readLine())!=null){
		         stringBuilder.append(line);
		   }
		} catch (IOException e) {
		   e.printStackTrace();
		} finally {
		   if (bufferedReader != null) {
		         try {
		                bufferedReader.close();
		         } catch (IOException e) {
		                e.printStackTrace();
		         }
		   }
		

			}


		JSONObject json = new JSONObject(stringBuilder.toString());
		System.out.println(json);
		if(getvalue == "totalSize")
		{
		String msgString = json.get(getvalue).toString();
	 	System.out.println("Returned value for "+getvalue+" = "+msgString);
		returnvalue= msgString;
		
		}
		else
		{
		JSONArray msg = (JSONArray) json.get("records");
		Iterator iterator = msg.iterator();
		while (iterator.hasNext()) {
	         JSONObject value = (JSONObject) iterator.next();
	         returnvalue= (String) value.get(getvalue).toString();
		}
		}
	 	System.out.println("Returned value for "+getvalue+" = "+returnvalue);
		return returnvalue;
		}
		
	 /**
	  * Get the value from any JsonArray returned
	  * usage: 
	  * JSONArray jsonArrayAcc = restServices.restCreate("SVMXC__Company__c?","{\"Name\": \""+accName+"\" }");
	  *
	  * getJsonValue( jsonArrayAcc, id)
	  * getJsonValue( jsonArrayAcc, sfieldName2)
	  * getJsonValue( jsonArrayAcc, sfieldName3)... etc
	  * 
	  * @param jsonArray
	  * @param sfieldName
	  * @return String
	  */
	 public String getJsonValue(JSONArray jsonArray,String sfieldName) {
		 String fieldValueObtained=null;
			Iterator iterator = jsonArray.iterator();
			while (iterator.hasNext()) {
		         JSONObject value = (JSONObject) iterator.next();
		         System.out.println((String) value.get(sfieldName).toString());
		         
		         fieldValueObtained= (String) value.get(sfieldName).toString();
		     }
		 	System.out.println("Returned value for "+sfieldName+" = "+fieldValueObtained);

	     return fieldValueObtained;
		 
	 }

	 /**
	  * Create a new record data by passing the so-object name and JSON body which returns a "id" by default that can be used in other queries, (use getJsonValue() to get the appropriate values)
	  * 
	  * Usage:
	  * For a Workorder pass:
	  * sSoObjectName = "SVMXC__Service_Order__c?"
	  * sWOJson = "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
	  * 
	  * @param sSoObjectName
	  * @param sWOJson
	  * @return JSONArray
	  * @throws IOException
	  */
	 public  String restCreate(String sSoObjectName,String sWOJson) throws IOException
	 {
		 getAccessToken();
	 	URL url = new URL(GenericLib.getCongigValue(GenericLib.sConfigFile, "CREATE_URL")+sSoObjectName
	 			+ "Username="+GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_USN")
	 			+ "&Password="+GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_PWD"));
	     HttpsURLConnection httpsUrlCon = (HttpsURLConnection) url.openConnection();
	     httpsUrlCon.setDoOutput(true);
	  	httpsUrlCon.setRequestMethod("POST");
	 	httpsUrlCon.setRequestProperty("Content-Type", "application/json");
	 	httpsUrlCon.setRequestProperty("Authorization", "OAuth "+sAccessToken);
	 	
	 	System.out.println("httpsUrlCon = "+httpsUrlCon);
	 	OutputStream os = httpsUrlCon.getOutputStream();
	     os.write(sWOJson.getBytes());
	     os.flush();
	 	
	     BufferedReader bufferedReader = null;
	     StringBuilder stringBuilder = new StringBuilder();
	     String line;
	     try {
	            bufferedReader = new BufferedReader(new InputStreamReader(httpsUrlCon.getInputStream()));
	            while ((line =bufferedReader.readLine())!=null){
	                  stringBuilder.append(line);
	            }
	     } catch (IOException e) {
	            e.printStackTrace();
	     } finally {
	            if (bufferedReader != null) {
	                  try {
	                         bufferedReader.close();
	                  } catch (IOException e) {
	                         e.printStackTrace();
	                  }
	            }
	     }


	    JSONObject json = new JSONObject(stringBuilder.toString());
	 	System.out.println("JSON value = "+json);
	 	String msg = (String) json.get("id");
	 	System.out.println("Returning ID value = "+msg);

		return msg;
	 }
	 
	 /**
	  * Return a new query after adding \"+\" to spaces
	  * @param query
	  * @return
	  */
	 public String parseQuery(String query) {
			query = query.replaceAll("\\s","+");
			System.out.println("New query after adding \"+\" to spaces = "+query);
			return query;
		}

//		/**
//		 * 
//		 * @param args
//		 * @throws IOException
//		 */
//		public static void main(String[] args) throws IOException {
//			RestServices appServices = new RestServices();
//
//			appServices.getAccessToken();
//
//			String sWOJsonData = "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";
//
//			String woNum = appServices.getWOName(appServices.getWOORecordID(sWOJsonData));
//			System.out.println("WO NUMBER FETCHED " + woNum);
//		
//		}
		
}