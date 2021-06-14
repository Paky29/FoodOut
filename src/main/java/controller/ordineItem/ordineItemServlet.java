package controller.ordineItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="ordineItemServlet", value="/ordineItem/*")
public class ordineItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=(req.getPathInfo() != null) ? req.getPathInfo() : "/";
        switch (path){
            case "/":
                break;
            case "/insert-amount"://inserisci la quantità del prodotto o menu, visualizza per i prodotti i menu dove si trova, mentre per i menu i nomi dei prodtti da cui è composto
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
            case "/add-item":
                break;//aggiungi l'ordine item all'ordine
            default:
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"Operazione non consentita");
        }
    }
}
