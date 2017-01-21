package com.example.first;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.twilio.sdk.verbs.Message;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;

public class SmsHandler   implements HttpHandler{
	public void handle(HttpExchange t) throws IOException {
         String str="hi";
		 BufferedReader inp = new BufferedReader(new InputStreamReader(t.getRequestBody()));
		 StringBuilder responseStrBuilder = new StringBuilder();

		 String inputStr;
		 while ((inputStr = inp.readLine()) != null)
			 	responseStrBuilder.append(inputStr);
		 
			//System.out.println(responseStrBuilder);
		 TwiMLResponse twiml = new TwiMLResponse();
	     Message message = new Message("You sent me" + str);
	     try {
	         twiml.append(message);
	     } catch (TwiMLException e) {
	         e.printStackTrace();
	     }

 		 
	     	t.sendResponseHeaders(200, twiml.toString().length());
	     	//System.out.println(response);
	     	OutputStream os = t.getResponseBody();
	     	os.write(twiml.toXML().getBytes());
	     	os.close();
		}
}
