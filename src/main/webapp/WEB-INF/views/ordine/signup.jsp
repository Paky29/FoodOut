<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="it" dir="ltr">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Sign up"/>
        <jsp:param name="scripts" value="signup_validation"/>
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
<form class="app grid-x justify-center align-center" action="${pageContext.request.contextPath}/ordine/signup" method="post" novalidate>
    <c:if test="${not empty alert}">
        <%@ include file="../partials/alert.jsp"%>
    </c:if>
    <fieldset class="grid-x cell w75 signup justify-center">
        <h1 id="title" class="cell"> Sign up </h1>
        <label for="nome" class="field w40 cell grid-x">
            <input class="cell" type="text" name="nome" id="nome" placeholder="Nome" maxlength="30" pattern="^([a-zA-Z]|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="cognome" class="field w40 cell grid-x">
            <input class="cell" type="text" name="cognome" id="cognome" placeholder="Cognome" maxlength="30" pattern="^([a-zA-Z]|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="provincia" class="field w40 cell grid-x">
            <input class="cell" type="text" name="provincia" id="provincia" placeholder="Provincia" pattern="^([a-zA-Z]|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" maxlength="30" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="citta" class="field w40 cell grid-x">
            <input class="cell" type="text" name="citta" id="citta" value="${citta}" maxlength="30" pattern="^([a-zA-Z]|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" readonly required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="via" class="field w50 cell grid-x" >
            <input class="cell" type="text" name="via" id="via" placeholder="Via" maxlength="50" required pattern="^(\w|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'|\.){1,50}$" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="civico" class="field w30 cell grid-x">
            <input class="cell" type="number" name="civico" id="civico" placeholder="n°" min="1" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="email" class="field w40 cell grid-x">
            <input class="cell" type="email" name="email" id="email" placeholder="Email" maxlength="50" pattern="^([\w\.\-]+)@([\w\-]+)((\.(\w){2,})+)$" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="pw" class="field w40 cell grid-x">
            <input class="cell" type="password" name="pw" id="pw" placeholder="Password" minlength="8" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" required>
            <small class="errMsg cell"> </small>
        </label>
        <button type="submit" class="btn primary w30"> Registrati </button>
        <span class="cell w75">Sei gia' utente? Clicca qui per <a href="/FoodOut/ordine/login" style="text-decoration: none">Accedere</a></span>
    </fieldset>
</form>
</body>
</html>
