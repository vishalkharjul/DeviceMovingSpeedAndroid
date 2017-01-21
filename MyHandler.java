package com.example.first;
import com.datastax.driver.core.Cluster;
import java.util.*;
import java.text.*;
import com.datastax.driver.core.Session;
import org.json.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MyHandler implements HttpHandler {
	

	public void handle(HttpExchange t) throws IOException {
	 String response = "hello world";
	 JSONObject json ; 
	 BufferedReader inp = new BufferedReader(new InputStreamReader(t.getRequestBody()));
	 StringBuilder responseStrBuilder = new StringBuilder();

	 String inputStr;
	 while ((inputStr = inp.readLine()) != null)
		 	responseStrBuilder.append(inputStr);
		//System.out.println(responseStrBuilder);
	 try {
		json = new JSONObject(responseStrBuilder.toString());
		//System.out.println(json.toString());
		Cluster cluster = Cluster.builder()
                   .addContactPoints("127.0.0.1")
                   .build();
		Session session = cluster.connect();
		   //		Session session= cass.dbConnect();
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
		if (json.getInt("bus_speed") != 0)
		{

			String cqlStatement = "INSERT INTO devicetrack.bus_by_routeno_state (Busno,routeno,state,event_time,date) values ("+json.getInt("busno")+","+json.getInt("routeno")+","+"'Running',now()"+",'"+ft.format(date)+"'"+");";
			System.out.println(cqlStatement);
			session.execute(cqlStatement);
			String cqlStatement1 = "INSERT INTO devicetrack.bus_location_by_busno (Busno,date,event_time,bus_speed,lattitude,longitude,loc_acc) values ("+json.getInt("busno")+",'"+ft.format(date)+"',"+"now(),"+json.getInt("bus_speed")+","+json.getDouble("latitude")+","+json.getDouble("longitude")+","+json.getInt("loc_acc")+");";
			System.out.println(cqlStatement1);
			session.execute(cqlStatement1);
		} else {
		
			String cqlStatement = "INSERT INTO devicetrack.bus_by_routeno_state (Busno,routeno,state,event_time,date) values ("+json.getInt("busno")+","+json.getInt("routeno")+","+"'Stopped',now()"+",'"+ft.format(date)+"'"+");";
			System.out.println(cqlStatement);
			session.execute(cqlStatement);
			String cqlStatement1 = "INSERT INTO devicetrack.bus_location_by_busno (Busno,date,event_time,bus_speed,lattitude,longitude,loc_acc) values ("+json.getInt("busno")+",'"+ft.format(date)+"',"+"now(),"+json.getInt("bus_speed")+","+json.getDouble("latitude")+","+json.getDouble("longitude")+","+json.getInt("loc_acc")+");";
			System.out.println(cqlStatement1);
			session.execute(cqlStatement1);
		}

		session.close();
		cluster.close();

	} catch (JSONException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 		 
     t.sendResponseHeaders(200, response.length());
 //    System.out.println(response);
     OutputStream os = t.getResponseBody();
     os.write(response.getBytes());
     os.close();

}
}