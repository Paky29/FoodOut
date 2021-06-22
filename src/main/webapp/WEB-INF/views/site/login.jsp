<%--
  Created by IntelliJ IDEA.
  User: User01
  Date: 22/06/2021
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it" dir="ltr">
<head>
    <jsp:include page="../../WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Login"/>
    </jsp:include>
    <style>
        .app {
            background: linear-gradient(red, white);
        <%--background-image: url("/prova_DB/images/image.jpg");--%>
        }

        img {
            width: 100%;
            height: auto;
            opacity: 0.3;
        }
        .login{
            padding: 1rem; <%--dimensione relativa al root--%>
            background-color:white;
            border-radius: 10px;
            opacity: revert;
        }

        .login > * {
            margin:10px;
        }


    </style>
</head>

<body>
<form class="app grid-x justify-center align-center" action="/utente/login" method="post">
    <fieldset class="grid-y cell w50 login">
        <h2> Login </h2>
        <span> Email </span>
        <label for="email" class="field">
            <input type="email" name="email" id="email">
        </label>
        <span> Password </span>
        <label for="password" class="field">
            <input type="password" name="password" id="password">
        </label>
        <button type="submit" class="btn primary"> Accedi </button>
    </fieldset>
</form>
</body>
</html>
