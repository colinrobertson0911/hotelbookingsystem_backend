<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>${hotel.hotelName}</title>
</head>
<body>

	<a href="${pageContext.request.contextPath}/Home">Back</a>
	<h3>${hotel.hotelName}</h3>
	<div>
		<p>Number of rooms: ${hotel.numOfRooms}</p>
		<p>Rooms available: ${hotel.numOfRooms - hotel.bookings.size()}</p>
		<c:forEach items="${hotel.room}" var="room">
			<div>
				<p>
					Type of rooms: ${room.roomType} Price: £ ${room.price} <a
						href="bookingPage?hotelId=${hotel.hotelId}&&roomId=${room.roomId}">Make
						a booking</a>
				</p>
			</div>
		</c:forEach>

		<p>Address:${hotel.address}</p>
		<p>Postcode:${hotel.postcode}</p>
		<p>City: ${hotel.city}</p>
		<p>Ammenities: ${hotel.ammenities}
		<p>Star Rating: ${hotel.starRating}/5</p>
		<p>Offers Airport Transfers: ${hotel.airportTransfers}</p>

	</div>
	<div>
		<p>-------------------------------------------</p>
	</div>

</body>
</html>