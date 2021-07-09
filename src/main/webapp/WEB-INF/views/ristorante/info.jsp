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
        <jsp:param name="scripts" value="info"/>
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

    div#header{
        background-color: rgba(0,0,0,0);
        background-image: url("/FoodOut/covers/${ristorante.urlImmagine}");
        background-size: cover;
        background-repeat: no-repeat;
        background-position: center center;
        height: 500px;
        position: relative;
    }

    #links{
        background-color: var(--primary);
        padding:3px;
        border:1px solid var(--primary);
        border-radius: 10px;
        margin:2px;
    }
    #links a{
        text-decoration: none;
        color:white;
        font-family:Myriad;
        font-weight:bold;
        font-style:normal;
        margin:5px;
    }

</style>
<div class="app">
    <div class="cell grid-x" id="header">
        <nav class="grid-y navbar align-center cell">
            <img onclick="goBack()" src="/FoodOut/images/logo.png" class="fluid-image" id="logo" title="Torna indietro">
            <div id="links">
                <a href="${pageContext.request.contextPath}/ristorante/show-info?id=${ristorante.codice}">
                    Info </a>
                <a href="${pageContext.request.contextPath}/ristorante/show-menu?id=${ristorante.codice}"/>
                Menu </a>
                <a href="${pageContext.request.contextPath}/ristorante/show-recensioni?id=${ristorante.codice}">
                    Recensioni </a>
            </div>
        </nav>
        <div class="grid-x justify-center align-center info-ris cell">
            <fieldset class="grid-x index cell w63">
                <h2 class="cell"> Info </h2>
                <label for="nome" class="field cell w80" >
                    <span style="font-weight: bold"> Nome: </span>
                    <span id="nome">${ristorante.nome}</span>
                </label>
                <label class="field cell w100 grid-x">
                    <span class="field cell w40" style="margin: 5px">
                        <span style="font-weight: bold"> Provincia: </span>
                        <span style="margin-left: 5px">${ristorante.provincia}</span>
                    </span>
                    <span>
                        <span style="font-weight: bold" class="field cell w40"> Citta: </span>
                        <span style="margin-left: 5px">${ristorante.citta}</span>
                    </span>
                </label>
                <label class="field cell w100 grid-x">
                    <span class="field cell w40" style="margin: 5px">
                        <span style="font-weight: bold"> Via: </span>
                        <span style="margin-left: 5px">${ristorante.via}</span>
                    </span>
                    <span>
                        <span style="font-weight: bold" class="field cell w40"> Civico: </span>
                        <span style="margin-left: 5px">${ristorante.civico}</span>
                    </span>
                </label>
                <label class="field cell w100 grid-x">
                    <span class="field cell w40" style="margin: 5px">
                        <span style="font-weight: bold"> Spesa minima: </span>
                        <span style="margin-left: 5px">${ristorante.spesaMinima}</span>
                    </span>
                    <span>
                        <span style="font-weight: bold" class="field cell w40"> Tasso consegna: </span>
                        <span style="margin-left: 5px">${ristorante.tassoConsegna}</span>
                    </span>
                </label>
                <label class="field cell w40 grid-x align-center">
                    <span style="font-weight: bold"> Rating: </span>
                    <span><c:forEach var="counter" begin="1" end="${ristorante.rating}">
                        <%@include file="../../../icons/moto.svg" %>
                    </c:forEach></span>
                </label>
                <label for="tipologie" class="field cell w80">
                    <span style="font-weight: bold"> Tipologie: </span>
                    <span name="tipologie" id="tipologie" rows="4" cols="100" readonly><%Ristorante r= (Ristorante) request.getAttribute("ristorante");
                        if(!r.getTipologie().isEmpty()){
                            StringJoiner sj=new StringJoiner(",");
                            for(Tipologia t: r.getTipologie()){
                                sj.add(t.getNome());
                            }
                    %><%=sj.toString()%>
                        <%}else{
                            String str="Non ci sono prodotti, quindi tipologie per il ristorante";
                        %><%=str%>
                        <%}%></span>
                </label>
                <label for="info" class="field cell w80">
                    <span style="font-weight: bold"> Info:</span>
                    <span name="info" id="info"> ${ristorante.info}</span>
                </label>
            </fieldset>
        </div>
        <div class="disponibilita grid-x justify-center align-center cell">
            <section class="grid-y cell w63">
                <table class="table">
                    <tbody>
                    <c:forEach items="${ristorante.giorni}" var="disp">
                        <tr>
                            <c:choose>
                                <c:when test="${disp.oraApertura==disp.oraChiusura}">
                                    <td data-head="${disp.giorno}">CHIUSO </td>
                                </c:when>
                                <c:otherwise>
                                    <td data-head="${disp.giorno}">${disp.oraApertura} - ${disp.oraChiusura} </td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </section>
        </div>
    </div>
</div>

</body>
</html>
