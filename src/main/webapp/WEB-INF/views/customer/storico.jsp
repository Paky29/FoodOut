<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ordine.Ordine" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Ordini"/>
        <jsp:param name="scripts" value="customer,customer_storico"/>
        <jsp:param name="styles" value="customer,customer_storico"/>
    </jsp:include>
    <style>
        .blank{
            display:none;
            border-bottom: 0 !important;
        }

        .delete>svg{
            cursor: pointer;
        }

        .valid > svg{
            fill: limegreen;
        }


    </style>
</head>
<body>
<main class="app">
    <%@include file="../partials/customer/sidebar.jsp" %>
    <section class="content grid-y">
        <%@include file="../partials/customer/header.jsp" %>
        <div class="body grid-x justify-center">
            <c:if test="${not empty alert}">
                <%@ include file="../partials/alert.jsp"%>
            </c:if>
            <div class="searchbar grid-x align-center cell">
                <label class="field command w20" id="totaleOrd">
                    <span> Totale ordini:</span>
                    <span style="color: black; font-weight: normal"> ${totOrd}</span>
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
                        <th> Recensione </th>
                        <th> Ristorante </th>
                        <th> </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${ordini}" var="ordine">
                        <tr>
                            <td data-head="Codice" id="id">${ordine.codice}</td>
                            <td data-head="Data">${ordine.dataOrdine}</td>
                            <td data-head="Ora Partenza">
                                    ${ordine.oraPartenza}
                            </td>
                            <td data-head="Ora Arrivo">
                                    ${ordine.oraArrivo}
                            </td>
                            <td data-head="Totale"><fmt:formatNumber value="${ordine.totale}" type="currency"/></td>
                            <td data-head="Metodo pagamento">${ordine.metodoPagamento}</td>
                            <td data-head="Consegnato">
                                <c:choose>
                                    <c:when test="${ordine.consegnato}">
                                        <span class="valid"><%@include file="../../../icons/valid.svg" %></span>
                                    </c:when>
                                    <c:otherwise>
                                        <%@include file="../../../icons/wait.svg" %>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="recensione" data-head="Recensione">
                                <c:choose>
                                <c:when test="${ordine.voto==0}">
                                <span onclick="addRecensione(${ordine.codice}, ${ordine.voto==0})"> <%@include file="../../../icons/pencil1.svg" %></span>
                                </c:when>
                                <c:otherwise>
                                    <span onclick="addRecensione(${ordine.codice},${ordine.voto==0})"> <span class="valid"><%@include file="../../../icons/valid.svg" %></span></span>
                                </c:otherwise>
                                </c:choose>
                            </td>
                            <td style="border-bottom: 0" data-head="Ristorante" > <a href="/FoodOut/ristorante/show-info?id=${ordine.ristorante.codice}"> ${ordine.ristorante.nome} </a></td>
                            <td class="blank" value="${ordine.codice}"></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div>
                    <jsp:include page="../partials/paginator.jsp">
                        <jsp:param name="risorsa" value="storico"/>
                    </jsp:include>
                </div>
            </section>
        </div>
        </div>
                <%@include file="../partials/customer/footer.jsp" %>
    </section>
</main>
</body>
</html>
