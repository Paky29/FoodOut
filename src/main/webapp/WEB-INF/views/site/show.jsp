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
        <div class="body"></div>
        <%@include file="../partials/crm/footer.jsp"%>
    </section>
</main>
</body>
</html>
