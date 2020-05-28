<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Owner Editing</title>
</head>
<body>
	<h2>Currently Editing ${hotelOwner.name}</h2>

	<f:form method="post" action="EditHotelOwnerSubmit"
		modelAttribute="hotelOwner">
		<div>
			<f:label path="name">Owner Name:</f:label>
			<f:input path="name" type="text" required="required" size="30" />
		</div>
		<div>
			<f:label path="email">Owner Email</f:label>
			<f:input path="email" type="text" required="required" size="30" />
		</div>
		<div>
			<f:label path="hotel"> Owned Hotels:</f:label>
			<f:select path="hotel" items="${allHotels}" itemLabel="hotelName"
				multiple="multiple" required="required" />
		</div>
		<div>
			<input type="submit" value="update" />
		</div>
		<f:hidden path="hotelOwnerId" />
		<f:hidden path="username" />
		<f:hidden path="password" />

	</f:form>



</body>
</html>