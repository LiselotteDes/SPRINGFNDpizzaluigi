<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="nl">
<head>
	<c:import url="/WEB-INF/JSP/head.jsp"><c:param name="title" value="Headers"/></c:import>
</head>
<body>
	<c:import url="/WEB-INF/JSP/menu.jsp"/>
	Je browser wordt uitgevoerd op 
	${opWindows ? "Windows" : "een niet-Windows besturingssysteem"}.
</body>
</html>