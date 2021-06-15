package controller.ordine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="ordineServlet", value="/ordine/*")
public class ordineServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=(req.getPathInfo() != null) ? req.getPathInfo() : "/";
        switch (path){
            case "/":
                break;
            case "/ordini-utente":
                break;
            case "/ordini-rider"://passare come parametro lo stato della consegna
                break;
            case "/ordini-ristorante":
                break;
            case "/all"://possibile inviare date come parametri per filtrare gli ordini
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,"Risorsa non trovata");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=(req.getPathInfo() != null) ? req.getPathInfo() : "/";
        switch (path){
            case "/":
                break;
            case "/ordini-recensione-utente":
                break;
            case "/ordini-update-rider":
                break;
            default:
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"Operazione non consentita");
        }
    }
}