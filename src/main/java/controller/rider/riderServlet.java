package controller.rider;
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
import controller.tipologia.tipologiaValidator;
import model.rider.Rider;
import model.rider.RiderDAO;
import model.ristorante.Ristorante;
import model.ristorante.RistoranteDAO;
import model.tipologia.TipologiaDAO;
import model.utility.Paginator;


@WebServlet(name="riderServlet", value="/rider/*")
public class riderServlet extends controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=getPath(req);
        try {
            switch (path) {
                case "/":
                    break;
                case "/create":
                    break;
                case "/profile"://mostra il profilo al rider
                    break;
                case "/show": //mostra profilo all'admin
                    break;
                case "/all": { //possibili filtri
                    authorizeUtente(req.getSession());
                    RiderDAO service = new RiderDAO();
                    if (req.getParameter("page") != null) {
                        validate(CommonValidator.validatePage(req));
                    }
                    int intPage = parsePage(req);
                    int totRid = service.countAll();
                    Paginator paginator = new Paginator(intPage, 6);
                    int size = service.countAll();
                    req.setAttribute("pages", paginator.getPages(size));
                    ArrayList<Rider> riders = service.doRetrieveAll(paginator);
                    req.setAttribute("riders", riders);
                    req.setAttribute("totRid", totRid);
                    req.getRequestDispatcher(view("rider/show-all")).forward(req, resp);
                    break;
                }
                case "/delete": {
                    authorizeUtente(req.getSession());
                    validate(CommonValidator.validateId(req));
                    RiderDAO service = new RiderDAO();
                    int id = Integer.parseInt(req.getParameter("id"));
                    Rider r=service.doRetrievebyId(id);
                    if(r==null)
                        notFound();
                    if (service.doDelete(id)) {
                        resp.sendRedirect("/FoodOut/rider/all");
                    }
                    else
                        InternalError();
                    break;
                }
                default:
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Risorsa non trovata");
            }
        }
        catch (SQLException e) {
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
        switch(path){
            case "/":
                break;
            case "/create":
                break;
            case "/update":
                break;
            case "/delete":
                break;
            default:
                resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,"Operazione non consentita");
        }
    }
}
