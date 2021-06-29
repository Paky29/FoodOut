<%--
  Created by IntelliJ IDEA.
  User: User01
  Date: 22/06/2021
  Time: 11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it" dir="ltr">
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Sign up"/>
    </jsp:include>
    <style>
        .app {
            background: linear-gradient(var(--primary), white);
        <%--background-image: url("http://localhost:8080/images/image.jpg");--%>
        }
        .signup{
            padding: 1rem; <%--dimensione relativa al root--%>
            background-color:white;
            border-radius: 10px;
        }

        .signup > * {
            margin:10px;
        }

        button {
            border-radius: 3px;
        }

        input {
            height: 50px;
            line-height: 27px;
        }

    </style>
</head>

<body>
<form class="app grid-x justify-center align-center" action="${pageContext.request.contextPath}/ristorante/add" method="post" enctype="multipart/form-data">
    <fieldset class="grid-x cell w75 signup justify-center"> <%-- vedere se è meglio  w50 o w75 ,  con justify-center , align-center o meno--%>
        <h2 class="cell"> Aggiungi ristorante</h2>
        <label for="nome" class="field cell w82">
            <input type="text" name="nome" id="nome" placeholder="Nome">
        </label>
        <label for="provincia" class="field w40 cell">
            <input type="text" name="provincia" id="provincia" placeholder="Provincia">
        </label>
        <label for="citta" class="field w40 cell">
            <input type="text" name="citta" id="citta" placeholder="Citta">
        </label>
        <label for="via" class="field w50 cell" >
            <input type="text" name="via" id="via" placeholder="Via">
        </label>
        <label for="civico" class="field w30 cell">
            <input type="number" name="civico" id="civico" placeholder="n°">
        </label>
        <label for="spesaMinima" class="field w40 cell">
            <input type="number" name="spesaMinima" id="spesaMinima" placeholder="Spesa minima (€)">
        </label>
        <label for="tassoConsegna" class="field w40 cell">
            <input type="number" name="tassoConsegna" id="tassoConsegna" placeholder="Tasso Consegna (€)">
        </label>
        <label for="urlImmagine" class="field w40 cell">
            <input type="file" name="urlImmagine" id="urlImmagine" placeholder="Immagine del tuo ristorante">
        </label>
        <label for="info" class="field cell w80">
            <textarea rows="4" cols="100" type="text" name="info" id="info" maxlength="200" placeholder="Informazioni sul tuo ristorante"></textarea>
        </label>
        <span class="grid-x cell justify-center">
            <button type="submit" class="btn primary w30"> Aggiungi </button>
        </span>
    </fieldset>
</form>
</body>
</html>
