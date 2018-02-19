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
<!DOCTYPE html>
<html lang="nl">
<head>
	<title>Pizza Luigi</title>
	<link rel='icon' href='images/pizza.ico' type='image/x-icon'>
	<meta name='viewport' content='width=device-width, initial-scale=1'>
	<link rel='stylesheet' href='css/pizzaluigi.css'>
</head>
<body>
	<h1>Pizza Luigi</h1>
	<img src='images/pizza.jpg' alt='pizza' class='fulwidth'>
	<!-- 
	  In een JSP lees je data die de controller doorgaf met een miniprogrammeertaal: 
	  EL (Expression Language). Elke EL expressie begint met $ gevolgd door { en eindigt op }.
	  Je leest de inhoud van de data met de naam boodschap met ${boodschap}
	-->
	<h2>${boodschap}</h2>
</body>
</html>