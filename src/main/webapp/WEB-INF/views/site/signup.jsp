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
        <jsp:param name="title" value="Sign up"/>
    </jsp:include>
    <style>
        .app {
            background: linear-gradient(red, white);
        <%--background-image: url("http://localhost:8080/images/image.jpg");--%>
        }
        .signup{
            padding: 1rem; <%--dimensione relativa al root--%>
            background-color:white;
            border-radius: 10px;
        }

        .signup > * {
            margin:10px;
        }

        #civico{
            display: inline;
            width: 20.1%;
            margin-right: 0;

        }

        #via{
            display: inline;
            width: 78.5%;
            margin-left: 0;
        }


    </style>
</head>

<body>
<form class="app grid-x justify-center align-center" action="/utente/signup" method="post">
    <fieldset class="grid-y cell w50 signup">
        <h1> Sign up </h1>
        <span> Nome </span>
        <label for="nome" class="field">
            <input type="text" name="nome" id="nome">
        </label>
        <span> Cognome </span>
        <label for="cognome" class="field">
            <input type="text" name="cognome" id="cognome">
        </label>
        <span> Provincia </span>
        <label for="provincia" class="field">
            <input type="text" name="provincia" id="provincia">
        </label>
        <span> Citta </span>
        <label for="citta" class="field">
            <input type="text" name="citta" id="citta">
        </label>
        <span> Via </span>
        <span>
        <label for="via" >
            <input type="text" name="via" id="via">
        </label>
        <label for="civico">
            <input type="number" name="civico" id="civico" placeholder="n°">
        </label>
        </span>
        <span> Email </span>
        <label for="email" class="field">
            <input type="email" name="email" id="email">
        </label>
        <span> Password </span>
        <label for="password" class="field">
            <input type="password" name="password" id="password">
        </label>
        <button type="submit" class="btn primary"> Registrati </button>
    </fieldset>
</form>
</body>
</html>
