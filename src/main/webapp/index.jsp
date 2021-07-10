<%@ page import="model.tipologia.TipologiaDAO" %>
<%@ page import="model.tipologia.Tipologia" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Home"/>
        <jsp:param name="styles" value="header"/>
        <jsp:param name="scripts" value="home"/>
    </jsp:include>
</head>
<body>
<style>
    .index {
        padding: 1rem;<%--dimensione relativa al root--%>
        background-color: white;
        border-radius: 10px;
        opacity: revert;
    }

    .index > * {
        margin: 10px;
    }

    input:focus {
        outline: 1px solid var(--primary);
    }

    .tipologie {
        padding: 1rem;
        background-color: white;
        opacity: revert;
        margin-top:10vh;
    }

    .box {
        border: 2px solid var(--primary);
        margin: 10px;
        padding: 15px;
        font-weight: bold;
        font-size: 3vw;
        color: white;
        background-color: var(--primary);
        border-radius: 5px;
    }

    .cover-image{
        max-width: 100%;
        height: 50vh;
    }

    .info{
        text-align: center;
        font-style: normal;
        font-weight: bold;
        padding: 1rem;
        border-top: 1px solid black;
        background-color: lightgrey;
    }

    .info > a {
        text-decoration: none;
        color: black;
        margin-right: 25px;
        margin-left: 25px;
    }

    .content{
        background-color: white;
        flex:1 1 100%;
        transition: all .3s ease-in-out;
    }

    .tipologia{
        margin: 10vw;
        margin-bottom: 5px;
    }

    .tipologia > p:hover{
        transform: scale3d(1.30,1.30,1.30);
    }

</style>
<div class="app grid-x">
    <jsp:include page="WEB-INF/views/partials/header.jsp">
        <jsp:param name="title" value="Header"/>
    </jsp:include>

    <%
        TipologiaDAO service=new TipologiaDAO();
        ArrayList<Tipologia> tipologie=service.doRetrieveByVendite();
        session.removeAttribute("citta");
    %>

    <div class="content">
        <div class="tipologie app grid-x justify-center align-center">
            <%if(tipologie!=null){for(int i=0;i<tipologie.size() && i<4;++i){%>
                <span class="cell w20 tipologia" onclick="search(this)" style="cursor: pointer">
                    <p class="box" value="<%=tipologie.get(i).getNome()%>"><%=tipologie.get(i).getNome()%></p>
                </span><%}}%>
        </div>
    </div>
    <footer class="info grid-x cell justify-center align-center">
        <a href="faq.jsp" class="cell w10"> FAQ </a>
        <a href="/" class="cell w10"> Chi siamo </a>
        <a href="/" class="cell w10"> Collabora con noi</a>
        <a href="contatti.jsp" class="cell w10"> Contatti</a>
    </footer>
</div>

</body>
</html>
