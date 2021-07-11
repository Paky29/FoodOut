<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Chi siamo"/>
    </jsp:include>
</head>
<body>
<style>

    body {
        background-image: url("/FoodOut/images/sfondo.jpg");
        background-size: cover;
    }

    .content {
        background-color: white;
        transition: all .3s ease-in-out;
        padding: 10px 40px;
        margin: 40px;
    }

    .title {
        font-weight: bold;
        font-family: Myriad;
        margin=3px;
    }

    .info {
        font-style: normal;
    }

</style>

<div class="app grid-x">
    <div class=" content w100 grid-x align-center">
        <h1 class="title">Chi siamo?</h1>
        <p class="info">
            FoodOut è il portale leader per ordinare online per ogni tuo pasto, e nasce in Italia dal 2021 e opera
            con tanti ristoranti su tutto il territorio.
        </p>
        <p class="info">
            Grazie alla sua semplicità consente di destreggiarsi comodamente tra i vari ristoranti della tua zona,
            trovando ciò che vuoi con la possibilità di godere di tante offerte e sconti.
        </p>
        <p class="info">
            FoodOut mette a tua disposizione molti piatti e ristoranti locali e ti consente di ordinare le tue
            pietanze preferite dove e quando vuoi.
        </p>
    </div>
</body>
</html>
