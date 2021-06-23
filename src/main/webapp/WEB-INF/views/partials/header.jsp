<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="/FoodOut/css/header.css" rel="stylesheet" type="text/css">
<div id="header">
    <figure>
        <img src="/FoodOut/images/logo.png" id="logo">
    </figure>
    <div id="links">
    <c:choose>
    <c:when test="${not empty utenteSession}">
        <c:choose>
            <c:when test="${utenteSession.admin==true}">
                <a href="/FoodOut/utente/show">Benvenuto, ${utenteSession.nome}</a>
            </c:when>
            <c:otherwise>
                <a href="/FoodOut/utente/profile">Benvenuto, ${utenteSession.nome}</a>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <a href="/FoodOut/utente/login"> Accedi </a>
        <a href="/FoodOut/utente/signup"> Registrati </a>
    </c:otherwise>
    </c:choose>
    </div>
</div>