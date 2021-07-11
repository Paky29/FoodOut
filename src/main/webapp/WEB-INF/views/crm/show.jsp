<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Profilo"/>
        <jsp:param name="scripts" value="crm"/>
        <jsp:param name="styles" value="crm"/>
    </jsp:include>
    <style>
        input {
            height: 40px;
            line-height: 40px;
        }
    </style>
</head>
<body>
<main class="app">
    <%@include file="../partials/crm/sidebar.jsp" %>
    <section class="content grid-y">
        <%@include file="../partials/crm/header.jsp" %>
        <div class="body grid-x justify-center">
            <c:if test="${not empty alert}">
                <%@ include file="../partials/alert.jsp"%>
            </c:if>
            <form class="grid-x justify-center align-center" action="${pageContext.request.contextPath}/utente/update" method="post">
                <fieldset
                        class="grid-x cell justify-center">
                    <legend> Profilo</legend>
                    <label for="nome" class="field cell w40">
                        <span style="font-weight: bold"> Nome: </span>
                        <input type="text" name="nome" id="nome" value="${profilo.nome}" required maxlength="30">
                    </label>
                    <label for="cognome" class="field cell w40">
                        <span style="font-weight: bold"> Cognome: </span>
                        <input type="text" name="cognome" id="cognome" value="${profilo.cognome}" required maxlength="30">
                    </label>
                    <label for="provincia" class="field cell w40">
                        <span style="font-weight: bold"> Provincia: </span>
                        <input type="text" name="provincia" id="provincia" value="${profilo.provincia}" required maxlength="30">
                    </label>
                    <label for="citta" class="field cell w40">
                        <span style="font-weight: bold"> Citt&agrave: </span>
                        <input type="text" name="citta" id="citta" value="${profilo.citta}" required maxlength="30">
                    </label>
                    <label for="via" class="field cell w40">
                        <span style="font-weight: bold"> Via: </span>
                        <input type="text" name="via" id="via" value="${profilo.via}" required maxlength="50">
                    </label>
                    <label for="civico" class="field cell w40">
                        <span style="font-weight: bold"> Civico: </span>
                        <input type="number" name="civico" id="civico" value="${profilo.civico}" required>
                    </label>
                    <label for="email" class="field cell w50">
                        <span style="font-weight: bold"> Email: </span>
                        <input type="email" name="email" id="email" value="${profilo.email}" readonly maxlength="50">
                    </label>
                    <label for="blank" class="field cell w10" style="visibility: hidden ">
                        <span style="font-weight: bold"></span>
                        <input type="number" name="id" id="blank" value="${profilo.codice}" readonly>
                    </label>
                    <label for="modifica" class="field cell w40 align-center justify-center">
                        <button type="submit" class="btn primary" id="modifica"> Modifica dati</button>
                    </label>
                    <label for="modificaPW" class="field cell w40 align-center justify-center">
                        <button type="submit" class="btn primary" id="modificaPW" formaction="${pageContext.request.contextPath}/utente/update-pw" formmethod="get"> Modifica Password</button>
                    </label>
                </fieldset>

            </form>
        </div>
        <%@include file="../partials/crm/footer.jsp" %>
    </section>
</main>
</body>
</html>
