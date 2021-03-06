<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it" dir="ltr">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Modifica Disponibilita"/>
        <jsp:param name="styles" value="crm"/>
        <jsp:param name="scripts" value="add_disponibilita,dis_validation"/>
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
<form class="app grid-x justify-center align-center"
      action="${pageContext.request.contextPath}/ristorante/disponibilita?function=1" method="post">
    <c:if test="${not empty alert}">
        <%@ include file="../partials/alert.jsp"%>
    </c:if>
    <fieldset
            class="grid-x cell w90 add-disp justify-center"> <%-- vedere se è meglio  w50 o w75 ,  con justify-center , align-center o meno--%>
        <h1 id="title" class="cell"> Scegli il tuo orario </h1>
        <c:forEach begin="0" end="${fn:length(ristorante.giorni)-1}" var="day">
        <label for="${ristorante.giorni[day].giorno}" class="field w33 cell">
            <span id="${ristorante.giorni[day].giorno}" style="font-weight: bold"> ● ${fn:toUpperCase(ristorante.giorni[day].giorno)} </span>
        </label>
        <span class="w50">
            <c:choose>
                <c:when test="${ristorante.giorni[day].oraApertura==ristorante.giorni[day].oraChiusura}">
                    <label for="apertura${day}" class="field w10 cell">
                        <input type="time" name="apertura${day}" id="apertura${day}" class="open" disabled>
                    </label>
                    <label for="chiusura${day}" class="field w10 cell">
                        <input type="time" name="chiusura${day}" id="chiusura${day}" class="open" disabled>
                    </label>
                    <label for="chiuso${day}" class="cell w10">
                        <input type="checkbox" onclick="disableThisOpens(this)" name="chiuso${day}" value="Chiuso" id="chiuso${day}"
                        class="closed" checked>
                        CHIUSO
                    </label>
                </c:when>
                <c:otherwise>
                    <label for="apertura${day}" class="field w10 cell">
                        <input type="time" name="apertura${day}" id="apertura${day}" class="open" value="${ristorante.giorni[day].oraApertura}">
                    </label>
                    <label for="chiusura${day}" class="field w10 cell">
                        <input type="time" name="chiusura${day}" id="chiusura${day}" class="open" value="${ristorante.giorni[day].oraChiusura}">
                    </label>
                    <label for="chiuso${day}" class="cell w10">
                        <input type="checkbox" onclick="disableThisOpens(this)" name="chiuso${day}" value="Chiuso" id="chiuso${day}"
                        class="closed">
                        CHIUSO
                    </label>
                </c:otherwise>
            </c:choose>
        </span>
        </c:forEach>
        <input style="display: none" name="id" id="id" value="${ristorante.codice}" readonly>
        <span class="grid-x cell justify-center">
            <button type="submit" class="btn primary" name="button" value="update">Imposta orario</button>
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
