package com.servlets;

import com.SOAPutil.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class WeatherServlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
//GET method
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//unused
	}
//POST method
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//reads the value sent by ajax
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));		
		String value = "";
        if(br != null){
            value = br.readLine();
        }
		//uses the value to make the SOAP call
		SOAPController Controller = new SOAPController(value);
		Day[] Days = Controller.getAllData();
		//builds the reply json array
		String json = "{\"days\":[";
		for(int i = 0; i<Days.length;i++){
			json = json + Days[i].getJson();
			if(i<(Days.length-1)){
				json = json + ",";
				System.out.println();
			}else{
				json = json + "]}";
				
			}
		}
      //replies to the ajax call with the built json
		response.setContentType("text/plain");
		response.getWriter().write(json);		
	} 
	public void destroy() {
	}	
}
