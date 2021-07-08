<%@ page import="model.ristorante.Ristorante" %>
<%@ page import="model.tipologia.Tipologia" %>
<%@ page import="java.util.StringJoiner" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Menu"/>
        <jsp:param name="styles" value="info_ris_crm"/>
        <jsp:param name="scripts" value="menu_admin"/>
    </jsp:include>
</head>
<body>
<style>

    .index  {
        padding: 1rem;
    <%--dimensione relativa al root--%> background-color: white;
        border-radius: 10px;
        opacity: revert;
    }

    .index > * {
        margin: 10px;
    }

    input:focus {
        outline: 1px solid var(--primary);
    }

    input {
        height: 40px;
        line-height: 40px;
    }

    #urlImmagine {
        height: 50px;
        line-height: 27px;
    }

    .info {
        text-align: center;
        font-style: normal;
        font-weight: bold;
        padding: 1rem;
        border-top: 1px solid black;
        background-color: lightgrey;
    }

    .info > a {
        text-decoration: none;
        color: black;
        margin-right: 25px;
        margin-left: 25px;
    }


    .table {
        border-collapse: collapse;
        background-color: white;
        font-size: large;
        font-weight: normal;
        font-style: normal;
        font-family: Myriad;
    }

    .table > thead {
        visibility: hidden;
    }

    .table tr {
        border: none;
        margin: .5rem;
        padding: .5rem;
        display: block;
    }

    .table > tbody {
        border: 1px solid black;
        border-radius: 20px;
    }

    .table > tbody td {
        display: block;
        border-bottom: 1px solid black;
        text-align: right;
        padding: .5rem;
    }

    .table > tbody td:before {
        content: attr(data-head);
        float: left;
        font-weight: bold;
        text-transform: uppercase;
    }

    .container {
        margin: 10px;
        justify-content: space-between;
        height: 100vh;
    }

    div#header {
        background-color: rgba(0, 0, 0, 0);
        background-image: url("/FoodOut/covers/${ristorante.urlImmagine}");
        background-size: cover;
        background-repeat: no-repeat;
        background-position: center center;
        height: 500px;
        position: relative;
    }

    .search {
        background-color: rgba(0, 0, 0, 0.53);
        border-radius: 5px;
        max-height: 500px;
        padding: 10px;
        margin-right: 5px;
    }

    .show-ord {
        background-color: rgba(0, 0, 0, 0.53);
        border-radius: 5px;
        max-height: 500px;
        padding: 10px;
        margin-right: 5px;
    }

    .search .tipologia, .search .fitro {
        color: white;
        background-color: var(--primary);
        font-weight: bold;
    }

    .search .title {
        color: white;
    }

    .tipologia, .fitro, .prodotto, .menu, .item {
        padding: 10px;
        border-radius: 5px;
        border: 1px solid black;
        margin: 2px;
        max-height: 100px;

    }


    .prodotto, .menu {
        color: black;
        background-color: lightgrey;
        justify-content: space-between;
    }

    .item{
        color: black;
        background-color: white;
        font-weight: bold;
    }

    .prodotto > img {
        max-height: 60px;
        max-width: 100px;
        margin-left: 5px;
    }


    .title {
        text-align: center;
        padding: 5px;
        height: content-box;
    }

    #sconto {
        height: auto;
        margin-right: 5px;
    }

    #sconto:focus {
        outline: none;
    }

    #links {
        background-color: var(--primary);
        padding: 3px;
        border: 1px solid var(--primary);
        border-radius: 10px;
        margin: 2px;
    }

    #links a {
        text-decoration: none;
        color: white;
        font-family: Myriad;
        font-weight: bold;
        font-style: normal;
        margin: 5px;
    }

    .prodotto, .menu, .tipologia, .item {
        cursor: pointer;
    }

    .tipologia{
        text-decoration: none;
    }

    .show-menu {
        position: relative;
    }

    #plus{
        position: absolute;
        top: 10px;
        right: 10px;
        cursor: pointer;
    }

    .show-ord{
        height: fit-content;
    }

    .prodotto, .menu {
        color: black;
        background-color: lightgrey;
        justify-content: space-between;
    }

    .prodotto > img {
        max-height: 60px;
        max-width: 100px;
        margin-left: 5px;
    }

    .show-ord * {
        margin: 3px;
    }

    .ord_info{
        color: white;
    }

    .deficit{
        color: white;
        padding: 10px;
        font-size: 15px;
        font-weight: bold;
        font-family: Myriad;
        text-align: center;
    }



