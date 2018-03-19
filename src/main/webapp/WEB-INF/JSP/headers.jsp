<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib uri="http://vdab.be/tags" prefix="vdab" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="nl">
<head>
<%-- 	<c:import url="/WEB-INF/JSP/head.jsp"><c:param name="title" value="Headers"/></c:import> --%>
	<vdab:head title="Headers"/>
</head>
<body>
<%-- <c:import url="/WEB-INF/JSP/menu.jsp"/> --%>
	<vdab:menu/>
	Je browser wordt uitgevoerd op 
	${opWindows ? "Windows" : "een niet-Windows besturingssysteem"}.
</body>
</html>