package controller.ordine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import controller.http.CommonValidator;
import controller.http.InvalidRequestException;
import controller.http.controller;
import model.ordine.Ordine;
import model.ordine.OrdineDAO;
import model.utility.Paginator;

@WebServlet(name="ordineServlet", value="/ordine/*")
public class ordineServlet extends controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        try {
            switch (path) {
                case "/utente-storico":
                    break;
                case "/utente-attesa":
                    break;
                case "/dettagli": {
                    authorizeUtente(req.getSession());
                    OrdineDAO service = new OrdineDAO();
                    validate(CommonValidator.validateId(req));
                    int id = Integer.parseInt(req.getParameter("id"));
                    Ordine o = service.doRetrieveById(id);
                    if (o == null)
                        notFound();
                    else {
                        req.setAttribute("ordine", o);
                        req.getRequestDispatcher(view("ordine/show")).forward(req, resp);
                    }
                    break;
                }
                /*case "/rider"://passare come parametro lo stato della consegna
                    break;*/
                case "/ristorante":
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

                    if(ordini!=null)
                        for(Ordine o: ordini)
                            incasso+=o.getTotale();

                    req.setAttribute("ordini", ordini);
                    req.setAttribute("totOrd", totOrd);
                    req.setAttribute("incasso", incasso);
                    req.getRequestDispatcher(view("ordine/show-all")).forward(req, resp);
                    break;
                }
                case "/pagamento":
                    break;
                case "/recensione":
                    break;
                case "/delete":{
                    authorizeUtente(req.getSession());
                    validate(CommonValidator.validateId(req));
                    int codice=Integer.parseInt(req.getParameter("id"));
                    OrdineDAO serviceOrd=new OrdineDAO();
                    if(serviceOrd.doRetrieveById(codice)==null)
                        notFound();
                    if(serviceOrd.doDelete(codice)){
                        resp.sendRedirect("/FoodOut/ordine/all");
                    }
                    else
                        InternalError();
                    break;
                }
                default:
                    notFound();
            }
        }catch (SQLException e) {
            log(e.getMessage());
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
        try {
            switch (path) {
                case "/ordine-recensione-utente":
                    break;
                case "/ordine-pagamento":
                    break;
                case "/update": {
                    authorizeUtente(req.getSession());
                    validate(CommonValidator.validateId(req));
                    validate(ordineValidator.validateFormAdmin(req));
                    OrdineDAO service=new OrdineDAO();
                    int id=Integer.parseInt(req.getParameter("id"));
                    Ordine o=service.doRetrieveById(id);
                    if(o==null)
                        notFound();
                    else {
                        if(!o.isConsegnato()) {
                            String oraPartenza = req.getParameter("oraPartenza");
                            String oraArrivo = req.getParameter("oraArrivo");
                            if (!oraPartenza.isBlank())
                                o.setOraPartenza(LocalTime.parse(oraPartenza));
                            if (!oraArrivo.isBlank()) {
                                o.setConsegnato(true);
                                o.setOraArrivo(LocalTime.parse(oraArrivo));
                            }
                        }
                        if(service.doUpdate(o))
                            resp.sendRedirect("/FoodOut/ordine/dettagli?id=" + o.getCodice());
                    }
                    break;
                }
                default:
                    notAllowed();
            }
        }

        catch (SQLException e) {
            log(e.getMessage());
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

        catch (InvalidRequestException e) {
            log(e.getMessage());
            System.out.println(e.getMessage());
            e.handle(req,resp);
        }
    }
}