</style>
<div class="app">
    <div class="cell grid-x" id="header">
        <nav class="grid-y navbar align-center cell">
            <img src="/FoodOut/images/logo.png" class="fluid-image" id="logo">
            <div id="links">
                <a href="${pageContext.request.contextPath}/ristorante/show-info?id=${ristorante.codice}">
                    Info </a>
                <a href="${pageContext.request.contextPath}/ristorante/show-menu?id=${ristorante.codice}"/>
                Menu </a>
                <a href="${pageContext.request.contextPath}/ristorante/show-recensioni?id=${ristorante.codice}">
                    Recensioni </a>
            </div>
        </nav>
        <section class="cell w100 grid-x container">
            <form class="cell w15 grid-x search">
                <section class="grid-x cell w100">
                    <h3 class="cell w100 title" style="color:white"> Tipologie: </h3>
                    <div class="tipologie cell grid-x">
                        <c:if test="${not empty ristorante.tipologie}">
                            <c:forEach items="${ristorante.tipologie}" var="tipologia">
                                <a href="#${tipologia.nome}" class="field cell w100 tipologia">
                                    <span style="font-style: italic"> ${tipologia.nome} </span>
                                </a>
                            </c:forEach>
                        </c:if>
                        <c:if test="${not empty menus}">
                            <a class="field cell w100 tipologia" href="#Menu"><span style="font-style: italic">Menu</span></a>
                        </c:if>
                    </div>
                    <h3 class="cell w100 title"> Filtri: </h3>
                    <div class="cell grid-x align-center fitro">
                        <input type="checkbox" id="sconto" name="sconto" value="1">
                        <label for="sconto"> Sconto </label>
                    </div>
                </section>
            </form>
            <div class="cell w50 grid-x justify-center show-menu">
                <div class="grid-x justify-center info-ris cell">
                    <fieldset class="grid-x cell w100 index">
                        <c:choose>
                            <c:when test="${not empty ristorante.prodotti}">
                                <c:if test="${countProdValidi>0}">
                                    <h2 class="cell"> Prodotti </h2>
                                    <c:forEach items="${ristorante.tipologie}" var="tipologia">
                                        <h3 class="cell" style="font-style: italic"><a name="${tipologia.nome}">${tipologia.nome}</a></h3>
                                        <c:forEach items="${ristorante.prodotti}" var="prodotto">
                                            <c:if test="${tipologia.nome.equals(prodotto.tipologia.nome)}">
                                                <c:if test="${prodotto.valido}">
                                                    <label class="field cell w100 prodotto grid-x" onclick="showProdDetails(this)" title="Clicca per acquistare">
                                                        <div class="w50">
                                                            <div class="w80" style="font-weight: bold;">${prodotto.nome}</div>
                                                        </div>
                                                        <c:if test="${not empty prodotto.urlImmagine}">
                                                        <img class="w80" src="/FoodOut/covers/${prodotto.urlImmagine}">
                                                        </c:if>
                                                        <input style="display: none" id="id" name="id" value="${prodotto.codice}"/>

                                                    </label>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </c:if>
                            </c:when>
                            <c:otherwise> <h2> Non sono presenti prodotti </h2> </c:otherwise>
                        </c:choose>
                        <c:if test="${countMenuValidi>0}">
                            <h2 class="cell"><a name="Menu"> Menu</a> </h2>
                            <c:forEach items="${menus}" var="menu">
                                <c:if test="${menu.valido}">
                                <label class="field cell w100 menu" style="font-weight: bold" onclick="showMenuDetails(this)" title="Clicca per acquistare" >
                                    <span> ${menu.nome} </span>
                                    <input style="display: none" id="id" name="id" value="${menu.codice}"/>
                                </label>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </fieldset>
                </div>
            </div>
            <form class="cell w20 grid-x show-ord">
                <section class="grid-x cell w100">
                    <h3 class="cell w100 title" style="color:white"> Ordine: </h3>
                    <c:if test="${not empty cart}">
                    <div class="items cell grid-x">
                        <c:if test="${not empty cart.ordineItems}">
                            <c:forEach items="${cart.ordineItems}" var="item">
                                <div class="field cell w100 item">
                                    <span style="font-style: italic"> ${item.off.nome} </span>
                                    <span> ${item.quantita} </span>
                                </div>
                            </c:forEach>
                        </c:if>

                        <div class="cell ord_info">
                            <span style="font-weight: bold"> Subtotale: </span> ${cart.totale}€
                        </div>
                        <div class="cell ord_info">
                            <span style="font-weight: bold"> Costo consegna: </span> ${ristorante.tassoConsegna}€
                        </div>
                        <div class="cell tot ord_info">
                            <span style="font-weight: bold"> Totale : </span>${cart.totale + ristorante.tassoConsegna}€
                        </div>
                    </div>
                        <span class="grid-x cell justify-center">
                        <c:choose>
                            <c:when test="${cart.totale > ristorante.spesaMinima }">
                                <button type="submit" class="btn primary"> Acquista </button>
                            </c:when>
                            <c:otherwise>
                            <div style="background-color: var(--primary)">
                                <div class="deficit"> Mancano ${ristorante.spesaMinima - cart.totale}€ per raggiungere la spesa minima </div>
                            </div>
                            </c:otherwise>
                        </c:choose>
                        </span>

                    </c:if>
                </section>
            </form>
            <input type="number" style="display:none;" id="idRis" name="idRis" value="${ristorante.codice}">
        </section>
    </div>
</div>

</body>
</html>