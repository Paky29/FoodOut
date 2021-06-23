<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Home"/>
        <jsp:param name="styles" value="header"/>
    </jsp:include>
</head>
<body>
<style>

    .top {
        position: relative;
    }


    .index {
        padding: 1rem;
    <%--dimensione relativa al root--%> background-color: white;
        border-radius: 10px;
        opacity: revert;
    }

    .index > * {
        margin: 10px;
    }

    input:focus {
        outline: 1px solid var(--primary);
    }

    .tipologie {
        padding: 1rem;
    <%--dimensione relativa al root--%> background-color: white;
        border-radius: 10px;
        opacity: revert;
    }

    .box {
        border: 2px solid var(--primary);
        margin: 10px;
        padding: 15px;
        font-weight: bold;
        font-size: 2vw;
        color: white;
        background-color: var(--primary);
    }

    .cover-image{
        max-width: 100%;
        height: 50vh;
    }


</style>
<div class="app grid-x">
    <jsp:include page="WEB-INF/views/partials/header.jsp">
        <jsp:param name="title" value="Header"/>
    </jsp:include>


    <div class="content cell">
        <div class="tipologie app grid-x justify-center align-center">
            <c:forEach items="${tipologie}" var="tipologia">
                <p class="tipologia box "> ${tipologia.nome} </p>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>
