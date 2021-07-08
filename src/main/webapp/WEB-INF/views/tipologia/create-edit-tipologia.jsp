<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="it" dir="ltr">
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Crea Tipologia"/>
    </jsp:include>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>
        .app {
            background: linear-gradient(var(--primary), white);
        <%--background-image: url("/FoodOut/images/sfondo.jpg");--%>
        }

        img {
            width: 100%;
            height: auto;
            opacity: 0.3;
        }
        .tip{
            padding: 1rem; <%--dimensione relativa al root--%>
            background-color:white;
            border-radius: 10px;
            opacity: revert;
        }

        .tip > * {
            margin:10px;
        }
    </style>
</head>

<body>
<form class="app grid-x justify-center align-center" action="${pageContext.request.contextPath}/tipologia/create-edit" method="post">
    <c:if test="${not empty alert}">
        <%@ include file="../partials/alert.jsp"%>
    </c:if>
    <fieldset class="grid-y cell w50 tip">
        <h2><c:choose>
            <c:when test="${function==0}"> Crea </c:when>
            <c:otherwise> Modifica </c:otherwise>
        </c:choose> tipologia </h2>
        <span> Nome </span>
        <label for="nome" class="field">
            <input type="text" name="nome" id="nome" value="${tipologia.nome}">
        </label>
        <span> Descrizione </span>
        <label for="descrizione" class="field">
            <input type="text" name="descrizione" id="descrizione" value="${tipologia.descrizione}">
        </label>
        <input type="number" style="display: none" name="function" id="function" value="${function}">
        <input type="text" style="display: none" name="nomeVecchio" id="nomeVecchio" value="${tipologia.nome}" readonly>

        <button type="submit" class="btn primary"> Salva </button>
    </fieldset>
</form>
</body>
</html>
