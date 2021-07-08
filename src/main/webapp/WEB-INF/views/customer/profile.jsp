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
            <form class="grid-x justify-center align-center" action="${pageContext.request.contextPath}/utente/update" method="post">
                <fieldset
                        class="grid-x cell justify-center">
                    <legend> Profilo</legend>
                    <label for="nome" class="field cell w40">
                        <span style="font-weight: bold"> Nome: </span>
                        <input type="text" name="nome" id="nome" value="${profilo.nome}">
                    </label>
                    <label for="cognome" class="field cell w40">
                        <span style="font-weight: bold"> Cognome: </span>
                        <input type="text" name="cognome" id="cognome" value="${profilo.cognome}">
                    </label>
                    <label for="provincia" class="field cell w40">
                        <span style="font-weight: bold"> Provincia: </span>
                        <input type="text" name="provincia" id="provincia" value="${profilo.provincia}">
                    </label>
                    <label for="citta" class="field cell w40">
                        <span style="font-weight: bold"> Citt&agrave: </span>
                        <input type="text" name="citta" id="citta" value="${profilo.citta}">
                    </label>
                    <label for="via" class="field cell w40">
                        <span style="font-weight: bold"> Via: </span>
                        <input type="text" name="via" id="via" value="${profilo.via}">
                    </label>
                    <label for="civico" class="field cell w40">
                        <span style="font-weight: bold"> Civico: </span>
                        <input type="text" name="civico" id="civico" value="${profilo.civico}">
                    </label>
                    <label for="email" class="field cell w80">
                        <span style="font-weight: bold"> Email: </span>
                        <input type="text" name="email" id="email" value="${profilo.email}" readonly>
                    </label>
                    <label for="blank" class="field cell w10" style="visibility: hidden ">
                        <span style="font-weight: bold"></span>
                        <input type="text" name="id" id="blank" value="${profilo.codice}" readonly>
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
        <%@include file="../partials/customer/footer.jsp" %>
    </section>
</main>
</body>
</html>

