<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="vdab" uri="http://vdab.be/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="nl">
<head>
<%-- 	<c:import url="/WEB-INF/JSP/head.jsp"> --%>
<%-- 		<c:param name="title" value="Prijzen"/> --%>
<%-- 	</c:import> --%>
	<vdab:head title="Prijzen"/>
</head>
<body>
<%-- 	<c:import url="/WEB-INF/JSP/menu.jsp"/> --%>
	<vdab:menu/>
	<h1>Prijzen</h1>
	<ul>
	<c:forEach items="${prijzen}" var="prijs">
		<c:url value="/pizzas" var="url">
			<c:param name="prijs" value="${prijs}"/>
		</c:url>
		<li><a href="${url}" >${prijs}</a></li>
	</c:forEach>
	</ul>
	<c:if test="${not empty pizzas}">
		<h2>${prijs}</h2>
		<ul>
		<c:forEach items="${pizzas}" var="pizza">
			<spring:url var="url" value="/pizzas/{id}">
				<spring:param name="id" value="${pizza.id}"/>
			</spring:url>
			<li><a href="${url}"><c:out value="${pizza.naam}"/></a></li>
		</c:forEach>
		</ul>
	</c:if>
</body>
</html>