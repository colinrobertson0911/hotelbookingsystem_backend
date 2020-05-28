<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin login</title>
</head>
<body>
	<h2>Login in as administrator below</h2>
	<div>
		<form action="LoginAdminSubmit" method="post">
			${errorMessage }
			<div>
				<label>user name:</label> <input type="text" name="username" />
			</div>
			<div>
				<label>Password:</label> <input type="password" name="password" />
			</div>
			<button type="submit">Submit</button>
		</form>
	</div>
</body>
</html>