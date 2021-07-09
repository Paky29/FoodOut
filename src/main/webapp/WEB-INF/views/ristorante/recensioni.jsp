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
        #welcome, #back {
            cursor: pointer;
        }

    </style>
</head>
<body>
<div class="app">
    <div class="cell grid-x" id="header">
        <div id="container-links" class="cell" style="justify-content: flex-end">
            <div class="links">
                <a href="${pageContext.request.contextPath}/utente/login"> Accedi </a>
                <a href="${pageContext.request.contextPath}/utente/signup"> Registrati </a>
            </div>
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
                    <c:choose>
                            <c:when test="${not empty ordini}">
                                <h2 class="cell"> Recensioni per <span style="color: var(--primary); font-style: italic"> ${nome}</span> </h2>
                                <c:forEach items="${ordini}" var="ordine">
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
                                            <c:if test="${not empty ordine.giudizio}">
                                                <span> Info:</span>
                                                <label for="giudizio" class="field cell w40">
                                                    <textarea rows="3" cols="100" type="text" name="giudizio" id="giudizio" maxlength="200" disabled>${ordine.giudizio}</textarea>
                                                </label>
                                            </c:if>


                                        </div>
                                    </label>
                                </c:forEach>
                            </c:when>
                            <c:otherwise><h2> Non sono presenti recensioni </h2></c:otherwise>
                        </c:choose>
                    </div>
                <div class="cell justify-center ">
                    <jsp:include page="../partials/paginator.jsp">
                        <jsp:param name="risorsa" value="show-recensioni"/>
                    </jsp:include>
                </div>
            <footer class="info grid-x cell justify-center align-center">
                <a href="faq.jsp" class="cell w10"> FAQ </a>
                <a href="/" class="cell w10"> Chi siamo </a>
                <a href="/" class="cell w10"> Collabora con noi</a>
                <a href="contatti.jsp" class="cell w10"> Contatti</a>
            </footer>
        </section>

    </div>
</div>
</body>
</html>
