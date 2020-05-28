<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Hotels</title>
</head>
<body>
	<p>${successMessage}</p>
	<c:forEach items="${hotels}" var="hotel">
		<h3>
			<a href="VerifyHotel?hotelId=${hotel.hotelId}">${hotel.hotelName}</a>
		</h3>
		<div>
			<p>City: ${hotel.city}</p>
		</div>
		<div>
			<p>Verified: ${hotel.verified}</p>
		</div>
		<div>
			<p>-------------------------------------------</p>
		</div>
	</c:forEach>

</body>
</html>