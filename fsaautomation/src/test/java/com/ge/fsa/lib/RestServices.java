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
	
//To Fetch Work Order ID
public  String getWOORecordID(String sWOJson) throws IOException
{
	URL url = new URL(GenericLib.getCongigValue(GenericLib.sConfigFile, "WORECORD_URL")
			+ "Username="+GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_USN")
			+ "&Password="+GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_PWD"));
    HttpsURLConnection httpsUrlCon = (HttpsURLConnection) url.openConnection();
    httpsUrlCon.setDoOutput(true);
 	httpsUrlCon.setRequestMethod("POST");
	httpsUrlCon.setRequestProperty("Content-Type", "application/json");
	httpsUrlCon.setRequestProperty("Authorization", "OAuth "+sAccessToken);
	
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
    sWORecordID = (String) json.get("id");
    System.out.println("WORecordID: "+sWORecordID);
    return sWORecordID;
}

	// To fetch WorkOrder Name
	public String  getWOName(String sWORecordID) throws IOException
	{
	String sURL = GenericLib.getCongigValue(GenericLib.sConfigFile, "WONAME_URL")+"SELECT+name+from+SVMXC__Service_Order__c+Where+id+=\'"+sWORecordID+"\'";
	URL url = new URL(sURL);
	System.out.println(sURL);
	HttpsURLConnection httpsUrlCon = (HttpsURLConnection) url.openConnection();
	httpsUrlCon.setDoOutput(true);
	httpsUrlCon.setRequestMethod("GET");
	httpsUrlCon.setRequestProperty("Authorization", "OAuth "+sAccessToken);
	httpsUrlCon.setRequestProperty("Username",GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_USN") );
	httpsUrlCon.setRequestProperty("Password", GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_PWD"));
	
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
	
	JSONArray msg = (JSONArray) json.get("records");
	Iterator iterator = msg.iterator();
	while (iterator.hasNext()) {
         JSONObject value = (JSONObject) iterator.next();
         System.out.println((String) value.get("Name"));
         
         sWorkOrderName=(String) value.get("Name");
     }
	
	return sWorkOrderName;
	}
  

	// To pass the required SOQL and then grab the data
	/**
	 * Author : Meghana Rao
	 * @param soqlquery - This method will query other objects and will send the values which is requested by us
	 * @return
	 * @throws IOException
	 */
	
	public String restsoql(String soqlquery , String getvalue) throws IOException
	{
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
		
		JSONArray msg = (JSONArray) json.get("records");
		Iterator iterator = msg.iterator();
		while (iterator.hasNext()) {
	         JSONObject value = (JSONObject) iterator.next();
	         System.out.println((String) value.get(getvalue));
	         
	         returnvalue=(String) value.get(getvalue);
	     }
		
		
		

		return returnvalue;
		}
		
		

		public String restapisoql(String soqlquery) throws IOException {		
		String sURL = GenericLib.getCongigValue(GenericLib.sConfigFile, "WONAME_URL")+soqlquery;
		URL url = new URL(sURL);
		System.out.println(sURL);
		HttpsURLConnection httpsUrlCon = (HttpsURLConnection) url.openConnection();
		httpsUrlCon.setDoOutput(true);
		httpsUrlCon.setRequestMethod("GET");
		httpsUrlCon.setRequestProperty("Authorization", "OAuth "+sAccessToken);
		httpsUrlCon.setRequestProperty("Username",GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_USN") );
		httpsUrlCon.setRequestProperty("Password", GenericLib.getCongigValue(GenericLib.sConfigFile, "ADMIN_PWD"));
		
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
		
		JSONArray msg = (JSONArray) json.get("records");
		Iterator iterator = msg.iterator();
		while (iterator.hasNext()) {
	         JSONObject value = (JSONObject) iterator.next();
	         System.out.println((String) value.get("Name"));
	         
	         sWorkOrderName=(String) value.get("Name");
	     }
		
		
		
		return sWorkOrderName;
		}
		
		
		public static void main(String[] args) throws IOException {
			RestServices appServices = new RestServices();

			appServices.getAccessToken();

			String sWOJsonData = "{\"SVMXC__City__c\":\"Delhi\",\"SVMXC__Zip__c\":\"110003\",\"SVMXC__Country__c\":\"India\",\"SVMXC__State__c\":\"Haryana\"}";

			String woNum = appServices.getWOName(appServices.getWOORecordID(sWOJsonData));
			System.out.println("WO NUMBER FETCHED " + woNum);
		
		}
		
}