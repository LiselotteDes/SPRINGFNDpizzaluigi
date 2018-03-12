<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%-- Associeert de prefix c met de URI van de core library die de tag forEach bevat. --%>
<%@taglib prefix = 'c' uri='http://java.sun.com/jsp/jstl/core'%>
<%-- Voegt een verwijzing toe naar de Spring tag library --%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="nl">
<head>
	<!-- 	<title>Pizza's</title> -->
	<!-- 	<meta name="viewport" content="widt=device-width,initial-scale=1"> -->
	<!-- 	<link rel="stylesheet" href="css/pizzaluigi.css"> -->
	<c:import url="/WEB-INF/JSP/head.jsp">
		<c:param name="title" value="Pizza's"/>
	</c:import>
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

	<%--<c:forEach var="entry" items="${pizzas}"> --%>
	<c:forEach var="pizza" items="${pizzas}">
		<li>
			<%-- ${entry.key}: <c:out value="${entry.value.naam}"/> ${entry.value.prijs}&euro; --%>
			${pizza.id}: <c:out value="${pizza.naam}"/> ${pizza.prijs}&euro;
			<c:choose>
				<%-- <c:when test="${entry.value.pikant}"> --%>
				<c:when test="${pizza.pikant}">
					pikant
				</c:when>
				<c:otherwise>
					niet pikant
				</c:otherwise>
			</c:choose>
			<%--
			<c:url value="/pizzas" var="url">
				<c:param name="id" value="${entry.key}"/>
			</c:url>
			 --%>
			 <%-- De tag url maakt een clean URL. 
			      value bevat de bijbehorende URI template. --%>
			<spring:url value="/pizzas/{id}" var="url">
				<%-- 
				  Je vult de path variabele in de URI template van hierboven in.
				  name bevat naam van de path variabele, 
				  value bevat de waarde voor de path variabele.
				  Als de pizza de id 1 heeft, bevat de variabele url de URL /pizzas/1.
				  Als de URI template meerdere path variabelen bevat, 
				  schrijf je meerdere regels zoals hieronder.
				--%>
				<%-- <spring:param name="id" value="${entry.key}"/> --%>
				<spring:param name="id" value="${pizza.id}"/>
			</spring:url>
			<a href="${url}">Detail</a>
		</li>
	</c:forEach>
	</ul>
</body>
</html>