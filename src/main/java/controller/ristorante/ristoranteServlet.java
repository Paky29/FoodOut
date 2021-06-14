package controller.ristorante;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet (name="ristoranteServlet", value="/ristorante/*")
public class ristoranteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=(req.getPathInfo()!=null) ? req.getPathInfo() : "/";
        switch(path){
            case "/":
                break;
            case "/all"://controllare se i parametri sono null per capire se è per l'admin o un utente
                break;
            case "/zona"://controllare se i parametri sono null per capire se è per l'admin o un utente
                break;
            case "/show-menu"://verificare se l'utente è admin, se è normale possibilità di aggiungere al carrello i prodotti, altrimenti di modificarli
                break;
            case "/show-info":
                break;
            case "/show-recensioni":
                break;
            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,"Risorsa non trovata");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=(req.getPathInfo()!=null) ? req.getPathInfo() : "/";
        switch(path){
            case "/":
                break;
            case "/show-menu"://modificare i prodotti e i menu
                break;
            case "/add-pref":
                break;
            case "update":
                break;
        }
    }
}
