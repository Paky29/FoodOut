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
        <jsp:param name="styles" value="recensioni"/>
        <jsp:param name="scripts" value="recensioni"/>
    </jsp:include>
    <style>


        div#header {
            background-color: rgba(0, 0, 0, 0);
            background-image: url("/FoodOut/covers/${urlImmagine}");
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center center;
            height: 500px;
            position: relative;
        }

    </style>
</head>
<body>
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
                <a href="${pageContext.request.contextPath}/ristorante/show-info?id=${id}">
                    Info </a>
                <a href="${pageContext.request.contextPath}/ristorante/show-menu?id=${id}"/>
                Menu </a>
                <a href="${pageContext.request.contextPath}/ristorante/show-recensioni?id=${id}">
                    Recensioni </a>
            </div>
        </nav>
        <section class="cell w100 justify-center grid-x container">
            <div class="grid-x justify-center align-center index w75">
                <h2 class="cell"> Recensioni per <span style="color: var(--primary); font-style: italic"> ${nome}</span> </h2>
                    <c:choose>
                        <c:when test="${numRecensioni!=0}">
                                <c:forEach items="${ordini}" var="ordine">
                                    <c:if test="${ordine.voto!=0}">
                                    <label class="field cell w40 ordine grid-x">
                                        <div class="w100">
                                            <div style="font-weight: bold;">${ordine.utente.nome}</div>
                                            <div class="w40 align-center">
                                                <span class="w10"> Voto: </span>
                                                <div class="cell">
                                                <c:forEach var="counter" begin="1" end="${ordine.voto}">
                                                    <%@include file="../../../icons/moto.svg" %>
                                                </c:forEach>
                                                </div>
                                            </div>

                                                <span> Giudizio:</span>
                                                <label for="giudizio" class="field cell w40">
                                                    <textarea rows="3" cols="100" type="text" name="giudizio" id="giudizio" maxlength="200" disabled>${ordine.giudizio}</textarea>
                                                </label>


                                        </div>
                                    </label>

            </c:if>
            </c:forEach>
            </div>
            <div class="cell justify-center ">
                <jsp:include page="../partials/paginator.jsp">
                    <jsp:param name="risorsa" value="show-recensioni"/>
                </jsp:include>
            </div>

                            </c:when>
                            <c:otherwise><h2> Non sono presenti recensioni </h2> </div></c:otherwise>
                        </c:choose>
        </section>

    </div>
</div>
</body>
</html>
