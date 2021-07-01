<%--
  Created by IntelliJ IDEA.
  User: User01
  Date: 26/06/2021
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="FAQ"/>
    </jsp:include>
</head>
<body>
<style>

    .content {
        background-color: white;
        transition: all .3s ease-in-out;
        margin: 20px;
        padding: 10px 40px;
        border-radius: 10px;
    }

    .text {
        position: absolute;
        left: 10%;
        top: 30%;
        width:30%;
    }

    .background {
        position: absolute;
        right: 0%;
        top: 0%;
        z-index: -1;
        width: 50%;
        height: 50%;
    }

    .foreground {
        position: absolute;
        right: 0%;
        top: 0%;
        width: 40%;
        height: 40%;
    }





</style>
<div class="app grid-x">
    <span class=" cell w100">
        <div class="text">
            <h3 class="title"> Hai bisogno di aiuto? </h3>
            <p class="body"> </p> Contattaci. <br> Siamo qui per offrirti il nostro supporto in diversi modi
        </div>
        <img src="images/sfondo.jpg" class="background">
        <img src="images/logo.png " class="foreground">
    </span>
    <span class="cell">


        <div class="topic">

        </div>
    </span>
</div>
</body>
</html>
