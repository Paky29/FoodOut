<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="cell grid-y" id="header">
    <nav class="grid-x navbar align-center">
        <img src="/FoodOut/images/logo.png" class="fluid-image" id="logo">
    <div id="links">
        <a href="${pageContext.request.contextPath}/utente/login"> Accedi </a>
        <a href="${pageContext.request.contextPath}/utente/signup"> Registrati </a>
    </div>
    </nav>
    <form class="grid-x justify-center align-center address" action="/FoodOut/ristorante/zona" novalidate>
        <fieldset class="grid-y cell w50 index">
            <h2> Inserisci la tua citt&agrave </h2>
            <label for="citta" class="field w40 cell grid-x">
                <input class="cell" type="text" name="citta" id="citta" maxlength="30" pattern="^([a-zA-Z]|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" required>
                <small class="errMsg cell"> </small>
            </label>
            <button type="submit" class="btn primary"> Cerca ristoranti</button>
        </fieldset>
    </form>
</div>