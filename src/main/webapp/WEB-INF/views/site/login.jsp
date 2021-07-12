<%--
  Created by IntelliJ IDEA.
  User: User01
  Date: 22/06/2021
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="it" dir="ltr">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <%--<script>
        $(document).ready(function (){
            $('#log').on('submit', function() {
               validateForm(this);
            });
        })
    </script>--%>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Login"/>
        <jsp:param name="scripts" value=""/>
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
<form class="app grid-x justify-center align-center" action="${pageContext.request.contextPath}/utente/login" method="post" novalidate>
    <c:if test="${not empty alert}">
        <%@ include file="../partials/alert.jsp"%>
    </c:if>
    <fieldset class="grid-y cell w50 login">
        <h2> Login </h2>
        <span> Email </span>
        <label for="email" class="field grid-x">
            <input class="cell" type="email" name="email" id="email" required maxlength="50" pattern="^([\w\.\-]+)@([\w\-]+)((\.(\w){2,})+)$">
            <small class="errMsg cell"> </small>
        </label>
        <small class="errMsg cell"></small>
        <span> Password </span>
        <label for="pw" class="field grid-x">
            <input class="cell" type="password" name="pw" id="pw" required minlength="8" pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$">
            <small class="errMsg cell"> </small>
        </label>
        <button type="submit" class="btn primary"> Accedi </button>
    </fieldset>
</form>
</body>
</html>
