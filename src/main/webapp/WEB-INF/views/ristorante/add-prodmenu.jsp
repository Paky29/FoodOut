<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Aggiungi prodotto-menu"/>
        <jsp:param name="scripts" value="add_prodmenu_validation"/>
    </jsp:include>
    <style>
        .app {
            background: linear-gradient(var(--primary), white);
        <%--background-image: url("http://localhost:8080/images/image.jpg");--%>
        }
        .add-ris{
            padding: 1rem; <%--dimensione relativa al root--%>
            background-color:white;
            border-radius: 10px;
        }

        .add-ris > * {
            margin:10px;
        }

        button {
            border-radius: 3px;
        }

        input {
            height: 50px;
            line-height: 27px;
        }

        button{
            margin:5px;
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
    <div class="grid-x justify-center align-center">
<form id="prodotto" class="grid-x justify-center" action="${pageContext.request.contextPath}/prodotto/create" method="post" enctype="multipart/form-data" novalidate>
    <fieldset class="grid-x cell w90 add-ris justify-center">
        <h2 class="cell"> Aggiungi prodotto</h2>
        <label for="nomeProd" class="field cell w82 grid-x">
            <input class="cell" type="text" name="nome" id="nomeProd" placeholder="Nome" pattern="^(\w|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="prezzoProd" class="field w40 cell grid-x">
            <input class="cell" type="number" name="prezzo" id="prezzoProd" placeholder="Prezzo (€)" min="0.01" step="0.01" required>
            <small class="errMsg cell"></small>
        </label>
        <label for="scontoProd" class="field w40 cell grid-x">
            <input class="cell" type="number" name="sconto" id="scontoProd" placeholder="Sconto (%)" max="100" min="0" required>
            <small class="errMsg cell"></small>
        </label>
        <label for="tipologia" class="field w65 cell grid-x">
            <span style="margin-right: 10px">
                Tipologia:
            </span>
            <select name="tipologia" id="tipologia">
                <c:forEach items="${tipologie}" var="tipologia">
                    <option value="${tipologia.nome}">
                        ${tipologia.nome}
                    </option>
                </c:forEach>
            </select>
        </label>
        <label for="urlImmagine" class="field w80 cell grid-x">
            <input class="cell" type="file" name="urlImmagine" id="urlImmagine" placeholder="Immagine del tuo ristorante" accept="image/*">
            <small class="errMsg cell"></small>
        </label>
        <label for="ingredienti" class="field cell w80 grid-x">
            <textarea rows="4" cols="100" type="text" name="ingredienti" id="ingredienti" maxlength="100" placeholder="Ingredienti del prodotto, separati da ','" required></textarea>
            <small class="errMsg cell"></small>
        </label>
        <label for="info" class="field cell w80 grid-x">
            <textarea rows="4" cols="100" type="text" name="info" id="info" maxlength="100" placeholder="Informazioni extra sul prodotto" required></textarea>
            <small class="errMsg cell"></small>
        </label>
        <input style="display: none" name="id" id="idProd" value="${ristorante.codice}" readonly>
        <input style="display: none" name="function" id="functionProd" value="${function}" readonly>

        <span class="grid-x cell justify-center">
            <button type="submit" class="btn primary w30" value="again" name="button"> Aggiungi prodotto </button>
            <button type="submit" class="btn primary w30" value="stop" name="button">Salva ed esci</button>
        </span>
    </fieldset>
</form>

<form id="menu" class="grid-x justify-center" action="${pageContext.request.contextPath}/menu/create" method="post" novalidate>
    <fieldset class="grid-x cell w90 add-ris justify-center">
        <h2 class="cell"> Aggiungi menu</h2>
        <label for="nomeMenu" class="field cell w80 grid-x">
            <input type="text" name="nome" id="nomeMenu" placeholder="Nome" pattern="^(\w|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" required>
            <small class="errMsg cell"></small>
        </label>
        <label for="prezzoMenu" class="field w40 cell grid-x">
            <input type="number" name="prezzo" id="prezzoMenu" placeholder="Prezzo (€)" min="0.01" step="0.01" required>
            <small class="errMsg cell"></small>
        </label>
        <label for="scontoMenu" class="field w40 cell grid-x">
            <input type="number" name="sconto" id="scontoMenu" placeholder="Sconto (%)" max="100" min="0" required>
            <small class="errMsg cell"></small>
        </label>
        <label for="prodotti" class="field w65 cell">
            <span style="margin-right: 10px">
                Prodotti:
            </span>
            <select name="prodotti" id="prodotti" multiple>
                <c:forEach items="${ristorante.prodotti}" var="prodotto">
                    <c:if test="${prodotto.valido}">
                    <option value="${prodotto.codice}">
                            ${prodotto.nome}
                    </option>
                    </c:if>
                </c:forEach>
            </select>
        </label>
        <input style="display: none" name="id" id="id" value="${ristorante.codice}" readonly>
        <input style="display: none" name="function" id="function" value="${function}" readonly>

        <span class="grid-x cell justify-center">
            <button type="submit" class="btn primary w30" value="again" name="button"> Aggiungi menu </button>
            <button type="submit" class="btn primary w30" value="stop" name="button">Salva ed esci</button>
        </span>
    </fieldset>
</form>
    </div>
</div>
<c:if test="${empty ristorante.prodotti || countProdValidi<2}">
    <script> document.getElementsByTagName("form")[1].style.display="none";</script>
</c:if>
</body>
</html>
