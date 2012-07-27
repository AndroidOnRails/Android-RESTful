package com.connection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class Connection extends Activity {
    
	private final static String SERVICE_URI = "http://immense-escarpment-5166.herokuapp.com";
	String tempString = "";
	Vector<String> vectorOfStrings = new Vector<String>();
	int orderCount;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        HttpGet request = new HttpGet(SERVICE_URI + "/tasks");       
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        DefaultHttpClient httpClient = new DefaultHttpClient();

        String theString = new String("");
        
        try {
        	HttpResponse response = httpClient.execute(request);
            HttpEntity responseEntity = response.getEntity();
            // Read response data into buffer
            // char[] buffer = new char[(int)responseEntity.getContentLength()];
            InputStream stream = responseEntity.getContent();
            // InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(stream));
            
            // reader.read(buffer);

    
            
            // jsaPersons = new JSONArray(new String(buffer));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                            builder.append(line);
                    }
            stream.close();
            theString = builder.toString();
            // senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android
            
            // www.jondev.net/articles/Android_JSON_Parser_Example
            JSONObject json=new JSONObject(theString);
            // JSONObject raspunsEfectiv = json.getJSONObject("getJsonPersonsResult");
            // json.getJSONArray("");
            Log.i("_GetPerson_","<jsonobject>\n"+json.toString()+"\n</jsonobject>");
            // Log.i("JLSP ","<raspunsEfectiv>\n"+raspunsEfectiv.toString()+"\n</raspunsEfectiv>");
            
            // A Simple JSONObject Parsing
        // JSONArray nameArray=json.names();
            JSONArray nameArray=json.getJSONArray("tasks");
        // Log.i("JLSP ","<jsonNames>\n"+raspunsEfectiv.toString()+"\n</jsonNames>");
        // JSONArray valArray=json.toJSONArray(nameArray);

            for(int i=0;i<nameArray.length();i++) {
            	//Log.d("title", nameArray.getJSONObject(i).getString("title"));
            	//Log.d("description", nameArray.getJSONObject(i).getString("description"));
            	
            	tempString=nameArray.getJSONObject(i).getString("title")+" "+nameArray.getJSONObject(i).getString("description")+"\n";
            	vectorOfStrings.add(new String(tempString));
            	Toast.makeText(this, tempString, Toast.LENGTH_LONG).show();
            }
            orderCount = vectorOfStrings.size();
             
            
                    
        } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
        } 
        //Toast.makeText(this, tempString + "\n", Toast.LENGTH_LONG).show();
        //Toast.makeText(this, theString + "\n", Toast.LENGTH_LONG).show();
        
        String[] orderTimeStamps = new String[orderCount];
        vectorOfStrings.copyInto(orderTimeStamps);

        
    }
}