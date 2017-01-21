package com.example.first;
import com.datastax.driver.core.Cluster;
import org.json.*;
import java.util.Date;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.*;
import com.datastax.driver.core.*;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
//import java.sql.*;

class VehicleTrack {
	Session session ;
	Cluster cluster ;
	Date date = new Date();
	SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
	
	public static void main(String[] args) throws IOException {
		VehicleTrack vehicleTrack = new VehicleTrack();
	
	
		//System.out.println("O/p is " +vehicleTrack.calculate_TimetoReachStop(null,null));
		vehicleTrack.get_distance_busandstop();
	}
	
	VehicleTrack(){
		
	}
	
	@SuppressWarnings("null")
	public void get_distance_busandstop()
	{
		    JSONObject json ;
		    JSONObject [] leg ;
		    JSONObject route ;
	        String start = "33.957443,-84.354277";
	        String finish = "33.933807,-84.354143";
	        int [] trdistances = new int[20];
	        int sum=0;

	        String region = "in";
	       // String Waypoints ="19.95201,73.83996|19.95679,73.83371|19.95885,73.83144|19.96177,73.82736|19.9643,73.824|19.96889,73.8184|19.97397,73.81261|19.97612,73.81057|19.98049,73.80635|19.98547,73.80188|19.98881,73.79974|19.99045,73.7992|19.99334,73.79722|19.99607,73.7882|20.00051,73.7868|20.00122,73.78257";
	        String Waypoints ="33.947621,-84.355016";
	        String urlString = "https://maps.googleapis.com/maps/api/directions/json?sensor=false&origin="+start+"&destination="+finish+"&waypoints="+Waypoints+"&language=en-EN&key=AIzaSyAxLunPd8wdgzwGEIU5Im8M0z-m9cIAye8";
	        System.out.println(urlString);
	        try{
	        	URL urlGoogleDirService = new URL(urlString);
	        	HttpURLConnection urlGoogleDirCon = (HttpURLConnection)urlGoogleDirService.openConnection();
	        	urlGoogleDirCon.setAllowUserInteraction( false );
	            urlGoogleDirCon.setDoInput( true );
	            urlGoogleDirCon.setDoOutput( false );
	            urlGoogleDirCon.setUseCaches( true );
	            urlGoogleDirCon.setRequestMethod("GET");
	            urlGoogleDirCon.connect();            
	            BufferedReader inp = new BufferedReader(new InputStreamReader(urlGoogleDirCon.getInputStream()));
	            StringBuilder responseStrBuilder = new StringBuilder();

	       	 	String inputStr;
	       	 	while ((inputStr = inp.readLine()) != null)
	       	 			responseStrBuilder.append(inputStr);
	       	 	
	       	   //System.out.println(responseStrBuilder);
	       	   json = new JSONObject(responseStrBuilder.toString());      	   
	       	   JSONArray array = json.getJSONArray("routes");
	       	   //Log.d("JSON","array: "+array.toString());
	       	   //Routes is a combination of objects and arrays
	       	   JSONObject routes = array.getJSONObject(0);
	       	   JSONArray legs = routes.getJSONArray("legs");
	       	   
	       	 //JSONObject distance = legs.getJSONObject(0);
	       	for (int i = 0; i < legs.length(); i++) {
                //this works fine and displays properly
               // = legs.getJSONArray(i).getJSONObject("distance").getString("text");
                //I want to store to this array so that I can sort
	       		sum = sum + legs.getJSONObject(i).getJSONObject("distance").getInt("value");
	       		trdistances[i] = sum;
	     	   System.out.println(" " + trdistances[i] );
            }
	      
	       	   
	       
	       	 
	       	    
	            urlGoogleDirCon.disconnect();
	            
	        	
	        }catch(Exception e)
	        {
	        	System.out.println(e);
	        }
	}
	
	public int calculate_TimetoReachStop(String srouteno,String sstopno)
	{   
		int routeno; 
		int stopno;
		int busno=1;
	    double bus_lattitude=0.0;
	    double bus_longitude=0.0;
	    double stop_lattitude=0.0;
	    double stop_longitude=0.0;
		if (srouteno == null)
		{
			System.out.println("I am null");
			routeno = 2;
			stopno=1;
			
		}else
		{
			System.out.println("I am not null");
			routeno = Integer.parseInt(srouteno);
			stopno =  Integer.parseInt(sstopno);
			
		}
			
		try {
		Cluster cluster = Cluster.builder()
                .addContactPoints("127.0.0.1")
                .build();
		Session session = cluster.connect();
		
	
		ResultSet results = session.execute("select busno from devicetrack.bus_by_routeno_state where routeno="+routeno+" and date='08/04/2016'  and state='Running' limit 1;");
		for (Row row : results) {
			System.out.println("In Resultset 1");
			System.out.println("select busno from devicetrack.bus_by_routeno_state where routeno="+routeno+" and date='08/04/2016'  and state='Running' limit 1;");
		    System.out.println(String.format(" "+row.getInt("busno")));	
		    busno = row.getInt("busno")  ;
		}
		ResultSet results2 = session.execute("select lattitude, longitude from devicetrack.bus_location_by_busno where busno="+busno+" and date='08/04/2016' limit 1;");
		for (Row row : results2) {
			System.out.println("In Resultset 2");
			System.out.println("select lattitude, longitude from devicetrack.bus_location_by_busno where busno="+busno+" and date='08/04/2016' limit 1;");
		 //   System.out.println(String.format(" "+row.getInt("busno")));	
		    bus_lattitude = row.getDouble("lattitude")  ;
		    bus_longitude = row.getDouble("longitude")  ;
		}
		ResultSet results3 = session.execute("select lattitude, longitude from devicetrack.routebystops where routeno="+routeno+" and stopno="+stopno+" limit 1;");
		for (Row row : results3) {
			System.out.println("In Resultset 3");
		  System.out.println("select lattitude, longitude from devicetrack.routebystops where routeno="+routeno+" and stopno="+stopno+" limit 1;");	
		    stop_lattitude = row.getDouble("lattitude")  ;
		    stop_longitude = row.getDouble("longitude")  ;
		}
		
		session.close();
		cluster.close();
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		return 1;
	}
	
	public void get_avgBusSpeed()
	{
		
	}
	

	

}
