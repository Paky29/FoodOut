<%@ page import="model.ristorante.Ristorante" %>
<%@ page import="model.tipologia.Tipologia" %>
<%@ page import="java.util.StringJoiner" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Dettagli ordine"/>
        <jsp:param name="styles" value="info_ord_crm"/>
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

    div#header{
        background-color: rgba(0,0,0,0);
        background-image:url("/FoodOut/images/o.jpg");
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

    #dettagliRis {
        text-decoration: none;
        margin-left: 5px;
    }

    .voto > svg {
        margin: 0 2px;
    }


</style>
<div class="app">
    <div class="cell grid-x" id="header">
        <nav class="grid-y navbar align-center cell">
            <img src="/FoodOut/images/logo.png" class="fluid-image" id="logo">
        </nav>
        <form class="grid-x justify-center align-center info-ord cell" action="${pageContext.request.contextPath}/ordine/update" method="post">
            <fieldset class="grid-x cell w63 index">
                <h2 class="cell"> Dettagli </h2>
                <label for="codice" class="field cell w80" >
                    <span style="font-weight: bold"> Codice: </span>
                    <input type="text" name="codice" id="codice" value="${ordine.codice}" readonly>
                </label>
                <label class="field cell w40 grid-x">
                    <span style="font-weight: bold" class="field cell w40"> Data: </span>
                    <input type="date" name="data" id="data" value="${ordine.dataOrdine}" readonly>
                    <span style="font-weight: bold" class="field cell w40"> Ristorante: </span>
                    <a href="/FoodOut/ristorante/show-info-admin?id=${ordine.ristorante.codice}" id="dettagliRis">  ${ordine.ristorante.nome} </a>
                </label>
                <label class="field cell w40 grid-x">
                    <span style="font-weight: bold" class="field cell w40"> Ora partenza: </span>
                    <input type="time" name="oraPartenza" id="oraPartenza" value="${ordine.oraPartenza}">
                    <span style="font-weight: bold" class="field cell w40"> Ora arrivo: </span>
                    <input type="time" name="oraArrivo" id="oraArrivo" value="${ordine.oraArrivo}">
                </label>
                <label class="field cell w80 grid-x">
                    <span style="font-weight: bold" class="field cell w25"> Totale: </span>
                    <input type="number" name="totale" id="totale" value="${ordine.totale}" readonly>
                    <span style="font-weight: bold" class="field cell w25"> Metodo pagamento:</span>
                    <input type="text" name="metodoPagamento" id="metodoPagamento" value="${ordine.metodoPagamento}" readonly>
                </label>
                <label class="field cell w80 grid-x voto">
                    <span style="font-weight: bold"> Voto:</span>
                    <c:choose>
                        <c:when test="${ordine.voto==0}">
                            <span style="font-weight: normal"> non inserito </span>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="counter" begin="1" end="${ordine.voto}">
                                <%@include file="../../../icons/moto.svg" %>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </label>
                <label for="giudizio" class="field cell w80">
                    <span style="font-weight: bold"> Giudizio: </span>
                    <c:choose>
                        <c:when test="${empty ordine.giudizio}">
                           <c:set var="giudizio" value="non inserito"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="giudizio" value="${ordine.giudizio}"/>
                        </c:otherwise>
                    </c:choose>
                    <textarea name="giudizio" id="giudizio" rows="4" cols="100" readonly>${giudizio}</textarea>
                </label>
                <label for="nota" class="field cell w80">
                    <span style="font-weight: bold"> Nota:</span>
                    <textarea rows="4" cols="100" type="text" name="nota" id="nota" maxlength="200" readonly>${ordine.nota}</textarea>
                </label>
                <span class="grid-x cell justify-center">
                <button type="submit" class="btn primary"> Modifica </button>
                </span>
            </fieldset>
        </form>
    <div class="disponibilita grid-x justify-center align-center cell">
        <section class="grid-y cell w63">
            <table class="table">
                <thead>
                    <tr>
                        <th> Prodotto</th>
                        <th> Prezzo </th>
                        <th> Quantità</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${ordine.ordineItems}" var="item">
                        <tr>
                            <td data-head="Prodotto">
                                ${item.off.nome}
                            </td>
                            <td data-head="Prezzo">
                                ${item.off.prezzo}
                            </td>
                            <td data-head="Quantità">
                                ${item.quantita}
                            </td>
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
