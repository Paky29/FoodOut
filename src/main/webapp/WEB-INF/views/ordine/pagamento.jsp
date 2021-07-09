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
        .pagamento{
            padding: 1rem;
            background-color:white;
            border-radius: 10px;
            opacity: revert;
        }

        .pagamento > * {
            margin:10px;
        }

        input{
            height: auto;
            line-height: unset;
        }
    </style>
</head>

<body>
<form class="app grid-x justify-center align-center" action="${pageContext.request.contextPath}/ordine/pagamento" method="post">
    <c:if test="${not empty alert}">
        <%@ include file="../partials/alert.jsp"%>
    </c:if>
    <fieldset class="grid-y cell w50 pagamento">
        <h2> Pagamento ordine </h2>
        <span style="font-weight: bold;font-style: italic"> Metodi di pagamento </span>
        <label class="field grid-x">
            <span class="cell" style="margin-bottom: 5px"><input type="radio" id="cash" value="cash" name="metodo"> <label for="cash">Pagamento in contanti</label></span>
            <c:choose>
                <c:when test="${saldo==false}">
                    <span class="cell"><input type="radio" id="saldo" value="saldo" name="metodo" disabled> <label for="saldo">Saldo insufficiente(<a href="/FoodOut/utente/saldo?function=1">Ricarica saldo</a>)</label></span>
                </c:when>
                <c:otherwise>
                    <span class="cell"><input type="radio" id="saldo" value="saldo" name="metodo"> <label for="saldo">Pagamento col saldo</label></span>
                </c:otherwise>
            </c:choose>
        </label>
        <label for="nota" class="field cell w80">
            <textarea rows="4" cols="100" type="text" name="nota" id="nota" maxlength="150" placeholder="Note extra"></textarea>
        </label>
        <button type="submit" class="btn primary"> Conferma acquisto </button>
    </fieldset>
</form>
</body>
</html>
