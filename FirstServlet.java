package com.example.first;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.twilio.sdk.verbs.TwiMLResponse;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.Message;
import java.util.Date;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class FirstServlet
 */
@WebServlet("/FirstServlet")
public class FirstServlet extends HttpServlet {
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
	 int time=6 ;
	 int s1 = 0 , s2 =0 ;
	//String split[]=null;
     
 
	String split[] = StringUtils.split(request.getParameter("Body"));
     VehicleTrack vehicle = new VehicleTrack();
     try{
    	// s1 = Integer.parseInt(split[0]) ;
    //	 s2 = Integer.parseInt(split[1]);
    	 time = vehicle.calculate_TimetoReachStop(split[0],split[1]);
     	}catch(Exception e) 
     {
     		e.printStackTrace();
     }	

    // s2 = Integer.parseInt(split[0]);
   //  time = vehicle.calculate_TimetoReachStop(Integer.parseInt(split[0]),Integer.parseInt(split[0]));
 
	 TwiMLResponse twiml = new TwiMLResponse();
     Message message = new Message("Next Bus expected time to reach at your stop : "+5 + " is "+time);
     try {
         twiml.append(message);
     } catch (TwiMLException e) {
         e.printStackTrace();
     }

     response.setContentType("application/xml");
     response.getWriter().print(twiml.toXML());
	}
	
	
	
/*	private static final long serialVersionUID = 1L;
	public static final String HTML_START="<html><body>";
    public static final String HTML_END="</body></html>";*/ 
    // service() responds to both GET and POST requests.
    // You can also use doGet() or doPost()

    /**
     * @see HttpServlet#HttpServlet()
     */
 /*       public FirstServlet() {
            super();
            // TODO Auto-generated constructor stub
       }

	*//**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 PrintWriter out = response.getWriter();
	     Date date = new Date();
	     out.println(HTML_START + "<h2>Hi There!</h2><br/><h3>Date="+date +"</h3>"+HTML_END);
	}

	*//**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *//*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}*/

}
