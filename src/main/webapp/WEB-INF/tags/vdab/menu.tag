<%@ tag description="menu" pageEncoding="UTF-8"%>
	<!-- Een custom tag bevat een page directive tag. 
	  Je tikt bij description een optionele omschrijving van wat de tag doet. De IDE toont die omschrijving als je de tag gebruikt in een JSP. -->

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
	<nav>
		<ul>
			<li><a href="<c:url value='/'/>">Welkom</a></li>
			<li><a href="<c:url value='/pizzas'/>">Pizza's</a></li>
			<li><a href="<c:url value='/pizzas/vantotprijs'/>">Van tot
					prijs</a></li>
			<li><a href="<c:url value='/pizzas/prijzen'/>">Prijzen</a></li>
			<li><a href="<c:url value='/pizzas/toevoegen'/>">Toevoegen</a></li>
			<li><a href="<c:url value='/mandje'/>">Mandje</a></li>
			<li><a href="<c:url value='/identificatie'/>">Identificatie</a></li>
			<li><a href="<c:url value='/headers'/>">Headers</a></li>
		</ul>
	</nav>
</header>
