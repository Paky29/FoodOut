<%--
  Created by IntelliJ IDEA.
  User: User01
  Date: 26/06/2021
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Contatti"/>
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
    }

    .subtitle {
        font-style: italic;
        font-weight: lighter;
        font-family: Myriad;
    }

    #div1  {
        max-height: 250px;
    }

</style>

<div class="app grid-x">
    <div id="div1" class="content text w100 cell">

        <h2 class="title"> Contattaci </h2>
        <p class="subtitle"> Hai una domanda, oppure hai bisogno di consigli o assistenza? </p>
    </div>

    <div class="content text w100 grid-x justify-center" >
        <div class="content w25">
            <img src="/FoodOut/icons/call_center.svg" align="center">
            <h3 class="title"> Assistenza per ordini attivi </h3>
            <p class="subtitle"> Assistenza telefonica per ordini attivi 24 ore su 24, 7 giorni su 7 </p>
        </div>

        <div class="content text w25">
            <img src="/FoodOut/icons/time.svg" align="center">
            <h3 class="title"> Soluzioni rapide </h3>
            <p class="subtitle"> Cerca le risposte alle domande più comuni nella sezione "Aiuto" </p>
        </div>

        <div class="content text w25">
            <img src="/FoodOut/icons/computer.svg" align="center">
            <h3 class="title"> A portata di mano </h3>
            <p class="subtitle"> Utilizza il Restaurant Hub per gestire il tuo account e contattare il nostro team </p>
        </div>
    </div>

    <div class="content text w100 grid-x justify-center">

        <h2 class="title w100""> Dove trovare assistenza e informazioni</h2>
        <div class="content text w35 justify-center">
            <img src="/FoodOut/icons/telefono.svg" >
            <h3 class="title"> Chiamaci </h3>
            <p class="subtitle"> Chiama il nostro numero verde 800 800 200 </p>
        </div>

        <div class="content text w35 justify-center">
            <img src="/FoodOut/icons/mail.svg">
            <h3 class="title"> Scrivici </h3>
            <p class="subtitle"> Scrivi alla nostro mail info@foodout.com </p>
        </div>
    </div>
</body>
</html>
