<%@ page import="model.ristorante.Ristorante" %>
<%@ page import="model.tipologia.Tipologia" %>
<%@ page import="java.util.StringJoiner" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Menu"/>
        <jsp:param name="styles" value="info_ris_crm"/>
        <jsp:param name="scripts" value="menu"/>
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

    .item{
        justify-content: space-between;
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

    .amount {
        display: none; /* Hidden by default */
        position: fixed; /* Stay in place */
        z-index: 1; /* Sit on top */
        padding-top: 100px; /* Location of the box */
        left: 0;
        top: 0;
        width: 100%; /* Full width */
        height: 100%; /* Full height */
        overflow: auto; /* Enable scroll if needed */
        background-color: rgb(0,0,0); /* Fallback color */
        background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
    }

    .amount-content {
        background-color: #fefefe;
        margin: auto;
        padding: 20px;
        border: 1px solid #888;
        width: 80%;
        box-sizing: unset;
    }

    .show-amount * {
        margin: 5px;
    }

    .close{
        justify-content: flex-end;
        margin-bottom: 5px;
    }

    fieldset  {
        border: none;
    }




</style>
<div class="app">

    <div class="cell grid-x" id="header">
        <nav class="grid-y navbar align-center cell">
            <img onclick="goBack()" src="/FoodOut/images/logo.png" class="fluid-image" id="logo">
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

                                                    <div id="amountProd" class="amount grid-x">
                                                        <div class="amount-content">
                                                            <span class="close grid-x" onclick="closeAmount(this)">
                                                                <%@include file="../../../icons/x.svg"%>
                                                            </span>
                                                            <form class=" grid-x justify-center align-center" action="${pageContext.request.contextPath}/ordineItem/add-prodotto-item" method="post">
                                                                <fieldset class="grid-y cell w100 show-amount">
                                                                    <h2 id="nome" class="w100" style="text-align: center"> ${prodotto.nome} </h2>
                                                                    <div class="w80" style="align-self: center" >
                                                                    <c:if test="${not empty prodotto.urlImmagine}">
                                                                       <img  style="max-height: 300px; max-width: 300px" src="/FoodOut/covers/${prodotto.urlImmagine}">
                                                                    </c:if>
                                                                    </div>
                                                                    <div id="prezzo" class="grid-x cell w100 align-center" >
                                                                        <span> Prezzo: </span> <span style="font-weight: bold">${prodotto.prezzo}€ </span>
                                                                    </div>
                                                                    <c:if test="${prodotto.sconto>0}">
                                                                        <c:set var="risparmio" value="${((prodotto.prezzo*prodotto.sconto)/100)}"/>
                                                                        <div id="scontoP" class="grid-x cell w100 align-center" >
                                                                        <span> Sconto: </span> <span style="font-weight: bold"> ${prodotto.sconto}% </span> <span style="color: red"> -<fmt:formatNumber value="${risparmio}" type="currency"/></span>
                                                                    </div>
                                                                    </c:if>
                                                                    <div id="ingredienti" class="grid-x cell w100 align-center" >
                                                                        <span> Ingredienti: </span> ${prodotto.ingredienti}
                                                                    </div>
                                                                    <div id="info" class="grid-x cell w100 align-center" >
                                                                        <span> Info: </span> ${prodotto.info}
                                                                    </div>
                                                                    <div id="amount" class="grid-x cell w100 align-center" >
                                                                        <span> Quantità: </span>
                                                                        <input type="number" name="quantita" id="quantita" value="1" max="99" min="1">
                                                                    </div>
                                                                    <input style="display: none" type="number" name="id" id="id" value="${prodotto.codice}">
                                                                    <input style="display: none" type="number" name="idRis" id="idRis" value="${ristorante.codice}">
                                                                    <span class="grid-x cell justify-center">
                                                                        <button type="submit" class="btn primary"> Aggiungi </button>
                                                                    </span>
                                                                </fieldset>
                                                            </form>

                                                        </div>
                                                    </div>

                                                    <label class="field cell w100 prodotto grid-x" onclick="showProdMenuDetails(this)" title="Clicca per acquistare">
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

                                    <div id="amountMenu" class="amount">
                                        <div class="amount-content">
                                            <span class="close grid-x" onclick="closeAmount(this)">
                                                <%@include file="../../../icons/x.svg"%>
                                            </span>
                                            <form class=" grid-x justify-center align-center" action="${pageContext.request.contextPath}/ordineItem/add-menu-item" method="post">
                                                <fieldset class="grid-y cell w100 show-amount">
                                                    <h2 id="nome" class="w100" style="text-align: center"> ${menu.nome} </h2>
                                                    <div id="prezzo" class="grid-x cell w100 align-center" >
                                                        <span> Prezzo: </span> <span style="font-weight: bold">${menu.prezzo}€ </span>
                                                    </div>
                                                    <c:if test="${menu.sconto>0}">
                                                        <c:set var="risparmio" value="${((menu.prezzo*menu.sconto)/100)}"/>
                                                        <div id="scontoP" class="grid-x cell w100 align-center" >
                                                            <span> Sconto: </span> <span style="font-weight: bold"> ${menu.sconto}% </span> <span style="color: red"> -<fmt:formatNumber value="${risparmio}" type="currency" /></span>
                                                        </div>
                                                    </c:if>
                                                    <ul class="grid-x cell w100 align-center">
                                                    <c:forEach items="${menu.prodotti}" var="prodotto">
                                                        <li class="cell"> <a style="text-decoration: none" href=""> ${prodotto.nome}</a> </li>
                                                    </c:forEach>
                                                    </ul>
                                                    <div id="amount" class="grid-x cell w100 align-center" >
                                                        <span> Quantità: </span>
                                                        <input type="number" name="quantita" id="quantita" value="1" max="99" min="1">
                                                    </div>
                                                    <input style="display: none" type="number" name="id" id="id" value="${menu.codice}">
                                                    <input style="display: none" type="number" name="idRis" id="idRis" value="${ristorante.codice}">
                                                    <span class="grid-x cell justify-center">
                                                        <button type="submit" class="btn primary"> Aggiungi </button>
                                                    </span>
                                                </fieldset>
                                            </form>

                                        </div>
                                    </div>


                                <label class="field cell w100 menu" style="font-weight: bold" onclick="showProdMenuDetails(this)" title="Clicca per acquistare" >
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
                                    <span style="color: darkgrey" class="grid-x align-center">
                                        <span> ${item.quantita} </span>
                                        <span onclick="removeItem(this)"> <%@include file="../../../icons/delete.svg"%> </span>
                                        <c:choose>
                                            <c:when test="${item.off.getClass().getName().contains(\"Menu\")}">
                                                <input class="item-cod" style="display: none" type="number" name="codiceMenu" value="${item.off.codice}"
                                            </c:when>
                                            <c:otherwise>
                                                <input class="item-cod" style="display: none" type="number" name="codiceProdotto" value="${item.off.codice}"
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                            </c:forEach>


                        <div class="cell ord_info">
                            <span style="font-weight: bold"> Subtotale: </span> <fmt:formatNumber value="${cart.totale}" type="currency"/>
                        </div>
                        <div class="cell ord_info">
                            <span style="font-weight: bold"> Costo consegna: </span> <fmt:formatNumber value="${ristorante.tassoConsegna}" type="currency"/>
                        </div>
                        <div class="cell tot ord_info">
                            <span style="font-weight: bold"> Totale : </span><fmt:formatNumber value="${cart.totale + ristorante.tassoConsegna}" type="currency"/>
                        </div>
                    </div>
                        <span class="grid-x cell justify-center">
                        <c:choose>
                            <c:when test="${cart.totale > ristorante.spesaMinima }">
                                <button type="submit" class="btn primary"> Acquista </button>
                            </c:when>
                            <c:otherwise>
                            <div style="background-color: var(--primary)">
                                <div class="deficit"> Mancano <fmt:formatNumber value="${ristorante.spesaMinima - cart.totale}" type="currency"/> per raggiungere la spesa minima </div>
                            </div>
                            </c:otherwise>
                        </c:choose>
                        </span>
                        </c:if>

                    </c:if>
                </section>
            </form>
            <input type="number" style="display:none;" id="idRis" name="idRis" value="${ristorante.codice}">
        </section>
    </div>
</div>

</body>
</html>
