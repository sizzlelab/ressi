<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html xmlns="[http://www.w3.org/1999/xhtml"] xml:lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>Secure Vaadin Application Demo Login</title>
    </head>
    <body>
        <h1>Please login</h1>
		<form method="post" action="j_security_check">
			<p>
				Username: <input type="text" name="j_username"/>
			</p>
			<p>
				Password: <input type="password" name="j_password"/>
			</p>
			<p>
				<input type="submit" value="Login"/>
			</p>
		</form>
    </body>
</html>
