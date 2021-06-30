<%@ page import="model.ristorante.Ristorante" %>
<%@ page import="model.tipologia.Tipologia" %>
<%@ page import="java.util.StringJoiner" %>
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

    .index {
        padding: 1rem;<%--dimensione relativa al root--%>
        background-color: white;
        border-radius: 10px;
        opacity: revert;
    }

    .index > * {
        margin: 10px;
    }

    input:focus {
        outline: 1px solid var(--primary);
    }

    input {
        height: 40px;
        line-height: 40px;
    }

    #urlImmagine {
        height: 50px;
        line-height: 27px;
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

    label > span {
        margin: 5px 5px;
        font-style: italic;
    }

    .table{
        border-collapse: collapse;
        background-color:white;
        font-size: large;
        font-weight: normal;
        font-style: normal;
        font-family: Myriad;
    }

    .table > thead{
        visibility: hidden;
    }

    .table tr{
        border:none;
        margin: .5rem;
        padding: .5rem;
        display:block;
    }

    .table > tbody{
        border:1px solid black;
        border-radius: 20px;
    }

    .table > tbody td{
        display: block;
        border-bottom: 1px solid black;
        text-align: right;
        padding: .5rem;
    }

    .table > tbody td:before{
        content: attr(data-head);
        float: left;
        font-weight: bold;
        text-transform: uppercase;
    }

</style>
<div class="app">
    <div class="cell grid-x" id="header">
        <nav class="grid-y navbar align-center">
            <img src="/prova_DB/images/logo.png" class="fluid-image" id="logo">
        </nav>
        <form class="grid-x justify-center align-center info-ris" action="${pageContext.request.contextPath}/ristorante/update" method="post" enctype="multipart/form-data">
            <fieldset class="grid-x cell w63 index">
                <h2 class="cell"> Info </h2>
                <label for="nome" class="field cell w80" >
                    <span style="font-weight: bold"> Nome: </span>
                    <input type="text" name="nome" id="nome" value="${ristorante.nome}">
                </label>
                <label class="field cell w40 grid-x">
                    <span style="font-weight: bold" class="field cell w40"> Provincia: </span>
                    <input type="text" name="provincia" id="provincia" value="${ristorante.provincia}">
                    <span style="font-weight: bold" class="field cell w40"> Citta: </span>
                    <input type="text" name="citta" id="citta" value="${ristorante.citta}">
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
                <label for="urlImmagine" class="field w80 cell">
                    <input type="file" name="urlImmagine" id="urlImmagine" placeholder="Immagine del tuo ristorante" style="">
                </label>
                <label for="tipologie" class="field cell w80">
                    <span style="font-weight: bold"> Tipologie: </span>
                    <textarea name="tipologie" id="tipologie" rows="3" cols="100" readonly>
                        <%
                            Ristorante r= (Ristorante) request.getAttribute("ristorante");
                            StringJoiner sj=new StringJoiner(",");
                            for(Tipologia t: r.getTipologie()){
                                sj.add(t.getNome());
                            }
                        %>
                        <%=sj.toString()%>
                    </textarea>
                </label>
                <label for="info" class="field cell w80">
                    <span style="font-weight: bold"> Info:</span>
                    <textarea rows="4" cols="100" type="text" name="info" id="info" maxlength="200"> ${ristorante.info}</textarea>
                </label>
                <span class="grid-x cell justify-center">
                <button type="submit" class="btn primary"> Modifica info</button>
                </span>
            </fieldset>
        </form>
        <div class="disponibilita grid-x justify-center align-center">
            <section class="grid-y cell w63">
                <table class="table">
                    <tbody>
                    <c:forEach items="${ristorante.giorni}" var="disp">
                        <tr>
                            <td data-head="${disp.giorno}">${disp.oraApertura} - ${disp.oraChiusura} </td> <%--link a pagina con prodotti--%>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td style="border-bottom: none"><a href="#">Modifica orario</a></td>
                    </tr>
                    </tbody>
                </table>
            </section>
        </div>
    </div>
</div>

</body>
</html>
