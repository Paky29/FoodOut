<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Profilo"/>
        <jsp:param name="scripts" value="customer"/>
        <jsp:param name="styles" value="customer"/>
    </jsp:include>
    <style>
        input {
            height: 40px;
            line-height: 40px;
        }
        svg{
            fill: black;
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
            <form class="grid-x justify-center align-center" action="${pageContext.request.contextPath}/utente/deposit?function=${function}" method="post">
                <fieldset
                        class="grid-x cell justify-center">
                    <legend> Gestion saldo</legend>
                    <label for="saldo" class="field cell w90">
                        <span style="font-weight: bold"> Saldo corrente: </span>
                        <span id="saldo">${profilo.saldo} €</span>
                    </label>
                    <label for="deposito" class="field cell w90">
                        <span style="font-weight: bold">Importo da depositare: </span>
                        <input type="number" name="deposito" id="deposito" min="0.0" step="0.01">
                    </label>
                    <label for="modifica" class="field cell w40 align-center justify-center">
                        <button type="submit" class="btn primary" id="modifica"> Deposita </button>
                    </label>
                </fieldset>
            </form>
        </div>
        <%@include file="../partials/customer/footer.jsp" %>
    </section>
</main>
</body>
</html>

