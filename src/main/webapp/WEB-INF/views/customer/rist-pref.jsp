<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Profilo"/>
        <jsp:param name="scripts" value="customer"/>
        <jsp:param name="styles" value="customer,customer_rist_pref"/>
    </jsp:include>
    <style>


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
                                <span> Totale preferiti:</span>
                                <span style="color: black; font-weight: normal"> ${totRis}</span>
                            </label>
                </div>
                <section class="grid-y cell restaurants justify-center">
                    <table class="table restaurants-table">
                        <caption> Ristoranti </caption>
                        <thead>
                        <tr>
                            <th> Nome </th>
                            <th> Provincia </th>
                            <th> Citt&agrave; </th>
                            <th> Via </th>
                            <th> Civico </th>
                            <th> Rating </th>
                            <th> Tipologie </th>
                            <th> </th>
                            <th> </th>

                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${ristoranti}" var="ristorante">
                            <tr <c:if test="${!ristorante.valido}"> style="background-color: lightgrey;"</c:if> >
                                <td data-head="Nome">${ristorante.nome} </td>
                                <td data-head="Provincia">${ristorante.provincia} </td>
                                <td data-head="Citt&agrave;">${ristorante.citta} </td>
                                <td data-head="Via">${ristorante.via} </td>
                                <td data-head="Civico">${ristorante.civico} </td>
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
                                <td style="border-bottom: 0" > <a href="/FoodOut/ristorante/show-menu?id=${ristorante.codice}"> Vai al profilo </a></td>
                                <td class="blank" value="${ristorante.codice}"></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div>
                        <jsp:include page="../partials/paginator.jsp">
                            <jsp:param name="risorsa" value="ristoranti-pref"/>
                        </jsp:include>
                    </div>
                </section>
            </div>
        <%@include file="../partials/customer/footer.jsp" %>
    </section>
</main>
</body>
</html>