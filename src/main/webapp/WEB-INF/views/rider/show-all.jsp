<%@ page import="java.util.ArrayList" %>
<%@ page import="model.rider.Rider" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Rider"/>
        <jsp:param name="scripts" value="crm,crm_riders"/>
        <jsp:param name="styles" value="crm,crm_riders"/>
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
                <span title="Aggiungi rider">
                    <%@include file="../../../icons/plus.svg" %>
                </span>
                <label class="field command w75">
                    <input type="text" placeholder="Cerca rider per citt&agrave;">
                </label>
                <label class="field command w25" id="totale">
                    <span> Totale rider:</span>
                    <span style="color: black; font-weight: normal"> ${totRid}</span>
                </label>
            </div>
            <section class="grid-y cell riders">
                <table class="table riders-table">
                    <caption> Rider </caption>
                    <thead>
                    <tr>
                        <th> Email </th>
                        <th> Citta </th>
                        <th> Veicolo </th>
                        <th> Elimina </th>
                        <th> </th>
                        <th> </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${riders}" var="rider">
                        <tr>
                            <td data-head="Email">${rider.email}
                            <td data-head="Citta">${rider.citta}</td>
                            <td data-head="Veicolo">${rider.veicolo}</td>
                            <td class="delete" onclick="deleteRid(this)" data-head="Elimina">
                                <%@include file="../../../icons/delete.svg" %>
                            </td>
                            <td style="border-bottom: 0" > <a href="/FoodOut/rider/show?id=${rider.codice}" target="_blank"> Vai al profilo </a></td>
                            <td class="blank" value="${rider.codice}"></td>
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
