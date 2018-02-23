<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Verwijst naar een parameter title, die je zal meegeven bij het importeren. --%>
<title>${param.title}</title>
<%-- <link rel="icon" href="images/pizza.ico" type="image/x-icon"> --%>
<%-- Opgelet: c:url tag werkt enkel correct als d waarde die je meegeeft bij value begint met /. --%>
<link rel="icon" href="<c:url value='/images/pizza.ico'/>" type="image/x-icon">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" href="<c:url value='/css/pizzaluigi.css'/>" >