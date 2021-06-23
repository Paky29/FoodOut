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
    <aside class="sidebar">
        <nav class="menu grid-y align-center">
            <img src="/prova_DB/images/logo.png" width="100" height="100">
            <a href="/"> Gestione rider</a>
            <a href="/"> Gestione ristoranti</a>
            <a href="/"> Gestione ordini</a>
            <a href="/"> Gestione tipologie</a>
            <a href="/"> Profilo</a>
            <a href="/"> Logout</a>
        </nav>
    </aside>
    <section class="content grid-y">
        <header class="topbar grid-x align-center">
            <%@include file="../../../icons/menu.svg"%>
            <label class="field command">
                <input type="text" placeholder="Cerca comandi">
            </label>
            <span class="account" style="color: white">
                <%@include file="../../../icons/menu.svg"%> <%--cambiare con icona user--%>
                Benvenuto, ${utenteSession.nome}
            </span>
        </header>
        <div class="body"></div>
        <footer class="info">
            <p> Copyright 2020 Foodout </p>
        </footer>
    </section>
</main>
</body>
</html>
