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
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Sign up"/>
    </jsp:include>
    <style>
        .app {
            background: linear-gradient(var(--primary), white);
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

        button {
            border-radius: 3px;
        }

        input {
            height: auto;
        }

    </style>
</head>

<body>
<form class="app grid-x justify-center align-center" action="${pageContext.request.contextPath}/utente/signup" method="post">
    <fieldset class="grid-x cell w75 signup justify-center"> <%-- vedere se è meglio  w50 o w75 ,  con justify-center , align-center o meno--%>
        <h1 id="title" class="cell"> Sign up </h1>
        <label for="nome" class="field w40 cell">
            <input type="text" name="nome" id="nome" placeholder="Nome">
        </label>
        <label for="cognome" class="field w40 cell">
            <input type="text" name="cognome" id="cognome" placeholder="Cognome">
        </label>
        <label for="provincia" class="field w40 cell">
            <input type="text" name="provincia" id="provincia" placeholder="Provincia">
        </label>
        <label for="citta" class="field w40 cell">
            <input type="text" name="citta" id="citta" placeholder="Citta">
        </label>
        <label for="via" class="field w50 cell" >
            <input type="text" name="via" id="via" placeholder="Via">
        </label>
        <label for="civico" class="field w30 cell">
            <input type="number" name="civico" id="civico" placeholder="n°">
        </label>
        <label for="email" class="field w40 cell">
            <input type="email" name="email" id="email" placeholder="Email">
        </label>
        <label for="pw" class="field w40 cell">
            <input type="password" name="pw" id="pw" placeholder="Password">
        </label>
        <button type="submit" class="btn primary w30"> Registrati </button>
    </fieldset>
</form>
</body>
</html>
