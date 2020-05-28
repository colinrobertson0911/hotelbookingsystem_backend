<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create a new room type</title>
</head>
<body>
	<p>${errorMessage}</p>
	<f:form method="post" action="AddNewRoomTypeSubmit"
		modelAttribute="room">
		<div>
			<f:label path="roomType">Type of Room:</f:label>
			<f:input path="roomType" type="text" required="required" />
		</div>
		<div>
			<f:label path="price">Price of Room:</f:label>
			<f:input path="price" type="number" required="required" />
		</div>
		<button type="submit">Add New Room Type</button>
	</f:form>

</body>
</html>