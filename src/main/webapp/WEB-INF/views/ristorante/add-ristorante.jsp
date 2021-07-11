<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="it" dir="ltr">
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Aggiungi Ristorante"/>
        <jsp:param name="scripts" value="add_ris_validation"/>
    </jsp:include>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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

    </style>
</head>

<body>
<form class="app grid-x justify-center align-center" action="${pageContext.request.contextPath}/ristorante/add" method="post" enctype="multipart/form-data" novalidate>
    <c:if test="${not empty alert}">
        <%@ include file="../partials/alert.jsp"%>
    </c:if>
    <fieldset class="grid-x cell w75 add-ris justify-center"> <%-- vedere se è meglio  w50 o w75 ,  con justify-center , align-center o meno--%>
        <h2 class="cell"> Aggiungi ristorante</h2>
        <label for="nome" class="field cell w82 grid-x">
            <input class="cell" type="text" name="nome" id="nome" placeholder="Nome" maxlength="30" pattern="^([a-zA-Z]|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="provincia" class="field w40 cell grid-x">
            <input class="cell" type="text" name="provincia" id="provincia" placeholder="Provincia" maxlength="30" pattern="^([a-zA-Z]|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="citta" class="field w40 cell grid-x">
            <input class="cell" type="text" name="citta" id="citta" placeholder="Citta" maxlength="30" pattern="^([a-zA-Z]|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="via" class="field w50 cell grid-x" >
            <input class="cell" type="text" name="via" id="via" placeholder="Via" maxlength="50" pattern="^(\w|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'|\.){1,50}$" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="civico" class="field w30 cell grid-x">
            <input class="cell" type="number" name="civico" id="civico" placeholder="n°" min="1" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="spesaMinima" class="field w40 cell grid-x">
            <input class="cell" type="number" name="spesaMinima" id="spesaMinima" placeholder="Spesa minima (€)" step="0.01" min="0" pattern="^[0-9]\d{0,9}(\.\d{1,3})?$" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="tassoConsegna" class="field w40 cell grid-x">
            <input class="cell" type="number" name="tassoConsegna" id="tassoConsegna" placeholder="Tasso Consegna (€)" step="0.01" min="0" pattern="^[0-9]\d{0,9}(\.\d{1,3})?$" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="urlImmagine" class="field w80 cell grid-x">
            <input class="cell" type="file" name="urlImmagine" id="urlImmagine" placeholder="Immagine del tuo ristorante" accept="image/*" required>
            <small class="errMsg cell"> </small>
        </label>
        <label for="info" class="field cell w80 grid-x">
            <textarea class="cell" rows="4" cols="100" type="text" name="info" id="info" maxlength="200" placeholder="Informazioni sul tuo ristorante" pattern="^(\\w|\\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'|\\.){1,200}$" required></textarea>
            <small class="errMsg cell"> </small>
        </label>
        <span class="grid-x cell justify-center">
            <button type="submit" class="btn primary w30"> Aggiungi </button>
        </span>
    </fieldset>
</form>
</body>
</html>
