<%--
  Created by IntelliJ IDEA.
  User: User01
  Date: 16/06/2021
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Profilo"/>
        <jsp:param name="scripts" value="crm"/>
        <jsp:param name="styles" value="crm"/>
    </jsp:include>
</head>
<body>
<main class="app">
    <%@include file="../partials/crm/sidebar.jsp" %>
    <section class="content grid-y">
        <%@include file="../partials/crm/header.jsp" %>
        <div class="body grid-x justify-center">
            <%--<div class="grid-x show "> <%-- vedere se è meglio  w50 o w75 ,  con justify-center , align-center o meno
                <h1 id="title" class="cell"> Profilo </h1>
                <label for="nome" class="field w40 cell">
                    <span style="font-weight: bold"> Nome: </span>
                    <p type="text" name="nome" id="nome"> ${utenteSession.nome} </p>
                </label>
                <label for="cognome" class="field w40 cell">
                    <span style="font-weight: bold"> Cognome: </span>
                    <p type="text" name="nome" id="cognome"> ${utenteSession.nome} </p>
                </label>
                <label for="provincia" class="field w40 cell">
                    <span style="font-weight: bold"> Provincia: </span>
                    <p type="text" name="nome" id="provincia"> ${utenteSession.nome} </p>
                </label>
                <label for="citta" class="field w40 cell">
                    <span style="font-weight: bold"> Citt&agrave: </span>
                    <p type="text" name="nome" id="citta"> ${utenteSession.nome} </p>
                </label>
                <label for="via" class="field w40 cell">
                    <span style="font-weight: bold"> Via: </span>
                    <p type="text" name="nome" id="via"> ${utenteSession.nome} </p>
                </label>
                <label for="civico" class="field w40 cell">
                    <span style="font-weight: bold"> Civico: </span>
                    <p type="text" name="nome" id="civico"> ${utenteSession.nome} </p>
                </label>
                <label for="email" class="field w40 cell">
                    <span style="font-weight: bold"> Email: </span>
                    <p type="text" name="nome" id="email"> ${utenteSession.nome} </p>
                </label>
                <label for="blank" class="field w40 cell" >
                    <span style="font-weight: bold"> </span>
                    <p type="text" name="nome" id="blank"> </p>
                </label>
                <button type="submit" class="btn primary w100"> Modifica </button>
            </div>
            --%>
            <form class="grid-x justify-center align-center" action="${pageContext.request.contextPath}/utente/update-admin"
                  method="post">
                <fieldset
                        class="grid-x cell justify-center"> <%-- vedere se è meglio  w50 o w75 ,  con justify-center , align-center o meno--%>
                    <legend> Profilo</legend>
                    <label for="nome" class="field cell w40">
                        <span style="font-weight: bold"> Nome: </span>
                        <input type="text" name="nome" id="nome" value="${utenteSession.nome}">
                    </label>
                    <label for="cognome" class="field cell w40">
                        <span style="font-weight: bold"> Cognome: </span>
                        <input type="text" name="nome" id="cognome" value="${utenteSession.nome}">
                    </label>
                    <label for="provincia" class="field cell w40">
                        <span style="font-weight: bold"> Provincia: </span>
                        <input type="text" name="nome" id="provincia" value="${utenteSession.nome}">
                    </label>
                    <label for="citta" class="field cell w40">
                        <span style="font-weight: bold"> Citt&agrave: </span>
                        <input type="text" name="nome" id="citta" value="${utenteSession.nome}">
                    </label>
                    <label for="via" class="field cell w40">
                        <span style="font-weight: bold"> Via: </span>
                        <input type="text" name="nome" id="via" value="${utenteSession.nome}">
                    </label>
                    <label for="civico" class="field cell w40">
                        <span style="font-weight: bold"> Civico: </span>
                        <input type="text" name="nome" id="civico" value="${utenteSession.nome}">
                    </label>
                    <label for="email" class="field cell w40">
                        <span style="font-weight: bold"> Email: </span>
                        <input type="text" name="nome" id="email" value="${utenteSession.nome}" disabled>
                    </label>
                    <label for="blank" class="field cell w40" style="visibility: hidden ">
                        <span style="font-weight: bold"> Email: </span>
                        <input type="text" name="nome" id="blank" value="${utenteSession.nome}" disabled>
                    </label>
                    <label for="modifica" class="field cell w40 align-center justify-center">
                        <button type="submit" class="btn primary" id="modifica"> Modifica dati</button>
                    </label>
                    <label for="modificaPW" class="field cell w40 align-center justify-center">
                        <button type="submit" class="btn primary" id="modificaPW" formaction="${pageContext.request.contextPath}/utente/update-pw" formmethod="get"> Modifica Password</button>
                    </label>
                </fieldset>

            </form>
        </div>
        <%@include file="../partials/crm/footer.jsp" %>
    </section>
</main>
</body>
</html>
