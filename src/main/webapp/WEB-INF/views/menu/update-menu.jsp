<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Modifica menu"/>
        <jsp:param name="scripts" value="update_menu_validation"/>
    </jsp:include>
    <style>
        .app {
            background: linear-gradient(var(--primary), white);
        }

        .add-ris {
            padding: 1rem;
            border-radius: 10px;
        }

        .add-ris > * {
            margin: 10px;
        }

        button {
            border-radius: 3px;
        }

        input {
            height: 50px;
            line-height: 27px;
        }

        button {
            margin: 5px;
        }

        @media screen and (min-width: 900px) {
            form {
                max-width: 50%;
            }

        }
    </style>
</head>
<body>
<div class="app grid-x justify-center align-center">
    <c:if test="${not empty alert}">
        <%@ include file="../partials/alert.jsp"%>
    </c:if>
    <form id="prodotto " class="grid-x justify-center" action="${pageContext.request.contextPath}/menu/update" method="post" novalidate>
        <fieldset class="grid-x cell w90 add-ris justify-center">
            <h2 class="cell"> Modifica menu</h2>
            <label for="nome" class="grid-x field cell w82">
                <span style="font-weight: bold" class="field cell w20"> Nome: </span>
                <span class="cell grid-x">
                <input class="cell" type="text" name="nome" id="nome" value="${menu.nome}" placeholder="Nome" pattern="^(\w|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" required>
                <small class="cell errMsg">  </small>
                </span>
            </label>
            <label for="prezzo" class="grid-x field w40 cell">
                <span class="field cell" style="font-weight: bold;margin-bottom: 3px;">Prezzo: </span>
                <span class="cell grid-x">
                <input class="cell" type="number" name="prezzo" id="prezzo" value="${menu.prezzo}" placeholder="Prezzo (€)" min="0.01" step="0.01" required>
                    <small class="cell errMsg"></small>
                </span>
            </label>
            <label for="sconto" class="grid-x field w40 cell">
                <span class="field cell" style="font-weight: bold;margin-bottom: 3px;">Sconto: </span>
                <span class="cell grid-x">
                <input class="cell" type="number" name="sconto" id="sconto" value="${menu.sconto}" placeholder="Sconto (%)" max="100" min="0" required>
                    <small class="cell errMsg"></small>
                </span>
            </label>
            <label for="prodotti" class="field w70 cell">
            <span style="margin-right: 10px; font-weight: bold;">
                Prodotti:
            </span>
                <select name="prodotti" id="prodotti" multiple>
                    <c:forEach items="${ristorante.prodotti}" var="prodotto">
                        <c:if test="${prodotto.valido}">
                            <c:set var="inMenu" value="false"/>
                            <c:forEach items="${menu.prodotti}" var="prodMenu">
                                <c:if test="${prodotto.codice==prodMenu.codice}">
                                    <option value="${prodotto.codice}" selected> ${prodotto.nome} </option>
                                    <c:set var="inMenu" value="true"/>
                                </c:if>
                            </c:forEach>
                            <c:if test="${inMenu==false}">
                                <option value="${prodotto.codice}"> ${prodotto.nome} </option>
                            </c:if>
                        </c:if>
                    </c:forEach>
                </select>
            </label>
            <input style="display: none" type="number" name="id" id="id" value="${menu.codice}" readonly>
            <input style="display: none" type="number" name="idRis" id="idRis" value="${ristorante.codice}" readonly>
            <button type="submit" class="btn primary w30" value="again" name="button"> Modifica</button>
            <button type="submit" class="btn primary w30"
                    formaction="${pageContext.request.contextPath}/menu/update-validita" formmethod="post">
                <c:choose>
                    <c:when test="${menu.valido}">
                        Invalida
                    </c:when>
                    <c:otherwise>
                        Valida
                    </c:otherwise>
                </c:choose>
            </button>
            </span>
        </fieldset>
    </form>
</div>
</body>
</html>
