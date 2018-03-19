<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="vdab" uri="http://vdab.be/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="nl">
<head>
<%-- 	<c:import url="/WEB-INF/JSP/head.jsp"><c:param name="title" value="Mandje"/></c:import> --%>
	<vdab:head title="Mandja"/>
</head>
<body>
	<vdab:menu/>
	<h1>Mandje</h1>
	<c:url value="/mandje" var="url"/>
	
	<form:form action="${url}" modelAttribute="mandjeForm" method="post" id="mandjeform">
		<form:label path="pizzaId">Pizza: <form:errors path="pizzaId"/></form:label>
		<%-- Je maakt een keuzelijst met alle pizzas waaruit de gebruiker er één kan selecteren.
		  Je vermeldt bij 'items' de verzameling waarmee Spring de keuzelijst vult.
		  Je vermeldt bij 'itemLabel' de eigenschap die Spring van ieder item in de keuzelijst toont.
		  Je vermeldt bij 'itemValue' de eigenschap van het gekozen item die de browser doorstuurt als de gebruiker de form submit.
		  Als de gebruiker bijvoorbeeld pizza 7 kiest en de form submit, zal de browser in de request het getal 7 naar de website sturen.
		--%>
		<form:select path="pizzaId" items="${allePizzas}" itemLabel="naam" itemValue="id" />
		<input type="submit" value="Toevoegen" id="toevoegknop">
	</form:form>
	
	<c:if test="${not empty pizzasInMandje}">
		<h2>Pizza's in mandje</h2>
		<ul>
			<c:forEach items="${pizzasInMandje}" var="pizza">
				<li><c:out value="${pizza.naam }"/></li>
			</c:forEach>
		</ul>
	</c:if>
	
	<script>
		document.getElementById("mandjeform").onsubmit = function() {
			document.getElementById("toevoegknop").disabled = true;
		};
	</script>
</body>
</html>