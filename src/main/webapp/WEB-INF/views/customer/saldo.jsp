<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Profilo"/>
        <jsp:param name="scripts" value="customer,saldo_validation"/>
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
            <form class="grid-x justify-center align-center" action="${pageContext.request.contextPath}/utente/deposit?function=${function}" method="post" novalidate>
                <fieldset
                        class="grid-x cell justify-center">
                    <legend> Gestion saldo</legend>
                    <label for="saldo" class="field cell w90">
                        <span style="font-weight: bold"> Saldo corrente: </span>
                        <span id="saldo">${profilo.saldo} €</span>
                    </label>
                    <label for="deposito" class="field cell w90">
                        <span style="font-weight: bold">Importo da depositare: </span>
                        <span class="grid-x">
                        <input class="cell" type="number" name="deposito" id="deposito" min="0.0" step="0.01" pattern="^[0-9]\d{0,9}(\.\d{1,3})?$" required>
                        <small class="cell errMsg"> </small>
                        </span>
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

