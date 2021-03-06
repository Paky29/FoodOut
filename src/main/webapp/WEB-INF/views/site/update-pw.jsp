<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="it" dir="ltr">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Modifica password"/>
        <jsp:param name="scripts" value="update_pw_validation"/>
    </jsp:include>
    <style>
        .app {
            background: linear-gradient(var(--primary), white);
        <%--background-image: url("/FoodOut/images/sfondo.jpg");--%>
        }

        img {
            width: 100%;
            height: auto;
            opacity: 0.3;
        }
        .update-pw{
            padding: 1rem; <%--dimensione relativa al root--%>
            background-color:white;
            border-radius: 10px;
            opacity: revert;
        }

        .update-pw > * {
            margin:10px;
        }


    </style>
</head>
<body>
<form class="app grid-x justify-center align-center" action="${pageContext.request.contextPath}/utente/update-pw" method="post" novalidate>
    <c:if test="${not empty alert}">
        <%@ include file="../partials/alert.jsp"%>
    </c:if>
    <fieldset class="grid-y cell w50 update-pw">
        <h2> Modifica password </h2>
        <span> Attuale password  </span>
        <label for="old_pw" class="field grid-x">
            <input class="cell" type="password" name="old_pw" id="old_pw"  minlength="8" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" required>
            <small class="errMsg cell"></small>
        </label>
        <span> Nuova password </span>
        <label for="new_pw" class="field grid-x">
            <input class="cell" type="password" name="new_pw" id="new_pw"  minlength="8" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" required>
            <small class="errMsg cell"></small>
        </label>
        <span> Conferma nuova password </span>
        <label for="conf_pw" class="field grid-x">
            <input class="cell" type="password" name="conf_pw" id="conf_pw"  minlength="8" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" required>
            <small class="errMsg cell"></small>
        </label>
        <label for="email" class="field" style="visibility: hidden">
            <input type="email" name="email" id="email" value="${profilo.email}" maxlength="50">
        </label>
        <button type="submit" class="btn primary"> Conferma </button>
    </fieldset>
</form>
</body>
</html>
