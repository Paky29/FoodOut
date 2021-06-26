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
        <jsp:param name="title" value="FAQ"/>
    </jsp:include>
</head>
<body>
<style>

    body {
        background-image: url("/prova_DB/images/sfondo.jpg");
        background-size: cover;
    }

    .content {
        background-color: white;
        transition: all .3s ease-in-out;
        margin: 20px;
        padding: 10px 40px;
        border-radius: 10px;
    }

    .topic{
        margin: 5px 0;
    }

    .title {
        font-weight: bold;
    }

    .subtitle {
        font-style: italic;
        margin: 2px 0;
    }

    .info {
        font-style: normal;
    }

    .menu{
        height: 30%;
        min-height: 240px;
        min-width: 200px;
        margin-bottom: 10px;
    }

    .menu > .segnalibro {
        text-decoration: none;
        color: black;
        font-weight: bold;
        font-style: italic;
        padding: 4px;
    }

    .menu > .title{
        color: var(--primary);
    }



</style>
<div class="app grid-x">
    <div class=" content menu w25 grid-x align-center">
        <h2 class="title"> FAQ </h2>
        <a href="#foodout" class="segnalibro cell"> Su Foodout</a>
        <a href="#utilizzo" class="segnalibro cell "> Utilizzare Foodout</a>
        <a href="#altro" class="segnalibro cell"> Altro? </a>
    </div>
<div class="content text w65">


    <div class="topic">
        <h2 class="title">
            <a name="foodout"> Su Foodout </a>
        </h2>
        <h4 class="subtitle">
            Cos'è Foodout?
        </h4>
        <p class="info">
            La missione di Fooodout è quella di trasformare il mondo del cibo a domicilio. Collaboriamo con i migliori
            ristoranti della città - dalle gemme locali alle grandi catene nazionali - per portarti dove vuoi i tuoi
            piatti
            preferiti.

            <br> Con centinaia di ristoranti e rider esperti, ti consegniamo il tuo ordine il più velocemente possibile.

        </p>
        <h4 class="subtitle">
            Qual è la storia di Foodout?
        </h4>
        <p class="info">
            Foodout è un'azienda digitale con una storia di successo. Nato da un progetto universitario, è
            riuscito ad imporsi nella scena della ristorazione italiana. La missione principale è stata sempre quella di
            portare i piatti dei migliori ristoranti direttamente casa dei loro clienti.
        </p>

    </div>
    <div class="topic">
        <h2 class="title">
            <a name="utilizzo"> Utilizzare FoodOut </a>
        </h2>
        <h4 class="subtitle">
            Come funziona?
        </h4>
        <p class="info">
            Aggiungi il tuo indirizzo di consegna per vedere quali ristoranti consegnano da te, scegli i tuoi piatti preferiti ed effettua l'ordine.
            <br>Quando il tuo ordine viene accettato, il ristorante inizia a prepararlo e a confezionarlo. Una volta pronto, un nostro rider lo ritira e lo porta da te.</p>
        <h4 class="subtitle">
            Che tipo di ristoranti trovo su Foodout?
        </h4>
        <p class="info">
            Ci prendiamo cura personalmente di proporti un'ampia selezione di ristoranti di alta qualità nella tua area. Questo significa che puoi trovare dal miglior ristorante giapponese, all'hamburgheria più gustosa, alla pizzeria italiana tradizionale con forno a legna. L'unica cosa che non troverai su Foodout sono ristoranti di scarsa qualità.
        </p>
        <h4 class="subtitle">
            C'è un ordine minimo?
        </h4>
        <p class="info">
            L'ordine minimo potrebbe variare a seconda del ristorante da cui ordini. Ti verrà mostrato al momento del checkout prima di pagare.        </p>
    </div>
    <div class="topic">
        <h2 class="title">
            <a name="altro"> Altro? </a>
        </h2>
        <h4 class="subtitle">
            Cosa succede se ho allergie?
        </h4>
        <p class="info">
            Se hai qualche allergia specifica e vuoi maggiori informazioni a proposito di qualche piatto indicato nel menu, per favore controlla la sezione note del ristorante nel menu. Per maggiori informazioni ti preghiamo di contattare il ristorante direttamente.
        </p>
        <h4 class="subtitle">
            Quando arriverete a consegnare nella mia zona?
        </h4>
        <p class="info">
            Ci espandiamo in fretta e speriamo di collaborare presto con i ristoranti vicini a te!
        </p>
        <h4 class="subtitle">
            Come viene confezionato il cibo?
        </h4>
        <p class="info">
            Il packaging dipende sempre dalla tipologia di cibo e dal ristorante da cui hai ordinato. I nostri ristoranti hanno molta cura nell'utilizzare packaging che mantenga la giusta temperatura il più a lungo possibile.
        </p>
        <h4 class="subtitle">
            Esiste l'app di Foodout?
        </h4>
        <p class="info">
            Ci stiamo lavorando e presto potrai avere i tuoi ristoranti a portata di mano. Stay tuned!
        </p>
    </div>
</div>
</div>
</body>
</html>
