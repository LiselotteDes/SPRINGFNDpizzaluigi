<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<!-- Associeert de prefix c met de URI van de core library die de tag forEach bevat. -->
<%@taglib prefix = 'c' uri='http://java.sun.com/jsp/jstl/core'%>
<!DOCTYPE html>
<html lang="nl">
<head>
	<title>Pizza's</title>
	<meta name="viewport" content="widt=device-width,initial-scale=1">
	<link rel="stylesheet" href="css/pizzaluigi.css">
</head>
<body>
	<c:import url="/WEB-INF/JSP/menu.jsp"/>
	<h1>Pizza's
		<%-- Itereren zonder verzameling --%>
		<c:forEach begin="1" end="5">
			&#9733;	<%-- de HTML code van een ster --%>
		</c:forEach>
	</h1>
	<ul class="zebra">
<%-- 
  Itereert over de List in pizzas.
  De variabele pizza wijst bij elke iteratie naar een volgend element.
-->

<%--<c:forEach var="pizza" items="${pizzas}"> --%>
<%-- 	<!-- 
<%-- 	  Toont het huidig element. -->
<%-- 	  Roept met $ {pizza.naam} de method getNaam op van het Pizza object. -->
<%-- 	  Roept met $ {pizza.prijs} de method getPrijs op van het Pizza object. -->
<%-- 	-->
<%-- 	<li>${pizza.naam} ${pizza.prijs} &euro;</li> --%>
<%--<!-- Sluit de foreach af -->
<%--</c:forEach> --%>

	<c:forEach var="entry" items="${pizzas}">
		<li>
			${entry.key}: <c:out value="${entry.value.naam}"/> ${entry.value.prijs}&euro;
			<c:choose>
				<c:when test="${entry.value.pikant}">
					pikant
				</c:when>
				<c:otherwise>
					niet pikant
				</c:otherwise>
			</c:choose>
			<c:url value="/pizzas" var="url">
				<c:param name="id" value="${entry.key}"/>
			</c:url>
			<a href="${url}">Detail</a>
		</li>
	</c:forEach>
	</ul>
</body>
</html>