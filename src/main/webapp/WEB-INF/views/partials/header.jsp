<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="cell grid-y" id="header">
    <nav class="grid-x navbar align-center">
        <img src="/FoodOut/images/logo.png" class="fluid-image" id="logo">
    <div class="" id="links">
        <c:choose>
            <c:when test="${not empty utenteSession}">
                <c:choose>
                    <c:when test="${utenteSession.admin==true}">
                        <a href=${pageContext.request.contextPath}/utente/signup">Benvenuto, ${utenteSession.nome}</a>
                    </c:when>
                    <c:otherwise>
                        <a href=${pageContext.request.contextPath}/utente/signup"">Benvenuto, ${utenteSession.nome}</a>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/utente/login"> Accedi </a>
                <a href="${pageContext.request.contextPath}/utente/signup"> Registrati </a>
            </c:otherwise>
        </c:choose>
    </div>
    </nav>
    <form class="grid-x justify-center align-center address" action="/ristorante/zona" method="post">
        <fieldset class="grid-y cell w50 index">
            <h2> Inserisci il tuo indirizzo </h2>
            <label for="indirizzo" class="field">
                <input type="text" name="indirizzo" id="indirizzo">
            </label>
            <button type="submit" class="btn primary"> Cerca ristoranti</button>
        </fieldset>
    </form>
</div>