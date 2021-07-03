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
        <jsp:param name="title" value="Risorsa non trovata"/>
        <jsp:param name="styles" value="error"/>
    </jsp:include>
    <style>
        .cnt {
            background-image: url("/FoodOut/images/server.jpg");
        }
    </style>

</head>
<body>
<div class="app grid-x justify-center align-center cnt">
    <div class="cell text justify-center">
        <p class="title"> Errore interno </p>
        <p> Siamo spiacenti, si è verificato un problema nel server </p>
        <p> </p><a href="" class="cell"> Torna indietro</a> </p>
    </div>
</div>
</body>
</html>
