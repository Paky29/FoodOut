<%@ page import="model.ristorante.Ristorante" %>
<%@ page import="model.tipologia.Tipologia" %>
<%@ page import="java.util.StringJoiner" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Menu"/>
        <jsp:param name="styles" value="menu"/>
        <jsp:param name="scripts" value="menu"/>
    </jsp:include>
</head>
<body>
<style>
    div#header {
        background-color: rgba(0, 0, 0, 0);
        background-image: url("/FoodOut/covers/${ristorante.urlImmagine}");
        background-size: cover;
        background-repeat: no-repeat;
        background-position: center center;
        height: 500px;
        position: relative;
    }
    .amount-content .close{
        cursor: pointer;
    }
</style>
<div class="app">

    <div class="cell grid-x" id="header">

            <div id="container-links" class="cell" style="justify-content: flex-end">
                <c:choose>
                    <c:when test="${utenteSession==null}">
                <div class="links">
                    <a href="${pageContext.request.contextPath}/utente/login"> Accedi </a>
                    <a href="${pageContext.request.contextPath}/utente/signup"> Registrati </a>
                </div>
                    </c:when>
                    <c:otherwise>
                        <div class="links">
                        <span class="account" style="color: white" onclick="toProfile()">
                    <%@include file="../../../icons/user.svg" %>
                    <span id="welcome">Benvenuto, ${utenteSession.nome}</span>
                        </span>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        <nav class="grid-y navbar align-center cell">
            <img onclick="goBack()" src="/FoodOut/images/logo.png" class="fluid-image" id="logo" title="Torna indietro">
            <div class="links">
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
                            <a class="field cell w100 tipologia" href="#Menu"><span
                                    style="font-style: italic">Menu</span></a>
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
                    <div class="grid-x cell w100 index" style="border: 1px solid lightgrey">
                        <span class="cell" id="star">
                            <c:if test="${utenteSession!=null}">
                                <c:choose>
                                    <c:when test="${pref==false}">
                                        <span onclick="addToPrefs(${ristorante.codice}, ${utenteSession.id})"> <%@include
                                                file="../../../icons/no_pref.svg" %></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span onclick="removeFromPrefs(${ristorante.codice}, ${utenteSession.id})"><%@include
                                                file="../../../icons/pref.svg" %></span>
                                    </c:otherwise>
                                </c:choose>

                            </c:if>
                        </span>
                        <c:choose>
                            <c:when test="${not empty ristorante.prodotti}">
                                <c:if test="${countProdValidi>0}">
                                    <h2 class="cell"> Prodotti </h2>
                                    <c:forEach items="${ristorante.tipologie}" var="tipologia">
                                        <h3 class="cell" style="font-style: italic"><a
                                                name="${tipologia.nome}">${tipologia.nome}</a></h3>
                                        <c:forEach items="${ristorante.prodotti}" var="prodotto">
                                            <c:if test="${tipologia.nome.equals(prodotto.tipologia.nome)}">
                                                <c:if test="${prodotto.valido}">

                                                    <div id="amountProd" class="amount grid-x">
                                                        <div class="amount-content">
                                                            <span class="close grid-x" onclick="closeAmount(this)">
                                                                <%@include file="../../../icons/x.svg" %>
                                                            </span>
                                                            <form class=" grid-x justify-center align-center"
                                                                  action="${pageContext.request.contextPath}/ordineItem/add-prodotto-item"
                                                                  method="post">
                                                                <fieldset class="grid-y cell w100 show-amount">
                                                                    <h2 id="nome" class="w100"
                                                                        style="text-align: center"> ${prodotto.nome} </h2>
                                                                    <div class="w80" style="align-self: center">
                                                                        <c:if test="${not empty prodotto.urlImmagine}">
                                                                            <img style="max-height: 300px; max-width: 300px"
                                                                                 src="/FoodOut/covers/${prodotto.urlImmagine}">
                                                                        </c:if>
                                                                    </div>
                                                                    <div id="prezzo"
                                                                         class="grid-x cell w100 align-center">
                                                                        <span> Prezzo: </span> <span
                                                                            style="font-weight: bold">${prodotto.prezzo}€ </span>
                                                                    </div>
                                                                    <c:if test="${prodotto.sconto>0}">
                                                                        <c:set var="risparmio"
                                                                               value="${((prodotto.prezzo*prodotto.sconto)/100)}"/>
                                                                        <div id="scontoP"
                                                                             class="grid-x cell w100 align-center">
                                                                            <span> Sconto: </span> <span
                                                                                style="font-weight: bold"> ${prodotto.sconto}% </span>
                                                                            <span style="color: red"> -<fmt:formatNumber
                                                                                    value="${risparmio}"
                                                                                    type="currency"/></span>
                                                                        </div>
                                                                    </c:if>
                                                                    <div id="ingredienti"
                                                                         class="grid-x cell w100 align-center">
                                                                        <span> Ingredienti: </span> ${prodotto.ingredienti}
                                                                    </div>
                                                                    <div id="info"
                                                                         class="grid-x cell w100 align-center">
                                                                        <span> Info: </span> ${prodotto.info}
                                                                    </div>
                                                                    <div id="amount"
                                                                         class="grid-x cell w100 align-center">
                                                                        <span> Quantità: </span>
                                                                        <input type="number" name="quantita"
                                                                               id="quantita" value="1" max="99" min="1">
                                                                    </div>
                                                                    <input style="display: none" type="number" name="id"
                                                                           id="id" value="${prodotto.codice}">
                                                                    <input style="display: none" type="number"
                                                                           name="idRis" id="idRis"
                                                                           value="${ristorante.codice}">
                                                                    <span class="grid-x cell justify-center">
                                                                        <c:choose>
                                                                            <c:when test="${isOpen==true}"><button type="submit" class="btn primary"> Aggiungi </button></c:when>
                                                                        <c:otherwise>
                                                                            <span style="color: red;font-weight: bold">Non puoi ordinare, ristorante chiuso</span>
                                                                        </c:otherwise>
                                                                        </c:choose>
                                                                    </span>
                                                                </fieldset>
                                                            </form>

                                                        </div>
                                                    </div>

                                                    <label class="field cell w100 prodotto grid-x"
                                                           onclick="showProdMenuDetails(this)"
                                                           title="Clicca per acquistare">
                                                        <div class="w50">
                                                            <div class="w80"
                                                                 style="font-weight: bold;">${prodotto.nome}</div>
                                                        </div>
                                                        <c:if test="${not empty prodotto.urlImmagine}">
                                                            <img class="w80"
                                                                 src="/FoodOut/covers/${prodotto.urlImmagine}">
                                                        </c:if>
                                                        <input style="display: none" id="id" name="id"
                                                               value="${prodotto.codice}"/>

                                                    </label>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </c:if>
                            </c:when>
                            <c:otherwise><h2> Non sono presenti prodotti </h2></c:otherwise>
                        </c:choose>
                        <c:if test="${countMenuValidi>0}">
                            <h2 class="cell"><a name="Menu"> Menu</a></h2>
                            <c:forEach items="${menus}" var="menu">
                                <c:if test="${menu.valido}">

                                    <div id="amountMenu" class="amount">
                                        <div class="amount-content">
                                            <span class="close grid-x" onclick="closeAmount(this)">
                                                <%@include file="../../../icons/x.svg" %>
                                            </span>
                                            <form class=" grid-x justify-center align-center"
                                                  action="${pageContext.request.contextPath}/ordineItem/add-menu-item"
                                                  method="post">
                                                <fieldset class="grid-y cell w100 show-amount">
                                                    <h2 id="nome" class="w100"
                                                        style="text-align: center"> ${menu.nome} </h2>
                                                    <div id="prezzo" class="grid-x cell w100 align-center">
                                                        <span> Prezzo: </span> <span
                                                            style="font-weight: bold">${menu.prezzo}€ </span>
                                                    </div>
                                                    <c:if test="${menu.sconto>0}">
                                                        <c:set var="risparmio"
                                                               value="${((menu.prezzo*menu.sconto)/100)}"/>
                                                        <div id="scontoP" class="grid-x cell w100 align-center">
                                                            <span> Sconto: </span> <span
                                                                style="font-weight: bold"> ${menu.sconto}% </span> <span
                                                                style="color: red"> -<fmt:formatNumber
                                                                value="${risparmio}" type="currency"/></span>
                                                        </div>
                                                    </c:if>
                                                    <ul class="grid-x cell w100 align-center">
                                                        <c:forEach items="${menu.prodotti}" var="prodotto">
                                                            <li class="cell"><a style="text-decoration: none"
                                                                                href=""> ${prodotto.nome}</a></li>
                                                        </c:forEach>
                                                    </ul>
                                                    <div id="amount" class="grid-x cell w100 align-center">
                                                        <span> Quantità: </span>
                                                        <input type="number" name="quantita" id="quantita" value="1"
                                                               max="99" min="1">
                                                    </div>
                                                    <input style="display: none" type="number" name="id" id="id"
                                                           value="${menu.codice}">
                                                    <input style="display: none" type="number" name="idRis" id="idRis"
                                                           value="${ristorante.codice}">
                                                    <span class="grid-x cell justify-center">
                                                        <button type="submit" class="btn primary"> Aggiungi </button>
                                                    </span>
                                                </fieldset>
                                            </form>

                                        </div>
                                    </div>


                                    <label class="field cell w100 menu" style="font-weight: bold"
                                           onclick="showProdMenuDetails(this)" title="Clicca per acquistare">
                                        <span> ${menu.nome} </span>
                                        <input style="display: none" id="id" name="id" value="${menu.codice}"/>
                                    </label>
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
            </div>
            <c:choose>
                <c:when test="${not empty utenteSession}">
                    <form class="cell w20 grid-x show-ord" action="/FoodOut/ordine/pagamento">
                </c:when>
                <c:otherwise>
                    <form class="cell w20 grid-x show-ord" action="/FoodOut/ordine/signup">
                        </c:otherwise>
                    </c:choose>
                    <section class="grid-x cell w100">
                        <c:choose>
                            <c:when test="${isOpen==true}">
                                <h3 class="cell w100 title" style="color:white"> Ordine: </h3>
                            </c:when>
                            <c:otherwise>
                                <h3 class="cell w100 title" style="color:white"> Chiuso </h3>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${not empty cart}">
                            <div class="items cell grid-x">
                            <c:if test="${not empty cart.ordineItems}">
                                <c:forEach items="${cart.ordineItems}" var="item">
                                    <div class="field cell w100 item">
                                        <span style="font-style: italic"> ${item.off.nome} </span>
                                        <span style="color: darkgrey" class="grid-x align-center">
                                        <span> ${item.quantita} </span>
                                        <span onclick="removeItem(this)"> <%@include
                                                file="../../../icons/delete.svg" %> </span>
                                        <c:choose>
                                            <c:when test="${item.off.getClass().getName().contains(\"Menu\")}">
                                                <input class="item-cod" style="display: none" type="number"
                                                       name="codiceMenu" value="${item.off.codice}"
                                            </c:when>
                                            <c:otherwise>
                                                <input class="item-cod" style="display: none" type="number"
                                                       name="codiceProdotto" value="${item.off.codice}"
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                    </div>
                                </c:forEach>
                                <div class="cell ord_info">
                                    <span style="font-weight: bold"> Subtotale: </span> <fmt:formatNumber
                                        value="${cart.totale}" type="currency"/>
                                </div>
                                <div class="cell ord_info">
                                    <span style="font-weight: bold"> Costo consegna: </span> <fmt:formatNumber
                                        value="${ristorante.tassoConsegna}" type="currency"/>
                                </div>
                                <div class="cell tot ord_info">
                                    <span style="font-weight: bold"> Totale : </span><fmt:formatNumber
                                        value="${cart.totale + ristorante.tassoConsegna}" type="currency"/>
                                </div>
                                </div>
                                <span class="grid-x cell justify-center">
                        <c:choose>
                            <c:when test="${cart.totale > ristorante.spesaMinima }">
                                <button type="submit" class="btn primary"> Acquista </button>
                            </c:when>
                            <c:otherwise>
                            <div style="background-color: var(--primary)">
                                <div class="deficit"> Mancano <fmt:formatNumber
                                        value="${ristorante.spesaMinima - cart.totale}" type="currency"/> per raggiungere la spesa minima </div>
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
