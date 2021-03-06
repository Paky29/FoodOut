<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ordine.Ordine" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel = "stylesheet">
    <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
    <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Ordini"/>
        <jsp:param name="scripts" value="crm,crm_ordini,ristorante"/>
        <jsp:param name="styles" value="crm,crm_ordini"/>
    </jsp:include>
    <style>
        .blank{
            display:none;
            border-bottom: 0 !important;
        }

        .delete>svg{
            cursor: pointer;
        }
    </style>
</head>
<body>
<main class="app">
    <%@include file="../partials/crm/sidebar.jsp" %>
    <section class="content grid-y">
        <%@include file="../partials/crm/header.jsp" %>
        <div class="body grid-x justify-center">
            <div class="searchbar grid-x align-center cell">
                <label class="field command w75">
                    <input type="text" placeholder="Cerca ordine per ristorante" id="nomeRis" onkeydown="search()">
                </label>
                <label class="field command w20" id="totaleOrd">
                    <span> Totale ordini:</span>
                    <span style="color: black; font-weight: normal"> ${totOrd}</span>
                </label>
                <label class="field command w20" id="incasso">
                    <span> Incasso:</span>
                    <span style="color: black; font-weight: normal"> <fmt:formatNumber value="${incasso}" type="currency"/></span>
                </label>
            </div>
            <section class="grid-y cell orders">
                <table class="table orders-table">
                    <caption> Ordini </caption>
                    <thead>
                    <tr>
                        <th> Codice </th>
                        <th> Data </th>
                        <th> Ora Partenza </th>
                        <th> Ora Arrivo </th>
                        <th> Totale </th>
                        <th> Metodo pagamento</th>
                        <th> Consegnato </th>
                        <th> Elimina </th>
                        <th> </th>
                        <th> </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${ordini}" var="ordine">
                        <tr>
                            <td data-head="Codice" id="id">${ordine.codice}</td>
                            <td data-head="Data">${ordine.dataOrdine}</td>
                            <td data-head="Ora Partenza">
                                <c:choose>
                                    <c:when test="${ordine.oraPartenza==null}">
                                        Non partito
                                    </c:when>
                                    <c:otherwise>
                                        ${ordine.oraPartenza}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td data-head="Ora Arrivo">
                                <c:choose>
                                    <c:when test="${ordine.oraArrivo==null}">
                                        Non arrivato
                                    </c:when>
                                    <c:otherwise>
                                        ${ordine.oraArrivo}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td data-head="Totale"><fmt:formatNumber value="${ordine.totale}" type="currency"/></td>
                            <td data-head="Metodo pagamento">${ordine.metodoPagamento}</td>
                            <td data-head="Consegnato">
                                <c:choose>
                                    <c:when test="${ordine.consegnato}">
                                        <%@include file="../../../icons/valid.svg" %>
                                    </c:when>
                                    <c:otherwise>
                                        <%@include file="../../../icons/wait.svg" %>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="delete" onclick="deleteOrd(${ordine.codice})" data-head="Elimina">
                                <%@include file="../../../icons/delete.svg" %>
                            </td>
                            <td style="border-bottom: 0" > <a href="/FoodOut/ordine/dettagli?id=${ordine.codice}"> Dettagli ordine </a></td>
                            <td class="blank" value="${ordine.codice}"></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div>
                    <jsp:include page="../partials/paginator.jsp">
                        <jsp:param name="risorsa" value="all"/>
                    </jsp:include>
                </div>
            </section>
        </div>
        <%@include file="../partials/crm/footer.jsp" %>
    </section>
</main>
<script>

    var nomeRis=document.getElementById("nomeRis");
    nomeRis.addEventListener("keydown", event => {
        if (event.code == "Enter") {
            var nome=nomeRis.value;
            window.location.href="/FoodOut/ordine/all-nome?nome=" + nome;
        }
    });
</script>
</body>
</html>
