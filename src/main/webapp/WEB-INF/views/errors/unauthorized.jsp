<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: somma
  Date: 15/06/2021
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Stop"/>
        <jsp:param name="styles" value="error"/>
    </jsp:include>
    <style>
        .cnt {
            background-image: url("/FoodOut/images/stop.jpg");
        }
    </style>

</head>
<body>
<div class="app grid-x justify-center align-center cnt">
    <div class="cell text justify-center">
        <p class="title">ALT!</p>
        <p>Non disponi dell'autorizzazione necessaria a questa azione </p>
        <c:choose>
            <c:when test="${utenteSession==null}">
                <p> </p><a href="/FoodOut/utente/login" class="cell"> Vai alla pagina di login</a> </p> <%--da vedere--%>
            </c:when>
            <c:otherwise>
                <p> </p><a href="/FoodOut/ristorante/zona" class="cell"> Torna alla pagina principale</a> </p> <%--da vedere--%>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
