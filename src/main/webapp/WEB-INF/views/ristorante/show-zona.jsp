<%@ page import="model.ristorante.Ristorante" %>
<%@ page import="model.tipologia.Tipologia" %>
<%@ page import="java.util.StringJoiner" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Scegli ristorante"/>
        <jsp:param name="styles" value="show_zona"/>
        <jsp:param name="scripts" value="show_zona"/>
    </jsp:include>
    <style>
        #welcome, #back {
            cursor: pointer;
        }

        #welcome{
            display: none;
        }

        #container-links {
            position: absolute;
            top:0px;
            right:0px;
            height: 10%;
        }

        #links{
            background-color: var(--primary);
            padding: 3px;
            border: 1px solid var(--primary);
            border-radius: 10px;
            margin: 5px;
            font-weight: bold;
            font-family: Myriad;
        }

        @media screen and (min-width: 768px){
            #welcome{
                display: inline;
            }
        }
    </style>
</head>
<body>
<div class="app">
    <div class="cell grid-x" id="header">
        <div id="container-links" class="cell" style="justify-content: flex-end">
            <c:choose>
                <c:when test="${utenteSession==null}">
                    <div id="links">
                        <a href="${pageContext.request.contextPath}/utente/login"> Accedi </a>
                        <a href="${pageContext.request.contextPath}/utente/signup"> Registrati </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="user">
                        <span class="account" style="color: white" onclick="toProfile()">
                            <%@include file="../../../icons/user.svg" %>
                            <span id="welcome">Benvenuto, ${utenteSession.nome}</span>
                        </span>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <nav class="grid-y navbar align-center cell">
            <img src="/FoodOut/images/logo.png" class="fluid-image" id="logo">
            <c:choose>
                <c:when test="${utenteSession==null}">
                    <div id="guest" style="color: white" onclick="goToIndex()" title="Clicca per cambiare citta">
                        <span id="back">${citta}</span>
                    </div>
                </c:when>
                <c:otherwise>
                    <div id="citta" style="color: white">
                        <span>${utente.citta}</span>
                    </div>
                </c:otherwise>
            </c:choose>
            <label class="field command w100 justify-center">
                <input type="text" placeholder="Cerca Ristoranti">
            </label>
        </nav>
        <section class="cell w100 grid-x container">
            <form class="cell grid-x search w20">
                <section class="grid-x cell w100">
                    <h3 class="cell w100 title" style="color:white"> Tipologie: </h3>
                    <c:if test="${not empty tipologie}">
                        <c:forEach items="${tipologie}" var="tipologia">
                            <div class="cell grid-x align-center filtro">
                                <input type="checkbox" id="tipologia" name="tipologia" value="${tipologia.nome}">
                                <label for="tipologia"> ${tipologia.nome} </label>
                            </div>
                        </c:forEach>
                    </c:if>

                    <h3 class="cell w100 title" style="color:white"> Filtri: </h3>
                    <div class="cell grid-x align-center filtro">
                        <input type="checkbox" id="sconto" name="sconto" value="1">
                        <label for="sconto"> Sconto </label>
                    </div>
                    <div class="cell grid-x align-center filtro">
                        <input type="checkbox" id="gratis" name="gratis" value="1">
                        <label style="font-size: 15px;" for="gratis"> Consegna gratis </label>
                    </div>
                </section>
            </form>
            <div class="cell w75 grid-x justify-center show-ris">
                <div class="grid-x justify-center info-ris cell">
                    <div class="grid-x cell w100 index" style="border: 1px solid lightgrey">
                        <c:choose>
                            <c:when test="${not empty ristoranti}">
                                <h2 class="cell"> Ristoranti </h2>
                                <c:forEach items="${ristoranti}" var="ristorante">
                                    <label class="field cell w100 ristorante grid-x" onclick="showRisDetails(this)"
                                           title="Clicca per visitare">
                                        <div class="w70">
                                            <div class="w80" style="font-weight: bold;">${ristorante.nome}</div>
                                            <c:forEach begin="0" end="2" var="counter">
                                                <span class="w80"
                                                      style="color:black;font-weight: normal; font-style: italic"> ${ristorante.tipologie[counter].nome}</span>
                                            </c:forEach>
                                            <div class="w80">
                                                <c:forEach var="counter" begin="1" end="${ristorante.rating}">
                                                    <%@include file="../../../icons/moto.svg" %>
                                                </c:forEach>
                                            </div>
                                            <div class="w80"
                                                 style="color:black;font-weight: normal; font-style: italic;"> Spesa
                                                minima: ${ristorante.spesaMinima}</div>
                                            <div class="w80"
                                                 style="color:black;font-weight: normal; font-style: italic"> Tasso
                                                consegna: ${ristorante.tassoConsegna}</div>

                                        </div>

                                        <img class="w80" src="/FoodOut/covers/${ristorante.urlImmagine}">
                                        <input style="display: none" id="id" name="id" value="${ristorante.codice}"/>
                                    </label>
                                </c:forEach>
                            </c:when>
                            <c:otherwise><h2> Non sono presenti ristoranti </h2></c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="cell justify-center ">
                    <jsp:include page="../partials/paginator.jsp">
                        <jsp:param name="risorsa" value="zona"/>
                    </jsp:include>
                </div>
            </div>
            <footer class="info grid-x cell justify-center align-center">
                <a href="faq.jsp" class="cell w10"> FAQ </a>
                <a href="/" class="cell w10"> Chi siamo </a>
                <a href="/" class="cell w10"> Collabora con noi</a>
                <a href="contatti.jsp" class="cell w10"> Contatti</a>
            </footer>
        </section>

    </div>
</div>
</body>
</html>
