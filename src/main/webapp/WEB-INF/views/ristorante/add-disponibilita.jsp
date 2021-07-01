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
        <jsp:param name="scripts" value="add_disponibilita"/>
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
<form class="app grid-x justify-center align-center" action="${pageContext.request.contextPath}/ristorante/disponibilita" method="post">
    <fieldset class="grid-x cell w90 add-disp justify-center"> <%-- vedere se è meglio  w50 o w75 ,  con justify-center , align-center o meno--%>
        <h1 id="title" class="cell"> Scegli il tuo orario </h1>
        <label for="lunedi" class="field w33 cell">
            <span id="lunedi" style="font-weight: bold"> ● LUNEDI </span>
        </label>
        <span class="w50">
        <label for="apertura0" class="field w10 cell">
            <input type="time" name="apertura0" id="apertura0" class="open">
        </label>
        <label for="chiusura0" class="field w10 cell">
            <input type="time" name="chiusura0" id="chiusura0" class="open">
        </label>
        <label for="chiuso0" class="cell w10">
            <input type="checkbox" onclick="disableThisOpens(this)" name="chiuso0" value="Chiuso" id="chiuso0" class="closed">
            CHIUSO
        </label>
        </span>

        <label for="martedi" class="field w33 cell">
            <span id="martedi"> ● MARTEDI </span>
        </label>
        <span class="w50">
        <label for="apertura1" class="field w10 cell">
            <input type="time" name="apertura1" id="apertura1" class="open">
        </label>
        <label for="chiusura1" class="field w10 cell">
            <input type="time" name="chiusura1" id="chiusura1" class="open">
        </label>
        <label for="chiuso1" class="cell w10">
            <input type="checkbox" onclick="disableThisOpens(this)" name="chiuso1" value="Chiuso" id="chiuso1" class="closed">
            CHIUSO
        </label>
        </span>

        <label for="mercoledi" class="field w33 cell">
            <span id="mercoledi"> ● MERCOLEDI </span>
        </label>
        <span class="w50">
        <label for="apertura2" class="field w10 cell">
            <input type="time" name="apertura2" id="apertura2" class="open">
        </label>
        <label for="chiusura2" class="field w10 cell">
            <input type="time" name="chiusura2" id="chiusura2" class="open">
        </label>
        <label for="chiuso2" class="cell w10">
            <input type="checkbox" onclick="disableThisOpens(this)" name="chiuso2" value="Chiuso" id="chiuso2" class="closed">
            CHIUSO
        </label>
        </span>

        <label for="giovedi" class="field w33 cell">
            <span id="giovedi"> ● GIOVEDI </span>
        </label>
        <span class="w50">
        <label for="apertura3" class="field w10 cell">
            <input type="time" name="apertura3" id="apertura3" class="open">
        </label>
        <label for="chiusura3" class="field w10 cell">
            <input type="time" name="chiusura3" id="chiusura3" class="open">
        </label>
        <label for="chiuso3" class="cell w10">
            <input type="checkbox" onclick="disableThisOpens(this)" name="chiuso3" value="Chiuso" id="chiuso3" class="closed">
            CHIUSO
        </label>
        </span>

        <label for="venerdi" class="field w33 cell">
            <span id="venerdi"> ● VENERDI </span>
        </label>
        <span class="w50">
        <label for="apertura4" class="field w10 cell">
            <input type="time" name="apertura4" id="apertura4" class="open">
        </label>
        <label for="chiusura4" class="field w10 cell">
            <input type="time" name="chiusura4" id="chiusura4" class="open">
        </label>
        <label for="chiuso4" class="cell w10">
            <input type="checkbox" onclick="disableThisOpens(this)" name="chiuso4" value="Chiuso" id="chiuso4" class="closed">
            CHIUSO
        </label>
        </span>

        <label for="sabato" class="field w33 cell">
            <span id="sabato"> ● SABATO </span>
        </label>
        <span class="w50">
        <label for="apertura5" class="field w10 cell">
            <input type="time" name="apertura5" id="apertura5" class="open">
        </label>
        <label for="chiusura5" class="field w10 cell">
            <input type="time" name="chiusura5" id="chiusura5" class="open">
        </label>
        <label for="chiuso5" class="cell w10">
            <input type="checkbox" onclick="disableThisOpens(this)" name="chiuso5" value="Chiuso" id="chiuso5" class="closed">
            CHIUSO
        </label>
        </span>

        <label for="domenica" class="field w33 cell">
            <span id="domenica"> ● DOMENICA </span>
        </label>
        <span class="w50">
        <label for="apertura6" class="field w10 cell">
            <input type="time" name="apertura6" id="apertura6" class="open">
        </label>
        <label for="chiusura6" class="field w10 cell">
            <input type="time" name="chiusura6" id="chiusura6" class="open">
        </label>
        <label for="chiuso6" class="cell w10">
            <input type="checkbox" onclick="disableThisOpens(this)" name="chiuso6" value="Chiuso" id="chiuso6" class="closed">
            CHIUSO
        </label>
        </span>
        <input style="display: none" name="id" id="id" value="${id}" readonly>
        <span class="grid-x cell justify-center">
            <button type="submit" class="btn primary" name="button" value="add">Imposta orario</button>
        </span>
    </fieldset>
</form>
<%--<script>
    var checks=document.getElementsByClassName("closed");
    for(let i=0; i<checks.length; i++) {
        var parent = checks[i].parentElement.parentElement;
        var opens = parent.getElementsByClassName("open");
        if (checks[i].checked) {
            for (let j = 0; j < opens.length; j++) {
                opens[j].setAttribute("disabled", true);
                opens[j].value = null;
            }
        } else {
            for (let j = 0; i < opens.length; j++) {
                opens[j].removeAttribute("disabled");
            }
        }
    }
</script>--%>
</body>
</html>
