<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html xmlns="[http://www.w3.org/1999/xhtml"] xml:lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Secure Vaadin Application Demo</title>
    </head>
    <body>
        <h1>You have been logged out</h1>
		<p>
			<a href="login.jsp">Log in</a> again.
		</p>
    </body>
</html>
<%
    session.invalidate();
%>
