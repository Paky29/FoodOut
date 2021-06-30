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
        <jsp:param name="title" value="Aggiungi Disponibilita"/>
        <jsp:param name="styles" value="crm"/>
    </jsp:include>
    <style>
        .app {
            background: linear-gradient(var(--primary), white);
        <%--background-image: url("http://localhost:8080/images/image.jpg");--%>
        }

        .add-disp {
            padding: 1rem;
        <%--dimensione relativa al root--%> background-color: white;
            border-radius: 10px;
        }

        .add-disp > * {
            margin: 10px;
        }

        button {
            border-radius: 3px;
        }

        input {
            height: auto;
        }

        fieldset > label > span {
            font-weight: bold;
            font-style: normal;
        }

        fieldset {
            font-family: Myriad;
        }

    </style>
</head>

<body>
<form class="app grid-x justify-center align-center" action="${pageContext.request.contextPath}/ristorante/add"
      method="post">
    <fieldset class="grid-x cell w100 add-disp justify-center"> <%-- vedere se è meglio  w50 o w75 ,  con justify-center , align-center o meno--%>
        <h1 id="title" class="cell"> Scegli il tuo orario </h1>
        <label for="lunedi" class="field w33 cell">
            <span id="lunedi" style="font-weight: bold"> ● LUNEDI </span>
        </label>
        <span>
        <label for="apLunedi" class="field w10 cell">
            <input type="time" name="apLunedi" id="apLunedi">
        </label>
        <label for="cLunedi" class="field w10 cell">
            <input type="time" name="cLunedi" id="cLunedi">
        </label>
        <label for="chiuso_Lunedi" class="cell w10">
            <input type="checkbox" name="chiusoLunedi" value="Chiuso" id="chiuso_Lunedi">
            CHIUSO
        </label>
        </span>

        <label for="martedi" class="field w33 cell">
            <span id="martedi"> ● MARTEDI </span>
        </label>
        <span>
        <label for="apMartedi" class="field w10 cell">
            <input type="time" name="apMartedi" id="apMartedi">
        </label>
        <label for="cMartedi" class="field w10 cell">
            <input type="time" name="cMartedi" id="cMartedi">
        </label>
        <label for="chiuso_Martedi" class="cell w10">
            <input type="checkbox" name="chiusoMartedi" value="Chiuso" id="chiuso_Martedi">
            CHIUSO
        </label>
        </span>

        <label for="mercoledi" class="field w33 cell">
            <span id="mercoledi"> ● MERCOLEDI </span>
        </label>
        <span>
        <label for="apMercoledi" class="field w10 cell">
            <input type="time" name="apMercoledi" id="apMercoledi">
        </label>
        <label for="cMercoledi" class="field w10 cell">
            <input type="time" name="cMercoledi" id="cMercoledi">
        </label>
        <label for="chiuso_Mercoledi" class="cell w10">
            <input type="checkbox" name="chiusoMercoledi" value="Chiuso" id="chiuso_Mercoledi">
            CHIUSO
        </label>
        </span>

        <label for="giovedi" class="field w33 cell">
            <span id="giovedi"> ● GIOVEDI </span>
        </label>
        <span>
        <label for="apGiovedi" class="field w10 cell">
            <input type="time" name="apGiovedi" id="apGiovedi">
        </label>
        <label for="cGiovedi" class="field w10 cell">
            <input type="time" name="cGiovedi" id="cGiovedi">
        </label>
        <label for="chiuso_Giovedi" class="cell w10">
            <input type="checkbox" name="chiusoGiovedi" value="Chiuso" id="chiuso_Giovedi">
            CHIUSO
        </label>
        </span>

        <label for="venerdi" class="field w33 cell">
            <span id="venerdi"> ● VENERDI </span>
        </label>
        <span>
        <label for="apVenerdi" class="field w10 cell">
            <input type="time" name="apVenerdi" id="apVenerdi">
        </label>
        <label for="cVenerdi" class="field w10 cell">
            <input type="time" name="cVenerdi" id="cVenerdi">
        </label>
        <label for="chiuso_Venerdi" class="cell w10">
            <input type="checkbox" name="chiusoVenerdi" value="Chiuso" id="chiuso_Venerdi">
            CHIUSO
        </label>
        </span>

        <label for="sabato" class="field w33 cell">
            <span id="sabato"> ● SABATO </span>
        </label>
        <span>
        <label for="apSabato" class="field w10 cell">
            <input type="time" name="apSabato" id="apSabato">
        </label>
        <label for="cSabato" class="field w10 cell">
            <input type="time" name="cSabato" id="cSabato">
        </label>
        <label for="chiuso_Sabato" class="cell w10">
            <input type="checkbox" name="chiusoSabato" value="Chiuso" id="chiuso_Sabato">
            CHIUSO
        </label>
        </span>

        <label for="domenica" class="field w33 cell">
            <span id="domenica"> ● DOMENICA </span>
        </label>
        <span>
        <label for="apDomenica" class="field w10 cell">
            <input type="time" name="apDomenica" id="apDomenica">
        </label>
        <label for="cDomenica" class="field w10 cell">
            <input type="time" name="cDomenica" id="cDomenica">
        </label>
        <label for="chiuso_Domenica" class="cell w10">
            <input type="checkbox" name="chiusoDomenica" value="Chiuso" id="chiuso_Domenica">
            CHIUSO
        </label>
        </span>

    </fieldset>
</form>
</body>
</html>
