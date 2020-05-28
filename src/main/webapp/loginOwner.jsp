<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Staff Login</title>
</head>
<body>
	<h2>Login as a Hotel Owner Below</h2>

	<div>
		<form action="LoginOwnerSubmit" method="post">
			${errorMessage}
			<div>
				<label>User Name:</label> <input type="text" name="username" />
			</div>
			<div>
				<label>Password:</label> <input type="password" name="password" />
			</div>
			<button type="submit">Submit</button>
		</form>
	</div>

</body>
</html>