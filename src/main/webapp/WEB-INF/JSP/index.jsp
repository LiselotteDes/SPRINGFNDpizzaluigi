<!-- 
  Met volgende regel stel je drie eigenschappen in:
  Als contentType text/html bevat en pageEncoding UTF-8,
  bevat de response een header Content-type met de waarde text/html;charset=UTF-8.
  UTF-8 encoding kan alle tekens van alle menselijke talen correct voorstellen.
  
  De webserver maakt bij de JSP standaard code om session variabelen bij te houden.
  Een session variabele is een variabele die de webserver per gebruiker bijhoudt
  (zoals een winkelmandje op een shopping website).
  session="false" geeft aan dat deze JSP geen session variabelen nodig heeft.
  Dit bevordert de performantie.
-->
<%@ page contentType="text/html; charset=UTF-8"
    	 pageEncoding="UTF-8"
    	 session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="nl">
<head>
	<!-- 	<title>Pizza Luigi</title> -->
	<!-- 	<link rel='icon' href='images/pizza.ico' type='image/x-icon'> -->
	<!-- 	<meta name='viewport' content='width=device-width, initial-scale=1'> -->
	<!-- 	<link rel='stylesheet' href='css/pizzaluigi.css'> -->
	<c:import url="/WEB-INF/JSP/head.jsp">
		<c:param name="title" value="Pizza Luigi"/>
	</c:import>
</head>
<body>
	<c:import url="/WEB-INF/JSP/menu.jsp"/>
	<h1>Pizza Luigi</h1>
	<img src='<c:url value="/images/pizza.jpg"/>' alt='pizza' class='fullwidth' >
	<!-- 
	  In een JSP lees je data die de controller doorgaf met een miniprogrammeertaal: 
	  EL (Expression Language). Elke EL expressie begint met $ gevolgd door { en eindigt op }.
	  Je leest de inhoud van de data met de naam boodschap met ${boodschap}
	-->
	<h2>${boodschap}</h2>
	<!-- EL en JavaBeans -->
	<h2>De zaakvoerder</h2>
	<dl>
		<dt>Naam</dt><dd>${zaakvoerder.naam}</dd>
		<dt>Aantal kinderen</dt><dd>${zaakvoerder.aantalKinderen}</dd>
		<dt>Gehuwd</dt><dd>${zaakvoerder.gehuwd ? 'Ja' : 'Nee'}</dd>
		<dt>Adres</dt>
		<dd>${zaakvoerder.adres.straat} ${zaakvoerder.adres.huisNr}<br>
			${zaakvoerder.adres.postcode} ${zaakvoerder.adres.gemeente }</dd>
	</dl>
	<c:if test="${not empty laatstBezocht}">
		<p>Je bezocht onze website laatst op ${laatstBezocht}.</p>
	</c:if>
	<p>Deze pagina werd ${aantalKeerBekeken} keer bekeken.</p>
	<p>${identificatie.emailAdres}</p>
</body>
</html>