<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="it" dir="ltr">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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

    </style>
</head>

<body>
<form class="app grid-x justify-center align-center" action="${pageContext.request.contextPath}/ordine/signup" method="post">
    <c:if test="${not empty alert}">
        <%@ include file="../partials/alert.jsp"%>
    </c:if>
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
            <input type="text" name="citta" id="citta" value="${citta}" readonly>
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
        <span class="cell w75">Sei gia' utente? Clicca qui per <a href="/FoodOut/ordine/login" style="text-decoration: none">Accedere</a></span>
    </fieldset>
</form>
</body>
</html>
