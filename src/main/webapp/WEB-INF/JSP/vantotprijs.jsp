<%@ page contentType="text/html" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- Associeert de prefix form met de URL van de Spring form tag library. --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="nl">
<head>
	<c:import url="/WEB-INF/JSP/head.jsp"><c:param name="title" value="Van tot prijs"/></c:import>
</head>
<body>
	<c:import url="/WEB-INF/JSP/menu.jsp"/>
	<h1>Van tot prijs</h1>
	<c:url value="/pizzas" var="url"/>
	
	<%-- Deze tag maakt een HTML tag <form...>.
	  modelAttribute bevat de naam van het form object.
	  Je tikt bij de method get, omdat je met deze form enkel data zoekt, geen data wijzigt.
	  Je geeft bij action de url aan waarde browser een request verstuurt als de gebruiker de form submit.
	  De naam en inhoud van de tekstvakken komen dan achter op die url. Voorbeeld: /pizzas?van=3&tot=4 --%>
	<form:form action="${url}" modelAttribute="vanTotPrijsForm" method="get">
		<%-- Deze tag maakt een HTML tag <label for="van">. 
		  form:errors toont een boodschap die hoort bij van, als van foutief is.--%>
		<form:label path="van">Van:<form:errors path="van"/></form:label>
		<%-- Deze tag maakt een HTML tag <input name="van" id="van" value="0" autofocus="autofocus">
		  De tag heeft 0 gelezen uit het form object via de method getVan.
		  De tag verwacht dat je bij elk attribuut een waarde meegeeft met =, ook bij het attribuut autofocus.
		  Je geeft dan de waarde "autofocus" mee.
		  Er bestaat naast <form:input ...> ook <form:password ...>, ... --%>
		<%-- Met HTML 5 is client sided validation toegevoegd. Dus nu gebeurt de validatie zowel op de server als op de browser. --%>
		<form:input path="van" autofocus="autofocus" type="number" required="required" min="0"/>
		<form:label path="tot">Tot:<form:errors path="tot"/></form:label>
		<form:input path="tot" required="required" type="number" min="0"/>
		<%-- De tag library bevat geen tag om een submit knop te maken.
		  Je gebruikt de klassieke HTML tag <input type="submit" ...> --%>
		<input type="submit" value="Zoeken">
		<%-- Je toont met <form:errors ...> (zonder path attribuut) foutmeldingen die niet aan één vak verbonden zijn.
		  Je geeft met cssClass="fout" aan dat Srping de CSS class fout moet toepassen op de div waarin Spring deze foutmeldingen toont. --%>
		<form:errors cssClass="fout"/>
	</form:form>
	
	<c:if test="${not empty pizzas}">
		<ul>
		<%-- Als de gebruiker de form submit, verwerk je de bijbehorende request. pizzas zal dan de opgevraagde pizza's bevatten. --%>
		<c:forEach items="${pizzas}" var="pizza">
			<spring:url var="url" value="/pizzas/{id}">
				<spring:param name="id" value="${pizza.id}"/>
			</spring:url>
			<li>
				<a href="${url}"><c:out value="${pizza.naam}"/></a>(${pizza.prijs})
			</li>
		</c:forEach>
		</ul>
	</c:if>
</body>
</html>