<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ristorante.Ristorante" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Ristoranti"/>
        <jsp:param name="scripts" value="crm,crm_ristoranti"/>
        <jsp:param name="styles" value="crm,crm_ristoranti"/>
    </jsp:include>
    <style>
        .blank{
            display:none;
            border-bottom: 0 !important;
        }

        .valid>svg{
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
                <span title="Aggiungi ristorante">
                    <%@include file="../../../icons/plus.svg" %>
                </span>
                <label class="field command w75">
                    <input type="text" placeholder="Cerca Ristoranti">
                </label>
                <label class="field command w25" id="totale">
                    <span> Totale ristoranti:</span>
                    <span style="color: black; font-weight: normal"> ${totRis}</span>
                </label>
            </div>
            <section class="grid-y cell restaurants">
                <table class="table restaurants-table">
                    <caption> Ristoranti </caption>
                    <thead>
                    <tr>
                        <th> Nome </th>
                        <th> Provincia </th>
                        <th> Citta </th>
                        <th> Via </th>
                        <th> Civico </th>
                        <th> Rating </th>
                        <th> Tipologie </th>
                        <th> Validita</th>
                        <th> </th>
                        <th> </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${ristoranti}" var="ristorante">
                        <tr <c:if test="${!ristorante.valido}"> style="background-color: lightgrey;"</c:if> >
                            <td data-head="Nome">${ristorante.nome} </td> <%--link a pagina con prodotti--%>
                            <td data-head="Provincia">${ristorante.provincia}</td>
                            <td data-head="Citta">${ristorante.citta}</td>
                            <td data-head="Via">${ristorante.via}</td>
                            <td data-head="Civico">${ristorante.civico}</td>
                            <td data-head="Rating">
                                <c:forEach var="counter" begin="1" end="${ristorante.rating}">
                                    <%@include file="../../../icons/moto.svg" %>
                                </c:forEach>
                            </td>
                            <td data-head="Tipologie">
                                <c:choose>
                                    <c:when test="${fn:length(ristorante.tipologie)==0}">
                                        No tipologie
                                    </c:when>
                                    <c:when test="${fn:length(ristorante.tipologie)==1}">
                                        ${ristorante.tipologie[0].nome}...
                                    </c:when>
                                    <c:otherwise>
                                        ${ristorante.tipologie[0].nome}, ${ristorante.tipologie[1].nome}...
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="valid" onclick="changeValidita(this)" data-head="Validita">
                                <c:choose>
                                    <c:when test="${!ristorante.valido}">
                                        <%@include file="../../../icons/valid.svg" %>
                                    </c:when>
                                    <c:otherwise>
                                        <%@include file="../../../icons/delete.svg" %>
                                    </c:otherwise>
                                </c:choose>

                            </td>
                            <td style="border-bottom: 0" > <a href="/FoodOut/ristorante/show-info-admin?id=${ristorante.codice}" target="_blank"> Vai al profilo </a></td>
                            <td class="blank" value="${ristorante.codice}"></td>
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
</body>
</html>
