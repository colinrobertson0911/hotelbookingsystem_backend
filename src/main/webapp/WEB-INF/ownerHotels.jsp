<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Owner Hotels</title>
</head>
<body>
	<h2>${hotelOwner.name}Hotels</h2>
	<div>
		<a href="AddHotel">Add a new hotel</a>
	</div>
	<div>
		<a href="NewRoomType">Click here to add a new room type</a>
	</div>

	<div>
		<p>${successMessage}</p>
	</div>
	<div>
		<a href="Refresh">Refresh hotel list</a>
		<p></p>
	</div>
	<div>
		<c:forEach items="${hotelOwner.hotel}" var="hotel">
			<div>
				<a
					href="EditHotel?hotelId=${hotel.hotelId}&hotelOwnerId=${hotelOwner.hotelOwnerId}">${hotel.hotelName}</a>
			</div>
			<div>
				<p>Number of rooms: ${hotel.numOfRooms}
			</div>
			<div>
				<p>City: ${hotel.city}</p>
			</div>
			<div>
				<p>Amenities: ${hotel.ammenities}
			</div>
			<div>
				<p>Star Rating: ${hotel.starRating}</p>
			</div>
			<div>
				<a href="AllBookings?hotelId=${hotel.hotelId}">Bookings for
					Hotel</a>
			</div>
			<div>
				<p>--------------------------------------------</p>
			</div>
		</c:forEach>
	</div>
</body>
</html>