<%@tag description="head onderdeel van pagina" pageEncoding="UTF-8"%>
<%--
  Je definieert één attribuut met een page directive attribute.
  Je tikt bij name de attribuut naam.
  Je geeft bij required aan dat dit attribuut verplicht in te vullen is. 
  Je tikt bij type het attribuut type.
--%>
<%@attribute name="title" required="true" type="java.lang.String" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<title>${title}</title>
<link rel="icon" href="<c:url value='/images/pizza.ico'/>" type="image/x-icon">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<c:url value='/css/pizzaluigi.css'/>">
