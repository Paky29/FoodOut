<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Info"/>
        <jsp:param name="styles" value="info_ris_crm"/>
        <jsp:param name="scripts" value="info_ris_crm"/>
    </jsp:include>
</head>
<body>
<style>

    .top {
        position: relative;
    }


    .index {
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

    .tipologie {
        padding: 1rem;
    <%--dimensione relativa al root--%> background-color: white;
        opacity: revert;
        margin-top:10vh;
    }

    .box {
        border: 2px solid var(--primary);
        margin: 10px;
        padding: 15px;
        font-weight: bold;
        font-size: 3vw;
        color: white;
        background-color: var(--primary);
        border-radius: 5px;
    }

    .cover-image{
        max-width: 100%;
        height: 50vh;
    }

    .info{
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

    .content{
        background-color: white;
        flex:1;
        transition: all .3s ease-in-out;
    }

    .tipologia{
        margin: 10vw;
        margin-bottom: 5px;
    }

    .tipologia > p:hover{
        transform: scale3d(1.30,1.30,1.30);
    }

    label > span {
        margin: 5px 5px;
        font-style: italic;
    }


</style>
<div class="app grid-x">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <div class="cell grid-y" id="header">
        <nav class="grid-y navbar align-center">
            <img src="/prova_DB/images/logo.png" class="fluid-image" id="logo">
        </nav>
        <form class="grid-x justify-center align-center info-ris" action="/ristorante/update" method="post" enctype="multipart/form-data">
            <fieldset class="grid-x cell w63 index">
                <h2 class="cell"> Info </h2>
                <label for="nome" class="field cell w80" >
                    <span style="font-weight: bold"> Nome: </span>
                    <input type="text" name="nome" id="nome" value="${ristorante.info}">
                </label>
                <label class="field cell w40 grid-x">
                    <span style="font-weight: bold" class="field cell w40"> Provincia: </span>
                    <input type="text" name="provincia" id="provincia" value="${ristorante.provincia}">
                    <span style="font-weight: bold" class="field cell w40"> Citta: </span>
                    <input type="text" name="citta" id="citta" placeholder="${ristorante.citta}">
                </label>
                <label class="field cell w40 grid-x">
                    <span style="font-weight: bold" class="field cell w75"> Via: </span>
                    <input type="text" name="via" id="via" value="${ristorante.via}">
                    <span style="font-weight: bold" class="field cell w25"> Civico: </span>
                    <input type="text" name="civico" id="civico" value="${ristorante.civico}">
                </label>
                <label class="field cell w40 grid-x">
                    <span style="font-weight: bold" class="field cell w40"> Spesa minima: </span>
                    <input type="text" name="spesaMinima" id="spesaMinima" value="${ristorante.spesaMinima}">
                    <span style="font-weight: bold" class="field cell w40"> Tasso consegna:</span>
                    <input type="text" name="tassoConsegna" id="tassoConsegna" value="${ristorante.tassoConsegna}">
                </label>
                <label class="field cell w40 grid-x">
                    <span style="font-weight: bold"> Rating: </span>
                    <input type="text" name="rating" id="rating" value="${ristorante.rating}" readonly>
                    <span style="font-weight: bold" class="cell" style="visibility: hidden"> </span>
                    <input type="text" name="id" id="id" value="${ristorante.codice}" style="visibility: hidden" readonly >
                </label>
                <label for="info" class="field cell w80">
                    <span style="font-weight: bold"> Info:</span>
                    <textarea rows="4" cols="100" type="text" name="info" id="info" maxlength="200"> ${ristorante.info}</textarea>
                </label>
                <button type="submit" class="btn primary"> Modifica info</button>
            </fieldset>
        </form>
    </div>


    <div class="content">
        <div class="tipologie app grid-x justify-center align-center">
            <c:forEach items="${tipologieVendute}" var="tipologia">
                <span class="cell w10 tipologia">
                <p class="box"> ${tipologia.nome} </p>
                </span>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>
