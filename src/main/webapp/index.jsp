<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="FoodOut a volo"/>
    </jsp:include>
    <style>
        p{
            font-family:"Myriad";
            font-weight: bold;
            font-style: normal;
        }
    </style>

</head>
<body>
<form action="Tryservlet">
    <input type="submit">
    <p>hello</p>
</form>
</body>
</html>