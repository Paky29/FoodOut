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
        <jsp:param name="title" value="Login"/>
    </jsp:include>
    <style>
        .app {
            background: linear-gradient(var(--primary), white);
        <%--background-image: url("/prova_DB/images/sfondo.jpg");--%>
        }

        img {
            width: 100%;
            height: auto;
            opacity: 0.3;
        }
        .update-pw{
            padding: 1rem; <%--dimensione relativa al root--%>
            background-color:white;
            border-radius: 10px;
            opacity: revert;
        }

        .update-pw > * {
            margin:10px;
        }


    </style>
</head>
<body>
<form class="app grid-x justify-center align-center" action="${pageContext.request.contextPath}/utente/update-pw" method="post">
    <fieldset class="grid-y cell w50 update-pw">
        <h2> Modifica password </h2>
        <span> Attuale password  </span>
        <label for="old_pw" class="field">
            <input type="password" name="old_pw" id="old_pw">
        </label>
        <span> Nuova password </span>
        <label for="new_pw" class="field">
            <input type="password" name="new_pw" id="new_pw">
        </label>
        <span> Conferma nuova password </span>
        <label for="conf_pw" class="field">
            <input type="password" name="conf_pw" id="conf_pw">
        </label>
        <label for="email" class="field" style="visibility: hidden">
            <input type="text" name="email" id="email" value="${profilo.email}">
        </label>
        <button type="submit" class="btn primary"> Conferma </button>
    </fieldset>
</form>
</body>
</html>
