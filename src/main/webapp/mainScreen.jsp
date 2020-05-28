<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Main</title>
</head>
<body>
	<h2>Main Page</h2>
	<div>
		<p>${ownerMessage}</p>
	</div>
	<div>
		<a href="LoginAsOwner">Log In as a Hotel Owner</a>
	</div>

	<div>
		<a href="LoginAsAdmin">Log in as an administrator</a>
	</div>
	<p>${errorMessage}
	<form action="SearchByCity" method="post">
		<div>
			<label>Search by city:</label> <input type="text" name="city" />
			<button type="submit">Search</button>
		</div>
	</form>

	<p>${errorRoomTypeMessage}
	<form action="SearchByRoomType" method="post">
		<label>Search by Room Type: </label> <input type="text"
			list="roomTypes" name="roomType">
		<datalist id="roomTypes">
			<c:forEach items="${allRooms}" var="allRooms">
				<option value="${allRooms.roomType}">
			</c:forEach>
		</datalist>
		<input type="submit" value="search" />
	</form>

	<p>
	<form action="SearchByAvailability" method="post">
		${errorAvailabilityMessage}
		<div>
			<label>Search for Availability</label> <input type="date"
				name="checkInDate" /> to <input type="date" name="checkOutDate" />
			<button type="submit">Search</button>
		</div>
	</form>

	<h3>${visabilityMessage}</h3>

	<c:forEach items="${hotel}" var="hotel">
		<h4>
			<a href="SeeHotel?hotelId=${hotel.hotelId}">${hotel.hotelName}</a>
		</h4>
		<div>
			<p>City: ${hotel.city}</p>
			<p>Star Rating: ${hotel.starRating}/5</p>
		</div>
		<div>
			<p>-------------------------------------------</p>
		</div>
	</c:forEach>

</body>
</html>