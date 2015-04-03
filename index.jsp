<%@ page import="java.util.*" %><%@ page import="com.servlets.*" %>
<%
//call the java SOAP functions to get default data
SOAPController Controller = new SOAPController();
//get location by IP
//Controller.getLatLongByIp(request.getRemoteAddr());
Day[] Days = Controller.getAllData();
%>
<html>


<head>
<!-- Import the jQuery, ajax and bootstrap javascrip libraries -->
<script src="http://code.jquery.com/jquery-1.10.2.js" type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="bootstrap/dist/js/bootstrap.min.js"></script>
<!-- point at the location of the bootstrap css -->
<link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>

<body>


<!-- Header Section -->
<div class="page-header" align="center">
  <h1>Artom's Weather App</h1>
</div>
<!-- Main Body Section -->
<div id="main" style="margin-left:100px;margin-right:10px">

	<!-- Top section and form -->
	<form class="form-inline" name='location'id='location' action='forecast' method='post'>
	
	<div  class="row" style="margin-top:50px">
		<div class="col-md-8" >

			<!-- Input text area -->
			<div class="col-md-4"><textarea class="form-control" rows="1" style="resize:none" placeholder="Zip Code" name='zipCode' id="zipCode"></textarea></div>
			
			<!-- Number of days selection -->
			<div class="col-md-4" align="center"><select class="form-control" id="days">
	  					<option>1 day</option>
	  					<option>2 day</option>
	  					<option>3 day</option>
	  					<option>4 day</option>
	  					<option>5 day</option></select></div>

			<!-- Send button -->
			<div class="col-md-4"><button type="submit" class="btn btn-primary" onclick="update()">Get Forecast</button></div>
		</div>	
	</div>
	</form>

	<!-- Bottom section -->
	<div id="wrapper" class="row" style="margin-top:50px">
		<div id="insidewrapper" class="col-md-12">

			<!-- Initial day display builder -->
			<% int length; if(Days.length>5) length = 5;else length = Days.length; for(int i = 0; i < length; i+=1) { String col;String day = "day" + i; if(i < 1)col =  "col-md-4";else col =  "col-md-2"; %>

				<div id="<%=day%>" class="<%=col%>"> 
					<div class="title"><%if(i==0){%>Today<%}else if(i==1){%>Tomorrow<%}else{%>Day <%=(i+1)%><%}%><br></div> 
					<div class="tempHigh">High: <%=Days[i].tempHigh%><br></div> 
					<div class="tempLow">Low: <%=Days[i].tempLow%><br></div> 
					<div class="precipEarly">Prec morning: <%=Days[i].precipEarly%><br></div>
					<div class="precipLate">Prec evening: <%=Days[i].precipLate%></div>  

				</div>

    			<% } %>
		</div>
	</div>
</div>


</body>
</html>

<script>
//functions to run on button press
//main function
function update(){
//disables the defaul button behavior so you stay on the same page
	event.preventDefault();
//clear old data
	for(var i = 0; i < 5; i++) {
		var day = "#" + "day" + i;
		$(day).find(".title").text("");
		$(day).find(".tempHigh").text("");
		$(day).find(".tempLow").text("");
		$(day).find(".precipEarly").text("");
		$(day).find(".precipLate").text("");
	}
//select the dropdown selection and save it as a string
	var dayNum = $( "select.form-control" ).val();
//split the string up into 2 section, the number and the word "day"
	var dayArray = dayNum.split(' ');
//hide all the unused divs based on the selected number of days
	for(var i = dayArray[0]/*this selects the number only*/; i < 5; i++) {
		var day = "#" + "day" + i;//build the string to find the right id for jQuery

		$(day).find(".title").hide;
		$(day).find(".tempHigh").hide;
		$(day).find(".tempLow").hide;
		$(day).find(".precipEarly").hide;
		$(day).find(".precipLate").hide;
	}
//runs the ajax call with the number of days which it passes as an int
	getAjax(parseInt(dayArray[0]));
}
//ajax function which passes the number of days to the SOAP functions and returns 
//a json with the weather data
function getAjax(value){
		//console.log(value);
	$.ajax({
   url: "/Weather/forecast",  			//url of the servlet
	type: 'POST',								//type of method
	dataType: 'json',							//data type to be sent
	data: JSON.stringify(value),			//data to be sent
	contentType: 'application/json',		//data type to be recieved
  	success : function(responseText){	//function to run when completed
		//console.log(responseText);
//sends the json to the next function which will 
//display the information on the screen
	parseJson(responseText,value);
      		  }
	});
}
//takes the json containig the weather data and fills the divs with it
function parseJson(message,length){
	
	//var json = message;
//loops through the number of days specified 
	for(var i = 0; i < length; i++) { 
//declares variables to build the id refrenced by jQuery
		var day = "#" + "day" + i; 
//Uses if statement to build the titles
//One for today, one for tomorrow ad numbered for the rest
		if(i<1)
			$(day).find(".title").text("Today");
		else if(i<2)
			$(day).find(".title").text("Tomorrow");
		else
			$(day).find(".title").text("Day"+ (i+1));
//puts in the the data from the json
		$(day).find(".tempHigh").text("High: " + message.days[i].tempHigh);
		$(day).find(".tempLow").text("Low: " + message.days[i].tempLow);
		$(day).find(".precipEarly").text("Prec morning: " + message.days[i].precipEarly);
		$(day).find(".precipLate").text("Prec evening: " + message.days[i].precipLate);
	}
}
</script>
