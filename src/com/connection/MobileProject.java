package com.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class MobileProject {
    
	private final static String SERVICE_URI = "http://murmuring-earth-4103.herokuapp.com/";
	Vector<String> vectorOfUsers = new Vector<String>();
    
    public String[] getUsers()
    {	
    	String tempString = "";
    	Vector<String> vectorOfStrings = new Vector<String>();
    	int orderCount = 0;
    	
    	HttpGet request = new HttpGet(SERVICE_URI + "/users");       
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        DefaultHttpClient httpClient = new DefaultHttpClient();

        String theString = new String("");
         
        try {
        	HttpResponse response = httpClient.execute(request);
            HttpEntity responseEntity = response.getEntity();

            InputStream stream = responseEntity.getContent();
             
            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(stream));
             
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
            	builder.append(line);
            }
            stream.close();
            theString = builder.toString();
        
            JSONObject json=new JSONObject(theString);

            JSONArray nameArray=json.getJSONArray("users");

            for(int i=0;i<nameArray.length();i++) {
            	JSONObject tmp = new JSONObject(nameArray.getJSONObject(i).getString("role"));
             	tempString = 	nameArray.getJSONObject(i).getInt("id")+" "+
             					nameArray.getJSONObject(i).getString("name")+" "+ 
             					tmp.getString("name")+" "+"\n";
             	vectorOfStrings.add(new String(tempString));
            }
            orderCount = vectorOfStrings.size();                              
         } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
         }  
         String[] orderTimeStamps = new String[orderCount];
         vectorOfStrings.copyInto(orderTimeStamps);        
    
         return orderTimeStamps;
    }
    
    public void postUser(String name, int role_id) throws JSONException
    {
    	DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(SERVICE_URI + "/users");

        try {
        	JSONObject jsonObjSend = new JSONObject();

        	jsonObjSend.put("name", name);
        	jsonObjSend.put("role_id", role_id);
        	
        	StringEntity se;
        	se = new StringEntity(jsonObjSend.toString());

        	// Set HTTP parameters
        	httppost.setEntity(se);
        	httppost.setHeader("Accept", "application/json");
        	httppost.setHeader("Content-type", "application/json");
		
        	long t = System.currentTimeMillis();
		
			HttpResponse response = (HttpResponse) httpclient.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void putUser(String name, int role_id, int user_id) throws JSONException
    {
    	DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPut httpput = new HttpPut(SERVICE_URI + "/users/"+user_id);

        try {
        	JSONObject jsonObjSend = new JSONObject();

        	jsonObjSend.put("name", name);
        	jsonObjSend.put("role_id", role_id);
        	
        	StringEntity se;
        	se = new StringEntity(jsonObjSend.toString());

        	// Set HTTP parameters
        	httpput.setEntity(se);
        	httpput.setHeader("Accept", "application/json");
        	httpput.setHeader("Content-type", "application/json");
		
        	long t = System.currentTimeMillis();
		
			HttpResponse response = (HttpResponse) httpclient.execute(httpput);
			StatusLine statusLine = response.getStatusLine();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void deleteUser(int user_id) throws JSONException
    {
    	DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpDelete httpdelete = new HttpDelete(SERVICE_URI + "/users/"+user_id);

        try {
        	JSONObject jsonObjSend = new JSONObject();

        	// Set HTTP parameters
        	httpdelete.setHeader("Accept", "application/json");
        	httpdelete.setHeader("Content-type", "application/json");
		
        	long t = System.currentTimeMillis();
		
			HttpResponse response = (HttpResponse) httpclient.execute(httpdelete);
			StatusLine statusLine = response.getStatusLine();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}