<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hotel Editing</title>
</head>
<body>
	<h2>Editing ${hotel.hotelName}</h2>

	<f:form method="post" action="EditHotelSubmit" modelAttribute="hotel">
		<div>
			<f:label path="hotelName">Hotel Name: </f:label>
			<f:input path="hotelName" type="text" required="required" />
		</div>
		<div>
			<f:label path="numOfRooms">Number of Rooms: </f:label>
			<f:input path="numOfRooms" type="number" required="required" />
		</div>
		<div>
			<f:label path="address">Hotel Address: </f:label>
			<f:input path="address" type="text" required="required" />
		</div>
		<div>
			<f:label path="postcode">Hotel Postcode: </f:label>
			<f:input path="postcode" type="text" required="required" />
		</div>
		<div>
			<f:label path="city">City: </f:label>
			<f:input path="city" type="text" required="required" />
		</div>
		<div>
			<f:label path="ammenities">List of Ammenities</f:label>
			<f:input path="ammenities" type="text" required="required" />
		</div>
		<div>
			<f:label path="starRating">Star Rating out of Five: </f:label>
			<f:input path="starRating" type="number" required="required" />
		</div>
		<div>
			<f:label path="room">Types of Rooms: </f:label>
			<f:select path="room" items="${allRooms}" itemLabel="roomType"
				multiple="multiple" required="required" />
		</div>

		<div>
			<input type="submit" value="update" />
		</div>

		<div>
			<f:hidden path="verified" />
			<f:hidden path="hotelId" />
		</div>



	</f:form>
</body>
</html>