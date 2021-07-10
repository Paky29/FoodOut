<%@ page import="model.ristorante.Ristorante" %>
<%@ page import="model.tipologia.Tipologia" %>
<%@ page import="java.util.StringJoiner" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Dettagli ordine"/>
        <jsp:param name="styles" value="show_utente"/>
        <jsp:param name="scripts" value="show_utente"/>
    </jsp:include>
    <script>
        $(document).ready(function(){
            $("#sliding-table").hide()
            $("#mock-button").click(function (){
                $("#sliding-table").fadeToggle();
            })
        })

        function goBack(){
            history.go(-1);
        }
    </script>
</head>
<body>
<style>

    .index {
        padding: 1rem;<%--dimensione relativa al root--%>
        background-color: white;
        border-radius: 10px;
        opacity: revert;
    }
</style>
<div class="app">
    <div class="cell grid-x" id="header">
        <nav class="grid-y navbar align-center cell">
            <img src="/FoodOut/images/logo.png" class="fluid-image" id="logo" onclick="goBack()">
        </nav>
        <form class="grid-x justify-center align-center info-ord cell" action="${pageContext.request.contextPath}/ordine/update" method="post">
            <fieldset class="grid-x cell w63 index">
                <h2 class="cell"> Dettagli </h2>
                <label for="codice" class="field cell w80" >
                    <span style="font-weight: bold"> Codice: </span>
                    <span id="codice"> ${ordine.codice} </span>
                </label>
                <label class="field cell w40 grid-x">
                    <span style="font-weight: bold" class="field cell w50"> Ristorante:
                    <a style="font-weight: normal" href="/FoodOut/ristorante/show-menu?id=${ordine.ristorante.codice}" id="dettagliRis">  ${ordine.ristorante.nome} </a>
                    </span>
                    <span style="font-weight: bold" class="field cell"> Data:
                    <span style="font-weight: normal" id="data"> ${ordine.dataOrdine} </span>
                    </span>
                </label>
                <label class="field cell w40 grid-x">
                    <span style="font-weight: bold" class="field cell w40"> Ora partenza: </span>
                    <c:choose>
                        <c:when test="${not empty ordine.oraPartenza}">
                            <span id="oraPartenza">${ordine.oraPartenza}</span>
                        </c:when>
                        <c:otherwise>
                            <span id="oraPartenza">Non partito</span>
                        </c:otherwise>
                    </c:choose>
                    <span style="font-weight: bold" class="field cell w40"> Ora arrivo: </span>
                    <c:choose>
                        <c:when test="${not empty ordine.oraArrivo}">
                            <span id="oraArrivo">${ordine.oraArrivo}</span>
                        </c:when>
                        <c:otherwise>
                            <span id="oraPartenza">Non arrivato</span>
                        </c:otherwise>
                    </c:choose>
                </label>
                <label class="field cell grid-x">
                    <span class="field cell w50"> <span style="font-weight: bold">Totale:</span>
                    <span id="tot" > <fmt:formatNumber value="${ordine.totale}" type="currency"/> </span>
                    </span>
                    <span style="font-weight: bold" class="field cell"> Metodo pagamento:
                    <span style="font-weight: normal" id="metodoPagamento"> ${ordine.metodoPagamento} </span>
                    </span>
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
                    <textarea rows="4" cols="100" type="text" id="nota" maxlength="200" readonly>${ordine.nota}</textarea>
                </label>
                <c:if test="${ordine.consegnato==false}">
                    <span class="grid-x cell justify-center">
                    <button type="submit" class="btn primary"> Modifica </button>
                    </span>
                </c:if>
            </fieldset>
        </form>
        <div class="disponibilita grid-x justify-center align-center cell">
            <section class="grid-y cell w63">
                <div class="cell justify-center" id="mock-button">
                    Elenco prodotti
                </div>
                <div class="grid-x cell " id="sliding-table">
                    <table class="table cell ">
                        <thead>
                        <tr>
                            <th> Prodotto</th>
                            <th> Prezzo </th>
                            <th> Quantità</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${ordine.ordineItems}" var="item">
                            <tr class="w100">
                                <td data-head="Prodotto">
                                        ${item.off.nome}
                                </td>
                                <td data-head="Prezzo" >
                                        ${item.off.prezzo}
                                </td>
                                <td data-head="Quantità">
                                        ${item.quantita}
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </section>
        </div>
    </div>
</div>
</body>
</html>
