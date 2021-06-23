<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Home"/>
    </jsp:include>
    <style>

        .top {
            background-image: url("/FoodOut/images/sfondo.jpg");
            background-repeat: no-repeat;
            background-size: cover;
            height:80vh;
        }


        .index{
            padding: 1rem; <%--dimensione relativa al root--%>
            background-color:white;
            border-radius: 10px;
            opacity: revert;
        }

        .index > * {
            margin:10px;
        }

        input:focus  {
            outline: 1px solid var(--primary);
        }
    </style>
</head>
<body>
<div class="top">
    <jsp:include page="WEB-INF/views/partials/header.jsp">
        <jsp:param name="title" value="Header"/>
    </jsp:include>

    <form class="app grid-x justify-center align-center" action="/ristorante/zona" method="post">
        <fieldset class="grid-y cell w50 index">
            <h2> Inserisci il tuo indirizzo </h2>
            <label for="indirizzo" class="field">
                <input type="text" name="indirizzo" id="indirizzo">
            </label>
            <button type="submit" class="btn primary"> Cerca ristoranti </button>
        </fieldset>
    </form>
</div>
<div class="tipologie app grid-x justify-center align-center">
    <c:forEach items="${tipologie}" var="tipologia">
        <p class="tipologia box "> ${tipologia.nome} </p>
    </c:forEach>
</div>
</body>
</html>
