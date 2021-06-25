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
    <%@include file="../partials/crm/sidebar.jsp"%>
    <section class="content grid-y">
        <%@include file="../partials/crm/header.jsp"%>
        <div class="body grid-x justify-center">
            <fieldset class="grid-x cell">
                <legend> Profilo </legend>
                <label for="nome" class="field cell w40">
                    <span style="font-weight: bold"> Nome: </span>
                    <p type="text" name="nome" id="nome"> ${utenteSession.nome} </p>
                </label>
                <label for="cognome" class="field cell w40">
                    <span style="font-weight: bold"> Cognome: </span>
                    <p type="text" name="nome" id="cognome"> ${utenteSession.nome} </p>
                </label>
                <label for="provincia" class="field cell w40">
                    <span style="font-weight: bold"> Provincia: </span>
                    <p type="text" name="nome" id="provincia"> ${utenteSession.nome} </p>
                </label>
                <label for="citta" class="field cell w40">
                    <span style="font-weight: bold"> Citt&agrave: </span>
                    <p type="text" name="nome" id="citta"> ${utenteSession.nome} </p>
                </label>
                <label for="via" class="field cell w40">
                    <span style="font-weight: bold"> Via: </span>
                    <p type="text" name="nome" id="via"> ${utenteSession.nome} </p>
                </label>
                <label for="civico" class="field cell w40">
                    <span style="font-weight: bold"> Civico: </span>
                    <p type="text" name="nome" id="civico"> ${utenteSession.nome} </p>
                </label>
                <label for="email" class="field cell w40">
                    <span style="font-weight: bold"> Email: </span>
                    <p type="text" name="nome" id="email"> ${utenteSession.nome} </p>
                </label>
                <label for="modifica" class="field cell w100 justify-center">
                    <button type="submit" class="btn primary" id="modifica"> Modifica </button>
                </label>
            </fieldset>
        </div>
        <%@include file="../partials/crm/footer.jsp"%>
    </section>
</main>
</body>
</html>
