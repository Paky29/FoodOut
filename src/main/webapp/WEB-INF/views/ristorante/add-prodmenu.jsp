<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
<form id="prodotto" class="grid-x justify-center" action="${pageContext.request.contextPath}/prodotto/create" method="post" enctype="multipart/form-data">
    <fieldset class="grid-x cell w90 add-ris justify-center">
        <h2 class="cell"> Aggiungi prodotto</h2>
        <label for="nome" class="field cell w82">
            <input type="text" name="nome" id="nome" placeholder="Nome">
        </label>
        <label for="prezzo" class="field w40 cell">
            <input type="number" name="prezzo" id="prezzo" placeholder="Prezzo (€)" min="0" step="0.01">
        </label>
        <label for="sconto" class="field w40 cell">
            <input type="number" name="sconto" id="sconto" placeholder="Sconto (%)" max="100" min="0">
        </label>
        <label for="tipologia" class="field w65 cell">
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
        <label for="urlImmagine" class="field w80 cell">
            <input type="file" name="urlImmagine" id="urlImmagine" placeholder="Immagine del tuo ristorante">
        </label>
        <label for="ingredienti" class="field cell w80">
            <textarea rows="4" cols="100" type="text" name="ingredienti" id="ingredienti" maxlength="100" placeholder="Ingredienti del prodotto, separati da ','"></textarea>
        </label>
        <label for="info" class="field cell w80">
            <textarea rows="4" cols="100" type="text" name="info" id="info" maxlength="100" placeholder="Informazioni extra sul prodotto"></textarea>
        </label>
        <input style="display: none" name="id" id="id" value="${ristorante.codice}" readonly>
        <span class="grid-x cell justify-center">
            <button type="submit" class="btn primary w30" value="again" name="button"> Aggiungi prodotto </button>
            <button type="submit" class="btn primary w30" value="stop" name="button">Salva ed esci</button>
        </span>
    </fieldset>
</form>

<form id="menu" class="grid-x justify-center" action="${pageContext.request.contextPath}/menu/create" method="post">
    <fieldset class="grid-x cell w90 add-ris justify-center">
        <h2 class="cell"> Aggiungi menu</h2>
        <label for="nome" class="field cell w80">
            <input type="text" name="nome" id="nome" placeholder="Nome">
        </label>
        <label for="prezzo" class="field w40 cell">
            <input type="number" name="prezzo" id="prezzo" placeholder="Prezzo (€)" step="0.01">
        </label>
        <label for="sconto" class="field w40 cell">
            <input type="number" name="sconto" id="sconto" placeholder="Sconto (%)">
        </label>
        <label for="prodotti" class="field w65 cell">
            <span style="margin-right: 10px">
                Prodotti:
            </span>
            <select name="prodotti" id="prodotti" multiple>
                <c:forEach items="${ristorante.prodotti}" var="prodotto">
                    <option value="${prodotto.codice}">
                            ${prodotto.nome}
                    </option>
                </c:forEach>
            </select>
        </label>
        <input style="display: none" name="id" id="id" value="${ristorante.codice}" readonly>
        <span class="grid-x cell justify-center">
            <button type="submit" class="btn primary w30" value="again" name="button"> Aggiungi menu </button>
            <button type="submit" class="btn primary w30" value="stop" name="button">Salva ed esci</button>
        </span>
    </fieldset>
</form>
</div>
<c:if test="${empty ristorante.prodotti || fn:length(ristorante.prodotti)<2}">
    <script> document.getElementsByTagName("form")[1].style.display="none";</script>
</c:if>
</body>
</html>
