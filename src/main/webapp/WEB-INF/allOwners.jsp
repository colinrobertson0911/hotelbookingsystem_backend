<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Owners List</title>
</head>
<body>

	<h2>A List of All Owners</h2>

	<p>${successMessage}</p>
	<c:forEach items="${hotelOwners}" var="hotelOwner">
		<h3>
			<a href="SeeHotelOwner?hotelOwnerId=${hotelOwner.hotelOwnerId}">${hotelOwner.name}</a>
		</h3>
	</c:forEach>

</body>
</html>