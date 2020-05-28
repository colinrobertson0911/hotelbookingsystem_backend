<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add Hotel</title>
</head>
<body>
	<h2>Add your hotel</h2>
	<div>
		<p>${errorMessage}</p>
	</div>
	<f:form method="post" action="AddHotelSubmit" modelAttribute="hotel">

		<div>
			<f:label path="hotelName">Hotel Name:</f:label>
			<f:input path="hotelName" type="text" required="required" />
		</div>
		<div>
			<f:label path="numOfRooms">Number of Rooms</f:label>
			<f:input path="numOfRooms" type="text" required="required" />
		</div>
		<div>
			<f:label path="address">Address:</f:label>
			<f:input path="address" type="text" required="required" />
		</div>
		<div>
			<f:label path="postcode">Postcode:</f:label>
			<f:input path="postcode" type="text" required="required" length="10" />
		</div>
		<div>
			<f:label path="city">City</f:label>
			<f:input path="city" type="text" required="required" />
		</div>
		<div>
			<f:label path="ammenities">Amenities:</f:label>
			<f:input path="ammenities" type="text" required="required"
				length="8000" />
		</div>
		<div>
			<f:label path="starRating">Star Rating</f:label>
			<f:input path="starRating" type="text" required="required" />
		</div>
		<div>
			<f:label path="room">Type of Rooms</f:label>
			<f:select path="room" items="${allRooms}"
				itemLabel="roomTypeAndPrice" required="required" multiple="multiple" />
		</div>
		<div>
			<f:label path="airportTransfers">Airport Transfers available?</f:label>
			<f:checkbox path="airportTransfers" />
		</div>
		<div>
			<f:hidden path="verified" />
		</div>
		<div>
			<button type="submit">Add Hotel</button>
		</div>
	</f:form>
	<div>
		<a href="ReturnToOwnerScreen">Return to your hotels</a>
</body>
</html>