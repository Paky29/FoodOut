<%--
  Created by IntelliJ IDEA.
  User: User01
  Date: 22/06/2021
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="it" dir="ltr">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Pagamento"/>
    </jsp:include>
    <style>
        .app {
            background: linear-gradient(var(--primary), white);
        }

        img {
            width: 100%;
            height: auto;
            opacity: 0.3;
        }
        .recensione{
            padding: 1rem;
            background-color:white;
            border-radius: 10px;
            opacity: revert;
        }

        .recensione > * {
            margin:10px;
        }

        input{
            height: auto;
            line-height: unset;
        }

        #giudizio{
            resize: none;
        }
    </style>
</head>

<body>
<form class="app grid-x justify-center align-center" action="${pageContext.request.contextPath}/ordine/add-recensione" method="post">
    <c:if test="${not empty alert}">
        <%@ include file="../partials/alert.jsp"%>
    </c:if>
    <fieldset class="grid-y cell w50 recensione">
        <h2> Aggiungi Recensione </h2>
        <label for="voto" class="field cell w80">
            <input type="number" name="voto" id="voto" min="1" max="5" placeholder="Voto">
        </label>
        <label for="giudizio" class="field cell w80">
            <textarea rows="4" cols="100" type="text" name="giudizio" id="giudizio" maxlength="150" placeholder="Giudizio"></textarea>
        </label>
        <input  style="display:none;" type="number" name="id" value="${id}">
        <button type="submit" class="btn primary"> Aggiungi </button>
    </fieldset>
</form>
</body>
</html>
