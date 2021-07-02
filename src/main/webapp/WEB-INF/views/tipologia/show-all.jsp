<%@ page import="java.util.ArrayList" %>
<%@ page import="model.tipologia.Tipologia" %>
<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Tipologie"/>
        <jsp:param name="scripts" value="crm,crm_tipologie"/>
        <jsp:param name="styles" value="crm,crm_tipologie"/>
    </jsp:include>
    <style>
        .blank{
            display:none;
            border-bottom: 0 !important;
        }

        .delete>svg, .edit>svg{
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
                <span title="Aggiungi tipologia">
                    <%@include file="../../../icons/plus.svg" %>
                </span>
                <label class="field command w75">
                    <input type="text" placeholder="Cerca Tipologie">
                </label>
                <label class="field command w25" id="totale">
                    <span> Totale tipologie:</span>
                    <span style="color: black; font-weight: normal"> ${totTip}</span>
                </label>
            </div>
            <section class="grid-y cell typologies">
                <table class="table typologies-table">
                    <caption> Tipologie </caption>
                    <thead>
                    <tr>
                        <th> Nome </th>
                        <th> Descrizione </th>
                        <th> Modifica</th>
                        <th> Elimina</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${tipologie}" var="tipologia">
                        <tr>
                            <td data-head="Nome" class="nome" value="${tipologia.nome}">${tipologia.nome} </td> <%--link a pagina con prodotti--%>
                            <td data-head="Descrizione" class="descrizione" value="${tipologia.descrizione}">${tipologia.descrizione}</td>
                            <td class="edit" data-head="Modifica" onclick="editTip(this)">
                                <%@include file="../../../icons/pencil1.svg" %>
                            </td>
                            <td class="delete" data-head="Elimina" onclick="deleteTip(this)">
                                <%@include file="../../../icons/delete.svg" %>
                            </td>
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
