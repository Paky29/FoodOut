<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Profilo"/>
        <jsp:param name="scripts" value="customer,show_zona"/>
        <jsp:param name="styles" value="customer,show_zona"/>
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
            <section class="cell w100 grid-x container justify-center">
                <div class="cell w100 grid-x show-ris">
                    <div class="grid-x justify-center info-ris cell">
                        <fieldset class="grid-x cell w100 index">
                            <c:choose>
                                <c:when test="${not empty ristoranti}">
                                    <h2 class="cell"> Ristoranti preferiti </h2>
                                    <c:forEach items="${ristoranti}" var="ristorante">
                                        <label class="field cell w100 ristorante grid-x" onclick="showRisDetails(this)" title="Clicca per visitare">
                                            <div class="w70">
                                                <div class="w80" style="font-weight: bold;">${ristorante.nome}</div>
                                                <c:forEach begin="0" end="2" var="counter">
                                                    <span class="w80" style="color:black;font-weight: normal; font-style: italic"> ${ristorante.tipologie[counter].nome}</span>
                                                </c:forEach>
                                                <div class="w80">
                                                    <c:forEach var="counter" begin="1" end="${ristorante.rating}">
                                                        <%@include file="../../../icons/moto.svg" %>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                            <img class="w80" src="/FoodOut/covers/${ristorante.urlImmagine}">
                                            <input style="display: none" id="id" name="id" value="${ristorante.codice}"/>
                                        </label>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise> <h2> Non sono presenti ristoranti preferiti</h2> </c:otherwise>
                            </c:choose>
                        </fieldset>
                    </div>
                    <div class="cell justify-center ">
                        <jsp:include page="../partials/paginator.jsp">
                            <jsp:param name="risorsa" value="ristoranti-pref"/>
                        </jsp:include>
                    </div>
                </div>
            </section>
        </div>
        <%@include file="../partials/customer/footer.jsp" %>
    </section>
</main>
</body>
</html>