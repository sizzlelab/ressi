<%-- 
    Document   : dashboard
    Created on : Feb 9, 2010, 10:53:32 PM
    Author     : ktuomain
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ressi dashboard</title>
		<style>
			.dashboard-widget {
				width: 450px; height: 300px; border: none;
			}
			h2 {
				margin: 0;
			}
			body {
				padding: 10px;
			}
		</style>
    </head>
    <body>	
		<div style="float:left;">
			<h2>User locations</h2>
			<iframe src ="locations.jsp" class="dashboard-widget">
				  <p>Your browser does not support iframes.</p>
			</iframe>
		</div>
		<div style="float:left;">
			<h2>Metalog</h2>
			<iframe src ="metalog.jsp" class="dashboard-widget">
				  <p>Your browser does not support iframes.</p>
			</iframe>
		</div>
		<div style="float:left;">
			<h2>User activity</h2>
			<iframe src ="activeUsers.jsp" class="dashboard-widget">
				  <p>Your browser does not support iframes.</p>
			</iframe>
		</div>
    </body>
</html>
