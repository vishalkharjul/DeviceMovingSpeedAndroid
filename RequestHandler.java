package com.example.first;
import java.io.IOException;
import java.net.*;

import com.sun.net.httpserver.HttpServer;
import java.util.Date;

public class RequestHandler {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	    HttpServer server = HttpServer.create(new InetSocketAddress(8082), 1000);
	    server.createContext("/requests", new MyHandler());
	    server.setExecutor(null); // creates a default executor
	    server.start();
	}
	
	public void get_distance_busandstop()
	{
		
	}
	
	public static int calculate_TimetoReachStop(int routeno,int Stopno)
	{
		return 1;
	}
	public void get_RunningBusOnRoute()
	{
		
	}
	public void get_avgBusSpeed()
	{
		
	}
	

}





