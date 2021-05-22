<%@ page import="model.tipologia.Tipologia" %>
<%@ page import="model.ristorante.Ristorante" %><%--
  Created by IntelliJ IDEA.
  User: User01
  Date: 12/05/2021
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Restaurant</title>
</head>
<body>
${utente.ordini[0].codice}
${utente.ordini[0].dataOrdine}
${utente.ordini[0].ordineItem[0].nome}
${utente.ordini[0].ordineItem[1].nome}
${utente.ordini[0].ordineItem[2].nome}
</body>
</html>
