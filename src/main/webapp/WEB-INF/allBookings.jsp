<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Bookings for ${hotel.hotelName}</title>
</head>
<body>
	<h1>All Bookings for ${hotel.hotelName}</h1>

	<c:forEach items="${hotel.bookings}" var="bookings">
		<div>
			<h3>Booking Details:</h3>
		</div>
		<div>
			<p>Check in date - ${bookings.checkInDate}</p>
		</div>
		<div>
			<p>Check out date - ${bookings.checkOutDate}</p>
		</div>
		<div>
			<p>Room Type - ${bookings.roomType}</p>
		</div>
		<div>
			<p>Extras - ${bookings.extras}
		</div>
		<div>
			<p>Total Price of stay = £${bookings.totalPrice}
		</div>
		<div>
			<p>-----------------------------------------------------</p>
		</div>
	</c:forEach>


</body>
</html>