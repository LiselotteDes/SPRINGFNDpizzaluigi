<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="vdab" uri="http://vdab.be/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="nl">
<head>
<%-- 	<c:import url="/WEB-INF/JSP/head.jsp"> --%>
<%-- 		<c:param name="title" value="${pizza.naam }"/> --%>
<%-- 	</c:import> --%>
	<vdab:head title="${pizza.naam}"/>
</head>
<body>
<%-- 	<c:import url="/WEB-INF/JSP/menu.jsp"/> --%>
	<vdab:menu/>
	<h1>${pizza.naam }</h1>
	<dl>
		<dt>Nummer</dt><dd>${pizza.id}</dd>
		<dt>Naam</dt><dd>${pizza.naam}</dd>
		<dt>Prijs</dt><dd><spring:eval expression="pizza.prijs"/></dd>
		<dt>In dollar</dt><dd><spring:eval expression="inDollar.waarde"/></dd>
		<dt>Pikant</dt><dd>${pizza.pikant ? "ja" : "Nee"}</dd>
	</dl>
</body>
</html>