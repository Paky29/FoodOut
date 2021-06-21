<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="header">
    <figure>
        <img src="images/logo.png" id="logo">
    </figure>
    <div id="links">
    <c:choose>
    <c:when test="${not empty utenteSession}">
        <a href="/FoodOut/utente/logout"> Logout </a>
        <c:choose>
            <c:when test="${utenteSession.isAdmin}">
                <a href="/FoodOut/utente/show"> ${utenteSession.nome}</a>
            </c:when>
            <c:otherwise>
                <a href="/FoodOut/utente/profile"> ${utenteSession.nome}</a>
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