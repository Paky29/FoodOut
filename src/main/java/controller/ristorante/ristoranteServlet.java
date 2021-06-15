package controller.ristorante;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import controller.http.controller;


@WebServlet (name="ristoranteServlet", value="/ristorante/*")
public class ristoranteServlet extends controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        switch(path){
            case "/":
                break;
            case "/all"://controllare se i parametri sono null per capire se è per l'admin o un utente
                break;
            case "/zona"://controllare se i parametri sono null per capire se è per l'admin o un utente
                break;
            case "/show-menu"://verificare se l'utente è admin, se è normale possibilità di aggiungere al carrello i prodotti, altrimenti di modificarli
                break;
            case "/show-info"://se l'utente è admin, mostrargli info modificabili e possibilità di cancellazione, altrimenti statiche
                break;
            case "/show-recensioni":
                break;
            case "/add":
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,"Risorsa non trovata");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        switch(path){
            case "/":
                break;
            case "/show-menu"://modificare i prodotti e i menu
                break;
            case "/add-pref":
                break;
            case "/update":
                break;
            case "/add":
                break;
            case "/delete":
                break;
            default:
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"Operazione non consentita");
        }
    }
}
