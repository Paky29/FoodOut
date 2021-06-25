<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="cell grid-y" id="header">
    <nav class="grid-x navbar align-center">
        <img src="/FoodOut/images/logo.png" class="fluid-image" id="logo">
    <div class="" id="links">
        <a href="${pageContext.request.contextPath}/utente/login"> Accedi </a>
        <a href="${pageContext.request.contextPath}/utente/signup"> Registrati </a>
    </div>
    </nav>
    <form class="grid-x justify-center align-center address" action="/ristorante/zona" method="post">
        <fieldset class="grid-y cell w50 index">
            <h2> Inserisci la tua citt&agrave </h2>
            <label for="indirizzo" class="field">
                <input type="text" name="indirizzo" id="indirizzo">
            </label>
            <button type="submit" class="btn primary"> Cerca ristoranti</button>
        </fieldset>
    </form>
</div>