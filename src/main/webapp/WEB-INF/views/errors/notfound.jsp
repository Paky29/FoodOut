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
            background-image: url("/FoodOut/images/404_bg.png");
        }
    </style>

</head>
<body>
<div class="app grid-x justify-center align-center cnt">
<div class="cell text justify-center">
    <p class="title">Risorsa non trovata </p>
    <p> </p><a href="${back}" class="cell"> Torna indietro</a> </p>
</div>
</div>
</body>
</html>
