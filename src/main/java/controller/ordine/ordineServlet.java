package controller.ordine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.http.CommonValidator;
import controller.http.InvalidRequestException;
import controller.http.controller;
import model.ordine.Ordine;
import model.ordine.OrdineDAO;
import model.ordine.OrdineItem;
import model.rider.Rider;
import model.rider.RiderDAO;
import model.utility.Paginator;

@WebServlet(name="ordineServlet", value="/ordine/*")
public class ordineServlet extends controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        try {
            switch (path) {
                case "/":
                    break;
                case "/ordini-utente":
                    break;
                case "ordine-dettagli":
                    break;
                case "/ordini-rider"://passare come parametro lo stato della consegna
                    break;
                case "/ordini-ristorante":
                    break;
                case "/all": {//possibile inviare date come parametri per filtrare gli ordini
                    authorizeUtente(req.getSession());
                    OrdineDAO service = new OrdineDAO();
                    if (req.getParameter("page") != null) {
                        validate(CommonValidator.validatePage(req));
                    }
                    int intPage = parsePage(req);
                    int totOrd = service.countAll();
                    int incasso=0;
                    Paginator paginator = new Paginator(intPage, 6);
                    int size = service.countAll();
                    req.setAttribute("pages", paginator.getPages(size));
                    ArrayList<Ordine> ordini = service.doRetrieveAll(paginator);
                    for(Ordine o: ordini)
                        incasso+=o.getTotale();

                    req.setAttribute("ordini", ordini);
                    req.setAttribute("totOrd", totOrd);
                    req.setAttribute("incasso", incasso);
                    req.getRequestDispatcher(view("ordine/show-all")).forward(req, resp);
                    break;
                }
                case "/ordine-pagamento":
                    break;
                case "/ordine-recensione":
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Risorsa non trovata");
            }
        }catch (SQLException e) {
            log(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
        catch (InvalidRequestException e) {
            log(e.getMessage());
            System.out.println(e.getMessage());
            e.handle(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        switch (path){
            case "/":
                break;
            case "/ordine-recensione-utente":
                break;
            case "/ordine-update-rider":
                break;
            case "/ordine-pagamento":
                break;
            default:
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"Operazione non consentita");
        }
    }
}
