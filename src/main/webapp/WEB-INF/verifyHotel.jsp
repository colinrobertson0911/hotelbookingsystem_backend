<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edit Hotel (Administrator)</title>
</head>
<body>
	<h2>${hotel.hotelName}</h2>

	<f:form method="post" action="VerifyHotelSubmit" modelAttribute="hotel">
		<div>
			<f:label path="hotelName"> ${hotel.hotelName}</f:label>
		</div>
		<div>
			<f:label path="address"> ${hotel.address}</f:label>
		</div>
		<div>
			<f:label path="city"> ${hotel.city}</f:label>
		</div>
		<div>
			<f:label path="postcode"> ${hotel.postcode}</f:label>
		</div>
		<div>
			<f:label path="verified"> Verify Hotel</f:label>
			<f:checkbox path="verified" />
		</div>
		<div>
			<f:hidden path="hotelId" />
			<f:hidden path="hotelName" />
			<f:hidden path="address" />
			<f:hidden path="city" />
			<f:hidden path="postcode" />
			<f:hidden path="ammenities" />
			<f:hidden path="starRating" />
			<f:hidden path="room" />
			<f:hidden path="numOfRooms" />
		</div>
		<div>
			<input type="submit" value="Verify" />
		</div>
	</f:form>



</body>
</html>