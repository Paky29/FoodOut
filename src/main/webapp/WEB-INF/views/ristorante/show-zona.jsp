<%@ page import="model.ristorante.Ristorante" %>
<%@ page import="model.tipologia.Tipologia" %>
<%@ page import="java.util.StringJoiner" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Scegli ristorante"/>
        <jsp:param name="styles" value="show_zona"/>
        <jsp:param name="scripts" value="menu_admin"/>
    </jsp:include>
</head>
<body>
<div class="app">
    <div class="cell grid-x" id="header">
        <nav class="grid-y navbar align-center cell">
            <img src="/FoodOut/images/logo.png" class="fluid-image" id="logo">
            <div id="user">
                <span class="account" style="color: white">
                    <%@include file="../../../icons/user.svg"%> <%--cambiare con icona user--%>
                    Benvenuto, ${utenteSession.nome}
                </span>
            </div>
            <label class="field command w100 justify-center">
                <input type="text" placeholder="Cerca Ristoranti">
            </label>
        </nav>
        <section class="cell w100 grid-x container">
            <form class="cell w20 grid-x search">
                <section class="grid-x cell w100">
                    <h3 class="cell w100 title" style="color:white"> Filtri: </h3>
                        <c:if test="${not empty tipologie}">
                            <c:forEach items="${tipologie}" var="tipologia">
                                <div class="cell grid-x align-center filtro">
                                    <input type="checkbox" id="tipologia" name="tipologia" value="${tipologia.nome}">
                                    <label for="tipologia"> ${tipologia.nome} </label>
                                </div>
                            </c:forEach>
                        </c:if>
                    <div class="cell grid-x align-center filtro">
                        <input type="checkbox" id="sconto" name="sconto" value="1">
                        <label for="sconto"> Sconto </label>
                    </div>
                    <div class="cell grid-x align-center filtro">
                        <input type="checkbox" id="gratis" name="gratis" value="1">
                        <label style="font-size: 15px;" for="gratis"> Consegna gratis </label>
                    </div>
                </section>
            </form>
            <div class="cell w75 grid-x justify-center show-ris">
                <div class="grid-x justify-center info-ris cell">
                    <fieldset class="grid-x cell w100 index">
                        <c:choose>
                            <c:when test="${not empty ristoranti}">
                                    <h2 class="cell"> Ristoranti </h2>
                                        <c:forEach items="${ristoranti}" var="ristorante">
                                                    <label class="field cell w100 ristorante grid-x" onclick="showRisDetails()" title="Clicca per visitare">
                                                        <div class="w70">
                                                        <div class="w80" style="font-weight: bold;">${ristorante.nome}</div>
                                                        <c:forEach begin="0" end="2" var="counter">
                                                            <span class="w80" style="color:black;font-weight: normal; font-style: italic"> ${ristorante.tipologie[counter].nome}</span>
                                                        </c:forEach>
                                                            <div class="w80">
                                                                <c:forEach var="counter" begin="1" end="${ristorante.rating}">
                                                                    <%@include file="../../../icons/moto.svg" %>
                                                                </c:forEach>
                                                            </div>
                                                        </div>

                                                        <img class="w80" src="/FoodOut/covers/${ristorante.urlImmagine}">
                                                        <input style="display: none" id="id" name="id" value="${ristorante.codice}"/>
                                                    </label>
                                        </c:forEach>
                            </c:when>
                            <c:otherwise> <h2> Non sono presenti ristoranti </h2> </c:otherwise>
                        </c:choose>
                    </fieldset>
                </div>
                <div class="cell justify-center ">
                    <jsp:include page="../partials/paginator.jsp">
                        <jsp:param name="risorsa" value="zona"/>
                    </jsp:include>
                </div>
            </div>
        </section>

    </div>
</div>

</body>
</html>
