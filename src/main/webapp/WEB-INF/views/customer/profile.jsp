<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Profilo"/>
        <jsp:param name="scripts" value="customer,update_validation"/>
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
            <form class="grid-x justify-center align-center" action="${pageContext.request.contextPath}/utente/update" method="post" novalidate>
                <fieldset
                        class="grid-x cell justify-center">
                    <legend> Profilo</legend>
                    <label for="nome" class="field cell w40">
                        <span style="font-weight: bold"> Nome: </span>
                        <span class="grid-x cell">
                            <input class="cell" type="text" name="nome" id="nome" value="${profilo.nome}" maxlength="30" pattern="^([a-zA-Z]|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" required> <%--si sminchia--%>
                            <small class="errMsg cell">  </small>
                        </span>
                    </label>
                    <label for="cognome" class="field cell w40">
                        <span style="font-weight: bold"> Cognome: </span>
                        <span class="grid-x cell">
                            <input class="cell" type="text" name="cognome" id="cognome" value="${profilo.cognome}" maxlength="30" pattern="^([a-zA-Z]|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" required> <%--si sminchia--%>
                            <small class="errMsg cell">  </small>
                        </span></label>
                    <label for="provincia" class="field cell w40">
                        <span style="font-weight: bold"> Provincia: </span>
                        <span class="grid-x cell">
                            <input class="cell" type="text" name="provincia" id="provincia" value="${profilo.provincia}" maxlength="30" pattern="^([a-zA-Z]|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" required> <%--si sminchia--%>
                            <small class="errMsg cell">  </small>
                        </span></label>
                    </label>
                    <label for="citta" class="field cell w40">
                        <span style="font-weight: bold"> Citt&agrave;: </span>
                        <span class="grid-x cell">
                            <input class="cell" type="text" name="citta" id="citta" value="${profilo.citta}" maxlength="30" pattern="^([a-zA-Z]|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'){1,30}$" required> <%--si sminchia--%>
                            <small class="errMsg cell">  </small>
                        </span></label></label>
                    <label for="via" class="field cell w40">
                        <span style="font-weight: bold"> Via: </span>
                        <span class="grid-x cell">
                            <input class="cell" type="text" name="via" id="via" value="${profilo.via}" maxlength="50" pattern="^(\w|\s|[è,à,ò,ù,ì,À, Ò, È, Ù, Ì]|'|\.){1,50}$" required> <%--si sminchia--%>
                            <small class="errMsg cell">  </small>
                        </span></label></label>
                    </label>
                    <label for="civico" class="field cell w40">
                        <span style="font-weight: bold"> Civico: </span>
                        <span class="grid-x cell">
                            <input class="cell" type="number" name="civico" id="civico" value="${profilo.civico}" min="1" required> <%--si sminchia--%>
                            <small class="errMsg cell">  </small>
                        </span></label></label>
                    </label>
                    <label for="email" class="field cell w80">
                        <span style="font-weight: bold"> Email: </span>
                        <span class="grid-x cell">
                            <input class="cell" type="email" name="email" id="email" value="${profilo.email}" maxlength="50" pattern="^([\w\.\-]+)@([\w\-]+)((\.(\w){2,})+)$" required> <%--si sminchia--%>
                            <small class="errMsg cell">  </small>
                        </span></label></label>
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

