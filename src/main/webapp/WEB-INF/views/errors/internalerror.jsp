
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Errore interno"/>
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
    </div>
</div>
</body>
</html>
