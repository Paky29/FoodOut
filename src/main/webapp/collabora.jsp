<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Collabora con noi"/>
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
        margin=10px;
    }

</style>

<div class="app grid-x">
    <div class=" content w100 grid-x align-center">
        <h1 class="title">Sei un ristoratore?</h1> <br>
        <form method="POST" id="formristorante" action="mailto:infofoodout@gmail.com" enctype="text/plain">
            <p>Nome del ristorante</p>
                <input type="text" name="nome" id="nome" placeholder="Nome">
            <p>Indirizzo e civico</p>
                <input type="text" name="indristorante">
            <p>CAP</p>
                <input type="text" name="capristorante">
            <p>Città</p>
                <input type="text" name="cittaristorante">
            <p>Spesa minima</p>
                <input type="number" name="spesaMinima" placeholder="Spesa minima (€)" step="0.01">
            <p>Tasso consegna</p>
                <input type="number" name="tassoConsegna" placeholder="Tasso Consegna (€)" step="0.01">
            <p>Informazioni aggiuntive</p>
                <textarea rows="4" cols="100" type="text" name="info" id="info" maxlength="200" placeholder="Informazioni sul tuo ristorante"></textarea>
            <br>
            <input type="submit" class="btn primary w30">
    </div>
</body>
</html>
