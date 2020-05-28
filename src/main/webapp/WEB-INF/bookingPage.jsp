<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Booking Page</title>
</head>
<body>
	<h2>Make a booking</h2>
	<div>
		<f:form method="post" action="BookingSubmit" modelAttribute="bookings">
			<p>Please enter your booking details</p>
			<div>
				<h3>
					<f:label path="hotel">${hotel.hotelName}</f:label>
					<f:hidden path="hotel" value="${hotel.hotelName}" />
				</h3>
				<h3>
					<f:label path="roomType">${room.roomType} - </f:label>
					<f:hidden path="roomType" value="${room.roomType}" />
					<f:label path="roomPrice">£${room.price} per night</f:label>
					<f:hidden path="roomPrice" value="${room.price}" />
				</h3>
			</div>

			<div>
				<label>Length of stay</label> <input type="date" name="checkInDate" />
				to <input type="date" name="checkOutDate" />
			</div>

			<div>
				<f:label path="extras">Extras</f:label>
				<f:select path="extras" items="${Extras}" itemLabel="service"
					required="required">
					<f:option value="0"> None </f:option>
				</f:select>
			</div>
			<f:hidden path="totalPrice" value="0" />
			<f:hidden path="bookingId" />
			<div>
				<button type="Submit">Create booking</button>
			</div>
		</f:form>
	</div>

</body>
</html>