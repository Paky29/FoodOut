<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Aggiungi Disponibilita"/>
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
    <form id="prodotto " class="grid-x justify-center" action="${pageContext.request.contextPath}/prodotto/update" method="post" enctype="multipart/form-data">
        <fieldset class="grid-x cell w90 add-ris justify-center">
            <h2 class="cell"> Modifica prodotto</h2>
            <label for="nome" class="grid-x field cell w82">
                <span style="font-weight: bold" class="field cell w20"> Nome: </span>
                <input class="cell w80" type="text" name="nome" id="nome" value="${prodotto.nome}" placeholder="Nome">
            </label>
            <label for="prezzo" class="grid-x field w40 cell">
                <span class="field cell" style="font-weight: bold;margin-bottom: 3px;">Prezzo: </span>
                <input type="number" name="prezzo" id="prezzo" value="${prodotto.prezzo}" placeholder="Prezzo (€)" min="0" step="0.01">
            </label>
            <label for="sconto" class="grid-x field w40 cell">
                <span class="field cell" style="font-weight: bold;margin-bottom: 3px;">Sconto: </span>
                <input type="number" name="sconto" id="sconto" value="${prodotto.sconto}" placeholder="Sconto (%)" max="100" min="0">
            </label>
            <label for="tipologia" class="grid-x field w82 cell">
                <span class="field cell w20" style="font-weight: bold;"> Tipologia: </span>
                <input class="cell w80" name="tipologia" id="tipologia" value="${prodotto.tipologia.nome}" readonly>
            </label>
            <label for="urlImmagine" class="field w80 cell">
                <input type="file" name="urlImmagine" id="urlImmagine" placeholder="Immagine del tuo ristorante" value="${prodotto.urlImmagine}">
            </label>
            <label for="ingredienti" class="grid-x field cell w82">
                <span class="field cell" style="font-weight: bold;margin-bottom: 3px;">Ingredienti: </span>
                <textarea rows="4" cols="100" type="text" name="ingredienti" id="ingredienti" maxlength="100" placeholder="Ingredienti del prodotto, separati da ','"> ${prodotto.ingredienti} </textarea>
            </label>
            <label for="info" class="grid-x field cell w82">
                <span class="field cell" style="font-weight: bold;margin-bottom: 3px;">Info extra: </span>
                <textarea rows="4" cols="100" type="text" name="info" id="info" maxlength="100" placeholder="Informazioni extra sul prodotto"> ${prodotto.info} </textarea>
            </label>
            <input style="display: none" name="id" id="id" value="${prodotto.codice}" readonly>
            <input style="display: none" name="idRis" id="idRis" value="${ristorante.codice}" readonly>

            <span class="grid-x cell justify-center">
            <button type="submit" class="btn primary w30" value="again" name="button"> Modifica </button>
                <button type="submit" class="btn primary w30" formaction="${pageContext.request.contextPath}/prodotto/update-validita" formmethod="post">
                    <c:choose>
                        <c:when test="${prodotto.valido}">
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
