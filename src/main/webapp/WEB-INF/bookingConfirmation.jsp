<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Confirm your booking details</title>
</head>
<body>
	<h2>Confirm your booking details</h2>
	<div>
		<f:form method="post" action="BookingConfirmationSubmit"
			modelAttribute="bookings">
			<div>
				<f:label path="hotel">${bookings.hotel}</f:label>
			</div>
			<div>
				<f:label path="roomType">${bookings.roomType} </f:label>
			</div>
			<div>
				<f:label path="checkInDate">Check In Date: ${bookings.checkInDate}</f:label>
				<f:label path="checkOutDate">Check Out Date: ${bookings.checkOutDate}</f:label>
			</div>
			<div>
				<f:label path="roomPrice">Cost of Room Per Night: £${bookings.roomPrice}</f:label>
			</div>
			<div>
				<f:label path="extrasPrice">Cost of Extras: £${bookings.extrasPrice}</f:label>
			</div>
			<div>
				<f:label path="totalPrice">Total Price: £${bookings.totalPrice}</f:label>
			</div>
			<div>
				<button type="Submit">Go To payment And Confirmation</button>
				<a href="CancelBackToMain">Cancel Booking</a>
			</div>
		</f:form>
	</div>
</body>
</html>