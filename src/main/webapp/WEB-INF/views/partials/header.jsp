<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="header">
    <div id="image">
        <img src="images/logo.png" id="logo">
    </div>
    <div id="links">
    <c:choose>
    <c:when test="${not empty utenteSession}">
        <a href="/prova_DB/utente/logout"> Logout </a>
        <c:choose>
            <c:when test="${utenteSession.isAdmin}">
                <a href="/prova_DB/utente/show"> ${utenteSession.nome}</a>
            </c:when>
            <c:otherwise>
                <a href="/prova_DB/utente/profile"> ${utenteSession.nome}</a>
            </c:otherwise>
        </c:choose>
    </c:when>

    <c:otherwise>
        <a href="/prova_DB/utente/login"> Accedi </a>
        <a href="/prova_DB/utente/signup"> Registrati </a>
    </c:otherwise>
    </c:choose>
    </div>
</div>